package br.com.astrosoft.atacado.view

import br.com.astrosoft.atacado.model.beans.ItensNota
import br.com.astrosoft.atacado.model.beans.Nota
import br.com.astrosoft.atacado.model.enum.ETipoNota
import br.com.astrosoft.atacado.model.enum.ETipoNota.ENTRADA
import br.com.astrosoft.atacado.model.enum.ETipoNota.SAIDA
import br.com.astrosoft.atacado.viewmodel.AtacadoViewModel
import br.com.astrosoft.atacado.viewmodel.IAtacadoView
import br.com.astrosoft.framework.view.ViewLayout
import com.github.mvysny.karibudsl.v10.addColumnFor
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.comboBox
import com.github.mvysny.karibudsl.v10.formLayout
import com.github.mvysny.karibudsl.v10.grid
import com.github.mvysny.karibudsl.v10.h6
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.isExpand
import com.github.mvysny.karibudsl.v10.label
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.provider.DataProvider
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import java.time.format.DateTimeFormatter

@Route("")
@Theme(value = Lumo::class, variant = Lumo.DARK)
class AtacadoLayout: ViewLayout<IAtacadoView, AtacadoViewModel>(), IAtacadoView {
  private var edtPedidoNota: TextField? = null
  private var comboTipoNota: ComboBox<ETipoNota>? = null
  private var edtCliente: TextField? = null
  private var edtUsuario: TextField? = null
  private var edtNf: TextField? = null
  private var edtStatus: TextField? = null
  private var edtData: TextField? = null
  private var edtNumero: TextField? = null
  private var edtLoja: TextField? = null
  override val viewModel = AtacadoViewModel(this)
  val dataProviderDados = DataProvider.ofCollection(emptyList<ItensNota>())
  override val tipoNota: ETipoNota?
    get() = comboTipoNota?.value
  override val numeroNota: String
    get() = edtPedidoNota?.value ?: ""
  var notaSelecionada: Nota? = null
  override var nota: Nota?
    get() = notaSelecionada
    set(value) {
      notaSelecionada = value
      if(value == null) {
        edtLoja?.value = ""
        edtPedidoNota?.value = ""
        edtData?.value = ""
        edtStatus?.value = ""
        edtNf?.value = ""
        edtUsuario?.value = ""
        edtCliente?.value = ""
      }
      else {
        edtLoja?.value = value.loja
        edtPedidoNota?.value = value.pedido
        edtData?.value = value.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        edtStatus?.value = ""
        edtNf?.value = value.numero
        edtUsuario?.value = value.usuario
        edtCliente?.value = value.cliente
      }
    }

  init {
    setSizeFull()
    titulo()
    filtro()
    cabercalhoDados()
    gridDados()
  }

  fun titulo() {
    h6("Estoque varejo")
  }

  fun filtro() {
    label("Filtro")
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
        value = SAIDA
        updateCampos(value)
      }
      edtPedidoNota = textField("Número Pedido/Nota") {}
      horizontalLayout {
        alignItems = Alignment.BASELINE
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
      ENTRADA -> edtPedidoNota?.label = "Nota fiscal loja 10"
      else    -> edtPedidoNota?.label = "Pedido loja 10"
    }
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
        edtLoja = textField("Loja")
        edtNumero = textField("Número")
        edtData = textField("Data")
        edtStatus = textField("Status")
        edtNf = textField("NF")
        edtUsuario = textField("Usuário")
        edtCliente = textField("Cliente")

        setColspan(edtCliente, 3)
      }
    }
  }

  fun gridDados() {
    grid(dataProvider = dataProviderDados) {
      isExpand = true
      //setSizeFull()
      addColumnFor(ItensNota::codigo)
      addColumnFor(ItensNota::descricao)
      addColumnFor(ItensNota::grade)
      addColumnFor(ItensNota::localizacao)
      addColumnFor(ItensNota::quantidade)
      addColumnFor(ItensNota::custoContabil)
    }
  }
}