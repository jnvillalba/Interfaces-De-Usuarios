import com.github.unqUi.model.*
import dto.*
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context

class ReviewController(val rottenTomatoesSystem: RottenTomatoesSystem) {

    fun addReview(ctx: Context) {
        try{
            val scoreByMovie = Utils.getScoreByMovie(rottenTomatoesSystem)
            val reviewDTO = ctx.bodyValidator<ReviewDTO>().get()
            val user = ctx.attribute<User>("user")!!
            val movie = rottenTomatoesSystem.getMovieById(ctx.pathParam("id"))
            val draftReview = (DraftReview(user.id, movie.id, reviewDTO.text, reviewDTO.stars))
            if (draftReview.stars > 5 || draftReview.stars < 0) {
                ctx.status(400)
                ctx.json(ErrorDTO("Can not rate above 5 stars"))
                return
            }
            val review = rottenTomatoesSystem.addReview(draftReview)
            ctx.json(SimpleReviewDTO(review, scoreByMovie)).status(200)
        } catch (exc: BadRequestResponse) {
            ctx.json(ErrorDTO(exc.message!!)).status(400)
        } catch (exc: MovieError) {
            ctx.json(ErrorDTO(exc.message!!)).status(400)
        } catch (exc: ReviewError) {
            ctx.json(ErrorDTO(exc.message!!)).status(400)
        }
    }
}