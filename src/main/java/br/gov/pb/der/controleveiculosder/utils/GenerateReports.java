package br.gov.pb.der.controleveiculosder.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class GenerateReports {

	private static final Logger logger = LoggerFactory.getLogger(GenerateReports.class);

	// This method generates a PDF report
	public void generatePdfReport(String jrxml, Map<String, Object> parametros, JRDataSource jr,
			HttpServletResponse response) {
		try {

			InputStream jrxmlInput = this.getClass().getResourceAsStream(jrxml);
			JasperDesign design = JRXmlLoader.load(jrxmlInput);
			JasperReport jasperReport = JasperCompileManager.compileReport(design);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, jr);
			logger.info("JasperPrint" + jasperPrint);

			JRPdfExporter pdfExporter = new JRPdfExporter();
			pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
			pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
			pdfExporter.exportReport();

			response.setContentType("application/pdf");
			response.setHeader("Content-Length", String.valueOf(pdfReportStream.size()));
			response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");

			OutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(pdfReportStream.toByteArray());
			responseOutputStream.close();
			pdfReportStream.close();
			logger.info("Completed Successfully: ");
		} catch (Exception e) {
			logger.info("Error: ", e);
		}

	}

}