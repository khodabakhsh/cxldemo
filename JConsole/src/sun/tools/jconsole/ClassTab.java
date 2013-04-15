/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.tools.jconsole;

import static sun.tools.jconsole.Formatter.formatDateTime;
import static sun.tools.jconsole.Formatter.justify;
import static sun.tools.jconsole.Formatter.newRow;
import static sun.tools.jconsole.Resources.getMnemonicInt;
import static sun.tools.jconsole.Resources.getText;
import static sun.tools.jconsole.Utilities.setAccessibleName;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.ExecutionException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
/**
 * “类”面板
 * @author cxl , 2013-4-14
 */
@SuppressWarnings("serial")
class ClassTab extends Tab implements ActionListener {
	/**
	 * “已装入类的数目”面板
	 */
	PlotterPanel loadedClassesMeter;
	/**
	 * “时间范围”下拉列表
	 */
	TimeComboBox timeComboBox;
	/**
	 * “详细输出”checkbox
	 */
	private JCheckBox verboseCheckBox;
	/**
	 * "详细输出"对应的HTMLPane
	 */
	private HTMLPane details;
	/**
	 * 用于在“概述”tab页显示的“类”坐标图
	 */
	private ClassOverviewPanel overviewPanel;
	private boolean plotterListening = false;

	private static final String loadedPlotterKey = "loaded";
	private static final String totalLoadedPlotterKey = "totalLoaded";
	/**
	 * 已装入
	 */
	private static final String loadedPlotterName = Resources.getText("Loaded");
	/**
	 * 已装入的总数
	 */
	private static final String totalLoadedPlotterName = Resources
			.getText("Total Loaded");
	private static final Color loadedPlotterColor = Plotter.defaultColor;
	private static final Color totalLoadedPlotterColor = Color.red;

	private static final String infoLabelFormat = "ClassTab.infoLabelFormat";

	/*
	 * Hierarchy of panels and layouts for this tab:
	 * 
	 * ClassTab (BorderLayout)
	 * 
	 * North: topPanel (BorderLayout)
	 * 
	 * Center: controlPanel (FlowLayout) timeComboBox
	 * 
	 * East: topRightPanel (FlowLayout) verboseCheckBox
	 * 
	 * Center: plotterPanel (BorderLayout)
	 * 
	 * Center: plotter
	 * 
	 * South: bottomPanel (BorderLayout)
	 * 
	 * Center: details
	 */

	public static String getTabName() {
		return Resources.getText("Classes");
	}

	public ClassTab(VMPanel vmPanel) {
		super(vmPanel, getTabName());

		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(4, 4, 3, 4));
		
        //顶层，包括“时间范围”下列列表、详细输出checkbox
		JPanel topPanel = new JPanel(new BorderLayout());
		//中间层，显示“已装入类的数目”
		JPanel plotterPanel = new JPanel(new BorderLayout());
		//底层，显示“详细信息”
		JPanel bottomPanel = new JPanel(new BorderLayout());

