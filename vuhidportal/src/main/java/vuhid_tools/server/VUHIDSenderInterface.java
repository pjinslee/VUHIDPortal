package vuhid_tools.server;

/**
 * @author Robert Hickey
 *
 * Comments:    ver 1.0 First version from Team
 *              ver 1.2 Changed getNewPVID Interface as per VuHID Transaction doc
 *                      Changed getReplacement Interface as per VuHID Transaction doc
 *                      Changed getDataLocations Interface as per VuHID Transaction doc
 *
 */

/*
 * VUHIDSender.java: Basic outline of interface for sending requests to VUHID.
*/
public interface VUHIDSenderInterface {

    public String   getNewOVID() throws Exception;

    //changed interface orig Team version to match VuHID Transaction doc
    //public String   getNewPVID() throws Exception;
    public String   getNewPVID(String privacyClass) throws Exception;

    public int      getStatusOfID(String ID) throws Exception;
    public int      retireID(String ID, String reason) throws Exception;
    public int      terminateID(String ID, String reason) throws Exception;



    //public String   getReplacementID() throws Exception;
    public String getReplacementID(String idToReplace, String reason) throws Exception;


    //changed interface orig Team version to match VuHID Transaction doc
    //public String[] getDataLocations(String ID, String response_uri) throws Exception;
    public String[] getDataLocations(String idToSearchFor) throws Exception;

    public boolean  getIsOVID(String ID) throws Exception;
    public boolean  getIsPVID(String ID) throws Exception;
    public boolean  getIsWellFormed(String ID) throws Exception;
    public String   getPrivacyClass(String ID) throws Exception;
};
