import com.github.unqUi.model.RottenTomatoesSystem
import dto.ErrorDTO
import io.javalin.core.security.AccessManager
import io.javalin.core.security.RouteRole
import io.javalin.http.Context
import io.javalin.http.Handler

class TokenAccessManager(private val rottenTomatoesSystem: RottenTomatoesSystem, private val jwt: JWTController) : AccessManager {
    override fun manage(handler: Handler, context: Context, roles: MutableSet<RouteRole>) {
        if (roles.contains(Rol.USER)){
            val token = context.header("Authorization")
            if (token != null) {
                val user = jwt.validate(token)
                context.attribute("user", user)
                if (user == null){
                    context.json(ErrorDTO("Unauthorized")).status(401)
                    return
                }
            }
            else {
                context.json(ErrorDTO("Unauthorized")).status(401)
                return
            }
        }
        handler.handle(context)
    }
}