package edu.seaaddicts.brockbutler.maps;

import android.app.Activity;
import android.os.Bundle;
import edu.brocku.seaaddicts.brockbutler.R;

public class MapActivity extends Activity {
	
	final static int POSITION_UPDATE		= 0;
	final static int WIFI_DOWN 				= 1;
	final static int DESTINATION_ARRIVAL 	= 2;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
	}

}
