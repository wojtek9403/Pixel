package controllers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import logic.DaoFactory;

@Controller
@RequestMapping("/Pixel")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private final DaoFactory daoFactory;

	public UserController(DaoFactory daoFactory) {

		this.daoFactory = daoFactory;

	}

	@GetMapping("/Patients")
	public String computePatientsInfo(@RequestParam(defaultValue = "ALL") String cities,
			@RequestParam(defaultValue = "ALL") String specializations, Model model) {

		log.info("ASK : computePatientsInfo : specializations: " + specializations + " cities: " + cities);

		List<String> cities_query = this.inputCleaner(cities);
		List<String> specializations_query = this.inputCleaner(specializations);

		List<Object[]> result = this.resultsOfQuery(cities, specializations, cities_query, specializations_query);

		if (result.size() == 0) {
			String[] placeholder = new String[2];
			placeholder[0] = "Nothing to see here";
			placeholder[1] = "X";
			result.add(placeholder);
		}

		model.addAttribute("patients", result);

		return "patientsWelcome";
	}

	@GetMapping("/Practitioners")
	public String computePractitionersInfo(@RequestParam(defaultValue = "Pediatrics") String specialization,
			Model model) {

		log.info("ASK : computePractitionersInfo : specialization: " + specialization);

		specialization = this.inputCleaner(specialization).get(0);

		List<Object[]> result = daoFactory.getVisitDao()
				.getVisitsOfSelectedPractitionerBySpecialization(specialization);

		if (result.size() == 1 && "NEPHROLOGY".equals(result.get(0)[0])
				&& ("0".equals(((BigInteger) result.get(0)[1]).toString()))) {
			result.clear();
			String[] placeholder = new String[2];
			placeholder[0] = "Nothing to see here";
			placeholder[1] = "X";
			result.add(placeholder);
		}

		model.addAttribute("practitioners", result);

		return "practitionersWelcome";

	}

	@ExceptionHandler(RuntimeException.class)
	public String handleRuntimeException(RuntimeException ex) {

		log.info("Exception details : " + ex.getMessage());

		return "error";
	}

	public List<Object[]> resultsOfQuery(String cities, String specializations, List<String> cities_query,
			List<String> specializations_query) {

		List<Object[]> result = new ArrayList<>();

		if (!"ALL".equals(cities) && "ALL".equals(specializations)) {

			log.info("method used : getDetailsByCityList");
			result = daoFactory.getPatientDao().getDetailsByCityList(cities_query);

		} else if ("ALL".equals(cities) && !"ALL".equals(specializations)) {

			log.info("method used : getDetailsBySpecializationList");
			result = daoFactory.getPatientDao().getDetailsBySpecializationList(specializations_query);

		} else if (!"ALL".equals(cities) && !"ALL".equals(specializations)) {

			log.info("method used : getDetailsBySpecializationAndCitiesList");
			result = daoFactory.getPatientDao().getDetailsBySpecializationAndCitiesList(cities_query,
					specializations_query);

		} else {

			log.info("method used : getDetailsList");
			result = daoFactory.getPatientDao().getDetailsList();
		}

		return result;
	}

	public List<String> inputCleaner(String data) {

		data = data.replaceAll("\\s+", "").toUpperCase();
		List<String> resultData = Arrays.asList(data.split("(,|;)"));

		return resultData;

	}

}
