import com.github.unqUi.model.Movie
import com.github.unqUi.model.Review
import com.github.unqUi.model.RottenTomatoesSystem

class Utils {

    companion object {
        fun getScoreByMovie(rottenTomatoesSystem: RottenTomatoesSystem): Map<Movie, Float> {
            val reviewsByMovie = mutableMapOf<Movie, MutableList<Review>>()
            for (movie in rottenTomatoesSystem.movies) {
                if (!reviewsByMovie.containsKey(movie)) {
                    reviewsByMovie.put(movie, mutableListOf())
                }
            }
            for (review in rottenTomatoesSystem.reviews) {
                reviewsByMovie[review.movie]!!.add(review)
            }
            val scoreByMovie = mutableMapOf<Movie, Float>()
            for (movie in reviewsByMovie.keys) {
                var sum = 0f
                val reviews = reviewsByMovie[movie]!!
                for (review in reviews) {
                    sum += review.stars
                }
                val avg = if (reviews.size > 0) sum / reviews.size else 0f
                scoreByMovie.put(movie, avg)
            }
            return scoreByMovie
        }
    }
}