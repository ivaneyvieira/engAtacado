package br.com.astrosoft.atacado.model

import br.com.astrosoft.atacado.model.beans.ItensNota
import br.com.astrosoft.atacado.model.beans.Nota
import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.framework.util.DB

class QuerySaci: QueryDB(driver, url, username, password) {
  fun findPedido(storeno: Int, numero: String): Nota? {
    val sql = "/sql/pedido.sql"
    return query(sql) {q ->
      q.addParameter("storeno", storeno)
      q.addParameter("numero", numero)
      q.executeAndFetchFirst(Nota::class.java)
    }?.apply {
      val sql = "/sql/itensPedido.sql"
      this.initProdutos(query(sql) {q ->
        q.addParameter("storeno", storeno)
        q.addParameter("numero", numero)
        q.executeAndFetch(ItensNota::class.java)
      })
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