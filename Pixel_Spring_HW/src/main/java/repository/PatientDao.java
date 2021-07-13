package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientDao extends JpaRepository<Patient, Long>  {

	
	@Query(value = "select patients.first_name, patients.last_name, count(visits.visit_id) "
			+ "from patients inner join visits on (patients.patient_id = visits.patient_id) "
			+ "where patients.city in (:city_list) "
			+ "group by (patients.first_name)", nativeQuery = true)
	List<Object[]> getDetailsByCityList(@Param("city_list") List<String> city_list);
	
	
	
	@Query(value = "select patients.first_name, patients.last_name, count(visits.visit_id) from "
			+ "patients inner join visits on (patients.patient_id = visits.patient_id) "
			+ "where visits.practitioner_id in "
			+ "(select practitioner_id from practitioners where practitioners.specialization in (:spec)) "
			+ "group by (patients.first_name)", nativeQuery = true)
	List<Object[]> getDetailsBySpecializationList(@Param("spec") List<String> spec);
	
	
	
	@Query(value = "select patients.first_name, patients.last_name, count(visits.visit_id) as countVisits "
			+ "from patients inner join visits on (patients.patient_id = visits.patient_id) "
			+ "where ((patients.city in (:city_list)) "
			+ "and "
			+ "(visits.practitioner_id in (select practitioner_id from practitioners where practitioners.specialization in (:spec)))) "
			+ "group by (patients.first_name)", nativeQuery = true)
	List<Object[]> getDetailsBySpecializationAndCitiesList(@Param("city_list") List<String> city_list, @Param("spec") List<String> spec);
	
	
	
	@Query(value = "select patients.first_name, patients.last_name, count(visits.visit_id) "
			+ "from patients inner join visits on (patients.patient_id = visits.patient_id) "
			+ "group by (patients.first_name)", nativeQuery = true)
	List<Object[]> getDetailsList();
	
	
	
	
}
