package vuhid_tools.server;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * This class is the basic communication class to the VuHID server
 * @author Robert Hickey
 * @version 1.4
 *
 *  Ver 1.1     First version that implements stub methods for team.
 *      1.2     Added state final vars for Configuration and AuthenticationManager
 *              Added code to getStatusOfID from Peter to make it real
 *      1.3     Got getNewOVID working with POST method
 *      1.4     Got getNewPVID working
 *              Changed Interface to getNewPVID routine; see below for changes
 *              Changed Interface to getDataLocations routine; see below for changes
 *              Changed Interface to getReplacementID routine; see below for changes
 *              Changed Interface to retireID routine; see below for changes
 *              Changed rev of Interface as well
 *              Walled off the print routines in methods => if(debug=true)
 *      1.5     Forgot "throws Exception" on getReplacementID => added here and in Interface
 *              Got terminateID working
 *      1.6     Got getDataLocations working with "200" SUCCESS but no return URLs
 *              wrote getReplacementID but still VUHID server says is not valid
 *
 */


/**
 * This class implements the VuHIDSender Interface with methods to implement specified Transactions with
 * VuHID Server.
 */
public class VUHIDSender implements VUHIDSenderInterface{

    //Configuration file passed to AuthenticationManager with many setup parameters
    private static final AuthenticationManager am = new AuthenticationManager(null);

    boolean debug = true;

