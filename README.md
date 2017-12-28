# JavaMicroHost

STRUCTURE:
 App > MHRunnable > API > Controller > Endpoint > [Service Layer] > model + conn > database

BASIC:
 * todo Make sure we are using Dagger DI correctly
 * todo 90+% unit test coverage

INTERMEDIATE:
 * todo Built-in support for common requirements like authentication, CORS, OPTIONS/HEAD responses
 * todo Built-in support for batch operations
 * todo Support for generation of Swagger documentation
 * todo build separate jars for example/library
 * todo Separate example from library into new repo to demonstrate pulling in the dependency
   ref: https://stackoverflow.com/questions/20161602/loading-maven-dependencies-from-github

ADVANCED:
 * todo Register service with registry service
 * todo Support for some sort of API schema code/stub generation
 * todo Support for JSONApi
   ref: http://jsonapi.org/format/
 * todo Try to get launcher to fire up more than one MicroHost in a single JVM launcher app, one thread each.
   ref: https://stackoverflow.com/questions/60764/how-should-i-load-jars-dynamically-at-runtime
   ref: https://docs.oracle.com/javase/tutorial/deployment/jar/jarclassloader.html
 * todo Add some sort of service/daemon wrapper/executor
   ref: http://commons.apache.org/proper/commons-daemon/
   ref: http://yajsw.sourceforge.net/
   ref: https://wrapper.tanukisoftware.com/doc/english/download.jsp
   ref: http://www.qbssoftware.com/products/Java_Service_Wrapper/overview/_prodjsvwrapper
   +++ Why are these dang things so expensive? Is it really that complicated?
   ref: http://www.jcgonzalez.com/linux-java-service-wrapper-example
   ref: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
   ref: https://spredzy.wordpress.com/2013/02/09/java-service-wrapper-or-how-to-daemonize-your-java-services-for-all-major-oses/
   ref: https://sourceforge.net/projects/wrapper/files/wrapper/
