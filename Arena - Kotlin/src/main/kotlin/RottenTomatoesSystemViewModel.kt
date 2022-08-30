import com.github.unqUi.model.*
import org.uqbar.commons.model.annotations.Observable

@Observable
class RottenTomatoesSystemViewModel( val rottenTomatoesSystem : RottenTomatoesSystem = getRottenTomatoesSystem()) {

    // Peliculas
    lateinit var moviesList: List<MovieModel>

    init { updateMovies() }

    var selectedMovie : MovieModel? = null
        set(value) {
            check = true
            field = value
        }

    var check = false

    private fun updateMovies() {
        moviesList = rottenTomatoesSystem.movies.map{MovieModel(it.id,it.title, it.description,it.poster, it.categories,it.relatedContent)}

    }

    fun addMovie(movie: DraftMovieModel) {
        rottenTomatoesSystem.addMovie(DraftMovie(movie.title, movie.description, movie.poster, movie.draftCategories.map { Category(it.id, it.name) }.toMutableList(), movie.draftMovies.map{Movie(it.id, it.title, it.description, it.poster, it.categories, it.relatedContent)}.toMutableList()))
        updateMovies()
    }

    fun editMovie(id: String, movie: DraftMovieModel,) {
        rottenTomatoesSystem.editMovie(id, DraftMovie(movie.title, movie.description, movie.poster, movie.draftCategories.map { Category(it.id, it.name) }.toMutableList(), movie.draftMovies.map{Movie(it.id, it.title, it.description, it.poster, it.categories, it.relatedContent)}.toMutableList()))
        updateMovies()
        println()
    }

    // Categorias

    lateinit var categoriesList: List<CategoryModel>

    init { updateCategories() }

    var selectedCategory: CategoryModel? = null
        set(value) {
            check = true
            field = value
        }



    fun addCategory(category: DraftCategoryModel) {
        rottenTomatoesSystem.addCategory(DraftCategory(category.name))
        updateCategories()
    }

    fun editCategory(id: String, category: DraftCategoryModel,) {
        rottenTomatoesSystem.editCategory(id, DraftCategory(category.name))
        updateCategories()
    }

    private fun updateCategories() {
        categoriesList = rottenTomatoesSystem.categories.map{CategoryModel(it.id,it.name)}
    }

}
    // Modelos
@Observable
class MovieModel(var id: String, var title: String, var description: String, var poster: String, var categories: MutableList<Category>, var relatedContent: MutableList<Movie>){
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieModel

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (poster != other.poster) return false
        if (categories != other.categories) return false
        if (relatedContent != other.relatedContent) return false

        return true
    }
}


@Observable
class CategoryModel(val id: String, var name: String){
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryModel

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }
}

@Observable
class DraftMovieModel {
    var title = ""
    var description = ""
    var poster = ""
    var categories = mutableListOf<Category>()
    var relatedContent = mutableListOf<Movie>()
    var categoriesAvailable = mutableListOf<CategoryModel>()
    var moviesAvailable = mutableListOf<MovieModel>()
    var selectedCategory : CategoryModel? = null
    var selectedMovie : MovieModel? = null
    var draftCategories = mutableListOf<CategoryModel>()
    var draftMovies = mutableListOf<MovieModel>()
}

@Observable
class DraftCategoryModel {
    var name = ""
}