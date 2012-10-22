/* VUHIDPortal.java: Sent from Peter to Teja via email at about 1:15am on Saturday, 2012-08-18. It includes modifications
 * made by Peter to the original version to account for the new "EMPI" class that Damon made. (Basically, eliminated PIX
 * and PDQ classes, and replaced with single EMPI class, and then changed methods to return ArrayLists instead of arrays.)
 * All comments were added by Peter. */

/*
package vuhidtools;
import java.util.ArrayList;

public class VUHIDPortal implements PIXInterface, PDQInterface, VUHIDSenderInterface {

   private EMPI EMPIService = null;
   private VUHIDSender VSender = null;

   public VUHIDPortal() {
      EMPIService = new EMPI();
      VSender = new VUHIDSender();
   }

   public boolean PatientRegistryRecordRevised(Patient P) { // TODO: maybe should throw exception here too? See 'EMPI.java'.
      return EMPIService.PatientRegistryRecordRevised(P);
   }

   public boolean PatientRegistryRecordAdded(Patient P) { // TODO: maybe should throw exception here too? See 'EMPI.java'.
      return EMPIService.PatientRegistryRecordAdded(P);
   }

   public ArrayList<String> PatientRegistryGetIdentifiersQuery(Patient P) { // TODO: maybe should throw exception here too? See 'EMPI.java'.
      return EMPIService.PatientRegistryGetIdentifiersQuery(P);
   }

   public boolean PatientRegistryDuplicatesResolved(Patient P1, Patient P2) { // TODO: maybe should throw exception here too? See 'EMPI.java'.
      return EMPIService.PatientRegistryDuplicatesResolved(P1, P2);
   }

   public ArrayList<Patient> PatientRegistryFindCandidatesQuery(Patient P) { // TODO: maybe should throw exception here too? See 'EMPI.java'.
      return EMPIService.PatientRegistryFindCandidatesQuery(P);
   }

   public String getNewOVID() throws Exception { // VUHID TRANSACTION #1
      return VSender.getNewOVID();
   }

   public String getNewPVID(String privacyClass) throws Exception { // VUHID TRANSACTION #2
      return VSender.getNewPVID(privacyClass);
   }

   public int getStatusOfID(String ID) throws Exception { // VUHID TRANSACTION #3
      return VSender.getStatusOfID(ID);
   }

   public int retireID(String ID, String reason) throws Exception { // VUHID TRANSACTION #4
      return VSender.retireID(ID, reason);
   }

   public int terminateID(String ID, String reason) throws Exception { // VUHID TRANSACTION #5
      return VSender.terminateID(ID, reason);
   }

   public String getReplacementID(String ID, String reason) throws Exception { // VUHID TRANSACTION #6
      return VSender.getReplacementID(ID, reason); 
   }

   public String[] getDataLocations(String ID) throws Exception { // VUHID TRANSACTION #7
      return VSender.getDataLocations(ID);
   }

   public boolean getIsOVID(String ID) throws Exception { // helper classes, not even implemented yet, and not so important to GUI
      return VSender.getIsOVID(ID);
   }

   public boolean getIsPVID(String ID) throws Exception { // helper classes, not even implemented yet, and not so important to GUI
      return VSender.getIsPVID(ID);
   }

   public boolean getIsWellFormed(String ID) throws Exception { // helper classes, not even implemented yet, and not so important to GUI
      return VSender.getIsWellFormed(ID);
   }

   public String getPrivacyClass(String ID) throws Exception { // helper classes, not even implemented yet, and not so important to GUI
      return VSender.getPrivacyClass(ID);
   }
}
*/