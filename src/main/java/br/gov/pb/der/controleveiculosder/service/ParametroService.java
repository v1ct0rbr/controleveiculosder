package br.gov.pb.der.controleveiculosder.service;

import java.io.IOException;
import java.security.InvalidKeyException;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.stereotype.Service;

import br.gov.pb.der.controleveiculosder.utils.ParametroUtils;

@Service
public class ParametroService {

	public ParametroUtils carregarParametros(String configuracao)
			throws IOException, ConfigurationException, InvalidKeyException {
//		Configuracao conf = configuracaoService.findByNome(configuracao);
//		ParametroUtils param = null;
//		param = new ParametroUtils(configuracao, conf.getParametros());
//		return param;
		return null;
	}

}
