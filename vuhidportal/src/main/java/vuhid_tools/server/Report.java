/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package vuhid_tools.server;

/**
 * @author Long Phan <long2@pdx.edu>
 * This class is the Reporter.
 * It connects to the database, queries information, and generate the desired Excel file.
 */
public class Report
{
	private static DatabaseHandler database = new DatabaseHandler();
	private static ExcelHandler excel = new ExcelHandler();
	
	/**
	 * Connects to the database, queries information, and generate the desired Excel file.
	 * 
	 * @param FileName	The (String) desired Excel file.
	 * @param Month		The (integer) month that the report will be generated on.
	 * @param Year		The (integer) year that the report will be generated on.
	 */
	public static void generateReport(String FileName, int Month, int Year)
	{
		try
	    {
			database.connect();
			String[] inputLabels = new String[3];
			inputLabels[0] = "Number of IDs issued";
			inputLabels[1] = "Number of searches performed";
			inputLabels[2] = "Number of searches with provided VUHID ID performed";
			String[][] inputContents = new String[6][2];
			inputContents[0] = numberOfIDsIssued(Month, Year);
			inputContents[1] = numberOfSearches(Month, Year);
			inputContents[2] = numberOfSearchesWithID(Month, Year);
			excel.setOutputFile(FileName);
			excel.write(Month, Year, inputLabels, inputContents);
	    }
	    catch (Exception e)
	    {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    }
	}
	
	/**
	 * Queries information about the number of IDs that were issued.
	 * 
	 * @param Month		The (integer) month that the report will be generated on.
	 * @param Year		The (integer) year that the report will be generated on.
	 * @return			Returns the array of String (2) of the query results.
	 */
	private static String[] numberOfIDsIssued(int Month, int Year)
	{
		try
	    {
			String[] Result = new String[2];
			database.query("SELECT COUNT(ID) FROM `Transactions` " + monthlyRange(Month, Year) + " AND (`Type` = \'" + TransactionLogger.VUHIDNewOVID + "\' OR `Type` = \'" + TransactionLogger.VUHIDNewPVID + "\')");
			Result[0] = database.getResult().getString(1); // Monthly total
			database.query("SELECT COUNT(ID) FROM `Transactions` " + yearToDateRange(Month, Year) + " AND (`Type` = \'" + TransactionLogger.VUHIDNewOVID + "\' OR `Type` = \'" + TransactionLogger.VUHIDNewPVID + "\')");
			Result[1] = database.getResult().getString(1); // Year-to-date total
			return Result;
	    }
	    catch (Exception e)
	    {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
			return null;
	    }
	}
	
	/**
	 * Queries information about the number of searches that were performed.
	 * 
	 * @param Month		The (integer) month that the report will be generated on.
	 * @param Year		The (integer) year that the report will be generated on.
	 * @return			Returns the array of String (2) of the query results.
	 */
	private static String[] numberOfSearches(int Month, int Year)
	{
		try
	    {
			String[] Result = new String[2];
			database.query("SELECT COUNT(ID) FROM `Transactions` " + monthlyRange(Month, Year) + " AND (`Type` = \'" + TransactionLogger.PDQPatientRegistryFindCandidatesQuery + "\')");
			Result[0] = database.getResult().getString(1); // Monthly total
			database.query("SELECT COUNT(ID) FROM `Transactions` " + yearToDateRange(Month, Year) + " AND (`Type` = \'" + TransactionLogger.PDQPatientRegistryFindCandidatesQuery + "\')");
			Result[1] = database.getResult().getString(1); // Year-to-date total
			return Result;
	    }
	    catch (Exception e)
	    {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
			return null;
	    }
	}
	
	/**
	 * Queries information about the number of searches with ID that were performed.
	 * 
	 * @param Month		The (integer) month that the report will be generated on.
	 * @param Year		The (integer) year that the report will be generated on.
	 * @return			Returns the array of String (2) of the query results.
	 */
	private static String[] numberOfSearchesWithID(int Month, int Year)
	{
		try
	    {
			String[] Result = new String[2];
			database.query("SELECT COUNT(ID) FROM `Transactions` " + monthlyRange(Month, Year) + " AND (`Type` = \'" + TransactionLogger.PDQPatientRegistryFindCandidatesQuery + "\') AND `InputValue1` <> \'\'");
			Result[0] = database.getResult().getString(1); // Monthly total
			database.query("SELECT COUNT(ID) FROM `Transactions` " + yearToDateRange(Month, Year) + " AND (`Type` = \'" + TransactionLogger.PDQPatientRegistryFindCandidatesQuery + "\') AND `InputValue1` <> \'\'");
			Result[1] = database.getResult().getString(1); // Year-to-date total
			return Result;
	    }
	    catch (Exception e)
	    {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
			return null;
	    }
	}
	
	/**
	 * Short-cut for the date range (in the specified month) of the SQL query.
	 * 
	 * @param Month		The (integer) month that the report will be generated on.
	 * @param Year		The (integer) year that the report will be generated on.
	 * @return			Returns the String date range query part.
	 */
	private static String monthlyRange(int Month, int Year)
	{
		try
	    {
			int NextMonth = 0;
			int NextYear = Year;
			if(Month <= 12)
			{
				NextMonth = Month + 1;
			}
			else
			{
				NextMonth = 1;
				NextYear = Year + 1;
			}
			return "WHERE `Time` >= \'" + Year + "-" + Month + "-01 00:00:00\' AND `Time` < \'" + NextYear + "-" + NextMonth + "-01 00:00:00\'";
	    }
	    catch (Exception e)
	    {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
			return null;
	    }
	}
	
	/**
	 * Short-cut for the date range (from the beginning of the year to the specified month) of the SQL query.
	 * 
	 * @param Month		The (integer) month that the report will be generated on.
	 * @param Year		The (integer) year that the report will be generated on.
	 * @return			Returns the String date range query part.
	 */
	private static String yearToDateRange(int Month, int Year)
	{
		try
	    {
			int NextMonth = 0;
			int NextYear = Year;
			if(Month <= 12)
			{
				NextMonth = Month + 1;
			}
			else
			{
				NextMonth = 1;
				NextYear = Year + 1;
			}
			return "WHERE `Time` >= \'" + Year + "-01-01 00:00:00\' AND `Time` < \'" + NextYear + "-" + NextMonth + "-01 00:00:00\'";
	    }
	    catch (Exception e)
	    {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
			return null;
	    }
	}
}