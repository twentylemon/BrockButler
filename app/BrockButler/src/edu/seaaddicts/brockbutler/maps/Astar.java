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
			if(nodeName.equalsIgnoreCase(graph[j].nodeNumber))
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
    		//graph[j].visited = false;
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
    	
    	graph = new Position[338];
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
    	graph[145] = new Position(850,883,"C Block","C01");
    	graph[146] = new Position(870,883,"C Block","C02");
    	graph[147] = new Position(909,883,"C Block","C03");
    	graph[148] = new Position(927,883,"C Block","C04");
    	graph[149] = new Position(949,883,"C Block","C05");
    	graph[150] = new Position(990,883,"C Block","C06");
    	graph[151] = new Position(1020,883,"C Block","C07");
    	graph[152] = new Position(1049,878,"C Block","C08");
    	graph[153] = new Position(1065,849,"C Block","C09");
    	graph[154] = new Position(1084,831,"C Block","C10");
    	graph[155] = new Position(1110,804,"C Block","C11");
    	graph[156] = new Position(1137,777,"C Block","C12");
    	graph[157] = new Position(1165,749,"C Block","C13");
    	graph[158] = new Position(1186,727,"C Block","C14");
    	graph[159] = new Position(1069,891,"C Block","C15");
    	graph[160] = new Position(1088,907,"C Block","C16");
    	graph[161] = new Position(1103,913,"C Block","C17");
    	graph[162] = new Position(1124,911,"C Block","C18");
    	graph[163] = new Position(1149,914,"C Block","C19");
    	graph[164] = new Position(1175,911,"C Block","C20");
    	graph[165] = new Position(1127,912,"C Block","C21");
    	graph[166] = new Position(1254,915,"C Block","C22");
    	graph[167] = new Position(1280,908,"C Block","C23");
    	graph[168] = new Position(1295,891,"C Block","C24");
    	graph[169] = new Position(1318,878,"C Block","C25");
    	graph[170] = new Position(1255,934,"C Block","C26");
    	graph[171] = new Position(1247,951,"C Block","C27");
    	graph[172] = new Position(1235,966,"C Block","C28");
    	graph[173] = new Position(1220,984,"C Block","C29");
    	graph[174] = new Position(1183,994,"C Block","C30");
    	graph[175] = new Position(1143,975,"C Block","C31");
    	graph[176] = new Position(1124,954,"C Block","C32");
    	graph[177] = new Position(1103,936,"C Block","C33");
    	graph[178] = new Position(858,840,"C300","C300");
    	graph[179] = new Position(894,840,"C301","C301");
    	graph[180] = new Position(929,850,"C302","C302");
    	graph[181] = new Position(965,840,"C303","C303");
    	graph[182] = new Position(995,840,"C304","C304");
    	graph[183] = new Position(1049,843,"C305","C305");
    	graph[184] = new Position(1162,873,"C306","C306");
    	graph[185] = new Position(1144,813,"C307","C307");
    	graph[186] = new Position(1208,782,"C308","C308");
    	graph[187] = new Position(1267,847,"C310","C310");
    	graph[188] = new Position(1297,936,"C312","C312");
    	graph[189] = new Position(1285,946,"C313","C313");
    	graph[190] = new Position(1271,958,"C314","C314");
    	graph[191] = new Position(1255,971,"C315","C315");
    	graph[192] = new Position(1191,960,"C316","C316");
    	graph[193] = new Position(1243,987,"C317","C317");
    	graph[194] = new Position(1231,998,"C318","C318");
    	graph[195] = new Position(1218,1015,"C319","C319");
    	graph[196] = new Position(1202,1026,"C320","C320");
    	graph[197] = new Position(1183,1042,"C321","C321");
    	graph[198] = new Position(1160,1023,"C322","C322");
    	graph[199] = new Position(1136,999,"C324","C324");
    	graph[200] = new Position(1124,987,"C325","C325");
    	graph[201] = new Position(1110,973,"C326","C326");
    	graph[202] = new Position(1097,961,"C327","C327");
    	graph[203] = new Position(1083,947,"C328","C328");
    	graph[204] = new Position(1069,935,"C329","C329");
    	graph[205] = new Position(1057,922,"C330","C330");
    	graph[206] = new Position(1044,908,"C331","C331");
    	graph[207] = new Position(509,875,"B Block","B01");
    	graph[208] = new Position(534,879,"B Block","B02");
    	graph[209] = new Position(557,886,"B Block","B03");
    	graph[210] = new Position(570,902,"B Block","B04");
    	graph[211] = new Position(578,917,"B Block","B05");
    	graph[212] = new Position(592,932,"B Block","B06");
    	graph[213] = new Position(607,947,"B Block","B07");
    	graph[214] = new Position(621,960,"B Block","B08");
    	graph[215] = new Position(633,973,"B Block","B09");
    	graph[216] = new Position(646,985,"B Block","B10");
    	graph[217] = new Position(674,994,"B Block","B11");
    	graph[218] = new Position(705,784,"B Block","B12");
    	graph[219] = new Position(720,969,"B Block","B13");
    	graph[220] = new Position(732,956,"B Block","B14");
    	graph[221] = new Position(746,942,"B Block","B15");
    	graph[222] = new Position(759,928,"B Block","B16");
    	graph[223] = new Position(772,917,"B Block","B17");
    	graph[224] = new Position(783,898,"B Block","B18");
    	graph[225] = new Position(797,883,"B Block","B19");
    	graph[226] = new Position(827,876,"B Block","B20");
    	graph[227] = new Position(760,888,"B Block","B21");
    	graph[228] = new Position(746,872,"B Block","B22");
    	graph[229] = new Position(732,858,"B Block","B23");
    	graph[230] = new Position(717,844,"B Block","B24");
    	graph[231] = new Position(704,832,"B Block","B25");
    	graph[232] = new Position(689,816,"B Block","B26");
    	graph[233] = new Position(672,801,"B Block","B27");
    	graph[234] = new Position(654,781,"B Block","B28");
    	graph[235] = new Position(674,764,"B Block","B29");
    	graph[236] = new Position(638,800,"B Block","B30");
    	graph[237] = new Position(617,821,"B Block","B31");
    	graph[238] = new Position(605,833,"B Block","B32");
    	graph[239] = new Position(584,849,"B Block","B33");
    	graph[240] = new Position(569,867,"B Block","B34");
    	graph[241] = new Position(590,905,"B Block","B35");
    	graph[242] = new Position(627,907,"B Block","B36");
    	graph[243] = new Position(648,910,"B Block","B37");
    	graph[244] = new Position(674,911,"B Block","B38");
    	graph[245] = new Position(699,910,"B Block","B39");
    	graph[246] = new Position(718,904,"B Block","B40");
    	graph[247] = new Position(753,904,"B Block","B41");
    	graph[248] = new Position(585,883,"B300","B300");
    	graph[249] = new Position(598,874,"B301","B301");
    	graph[250] = new Position(610,865,"B302","B302");
    	graph[251] = new Position(620,854,"B303","B303");
    	graph[252] = new Position(650,816,"B304","B304");
    	graph[253] = new Position(693,791,"B305","B305");
    	graph[254] = new Position(705,802,"B306","B306");
    	graph[255] = new Position(721,818,"B307","B307");
    	graph[256] = new Position(734,833,"B308","B308");
    	graph[257] = new Position(693,853,"B309","B309");
    	graph[258] = new Position(745,846,"B310","B310");
    	graph[259] = new Position(766,850,"B311","B311");
    	graph[260] = new Position(735,887,"B312","B312");
    	graph[261] = new Position(772,872,"B313","B313");
    	graph[262] = new Position(810,905,"B314","B314");
    	graph[263] = new Position(798,920,"B315","B315");
    	graph[264] = new Position(785,934,"B316","B316");
    	graph[265] = new Position(773,947,"B317","B317");
    	graph[266] = new Position(743,923,"B318","B318");
    	graph[267] = new Position(755,950,"B319","B319");
    	graph[268] = new Position(747,972,"B320","B320");
    	graph[269] = new Position(731,986,"B321","B321");
    	graph[270] = new Position(647,964,"B322","B322");
    	graph[271] = new Position(720,1003,"B323","B323");
    	graph[272] = new Position(708,1012,"B324","B324");
    	graph[273] = new Position(693,1026,"B325","B325");
    	graph[274] = new Position(674,1044,"B326","B326");
    	graph[275] = new Position(656,1024,"B327","B327");
    	graph[276] = new Position(641,1012,"B328","B328");
    	graph[277] = new Position(631,996,"B329","B329");
    	graph[278] = new Position(617,985,"B330","B330");
    	graph[279] = new Position(605,973,"B331","B331");
    	graph[280] = new Position(590,958,"B332","B332");
    	graph[281] = new Position(576,946,"B333","B333");
    	graph[282] = new Position(560,934,"B334","B334");
    	graph[283] = new Position(547,922,"B335","B335");
    	graph[284] = new Position(533,907,"B336","B336");
    	graph[285] = new Position(155,615,"A Block","A01");
    	graph[286] = new Position(195,600,"A Block","A02");
    	graph[287] = new Position(222,625,"A Block","A03");
    	graph[288] = new Position(256,652,"A Block","A04");
    	graph[289] = new Position(290,684,"A Block","A05");
    	graph[290] = new Position(325,720,"A Block","A06");
    	graph[291] = new Position(353,748,"A Block","A07");
    	graph[292] = new Position(383,777,"A Block","A08");
    	graph[293] = new Position(420,815,"A Block","A09");
    	graph[294] = new Position(453,851,"A Block","A10");
    	graph[295] = new Position(448,875,"A Block","A11");
    	graph[296] = new Position(490,880,"A Block","A12");
    	graph[297] = new Position(403,856,"A Block","A13");
    	graph[298] = new Position(360,856,"A Block","A14");
    	graph[299] = new Position(340,852,"A Block","A15");
    	graph[300] = new Position(315,828,"A Block","A16");
    	graph[301] = new Position(291,802,"A Block","A17");
    	graph[302] = new Position(264,775,"A Block","A18");
    	graph[303] = new Position(240,751,"A Block","A19");
    	graph[304] = new Position(210,721,"A Block","A20");
    	graph[305] = new Position(183,694,"A Block","A21");
    	graph[306] = new Position(186,654,"A Block","A22");
    	graph[307] = new Position(83,644,"A304","A304");
    	graph[308] = new Position(78,688,"A305","A305");
    	graph[309] = new Position(100,644,"A306","A306");
    	graph[310] = new Position(113,688,"A307","A307");
    	graph[311] = new Position(158,685,"A310","A310");
    	graph[312] = new Position(173,712,"A311","A311");
    	graph[313] = new Position(196,680,"A312","A312");
    	graph[314] = new Position(183,725,"A313","A313");
    	graph[315] = new Position(213,695,"A314","A314");
    	graph[316] = new Position(241,718,"A315","A315");
    	graph[317] = new Position(198,739,"A316","A316");
    	graph[318] = new Position(211,753,"A317","A317");
    	graph[319] = new Position(223,767,"A318","A318");
    	graph[320] = new Position(275,750,"A319","A319");
    	graph[321] = new Position(236,776,"A320","A320");
    	graph[322] = new Position(250,789,"A321","A321");
    	graph[323] = new Position(262,802,"A322","A322");
    	graph[324] = new Position(306,748,"A323","A323");
    	graph[325] = new Position(278,818,"A324","A324");
    	graph[326] = new Position(319,800,"A325","A325");
    	graph[327] = new Position(288,829,"A326","A326");
    	graph[328] = new Position(331,813,"A327","A327");
    	graph[329] = new Position(302,842,"A328","A328");
    	graph[330] = new Position(347,825,"A329","A329");
    	graph[331] = new Position(319,858,"A330","A330");
    	graph[332] = new Position(332,871,"A331","A331");
    	graph[333] = new Position(355,884,"A332","A332");
    	graph[334] = new Position(361,836,"A334","A334");
    	graph[335] = new Position(387,881,"A335","A335");
    	graph[336] = new Position(420,887,"A336","A336");
    	graph[337] = new Position(238,671,"A338","A338");

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

    	graph[47].accesible = new Position[2];
    	graph[47].accesible[0] = graph[48];
    	graph[47].accesible[1] = graph[169];

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

    	graph[145].accesible = new Position[2];
    	graph[145].accesible[0] = graph[226];
    	graph[145].accesible[1] = graph[146];

    	graph[146].accesible = new Position[3];
    	graph[146].accesible[0] = graph[145];
    	graph[146].accesible[1] = graph[147];
    	graph[146].accesible[2] = graph[178];

    	graph[147].accesible = new Position[3];
    	graph[147].accesible[0] = graph[146];
    	graph[147].accesible[1] = graph[148];
    	graph[147].accesible[2] = graph[179];

    	graph[148].accesible = new Position[3];
    	graph[148].accesible[0] = graph[147];
    	graph[148].accesible[1] = graph[149];
    	graph[148].accesible[2] = graph[180];

    	graph[149].accesible = new Position[3];
    	graph[149].accesible[0] = graph[148];
    	graph[149].accesible[1] = graph[150];
    	graph[149].accesible[2] = graph[181];

    	graph[150].accesible = new Position[3];
    	graph[150].accesible[0] = graph[149];
    	graph[150].accesible[1] = graph[151];
    	graph[150].accesible[2] = graph[182];

    	graph[151].accesible = new Position[2];
    	graph[151].accesible[0] = graph[150];
    	graph[151].accesible[1] = graph[152];

    	graph[152].accesible = new Position[3];
    	graph[152].accesible[0] = graph[151];
    	graph[152].accesible[1] = graph[153];
    	graph[152].accesible[2] = graph[159];

    	graph[153].accesible = new Position[3];
    	graph[153].accesible[0] = graph[152];
    	graph[153].accesible[1] = graph[154];
    	graph[153].accesible[2] = graph[183];

    	graph[154].accesible = new Position[2];
    	graph[154].accesible[0] = graph[153];
    	graph[154].accesible[1] = graph[155];

    	graph[155].accesible = new Position[4];
    	graph[155].accesible[0] = graph[154];
    	graph[155].accesible[1] = graph[156];
    	graph[155].accesible[2] = graph[184];
    	graph[155].accesible[3] = graph[185];

    	graph[156].accesible = new Position[2];
    	graph[156].accesible[0] = graph[155];
    	graph[156].accesible[1] = graph[157];

    	graph[157].accesible = new Position[3];
    	graph[157].accesible[0] = graph[156];
    	graph[157].accesible[1] = graph[158];
    	graph[157].accesible[2] = graph[186];

    	graph[158].accesible = new Position[2];
    	graph[158].accesible[0] = graph[157];
    	graph[158].accesible[1] = graph[186];

    	graph[159].accesible = new Position[3];
    	graph[159].accesible[0] = graph[152];
    	graph[159].accesible[1] = graph[160];
    	graph[159].accesible[2] = graph[206];

    	graph[160].accesible = new Position[5];
    	graph[160].accesible[0] = graph[204];
    	graph[160].accesible[1] = graph[205];
    	graph[160].accesible[2] = graph[159];
    	graph[160].accesible[3] = graph[161];
    	graph[160].accesible[4] = graph[177];

    	graph[161].accesible = new Position[2];
    	graph[161].accesible[0] = graph[160];
    	graph[161].accesible[1] = graph[162];

    	graph[162].accesible = new Position[2];
    	graph[162].accesible[0] = graph[161];
    	graph[162].accesible[1] = graph[163];

    	graph[163].accesible = new Position[2];
    	graph[163].accesible[0] = graph[162];
    	graph[163].accesible[1] = graph[164];

    	graph[164].accesible = new Position[3];
    	graph[164].accesible[0] = graph[163];
    	graph[164].accesible[1] = graph[165];
    	graph[164].accesible[2] = graph[184];

    	graph[165].accesible = new Position[2];
    	graph[165].accesible[0] = graph[164];
    	graph[165].accesible[1] = graph[166];

    	graph[166].accesible = new Position[2];
    	graph[166].accesible[0] = graph[165];
    	graph[166].accesible[1] = graph[167];

    	graph[167].accesible = new Position[4];
    	graph[167].accesible[0] = graph[166];
    	graph[167].accesible[1] = graph[168];
    	graph[167].accesible[2] = graph[170];
    	graph[167].accesible[3] = graph[188];

    	graph[168].accesible = new Position[3];
    	graph[168].accesible[0] = graph[167];
    	graph[168].accesible[1] = graph[169];
    	graph[168].accesible[2] = graph[187];

    	graph[169].accesible = new Position[2];
    	graph[169].accesible[0] = graph[47];
    	graph[169].accesible[1] = graph[168];

    	graph[170].accesible = new Position[3];
    	graph[170].accesible[0] = graph[167];
    	graph[170].accesible[1] = graph[171];
    	graph[170].accesible[2] = graph[189];

    	graph[171].accesible = new Position[4];
    	graph[171].accesible[0] = graph[170];
    	graph[171].accesible[1] = graph[172];
    	graph[171].accesible[2] = graph[190];
    	graph[171].accesible[3] = graph[191];

    	graph[172].accesible = new Position[3];
    	graph[172].accesible[0] = graph[171];
    	graph[172].accesible[1] = graph[173];
    	graph[172].accesible[2] = graph[192];

    	graph[173].accesible = new Position[4];
    	graph[173].accesible[0] = graph[172];
    	graph[173].accesible[1] = graph[174];
    	graph[173].accesible[2] = graph[193];
    	graph[173].accesible[3] = graph[194];

    	graph[174].accesible = new Position[7];
    	graph[174].accesible[0] = graph[173];
    	graph[174].accesible[1] = graph[175];
    	graph[174].accesible[2] = graph[195];
    	graph[174].accesible[3] = graph[196];
    	graph[174].accesible[4] = graph[197];
    	graph[174].accesible[5] = graph[198];
    	graph[174].accesible[6] = graph[199];

    	graph[175].accesible = new Position[4];
    	graph[175].accesible[0] = graph[174];
    	graph[175].accesible[1] = graph[176];
    	graph[175].accesible[2] = graph[192];
    	graph[175].accesible[3] = graph[200];

    	graph[176].accesible = new Position[3];
    	graph[176].accesible[0] = graph[175];
    	graph[176].accesible[1] = graph[177];
    	graph[176].accesible[2] = graph[201];

    	graph[177].accesible = new Position[4];
    	graph[177].accesible[0] = graph[160];
    	graph[177].accesible[1] = graph[176];
    	graph[177].accesible[2] = graph[202];
    	graph[177].accesible[3] = graph[203];

    	graph[178].accesible = new Position[1];
    	graph[178].accesible[0] = graph[146];

    	graph[179].accesible = new Position[1];
    	graph[179].accesible[0] = graph[147];

    	graph[180].accesible = new Position[1];
    	graph[180].accesible[0] = graph[148];

    	graph[181].accesible = new Position[1];
    	graph[181].accesible[0] = graph[149];

    	graph[182].accesible = new Position[1];
    	graph[182].accesible[0] = graph[150];

    	graph[183].accesible = new Position[1];
    	graph[183].accesible[0] = graph[153];

    	graph[184].accesible = new Position[2];
    	graph[184].accesible[0] = graph[155];
    	graph[184].accesible[1] = graph[164];

    	graph[185].accesible = new Position[1];
    	graph[185].accesible[0] = graph[155];

    	graph[186].accesible = new Position[2];
    	graph[186].accesible[0] = graph[157];
    	graph[186].accesible[1] = graph[158];

    	graph[187].accesible = new Position[1];
    	graph[187].accesible[0] = graph[168];

    	graph[188].accesible = new Position[1];
    	graph[188].accesible[0] = graph[167];

    	graph[189].accesible = new Position[1];
    	graph[189].accesible[0] = graph[170];

    	graph[190].accesible = new Position[1];
    	graph[190].accesible[0] = graph[171];

    	graph[191].accesible = new Position[1];
    	graph[191].accesible[0] = graph[171];

    	graph[192].accesible = new Position[2];
    	graph[192].accesible[0] = graph[172];
    	graph[192].accesible[1] = graph[175];

    	graph[193].accesible = new Position[1];
    	graph[193].accesible[0] = graph[173];

    	graph[194].accesible = new Position[1];
    	graph[194].accesible[0] = graph[173];

    	graph[195].accesible = new Position[1];
    	graph[195].accesible[0] = graph[174];

    	graph[196].accesible = new Position[1];
    	graph[196].accesible[0] = graph[174];

    	graph[197].accesible = new Position[1];
    	graph[197].accesible[0] = graph[174];

    	graph[198].accesible = new Position[1];
    	graph[198].accesible[0] = graph[174];

    	graph[199].accesible = new Position[1];
    	graph[199].accesible[0] = graph[174];

    	graph[200].accesible = new Position[1];
    	graph[200].accesible[0] = graph[175];

    	graph[201].accesible = new Position[1];
    	graph[201].accesible[0] = graph[176];

    	graph[202].accesible = new Position[1];
    	graph[202].accesible[0] = graph[177];

    	graph[203].accesible = new Position[1];
    	graph[203].accesible[0] = graph[177];

    	graph[204].accesible = new Position[1];
    	graph[204].accesible[0] = graph[160];

    	graph[205].accesible = new Position[1];
    	graph[205].accesible[0] = graph[160];

    	graph[206].accesible = new Position[1];
    	graph[206].accesible[0] = graph[159];

    	graph[207].accesible = new Position[2];
    	graph[207].accesible[0] = graph[208];
    	graph[207].accesible[1] = graph[296];

    	graph[208].accesible = new Position[2];
    	graph[208].accesible[0] = graph[207];
    	graph[208].accesible[1] = graph[209];

    	graph[209].accesible = new Position[4];
    	graph[209].accesible[0] = graph[208];
    	graph[209].accesible[1] = graph[210];
    	graph[209].accesible[2] = graph[240];
    	graph[209].accesible[3] = graph[284];

    	graph[210].accesible = new Position[4];
    	graph[210].accesible[0] = graph[209];
    	graph[210].accesible[1] = graph[211];
    	graph[210].accesible[2] = graph[283];
    	graph[210].accesible[3] = graph[241];

    	graph[211].accesible = new Position[3];
    	graph[211].accesible[0] = graph[210];
    	graph[211].accesible[1] = graph[212];
    	graph[211].accesible[2] = graph[282];

    	graph[212].accesible = new Position[3];
    	graph[212].accesible[0] = graph[211];
    	graph[212].accesible[1] = graph[213];
    	graph[212].accesible[2] = graph[281];

    	graph[213].accesible = new Position[3];
    	graph[213].accesible[0] = graph[212];
    	graph[213].accesible[1] = graph[214];
    	graph[213].accesible[2] = graph[280];

    	graph[214].accesible = new Position[3];
    	graph[214].accesible[0] = graph[213];
    	graph[214].accesible[1] = graph[215];
    	graph[214].accesible[2] = graph[279];

    	graph[215].accesible = new Position[4];
    	graph[215].accesible[0] = graph[214];
    	graph[215].accesible[1] = graph[216];
    	graph[215].accesible[2] = graph[278];
    	graph[215].accesible[3] = graph[270];

    	graph[216].accesible = new Position[3];
    	graph[216].accesible[0] = graph[215];
    	graph[216].accesible[1] = graph[217];
    	graph[216].accesible[2] = graph[277];

    	graph[217].accesible = new Position[7];
    	graph[217].accesible[0] = graph[216];
    	graph[217].accesible[1] = graph[218];
    	graph[217].accesible[2] = graph[272];
    	graph[217].accesible[3] = graph[273];
    	graph[217].accesible[4] = graph[274];
    	graph[217].accesible[5] = graph[275];
    	graph[217].accesible[6] = graph[276];

    	graph[218].accesible = new Position[3];
    	graph[218].accesible[0] = graph[217];
    	graph[218].accesible[1] = graph[219];
    	graph[218].accesible[2] = graph[271];

    	graph[219].accesible = new Position[3];
    	graph[219].accesible[0] = graph[218];
    	graph[219].accesible[1] = graph[220];
    	graph[219].accesible[2] = graph[269];

    	graph[220].accesible = new Position[3];
    	graph[220].accesible[0] = graph[219];
    	graph[220].accesible[1] = graph[221];
    	graph[220].accesible[2] = graph[268];

    	graph[221].accesible = new Position[3];
    	graph[221].accesible[0] = graph[220];
    	graph[221].accesible[1] = graph[222];
    	graph[221].accesible[2] = graph[267];

    	graph[222].accesible = new Position[4];
    	graph[222].accesible[0] = graph[221];
    	graph[222].accesible[1] = graph[223];
    	graph[222].accesible[2] = graph[265];
    	graph[222].accesible[3] = graph[266];

    	graph[223].accesible = new Position[4];
    	graph[223].accesible[0] = graph[222];
    	graph[223].accesible[1] = graph[224];
    	graph[223].accesible[2] = graph[247];
    	graph[223].accesible[3] = graph[264];

    	graph[224].accesible = new Position[5];
    	graph[224].accesible[0] = graph[223];
    	graph[224].accesible[1] = graph[225];
    	graph[224].accesible[2] = graph[227];
    	graph[224].accesible[3] = graph[247];
    	graph[224].accesible[4] = graph[263];

    	graph[225].accesible = new Position[3];
    	graph[225].accesible[0] = graph[224];
    	graph[225].accesible[1] = graph[226];
    	graph[225].accesible[2] = graph[262];

    	graph[226].accesible = new Position[2];
    	graph[226].accesible[0] = graph[225];
    	graph[226].accesible[1] = graph[145];

    	graph[227].accesible = new Position[4];
    	graph[227].accesible[0] = graph[224];
    	graph[227].accesible[1] = graph[223];
    	graph[227].accesible[2] = graph[247];
    	graph[227].accesible[3] = graph[261];

    	graph[228].accesible = new Position[4];
    	graph[228].accesible[0] = graph[227];
    	graph[228].accesible[1] = graph[229];
    	graph[228].accesible[2] = graph[259];
    	graph[228].accesible[3] = graph[260];

    	graph[229].accesible = new Position[3];
    	graph[229].accesible[0] = graph[228];
    	graph[229].accesible[1] = graph[230];
    	graph[229].accesible[2] = graph[258];

    	graph[230].accesible = new Position[4];
    	graph[230].accesible[0] = graph[229];
    	graph[230].accesible[1] = graph[231];
    	graph[230].accesible[2] = graph[257];
    	graph[230].accesible[3] = graph[256];

    	graph[231].accesible = new Position[4];
    	graph[231].accesible[0] = graph[230];
    	graph[231].accesible[1] = graph[232];
    	graph[231].accesible[2] = graph[255];
    	graph[231].accesible[3] = graph[257];

    	graph[232].accesible = new Position[3];
    	graph[232].accesible[0] = graph[231];
    	graph[232].accesible[1] = graph[233];
    	graph[232].accesible[2] = graph[254];

    	graph[233].accesible = new Position[3];
    	graph[233].accesible[0] = graph[232];
    	graph[233].accesible[1] = graph[234];
    	graph[233].accesible[2] = graph[253];

    	graph[234].accesible = new Position[3];
    	graph[234].accesible[0] = graph[233];
    	graph[234].accesible[1] = graph[235];
    	graph[234].accesible[2] = graph[236];

    	graph[235].accesible = new Position[1];
    	graph[235].accesible[0] = graph[234];

    	graph[236].accesible = new Position[3];
    	graph[236].accesible[0] = graph[234];
    	graph[236].accesible[1] = graph[237];
    	graph[236].accesible[2] = graph[252];

    	graph[237].accesible = new Position[2];
    	graph[237].accesible[0] = graph[236];
    	graph[237].accesible[1] = graph[238];

    	graph[238].accesible = new Position[3];
    	graph[238].accesible[0] = graph[237];
    	graph[238].accesible[1] = graph[239];
    	graph[238].accesible[2] = graph[251];

    	graph[239].accesible = new Position[4];
    	graph[239].accesible[0] = graph[238];
    	graph[239].accesible[1] = graph[240];
    	graph[239].accesible[2] = graph[249];
    	graph[239].accesible[3] = graph[250];

    	graph[240].accesible = new Position[3];
    	graph[240].accesible[0] = graph[239];
    	graph[240].accesible[1] = graph[209];
    	graph[240].accesible[2] = graph[248];

    	graph[241].accesible = new Position[2];
    	graph[241].accesible[0] = graph[210];
    	graph[241].accesible[1] = graph[242];

    	graph[242].accesible = new Position[2];
    	graph[242].accesible[0] = graph[241];
    	graph[242].accesible[1] = graph[243];

    	graph[243].accesible = new Position[2];
    	graph[243].accesible[0] = graph[242];
    	graph[243].accesible[1] = graph[244];

    	graph[244].accesible = new Position[2];
    	graph[244].accesible[0] = graph[243];
    	graph[244].accesible[1] = graph[245];

    	graph[245].accesible = new Position[2];
    	graph[245].accesible[0] = graph[244];
    	graph[245].accesible[1] = graph[246];

    	graph[246].accesible = new Position[2];
    	graph[246].accesible[0] = graph[245];
    	graph[246].accesible[1] = graph[247];

    	graph[247].accesible = new Position[4];
    	graph[247].accesible[0] = graph[223];
    	graph[247].accesible[1] = graph[224];
    	graph[247].accesible[2] = graph[227];
    	graph[247].accesible[3] = graph[246];

    	graph[248].accesible = new Position[1];
    	graph[248].accesible[0] = graph[240];

    	graph[249].accesible = new Position[1];
    	graph[249].accesible[0] = graph[239];

    	graph[250].accesible = new Position[1];
    	graph[250].accesible[0] = graph[239];

    	graph[251].accesible = new Position[1];
    	graph[251].accesible[0] = graph[238];

    	graph[252].accesible = new Position[1];
    	graph[252].accesible[0] = graph[236];

    	graph[253].accesible = new Position[1];
    	graph[253].accesible[0] = graph[233];

    	graph[254].accesible = new Position[1];
    	graph[254].accesible[0] = graph[232];

    	graph[255].accesible = new Position[1];
    	graph[255].accesible[0] = graph[231];

    	graph[256].accesible = new Position[1];
    	graph[256].accesible[0] = graph[230];

    	graph[257].accesible = new Position[1];
    	graph[257].accesible[0] = graph[231];

    	graph[258].accesible = new Position[1];
    	graph[258].accesible[0] = graph[229];

    	graph[259].accesible = new Position[1];
    	graph[259].accesible[0] = graph[228];

    	graph[260].accesible = new Position[1];
    	graph[260].accesible[0] = graph[228];

    	graph[261].accesible = new Position[1];
    	graph[261].accesible[0] = graph[227];

    	graph[262].accesible = new Position[1];
    	graph[262].accesible[0] = graph[225];

    	graph[263].accesible = new Position[1];
    	graph[263].accesible[0] = graph[224];

    	graph[264].accesible = new Position[1];
    	graph[264].accesible[0] = graph[223];

    	graph[265].accesible = new Position[1];
    	graph[265].accesible[0] = graph[222];

    	graph[266].accesible = new Position[1];
    	graph[266].accesible[0] = graph[222];

    	graph[267].accesible = new Position[1];
    	graph[267].accesible[0] = graph[221];

    	graph[268].accesible = new Position[1];
    	graph[268].accesible[0] = graph[220];

    	graph[269].accesible = new Position[1];
    	graph[269].accesible[0] = graph[219];

    	graph[270].accesible = new Position[1];
    	graph[270].accesible[0] = graph[215];

    	graph[271].accesible = new Position[1];
    	graph[271].accesible[0] = graph[218];

    	graph[272].accesible = new Position[1];
    	graph[272].accesible[0] = graph[217];

    	graph[273].accesible = new Position[1];
    	graph[273].accesible[0] = graph[217];

    	graph[274].accesible = new Position[1];
    	graph[274].accesible[0] = graph[217];

    	graph[275].accesible = new Position[1];
    	graph[275].accesible[0] = graph[217];

    	graph[276].accesible = new Position[1];
    	graph[276].accesible[0] = graph[217];

    	graph[277].accesible = new Position[1];
    	graph[277].accesible[0] = graph[216];

    	graph[278].accesible = new Position[1];
    	graph[278].accesible[0] = graph[215];

    	graph[279].accesible = new Position[1];
    	graph[279].accesible[0] = graph[214];

    	graph[280].accesible = new Position[1];
    	graph[280].accesible[0] = graph[213];

    	graph[281].accesible = new Position[1];
    	graph[281].accesible[0] = graph[212];

    	graph[282].accesible = new Position[1];
    	graph[282].accesible[0] = graph[211];

    	graph[283].accesible = new Position[1];
    	graph[283].accesible[0] = graph[210];

    	graph[284].accesible = new Position[1];
    	graph[284].accesible[0] = graph[209];

    	graph[285].accesible = new Position[1];
    	graph[285].accesible[0] = graph[286];

    	graph[286].accesible = new Position[2];
    	graph[286].accesible[0] = graph[285];
    	graph[286].accesible[1] = graph[287];

    	graph[287].accesible = new Position[3];
    	graph[287].accesible[0] = graph[286];
    	graph[287].accesible[1] = graph[288];
    	graph[287].accesible[2] = graph[306];

    	graph[288].accesible = new Position[3];
    	graph[288].accesible[0] = graph[287];
    	graph[288].accesible[1] = graph[289];
    	graph[288].accesible[2] = graph[337];

    	graph[289].accesible = new Position[2];
    	graph[289].accesible[0] = graph[288];
    	graph[289].accesible[1] = graph[290];

    	graph[290].accesible = new Position[3];
    	graph[290].accesible[0] = graph[289];
    	graph[290].accesible[1] = graph[291];
    	graph[290].accesible[2] = graph[324];

    	graph[291].accesible = new Position[2];
    	graph[291].accesible[0] = graph[290];
    	graph[291].accesible[1] = graph[292];

    	graph[292].accesible = new Position[2];
    	graph[292].accesible[0] = graph[291];
    	graph[292].accesible[1] = graph[293];

    	graph[293].accesible = new Position[2];
    	graph[293].accesible[0] = graph[292];
    	graph[293].accesible[1] = graph[294];

    	graph[294].accesible = new Position[4];
    	graph[294].accesible[0] = graph[293];
    	graph[294].accesible[1] = graph[295];
    	graph[294].accesible[2] = graph[296];
    	graph[294].accesible[3] = graph[297];

    	graph[295].accesible = new Position[2];
    	graph[295].accesible[0] = graph[294];
    	graph[295].accesible[1] = graph[296];

    	graph[296].accesible = new Position[3];
    	graph[296].accesible[0] = graph[295];
    	graph[296].accesible[1] = graph[207];
    	graph[296].accesible[2] = graph[294];

    	graph[297].accesible = new Position[4];
    	graph[297].accesible[0] = graph[294];
    	graph[297].accesible[1] = graph[298];
    	graph[297].accesible[2] = graph[335];
    	graph[297].accesible[3] = graph[336];

    	graph[298].accesible = new Position[4];
    	graph[298].accesible[0] = graph[297];
    	graph[298].accesible[1] = graph[299];
    	graph[298].accesible[2] = graph[333];
    	graph[298].accesible[3] = graph[334];

    	graph[299].accesible = new Position[5];
    	graph[299].accesible[0] = graph[298];
    	graph[299].accesible[1] = graph[300];
    	graph[299].accesible[2] = graph[330];
    	graph[299].accesible[3] = graph[331];
    	graph[299].accesible[4] = graph[332];

    	graph[300].accesible = new Position[5];
    	graph[300].accesible[0] = graph[299];
    	graph[300].accesible[1] = graph[301];
    	graph[300].accesible[2] = graph[327];
    	graph[300].accesible[3] = graph[328];
    	graph[300].accesible[4] = graph[329];

    	graph[301].accesible = new Position[4];
    	graph[301].accesible[0] = graph[300];
    	graph[301].accesible[1] = graph[302];
    	graph[301].accesible[2] = graph[325];
    	graph[301].accesible[3] = graph[326];

    	graph[302].accesible = new Position[5];
    	graph[302].accesible[0] = graph[301];
    	graph[302].accesible[1] = graph[303];
    	graph[302].accesible[2] = graph[320];
    	graph[302].accesible[3] = graph[322];
    	graph[302].accesible[4] = graph[323];

    	graph[303].accesible = new Position[6];
    	graph[303].accesible[0] = graph[302];
    	graph[303].accesible[1] = graph[304];
    	graph[303].accesible[2] = graph[316];
    	graph[303].accesible[3] = graph[318];
    	graph[303].accesible[4] = graph[319];
    	graph[303].accesible[5] = graph[321];

    	graph[304].accesible = new Position[5];
    	graph[304].accesible[0] = graph[303];
    	graph[304].accesible[1] = graph[305];
    	graph[304].accesible[2] = graph[314];
    	graph[304].accesible[3] = graph[315];
    	graph[304].accesible[4] = graph[317];

    	graph[305].accesible = new Position[4];
    	graph[305].accesible[0] = graph[304];
    	graph[305].accesible[1] = graph[306];
    	graph[305].accesible[2] = graph[312];
    	graph[305].accesible[3] = graph[313];

    	graph[306].accesible = new Position[7];
    	graph[306].accesible[0] = graph[305];
    	graph[306].accesible[1] = graph[287];
    	graph[306].accesible[2] = graph[307];
    	graph[306].accesible[3] = graph[308];
    	graph[306].accesible[4] = graph[309];
    	graph[306].accesible[5] = graph[310];
    	graph[306].accesible[6] = graph[311];

    	graph[307].accesible = new Position[1];
    	graph[307].accesible[0] = graph[306];

    	graph[308].accesible = new Position[1];
    	graph[308].accesible[0] = graph[306];

    	graph[309].accesible = new Position[1];
    	graph[309].accesible[0] = graph[306];

    	graph[310].accesible = new Position[1];
    	graph[310].accesible[0] = graph[306];

    	graph[311].accesible = new Position[1];
    	graph[311].accesible[0] = graph[306];

    	graph[312].accesible = new Position[1];
    	graph[312].accesible[0] = graph[305];

    	graph[313].accesible = new Position[1];
    	graph[313].accesible[0] = graph[305];

    	graph[314].accesible = new Position[1];
    	graph[314].accesible[0] = graph[304];

    	graph[315].accesible = new Position[1];
    	graph[315].accesible[0] = graph[304];

    	graph[316].accesible = new Position[1];
    	graph[316].accesible[0] = graph[303];

    	graph[317].accesible = new Position[1];
    	graph[317].accesible[0] = graph[304];

    	graph[318].accesible = new Position[1];
    	graph[318].accesible[0] = graph[303];

    	graph[319].accesible = new Position[1];
    	graph[319].accesible[0] = graph[303];

    	graph[320].accesible = new Position[1];
    	graph[320].accesible[0] = graph[302];

    	graph[321].accesible = new Position[1];
    	graph[321].accesible[0] = graph[303];

    	graph[322].accesible = new Position[1];
    	graph[322].accesible[0] = graph[302];

    	graph[323].accesible = new Position[1];
    	graph[323].accesible[0] = graph[302];

    	graph[324].accesible = new Position[1];
    	graph[324].accesible[0] = graph[290];

    	graph[325].accesible = new Position[1];
    	graph[325].accesible[0] = graph[301];

    	graph[326].accesible = new Position[1];
    	graph[326].accesible[0] = graph[301];

    	graph[327].accesible = new Position[1];
    	graph[327].accesible[0] = graph[300];

    	graph[328].accesible = new Position[1];
    	graph[328].accesible[0] = graph[300];

    	graph[329].accesible = new Position[1];
    	graph[329].accesible[0] = graph[300];

    	graph[330].accesible = new Position[1];
    	graph[330].accesible[0] = graph[299];

    	graph[331].accesible = new Position[1];
    	graph[331].accesible[0] = graph[299];

    	graph[332].accesible = new Position[1];
    	graph[332].accesible[0] = graph[299];

    	graph[333].accesible = new Position[1];
    	graph[333].accesible[0] = graph[298];

    	graph[334].accesible = new Position[1];
    	graph[334].accesible[0] = graph[298];

    	graph[335].accesible = new Position[1];
    	graph[335].accesible[0] = graph[297];

    	graph[336].accesible = new Position[1];
    	graph[336].accesible[0] = graph[297];

    	graph[337].accesible = new Position[1];
    	graph[337].accesible[0] = graph[288];

    	Log.i("ASTAR", "Graph Built of size " + graph.length);
    }
}
