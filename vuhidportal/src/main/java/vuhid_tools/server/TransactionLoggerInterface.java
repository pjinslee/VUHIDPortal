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

import java.util.ArrayList;
 /**
 * @author Long Phan <long2@pdx.edu>
 * Interface for TransactionLogger.
 * newTransaction: type - VUHID transaction number from 1 to 14; previous_transaction - ID of the previous related transaction,
 * 				there also an overloaded function which does not take previous_transaction; values is the list of inputs (from left to right) then the output,
 * 				for example VUHID transaction 5: values[0] is the VUHID ID, values[1] is the reason, values[2] is the returned status code.
 * 				Returns the transaction row ID (in database).
 * 					cases 1, 10, 11, 12, 13 (New OVID, PIX PatientRegistryRecordRevised, PIX PatientRegistryRecordAdded, PIX PatientRegistryGetIdentifiersQuery, PIX PatientRegistryDuplicatesResolved) do not have an input value.
 * 					cases 2, 3, 6, 14 (New PVID, VUHID ID Status, Request data locations, PDQ PatientRegistryFindCandidatesQuery) take 1 input value (values[0] is the PC or the VUHID ID).
 * 					cases 4, 5, 7 (Retire ID, Terminate ID, Replace ID) take 2 input values (values[0] is the VUHID ID, values[1] is the reason)
 * 					case 8 (VUHID Retirement/Termination Notice) takes 5 input values (values[0] is the VUHID ID, values[1] is the action taken, values[2] is the URL of the EMPI that has requested the change, values[3] is the time stamp, values[4] is the reason)
 * 					case 9 (VUHID Replacement Notice) takes 5 input values (values[0] is the old VUHID ID, values[1] is the new VUHID ID, values[2] is the URL of the EMPI that has requested the change, values[3] is the time stamp, values[4] is the reason)
 * setTransactionCompleted: set the input transaction row ID's (ID is returned by log) status to Completed, and also takes the return value if available
 * report: generate the report Excel file, FileLocation must contain file name and the extension e.g. "C:\\Test.xls", Month and Year are the month and year that user specifies.
 */
public interface TransactionLoggerInterface
{
	public int newTransaction(int type, String previous_transaction, String[] values); // type - transaction number from 1 to 14, previous_transaction - ID of the previous related transaction,
	public int newTransaction(int type, String[] values);
	public int newTransaction(int type, String previous_transaction);
	public int newTransaction(int type);
	public void setTransactionCompleted(int ID, String return_value);
	public void setTransactionCompleted(int ID, String[] return_value);
	public void setTransactionCompleted(int ID, ArrayList<String> return_value);
	public void setTransactionCompleted(int ID, boolean return_value);
	public void setTransactionCompleted(int ID, int return_value);
	public void setTransactionCompleted(int ID);
	public String report(int Month, int Year);
}
