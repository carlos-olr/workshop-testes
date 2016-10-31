package br.com.fatec.cursoTestes.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author carlos.oliveira
 * @since 2016-10-31
 */
@EnableWebMvc
@Configuration
@ComponentScan({"br.com.fatec.cursoTestes.config",
    "br.com.fatec.cursoTestes.controller", "br.com.fatec.cursoTestes.service"})
public class AppWebConfiguration extends WebMvcConfigurationSupport {

    // equivalents for <mvc:resources/> tags
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    // equivalent for <mvc:default-servlet-handler/> tag
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    @Bean
    public HandlerMapping resourceHandlerMapping() {
        AbstractHandlerMapping handlerMapping = (AbstractHandlerMapping) super.resourceHandlerMapping();
        handlerMapping.setOrder(- 1);
        return handlerMapping;
    }

    // Configurações de Banco de dados

    /**
     * Determinda a Configuração com o BD
     *
     * @return O {@link DataSource} que repreenta a conexão com o BD
     */
    @Bean(name = "dataSource")
    public DataSource getDateDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.hsqldb.jdbcDriver");
        ds.setUrl("jdbc:hsqldb:file:bancoDeVerdadeDB");
        ds.setUsername("bancoDeVerdade");
        ds.setPassword("bancoDeVerdade");

        return ds;
    }

    /**
     * @param emf
     * @return o transactionManager
     */
    @Autowired
    @Bean(name = "transactionManager")
    public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    /**
     * Configura os pacotes/classes a serem utilizados pelo Hibernate
     *
     * @param dataSource
     * @return o entityManagerFactory
     */
    @Autowired
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("br.com.fatec.cursoTestes.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(getProperties());

        return emf;
    }

    /**
     * Configurações do Hibernate
     *
     * @return properties
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql.", "true");
        return properties;
    }

}