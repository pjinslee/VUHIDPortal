package vuhid_tools.server;

/**
 * Author: Robert Hickey
 * Date: 8/11/12
 * @version 1.2
 * Comments:    Ver 1.0 First version of Peter's work
 *                  1.1 Moved code to validate ID into getStatusID routine of VUHIDSender.java
 *                      Tested with old code here commented out and works as before
 *                  1.2 Added routine to test getNewOVID routine
 *                      Added routine to test getNewPVID routine
 *                      Added routine to test retireID routine
 *                  1.3 added routine to test terminateID
 *                  1.4 added routine to test getReplacementID
 *                      added routine to test getDataLocations
 *
 */
public class VUHIDPortalPrototype {

    //add new VuHID Sender to test methods
    private static final VUHIDPortal vuhidsend = new VUHIDPortal();

    //UNCOMMENT ROUTINES BELOW TO TEST EACH VUHID TRANSACTION
    //MAKE SURE THAT AN ID IS GENERATED FIRST BEFORE ROUTINE THAT RETIRES/TERMINATES IT IS CALLED
    //IDs ARE HARDCODED IN MANY OF THE RETIRE/TERMINATE/STATUS ROUTINES

/*    public static void main(String[] args) {

        //section to test getStatusOfID routine
        int temp1 = 0;
        try {
            temp1 = vuhidsend.getStatusOfID("0000000409177781.095215510000000");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nResponse code for check of ID should be 200; is: " + temp1);


        //section to test getNewOVID routine
        String temp2 = null;
        try {
            temp2 = vuhidsend.getNewOVID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nResponse from getNewOVID routine: " + temp2);


        //section to test getNewPVID routine
        String temp3 = null;
        try {
            temp3 = vuhidsend.getNewPVID("4000000");     //as per email "4000000" is good for test PC
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nResponse from getNewPVID routine: " + temp3);


        //section to test retireID routine
        String reason4 = "PVID was used by CsTheDay only for software testing.";
        String ID4 = "0000000000000008.023042694000000"; //just generated this in getNewPVID above
        int temp4 = 0;
        try {
            temp4 = vuhidsend.retireID(ID4, reason4 );
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("HTTP Response from retireID routine, should be 200: " + temp4);


        //section to test terminateID routine
        String reason5 = "OVID was used by CsTheDay only for software testing.";
        String ID5 = "0000000000000009.523079864000000"; //just generated this in getNewPVID above
        int temp5 = 0;
        try {
            temp5 = vuhidsend.terminateID(ID5, reason5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("HTTP Response from terminateID routine, should be 200: " + temp5);


        //section to test getReplacementID routine
        String temp6 = null;
        String reason6 = "OVID was used by CsTheDay only for software testing.";
        String ID6 = "0000000409177781.095215510000000"; //just generated this in getNewPVID above
        try {
            temp6 = vuhidsend.getReplacementID(ID6, reason6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nResponse from getReplacementID routine: " + temp6);

        //section to test getDataLocations routine
        String temp7[] = null;
        String ID7 = "0000000000000016.157994624000000"; //just generated this in getNewPVID above
        try {
            temp7 = vuhidsend.getDataLocations(ID7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int index = 0;
        for (String i:temp7) {
            System.out.println("\nResponse from getReplacementID routine: " + temp7[index]);
            index++;
        }


    }//end main()
*/
}
