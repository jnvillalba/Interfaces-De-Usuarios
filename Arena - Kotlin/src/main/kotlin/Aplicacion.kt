package org.example.org.example

import RottenTomatoesSystemViewModel
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window

class Aplicacion : Application() {
    override fun createMainWindow(): Window<*> {
        return RottenTomatoesWindow(this, RottenTomatoesSystemViewModel())
    }
}

fun main(){
    Aplicacion().start()
}