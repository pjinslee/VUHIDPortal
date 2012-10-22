package vuhid_tools.server;

import java.util.ArrayList;

import vuhid_tools.client.VUHIDService;
import vuhid_tools.shared.Patient;
import vuhid_tools.server.VUHIDPortal;





import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class VUHIDServiceImpl extends RemoteServiceServlet implements
		VUHIDService {
	
	private VUHIDPortal vp;
	
	public VUHIDServiceImpl()
	{
		vp = new VUHIDPortal();
				
	}
	
	
	

/*
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}
	*/

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	
	/*
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
*/
	
	
	public String getNewOVID() throws Exception {
		
		return vp.getNewOVID();
		
	}

	
	public boolean PatientRegistryRecordRevised(Patient P)
	{

		return vp.PatientRegistryRecordRevised(P);
	}

	
	public boolean PatientRegistryRecordAdded(Patient P)
    {
		return vp.PatientRegistryRecordAdded(P);

	}

	
	public ArrayList<String> PatientRegistryGetIdentifiersQuery(Patient P)
    {
		
		return vp.PatientRegistryGetIdentifiersQuery(P);
	}

	
	public boolean PatientRegistryDuplicatesResolved(Patient P1, Patient P2)
    {
		
		return vp.PatientRegistryDuplicatesResolved(P1, P2);
	}

	
	public ArrayList<Patient> PatientRegistryFindCandidatesQuery(Patient P)
    {
		
		return vp.PatientRegistryFindCandidatesQuery(P);

	}

	@Override
	public String getNewPVID(String privacyClass)
			throws Exception {
		return vp.getNewPVID(privacyClass);
	}

	@Override
	public int getStatusOfID(String ID) throws Exception {
		return vp.getStatusOfID(ID);
	}

	@Override
	public int retireID(String ID, String reason)
			throws Exception {
		return vp.retireID(ID, reason);
	}

	@Override
	public int terminateID(String ID, String reason)
			throws Exception {
		// TODO Auto-generated method stub
		return vp.terminateID(ID, reason);
	}

	@Override
	public String getReplacementID(String ID, String reason)
			throws Exception {
		
		return vp.getReplacementID(ID, reason);
	}

	@Override
	public String[] getDataLocations(String ID) throws Exception {

		return vp.getDataLocations(ID);
	}

	@Override
	public boolean getIsOVID(String ID) throws Exception {

		return vp.getIsOVID(ID);
	}

	@Override
	public boolean getIsPVID(String ID) throws Exception {

		return vp.getIsPVID(ID);
	}

	@Override
	public boolean getIsWellFormed(String ID) throws Exception {

		return vp.getIsWellFormed(ID);
	}

	@Override
	public String getPrivacyClass(String ID) throws Exception {

		return vp.getPrivacyClass(ID);
	}


	@Override
	public String report(int month, int year)  {
		
		return vp.Report(month, year);
		
	}





	
	
	
	
	
	
}
