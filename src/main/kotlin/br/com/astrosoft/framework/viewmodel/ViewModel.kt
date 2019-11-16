package br.com.astrosoft.framework.viewmodel

open class ViewModel<V: IView>(val view: V) {}

interface IView {
  fun showError(msg: String)
  fun showWarning(msg: String)
  fun showInformation(msg: String)
}