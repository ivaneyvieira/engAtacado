package br.com.astrosoft.atacado.view

import br.com.astrosoft.atacado.model.beans.ItensNota
import br.com.astrosoft.atacado.model.beans.Nota
import br.com.astrosoft.atacado.model.enum.ETipoNota
import br.com.astrosoft.atacado.model.enum.ETipoNota.ENTRADA
import br.com.astrosoft.atacado.model.enum.ETipoNota.SAIDA
import br.com.astrosoft.atacado.viewmodel.AtacadoViewModel
import br.com.astrosoft.atacado.viewmodel.IAtacadoView
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.framework.view.ViewLayout
import com.github.mvysny.karibudsl.v10.addColumnFor
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.comboBox
import com.github.mvysny.karibudsl.v10.formLayout
import com.github.mvysny.karibudsl.v10.grid
import com.github.mvysny.karibudsl.v10.h6
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.isExpand
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep
import com.vaadin.flow.component.grid.ColumnTextAlign.END
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.provider.DataProvider
import com.vaadin.flow.data.renderer.NumberRenderer
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import java.text.DecimalFormat

@Route("")
@Theme(value = Lumo::class, variant = Lumo.DARK)
class AtacadoLayout: ViewLayout<IAtacadoView, AtacadoViewModel>(), IAtacadoView {
  private var edtPedidoNota: TextField? = null
  private var comboTipoNota: ComboBox<ETipoNota>? = null
  private var edtCliente: TextField? = null
  private var edtUsuario: TextField? = null
  private var edtStatus: TextField? = null
  private var edtData: TextField? = null
  private var edtNumero: TextField? = null
  private var edtNfSaida: TextField? = null
  private var edtNfEntrada: TextField? = null
  override val viewModel = AtacadoViewModel(this)
  val dataProviderDados = DataProvider.ofCollection(mutableListOf<ItensNota>())
  override val tipoNota: ETipoNota?
    get() = comboTipoNota?.value
  override val numeroNota: String
    get() = edtPedidoNota?.value ?: ""
  var notaSelecionada: Nota? = null
  override var nota: Nota?
    get() = notaSelecionada
    set(value) {
      notaSelecionada = value
      if(value == null) clearNotaValue()
      else setNotaValue(value)
    }

  private fun setNotaValue(value: Nota) {
    edtNumero?.value = value.numero
    edtData?.value = value.data.format()
    edtStatus?.value = ""
    edtUsuario?.value = value.userName
    edtCliente?.value = value.cliente
    edtStatus?.value = value.statusDescricao
    edtNfSaida?.value = value.notaSaida
    edtNfEntrada?.value = value.notaEntrada
    dataProviderDados.items.clear()
    dataProviderDados.items.addAll(value.produtos)
    dataProviderDados.refreshAll()
  }

  override fun clear() {
    edtPedidoNota?.value = ""
    nota = null
    clearNotaValue()
  }

  private fun clearNotaValue() {
    edtNumero?.value = ""
    edtData?.value = ""
    edtStatus?.value = ""
    edtUsuario?.value = ""
    edtCliente?.value = ""
    edtStatus?.value = ""
    edtNfSaida?.value = ""
    edtNfEntrada?.value = ""
    dataProviderDados.items.clear()
  }

  init {
    setSizeFull()
    titulo()
    filtro()
    cabercalhoDados()
    gridDados()
    //Inicializa o os campos
    val tipoNota = SAIDA
    comboTipoNota?.value = tipoNota
    updateCampos(tipoNota)
  }

  fun titulo() {
    h6("Estoque varejo")
  }

  fun filtro() {
    horizontalLayout {
      width = "100%"
      isSpacing = true
      //this.isMargin = true
      isPadding = true
      this.style.set("border", "1px solid #9E9E9E")
      comboTipoNota = comboBox<ETipoNota>("Movimentação") {
        isAllowCustomValue = false
        isPreventInvalidInput = true
        setItemLabelGenerator {
          it.descricao
        }
        setItems(viewModel.tiposNota())
        addValueChangeListener {event ->
          val value = event.value
          updateCampos(value)
        }
      }
      edtPedidoNota = textField("Número Pedido/Nota") {}
      horizontalLayout {
        setWidthFull()
        this.justifyContentMode = FlexComponent.JustifyContentMode.END
        button("Pesquisa") {
          addClickListener {
            viewModel.pesquisa()
          }
        }
        button("Processamento") {
          addClickListener {
            viewModel.processamento()
          }
        }
        button("Desfaz") {
          addClickListener {
            viewModel.desfaz()
          }
        }
      }
    }
  }

  private fun updateCampos(value: ETipoNota) {
    when(value) {
      ENTRADA -> {
        edtPedidoNota?.label = "Nota fiscal loja 10"
        edtNumero?.label = "Numero da nota"
      }
      SAIDA   -> {
        edtPedidoNota?.label = "Pedido loja 4"
        edtNumero?.label = "Numero do pedido"
      }
    }
    edtPedidoNota?.focus()
  }

  fun cabercalhoDados() {
    horizontalLayout {
      width = "100%"
      isSpacing = true
      //this.isMargin = true
      isPadding = true
      this.style.set("border", "1px solid #9E9E9E")
      formLayout {
        this.setWidthFull()
        responsiveSteps = listOf(ResponsiveStep("10em", 1),
                                 ResponsiveStep("10em", 2),
                                 ResponsiveStep("10em", 3),
                                 ResponsiveStep("10em", 4),
                                 ResponsiveStep("10em", 5),
                                 ResponsiveStep("10em", 6),
                                 ResponsiveStep("10em", 7),
                                 ResponsiveStep("10em", 8),
                                 ResponsiveStep("10em", 9))

        edtNumero = textField("Número") {
          this.isReadOnly = true
        }
        edtData = textField("Data") {
          this.isReadOnly = true
        }
        edtStatus = textField("Status") {
          this.isReadOnly = true
        }
        edtUsuario = textField("Usuário") {
          this.isReadOnly = true
        }
        edtNfSaida = textField("NF Saída") {
          this.isReadOnly = true
        }
        edtNfEntrada = textField("NF Entrada") {
          this.isReadOnly = true
        }
        edtCliente = textField("Cliente") {
          this.isReadOnly = true
        }

        setColspan(edtCliente, 3)
      }
    }
  }

  fun gridDados() {
    grid(dataProvider = dataProviderDados) {
      isExpand = true
      //setSizeFull()
      addColumnFor(ItensNota::prdno) {
        this.setHeader("Código")
        this.textAlign = END
        this.flexGrow = 1
      }
      addColumnFor(ItensNota::descricao) {
        this.setHeader("Descrição")
        this.flexGrow = 3
      }
      addColumnFor(ItensNota::grade) {
        this.setHeader("Grade")
        this.flexGrow = 1
      }
      addColumnFor(ItensNota::localizacao) {
        this.setHeader("Localização")
        this.flexGrow = 3
      }
      addColumnFor(ItensNota::quant, NumberRenderer(ItensNota::quant, DecimalFormat("0.00"))) {
        this.setHeader("Quant")
        this.textAlign = END
        this.flexGrow = 1
      }
      addColumnFor(ItensNota::preco, NumberRenderer(ItensNota::preco, DecimalFormat("0.0000"))) {
        this.setHeader("Custo")
        this.textAlign = END
        this.flexGrow = 1
      }
    }
  }
}