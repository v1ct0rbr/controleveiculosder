package br.gov.pb.der.controleveiculosder.config.parameters;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;

public interface ParametersUtils<T> {

	public T carregarParametros() throws IOException, ConfigurationException;
	
	public void salvarParametros(T config) throws IOException, ConfigurationException;
	
	
}
