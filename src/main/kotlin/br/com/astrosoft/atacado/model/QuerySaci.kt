package br.com.astrosoft.atacado.model

import br.com.astrosoft.atacado.model.beans.ItensNota
import br.com.astrosoft.atacado.model.beans.Nota
import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.framework.util.DB
import br.com.astrosoft.framework.util.lpad

class QuerySaci: QueryDB(driver, url, username, password) {
  fun findPedido(storeno: Int, numero: String): Nota? =
    findNota(storeno, numero, sqlNota = "/sql/pedido.sql", sqlItens = "/sql/itensPedido.sql")

  fun findNotaSaida(storeno: Int, numero: String): Nota? =
    findNota(storeno, numero, sqlNota = "/sql/notaSaida.sql", sqlItens = "/sql/itensNotaSaida.sql")

  private fun findNota(storeno: Int, numero: String, sqlNota: String, sqlItens: String): Nota? {
    return query(sqlNota) {q ->
      q.addParameter("storeno", storeno)
      q.addParameter("numero", numero)
      q.executeAndFetchFirst(Nota::class.java)
    }?.apply {
      this.initProdutos(query(sqlItens) {q ->
        q.addParameter("storeno", storeno)
        q.addParameter("numero", numero)
        q.executeAndFetch(ItensNota::class.java)
      })
    }
  }

  fun criaNotaEntrada(storenoEntrada: Int, storenoSaida: Int, nota: Nota) {
    transaction {
      val numero = newNfno(storenoEntrada)
      val invno = newInvno()
      insertNotaEntrada(storenoEntrada, storenoSaida, numero, invno, nota)
      nota.produtos.forEach {item ->
        insertItemNotaEntrada(storenoEntrada, numero, invno, item)
      }
    }
  }

  fun criaNotaSaida(storenoSaida: Int, storenoEntrada: Int, nota: Nota) {
    transaction {
      val numero = newNfno(storenoSaida)
      val xano = newXano(storenoSaida)
      insertNotaSaida(storenoSaida, storenoEntrada, numero, xano, nota)
      nota.produtos.forEach {item ->
        insertItemNotaSaida(storenoSaida, numero, xano, item)
      }
    }
  }

  fun criaNotaTransferencia(storenoSaida: Int, storenoEntrada: Int, nota: Nota) {
    transaction {
      criaNotaSaida(storenoSaida, storenoEntrada, nota)
      criaNotaEntrada(storenoEntrada, storenoSaida, nota)
    }
  }

  private fun insertItemNotaEntrada(storenoEntrada: Int, numero: Int, invno: Int, item: ItensNota) {
    val sql = "/sql/insertItemNotaEntrada.sql"
    val prdno = item.prdno.lpad(16, " ")
    val grade = item.grade
    val cost = item.preco
    val qtty = item.quant
    script(sql) {q ->
      q.addOptionalParameter("storeno", storenoEntrada)
      q.addOptionalParameter("invno", invno)
      q.addOptionalParameter("numero", numero)
      q.addOptionalParameter("prdno", prdno)
      q.addOptionalParameter("grade", grade)
      q.addOptionalParameter("cost", cost)
      q.addOptionalParameter("qtty", qtty)
      q.executeUpdate()
    }
    salvaStk(storenoEntrada, numero, prdno, grade, qtty)
  }

  private fun insertItemNotaSaida(storenoSaida: Int, numero: Int, xano: Int, item: ItensNota) {
    val sql = "/sql/insertItemNotaSaida.sql"
    val prdno = item.prdno.lpad(16, " ")
    val grade = item.grade
    val cost = item.preco
    val qtty = item.quant
    script(sql) {q ->
      q.addOptionalParameter("storeno", storenoSaida)
      q.addOptionalParameter("xano", xano)
      q.addOptionalParameter("numero", numero)
      q.addOptionalParameter("prdno", prdno)
      q.addOptionalParameter("grade", grade)
      q.addOptionalParameter("cost", cost)
      q.addOptionalParameter("qtty", qtty)
      q.executeUpdate()
    }
    salvaStk(storenoSaida, numero, prdno, grade, -qtty)
  }

