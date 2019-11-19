package br.com.astrosoft.atacado.model.beans

import java.util.*

class Nota(val storeno: String,
           val numero: String,
           val data: Date,
           val userno: Int,
           val userName: String,
           val cliente: String,
           val status: Int) {
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
}