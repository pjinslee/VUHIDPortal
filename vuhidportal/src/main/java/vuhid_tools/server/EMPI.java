package vuhid_tools.server;
import java.util.ArrayList;

import vuhid_tools.shared.Patient;

/**
 * @author Damon Liang <damonl@cecs.pdx.edu>
 *
 * This is the implementation of the PIX and PDQ interfaces. 
 * The purpose of the EMPI class is to emulate what an actual
 * EMPI might do. 
 */

public class EMPI implements PIXInterface,PDQInterface
{
   //ArrayList of Patients
   private ArrayList<Patient> P_List;

   EMPI()
   {
      P_List = new ArrayList<Patient>();

      //Populate P_List
      P_List.add(new Patient("Dustin", "Schmidt", "000001", false));
      P_List.add(new Patient("Peter", "Inslee", "000002", false));
      P_List.add(new Patient("Teja", "Pitla", "000003", false));
      P_List.add(new Patient("Robert", "Hickey", "000004", false));
      P_List.add(new Patient("Vy", "Le", "000005", false));
      P_List.add(new Patient("Long", "Phan", "000006", false));
      P_List.add(new Patient("Damon", "Liang", "000007", false));
      P_List.add(new Patient("Bart", "Massey", "000008", false));
      P_List.add(new Patient("Jen", "Henni", "000009", false));
      P_List.add(new Patient("Barry", "Hieb", "000010", false));
   }

   /**
    * Edits an existing Patient's information. 
    *
    * @param P    Patient's new information
    * @return     Returns a boolean, true on success false otherwise
    */
	public boolean PatientRegistryRecordRevised(Patient P){
      if (P == null)
         return false;
      
      Patient Search_Patient = new Patient();      //Patient used for searching
      Search_Patient.VUHID_ID = P.VUHID_ID;        //Only search by VUHID_ID
      
      for (int i = 0; i < P_List.size(); i++)
      {
         if (Patient.match(Search_Patient,P_List.get(i)))
         {
            P_List.set(i, P);   //Replaces P_List[i] with P
		      return true;
         }
      }
		return false;
	}

   /**
    * Adds a Patient to the ArrayList P_List
    * 
    * @param P    The patient to be added
    * @return     Returns a boolean based on success of adding user
    */
   public boolean PatientRegistryRecordAdded(Patient P)
   {
      if (P == null)
         return false;
      return P_List.add(P);
   }

   /**
    * Gets all of the VUHID IDs belonging to a Patient P.
    * 
    * @param P    Demographic info of the Patient
    * @return     An ArrayList of strings containing all of the Patient's IDs
    */
   public ArrayList<String> PatientRegistryGetIdentifiersQuery(Patient P)
   {
      ArrayList<String> IDs = new ArrayList<String>();
      for (int i = 0; i < P_List.size(); i++)
      {
         if (Patient.match(P,P_List.get(i)))
         {
            //Add IDs
            IDs.add(P_List.get(i).VUHID_ID);
         }
      }
      
      return IDs;
   }

   /**
    * Takes two patients, takes all instances of P1 in P_list and replaces it with P2
    * 
    * @param P1      The duplicate patient to be removed
    * @param P2      Patient information that will replace P1
    * @return        Returns true if any changes were made, false otherwise
    */
   public boolean PatientRegistryDuplicatesResolved(Patient P1, Patient P2)
   {
      boolean Made_a_Change = false;   //return value
      if (P1 == null || P2 == null)
         return false;

      //Search for matches and then replace them
      //Currently replaces all matches
      for (int i = 0; i < P_List.size(); i++)
      {
         if (Patient.match(P1,P_List.get(i)))
         {
            P_List.set(i, P2);
            Made_a_Change = true;  //make this line "return true" if you want it to change only one element
         }
      }
      return Made_a_Change;
   }

   //PDQ Function
   /**
    * Searchs for any patients matching P. 
    *
    * @param P       A patient containing demographic to search on
    * @return        An ArrayList of patients that match demographic information
    */
   public ArrayList<Patient> PatientRegistryFindCandidatesQuery(Patient P) {
      ArrayList<Patient> list = new ArrayList<Patient>();
      for (int i = 0; i < P_List.size(); i++)
      {
         if (Patient.match(P,P_List.get(i)))
         {
            list.add(P_List.get(i));
         }
      }
      return list;
   }
}
