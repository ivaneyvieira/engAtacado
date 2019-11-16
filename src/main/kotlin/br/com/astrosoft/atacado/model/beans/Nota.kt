package br.com.astrosoft.atacado.model.beans

import java.time.LocalDate

class Nota(val loja: String, val pedido: String, val data: LocalDate, val usuario: String, val cliente: String) {
  val produto: List<ItensNota>
    get() = emptyList()
  val numero: String
    get() = ""
  val status: String
    get() = ""
}