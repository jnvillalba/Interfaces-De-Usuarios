import com.github.unqUi.model.CategoryError
import com.github.unqUi.model.RottenTomatoesSystem
import io.javalin.http.Context
import dto.*



class CategoriesController(private val rottenTomatoesSystem: RottenTomatoesSystem){

    fun getCategories(ctx : Context) {
        val categoriesDTO = rottenTomatoesSystem.categories.parallelStream().map{CategoryDTO(it)}.toList()
        ctx.json(ResultDTO(categoriesDTO)).status(200)
    }

    fun getCategoryById(ctx : Context) {
        val scoreByMovie = Utils.getScoreByMovie(rottenTomatoesSystem)
        try {
            val category = rottenTomatoesSystem.getCategoryById(ctx.pathParam("id"))
            val moviesDTO = rottenTomatoesSystem.movies.parallelStream().filter{movie -> movie.categories.contains(category)}.map{SimpleMovieDTO(it, scoreByMovie)}.toList()
            ctx.json(ResultDTO(moviesDTO)).status(200)
        } catch (exc: CategoryError) {
            ctx.status(404)
            ctx.json(ErrorDTO(exc.message!!))
        }
    }
}