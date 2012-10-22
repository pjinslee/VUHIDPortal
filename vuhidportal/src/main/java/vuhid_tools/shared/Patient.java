package vuhid_tools.shared;
import java.lang.String;
import java.io.Serializable;

public class Patient implements Serializable
{
   public String FirstName;
   public String LastName;
   public String VUHID_ID;
   public String DOB;
   public String gender;
   public String email;
   public String phoneNumber;
   public String address;
   public String city;
   public String state;
   public String zip;
   public String EMPIId;
   public String bloodType;
   public String bedID;
   public String contactFirstName;
   public String contactLastName;
   public String contactPhoneNumber;
   public String contactAddress;
   public int    weightLbs; 
   public int    heightInches;
   public boolean   deceased;
   
   public Patient()
   {
   }

   public Patient(String FN, String LN, String Vid, boolean tf)
   {
      FirstName = FN;
      LastName = LN;
      VUHID_ID = Vid;
      deceased = tf;
   }

   public Patient(String FN, String LN, String Vid, String birthdate, String gd, String e_mail, String phoneNum,
                  String addr, String cty, String states, String zp, String EMPI_Id, String blood, String bed,
                  String contactFN, String contactLN, String contactPhone, String contactAddr,
                  int weight, int height, boolean TF)
   {
      FirstName          = FN;
      LastName           = LN;
      VUHID_ID           = Vid;
      DOB                = birthdate;
      gender             = gd;
      email              = e_mail;
      phoneNumber        = phoneNum;
      address            = addr;
      city               = cty;
      state              = states;
      zip                = zp;
      EMPIId             = EMPI_Id;
      bloodType          = blood;
      bedID              = bed;
      contactFirstName   = contactFN;
      contactLastName    = contactLN;
      contactPhoneNumber = contactPhone;
      contactAddress     = contactAddr;
      weightLbs          = weight;
      heightInches       = height;
      deceased           = TF;
   }

   public static boolean match(Patient P1, Patient P2){
      // Should return true if all none-empty fields of Patient P1 (first, last name, etc)
      //  matches all non-empty fields of Patient P2
      // P1.field != null && P2.field != null && P1.field == P2.field
      // If the above is true for all fields then the match is true

      if (P1.FirstName != null && P2.FirstName != null && 
          !P1.FirstName.equals("") && !P2.FirstName.equals("") && 
          !P1.FirstName.equals(P2.FirstName))
         return false;
      if (P1.LastName != null && P2.LastName != null && 
          !P1.LastName.equals("") && !P2.LastName.equals("") && 
          !P1.LastName.equals(P2.LastName))
         return false;
      if (P1.VUHID_ID != null && P2.VUHID_ID != null && 
          !P1.VUHID_ID.equals("") && !P2.VUHID_ID.equals("") && 
          !P1.VUHID_ID.equals(P2.VUHID_ID))
         return false;
      if (P1.DOB != null && P2.DOB != null && 
          !P1.DOB.equals("") && !P2.DOB.equals("") && 
          !P1.DOB.equals(P2.DOB))
         return false;
      if (P1.gender != null && P2.gender != null && 
          !P1.gender.equals("") && !P2.gender.equals("") && 
          !P1.gender.equals(P2.gender))
         return false;
      if (P1.email != null && P2.email != null && 
          !P1.email.equals("") && !P2.email.equals("") && 
          !P1.email.equals(P2.email))
         return false;
      if (P1.phoneNumber != null && P2.phoneNumber != null && 
          !P1.phoneNumber.equals("") && !P2.phoneNumber.equals("") && 
          !P1.phoneNumber.equals(P2.phoneNumber))
         return false;
      if (P1.address != null && P2.address != null && 
          !P1.address.equals("") && !P2.address.equals("") && 
          !P1.address.equals(P2.address))
         return false;
      if (P1.city != null && P2.city != null && 
          !P1.city.equals("") && !P2.city.equals("") && 
          !P1.city.equals(P2.city))
         return false;
      if (P1.state != null && P2.state != null && 
          !P1.state.equals("") && !P2.state.equals("") && 
          !P1.state.equals(P2.state))
         return false;
      if (P1.zip != null && P2.zip != null && 
          !P1.zip.equals("") && !P2.zip.equals("") && 
          !P1.zip.equals(P2.zip))
         return false;
      if (P1.EMPIId != null && P2.EMPIId != null && 
          !P1.EMPIId.equals("") && !P2.EMPIId.equals("") && 
          !P1.EMPIId.equals(P2.EMPIId))
         return false;
      if (P1.bloodType != null && P2.bloodType != null && 
          !P1.bloodType.equals("") && !P2.bloodType.equals("") && 
          !P1.bloodType.equals(P2.bloodType))
         return false;
      if (P1.bedID != null && P2.bedID != null && 
          !P1.bedID.equals("") && !P2.bedID.equals("") && 
          !P1.bedID.equals(P2.bedID))
         return false;
      if (P1.contactFirstName != null && P2.contactFirstName != null && 
          !P1.contactFirstName.equals("") && !P2.contactFirstName.equals("") && 
          !P1.contactFirstName.equals(P2.contactFirstName))
         return false;
      if (P1.contactLastName != null && P2.contactLastName != null && 
          !P1.contactLastName.equals("") && !P2.contactLastName.equals("") && 
          !P1.contactLastName.equals(P2.contactLastName))
         return false;
      if (P1.contactPhoneNumber != null && P2.contactLastName != null && 
          !P1.contactPhoneNumber.equals("") && !P2.contactPhoneNumber.equals("") && 
          !P1.contactPhoneNumber.equals(P2.contactPhoneNumber))
         return false;
      if (P1.contactAddress != null && P2.contactAddress != null && 
          !P1.contactAddress.equals("") && !P2.contactAddress.equals("") && 
          !P1.contactAddress.equals(P2.contactAddress))
         return false;
      if (P1.weightLbs != -1 && P2.weightLbs != -1 && P1.weightLbs != P2.weightLbs)
         return false;
      if (P1.heightInches != -1 && P2.heightInches != -1 && P1.heightInches != P2.heightInches)
         return false;
      if (P1.deceased != P2.deceased)
         return false;
      return true;
   }

