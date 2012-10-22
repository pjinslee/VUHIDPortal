package vuhid_tools.server;

import vuhid_tools.shared.Patient;
import java.util.ArrayList;

/**
 * @author Long Phan <long2@pdx.edu>
 * This class is the central class.
 * It implements the PIXInterface, the PDQInterface, and the VUHIDSenderInterface.
 */
public class VUHIDPortal implements PIXInterface, PDQInterface, VUHIDSenderInterface
{
   private PDQInterface PDQService = null;
   private PIXInterface PIXService = null;
   private VUHIDSender VSender = null;
   private TransactionLogger logger = new TransactionLogger();

   /**
    * Constructor, initializes the objects.
    */
   public VUHIDPortal()
   {
	  Config.loadConfiguration();
      EMPI empi = new EMPI();
      PDQService = empi;
      PIXService = empi;
      VSender = new VUHIDSender();
   }
   
   /**
	* Generate a report and returns its URL location part.
	* 
	* @param Month		The (integer) month that the report will be generated on.
	* @param Year		The (integer) year that the report will be generated on.
	* @return			Returns a part of the report file URL location.
	*/
   public String Report(int Month, int Year)
   {
      return logger.report(Month, Year);
   }

   /**
	* Edits an existing Patient's information.
	* 
	* @param P			Patient's new information.
	* @return			Returns a boolean, true on success false otherwise.
	*/
   public boolean PatientRegistryRecordRevised(Patient P)
   {
	  int TransactionID = logger.newTransaction(TransactionLogger.PIXPatientRegistryRecordRevised);
	  boolean result = PIXService.PatientRegistryRecordRevised(P);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Adds a Patient to the ArrayList P_List.
	* 
	* @param P			The patient to be added.
	* @return			Returns a boolean, true on success false otherwise.
	*/
   public boolean PatientRegistryRecordAdded(Patient P)
   {
      int TransactionID = logger.newTransaction(TransactionLogger.PIXPatientRegistryRecordAdded);
	  boolean result = PIXService.PatientRegistryRecordAdded(P);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Gets all of the VUHID IDs belonging to a Patient P.
	* 
	* @param P			Demographic info of the Patient.
	* @return			An ArrayList of strings containing all of the Patient's IDs.
	*/
   public ArrayList<String> PatientRegistryGetIdentifiersQuery(Patient P)
   {
      int TransactionID = logger.newTransaction(TransactionLogger.PIXPatientRegistryGetIdentifiersQuery);
	  ArrayList<String> result = PIXService.PatientRegistryGetIdentifiersQuery(P);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Takes two patients, takes all instances of P1 in P_list and replaces it with P2.
	* 
	* @param P1			The duplicate patient to be removed.
	* @param P2			Patient information that will replace P1.
	* @return			Returns true if any changes were made, false otherwise.
	*/
   public boolean PatientRegistryDuplicatesResolved(Patient P1, Patient P2)
   {
	  int TransactionID = logger.newTransaction(TransactionLogger.PIXPatientRegistryDuplicatesResolved);
	  boolean result = PIXService.PatientRegistryDuplicatesResolved(P1, P2);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Searches for any patients matching P.
	* 
	* @param P			A patient containing demographic to search on.
	* @return			An ArrayList of patients that match demographic information.
	*/
   public ArrayList<Patient> PatientRegistryFindCandidatesQuery(Patient P)
   {
	  String[] values = new String[1];
	  values[0] = P.VUHID_ID;
      int TransactionID = logger.newTransaction(TransactionLogger.PDQPatientRegistryFindCandidatesQuery, values);
	  ArrayList<Patient> result = PDQService.PatientRegistryFindCandidatesQuery(P);
	  logger.setTransactionCompleted(TransactionID);
      return result;
   }

   /**
	* Connect to VUHID's server and get a new OVID.
	* 
	* @return			Returns the server's respond string.
	*/
   public String getNewOVID() throws Exception
   {
      int TransactionID = logger.newTransaction(TransactionLogger.VUHIDNewOVID);
	  String result = VSender.getNewOVID();
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Connect to VUHID's server and get a new PVID.
	* 
	* @param privacyClass		The (String) privacy class of the ID.
	* @return					Returns the server's respond string.
	*/
   public String getNewPVID(String privacyClass) throws Exception
   {
	  String[] values = new String[1];
	  values[0] = privacyClass;
      int TransactionID = logger.newTransaction(TransactionLogger.VUHIDNewPVID, values);
	  String result = VSender.getNewPVID(privacyClass);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Connect to VUHID's server and get the status of an ID.
	* 
	* @param ID			The (String) ID in question.
	* @return			Returns the server's respond string.
	*/
   public int getStatusOfID(String ID) throws Exception
   {
	  String[] values = new String[1];
	  values[0] = ID;
      int TransactionID = logger.newTransaction(TransactionLogger.VUHIDIDStatus, values);
	  int result = VSender.getStatusOfID(ID);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Connect to VUHID's server and request the retirement of an ID.
	* 
	* @param ID			The (String) ID in question.
	* @param reason		The (String) reason for the retirement.
	* @return			Returns the server's respond integer.
	*/
   public int retireID(String ID, String reason) throws Exception
   {
	  String[] values = new String[2];
	  values[0] = ID;
	  values[1] = reason;
      int TransactionID = logger.newTransaction(TransactionLogger.VUHIDRetireID, values);
	  int result = VSender.retireID(ID, reason);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Connect to VUHID's server and request the termination of an ID.
	* 
	* @param ID			The (String) ID in question.
	* @param reason		The (String) reason for the termination.
	* @return			Returns the server's respond integer.
	*/
   public int terminateID(String ID, String reason) throws Exception
   {
      String[] values = new String[2];
	  values[0] = ID;
	  values[1] = reason;
      int TransactionID = logger.newTransaction(TransactionLogger.VUHIDTerminateID, values);
	  int result = VSender.terminateID(ID, reason);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Connect to VUHID's server and request the replacement of an ID.
	* 
	* @param ID			The (String) ID in question.
	* @param reason		The (String) reason for the replacement.
	* @return			Returns the server's respond string.
	*/
   public String getReplacementID(String ID, String reason) throws Exception
   {
      String[] values = new String[2];
	  values[0] = ID;
	  values[1] = reason;
      int TransactionID = logger.newTransaction(TransactionLogger.VUHIDReplaceID, values);
	  String result = VSender.getReplacementID(ID, reason);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Connect to VUHID's server and request the data locations list of an ID.
	* 
	* @param ID			The (String) ID in question.
	* @return			Returns the (array of String) data locations.
	*/
   public String[] getDataLocations(String ID) throws Exception
   {
      String[] values = new String[1];
	  values[0] = ID;
      int TransactionID = logger.newTransaction(TransactionLogger.VUHIDRequestDataLocations, values);
	  String[] result = VSender.getDataLocations(ID);
	  logger.setTransactionCompleted(TransactionID, result);
      return result;
   }

   /**
	* Check if the ID were an OVID or not.
	* 
	* @param ID			The (String) ID in question.
	* @return			Returns respond boolean.
	*/
   public boolean getIsOVID(String ID) throws Exception
   {
      return VSender.getIsOVID(ID);
   }

   /**
	* Check if the ID were a PVID or not.
	* 
	* @param ID			The (String) ID in question.
	* @return			Returns respond boolean.
	*/
   public boolean getIsPVID(String ID) throws Exception
   {
      return VSender.getIsPVID(ID);
   }

   /**
	* Check if the ID were valid or not.
	* 
	* @param ID			The (String) ID in question.
	* @return			Returns respond boolean.
	*/
   public boolean getIsWellFormed(String ID) throws Exception
   {
      return VSender.getIsWellFormed(ID);
   }

   /**
	* Get the privacy class of an ID.
	* 
	* @param ID			The (String) ID in question.
	* @return			Returns respond privacy class string.
	*/
   public String getPrivacyClass(String ID) throws Exception
   {
      return VSender.getPrivacyClass(ID);
   }
}
