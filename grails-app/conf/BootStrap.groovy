import asia.grails.forum.Comment
import asia.grails.forum.DiscussionThread
import asia.grails.forum.SecRole
import asia.grails.forum.SecUser
import asia.grails.forum.SecUserSecRole
import asia.grails.forum.Section
import asia.grails.forum.Topic

class BootStrap {
    def random = new Random();
    def words = ("time,person,year,way,day,thing,man,world,life,hand,part,child,eye,woman,place,work,week,case,point," +
                "government,company,number,group,problem,fact,be,have,do,say,get,make,go,know,take,see,come,think,look," +
                "want,give,use,find,tell,ask,work,seem,feel,try,leave,call,good,new,first,last,long,great,little,own," +
                "other,old,right,big,high,different,small,large,next,early,young,important,few,public,bad,same,able,to,of," +
                "in,for,on,with,at,by,from,up,about,into,over,after,beneath,under,above,the,and,a,that,I,it,not,he,as,you," +
                "this,but,his,they,her,she,or,an,will,my,one,all,would,there,their").split(",")

    def init = { servletContext ->
        if (SecUser.count() == 0) {  // no user in db, lets create some
            def defaultRole = new SecRole(authority: 'ROLE_USER').save()
            // create 100 users
            (1..100).each { userNo ->
                String username = "user${userNo}"
                def user = new SecUser(username:username, password: 'secret', enabled: true).save()
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
        def numberOfWords = random.nextInt(50) + 15
        StringBuilder sb = new StringBuilder()
        numberOfWords.times {
            def randomWord = words[random.nextInt(words.length)]
            sb.append("${randomWord} ")
        }
        return sb.toString()
    }

    def destroy = {
    }
}
