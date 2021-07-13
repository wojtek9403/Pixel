package repository;

import com.sun.istack.NotNull;

import net.sf.jsefa.common.converter.LongConverter;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;


@CsvDataType()
public class Patient2practitioner {
	
	@NotNull
	@CsvField(pos = 1,converterType = LongConverter.class)
	private Long patientId;

	@NotNull
	@CsvField(pos = 2,converterType = LongConverter.class)
	private Long practitionerId;
	
	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getPractitionerId() {
		return practitionerId;
	}

	public void setPractitionerId(Long practitionerId) {
		this.practitionerId = practitionerId;
	}


}
