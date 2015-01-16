package asia.grails.forum

class Thread {
    static belongsTo = Topic
    static hasMany = [comments:Comment]

    Topic topic
    String subject
    SecUser opener
    Date createDate

    static constraints = {
    }
}
