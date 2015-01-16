package asia.grails.forum

class ForumController {

    def home() {
        [sections:Section.listOrderByTitle()]
    }

    def topic(long topicId) {
        Topic topic = Topic.get(topicId)

        params.max = 10
        params.sort = 'createDate'
        params.order = 'desc'

        [threads:DiscussionThread.findAllByTopic(topic, params),
         numberOfThreads:DiscussionThread.countByTopic(topic), topic:topic]
    }

}
