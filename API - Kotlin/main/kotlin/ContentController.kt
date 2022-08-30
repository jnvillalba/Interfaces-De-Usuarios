import com.github.unqUi.model.*
import dto.*
import io.javalin.http.Context
import java.util.*


class ContentController(private val rottenTomatoesSystem: RottenTomatoesSystem) {

    fun getContentByIdParameter(ctx : Context) {
        try {
            val movie = rottenTomatoesSystem.getMovieById(ctx.pathParam("id"))
            val scoreByMovie = Utils.getScoreByMovie(rottenTomatoesSystem)
            val reviews = reviewsByMovie()[movie]
            ctx.json(MovieDTO(movie, scoreByMovie, reviews)).status(200)
        } catch (exc: MovieError) {
            ctx.status(400)
            ctx.json(ErrorDTO(exc.message!!))
        }
    }

    fun getLatestContent(ctx : Context) {
        val scoreByMovie = Utils.getScoreByMovie(rottenTomatoesSystem)
        val movies = rottenTomatoesSystem.movies
        val latestMovies = movies.subList(movies.size - 10, movies.size)
            .parallelStream().map{ SimpleMovieDTO(it, scoreByMovie) }.toList()
        ctx.json(ResultSimpleMoviesDTO(latestMovies)).status(200)
    }

    fun getTopContent(ctx : Context){
        val scoreByMovie = Utils.getScoreByMovie(rottenTomatoesSystem)
        val sortedMovies = scoreByMovie.keys.parallelStream().sorted{ m1, m2 -> scoreByMovie[m2]!!.compareTo(scoreByMovie[m1]!!)}.toList().take(10)
        ctx.json(ResultSimpleMoviesDTO(sortedMovies.parallelStream().map{SimpleMovieDTO(it, scoreByMovie)}.toList())).status(200)
    }

    //Poner lindo este codigo reutilizado
    fun reviewsByMovie(): Map<Movie, List<SimpleReviewDTO>> {
        val scoreByMovie = Utils.getScoreByMovie(rottenTomatoesSystem)
        val reviewsByMovie = mutableMapOf<Movie, MutableList<SimpleReviewDTO>>()
        for (movie in rottenTomatoesSystem.movies) {
            if (!reviewsByMovie.containsKey(movie)) {
                reviewsByMovie.put(movie, mutableListOf())
            }
        }
        for (review in rottenTomatoesSystem.reviews) {
            reviewsByMovie[review.movie]!!.add(SimpleReviewDTO(review, scoreByMovie))
        }
        return reviewsByMovie

    }
}