   //Checks if a patient is fully filled out.
   public boolean CheckPatient()
   {
      if (FirstName == null)
      {
         System.out.println("Require First name.");
         return false;
      }
      if (LastName == null)
      {
         System.out.println("Require Last name.");
         return false;
      }
      if (VUHID_ID == null)
      {
         System.out.println("Require VUHID ID.");
         return false;
      }
      if (DOB == null)
      {
         System.out.println("Require a DOB.");
         return false;
      }
      if (gender == null) {
         System.out.println("Require gender.");
         return false;
      }
      if (email == null) {
         System.out.println("Require an email address.");
         return false;
      }
      if (phoneNumber == null) {
         System.out.println("Require phone number.");
         return false;
      }
      if (address == null) {
         System.out.println("Require an address.");
         return false;
      }
      if (city == null) {
         System.out.println("Require city.");
         return false;
      }
      if (state == null) {
         System.out.println("Require state.");
         return false;
      }
      if (zip == null) {
         System.out.println("Require zip code.");
         return false;
      }
      if (EMPIId == null) {
         System.out.println("Require EMPI ID.");
         return false;
      }
      if (bloodType == null) {
         System.out.println("Require blood type.");
         return false;	
      }
      if (bedID == null) {
         System.out.println("Require bed ID.");
         return false;
      }
      if (contactFirstName == null) {
         System.out.println("Require a contact First Name.");
         return false;
      }
      if (contactLastName == null) {
         System.out.println("Require a contact Last Name.");
         return false;
      }
      if (contactPhoneNumber == null) {
         System.out.println("Require a contact phone number.");
         return false;
      }
      if (contactAddress == null) {
         System.out.println("Require a contact address.");
         return false;
      }
      if (weightLbs == -1) {
         System.out.println("Require weight in lbs.");
         return false;
      }
      if (heightInches == -1) {
         System.out.println("Require height in inches.");
         return false;
      }
      return true;
   }
}
