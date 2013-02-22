package com.example.gatherinfo;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import android.view.Menu;

public class GatherInfo extends Activity {
	
	private WifiManager      wifiMgr;
	private List<ScanResult> scanResults;
	
	TextView text1;
	Button button1;
	EditText editText1;
	
	private int[] sigStr  = new int[10];
	private int[] freq    = new int[10];
	private String[] address = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gather_info);
        
        wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        text1 = (TextView)findViewById(R.id.textView1);
        editText1   = (EditText)findViewById(R.id.editText1);
        
        addListenerOnButton();
    }
    
    @Override
    public void onStart() {
    	text1.setText("Application has started.\n\nPress the button to begin!");
       super.onStart();
    }
    
    @Override 
    public void onStop() {
    	text1.setText("Activity has stopped");
       super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_gather_info, menu);
        return true;
    }
    
    public void runItBitch() throws IOException {
    	text1.setText("Starting new scan for node: " + editText1.getText().toString() + "...\n");
    	File sdCardFile = new File(Environment.getExternalStorageDirectory() + "/wapInfo.txt");
    	text1.append("Data will be written to wapInfo.csv located in  " + sdCardFile.getPath() + "...\n\n");
    	wifiMgr = (WifiManager)getSystemService(Context.WIFI_SERVICE);
    	int x = 0;
    	
    	for(int num=0; num<10; num++) {
    		wifiMgr.startScan();
    		scanResults = wifiMgr.getScanResults();
    		
    		text1.append("Scanned routers for " + editText1.getText().toString() + "for the " + num + " time!\n");
    		
    		x = 0;
    		sigStr  = new int[10];
    		freq    = new int[10];
    		address = new String[10];
	   		 
    		for(ScanResult scanRes : scanResults) {
    			if(x < 10) {
    				address[x] = scanRes.BSSID;
    				sigStr[x] = scanRes.level;
    				freq[x] = scanRes.frequency;
    				x++;
    			}
    		}
	    
    		FileWriter fWriter;
    		try{
    			fWriter = new FileWriter(sdCardFile,true);
    			for(int i=0; i<10; i++) {
    				fWriter.append(editText1.getText().toString() + "," + address[i] + "," + sigStr[i] + "," + freq[i] + "\n");
    			}
    			fWriter.flush();
    			fWriter.close();
    		}catch(Exception e){
    			text1.append("Failed to write to csv file!");
    		}
    	}
    }
    
    public void addListenerOnButton() {
		button1 = (Button) findViewById(R.id.button1);
 
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				try {
					runItBitch();
				} catch (IOException e) {
					text1.setText("Failed to run the capture node sequence!");
				}
			}
		});
 
	}
    
}
