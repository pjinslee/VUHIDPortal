package vuhid_tools.client;

import java.util.ArrayList;

import vuhid_tools.shared.Patient;







import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface VUHIDServiceAsync {
	/*
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	
	*/
	void PatientRegistryRecordRevised(Patient P, AsyncCallback<Boolean> callback);
	
	void PatientRegistryRecordAdded(Patient P, AsyncCallback<Boolean> callback);
	
	void PatientRegistryGetIdentifiersQuery(Patient P, AsyncCallback<ArrayList<String>> callback);
	
	void PatientRegistryDuplicatesResolved(Patient P1, Patient P2, AsyncCallback<Boolean> callback);
	
	void PatientRegistryFindCandidatesQuery(Patient P, AsyncCallback<ArrayList<Patient>> callback);
	
	void getNewOVID(AsyncCallback<String> callback) throws Exception;
	
	void getNewPVID(String privacyClass, AsyncCallback<String> callback) throws Exception;
	
	void getStatusOfID(String ID, AsyncCallback<Integer> callback) throws Exception;
	
	void retireID(String ID, String reason, AsyncCallback<Integer> callback) throws Exception;
	
	void terminateID(String ID, String reason, AsyncCallback<Integer> callback) throws Exception;
	
	void getReplacementID(String ID, String reason, AsyncCallback<String> callback) throws Exception;
	
	void getDataLocations(String ID, AsyncCallback<String[]> callback) throws Exception;

	void getIsOVID(String ID, AsyncCallback<Boolean> callback) throws Exception;
	
	void getIsPVID(String ID, AsyncCallback<Boolean> callback) throws Exception;
	
	void getIsWellFormed(String ID, AsyncCallback<Boolean> callback) throws Exception;
	
	void getPrivacyClass(String ID, AsyncCallback<String> callback) throws Exception;

	void report(int month, int year, AsyncCallback<String> callback);

	
	
}
