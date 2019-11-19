package br.com.astrosoft.atacado.viewmodel

import br.com.astrosoft.atacado.model.beans.Nota
import br.com.astrosoft.atacado.model.enum.ETipoNota
import br.com.astrosoft.atacado.model.enum.ETipoNota.ENTRADA
import br.com.astrosoft.atacado.model.enum.ETipoNota.SAIDA
import br.com.astrosoft.atacado.model.saci
import br.com.astrosoft.framework.viewmodel.EViewModelError
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel

class AtacadoViewModel(view: IAtacadoView): ViewModel<IAtacadoView>(view) {
  private val storenoPedido = 4
  private val storenoNota = 10

  fun tiposNota() = ETipoNota.values().toList()

  fun pesquisa() = exec {
    val tipoNota = view.tipoNota ?: throw ETipoOperacaoInvalido()
    when(tipoNota) {
      SAIDA   -> pesquisaPedido()
      ENTRADA -> pesquisaNota()
    }
  }

  private fun pesquisaNota() {
    val numeroNota = view.numeroNota
    if(numeroNota.isBlank()) throw ENumeroOperacao()
    val nota = saci.findNotaSaida(storenoNota, numeroNota)
    view.nota = nota
  }

  private fun pesquisaPedido() {
    val numeroNota = view.numeroNota
    if(numeroNota.isBlank()) throw ENumeroOperacao()
    val nota = saci.findPedido(storenoPedido, numeroNota)
    view.nota = nota
  }

  fun processamento() = exec {
    val nota = view.nota ?: throw EDadosNaoSelecionado()
    val tipoNota = view.tipoNota ?: throw ETipoOperacaoInvalido()
    when(tipoNota) {
      SAIDA   -> processaPedido(nota)
      ENTRADA -> processaNota(nota)
    }
  }

  private fun processaNota(nota: Nota) {
    saci.criaNotaTransferencia(storenoNota, storenoPedido, nota)
  }

  private fun processaPedido(nota: Nota) {
    saci.criaNotaTransferencia(storenoPedido, storenoNota, nota)
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

class ETipoOperacaoInvalido: EViewModelError("O tipo de operação é inválido")
class ENumeroOperacao: EViewModelError("O numero da operação é inválido")
class EStatusOperacao(val status: String): EViewModelError("O status $status não é aceito")
class EDadosNaoSelecionado: EViewModelError("Dados não selecionado")