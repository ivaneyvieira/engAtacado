package br.com.astrosoft.atacado.model.beans

import br.com.astrosoft.atacado.model.saci
import java.util.*

class Nota(val storeno: String,
           val numero: String,
           val data: Date,
           val userno: Int,
           val userName: String,
           val cliente: String,
           val status: Int,
           val origem: String,
           val pedido: String,
           val nfno: String) {
  val statusDescricao: String
    get() = when(status) {
      0    -> "Incluido"
      1    -> "Orcamento"
      2    -> "ReservaA"
      3    -> "Vendido"
      4    -> "Exprirado"
      5    -> "Cancelado"
      6    -> "ReservaB"
      7    -> "Transito"
      8    -> "Entrega Futura"
      90   -> "Nota de Entrada"
      91   -> "Nota de Saida"
      else -> "Erro"
    }
  private var _produtos = mutableListOf<ItensNota>()
  val produtos
    get() = _produtos.toList()

  fun initProdutos(produtos: List<ItensNota>) {
    _produtos = mutableListOf<ItensNota>()
    _produtos.addAll(produtos)
  }

  val valor
    get() = produtos.sumBy {
      val valor = it.quant * it.preco * 100
      valor.toInt()
    } / 100.00
  val notaSaida: String
    get() {
      return when(origem) {
        "P"  -> notaSaida(4)
        "E"  -> notaSaida(10)
        else -> ""
      }
    }
  val notaEntrada: String
    get() {
      return when(origem) {
        "P"  -> notaEntrada(10)
        "E"  -> notaEntrada(4)
        else -> ""
      }
    }

  private fun notaEntrada(storeno: Int): String {
    return saci.findNotaEntrada(storeno, "", nfno)?.numero ?: ""
  }

  private fun notaSaida(storeno: Int): String {
    return saci.findNotaSaida(storeno, "", nfno)?.numero ?: ""
  }

  val isProcessada
    get() = notaSaida.isNotEmpty() && notaEntrada.isNotEmpty()
}