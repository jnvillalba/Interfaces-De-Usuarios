package dto

import com.github.unqUi.model.Category
import com.github.unqUi.model.Movie
import com.github.unqUi.model.Review
import com.github.unqUi.model.User

data class ResultDTO<E>(
    val result: List<E>
)

data class UserLoginDTO(
    val email:	  String,
    val password: String
)

data class UserDTO(
    val id:	    String,
    val name:   String,
    val image: 	String,
    val email:	String
) {
    constructor(user: User): this(
        user.id,
        user.name,
        user.image,
        user.email
    )
}

data class ErrorDTO(
    val message:    String
)

data class UserRegisterDTO(
    val email:	    String,
    val password:   String,
    val image: 	String,
    val name:	String
) {
    constructor(user: User): this(
        user.email,
        user.password,
        user.image,
        user.name
    )
}

data class CategoryDTO(
    val id:	    String,
    val name:   String
) {
    constructor(category: Category): this(
        category.id,
        category.name
    )
}

data class SimpleMovieDTO(
    val id:	    String,
    val title:   String,
    val description: 	String,
    val poster:	String,
    val categories: List<CategoryDTO>,
    val score: Float?
) {
    constructor(movie: Movie, scoreByMovie: Map<Movie, Float>): this(
        movie.id,
        movie.title,
        movie.description,
        movie.poster,
        movie.categories.parallelStream().map{CategoryDTO(it)}.toList(),
        scoreByMovie[movie]
    )
}

data class SimpleReviewDTO(
    val id:	    String,
    val user:   UserDTO,
    val movie: 	SimpleMovieDTO,
    val text:	String,
    val stars:  Int,
) {
    constructor(review: Review, scoreByMovie: Map<Movie, Float>): this(
        review.id,
        UserDTO(review.user),
        SimpleMovieDTO(review.movie, scoreByMovie),
        review.text,
        review.stars
    )
}




data class UserWithReviewsDTO(
    val id:	    String,
    val name:   String,
    val image: 	String,
    val email:	String,
    val reviews: List<SimpleReviewDTO>
) {
    constructor(user: User, scoreByMovie: Map<Movie, Float>): this(
        user.id,
        user.name,
        user.image,
        user.email,
        user.reviews.parallelStream().map{SimpleReviewDTO(it, scoreByMovie)}.toList()
    )
}

data class ResultSimpleMoviesDTO(
    val result:	List<SimpleMovieDTO>
)

data class MovieDTO(
    val id:	            String,
    val title:          String,
    val description: 	String,
    val poster:	        String,
    val categories:	    List<CategoryDTO>,
    val relatedContent: List<SimpleMovieDTO>,
    val score: Float?,
    val reviews: List<SimpleReviewDTO>?
) {
    constructor(movie: Movie, scoreByMovie: Map<Movie, Float>, reviews: List<SimpleReviewDTO>?): this(
        movie.id,
        movie.title,
        movie.description,
        movie.poster,
        movie.categories.parallelStream().map{CategoryDTO(it)}.toList(),
        movie.relatedContent.parallelStream().map{SimpleMovieDTO(it, scoreByMovie)}.toList(),
        scoreByMovie[movie],
        reviews
    )
}

data class ReviewDTO(
    val text: String,
    val stars: Int
)

data class ResultCategoriesDTO(
    val result: List<CategoryDTO>
)