package repository;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.sun.istack.NotNull;

import net.sf.jsefa.common.converter.DateConverter;
import net.sf.jsefa.common.converter.LongConverter;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

@Entity
@Table(name = "patients")
@CsvDataType()
public class Patient {

	@Id
	@CsvField(pos = 1, converterType = LongConverter.class)
	private Long patientId;

	@CsvField(pos = 2)
	@NotNull
	@Column(nullable = false)
	private String firstName;

	@CsvField(pos = 3)
	@NotNull
	@Column(nullable = false)
	private String lastName;

	@CsvField(pos = 4)
	@NotNull
	@Column(nullable = false)
	private String city;

	@NotNull
	@CsvField(pos = 5, converterType = DateConverter.class, format ="yyyy-MM-dd hh:mm:ss")
	@Column(nullable = false)
	private Date createdAt;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
    name = "Patient2practitioner",
    	joinColumns = @JoinColumn(name = "patientId"), // aby hibernate nie robil patient_ID tylko patientid nie moze być ID z duzej xd
    	inverseJoinColumns = @JoinColumn(name = "practitionerId"))
	private Set<Practitioner> Practitioners = new HashSet<Practitioner>();
	
	
	public Set<Practitioner> getPractitioners() {
		return Practitioners;
	}

	public void setPractitioners(Set<Practitioner> practitioners) {
		Practitioners = practitioners;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
