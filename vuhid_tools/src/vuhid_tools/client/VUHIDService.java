package vuhid_tools.client;

import java.util.ArrayList;

import vuhid_tools.shared.Patient;






import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("VUHIDPortal")
public interface VUHIDService extends RemoteService {
	//String greetServer(String name) throws IllegalArgumentException;
	

	
	boolean PatientRegistryRecordRevised(Patient P);

	 boolean PatientRegistryRecordAdded(Patient P);

	 ArrayList<String> PatientRegistryGetIdentifiersQuery(Patient P);

	 boolean PatientRegistryDuplicatesResolved(Patient P1, Patient P2);

	 ArrayList<Patient> PatientRegistryFindCandidatesQuery(Patient P);

	String getNewOVID() throws Exception;

	 String getNewPVID(String privacyClass)
			throws Exception;

	 int getStatusOfID(String ID) throws Exception;

	 int retireID(String ID, String reason)
			throws Exception;

	 int terminateID(String ID, String reason)
			throws Exception;

	 String getReplacementID(String ID, String reason)
			throws Exception;

	 String[] getDataLocations(String ID) throws Exception;

	 boolean getIsOVID(String ID) throws Exception;

	 boolean getIsPVID(String ID) throws Exception;

	 boolean getIsWellFormed(String ID) throws Exception;

	 String getPrivacyClass(String ID) throws Exception;
	 
	 String report(int month, int year);
	
}
