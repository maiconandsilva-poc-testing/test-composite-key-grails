package elit.comum

import grails.test.hibernate.HibernateSpec
import grails.testing.gorm.DataTest
import grails.testing.mixin.integration.Integration
import org.springframework.test.annotation.Commit
import org.springframework.test.annotation.Rollback
import spock.lang.Specification

@Commit
class CompositeKeySpec extends Specification implements DataTest {

    def setup() {
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
    }

    void "composite key working"() {
        when:
            def prof = Professional.findById(1395)
            def area = Area.findById("TEST_AREA")
            def area2 = Area.findById("TEST_AREA2")
        then: "Professional is in 2 areas"
            prof.areas.size() == 2
        and: "professional contains area"
            prof.areas.containsAll([area, area2])
        and: "ProfessionalArea Contains 2 rows"
            ProfessionalArea.count() == 2
        and: "Can find area by professionals"
            Area.findAllByProfessionalsInList([prof], [fetch: [professionals: 'join']]).size() == 2
        and: "Can find Professionals by area"
            Professional.findByAreasInList([area], [fetch: [areas: 'join']])?.id == 1395
        and: "Can get Professionals through joinTable ProfessionalArea"
            1395 in ProfessionalArea.findAllByArea(area2)*.professional*.id
        and: "Can get Area through joinTable ProfessionalArea"
            "TEST_AREA2" in ProfessionalArea.findAllByProfessional(prof)*.area*.id
    }

    Class<?>[] getDomainClassesToMock(){
        return [ProfessionalArea, Professional, Area] as Class[]
    }
}