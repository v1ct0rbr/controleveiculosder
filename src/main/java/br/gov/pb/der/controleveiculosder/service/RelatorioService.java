package br.gov.pb.der.controleveiculosder.service;

import java.io.IOException;
import java.util.Locale;

import org.springframework.stereotype.Service;

import br.gov.pb.der.controleveiculosder.model.Veiculo;

@Service
public class RelatorioService {

	// OrderModel is a POJO contains all the data about the Invoice
	// Locale is used to localize the PDF file (French, English...)
	public void generateInvoiceFor(Veiculo order, Locale locale) throws IOException {

		// We will generate the PDF here

	}
}