		add(topPanel, BorderLayout.NORTH);
		add(plotterPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,
				5));
		topPanel.add(controlPanel, BorderLayout.CENTER);

		verboseCheckBox = new JCheckBox(Resources.getText("Verbose Output"));
		verboseCheckBox.addActionListener(this);
		verboseCheckBox.setToolTipText(getText("Verbose Output.toolTip"));
		JPanel topRightPanel = new JPanel();
		topRightPanel.setBorder(new EmptyBorder(0, 65 - 8, 0, 70));
		topRightPanel.add(verboseCheckBox);
		topPanel.add(topRightPanel, BorderLayout.AFTER_LINE_ENDS);

		loadedClassesMeter = new PlotterPanel(
				Resources.getText("Number of Loaded Classes"),
				Plotter.Unit.NONE, false);
		loadedClassesMeter.plotter.createSequence(loadedPlotterKey,
				loadedPlotterName, loadedPlotterColor, true);
		loadedClassesMeter.plotter.createSequence(totalLoadedPlotterKey,
				totalLoadedPlotterName, totalLoadedPlotterColor, true);
		setAccessibleName(loadedClassesMeter.plotter,
				getText("ClassTab.loadedClassesPlotter.accessibleName"));
		plotterPanel.add(loadedClassesMeter);

		timeComboBox = new TimeComboBox(loadedClassesMeter.plotter);
		controlPanel.add(new LabeledComponent(Resources.getText("Time Range:"),
				getMnemonicInt("Time Range:"), timeComboBox));

		LabeledComponent.layout(plotterPanel);

		bottomPanel.setBorder(new CompoundBorder(new TitledBorder(Resources
				.getText("Details")), new EmptyBorder(10, 10, 10, 10)));

		details = new HTMLPane();
		setAccessibleName(details, getText("Details"));
		JScrollPane scrollPane = new JScrollPane(details);
		scrollPane.setPreferredSize(new Dimension(0, 150));
		bottomPanel.add(scrollPane, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent ev) {
		final boolean b = verboseCheckBox.isSelected();
		workerAdd(new Runnable() {
			public void run() {
				ProxyClient proxyClient = vmPanel.getProxyClient();
				try {
					proxyClient.getClassLoadingMXBean().setVerbose(b);
				} catch (UndeclaredThrowableException e) {
					proxyClient.markAsDead();
				} catch (IOException ex) {
					// Ignore
				}
			}
		});
	}

	/**
	 * 每次调用此方法，都新建一个SwingWorker实例，完成数据更新任务
	 */
	public SwingWorker<?, ?> newSwingWorker() {
		final ProxyClient proxyClient = vmPanel.getProxyClient();

		if (!plotterListening) {
			proxyClient
					.addWeakPropertyChangeListener(loadedClassesMeter.plotter);
			plotterListening = true;
		}

		return new SwingWorker<Boolean, Object>() {
			private long clCount, cuCount, ctCount;
			private boolean isVerbose;
			private String detailsStr;
			private long timeStamp;

			public Boolean doInBackground() {
				try {
					ClassLoadingMXBean classLoadingMBean = proxyClient
							.getClassLoadingMXBean();

					clCount = classLoadingMBean.getLoadedClassCount();
					cuCount = classLoadingMBean.getUnloadedClassCount();
					ctCount = classLoadingMBean.getTotalLoadedClassCount();
					isVerbose = classLoadingMBean.isVerbose();
					detailsStr = formatDetails();
					timeStamp = System.currentTimeMillis();

					return true;
				} catch (UndeclaredThrowableException e) {
					proxyClient.markAsDead();
					return false;
				} catch (IOException e) {
					return false;
				}
			}

			protected void done() {
				try {
					if (get()) {
						loadedClassesMeter.plotter.addValues(timeStamp,
								clCount, ctCount);

						if (overviewPanel != null) {
							overviewPanel.updateClassInfo(ctCount, clCount);
							overviewPanel.getPlotter().addValues(timeStamp,
									clCount);
						}

						loadedClassesMeter.setValueLabel(clCount + "");
						verboseCheckBox.setSelected(isVerbose);
						details.setText(detailsStr);
					}
				} catch (InterruptedException ex) {
				} catch (ExecutionException ex) {
					if (JConsole.isDebug()) {
						ex.printStackTrace();
					}
				}
			}

			private String formatDetails() {
				String text = "<table cellspacing=0 cellpadding=0>";

				long time = System.currentTimeMillis();
				String timeStamp = formatDateTime(time);
				text += newRow(Resources.getText("Time"), timeStamp);
				text += newRow(Resources.getText("Current classes loaded"),
						justify(clCount, 5));
				text += newRow(Resources.getText("Total classes loaded"),
						justify(ctCount, 5));
				text += newRow(Resources.getText("Total classes unloaded"),
						justify(cuCount, 5));

				return text;
			}
		};
	}

	OverviewPanel[] getOverviewPanels() {
		if (overviewPanel == null) {
			overviewPanel = new ClassOverviewPanel();
		}
		return new OverviewPanel[] { overviewPanel };
	}

	/**
	 * 用于在“概述”tab页显示的“类”坐标图
	 */
	private static class ClassOverviewPanel extends OverviewPanel {
		ClassOverviewPanel() {
			super(getText("Classes"), loadedPlotterKey, loadedPlotterName, null);
		}

		private void updateClassInfo(long total, long loaded) {
			long unloaded = (total - loaded);
			getInfoLabel().setText(
					getText(infoLabelFormat, loaded, unloaded, total));
		}
	}
}
