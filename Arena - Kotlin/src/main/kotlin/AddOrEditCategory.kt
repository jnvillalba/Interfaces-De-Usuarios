import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class AddOrEditCategory(owner: WindowOwner, model: DraftCategoryModel) : Dialog<DraftCategoryModel>(owner,model){

    override fun createFormPanel(mainPanel: Panel) {
        title = "Agregar Categoria"

        Label(mainPanel) withText "Name: "

        TextBox(mainPanel) with {
            bindTo("name")
        }

        Button(mainPanel) with{
            caption = "Accept"
            onClick {
                if (modelObject.name.isEmpty()) {
                    showError("Se necesita un nombre")

                }else{
                    accept()
                }
            }
        }


        Button(mainPanel) with{
            caption = "Cancel"
            onClick({cancel()})
        }
    }

    override fun addActions(actionsPanel: Panel) {}
}