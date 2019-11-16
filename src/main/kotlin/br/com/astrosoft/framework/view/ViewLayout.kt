package br.com.astrosoft.framework.view

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import org.claspina.confirmdialog.ConfirmDialog

abstract class ViewLayout<V: IView, VM: ViewModel<V>>(): VerticalLayout(), IView {
  abstract val viewModel: VM

  override fun showError(msg: String) {
    ConfirmDialog.createError().withCaption("Erro do aplicativo").withMessage(msg).open()
  }

  override fun showWarning(msg: String) {
    ConfirmDialog.createWarning().withCaption("Aviso").withMessage(msg).open()
  }

  override fun showInformation(msg: String) {
    ConfirmDialog.createInfo().withCaption("Informação").withMessage(msg).open()
  }
}