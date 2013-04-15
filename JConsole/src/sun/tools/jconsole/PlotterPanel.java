/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.tools.jconsole;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.accessibility.AccessibleContext;
import javax.swing.JPopupMenu;
/**
 * 包含坐标图的面板
 */
@SuppressWarnings("serial")
public class PlotterPanel extends BorderedComponent {
	Plotter plotter;
    /**
     * @param labelStr 坐标图标题
     * @param unit y坐标单位,参考 {@link Plotter.Unit}
     * @param collapsible 是否可收缩
     */
	public PlotterPanel(String labelStr, Plotter.Unit unit, boolean collapsible) {
		super(labelStr, new Plotter(unit), collapsible);

		this.plotter = (Plotter) comp;
        //设置可以获得焦点
		init();
	}

	public PlotterPanel(String labelStr) {
		super(labelStr, null);

		init();
	}

	public Plotter getPlotter() {
		return this.plotter;
	}

	public void setPlotter(Plotter plotter) {
		this.plotter = plotter;
		setComponent(plotter);
	}
    /**
     * 设置可以获得焦点
     */
	private void init() {
		setFocusable(true);
        //鼠标单击获得焦点
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				requestFocusInWindow();
			}
		});
	}

	public JPopupMenu getComponentPopupMenu() {
		return (getPlotter() != null) ? getPlotter().getComponentPopupMenu()
				: null;
	}

	public AccessibleContext getAccessibleContext() {
		if (accessibleContext == null) {
			accessibleContext = new AccessiblePlotterPanel();
		}
		return accessibleContext;
	}

	protected class AccessiblePlotterPanel extends AccessibleJComponent {
		public String getAccessibleName() {
			String name = null;
			if (getPlotter() != null) {
				name = getPlotter().getAccessibleContext().getAccessibleName();
			}
			if (name == null) {
				name = super.getAccessibleName();
			}
			return name;
		}
	}
}
