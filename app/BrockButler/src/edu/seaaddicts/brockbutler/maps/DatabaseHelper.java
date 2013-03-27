package edu.seaaddicts.brockbutler.maps;

/**
 * DatabaseHelper.java
 * Brock Butler
 * Database helper class to ease database interaction 
 * portion of Brock Butler.
 * Created by Thomas Nelson 2013-03-05
 * Copyright (c) 2013 Sea Addicts. All rights reserved.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	/**
	 * Class variable for the DATABASEHARNESS class.
	 */
	private static final String KEY_NODE = "node_id";
	private static final String KEY_DESC = "desc";
	private static final String KEY_XPOS = "x";
	private static final String KEY_YPOS = "y";
	private static final String KEY_CONN = "con";

	private static final String DATABASE_NAME = "Database";
	private static final String DATABASE_TABLE = "node_connections";
	private static final int DATABASE_VERSION = 1;

	/**
	 * Constructor method for this class uses context argument to set up
	 * database connection.
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * If the database has not been created yet (ie. new device run) then the
	 * onCreate method is run to set up and populate the databases needed for
	 * the application.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_NODE
				+ " TEXT NOT NULL, " + KEY_DESC + " TEXT NOT NULL, " + KEY_XPOS
				+ " INTEGER NOT NULL, " + KEY_YPOS + " INTEGER NOT NULL, "
				+ KEY_CONN + " TEXT NOT NULL);");

		InsertNodes(db);
	}

	/**
	 * If the database version is updated then the update method is invoked to
	 * delete the old database and create it new again.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}

	/**
	 * Populates the node connection table.
	 */
	public void InsertNodes(SQLiteDatabase db) {
		db.execSQL("INSERT INTO " + DATABASE_TABLE + " (" + KEY_NODE + ", "
				+ KEY_DESC + ", " + KEY_XPOS + ", " + KEY_YPOS + ", "
				+ KEY_CONN + ") VALUES "
				+ "('D01', 'D Block', 1323, 879, 'D02'),"
				+ "('D02', 'D Block', 1348, 878, 'D01'),"
				+ "('D03', 'D Block', 1371, 877, 'D02'),"
				+ "('D04', 'D Block', 1390, 877, 'D03'),"
				+ "('D05', 'D Block', 1417, 876, 'D04'),"
				+ "('D06', 'D Block', 1437, 876, 'D05'),"
				+ "('D07', 'D Block', 1459, 876, 'D06'),"
				+ "('D08', 'D Block', 1480, 877, 'D07'),"
				+ "('D09', 'D Block', 1505, 878, 'D08'),"
				+ "('D10', 'D Block', 1523, 878, 'D09'),"
				+ "('D11', 'D Block', 1551, 871, 'D10'),"
				+ "('D12', 'D Block', 1563, 857, 'D11'),"
				+ "('D13', 'D Block', 1580, 838, 'D12'),"
				+ "('D14', 'D Block', 1591, 824, 'D13'),"
				+ "('D15', 'D Block', 1606, 812, 'D14'),"
				+ "('D16', 'D Block', 1623, 790, 'D15'),"
				+ "('D17', 'D Block', 1639, 782, 'D16'),"
				+ "('D18', 'D Block', 1656, 764, 'D17'),"
				+ "('D19', 'D Block', 1668, 750, 'D18'),"
				+ "('D20', 'D Block', 1679, 729, 'D19'),"
				+ "('D21', 'D Block', 1700, 708, 'D20'),"
				+ "('D22', 'D Block', 1670, 777, 'D18'),"
				+ "('D23', 'D Block', 1688, 795, 'D22'),"
				+ "('D24', 'D Block', 1710, 810, 'D23'),"
				+ "('D25', 'D Block', 1732, 839, 'D24'),"
				+ "('D26', 'D Block', 1747, 854, 'D25'),"
				+ "('D27', 'D Block', 1767, 875, 'D26'),"
				+ "('D28', 'D Block', 1780, 887, 'D27'),"
				+ "('D29', 'D Block', 1863, 909, 'D28'),"
				+ "('D30', 'D Block', 1821, 929, 'D29'),"
				+ "('D31', 'D Block', 1805, 943, 'D30'),"
				+ "('D32', 'D Block', 1784, 965, 'D31'),"
				+ "('D33', 'D Block', 1768, 980, 'D32'),"
				+ "('D34', 'D Block', 1746, 1003, 'D33'),"
				+ "('D35', 'D Block', 1728, 1020, 'D34'),"
				+ "('D36', 'D Block', 1708, 1042, 'D35'),"
				+ "('D37', 'D Block', 1693, 1052, 'D36'),"
				+ "('D38', 'D Block', 1672, 1037, 'D37'),"
				+ "('D39', 'D Block', 1657, 1022, 'D38'),"
				+ "('D40', 'D Block', 1638, 1002, 'D39'),"
				+ "('D41', 'D Block', 1615, 978, 'D40'),"
				+ "('D42', 'D Block', 1595, 960, 'D41'),"
				+ "('D43', 'D Block', 1585, 949, 'D42'),"
				+ "('D44', 'D Block', 1565, 929, 'D43'),"
				+ "('D45', 'D Block', 1541, 905, 'D44'),"
				+ "('D46', 'D Block', 1714, 691, 'D21'),"
				+ "('D02', 'D Block', 1348, 878, 'D03'),"
				+ "('D03', 'D Block', 1371, 877, 'D04'),"
				+ "('D04', 'D Block', 1390, 877, 'D05'),"
				+ "('D05', 'D Block', 1417, 876, 'D06'),"
				+ "('D06', 'D Block', 1437, 876, 'D07'),"
				+ "('D07', 'D Block', 1459, 876, 'D08'),"
				+ "('D08', 'D Block', 1480, 877, 'D09'),"
				+ "('D09', 'D Block', 1505, 878, 'D10'),"
				+ "('D10', 'D Block', 1523, 878, 'D11'),"
				+ "('D11', 'D Block', 1551, 871, 'D12'),"
				+ "('D12', 'D Block', 1563, 857, 'D13'),"
				+ "('D13', 'D Block', 1580, 838, 'D14'),"
				+ "('D14', 'D Block', 1591, 824, 'D15'),"
				+ "('D15', 'D Block', 1606, 812, 'D16'),"
				+ "('D16', 'D Block', 1623, 790, 'D17'),"
				+ "('D17', 'D Block', 1639, 782, 'D18'),"
				+ "('D18', 'D Block', 1656, 764, 'D19'),"
				+ "('D19', 'D Block', 1668, 750, 'D20'),"
				+ "('D20', 'D Block', 1679, 729, 'D21'),"
				+ "('D21', 'D Block', 1700, 708, 'D46'),"
				+ "('D22', 'D Block', 1670, 777, 'D23'),"
				+ "('D23', 'D Block', 1688, 795, 'D24'),"
				+ "('D24', 'D Block', 1710, 810, 'D25'),"
				+ "('D25', 'D Block', 1732, 839, 'D26'),"
				+ "('D26', 'D Block', 1747, 854, 'D27'),"
				+ "('D27', 'D Block', 1767, 875, 'D28'),"
				+ "('D28', 'D Block', 1780, 887, 'D29'),"
				+ "('D29', 'D Block', 1863, 909, 'D30'),"
				+ "('D30', 'D Block', 1821, 929, 'D31'),"
				+ "('D31', 'D Block', 1805, 943, 'D32'),"
				+ "('D32', 'D Block', 1784, 965, 'D33'),"
				+ "('D33', 'D Block', 1768, 980, 'D34'),"
				+ "('D34', 'D Block', 1746, 1003, 'D35'),"
				+ "('D35', 'D Block', 1728, 1020, 'D36'),"
				+ "('D36', 'D Block', 1708, 1042, 'D37'),"
				+ "('D37', 'D Block', 1693, 1052, 'D38'),"
				+ "('D38', 'D Block', 1672, 1037, 'D39'),"
				+ "('D39', 'D Block', 1657, 1022, 'D40'),"
				+ "('D40', 'D Block', 1638, 1002, 'D41'),"
				+ "('D41', 'D Block', 1615, 978, 'D42'),"
				+ "('D42', 'D Block', 1595, 960, 'D43'),"
				+ "('D43', 'D Block', 1585, 949, 'D44'),"
				+ "('D44', 'D Block', 1565, 929, 'D45'),"
				+ "('D45', 'D Block', 1541, 905, 'D10'),"
				+ "('D46', 'D Block', 1714, 691, 'J01'),"
				+ "('D10', 'D Block', 1523, 878, 'D45'),"
				+ "('D18', 'D Block', 1656, 764, 'D22'),"
				+ "('J01', 'J Block', 1732, 687, 'D46'),"
				+ "('J11', 'J Block', 1836, 600, 'J10'),"
				+ "('J11', 'J Block', 1836, 600, 'J12'),"
				+ "('J12', 'J Block', 1818, 583, 'J11'),"
				+ "('J13', 'J Block', 1858, 624, 'J10'),"
				+ "('J13', 'J Block', 1858, 624, 'J14'),"
				+ "('J14', 'J Block', 1873, 638, 'J13'),"
				+ "('J14', 'J Block', 1873, 638, 'J15'),"
				+ "('J15', 'J Block', 1886, 652, 'J14'),"
				+ "('J15', 'J Block', 1886, 652, 'J16'),"
				+ "('J16', 'J Block', 1901, 666, 'J15'),"
				+ "('J16', 'J Block', 1901, 666, 'J17'),"
				+ "('J17', 'J Block', 1906, 684, 'J16'),"
				+ "('J17', 'J Block', 1906, 684, 'J18'),"
				+ "('J18', 'J Block', 1902, 705, 'J17'),"
				+ "('J18', 'J Block', 1902, 705, 'J19'),"
				+ "('J19', 'J Block', 1886, 721, 'J18'),"
				+ "('J19', 'J Block', 1886, 721, 'J20'),"
				+ "('J20', 'J Block', 1868, 738, 'J19'),"
				+ "('J20', 'J Block', 1868, 738, 'J21'),"
				+ "('J21', 'J Block', 1856, 752, 'J20'),"
				+ "('J21', 'J Block', 1856, 752, 'J22'),"
				+ "('J22', 'J Block', 1836, 771, 'J21'),"
				+ "('J22', 'J Block', 1836, 771, 'J23'),"
				+ "('J23', 'J Block', 1818, 789, 'J22'),"
				+ "('J01', 'J Block', 1732, 687, 'J02'),"
				+ "('J02', 'J Block', 1763, 688, 'J01'),"
				+ "('J02', 'J Block', 1763, 688, 'J03'),"
				+ "('J02', 'J Block', 1763, 688, 'J05'),"
				+ "('J03', 'J Block', 1775, 698, 'J02'),"
				+ "('J03', 'J Block', 1775, 698, 'J04'),"
				+ "('J04', 'J Block', 1799, 723, 'J03'),"
				+ "('J05', 'J Block', 1724, 677, 'J02'),"
				+ "('J05', 'J Block', 1724, 677, 'J06'),"
				+ "('J06', 'J Block', 1789, 663, 'J05'),"
				+ "('J06', 'J Block', 1789, 663, 'J07'),"
				+ "('J07', 'J Block', 1805, 648, 'J06'),"
				+ "('J07', 'J Block', 1805, 648, 'J08'),"
				+ "('J08', 'J Block', 1819, 637, 'J07'),"
				+ "('J08', 'J Block', 1819, 637, 'J09'),"
				+ "('J09', 'J Block', 1832, 625, 'J08'),"
				+ "('J09', 'J Block', 1832, 625, 'J10'),"
				+ "('J10', 'J Block', 1846, 613, 'J09'),"
				+ "('J10', 'J Block', 1846, 613, 'J11'),"
				+ "('J10', 'J Block', 1846, 613, 'J13');");
	}
}