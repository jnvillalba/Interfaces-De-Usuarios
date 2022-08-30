import com.github.unqUi.model.RottenTomatoesSystem
import com.github.unqUi.model.getRottenTomatoesSystem
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*


class Api() {
    val rottenTomatoesSystem: RottenTomatoesSystem = getRottenTomatoesSystem()
    val jwt = JWTController(rottenTomatoesSystem)
    val authController = AuthController(rottenTomatoesSystem, jwt)
    val usersController = UsersController(rottenTomatoesSystem)
    val categoriesController = CategoriesController(rottenTomatoesSystem)
    val reviewController = ReviewController(rottenTomatoesSystem)
    val contentController = ContentController(rottenTomatoesSystem)

    fun start(port: Int) {
        val app = Javalin.create {
            it.defaultContentType = "application/json"
            it.accessManager(TokenAccessManager(rottenTomatoesSystem, jwt))
            it.enableCorsForAllOrigins()
        }

        app.before {
            it.header("Access-Control-Expose-Headers", "*")
        }

        //paths
        app.routes {

            path("login"){
                post(authController::login)
            }

            path("register"){
                post(authController::register)
            }


            path("user") {
                get(usersController::getUserByHeader, Rol.USER)
                path("{id}") {
                    get(usersController::getUserByIdParameter)
                }
            }


            path("categories") {
                get(categoriesController::getCategories)
                path("{id}") {
                    get(categoriesController::getCategoryById)
                }
            }


            path("content"){
                path("latest"){
                    get(contentController::getLatestContent)
                }
                path("top"){
                    get(contentController::getTopContent)
                }
                path("{id}"){
                    post(reviewController::addReview, Rol.USER)
                    get(contentController::getContentByIdParameter)
                }
            }
        }

        app.start(port)
    }
}

fun main(args: Array<String>) {
    Api().start(7070)
}