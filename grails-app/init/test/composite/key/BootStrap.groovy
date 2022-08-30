package test.composite.key

import elit.comum.Area
import elit.comum.Professional
import elit.comum.ProfessionalArea

class BootStrap {

    def init = { servletContext ->
        Area.withNewTransaction {
            def area = new Area(name: "TEST AREA")
            area.id =  "TEST_AREA"
            area.save(flush: true, failOnError: true)
            def area2 = new Area(name: "TEST AREA TWO")
            area2.id = "TEST_AREA2"
            area2.save(flush: true, failOnError: true)
            def prof = new Professional(name: "Test")
            prof.addToAreas(area)
            prof.addToAreas(area2)
            prof.id = 1395
            prof.save(flush: true, failOnError: true)

//            new ProfessionalArea(professional: prof, area: area).save(flush: true)
//            new ProfessionalArea(professional: prof, area: area2).save(flush: true)
        }

        println "Professional"
        def profs = Professional.findAll()
        profs.each {
            println "ID: $it.id"
            println "NAME: $it.name"
            println "AREAS: $it.areas"
        }

        println "Area"
        Area.findAll().each {
            println "AREA NAME: $it.id"
            println "Professional $it.professionals"
        }

        def areas = Area.findAllByProfessionalsInList(profs)
        println "AREAS BY PROFESSIONAL: $areas"

        println "ProfessionalArea"
        ProfessionalArea.findAll().each {
            println "AREA NAME: $it.area.name"
            println "Professsional: $it.professional.name"
        }
    }

    def destroy = {
    }
}
