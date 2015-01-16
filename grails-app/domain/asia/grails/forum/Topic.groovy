package asia.grails.forum

class Topic {
    static belongsTo = Section
    static hasMany = [threads:Thread]

    Section section
    String title
    String description

    static constraints = {
    }
}
