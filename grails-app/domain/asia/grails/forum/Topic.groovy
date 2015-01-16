package asia.grails.forum

class Topic {
    static belongsTo = Section
    static hasMany = [threads:DiscussionThread]

    Section section
    String title
    String description

    static constraints = {
    }
}
