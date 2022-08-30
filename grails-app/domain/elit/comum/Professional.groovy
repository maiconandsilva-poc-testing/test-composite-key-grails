package elit.comum

class Professional implements Serializable {
	String name

	static hasMany = [
		areas: Area,
	]

	static mapping = {
		id column: "id_professional", generator: "assigned"
		tablePerHierarchy false
		version false
		areas joinTable: [name:'professional_area', key: 'id_professional']
	}

	static constraints = {
//		ativo nullable: true
	}
}