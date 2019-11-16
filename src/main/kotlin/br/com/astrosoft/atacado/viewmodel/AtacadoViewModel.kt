package br.com.astrosoft.atacado.viewmodel

import br.com.astrosoft.atacado.model.beans.Nota
import br.com.astrosoft.atacado.model.enum.ETipoNota
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class AtacadoViewModel(view: IAtacadoView): ViewModel<IAtacadoView>(view) {
  fun tiposNota() = ETipoNota.values().toList()

  fun pesquisa() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  fun processamento() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  fun desfaz() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

interface IAtacadoView: IView {
  val tipoNota: ETipoNota?
  val numeroNota: String
  var nota: Nota?
}