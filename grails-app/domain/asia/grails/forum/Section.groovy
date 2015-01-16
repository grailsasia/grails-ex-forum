package asia.grails.forum

class Section {
    static hasMany = [topics:Topic]
    String title

    static constraints = {
    }
}
