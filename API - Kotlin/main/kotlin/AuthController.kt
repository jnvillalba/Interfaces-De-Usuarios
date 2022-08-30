import com.github.unqUi.model.DraftUser
import com.github.unqUi.model.RottenTomatoesSystem
import io.javalin.http.Context
import dto.*
import io.javalin.http.BadRequestResponse


class AuthController(private val rottenTomatoesSystem: RottenTomatoesSystem, private val jwt: JWTController){

    fun register(ctx: Context) {
        try {
            val userRegisterDTO = ctx.bodyValidator<UserRegisterDTO>()
                .check({it -> it.email.length > 0}, "Email can not be empty")
                .check({it -> it.password.length > 0}, "Password can not be empty")
                .get()
            val userWithSameEmail = rottenTomatoesSystem.users.parallelStream().filter{user -> user.email == userRegisterDTO.email}.findFirst()
            if (userWithSameEmail.isPresent) {
                ctx.json(ErrorDTO("Email already exists")).status(400)
                return
            }
            val user = rottenTomatoesSystem.addUser(DraftUser(userRegisterDTO.name, userRegisterDTO.image, userRegisterDTO.email, userRegisterDTO.password))
            val token = jwt.generateToken(user)
            ctx.header("Authorization", token)
            ctx.json(UserRegisterDTO(user)).status(200)

        } catch (exc: BadRequestResponse ) {
            ctx.json(ErrorDTO(exc.message!!)).status(400)
        }
    }

    fun login(ctx: Context) {
        try {
            val userLoginDTO = ctx.bodyValidator<UserLoginDTO>().get()
            val userWithSameEmail =
                rottenTomatoesSystem.users.parallelStream()
                    .filter { user -> user.email == userLoginDTO.email }
                    .findFirst()
            if (userWithSameEmail.isEmpty) {
                ctx.status(404)
                ctx.json(ErrorDTO("User not found"))
                return
            }
            val user = userWithSameEmail.get()
            if (user.password != userLoginDTO.password) {
                ctx.status(401)
                ctx.json(ErrorDTO("Incorrect password"))
                return
            }
            val token = jwt.generateToken(user)
            ctx.header("Authorization", token)
            ctx.json(UserDTO(user)).status(200)
        } catch (exc: BadRequestResponse ) {
            ctx.json(ErrorDTO(exc.message!!)).status(400)
        }
    }
}
