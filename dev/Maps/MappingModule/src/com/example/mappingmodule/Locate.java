package com.example.mappingmodule;

/**
 * Locate.java
 * Brock Butler
 * Database helper class to ease database interaction 
 * portion of Brock Butler.
 * Created by Thomas Nelson 2013-03-05
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */


public class Locate {
	
	private static final int inputs = 2;
	private static final int hidden = 8;
	private static final int output = 5;
	
	//weights
	private static double[][] W = new double[inputs][hidden];
	private static double[][] V = new double[hidden][output];
	private static double[] HB = new double[hidden];
	private static double[] OB = new double[output];
	//neuron outputs
	private static double[] hiddenVal = new double[hidden];
	private static double[] outputVal = new double[output];
	//neuron inputs
	private static double[] inputVal = new double[inputs];

	private static double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	private static void calcNetwork() {
		for(int h=0; h<hidden; h++) {
			hiddenVal[h] = -HB[h];
			for(int i=0; i<inputs; i++) {
				hiddenVal[h] += (inputVal[i] * W[i][h]);
			}
			hiddenVal[h] = sigmoid(hiddenVal[h]);
		}
		
		for(int o=0; o<output; o++) {
			outputVal[o] = -OB[o];
			for(int h=0; h<hidden; h++) {
				outputVal[o] += (hiddenVal[h] * V[h][o]);
			}
			outputVal[o] = sigmoid(outputVal[o]);
			
			//if(outputVal[o] >= 0.9)
			//	outputVal[o] = 1;
		    //else if(outputVal[o] <= 0.1)
		    //	outputVal[o] = 0;
			
			//outputError[o] = outTrain[pat][o] - outputVal[o];
		}
	}
	
	public void initWeights ( ) {
		W[0][0] = 0;
		W[0][1] = 0;
		W[0][2] = 0;
		W[0][3] = 0;
		W[0][4] = 0;
		W[0][5] = 0;
		W[0][6] = 0;
		W[0][7] = 0;
		
		W[1][0] = 0;
		W[1][1] = 0;
		W[1][2] = 0;
		W[1][3] = 0;
		W[1][4] = 0;
		W[1][5] = 0;
		W[1][6] = 0;
		W[1][7] = 0;
		
		V[0][0] = 0;
		V[0][1] = 0;
		V[0][2] = 0;
		V[0][3] = 0;
		V[0][4] = 0;
		
		V[1][0] = 0;
		V[1][1] = 0;
		V[1][2] = 0;
		V[1][3] = 0;
		V[1][4] = 0;
		
		V[2][0] = 0;
		V[2][1] = 0;
		V[2][2] = 0;
		V[2][3] = 0;
		V[2][4] = 0;
		
		V[3][0] = 0;
		V[3][1] = 0;
		V[3][2] = 0;
		V[3][3] = 0;
		V[3][4] = 0;
		
		V[4][0] = 0;
		V[4][1] = 0;
		V[4][2] = 0;
		V[4][3] = 0;
		V[4][4] = 0;
		
		V[5][0] = 0;
		V[5][1] = 0;
		V[5][2] = 0;
		V[5][3] = 0;
		V[5][4] = 0;
		
		V[6][0] = 0;
		V[6][1] = 0;
		V[6][2] = 0;
		V[6][3] = 0;
		V[6][4] = 0;
		
		V[7][0] = 0;
		V[7][1] = 0;
		V[7][2] = 0;
		V[7][3] = 0;
		V[7][4] = 0;
		
		HB[0] = 0;
		HB[1] = 0;
		HB[2] = 0;
		HB[3] = 0;
		HB[4] = 0;
		HB[5] = 0;
		HB[6] = 0;
		HB[7] = 0;
		
		OB[0] = 0;
		OB[1] = 0;
		OB[2] = 0;
		OB[3] = 0;
		OB[4] = 0;
	}
	
	public void initData() {
		
	}

	public Locate () {
		initWeights();
		initData();
		calcNetwork();
		
		int answer = (int) (outputVal[0]*16 + outputVal[1]*8 + outputVal[2]*4 + outputVal[3]*2 + outputVal[4]*1);
	}


}
