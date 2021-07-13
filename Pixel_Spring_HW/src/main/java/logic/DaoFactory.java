package logic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import repository.PatientDao;
import repository.PractitionerDao;
import repository.VisitDao;

@Service
public class DaoFactory {
	
	private final PatientDao patientDao;
	
	private final PractitionerDao practitionerDao;
	
	private final VisitDao visitDao;
	

	public DaoFactory(PatientDao patientDao, PractitionerDao practitionerDao, VisitDao visitDao) {
		
		this.patientDao = patientDao;
		this.practitionerDao = practitionerDao;
		this.visitDao = visitDao;
		
	}


	public PatientDao getPatientDao() {
		return patientDao;
	}


	public PractitionerDao getPractitionerDao() {
		return practitionerDao;
	}


	public VisitDao getVisitDao() {
		return visitDao;
	}
	

	
	
	
}
