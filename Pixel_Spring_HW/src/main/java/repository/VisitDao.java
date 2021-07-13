package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisitDao extends JpaRepository<Visit, Long> {

	@Query(value = "select practitioners.specialization as visit, count(practitioners.specialization) as numberOfVists "
			+ "from visits inner join practitioners on (visits.practitioner_id = practitioners.practitioner_id) "
			+ "where (practitioners.specialization in (:spec))", nativeQuery = true)
	public List<Object[]> getVisitsOfSelectedPractitionerBySpecialization(@Param("spec") String specialization);
	

}
