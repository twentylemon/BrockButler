package edu.seaaddicts.brockbutler.help;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import edu.seaaddicts.brockbutler.R;
public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView wb = new WebView(this);
		wb.getSettings().setJavaScriptEnabled(true);
		wb.getSettings().setPluginState(PluginState.ON);
		//this does not work contrary to popular belief
		//wb.getSettings().setBuiltInZoomControls(false);
		//wb.getSettings().setUseWideViewPort(true);
		//wb.getSettings().setLoadWithOverviewMode(true);
		float scaling = 100;
		int display_width;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		display_width = dm.widthPixels;
		scaling = (((float)display_width/400)*100); // 400 here is my container div width
		scaling = (int) Math.floor(scaling); 
		wb.setInitialScale((int)scaling);
		Bundle 	bundle = this.getIntent().getExtras();
		String actName = bundle.getString("activity");
		String url;
		if(actName.equalsIgnoreCase("scheduler"))
			url ="file:///android_asset/help_schedule.htm";
		else if(actName.equalsIgnoreCase("coursemanager"))
			url ="file:///android_asset/help_coursemanager.htm";
		else if(actName.equalsIgnoreCase("maps"))
			url ="file:///android_asset/help_gps.htm";
		else if(actName.equalsIgnoreCase("contacts"))
			url="file:///android_asset/contacts.htm";
		else
			url ="file:///android_asset/helpMain.html";
			
		
        wb.loadUrl(url);
        setContentView(wb);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_help, menu);
		return true;
	}

}