    /**
     *
     * @return              Returns new requested OVID from VuHID Server
     * @throws Exception
     */
    public String getNewOVID() throws Exception {

        //number of new OVID's requested
        String count = "1";

        //String array to hold return lines from VuHID Server
        String[] responseStrings = new String[Integer.parseInt(count)];


        HttpsURLConnection connection = null;  //define here so I can return part of it outside try
        try {
            // create key and trust managers, then initialize the ssl context with their data
            SSLSocketFactory factory = am.getSSLSocketFactory();

            // communicate with the server
            // new ovid POST to /new
            URL url = new URL("https://" + Config.vuhidServerHostName + "/new" );


            connection = (HttpsURLConnection) url.openConnection();
            //connection.setRequestMethod("GET");
            connection.setRequestMethod("POST");

            //added
            connection.setDoOutput(true);

            connection.setRequestProperty("From", Config.fromHeaderValue);
            connection.setRequestProperty("User-Agent", Config.userAgentHeaderValue);
            connection.setRequestProperty("Host", Config.vuhidServerHostName);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Length", "32");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //added as per Peter's SSLSocketClientWithClientAuth.java example


            //needs to be here; after set connection; before OutputStreamWriter
            connection.setSSLSocketFactory(factory);

            //modified from GET request
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

            //out.write("count=\"1\""); // Remove this for GET requests, or change as needed
            out.write("count=" + count); // Remove this for GET requests, or change as needed
            out.close();


            if (debug) {
                System.out.println("RESPONSE CODE: " + connection.getResponseCode());
                System.out.println("RESPONSE MESSAGE: " + connection.getResponseMessage());
                System.out.println("RETURN MESSAGE: " + connection.getContent().toString());
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            int index = 0;
            while ( (inputLine = in.readLine()) != null){
                if (index < Integer.parseInt(count)) {
                    responseStrings[index] = inputLine;
                }
                index++;
                if (debug) {
                    System.out.println(inputLine);
                }
            }
            in.close();


            //finished, print/output results if debug true
            if (debug) {
                System.out.println("RESPONSE HEADERS:");
                for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                    System.out.println("\t" + header.getKey() + ": " + header.getValue());
                }
                System.out.println("DONE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStrings[0];

    }


    /**
     *
     * @param privacyClass  Passed in 7 digit ASCII encoded string for requested Privacy Class of new ID
     * @return              Returns new PVID from VuHID Server
     * @throws Exception
     */
    public String getNewPVID(String privacyClass) throws Exception {

        //number of new PVID's requested
        String count = "1";

        //String array to hold return lines from VuHID Server
        String[] responseStrings = new String[Integer.parseInt(count)];


        HttpsURLConnection connection = null;  //define here so I can return part of it outside try
        try {
            // create key and trust managers, then initialize the ssl context with their data
            SSLSocketFactory factory = am.getSSLSocketFactory();

            // communicate with the server
            // new pvid POST to /new/privacyClass where last part is passed in 7 digit ASCII string
            // as per email Barry Hieb; test Privacy Class of 4000000 is OK
            URL url = new URL("https://" + Config.vuhidServerHostName + "/new/" + privacyClass );


            connection = (HttpsURLConnection) url.openConnection();
            //connection.setRequestMethod("GET");
            connection.setRequestMethod("POST");

            //added
            connection.setDoOutput(true);

            connection.setRequestProperty("From", Config.fromHeaderValue);
            connection.setRequestProperty("User-Agent", Config.userAgentHeaderValue);
            connection.setRequestProperty("Host", Config.vuhidServerHostName);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Length", "32");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //added as per Peter's SSLSocketClientWithClientAuth.java example


            //needs to be here; after set connection; before OutputStreamWriter
            connection.setSSLSocketFactory(factory);

            //modified from GET request
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

            //out.write("count=\"1\""); // Remove this for GET requests, or change as needed
            out.write("count=" + count); // Remove this for GET requests, or change as needed
            out.close();


            if (debug) {
                System.out.println("RESPONSE CODE: " + connection.getResponseCode());
                System.out.println("RESPONSE MESSAGE: " + connection.getResponseMessage());
                System.out.println("RETURN MESSAGE: " + connection.getContent().toString());
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            int index = 0;
            while ( (inputLine = in.readLine()) != null){
                if (index < Integer.parseInt(count)) {
                    responseStrings[index] = inputLine;
                }
                index++;
                if (debug) {
                    System.out.println(inputLine);
                }
            }
            in.close();


            //finished, print/output results:
            if (debug) {
                System.out.println("RESPONSE HEADERS:");
                for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                    System.out.println("\t" + header.getKey() + ": " + header.getValue());
                }
                System.out.println("DONE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStrings[0];

    }

    public int getStatusOfID(String ID) throws Exception {
        int test = 0;

        HttpsURLConnection connection = null;  //define here so I can return part of it outside try
        try {
            // create key and trust managers, then initialize the ssl context with their data
            SSLSocketFactory factory = am.getSSLSocketFactory();

            // communicate with the server
            URL url = new URL("https://" + Config.vuhidServerHostName + "/verify/" + ID);

            //define connection outside of try block so can return some of it as string
            connection = (HttpsURLConnection) url.openConnection();

            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("From", Config.fromHeaderValue);
            connection.setRequestProperty("User-Agent", Config.userAgentHeaderValue);
            connection.setRequestProperty("Host", Config.vuhidServerHostName);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Length", "32");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setSSLSocketFactory(factory);

            if (debug) {
                System.out.println("RESPONSE CODE: " + connection.getResponseCode());
                System.out.println("RESPONSE MESSAGE: " + connection.getResponseMessage());
                System.out.println("RESPONSE HEADERS:");
                for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                    System.out.println("\t" + header.getKey() + ": " + header.getValue());
                }
                System.out.println("DONE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection.getResponseCode();
    }

    /**
     *
     * @param ID        The OVID or PVID ID (String) to be retired.
     * @param reason    The (String) reason to retire the ID.
     * @return          Returned is the HTTP status code from the VuHID Server; should be 200 for success
     * @throws Exception
     */
    public int retireID(String ID, String reason) throws Exception {

        HttpsURLConnection connection = null;  //define here so I can return part of it outside try
        try {
            // create key and trust managers, then initialize the ssl context with their data
            SSLSocketFactory factory = am.getSSLSocketFactory();

            // communicate with the server
            URL url = new URL("https://" + Config.vuhidServerHostName + "/retire/" + ID );


            connection = (HttpsURLConnection) url.openConnection();
            //connection.setRequestMethod("GET");
            connection.setRequestMethod("POST");

            //added
            connection.setDoOutput(true);

            connection.setRequestProperty("From", Config.fromHeaderValue);
            connection.setRequestProperty("User-Agent", Config.userAgentHeaderValue);
            connection.setRequestProperty("Host", Config.vuhidServerHostName);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Length", "32");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //added as per Peter's SSLSocketClientWithClientAuth.java example


            //needs to be here; after set connection; before OutputStreamWriter
            connection.setSSLSocketFactory(factory);

            //modified from GET request
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

            out.write("retirement_explanation=" + reason); // Remove this for GET requests, or change as needed
            out.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            if (debug) {
            System.out.println("RESPONSE CODE: " + connection.getResponseCode());
            System.out.println("RESPONSE MESSAGE: " + connection.getResponseMessage());
            System.out.println("RETURN MESSAGE: " + connection.getContent().toString());

            String inputLine;

                while ( (inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                }
            }

            in.close();


            //finished, print/output results:
            if (debug) {
                System.out.println("RESPONSE HEADERS:");
                for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                    System.out.println("\t" + header.getKey() + ": " + header.getValue());
                }
                System.out.println("DONE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (connection.getResponseCode());

    }

    public int terminateID(String ID, String reason) throws Exception {

        HttpsURLConnection connection = null;  //define here so I can return part of it outside try
        try {
            // create key and trust managers, then initialize the ssl context with their data
            SSLSocketFactory factory = am.getSSLSocketFactory();

            // communicate with the server
            //URL url = new URL("https://" + config.getVuhidServerHostName() + "/retire/" + ID );
            URL url = new URL("https://" + Config.vuhidServerHostName + "/terminate/" + ID );


            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            //added
            connection.setDoOutput(true);

            connection.setRequestProperty("From", Config.fromHeaderValue);
            connection.setRequestProperty("User-Agent", Config.userAgentHeaderValue);
            connection.setRequestProperty("Host", Config.vuhidServerHostName);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Length", "32");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            //needs to be here; after set connection; before OutputStreamWriter
            connection.setSSLSocketFactory(factory);

            //modified from GET request
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

            out.write("termination_explanation=" + reason); // Remove this for GET requests, or change as needed
            out.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            if (debug) {
                System.out.println("RESPONSE CODE: " + connection.getResponseCode());
                System.out.println("RESPONSE MESSAGE: " + connection.getResponseMessage());
                System.out.println("RETURN MESSAGE: " + connection.getContent().toString());

                String inputLine;

                while ( (inputLine = in.readLine()) != null){
                    System.out.println(inputLine);
                }
            }

            in.close();


            //finished, print/output results:
            if (debug) {
                System.out.println("RESPONSE HEADERS:");
                for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                    System.out.println("\t" + header.getKey() + ": " + header.getValue());
                }
                System.out.println("DONE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (connection.getResponseCode());
    }

    //chg'd Interface to match VuHID Transaction doc
    //public String getReplacementID() throws Exception {
    public String getReplacementID(String idToReplace, String reason) {

        int count = 10; //fixed size for String[] to read back VuHID Server response
        String[] responseStrings = new String[count];


        HttpsURLConnection connection = null;  //define here so I can return part of it outside try
        try {
            // create key and trust managers, then initialize the ssl context with their data
            SSLSocketFactory factory = am.getSSLSocketFactory();

            // communicate with the server
            //URL url = new URL("https://" + config.getVuhidServerHostName() + "/retire/" + ID );
            URL url = new URL("https://" + Config.vuhidServerHostName + "/replace/" + idToReplace );

            if(debug) {
                System.out.println("URL is: " + url);
                System.out.println("idToReplace is: " + idToReplace);
                System.out.println("reason is: " + reason);
            }


            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            //added
            connection.setDoOutput(true);

            connection.setRequestProperty("From", Config.fromHeaderValue);
            connection.setRequestProperty("User-Agent", Config.userAgentHeaderValue);
            connection.setRequestProperty("Host", Config.vuhidServerHostName);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Length", "32");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            //needs to be here; after set connection; before OutputStreamWriter
            connection.setSSLSocketFactory(factory);

            //modified from GET request
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

            out.write("replacement_explanation=" + reason); // Remove this for GET requests, or change as needed
            out.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            if (debug) {
                System.out.println("RESPONSE CODE: " + connection.getResponseCode());
                System.out.println("RESPONSE MESSAGE: " + connection.getResponseMessage());
                System.out.println("RETURN MESSAGE: " + connection.getContent().toString());

            }

            String inputLine;
            int index = 0;
            while ( (inputLine = in.readLine()) != null){
                if (index < 10) {
                    responseStrings[index] = inputLine;
                }
                index++;
                if (debug) {
                    System.out.println(inputLine);
                }
            }

            in.close();


            //finished, print/output results:
            if (debug) {
                System.out.println("RESPONSE HEADERS:");
                for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                    System.out.println("\t" + header.getKey() + ": " + header.getValue());
                }
                System.out.println("DONE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStrings[0];
    }

    //chg'd Interface to match VuHID Transaction doc
    //public String[] getDataLocations(String ID, String response_uri) throws Exception {
    public String[] getDataLocations(String idToSearchFor) throws Exception {
        //String[] listOfReturnURLs = {"1", "2", "3", "4"};
        String[] listOfReturnURLs = new String[50];
        //return listOfReturnURLs;

        HttpsURLConnection connection = null;  //define here so I can return part of it outside try
        try {
            // create key and trust managers, then initialize the ssl context with their data
            SSLSocketFactory factory = am.getSSLSocketFactory();

            // communicate with the server
            //URL url = new URL("https://" + config.getVuhidServerHostName() + "/retire/" + ID );
            URL url = new URL("https://" + Config.vuhidServerHostName + "/dlr/" + idToSearchFor );


            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            //added
            connection.setDoOutput(true);

            connection.setRequestProperty("From", Config.fromHeaderValue);
            connection.setRequestProperty("User-Agent", Config.userAgentHeaderValue);
            connection.setRequestProperty("Host", Config.vuhidServerHostName);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Length", "32");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            //needs to be here; after set connection; before OutputStreamWriter
            connection.setSSLSocketFactory(factory);

            //modified from GET request
            //OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

            //out.write("termination_explanation=" + reason); // Remove this for GET requests, or change as needed
            //out.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            if (debug) {
                System.out.println("RESPONSE CODE: " + connection.getResponseCode());
                System.out.println("RESPONSE MESSAGE: " + connection.getResponseMessage());
                System.out.println("RETURN MESSAGE: " + connection.getContent().toString());
            }

            String inputLine;
            int index = 0;
            while ( (inputLine = in.readLine()) != null){
                if (index < 50) {
                    listOfReturnURLs[index] = inputLine;
                }
                index++;
                if (debug) {
                    System.out.println(inputLine);
                }
            }

            in.close();


            //finished, print/output results:
            if (debug) {
                System.out.println("RESPONSE HEADERS:");
                for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                    System.out.println("\t" + header.getKey() + ": " + header.getValue());
                }
                System.out.println("DONE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfReturnURLs;

    }

    public boolean getIsOVID(String ID) throws Exception {
        return true;
    }

    public boolean getIsPVID(String ID) throws Exception {
        return true;
    }

    public boolean getIsWellFormed(String ID) throws Exception {
        return true;
    }

    public String getPrivacyClass(String ID) throws Exception {
        String prefix = "0123456789012345"; //16 digits for prefix
        String checkDigits = "01234567";    //8 check digits
        String privacyClass = "0000000";    //OVID has 7 all zero privacy class digits
        //String test = "0123456789012345.012345670000000" ;
        String test = prefix + "." + checkDigits + privacyClass;
        return privacyClass;
    }
}