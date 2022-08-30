package org.example.org.example

import AddOrEditCategory
import AddOrEditMovie
import CategoryModel
import DraftCategoryModel
import DraftMovieModel
import MovieModel
import RottenTomatoesSystemViewModel
import com.github.unqUi.model.CategoryError
import com.github.unqUi.model.MovieError
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class RottenTomatoesWindow(owner: WindowOwner, model: RottenTomatoesSystemViewModel) : SimpleWindow<RottenTomatoesSystemViewModel>(owner, model){

    override fun addActions(mainPanel: Panel) {}

    override fun createFormPanel(mainPanel: Panel) {

        title = "Rotten Tomatoes"

        table<CategoryModel>(mainPanel) {
            bindItemsTo("categoriesList")
            bindSelectionTo("selectedCategory")
            visibleRows = 7
            column {
                title = "ID"
                bindContentsTo("id")
            }

            column {
                title = "Name"
                bindContentsTo("name")
            }

            Button(mainPanel) with {
                caption = "Add Category"
                onClick {
                    val category = DraftCategoryModel()
                    val view = AddOrEditCategory(this@RottenTomatoesWindow, category)
                    view.onAccept {
                        try {
                            modelObject.addCategory(category)
                        } catch (e: CategoryError) {
                            throw UserException(e.message)
                        }
                    }
                    view.open()
                }
            }

            Button(mainPanel) with {
                caption = "Edit Category"
                onClick {
                    val selectedCategory = modelObject.selectedCategory
                    selectedCategory?.let {
                        val category = DraftCategoryModel()
                        category.name = selectedCategory.name
                        val view = AddOrEditCategory(this@RottenTomatoesWindow, category)
                        view.onAccept {
                            try {
                                modelObject.editCategory(selectedCategory.id, category)
                            } catch (e: CategoryError) {
                                throw UserException(e.message)
                            }
                        }
                        view.open()
                    }
                }
            }
        }


        table<MovieModel>(mainPanel) {
            bindItemsTo("moviesList")
            bindSelectionTo("selectedMovie")
            visibleRows = 10
            column {
                title = "ID"
                fixedSize = 100
                bindContentsTo("id")
            }

            column {
                title = "Title"
                fixedSize = 250
                bindContentsTo("title")
            }

            column {
                title = "Poster"
                fixedSize = 250
                bindContentsTo("poster")
            }
        }

        Button(mainPanel) with {
            caption = "Add Movie"
            onClick {
                val movie = DraftMovieModel()
                val categoriesList = modelObject.categoriesList
                val moviesList = modelObject.moviesList
                movie.categoriesAvailable.addAll(categoriesList.filter{!movie.categories.any{ c -> c.id == it.id }}.map{ CategoryModel(it.id, it.name)}.toMutableList())
                movie.moviesAvailable.addAll(moviesList.filter{!movie.relatedContent.any{ m -> m.id == it.id }}.map{ MovieModel(it.id, it.title, it.description, it.poster, it.categories, it.relatedContent)}.toMutableList())
                movie.draftCategories.addAll(categoriesList.filter{movie.categories.any{ c -> c.id == it.id }}.map{ CategoryModel(it.id, it.name)}.toMutableList())
                movie.draftMovies.addAll(moviesList.filter{movie.relatedContent.any{ m -> m.id == it.id }}.map{ MovieModel(it.id, it.title, it.description, it.poster, it.categories, it.relatedContent)}.toMutableList())
                val view = AddOrEditMovie(this@RottenTomatoesWindow, movie)
                view.onAccept {
                    try {
                        modelObject.addMovie(movie)
                    } catch (e: MovieError) {
                        throw UserException(e.message)
                    }
                }
                view.open()
            }
        }

        Button(mainPanel) with {
            caption = "Edit Movie"
            onClick {
                val selectedMovie = modelObject.selectedMovie

                selectedMovie?.let {
                    val movie = DraftMovieModel()
                    movie.title = selectedMovie.title
                    movie.description = selectedMovie.description
                    movie.poster = selectedMovie.poster
                    movie.categories = selectedMovie.categories
                    movie.relatedContent = selectedMovie.relatedContent
                    val categoriesList = modelObject.categoriesList
                    val moviesList = modelObject.moviesList
                    movie.categoriesAvailable.addAll(categoriesList.filter{!movie.categories.any{ c -> c.id == it.id }}.map{ CategoryModel(it.id, it.name)}.toMutableList())
                    movie.moviesAvailable.addAll(moviesList.filter{it.id != selectedMovie.id && !movie.relatedContent.any{ m -> m.id == it.id }}.map{ MovieModel(it.id, it.title, it.description, it.poster, it.categories, it.relatedContent)}.toMutableList())
                    movie.draftCategories.addAll(categoriesList.filter{movie.categories.any{ c -> c.id == it.id }}.map{ CategoryModel(it.id, it.name)}.toMutableList())
                    movie.draftMovies.addAll(moviesList.filter{movie.relatedContent.any{ m -> m.id == it.id }}.map{ MovieModel(it.id, it.title, it.description, it.poster, it.categories, it.relatedContent)}.toMutableList())


                    val view = AddOrEditMovie(this@RottenTomatoesWindow, movie)
                    view.onAccept {
                        try {
                            modelObject.editMovie(selectedMovie.id, movie)

                        } catch (e: MovieError) {
                            throw UserException(e.message)
                        }
                    }
                    view.open()
                }
            }
        }
    }
}



