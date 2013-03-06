package com.example.threadhandletest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Mapping extends Thread {
 
    // instance vars for managing text move
    private String myText;
    private int myTextNum;
    private Handler parentHandler;
	private boolean killMe = true;

    private Handler myThreadHandler = new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == 0){
            	if((int)msg.getData().getInt("exit") == 1) {
            		killMe = false;
            	}
            }
        }
    };
 
    //constructor
    public Mapping(Handler handler){
        this.parentHandler = handler;
        myText = new String("Number of times this thread has looped: ");
        myTextNum = 0;
    }
 
    @Override
    public void run() {
        super.run();
        try {
            // Thread loop
            while(killMe){
                // prepare a message with the updated text
                Message messageToParent = new Message();
                Bundle messageData = new Bundle();
                messageToParent.what = 0;
                messageData.putString("text", updateText());
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
        return myThreadHandler;
    }
 
    // updates the text based on the currentPosition
    private String updateText(){
    	myTextNum++;
        return myText + myTextNum;
    }
}
