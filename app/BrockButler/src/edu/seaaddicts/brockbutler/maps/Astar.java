package edu.seaaddicts.brockbutler.maps;

/**
 * Astar.java
 * Brock Butler
 * Path finding algorithm for navigation route
 * portion of Brock Butler.
 * Created by Thomas Nelson 2013-03-05
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */

import java.util.ArrayList;
import java.util.PriorityQueue;
import android.util.Log;

public class Astar {
	
	/**
	 * Class variable for the ASTAR class. All are private variables
	 * as they are all atributited to the database helper and 
	 * are only used by this class.
	 */
    private Position[] graph;
    
    /**
     * This method returns true or false if a given position exists
	 * within the graph(school). This method is to be used before pathGeneration. 
     * @param node
     * @return
     */
    public boolean nodeExist(Position node) {
    	for(int j=0; j<graph.length; j++) {
			if(node.compare(graph[j]))
				return true;
		}
    	return false;
    }
    
    /**
     * This method returns a position if it exists within
	 * the graph(school). Used by the interface searching 
	 * feature.
     * @param nodeName
     * @return
     */
    public Position findPosition(String nodeName) {
    	for(int j=0; j<graph.length; j++) {
			if(nodeName.equals(graph[j].nodeNumber))
				return graph[j];
		}
    	return null;
    }
    
    
    /**
     * The pathGeneration method is the main part of the A* algorithm. This method
	 * achieves an efficient and route between two positions based on a heuristic 
	 * score. 
     * @param startNode
     * @param goalNode
     * @return
     */
    public Position[] pathGeneration(Position startNode, Position goalNode)  {
    	for(int j=0; j<graph.length; j++) {
			if(startNode.compare(graph[j]))
				startNode = graph[j];
			if(goalNode.compare(graph[j]))
				goalNode = graph[j];
		}
    	
    	if(startNode.accesible.length == 0 || goalNode.accesible.length == 0)
    		return null;
    	
        PriorityQueue<Position> openList = new PriorityQueue<Position>();
        openList.add(startNode);
        
        while(openList.size() != 0)  {
        	
        	Position tempNode = openList.remove();
            
            if(tempNode == goalNode) 
            	return pathReturn(tempNode);
            
            tempNode.visited = true;
            tempNode.gScore  = 0;
            
            double tempX = tempNode.xPosition - goalNode.xPosition;
            double tempY = tempNode.yPosition - goalNode.yPosition;
            tempNode.hScore = Math.sqrt(tempX*tempX + tempY*tempY);

            for(int i=0; i<tempNode.accesible.length; i++) {
            	Position adjacentNode = tempNode.accesible[i];
            	
                if(adjacentNode.visited) 
                	continue;
                
                double tempG = tempNode.gScore;
                double x = tempNode.xPosition - adjacentNode.xPosition;
                double y = tempNode.yPosition - adjacentNode.yPosition;
                double dist = Math.sqrt(x*x + y*y);
                tempG += dist;
               
                if(tempG < adjacentNode.gScore ) {
                	openList.remove(adjacentNode);
                	adjacentNode.from = tempNode;
                	adjacentNode.gScore = tempG;

                	tempX = adjacentNode.xPosition - goalNode.xPosition;
                	tempY = adjacentNode.yPosition - goalNode.yPosition;


                	adjacentNode.hScore = Math.sqrt(tempX*tempX + tempY*tempY);
                	adjacentNode.fScore = adjacentNode.gScore + adjacentNode.hScore;
                	adjacentNode.visited = true;
                    openList.add(adjacentNode);
                }

            }
        }
        return null;
    }
    
    
    /**
     * This method takes the generated route from the A* algorithm
	 * and processes it into a usable ArrayList of positions to be 
	 * passed to the mapping activity for drawing a route on the
	 * map.
     * @param end
     * @return
     * @throws ClassCastException
     */
    public Position[] pathReturn(Position end) throws ClassCastException {
        ArrayList<Position> routeList = new ArrayList<Position>();
        routeList.add(end);

        while(end.from != null) {
            end = end.from;
            routeList.add(end);
        }
        
        Object tempRoute[] = (routeList.toArray());
        Position route[] = new Position[tempRoute.length];
        
        for(int i=0; i<route.length; i++) {
            route[i] = (Position)(tempRoute[route.length - i-1]);
        }
        
        return route;
    }
	
