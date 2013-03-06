package com.example.threadhandletest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView text1;
	Button button1;
	private Mapping myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        text1 = (TextView)findViewById(R.id.textView1);
        addListenerOnButton();
        
        myThread = new Mapping(mainHandler);
        myThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void addListenerOnButton() {
		button1 = (Button) findViewById(R.id.button1);
 
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Message messageToThread = new Message();
				Bundle messageData = new Bundle();
				messageToThread.what = 0;
				messageData.putFloat("exit", 1);
				messageToThread.setData(messageData);
 
				// sending message to MyThread
				myThread.getHandler().sendMessage(messageToThread);
			}
		});
 
	}
    
 // manages messages for current Thread (main)
    // received from our Thread
    public Handler mainHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
            	text1.append(msg.getData().getString("text") + "\n");
            }
        };
    };
    
}
