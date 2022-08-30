package elit.comum

class Area implements Serializable {
	String id
	String name

	static belongsTo = [areas: Professional]

	static hasMany = [
		professionals: Professional,
    ]
	
	static mapping = {
		id column: "id_area", generator: "assigned"
		version false
		professionals joinTable: [name: 'professional_area', key: 'id_area']
	}

	static constraints = {
	}
}
