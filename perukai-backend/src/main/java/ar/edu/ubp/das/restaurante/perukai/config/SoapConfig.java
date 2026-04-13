package ar.edu.ubp.das.restaurante.perukai.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.server.endpoint.interceptor.DelegatingSmartEndpointInterceptor;
import org.springframework.ws.server.SmartEndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Properties;

@EnableWs
@Configuration
public class SoapConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "perukai")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema perukaiSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PerukaiPort");
        wsdl11Definition.setLocationUri("http://localhost:8082/ws");
        wsdl11Definition.setTargetNamespace("http://das.ubp.edu.ar/soap");
        wsdl11Definition.setSchema(perukaiSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema perukaiSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/perukai.xsd"));
    }

    // === WS-Security (Wss4jSecurityInterceptor + SimplePasswordValidationCallbackHandler) ===

    @Bean
    public SimplePasswordValidationCallbackHandler securityCallbackHandler() {
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        Properties users = new Properties();
        users.setProperty("admin", "123");
        handler.setUsers(users);
        return handler;
    }

    @Bean
    public SmartEndpointInterceptor securityInterceptor(SimplePasswordValidationCallbackHandler callbackHandler) {
        Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();
        interceptor.setValidationActions("UsernameToken");
        interceptor.setValidationCallbackHandler(callbackHandler);
        return new DelegatingSmartEndpointInterceptor(interceptor);
    }
}