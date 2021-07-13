package repository;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import net.sf.jsefa.common.converter.LongConverter;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

@Entity
@Table(name = "practitioners")
@CsvDataType()
public class Practitioner {
	
	@Id
	@CsvField(pos = 1,converterType = LongConverter.class)
	@NotNull
	private Long practitionerId;

	@CsvField(pos = 2)
	@NotNull
	@Column(nullable = false)
	private String specialization;
	
	@ManyToMany(mappedBy = "Practitioners")
	private Set<Patient> Patients = new HashSet<Patient>();
	

	public Set<Patient> getPatients() {
		return Patients;
	}

	public void setPatients(Set<Patient> patients) {
		Patients = patients;
	}

	public Long getPractitionerId() {
		return practitionerId;
	}

	public void setPractitionerId(Long practitionerId) {
		this.practitionerId = practitionerId;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}


	
}
