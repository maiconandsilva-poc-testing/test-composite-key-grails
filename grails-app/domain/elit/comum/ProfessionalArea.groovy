package elit.comum

import org.apache.commons.lang.builder.HashCodeBuilder

class ProfessionalArea implements Serializable {
	String activities
	Area area
	Professional professional

	boolean equals(other) {
		if (!(other instanceof ProfessionalArea)){
			return false
		}
		other.areaId == areaId && other.professionalId == professionalId
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		builder.append areaId
		builder.append professionalId
		builder.toHashCode()
	}

	static constraints = {
		area (nullable:false)
		professional (nullable:false)
		activities nullable: true
	}

	static mapping = {
		table name: "professional_area"
		id composite: ['professional', 'area']
		version false
		professional column : 'id_professional', lazy: false
		area column: 'id_area', lazy: false
	}
}
