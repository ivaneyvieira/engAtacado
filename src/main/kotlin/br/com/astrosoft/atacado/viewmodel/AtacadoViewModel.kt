package br.com.astrosoft.atacado.viewmodel

import br.com.astrosoft.atacado.model.beans.Nota
import br.com.astrosoft.atacado.model.enum.ETipoNota
import br.com.astrosoft.atacado.model.saci
import br.com.astrosoft.framework.viewmodel.EViewModelError
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class AtacadoViewModel(view: IAtacadoView): ViewModel<IAtacadoView>(view) {
  private val storenoPedido = 4
  private val storenoNota = 10

  fun tiposNota() = ETipoNota.values().toList()

  fun pesquisa() = exec {
    val tipoNota = view.tipoNota ?: throw ETipoNotaInvalido()
    val numeroNota = view.numeroNota
    if(numeroNota.isBlank()) throw ENumeroNotaInvalidada()
    val nota = saci.findPedido(storenoPedido, numeroNota)
    view.nota = nota
    nota?.let {nota ->
      if(nota.status != 1 && nota.status != 4) {
        view.clear()
        throw EStatusPedidoInvalido(nota.statusDescricao)
      }
    }
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

  fun clear()
}

class ETipoNotaInvalido: EViewModelError("O tipo de nota é inválido")
class ENumeroNotaInvalidada: EViewModelError("O numero da nota é inválido")
class EStatusPedidoInvalido(val status: String): EViewModelError("O status $status não é aceito")