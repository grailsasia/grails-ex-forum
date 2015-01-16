package asia.grails.forum

class DiscussionThread {
    static belongsTo = Topic
    static hasMany = [comments:Comment]

    Topic topic
    String subject
    SecUser opener
    Date createDate = new Date()

    public long getNumberOfReplies() {
        Comment.countByThread(this)
    }

    static constraints = {
    }
}
