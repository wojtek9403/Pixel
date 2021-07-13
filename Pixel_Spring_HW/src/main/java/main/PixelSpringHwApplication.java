package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import logic.CustomQueryHandler;
import logic.Parser;
import repository.Patient;
import repository.Patient2practitioner;
import repository.PatientDao;
import repository.Practitioner;
import repository.PractitionerDao;
import repository.Visit;
import repository.VisitDao;

@ComponentScan(basePackages = { "main", "controllers", "logic" })
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "repository")
@SpringBootApplication
public class PixelSpringHwApplication {
	
	private static final Logger log = LoggerFactory.getLogger(PixelSpringHwApplication.class);


	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(PixelSpringHwApplication.class, args);
		
		//---------------------------------Fill database here--------------------------------//

		File patients = new File("src/main/resources/patients.csv");
		File practitioners = new File("src/main/resources/practitioners.csv");
		File visits = new File("src/main/resources/visits.csv");
		File patient2practitioner = new File("src/main/resources/patient2practitioner.csv");

		try {

			PatientDao patientDao = (PatientDao) context.getBean("patientDao");
			Parser.putCSVdataToBase(patients, new ArrayList<Patient>(), patientDao,
					Parser.getSimpleCSVdeserializer(Patient.class, ','));
			log.info("Patients data succesfully loaded to H2 database");

			PractitionerDao practitionerDao = (PractitionerDao) context.getBean("practitionerDao");
			Parser.putCSVdataToBase(practitioners, new ArrayList<Practitioner>(), practitionerDao,
					Parser.getSimpleCSVdeserializer(Practitioner.class, ','));
			log.info("Practitioners data succesfully loaded to H2 database");


			List<Patient2practitioner> relationData = Parser.parseCSVtoList(patient2practitioner, new ArrayList<Patient2practitioner>(),
					Parser.getSimpleCSVdeserializer(Patient2practitioner.class, ','));

			CustomQueryHandler queryHandler = (CustomQueryHandler) context.getBean("customQueryHandler");

			queryHandler.insertDataIntoDBbyFields(relationData, Patient2practitioner.class,
					"INSERT INTO Patient2practitioner (patient_Id,practitioner_Id) VALUES (?,?)");
			log.info("Patient2practitioner relation data succesfully loaded to H2 database");


			VisitDao visitDao = (VisitDao) context.getBean("visitDao");
			Parser.putCSVdataToBase(visits, new ArrayList<Visit>(), visitDao,
					Parser.getSimpleCSVdeserializer(Visit.class, ','));
			log.info("Visits data succesfully loaded to H2 database");


		} catch (IOException | RuntimeException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
