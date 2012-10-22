package vuhid_tools.server;
import java.util.ArrayList;

import vuhid_tools.shared.Patient;

/** 
 * @author Damon Liang <damonl@cecs.pdx.edu>
 *
 * Interface for PDQ IHE Profile
 */

public interface PDQInterface
{
   ArrayList<Patient> PatientRegistryFindCandidatesQuery(Patient P);
}
