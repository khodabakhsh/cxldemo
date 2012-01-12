package com.cxl.zhougongjiemeng;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cxl.zhougongjiemeng.util.DataParser;
import com.cxl.zhougongjiemeng.util.IndexParser;

public class MainActivity extends Activity {
	 private static String js;
	  private static String style;
	  private LinearLayout Lv;
	  Handler dh;
	  private ImageView iv;
	  private LinearLayout l;

	 
	  private ArrayAdapter s1_aspn;

	 
	  private ArrayAdapter s2_aspn;
	  private Toast toast;
	  private WebView web;


	  public void onCreate(Bundle paramBundle)
	  {
	    super.onCreate(paramBundle);
	    setContentView(R.layout.main);
	    WebView localWebView = (WebView)findViewById(R.id.more_webview);
	    this.web = localWebView;
	    ImageView localImageView = (ImageView)findViewById(R.id.welcome_ImageView01);
	    this.iv = localImageView;
	    this.iv.setImageResource(R.drawable.zgjm);
//	    this.web.setVisibility(View.GONE);
	    this.web.getSettings().setJavaScriptEnabled(true);
	    Hashtable localHashtable = IndexParser.getInstance(this).getResult();
	    ArrayList<String> localArrayList = new ArrayList<String>();
	    Enumeration localEnumeration = localHashtable.keys();
	    
	    Spinner localSpinner1 = (Spinner)findViewById(R.id.Spinner01);
        final Spinner localSpinner2 = (Spinner)findViewById(R.id.Spinner02);
        
        final IndexParser indexParser = IndexParser.getInstance(this);
	    	while (localEnumeration.hasMoreElements())
	      {
	    		String str3 = (String)localEnumeration.nextElement();
	  	      localArrayList.add(str3);
//	        style = getResources().getString(2131034120);
//	        js = getResources().getString(2131034119);
	        
	        String str1 = new DataParser(this).getResult("情爱", "少女");
	      }
	    	   this.s1_aspn =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, localArrayList);
	    	   localSpinner1.setAdapter(s1_aspn);
		        localSpinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						   String typeDetail =  (String) indexParser.getResult().get(arg0.getSelectedItem().toString());
						ArrayAdapter adapter  =  new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, typeDetail.split("#"));
				    	   localSpinner2.setAdapter(adapter);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						
					}
				});
		       String typeDetail =  (String) indexParser.getResult().get((String)s1_aspn.getItem(0));
		       
		    	   this.s2_aspn =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeDetail.split("#"));
		    	   localSpinner2.setAdapter(s2_aspn);
			        localSpinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

						public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
							
						}

						public void onNothingSelected(AdapterView<?> arg0) {
							
						}
					});
	    }
}