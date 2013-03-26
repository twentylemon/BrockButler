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
		
		Position start = new Position(1693,1052,"D Block","D37");
		//"('D37', 'D Block', 1693, 1052, 'D38')," +
		//"('J23', 'J Block', 1818, 789, 'J22')," +
		Position goal = new Position(1818,789,"J Block","J23");
	    
	    Astar a = new Astar(this);
	    
	    if(a.nodeExist(start) && a.nodeExist(goal)) {
		    Position[] route = a.pathGeneration(start, goal);
		    
		    if(route != null)
		    	for(int i=0; i<route.length; i++)
			    	Log.d("PRINT", route[i].nodeNumber);
		    else
		    	Log.e("PRINT", "Ahhh the Motherland");   
	    } else {
	    	Log.e("PRINT", "positions apperantly dont exist"); 
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
