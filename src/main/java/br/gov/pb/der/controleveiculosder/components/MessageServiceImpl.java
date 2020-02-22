package br.gov.pb.der.controleveiculosder.components;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceImpl implements MessageService {

	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;



	@Override
	public String getValue(String code) {
		// TODO Auto-generated method stub
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(code, null, locale);

	}

}
