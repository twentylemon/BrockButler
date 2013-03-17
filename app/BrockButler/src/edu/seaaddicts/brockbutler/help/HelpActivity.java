package edu.seaaddicts.brockbutler.help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import edu.seaaddicts.brockbutler.R;
import android.webkit.WebView;
public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_help);
		WebView wb = new WebView(this);
		Bundle 	bundle = this.getIntent().getExtras();
		String actName = bundle.getString("activity");
		String url;
		if(actName.equalsIgnoreCase("scheduler"))
			url ="file:///android_asset/help_schedule.htm";
		else if(actName.equalsIgnoreCase("coursemanager"))
			url ="file:///android_asset/help_coursemanager.htm";
		else if(actName.equalsIgnoreCase("maps"))
			url ="file:///android_asset/help_maps.htm";
		else
			url ="file:///android_asset/helpMain.htm";
			
		
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
