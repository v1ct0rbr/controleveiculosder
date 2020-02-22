package br.gov.pb.der.controleveiculosder.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import br.gov.pb.der.controleveiculosder.config.parameters.EmpresaConfig;
import br.gov.pb.der.controleveiculosder.config.parameters.ParametersUtils;

@Service
public class EmpresaConfigService implements ParametersUtils<EmpresaConfig> {

	@Autowired
	private ResourceLoader resourceLoader;

	private static final String ARQ_CONFIG = "classpath:configuration/empresa.properties";

	@Override
	public EmpresaConfig carregarParametros() throws IOException, ConfigurationException {
		// TODO Auto-generated method stub
		PropertiesConfiguration config = new PropertiesConfiguration();
		File file = resourceLoader.getResource(ARQ_CONFIG).getFile();
		config.setFile(file);
		config.load();
		EmpresaConfig empresaConfig = new EmpresaConfig();
		empresaConfig.setEmpresaNome((String) config.getProperty("empresa.nome"));
		empresaConfig.setEmpresaLocalidade((String) config.getProperty("empresa.localidade"));
		empresaConfig.setEmpresaEndereco((String) config.getProperty("empresa.endereco"));
		empresaConfig.setEmpresaSetor((String) config.getProperty("empresa.setor"));
		empresaConfig.setEmpresaTelefone((String) config.getProperty("empresa.telefone"));
		empresaConfig.setEmpresaEmail((String) config.getProperty("empresa.email"));

		return empresaConfig;
	}

	@Override
	public void salvarParametros(EmpresaConfig empresaConfig) throws IOException, ConfigurationException {
		PropertiesConfiguration config = new PropertiesConfiguration();
		File file = resourceLoader.getResource(ARQ_CONFIG).getFile();
		config.setFile(file);
		config.setProperty("empresa.nome", empresaConfig.getEmpresaNome());
		config.setProperty("empresa.localidade", empresaConfig.getEmpresaLocalidade());
		config.setProperty("empresa.endereco", empresaConfig.getEmpresaEndereco());
		config.setProperty("empresa.setor", empresaConfig.getEmpresaSetor());
		config.setProperty("empresa.telefone", empresaConfig.getEmpresaTelefone());
		config.setProperty("empresa.email", empresaConfig.getEmpresaEmail());
		config.save();

	}

}