	/**
	 * Constructor method for the ASTAR class. The constructor
	 * creates the searchable graph space that represents the
	 * school hallways and classrooms.
	 */
    public Astar () {
    	
    	graph = new Position[145];
    	
    	graph[0] = new Position(1732,687,"J Block","J01");
graph[1] = new Position(1763,688,"J Block","J02");
graph[2] = new Position(1775,698,"J Block","J03");
graph[3] = new Position(1799,723,"J Block","J04");
graph[4] = new Position(1724,677,"J Block","J05");
graph[5] = new Position(1789,663,"J Block","J06");
graph[6] = new Position(1805,648,"J Block","J07");
graph[7] = new Position(1819,637,"J Block","J08");
graph[8] = new Position(1832,625,"J Block","J09");
graph[9] = new Position(1846,613,"J Block","J10");
graph[10] = new Position(1836,600,"J Block","J11");
graph[11] = new Position(1818,583,"J Block","J12");
graph[12] = new Position(1858,624,"J Block","J13");
graph[13] = new Position(1873,638,"J Block","J14");
graph[14] = new Position(1886,652,"J Block","J15");
graph[15] = new Position(1901,666,"J Block","J16");
graph[16] = new Position(1906,684,"J Block","J17");
graph[17] = new Position(1902,705,"J Block","J18");
graph[18] = new Position(1886,721,"J Block","J19");
graph[19] = new Position(1868,738,"J Block","J20");
graph[20] = new Position(1856,752,"J Block","J21");
graph[21] = new Position(1836,771,"J Block","J22");
graph[22] = new Position(1818,789,"J Block","J23");
graph[23] = new Position(1791,765,"J301","J301");
graph[24] = new Position(1831,747,"J304","J304");
graph[25] = new Position(1819,812,"J305","J305");
graph[26] = new Position(1852,780,"J306","J306");
graph[27] = new Position(1869,766,"J307","J307");
graph[28] = new Position(1845,714,"J310","J310");
graph[29] = new Position(1890,748,"J311","J311");
graph[30] = new Position(1904,733,"J312","J312");
graph[31] = new Position(1921,717,"J313","J313");
graph[32] = new Position(1939,684,"J314","J314");
graph[33] = new Position(1888,684,"J315","J315");
graph[34] = new Position(1923,656,"J318","J318");
graph[35] = new Position(1904,640,"J319","J319");
graph[36] = new Position(1865,664,"J320","J320");
graph[37] = new Position(1886,622,"J321","J321");
graph[38] = new Position(1870,606,"J322","J322");
graph[39] = new Position(1854,588,"J323","J323");
graph[40] = new Position(1808,567,"J324","J324");
graph[41] = new Position(1799,604,"J327","J327");
graph[42] = new Position(1841,651,"J328","J328");
graph[43] = new Position(1779,632,"J330","J330");
graph[44] = new Position(1811,674,"J331","J331");
graph[45] = new Position(1796,690,"J332","J332");
graph[46] = new Position(1762,650,"J333","J333");
graph[47] = new Position(1323,879,"D Block","D01");
graph[48] = new Position(1348,878,"D Block","D02");
graph[49] = new Position(1371,877,"D Block","D03");
graph[50] = new Position(1390,877,"D Block","D04");
graph[51] = new Position(1417,876,"D Block","D05");
graph[52] = new Position(1437,876,"D Block","D06");
graph[53] = new Position(1459,876,"D Block","D07");
graph[54] = new Position(1480,877,"D Block","D08");
graph[55] = new Position(1505,878,"D Block","D09");
graph[56] = new Position(1523,878,"D Block","D10");
graph[57] = new Position(1551,871,"D Block","D11");
graph[58] = new Position(1563,857,"D Block","D12");
graph[59] = new Position(1580,838,"D Block","D13");
graph[60] = new Position(1591,824,"D Block","D14");
graph[61] = new Position(1606,812,"D Block","D15");
graph[62] = new Position(1623,790,"D Block","D16");
graph[63] = new Position(1639,782,"D Block","D17");
graph[64] = new Position(1656,764,"D Block","D18");
graph[65] = new Position(1668,750,"D Block","D19");
graph[66] = new Position(1679,729,"D Block","D20");
graph[67] = new Position(1700,708,"D Block","D21");
graph[68] = new Position(1670,777,"D Block","D22");
graph[69] = new Position(1688,795,"D Block","D23");
graph[70] = new Position(1710,810,"D Block","D24");
graph[71] = new Position(1732,839,"D Block","D25");
graph[72] = new Position(1747,854,"D Block","D26");
graph[73] = new Position(1767,875,"D Block","D27");
graph[74] = new Position(1780,887,"D Block","D28");
graph[75] = new Position(1863,909,"D Block","D29");
graph[76] = new Position(1821,929,"D Block","D30");
graph[77] = new Position(1805,943,"D Block","D31");
graph[78] = new Position(1784,965,"D Block","D32");
graph[79] = new Position(1768,980,"D Block","D33");
graph[80] = new Position(1746,1003,"D Block","D34");
graph[81] = new Position(1728,1020,"D Block","D35");
graph[82] = new Position(1708,1042,"D Block","D36");
graph[83] = new Position(1693,1052,"D Block","D37");
graph[84] = new Position(1672,1037,"D Block","D38");
graph[85] = new Position(1657,1022,"D Block","D39");
graph[86] = new Position(1638,1002,"D Block","D40");
graph[87] = new Position(1615,978,"D Block","D41");
graph[88] = new Position(1595,960,"D Block","D42");
graph[89] = new Position(1585,949,"D Block","D43");
graph[90] = new Position(1565,929,"D Block","D44");
graph[91] = new Position(1541,905,"D Block","D45");
graph[92] = new Position(1714,691,"D Block","D46");
graph[93] = new Position(1369,843,"D300","D300");
graph[94] = new Position(1402,843,"D301","D301");
graph[95] = new Position(1438,843,"D302","D302");
graph[96] = new Position(1471,843,"D303","D303");
graph[97] = new Position(1505,843,"D304","D304");
graph[98] = new Position(1546,822,"D306","D306");
graph[99] = new Position(1563,810,"D307","D307");
graph[100] = new Position(1606,904,"D308","D308");
graph[101] = new Position(1662,849,"D309","D309");
graph[102] = new Position(1692,773,"D310","D310");
graph[103] = new Position(1743,808,"D314","D314");
graph[104] = new Position(1785,853,"D315","D315");
graph[105] = new Position(1812,873,"D316","D316");
graph[106] = new Position(1830,890,"D317","D317");
graph[107] = new Position(1835,913,"D318","D318");
graph[108] = new Position(1746,929,"D319","D319");
graph[109] = new Position(1707,965,"D320","D320");
graph[110] = new Position(1678,994,"D321","D321");
graph[111] = new Position(1579,798,"D328","D328");
graph[112] = new Position(1595,782,"D329","D329");
graph[113] = new Position(1612,764,"D330","D330");
graph[114] = new Position(1625,753,"D331","D331");
graph[115] = new Position(1644,733,"D332","D332");
graph[116] = new Position(1664,713,"D333","D333");
graph[117] = new Position(1677,682,"G Block","G01");
graph[118] = new Position(1655,664,"G Block","G02");
graph[119] = new Position(1640,644,"G Block","G03");
graph[120] = new Position(1640,614,"G Block","G04");
graph[121] = new Position(1670,594,"G Block","G05");
graph[122] = new Position(1640,588,"G Block","G06");
graph[123] = new Position(1640,555,"G Block","G07");
graph[124] = new Position(1622,515,"G Block","G08");
graph[125] = new Position(1585,495,"G Block","G09");
graph[126] = new Position(1495,492,"G Block","G10");
graph[127] = new Position(1495,469,"G Block","G11");
graph[128] = new Position(1447,469,"G Block","G12");
graph[129] = new Position(1406,506,"G Block","G13");
graph[130] = new Position(1534,469,"G Block","G14");
graph[131] = new Position(1573,469,"G Block","G15");
graph[132] = new Position(1617,469,"G Block","G16");
graph[133] = new Position(1661,469,"G Block","G17");
graph[134] = new Position(1723,469,"G Block","G18");
graph[135] = new Position(1750,469,"G Block","G19");
graph[136] = new Position(1750,430,"G Block","G20");
graph[137] = new Position(1452,444,"G300","G300");
graph[138] = new Position(1509,444,"G301","G301");
graph[139] = new Position(1607,444,"G302","G302");
graph[140] = new Position(1628,444,"G303","G303");
graph[141] = new Position(1669,444,"G304","G304");
graph[142] = new Position(1703,444,"G305","G305");
graph[143] = new Position(1734,444,"G306","G306");
graph[144] = new Position(1698,621,"G310","G310");

graph[0].accesible = new Position[2];
graph[0].accesible[0] = graph[1];
graph[0].accesible[1] = graph[92];

graph[1].accesible = new Position[3];
graph[1].accesible[0] = graph[0];
graph[1].accesible[1] = graph[2];
graph[1].accesible[2] = graph[4];

graph[2].accesible = new Position[2];
graph[2].accesible[0] = graph[1];
graph[2].accesible[1] = graph[3];

graph[3].accesible = new Position[3];
graph[3].accesible[0] = graph[2];
graph[3].accesible[1] = graph[23];
graph[3].accesible[2] = graph[28];

graph[4].accesible = new Position[4];
graph[4].accesible[0] = graph[1];
graph[4].accesible[1] = graph[5];
graph[4].accesible[2] = graph[45];
graph[4].accesible[3] = graph[46];

graph[5].accesible = new Position[4];
graph[5].accesible[0] = graph[4];
graph[5].accesible[1] = graph[6];
graph[5].accesible[2] = graph[43];
graph[5].accesible[3] = graph[44];

graph[6].accesible = new Position[4];
graph[6].accesible[0] = graph[5];
graph[6].accesible[1] = graph[7];
graph[6].accesible[2] = graph[41];
graph[6].accesible[3] = graph[42];

graph[7].accesible = new Position[2];
graph[7].accesible[0] = graph[6];
graph[7].accesible[1] = graph[8];

graph[8].accesible = new Position[2];
graph[8].accesible[0] = graph[7];
graph[8].accesible[1] = graph[9];

graph[9].accesible = new Position[5];
graph[9].accesible[0] = graph[8];
graph[9].accesible[1] = graph[10];
graph[9].accesible[2] = graph[12];
graph[9].accesible[3] = graph[38];
graph[9].accesible[4] = graph[39];

graph[10].accesible = new Position[2];
graph[10].accesible[0] = graph[9];
graph[10].accesible[1] = graph[11];

graph[11].accesible = new Position[2];
graph[11].accesible[0] = graph[10];
graph[11].accesible[1] = graph[40];

graph[12].accesible = new Position[2];
graph[12].accesible[0] = graph[9];
graph[12].accesible[1] = graph[13];

graph[13].accesible = new Position[3];
graph[13].accesible[0] = graph[12];
graph[13].accesible[1] = graph[14];
graph[13].accesible[2] = graph[37];

graph[14].accesible = new Position[4];
graph[14].accesible[0] = graph[13];
graph[14].accesible[1] = graph[15];
graph[14].accesible[2] = graph[35];
graph[14].accesible[3] = graph[36];

graph[15].accesible = new Position[3];
graph[15].accesible[0] = graph[14];
graph[15].accesible[1] = graph[16];
graph[15].accesible[2] = graph[34];

graph[16].accesible = new Position[4];
graph[16].accesible[0] = graph[15];
graph[16].accesible[1] = graph[17];
graph[16].accesible[2] = graph[32];
graph[16].accesible[3] = graph[33];

graph[17].accesible = new Position[3];
graph[17].accesible[0] = graph[16];
graph[17].accesible[1] = graph[18];
graph[17].accesible[2] = graph[31];

graph[18].accesible = new Position[3];
graph[18].accesible[0] = graph[17];
graph[18].accesible[1] = graph[19];
graph[18].accesible[2] = graph[30];

graph[19].accesible = new Position[3];
graph[19].accesible[0] = graph[18];
graph[19].accesible[1] = graph[20];
graph[19].accesible[2] = graph[29];

graph[20].accesible = new Position[4];
graph[20].accesible[0] = graph[19];
graph[20].accesible[1] = graph[21];
graph[20].accesible[2] = graph[27];
graph[20].accesible[3] = graph[28];

graph[21].accesible = new Position[3];
graph[21].accesible[0] = graph[20];
graph[21].accesible[1] = graph[22];
graph[21].accesible[2] = graph[26];

graph[22].accesible = new Position[3];
graph[22].accesible[0] = graph[21];
graph[22].accesible[1] = graph[23];
graph[22].accesible[2] = graph[25];

graph[23].accesible = new Position[3];
graph[23].accesible[0] = graph[3];
graph[23].accesible[1] = graph[22];
graph[23].accesible[2] = graph[24];

graph[24].accesible = new Position[2];
graph[24].accesible[0] = graph[23];
graph[24].accesible[1] = graph[28];

graph[25].accesible = new Position[1];
graph[25].accesible[0] = graph[22];

graph[26].accesible = new Position[1];
graph[26].accesible[0] = graph[21];

graph[27].accesible = new Position[1];
graph[27].accesible[0] = graph[20];

graph[28].accesible = new Position[3];
graph[28].accesible[0] = graph[3];
graph[28].accesible[1] = graph[20];
graph[28].accesible[2] = graph[24];

graph[29].accesible = new Position[1];
graph[29].accesible[0] = graph[19];

graph[30].accesible = new Position[1];
graph[30].accesible[0] = graph[18];

graph[31].accesible = new Position[1];
graph[31].accesible[0] = graph[17];

graph[32].accesible = new Position[1];
graph[32].accesible[0] = graph[16];

graph[33].accesible = new Position[1];
graph[33].accesible[0] = graph[16];

graph[34].accesible = new Position[1];
graph[34].accesible[0] = graph[15];

graph[35].accesible = new Position[1];
graph[35].accesible[0] = graph[14];

graph[36].accesible = new Position[1];
graph[36].accesible[0] = graph[14];

graph[37].accesible = new Position[1];
graph[37].accesible[0] = graph[13];

graph[38].accesible = new Position[1];
graph[38].accesible[0] = graph[9];

graph[39].accesible = new Position[1];
graph[39].accesible[0] = graph[9];

graph[40].accesible = new Position[1];
graph[40].accesible[0] = graph[11];

graph[41].accesible = new Position[1];
graph[41].accesible[0] = graph[6];

graph[42].accesible = new Position[1];
graph[42].accesible[0] = graph[6];

graph[43].accesible = new Position[1];
graph[43].accesible[0] = graph[5];

graph[44].accesible = new Position[1];
graph[44].accesible[0] = graph[5];

graph[45].accesible = new Position[1];
graph[45].accesible[0] = graph[4];

graph[46].accesible = new Position[1];
graph[46].accesible[0] = graph[4];

graph[47].accesible = new Position[1];
graph[47].accesible[0] = graph[48];

graph[48].accesible = new Position[2];
graph[48].accesible[0] = graph[47];
graph[48].accesible[1] = graph[49];

graph[49].accesible = new Position[3];
graph[49].accesible[0] = graph[48];
graph[49].accesible[1] = graph[50];
graph[49].accesible[2] = graph[93];

graph[50].accesible = new Position[2];
graph[50].accesible[0] = graph[49];
graph[50].accesible[1] = graph[51];

graph[51].accesible = new Position[3];
graph[51].accesible[0] = graph[50];
graph[51].accesible[1] = graph[52];
graph[51].accesible[2] = graph[94];

graph[52].accesible = new Position[3];
graph[52].accesible[0] = graph[51];
graph[52].accesible[1] = graph[53];
graph[52].accesible[2] = graph[95];

graph[53].accesible = new Position[3];
graph[53].accesible[0] = graph[52];
graph[53].accesible[1] = graph[54];
graph[53].accesible[2] = graph[96];

graph[54].accesible = new Position[2];
graph[54].accesible[0] = graph[53];
graph[54].accesible[1] = graph[55];

graph[55].accesible = new Position[3];
graph[55].accesible[0] = graph[54];
graph[55].accesible[1] = graph[56];
graph[55].accesible[2] = graph[97];

graph[56].accesible = new Position[3];
graph[56].accesible[0] = graph[55];
graph[56].accesible[1] = graph[57];
graph[56].accesible[2] = graph[91];

graph[57].accesible = new Position[3];
graph[57].accesible[0] = graph[56];
graph[57].accesible[1] = graph[58];
graph[57].accesible[2] = graph[100];

graph[58].accesible = new Position[2];
graph[58].accesible[0] = graph[57];
graph[58].accesible[1] = graph[59];

graph[59].accesible = new Position[4];
graph[59].accesible[0] = graph[58];
graph[59].accesible[1] = graph[60];
graph[59].accesible[2] = graph[98];
graph[59].accesible[3] = graph[99];

graph[60].accesible = new Position[4];
graph[60].accesible[0] = graph[59];
graph[60].accesible[1] = graph[61];
graph[60].accesible[2] = graph[101];
graph[60].accesible[3] = graph[111];

graph[61].accesible = new Position[3];
graph[61].accesible[0] = graph[60];
graph[61].accesible[1] = graph[62];
graph[61].accesible[2] = graph[112];

graph[62].accesible = new Position[3];
graph[62].accesible[0] = graph[61];
graph[62].accesible[1] = graph[63];
graph[62].accesible[2] = graph[113];

graph[63].accesible = new Position[3];
graph[63].accesible[0] = graph[62];
graph[63].accesible[1] = graph[64];
graph[63].accesible[2] = graph[114];

graph[64].accesible = new Position[4];
graph[64].accesible[0] = graph[63];
graph[64].accesible[1] = graph[65];
graph[64].accesible[2] = graph[68];
graph[64].accesible[3] = graph[115];

graph[65].accesible = new Position[3];
graph[65].accesible[0] = graph[64];
graph[65].accesible[1] = graph[66];
graph[65].accesible[2] = graph[116];

graph[66].accesible = new Position[2];
graph[66].accesible[0] = graph[65];
graph[66].accesible[1] = graph[67];

graph[67].accesible = new Position[3];
graph[67].accesible[0] = graph[66];
graph[67].accesible[1] = graph[92];
graph[67].accesible[2] = graph[117];

graph[68].accesible = new Position[2];
graph[68].accesible[0] = graph[64];
graph[68].accesible[1] = graph[69];

graph[69].accesible = new Position[3];
graph[69].accesible[0] = graph[68];
graph[69].accesible[1] = graph[70];
graph[69].accesible[2] = graph[102];

graph[70].accesible = new Position[3];
graph[70].accesible[0] = graph[69];
graph[70].accesible[1] = graph[71];
graph[70].accesible[2] = graph[103];

graph[71].accesible = new Position[2];
graph[71].accesible[0] = graph[70];
graph[71].accesible[1] = graph[72];

graph[72].accesible = new Position[2];
graph[72].accesible[0] = graph[71];
graph[72].accesible[1] = graph[73];

graph[73].accesible = new Position[3];
graph[73].accesible[0] = graph[72];
graph[73].accesible[1] = graph[74];
graph[73].accesible[2] = graph[104];

graph[74].accesible = new Position[3];
graph[74].accesible[0] = graph[73];
graph[74].accesible[1] = graph[75];
graph[74].accesible[2] = graph[105];

graph[75].accesible = new Position[3];
graph[75].accesible[0] = graph[74];
graph[75].accesible[1] = graph[76];
graph[75].accesible[2] = graph[106];

graph[76].accesible = new Position[3];
graph[76].accesible[0] = graph[75];
graph[76].accesible[1] = graph[77];
graph[76].accesible[2] = graph[107];

graph[77].accesible = new Position[3];
graph[77].accesible[0] = graph[76];
graph[77].accesible[1] = graph[78];
graph[77].accesible[2] = graph[108];

graph[78].accesible = new Position[2];
graph[78].accesible[0] = graph[77];
graph[78].accesible[1] = graph[79];

graph[79].accesible = new Position[3];
graph[79].accesible[0] = graph[78];
graph[79].accesible[1] = graph[80];
graph[79].accesible[2] = graph[108];

graph[80].accesible = new Position[3];
graph[80].accesible[0] = graph[79];
graph[80].accesible[1] = graph[81];
graph[80].accesible[2] = graph[109];

graph[81].accesible = new Position[2];
graph[81].accesible[0] = graph[80];
graph[81].accesible[1] = graph[82];

graph[82].accesible = new Position[2];
graph[82].accesible[0] = graph[81];
graph[82].accesible[1] = graph[83];

graph[83].accesible = new Position[2];
graph[83].accesible[0] = graph[82];
graph[83].accesible[1] = graph[84];

graph[84].accesible = new Position[3];
graph[84].accesible[0] = graph[83];
graph[84].accesible[1] = graph[85];
graph[84].accesible[2] = graph[110];

graph[85].accesible = new Position[2];
graph[85].accesible[0] = graph[84];
graph[85].accesible[1] = graph[86];

graph[86].accesible = new Position[3];
graph[86].accesible[0] = graph[85];
graph[86].accesible[1] = graph[87];
graph[86].accesible[2] = graph[110];

graph[87].accesible = new Position[2];
graph[87].accesible[0] = graph[86];
graph[87].accesible[1] = graph[88];

graph[88].accesible = new Position[3];
graph[88].accesible[0] = graph[87];
graph[88].accesible[1] = graph[89];
graph[88].accesible[2] = graph[100];

graph[89].accesible = new Position[2];
graph[89].accesible[0] = graph[88];
graph[89].accesible[1] = graph[90];

graph[90].accesible = new Position[2];
graph[90].accesible[0] = graph[89];
graph[90].accesible[1] = graph[91];

graph[91].accesible = new Position[2];
graph[91].accesible[0] = graph[56];
graph[91].accesible[1] = graph[90];

graph[92].accesible = new Position[3];
graph[92].accesible[0] = graph[0];
graph[92].accesible[1] = graph[67];
graph[92].accesible[2] = graph[144];

graph[93].accesible = new Position[1];
graph[93].accesible[0] = graph[49];

graph[94].accesible = new Position[1];
graph[94].accesible[0] = graph[51];

graph[95].accesible = new Position[1];
graph[95].accesible[0] = graph[52];

graph[96].accesible = new Position[1];
graph[96].accesible[0] = graph[53];

graph[97].accesible = new Position[1];
graph[97].accesible[0] = graph[55];

graph[98].accesible = new Position[1];
graph[98].accesible[0] = graph[59];

graph[99].accesible = new Position[1];
graph[99].accesible[0] = graph[59];

graph[100].accesible = new Position[2];
graph[100].accesible[0] = graph[57];
graph[100].accesible[1] = graph[88];

graph[101].accesible = new Position[2];
graph[101].accesible[0] = graph[60];
graph[101].accesible[1] = graph[63];

graph[102].accesible = new Position[1];
graph[102].accesible[0] = graph[69];

graph[103].accesible = new Position[1];
graph[103].accesible[0] = graph[70];

graph[104].accesible = new Position[1];
graph[104].accesible[0] = graph[73];

graph[105].accesible = new Position[1];
graph[105].accesible[0] = graph[74];

graph[106].accesible = new Position[1];
graph[106].accesible[0] = graph[75];

graph[107].accesible = new Position[1];
graph[107].accesible[0] = graph[76];

graph[108].accesible = new Position[2];
graph[108].accesible[0] = graph[77];
graph[108].accesible[1] = graph[79];

graph[109].accesible = new Position[1];
graph[109].accesible[0] = graph[80];

graph[110].accesible = new Position[2];
graph[110].accesible[0] = graph[84];
graph[110].accesible[1] = graph[86];

graph[111].accesible = new Position[1];
graph[111].accesible[0] = graph[60];

graph[112].accesible = new Position[1];
graph[112].accesible[0] = graph[61];

graph[113].accesible = new Position[1];
graph[113].accesible[0] = graph[62];

graph[114].accesible = new Position[1];
graph[114].accesible[0] = graph[63];

graph[115].accesible = new Position[2];
graph[115].accesible[0] = graph[64];
graph[115].accesible[1] = graph[116];

graph[116].accesible = new Position[2];
graph[116].accesible[0] = graph[65];
graph[116].accesible[1] = graph[115];

graph[117].accesible = new Position[2];
graph[117].accesible[0] = graph[118];
graph[117].accesible[1] = graph[67];

graph[118].accesible = new Position[2];
graph[118].accesible[0] = graph[117];
graph[118].accesible[1] = graph[119];

graph[119].accesible = new Position[2];
graph[119].accesible[0] = graph[118];
graph[119].accesible[1] = graph[120];

graph[120].accesible = new Position[3];
graph[120].accesible[0] = graph[119];
graph[120].accesible[1] = graph[121];
graph[120].accesible[2] = graph[122];

graph[121].accesible = new Position[3];
graph[121].accesible[0] = graph[120];
graph[121].accesible[1] = graph[122];
graph[121].accesible[2] = graph[144];

graph[122].accesible = new Position[3];
graph[122].accesible[0] = graph[120];
graph[122].accesible[1] = graph[121];
graph[122].accesible[2] = graph[123];

graph[123].accesible = new Position[2];
graph[123].accesible[0] = graph[122];
graph[123].accesible[1] = graph[124];

graph[124].accesible = new Position[2];
graph[124].accesible[0] = graph[123];
graph[124].accesible[1] = graph[125];

graph[125].accesible = new Position[2];
graph[125].accesible[0] = graph[124];
graph[125].accesible[1] = graph[126];

graph[126].accesible = new Position[2];
graph[126].accesible[0] = graph[125];
graph[126].accesible[1] = graph[127];

graph[127].accesible = new Position[3];
graph[127].accesible[0] = graph[126];
graph[127].accesible[1] = graph[128];
graph[127].accesible[2] = graph[130];

graph[128].accesible = new Position[3];
graph[128].accesible[0] = graph[127];
graph[128].accesible[1] = graph[129];
graph[128].accesible[2] = graph[137];

graph[129].accesible = new Position[1];
graph[129].accesible[0] = graph[128];

graph[130].accesible = new Position[3];
graph[130].accesible[0] = graph[127];
graph[130].accesible[1] = graph[131];
graph[130].accesible[2] = graph[138];

graph[131].accesible = new Position[2];
graph[131].accesible[0] = graph[130];
graph[131].accesible[1] = graph[132];

graph[132].accesible = new Position[4];
graph[132].accesible[0] = graph[131];
graph[132].accesible[1] = graph[133];
graph[132].accesible[2] = graph[139];
graph[132].accesible[3] = graph[140];

graph[133].accesible = new Position[3];
graph[133].accesible[0] = graph[132];
graph[133].accesible[1] = graph[134];
graph[133].accesible[2] = graph[141];

graph[134].accesible = new Position[4];
graph[134].accesible[0] = graph[133];
graph[134].accesible[1] = graph[135];
graph[134].accesible[2] = graph[142];
graph[134].accesible[3] = graph[143];

graph[135].accesible = new Position[2];
graph[135].accesible[0] = graph[134];
graph[135].accesible[1] = graph[136];

graph[136].accesible = new Position[1];
graph[136].accesible[0] = graph[135];

graph[137].accesible = new Position[2];
graph[137].accesible[0] = graph[128];
graph[137].accesible[1] = graph[138];

graph[138].accesible = new Position[2];
graph[138].accesible[0] = graph[130];
graph[138].accesible[1] = graph[137];

graph[139].accesible = new Position[1];
graph[139].accesible[0] = graph[132];

graph[140].accesible = new Position[1];
graph[140].accesible[0] = graph[132];

graph[141].accesible = new Position[1];
graph[141].accesible[0] = graph[133];

graph[142].accesible = new Position[1];
graph[142].accesible[0] = graph[134];

graph[143].accesible = new Position[1];
graph[143].accesible[0] = graph[134];

graph[144].accesible = new Position[2];
graph[144].accesible[0] = graph[121];
graph[144].accesible[1] = graph[92];
			
    	Log.i("ASTAR", "Graph Built of size " + graph.length);
    }
}