  private fun insertNotaEntrada(storenoEntrada: Int, storenoSaida: Int, numero: Int, invno: Int, nota: Nota) {
    val sql = "/sql/insertNotaEntrada.sql"
    val ordno = nota.numero
    val valor = nota.valor
    val vendno = findVendno(storenoSaida)
    script(sql) {q ->
      q.addOptionalParameter("invno", invno)
      q.addOptionalParameter("vendno", vendno)
      q.addOptionalParameter("numero", numero)
      q.addOptionalParameter("storeno", storenoEntrada)
      q.addOptionalParameter("ordno", ordno)
      q.addOptionalParameter("valor", valor)
      q.executeUpdate()
    }
  }

  private fun insertNotaSaida(storenoSaida: Int, storenoEntrada: Int, numero: Int, xano: Int, nota: Nota) {
    val sql = "/sql/insertNotaSaida.sql"
    val ordno = nota.numero
    val valor = nota.valor
    val custno = findCustno(storenoEntrada)
    script(sql) {q ->
      q.addOptionalParameter("xano", xano)
      q.addOptionalParameter("custno", custno)
      q.addOptionalParameter("numero", numero)
      q.addOptionalParameter("storeno", storenoSaida)
      q.addOptionalParameter("ordno", ordno)
      q.addOptionalParameter("valor", valor)
      q.executeUpdate()
    }
  }

  private fun newNfno(storeno: Int): Int {
    val sql = "SELECT MAX(no) +1 FROM lastno WHERE se = '66' AND storeno = :storeno"
    return query(sql) {q ->
      q.addParameter("storeno", storeno)
      q.executeScalar(Int::class.java)
    } ?: 1
  }

  private fun newXano(storeno: Int): Int {
    val sql = "SELECT MAX(xano) +1 FROM sqldados.nf WHERE pdvno = 0 AND storeno = :storeno"
    return query(sql) {q ->
      q.addParameter("storeno", storeno)
      q.executeScalar(Int::class.java)
    } ?: 1
  }

  private fun newInvno(): Int {
    val sql = "SELECT MAX(invno) + 1 FROM sqldados.inv"
    return query(sql) {q ->
      q.executeScalar(Int::class.java)
    } ?: 1
  }

  private fun salvaStk(storeno: Int, pedido: Int, prdno: String, grade: String, qtty: Double) {
    val sql = "/sql/salvaStk.sql"
    script(sql) {q ->
      q.addOptionalParameter("storeno", storeno)
      q.addOptionalParameter("numero", pedido)
      q.addOptionalParameter("prdno", prdno.lpad(16, " "))
      q.addOptionalParameter("grade", grade)
      q.addOptionalParameter("qtty", qtty)
      q.executeUpdate()
    }
  }

  private fun findVendno(storeno: Int): Int {
    val sql = """select V.no 
      |from sqldados.vend AS V inner 
      |join sqldados.store AS S on S.cgc = V.cgc 
      |where S.no = :storeno
      |""".trimMargin()
    return query(sql) {q ->
      q.addParameter("storeno", storeno)
      q.executeScalar(Int::class.java)
    }
  }

  private fun findCustno(storeno: Int): Int {
    val sql = """select C.no 
      |from sqldados.custp AS C 
      |  inner join sqldados.store AS S 
      |  on S.cgc = C.cpf_cgc 
      |where S.no = :storeno
      |""".trimMargin()
    return query(sql) {q ->
      q.addParameter("storeno", storeno)
      q.executeScalar(Int::class.java)
    }
  }

  companion object {
    private val db = DB("saci")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
    internal val test = db.test
    val ipServer = url.split("/").getOrNull(2)
  }
}

val saci = QuerySaci()