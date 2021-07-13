package repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import net.sf.jsefa.common.converter.LongConverter;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;



@Entity
@Table(name = "visits")
@CsvDataType()
public class Visit {
	@Id
	@NotNull
	@CsvField(pos = 1, converterType = LongConverter.class)
	private Long visitId;

	@NotNull
	@CsvField(pos = 2,converterType = LongConverter.class)
	private Long practitionerId;

	@NotNull
	@CsvField(pos = 3,converterType = LongConverter.class)
	private Long patientId;

	public Long getVisitId() {
		return visitId;
	}

	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	public Long getPractitionerId() {
		return practitionerId;
	}

	public void setPractitionerId(Long practitionerId) {
		this.practitionerId = practitionerId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

}
