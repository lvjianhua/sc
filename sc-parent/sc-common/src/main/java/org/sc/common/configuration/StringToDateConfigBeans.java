package org.sc.common.configuration;

import java.util.Date;

import org.sc.common.utils.web.StringToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;


@Configuration
public class StringToDateConfigBeans {
	@Bean
	public Converter<String, Date> addNewConvert() {
		return new StringToDateConverter();
    }
}
