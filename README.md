# JavaMicroHost

STRUCTURE:
 App > MHRunnable > API > Controller > Endpoint > [Service Layer] > model + conn > database

BASIC:
 * todo Make sure we are using Dagger DI correctly
 * todo build separate jars for example/library
 * todo 90+% unit test coverage
 * todo Separate example from library into new repo to demonstrate pulling in the dependency
   ref: https://stackoverflow.com/questions/20161602/loading-maven-dependencies-from-github

INTERMEDIATE:
 * todo Built-in support for common requirements like authentication, CORS, OPTIONS/HEAD responses
 * todo Built-in support for batch operations
 * todo Support for generation of Swagger documentation

ADVANCED:
 * todo Register service with registry service
 * todo Support for some sort of API schema code/stub generation
 * todo Support for JSONApi