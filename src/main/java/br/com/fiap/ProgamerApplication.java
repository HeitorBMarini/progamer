package br.com.fiap;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import com.sun.faces.config.ConfigureListener;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ProgamerApplication implements ServletContextAware {

	public static void main(String[] args) {
		SpringApplication.run(ProgamerApplication.class);
	}

	@Bean
	ServletRegistrationBean<FacesServlet> facesServletRegistration() {
		ServletRegistrationBean<FacesServlet> registration = new ServletRegistrationBean<FacesServlet>(
				new FacesServlet(), "*.xhtml"); 
		registration.setLoadOnStartup(1);
		return registration;
	}

	@Bean
	ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
	}

	@Bean
	FilterRegistrationBean<FileUploadFilter> primeFacesFileUploadFilter() {
		FilterRegistrationBean<FileUploadFilter> registration = new FilterRegistrationBean<FileUploadFilter>(
				new org.primefaces.webapp.filter.FileUploadFilter(), facesServletRegistration());
		registration.addUrlPatterns("/*");
		registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
		registration.setName("primeFacesFileUploadFilter");
		registration.setOrder(1);
		return registration;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		servletContext.setInitParameter("primefaces.UPLOADER", "commons");
	}

}
