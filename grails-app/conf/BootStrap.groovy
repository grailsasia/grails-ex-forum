import asia.grails.forum.Comment
import asia.grails.forum.DiscussionThread
import asia.grails.forum.SecRole
import asia.grails.forum.SecUser
import asia.grails.forum.SecUserSecRole
import asia.grails.forum.Section
import asia.grails.forum.Topic
import org.apache.commons.lang.RandomStringUtils

class BootStrap {
    def random = new Random();

    def init = { servletContext ->
        if (SecUser.count() == 0) {  // no user in db, lets create some
            def defaultRole = new SecRole(authority: 'ROLE_USER').save()
            // create 100 users
            (1..100).each { userNo ->
                String username = RandomStringUtils.random(10, true, false)
                def user = new SecUser(username:"user${userNo}", password: 'secret', enabled: true).save()
                // all users will have default role
                new SecUserSecRole(	secUser:user, secRole: defaultRole).save()
            }
        }

        if ( Section.count() == 0 ) { // create data if no forum data found
            // get all users
            def users = SecUser.list()
            // create 3 sections
            ('A'..'C').each { sectionLetter ->
                def sectionTitle = "Section ${sectionLetter}"
                def section = new Section(title: sectionTitle).save()
                // create 4 topics per section
                (1..4).each { topicNumber ->
                    def topicTitle = "Topic ${sectionLetter}-${topicNumber}"
                    def topicDescription = "Description of ${topicTitle}"
                    def topic = new Topic(section: section, title: topicTitle, description: topicDescription).save()
                    // create 10-20 threads each topic
                    def numberOfThreads = random.nextInt(11)+10
                    (1..numberOfThreads).each { threadNo ->
                        def opener = users[random.nextInt(100)]
                        def subject = "Subject ${sectionLetter}-${topicNumber}-${threadNo} "
                        def thread = new DiscussionThread(topic:topic, subject:subject, opener:opener).save()
                        new Comment(thread:thread, commentBy:opener, body:generateRandomComment()).save()
                        // create 10-35 replies per thread
                        def numberOfReplies = random.nextInt(26)+10
                        numberOfReplies.times {
                            def commentBy = users[random.nextInt(100)]
                            new Comment(thread:thread, commentBy:commentBy, body:generateRandomComment()).save()
                        }
                    }
                }
            }
        }
    }

    private String generateRandomComment() {
        def numberOfWords = random.nextInt(15) + 10
        StringBuilder sb = new StringBuilder()
        numberOfWords.times {
            def numberOfChars = random.nextInt(15) + 2
            def randomWord = RandomStringUtils.random(numberOfChars, true, false)
            sb.append("${randomWord} ")
        }
        return sb.toString()
    }

    def destroy = {
    }
}
