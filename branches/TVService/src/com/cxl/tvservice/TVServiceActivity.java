package com.cxl.tvservice;

import java.util.List;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class TVServiceActivity extends TabActivity {

	TextView TVdetails;

	Spinner areaSpinner;
	Spinner TVstationSpinner;
	Spinner TVchannelSpinner;

	List<KeyValuePair> TVstationSpinnerList;
	List<KeyValuePair> TVchannelSpinnerList;
	ArrayAdapter<KeyValuePair> TVstationaAdapter;
	ArrayAdapter<KeyValuePair> TVchannelAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TVdetails = (TextView) findViewById(R.id.TVdetails);

		areaSpinner = (Spinner) findViewById(R.id.area);
		List<KeyValuePair> lst = TVServiceHelper.getAreas();
		ArrayAdapter<KeyValuePair> myaAdapter = new ArrayAdapter<KeyValuePair>(this,
				android.R.layout.simple_spinner_item, lst);
		areaSpinner.setAdapter(myaAdapter);
		areaSpinner.setSelection(TVServiceHelper.currentAreaSpinnerIndex, true);
		areaSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selectedId = ((KeyValuePair) areaSpinner.getSelectedItem()).getKey();
				TVstationSpinnerList = TVServiceHelper.getTVstationString(selectedId);
				TVstationaAdapter = new ArrayAdapter<KeyValuePair>(TVServiceActivity.this,
						android.R.layout.simple_spinner_item, TVstationSpinnerList);
				TVstationSpinner.setAdapter(TVstationaAdapter);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		TVstationSpinner = (Spinner) findViewById(R.id.TVstation);
		//当前选择的AreaId
		String currentSelectAreaId = ((KeyValuePair) areaSpinner.getSelectedItem()).getKey();
		TVstationSpinnerList = TVServiceHelper.getTVstationString(currentSelectAreaId);
		TVstationaAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.simple_spinner_item,
				TVstationSpinnerList);
		TVstationSpinner.setAdapter(TVstationaAdapter);
		TVstationSpinner.setSelection(TVServiceHelper.currentTVstationSpinnerIndex, true);
		TVstationSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selectedId = ((KeyValuePair) TVstationSpinner.getSelectedItem()).getKey();
				TVchannelSpinnerList = TVServiceHelper.getTVchannelString(selectedId);
				TVchannelAdapter = new ArrayAdapter<KeyValuePair>(TVServiceActivity.this,
						android.R.layout.simple_spinner_item, TVchannelSpinnerList);
				TVchannelSpinner.setAdapter(TVchannelAdapter);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		TVchannelSpinner = (Spinner) findViewById(R.id.TVchannel);
		//当前选择的TVstationId
		String currentSelectTVstationId = ((KeyValuePair) TVstationSpinner.getSelectedItem()).getKey();
		TVchannelSpinnerList = TVServiceHelper.getTVchannelString(currentSelectTVstationId);
		TVchannelAdapter = new ArrayAdapter<KeyValuePair>(this, android.R.layout.simple_spinner_item,
				TVchannelSpinnerList);
		TVchannelSpinner.setAdapter(TVchannelAdapter);
		TVchannelSpinner.setSelection(TVServiceHelper.currentTVchannelSpinnerIndex, true);

		TVchannelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selectedId = ((KeyValuePair) TVchannelSpinner.getSelectedItem()).getKey();
				TVdetails.setText(TVServiceHelper.getTVprogramDetail(selectedId, ""));
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		TVdetails.setText(TVServiceHelper.getTVprogramDetail(((KeyValuePair) TVchannelSpinner.getSelectedItem()).getKey(), ""));

		TabHost mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("TAB 1").setContent(R.id.mainPanel));
		//		mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("TAB 2").setContent(R.id.TVstation));

		mTabHost.setCurrentTab(0);

	}

}