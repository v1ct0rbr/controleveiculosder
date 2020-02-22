
package br.gov.pb.der.controleveiculosder.utils;

import java.util.Map;

public class ParametroUtils {

	public static final String CONFIG_EMPRESA = "empresa";

	private String config;
	protected Map<String, String> parametros;

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametrosEmail) {
		this.parametros = parametrosEmail;
	}

	public String getParameter(String param) {
		return this.parametros.get(param);
	}

}
