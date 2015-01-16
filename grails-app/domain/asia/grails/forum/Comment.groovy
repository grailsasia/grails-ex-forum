package asia.grails.forum

class Comment {

    static belongsTo = DiscussionThread

    DiscussionThread thread
    SecUser commentBy
    String body
    Date createDate = new Date()

    static constraints = {
        body( maxSize: 8000)
    }
}
