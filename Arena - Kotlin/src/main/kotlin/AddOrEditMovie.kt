import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.arena.widgets.List
import org.uqbar.arena.windows.Dialog

class AddOrEditMovie (owner: WindowOwner, model: DraftMovieModel) : Dialog<DraftMovieModel>(owner, model) {

    override fun addActions(mainPanel: Panel) {}
    override fun createFormPanel(mainPanel: Panel) {}

    override fun createMainTemplate(mainPanel: Panel) {
        title = "Add Movie"

        mainPanel.asVertical()
        addMovieTextPanel(mainPanel)
        panelParaTextoCategorias(mainPanel)
        categoriesPanel(mainPanel, modelObject)
        panelParaTextoRelatedContent(mainPanel)
        relatedContentPanel(mainPanel, modelObject)
    }

    fun addMovieTextPanel(owner: Panel) {
        Panel(owner) with {
            asVertical()

            Label(it) with { text = "Title:" }
            TextBox(it) with {
                bindTo("title")
                width = 500
                align = "center"
            }

            Label(it) with { text = "Description:" }
            TextBox(it) with {
                bindTo("description")
                width = 300
            }

            Label(it) with { text = "Poster:" }
            TextBox(it) with {
                bindTo("poster")
                width = 300
            }
        }
    }

    fun categoriesPanel(owner: Panel, modelObject: DraftMovieModel) {
        Panel(owner) with {
            asHorizontal()
            List<CategoryModel>(it) with {
                bindTo("selectedCategory")
                bindItemsTo("categoriesAvailable").adaptWithProp<CategoryModel>("name")
                setWidth(200)
                setHeight(200)
            }

            Button(it) with {
                caption = ">"
                onClick{
                    val selectedCategory = modelObject.selectedCategory
                    selectedCategory?.let{
                        if (!modelObject.draftCategories.contains(selectedCategory)) {
                            modelObject.draftCategories.add(selectedCategory)
                            modelObject.categoriesAvailable.remove(selectedCategory)
                        }
                    }
                }
                width = 30
                alignCenter()
            }

            Button(it) with {
                caption = "<"
                onClick{
                    val selectedCategory = modelObject.selectedCategory
                    selectedCategory?.let{
                        if (!modelObject.categoriesAvailable.contains(selectedCategory)) {
                            modelObject.draftCategories.remove(selectedCategory)
                            modelObject.categoriesAvailable.add(selectedCategory)
                        }
                    }
                }
                width = 30
                alignCenter()
            }

            List<CategoryModel>(it) with {
                bindTo("selectedCategory")
                bindItemsTo("draftCategories").adaptWithProp<CategoryModel>("name")
                setWidth(200)
                setHeight(200)
            }
        }
    }

    fun panelParaTextoRelatedContent(p1: Panel) {
           Label(p1) with {
                text = "Related Content"
                align = "center"
        }
    }

    fun panelParaTextoCategorias(p1: Panel) {
           Label(p1) with {
                text = "Categorias"
                align = "center"
         }
     }

    fun relatedContentPanel(owner: Panel, modelObject: DraftMovieModel) {
        Panel(owner) with {
            asHorizontal()

            List<MovieModel>(it) with {
                bindTo("selectedMovie")
                bindItemsTo("moviesAvailable").adaptWithProp<MovieModel>("title")
                setWidth(200)
                setHeight(200)
            }

            Button(it) with {
                caption = ">"
                onClick{
                    val selectedMovie = modelObject.selectedMovie
                    selectedMovie?.let {
                        if (!modelObject.draftMovies.contains(selectedMovie)) {
                            modelObject.draftMovies.add(selectedMovie)
                            modelObject.moviesAvailable.remove(selectedMovie)
                        }
                    }
                }
                width = 30
                alignCenter()
            }

            Button(it) with {
                caption = "<"
                onClick{
                    val selectedMovie = modelObject.selectedMovie
                    selectedMovie?.let {
                        if (!modelObject.moviesAvailable.contains(selectedMovie)) {
                            modelObject.draftMovies.remove(selectedMovie)
                            modelObject.moviesAvailable.add(selectedMovie)
                        }
                    }
                }
                width = 30
                alignCenter()
            }

            List<MovieModel>(it) with {
                bindTo("selectedMovie")
                bindItemsTo("draftMovies").adaptWithProp<MovieModel>("title")
                setWidth(200)
                setHeight(200)
            }

            Button(owner) with{
                caption = "Accept"
                onClick {
                    accept()
                }
            }

            Button(owner) with{
                caption = "Cancel"
                onClick {
                    cancel()
                }
            }
        }
    }
}
