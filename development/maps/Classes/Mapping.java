package edu.seaaddicts.brockbutler.map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Mapping extends Thread{
	
	/**
	 * Class variable for the MAPPING class
	 */ 
	private Position   userLocation;
	private Position   destinationLocation;
	private Position   previousLocation;
	private Position[] navigationRoute;
	private Handler    parentHandler;
	private boolean    threadLife;
	
	/**
	 * Custom handler to handle messages from the parent
	 * activity. This is how the parent will request a route,
	 * kill the thread, or a manual position update.
	 */ 
	private Handler mappingHandler = new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == 0){
            	if((int)msg.getData().getInt("exit") == 1) {
            		threadLife = false;
            	}
            }
        }
    };
    
    /**
	 * Constructor method for the MAPPING class. The constructor
	 * sets up the link between the parent and this thread to send
	 * messages.
	 */ 
    public Mapping (Handler handler) {
    	this.parentHandler = handler;
    }
    
    @Override
    public void run() {
        super.run();
        try {
            // Thread loop
            while(threadLife){
                // prepare a message with the updated text
                Message messageToParent = new Message();
                Bundle messageData = new Bundle();
                messageToParent.what = 0;
                messageData.putString("text", "yea poop");
                messageToParent.setData(messageData);
 
                // send message to mainThread
                parentHandler.sendMessage(messageToParent);
 
                sleep(2000);
            }
        }
        catch (Exception e) {
            // Logging exception
            Log.e("TestingAreaLOG","Main loop exception - " + e);
        }
    }
 
    // getter for local Handler
    public Handler getHandler() {
        return mappingHandler;
    }
 

}
