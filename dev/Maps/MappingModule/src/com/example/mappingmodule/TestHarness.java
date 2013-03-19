package com.example.mappingmodule;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class TestHarness extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_harness);
        
        Context contextNew = this;
		Astar a = new Astar(contextNew);
		
		Position start = new Position(1732,687,"J Block","J01");
		Position goal = new Position(1858,624,"J Block","J13");
		
		Position[] route = a.pathGeneration(start, goal);
		
		for(int i=0; i<route.length; i++) {
			Log.i("MAIN CLASS", "Node: " + route[i].nodeNumber);
		}
		
		a.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_test_harness, menu);
        return true;
    }
    
}
