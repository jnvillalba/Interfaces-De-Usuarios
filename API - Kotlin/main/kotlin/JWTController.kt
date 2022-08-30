import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.github.unqUi.model.RottenTomatoesSystem
import com.github.unqUi.model.User
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider

class UserGenerator() : JWTGenerator<User>{
    override fun generate(user: User, algorithm: Algorithm): String {
        val token = JWT.create()
            .withClaim("id", user.id)
            .withClaim("email", user.email)
            .withClaim("password", user.password)

        return token.sign(algorithm)
    }
}

class JWTController(val rottenTomatoesSystem: RottenTomatoesSystem) {
    val algorithm = Algorithm.HMAC256("secret")
    val verifier = JWT.require(algorithm).build()
    val generator = UserGenerator()
    val provider = JWTProvider(algorithm, generator, verifier)

    fun generateToken(user: User): String{
        return provider.generateToken(user)

    }

    fun validate(token: String): User?{
        val token = provider.validateToken(token)
        if (!token.isPresent) {
            return null
        }
        val userId = token.get().getClaim("id").asString()
        return rottenTomatoesSystem.getUserById(userId)
    }
}


