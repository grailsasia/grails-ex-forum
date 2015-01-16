package asia.grails.forum

class Comment {

    static belongsTo = Thread
    Thread thread

    SecUser commentBy
    String body
    Date createDate

    static constraints = {
    }
}
