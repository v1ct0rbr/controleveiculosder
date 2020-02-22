package br.gov.pb.der.controleveiculosder.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pb.der.controleveiculosder.filtros.VeiculoFiltro;
import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.model.SetorCVD;
import br.gov.pb.der.controleveiculosder.model.TipoVeiculo;
import br.gov.pb.der.controleveiculosder.model.Veiculo;
import br.gov.pb.der.controleveiculosder.service.SecretariaService;
import br.gov.pb.der.controleveiculosder.service.SetorCVDService;
import br.gov.pb.der.controleveiculosder.service.TipoVeiculoService;
import br.gov.pb.der.controleveiculosder.service.VeiculoService;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.utils.GenerateReports;
import br.gov.pb.der.controleveiculosder.utils.Parametros;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/menu/impressao")
public class RelatorioController extends MyAbstractController {

	@SuppressWarnings("unused")
	private static final String VIEW_PESQUISA = Parametros.PATH_MENU + "/relatorios";

	@Autowired
	private SetorCVDService setorService;

	@Autowired
	private VeiculoService veiculoService;

	@Autowired
	private SecretariaService secretariaService;

	@Autowired
	private TipoVeiculoService tipoveiculoService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/veiculos", method = RequestMethod.GET)
	public void estagiarios2(@ModelAttribute("filtro") VeiculoFiltro filtro, Pageable pageable, RedirectAttributes attr,
			HttpServletRequest req, HttpServletResponse response) {

		filtro.setPaginar(false);
		Page<Veiculo> estagiarios = veiculoService.filtrar(filtro, pageable);

		Map<String, Object> parametros = null;
		parametros = getEmpresaInfo();
		parametros.put("titulo_relatorio", tituloEstagiariosRelatorio(filtro));

		JRDataSource ds = new JRBeanCollectionDataSource(estagiarios.getContent());
		parametros.put("datasource", ds);

		GenerateReports gr = new GenerateReports();
//		gr.generatePdfReport("classpath:reports/relatorio_veiculos.jrxm", parametros, ds, response);
		gr.generatePdfReport("/reports/relatorio_veiculos.jrxml", parametros, ds, response);

	}

	public String tituloEstagiariosRelatorio(VeiculoFiltro fs) {
		String titulo = "";
		int countParams = 0;

		if (Funcoes.isValidStringValue(fs.getAdesivo())) {
			titulo += " | Nº de adesivo: " + fs.getAdesivo();
			countParams++;
		}

		if (Funcoes.isValidStringValue(fs.getPlaca())) {
			titulo += " | Placa: " + fs.getAdesivo();
			countParams++;
		}

		if (Funcoes.isValidStringValue(fs.getModelo())) {
			titulo += " | Referência: " + fs.getModelo();
			countParams++;
		}
		if (Funcoes.isValidStringValue(fs.getFuncionarioNome())) {
			titulo += " | Nome do Funcionário: " + fs.getFuncionarioNome();
			countParams++;
		}
		System.out.println("secretaria id: " + fs.getSecretariaId());
		if (Funcoes.isValidIntegerParameter(fs.getSecretariaId())) {

			Secretaria sec = secretariaService.findById(fs.getSecretariaId()).get();
			if (sec != null) {
				titulo += " | Secretaria: " + sec;
				countParams++;
			}
		}
		if (Funcoes.isValidIntegerParameter(fs.getTipoveiculoId())) {
			TipoVeiculo tipo = tipoveiculoService.findById(fs.getTipoveiculoId()).get();
			if (tipo != null) {
				titulo += " | Tipo de Veículo:" + tipo;
				countParams++;
			}
		}

		if (Funcoes.isValidIntegerParameter(fs.getSetorId())) {
			SetorCVD setor = setorService.findById(fs.getSetorId()).get();
			if (setor != null) {
				titulo += " | Setor: " + setor.getNome();
				countParams++;
			}
		}

		titulo += " |";
		if (countParams > 0)
			return titulo;
		else
			return "Todos os veículos";
	}

	@Override
	public String getCurrURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasValidData(Object object, Errors erros) {
		// TODO Auto-generated method stub
		return false;
	}

}
