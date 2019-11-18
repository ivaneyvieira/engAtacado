package br.com.astrosoft.framework.viewmodel

open class ViewModel<V: IView>(val view: V) {
  fun exec(block: () -> Unit) {
    try {
      block()
    } catch(e: EViewModelError) {
      view.showError(e.message ?: "Erro generico")
    } catch(e: EViewModelWarning) {
      view.showWarning(e.message ?: "Aviso generico")
    }
  }
}

interface IView {
  fun showError(msg: String)
  fun showWarning(msg: String)
  fun showInformation(msg: String)
}