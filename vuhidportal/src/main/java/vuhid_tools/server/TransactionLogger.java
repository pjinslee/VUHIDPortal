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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * @author Long Phan <long2@pdx.edu>
 * This class implements the TransactionLoggerInterface.
 * It connects to the database.
 * And logs 14 transactions.
 */
public class TransactionLogger implements TransactionLoggerInterface
{
	public static final int VUHIDNewOVID = 1;
	public static final int VUHIDNewPVID = 2;
	public static final int VUHIDIDStatus = 3;
	public static final int VUHIDRetireID = 4;
	public static final int VUHIDTerminateID = 5;
	public static final int VUHIDRequestDataLocations = 6;
	public static final int VUHIDReplaceID = 7;
	public static final int VUHIDRetirementTerminationNotice = 8;
	public static final int VUHIDReplacementNotice = 9;
	public static final int PIXPatientRegistryRecordRevised = 10;
	public static final int PIXPatientRegistryRecordAdded = 11;
	public static final int PIXPatientRegistryGetIdentifiersQuery = 12;
	public static final int PIXPatientRegistryDuplicatesResolved = 13;
	public static final int PDQPatientRegistryFindCandidatesQuery = 14;
	private static DatabaseHandler database = new DatabaseHandler();
	
	/**
	 * Connects to the database, log the input transaction.
	 * 
	 * @param type						The (integer) transaction type number.
	 * @param previous_transaction		The (String) parent transaction SHA1 hash.
	 * @param values					The (array of String) transactions' input values.
	 * @return							Returns the newly added transaction record's ID.
	 */
	public int newTransaction(int type, String previous_transaction, String[] values)
	{
		database.connect();
		String Hash = "";
		String CurrentTimeStamp = SHA1Calculator.getCurrentTimeStamp();
		try // Calculate an unique transaction ID using SHA1
		{
			Hash = SHA1Calculator.SHA1(Integer.toString(type) + previous_transaction + values.toString() + CurrentTimeStamp);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		switch(type)
		{
			case VUHIDNewOVID:
			case PIXPatientRegistryRecordRevised:
			case PIXPatientRegistryRecordAdded:
			case PIXPatientRegistryGetIdentifiersQuery:
			case PIXPatientRegistryDuplicatesResolved:
			case PDQPatientRegistryFindCandidatesQuery:
			{
				database.query("INSERT INTO `transactions` (`Hash`, `Type`, `Time`) VALUES (\'" + Hash + "\', \'" + type + "\', \'" + CurrentTimeStamp + "\')");
				break;
			}
			case VUHIDNewPVID: // values[0] is the PC
			case VUHIDIDStatus: // values[0] is the VUHID ID
			case VUHIDRequestDataLocations: // values[0] is the VUHID ID
			{
				database.query("INSERT INTO `transactions` (`Hash`, `Type`, `Time`, `InputValue1`) VALUES (\'" + Hash + "\', \'" + type + "\', \'" + CurrentTimeStamp + "\', \'" + values[0] + "\')");
				break;
			}
			case VUHIDRetireID: // values[0] is the VUHID ID, values[1] is the reason
			case VUHIDTerminateID: // values[0] is the VUHID ID, values[1] is the reason
			case VUHIDReplaceID: // values[0] is the VUHID ID, values[1] is the reason
			{
				database.query("INSERT INTO `transactions` (`Hash`, `Type`, `Time`, `InputValue1`, `InputValue2`) VALUES (\'" + Hash + "\', \'" + type + "\', \'" + CurrentTimeStamp + "\', \'" + values[0] + "\', \'" + values[1] + "\')");
				break;
			}
			case VUHIDRetirementTerminationNotice: // values[0] is the VUHID ID, values[1] is the action taken, values[2] is the URL of the EMPI that has requested the change, values[3] is the time stamp, values[4] is the reason
			case VUHIDReplacementNotice: // values[0] is the old VUHID ID, values[1] is the new VUHID ID, values[2] is the URL of the EMPI that has requested the change, values[3] is the time stamp, values[4] is the reason
			{
				database.query("INSERT INTO `transactions` (`Hash`, `Type`, `Time`, `InputValue1`, `InputValue2`, `InputValue3`, `InputValue4`) VALUES (\'" + Hash + "\', \'" + type + "\', \'" + CurrentTimeStamp + "\', \'" + values[0] + "\', \'" + values[1] + "\')" + "\', \'" + values[2] + "\')" + "\', \'" + values[3] + "\')" + "\', \'" + values[4] + "\')");
				break;
			}
		}
		return getTransactionID(Hash);
	}
	
	/**
	 * Overloaded function without previous_transaction.
	 * 
	 * @param type						The (integer) transaction type number.
	 * @param values					The (array of String) transactions' input values.
	 * @return							Returns the newly added transaction record's ID.
	 */
	public int newTransaction(int type, String[] values)
	{
		return newTransaction(type, "-1", values);
	}
	
	/**
	 * Overloaded function without input values.
	 * 
	 * @param type						The (integer) transaction type number.
	 * @param previous_transaction		The (String) parent transaction SHA1 hash.
	 * @return							Returns the newly added transaction record's ID.
	 */
	public int newTransaction(int type, String previous_transaction)
	{
		String[] values = new String[1];
		values[0] = "";
		return newTransaction(type, previous_transaction, values);
	}
	
	/**
	 * Overloaded function without input values and previous_transaction.
	 * 
	 * @param type						The (integer) transaction type number.
	 * @return							Returns the newly added transaction record's ID.
	 */
	public int newTransaction(int type)
	{
		return newTransaction(type, "-1");
	}
	
	/**
	 * Get the transaction record's ID associated with the input SHA1 hash.
	 * 
	 * @param Hash						The (String) SHA1 hash.
	 * @return							Returns the transaction record's ID.
	 */
	private static int getTransactionID(String Hash)
	{
		database.query("SELECT `ID` FROM `transactions` WHERE `Hash` = \'" + Hash + "\'");
		try
		{
			return database.getResult().getInt(1);
		}
		catch (SQLException e)
		{
			System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    	return -1;
		}
	}
	
	/**
	 * Get the transaction record's type associated with the input transaction record's ID.
	 * 
	 * @param ID						The (int) ID.
	 * @return							Returns the transaction record's integer type.
	 */
	private static int getTransactionType(int ID)
	{
		database.query("SELECT `Type` FROM `transactions` WHERE `ID` = \'" + ID + "\'");
		try
		{
			return database.getResult().getInt(1);
		}
		catch (SQLException e)
		{
			System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    	return -1;
		}
	}
	
	/**
	 * Set the values of transaction record's Completed and ReturnValue fields associated with the input transaction record's ID.
	 * 
	 * @param ID						The (int) ID.
	 * @param return_value				The (String) return value of the transaction.
	 */
	public void setTransactionCompleted(int ID, String return_value)
	{
		database.query("UPDATE `transactions` SET `ReturnValue` = \'" + return_value + "\' WHERE `ID` = \'" + ID + "\'");
		database.query("UPDATE `transactions` SET `Completed` = True WHERE `ID` = \'" + ID + "\'");
	}
	
	/**
	 * Overloaded function with multiple return values (array of String).
	 * 
	 * @param ID						The (int) ID.
	 * @param return_value				The (array of String) return values of the transaction.
	 */
	public void setTransactionCompleted(int ID, String[] return_value)
	{
		String result = null;
		for(String s : return_value)
		{
			result += s;
			result += "\n";
		}
		setTransactionCompleted(ID, result);
	}
	
	/**
	 * Overloaded function with multiple return values (ArrayList of String).
	 * 
	 * @param ID						The (int) ID.
	 * @param return_value				The (ArrayList of String) return values of the transaction.
	 */
	public void setTransactionCompleted(int ID, ArrayList<String> return_value)
	{
		String result = null;
		for(String s : return_value)
		{
			result += s;
			result += "\n";
		}
		setTransactionCompleted(ID, result);
	}
	
	/**
	 * Overloaded function with a boolean return value.
	 * 
	 * @param ID						The (int) ID.
	 * @param return_value				The (boolean) return value of the transaction.
	 */
	public void setTransactionCompleted(int ID, boolean return_value)
	{
		if(return_value)
		{
			setTransactionCompleted(ID, "true");
		}
		else
		{
			setTransactionCompleted(ID, "false");
		}
	}
	
	/**
	 * Overloaded function with an integer return value.
	 * 
	 * @param ID						The (int) ID.
	 * @param return_value				The (integer) return value of the transaction.
	 */
	public void setTransactionCompleted(int ID, int return_value)
	{
		setTransactionCompleted(ID, Integer.toString(return_value));
	}
	
	/**
	 * Overloaded function without the return value.
	 * 
	 * @param ID						The (int) ID.
	 */
	public void setTransactionCompleted(int ID)
	{
		setTransactionCompleted(ID, "");
	}
	
	/**
	 * Generate the Excel report based on input month and year.
	 * 
	 * @param Month		The (integer) month that the report will be generated on.
	 * @param Year		The (integer) year that the report will be generated on.
	 * @return			Returns a part of the report location HTTP URL string.
	 */
	public String report(int Month, int Year)
	{
	    String FileName = null;
		try
		{
			FileName = "VUHIDPortalReport-" + SHA1Calculator.SHA1(SHA1Calculator.getCurrentTimeStamp()) + ".xls";
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		Report.generateReport(FileName, Month, Year);
		return Config.SERVER_PATH.replaceFirst("applications/", "") + "reports/" + FileName;
	}
	/*public static void main(String[] args)
	{
		TransactionLogger logger = new TransactionLogger();
		Config.loadConfiguration();
		String[] test = new String[3];
		test[0] = "input msg1";
		test[1] = "input msg2";
		test[2] = "output msg";
		int ID = logger.newTransaction(1);
		logger.setTransactionCompleted(ID, "Test return");
		String testurl = logger.report(8, 2012);
		System.out.println(testurl);
	}*/
}
