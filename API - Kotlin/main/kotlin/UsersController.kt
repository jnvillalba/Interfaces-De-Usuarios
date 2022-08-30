import com.github.unqUi.model.RottenTomatoesSystem
import com.github.unqUi.model.User
import com.github.unqUi.model.UserError
import io.javalin.http.Context
import dto.*
import io.javalin.http.NotFoundResponse


class UsersController(private val rottenTomatoesSystem: RottenTomatoesSystem){

    fun getUserByHeader(ctx : Context) {
        try {
            val scoreByMovie = Utils.getScoreByMovie(rottenTomatoesSystem)
            val user = ctx.attribute<User>("user")!!
            ctx.json(UserWithReviewsDTO(user, scoreByMovie)).status(200)
        } catch (exc: NotFoundResponse) {
            ctx.json(ErrorDTO(exc.message!!)).status(401)
        }
    }

    fun getUserByIdParameter(ctx : Context) {
        try {
            val scoreByMovie = Utils.getScoreByMovie(rottenTomatoesSystem)
            val user = rottenTomatoesSystem.getUserById(ctx.pathParam("id"))
            ctx.json(UserWithReviewsDTO(user, scoreByMovie)).status(200)
        } catch (exc: UserError) {
            ctx.status(404)
            ctx.json(ErrorDTO(exc.message!!))
        }
    }
}