package com.example.mappingmodule;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class TestHarness extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_harness);
	    
	    Locate l = new Locate(this);
	    Log.i("PRINT",l.getUserPosition());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_test_harness, menu);
        return true;
    }
    
}
