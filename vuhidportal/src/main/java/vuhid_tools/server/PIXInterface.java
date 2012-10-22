package vuhid_tools.server;
import java.util.ArrayList;

import vuhid_tools.shared.Patient;

/** 
 * @author Damon Liang <damonl@cecs.pdx.edu>
 * 
 * Interface for PIX IHE Profile used in the EMPI
 */

public interface PIXInterface
{
   boolean PatientRegistryRecordRevised(Patient P);
   boolean PatientRegistryRecordAdded(Patient P);
   ArrayList<String> PatientRegistryGetIdentifiersQuery(Patient P);
   boolean PatientRegistryDuplicatesResolved(Patient P1, Patient P2);
}
