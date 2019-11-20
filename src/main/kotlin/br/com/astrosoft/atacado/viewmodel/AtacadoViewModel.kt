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
      SAIDA   -> pesquisaItemNota(storenoPedido)
      ENTRADA -> pesquisaItemNota(storenoNota)
    }
  }

  private fun pesquisaItemNota(storeno: Int) {
    val numeroNota = view.numeroNota
    if(numeroNota.isBlank()) throw ENumeroOperacao()
    val nota = when(storeno) {
      storenoPedido -> saci.findPedido(storeno, numeroNota)
      storenoNota   -> saci.findNotaEntrada(storeno, numeroNota)
      else          -> null
    }
    if(nota?.produtos?.isEmpty() == true) throw ENotaPedidoSemProdutos()
    view.nota = nota
    if(nota == null) view.showInformation("Pedido/Nota não encontrado")
  }

  fun processamento() = exec {
    val nota = view.nota ?: throw EDadosNaoSelecionado()
    val tipoNota = view.tipoNota ?: throw ETipoOperacaoInvalido()
    if(nota.isProcessada) throw ENotaProcessada()
    when(tipoNota) {
      SAIDA   -> processaPedido(nota)
      ENTRADA -> processaNota(nota)
    }
    view.showInformation("Processamento concluido com sucesso!!!")
  }

  private fun processaNota(nota: Nota) {
    saci.criaNotaTransferencia(storenoNota, storenoPedido, nota)
    pesquisa()
  }

  private fun processaPedido(nota: Nota) {
    saci.criaNotaTransferencia(storenoPedido, storenoNota, nota)
    pesquisa()
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
class ENumeroOperacao: EViewModelError("O numero da Nota/Pedido é inválido")
class EStatusOperacao(val status: String): EViewModelError("O status $status não é aceito")
class EDadosNaoSelecionado: EViewModelError("Dados da Nota/Pedido não foram selecionado")
class ENotaProcessada(): EViewModelError("Nota/Pedido já foi processado")
class ENotaPedidoSemProdutos(): EViewModelError("A Nota/Pedido está sem produtos")