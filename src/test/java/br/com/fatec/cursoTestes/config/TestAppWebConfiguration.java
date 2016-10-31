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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author carlos.oliveira
 * @since 2016-10-31
 */
// Diferença Aqui
@Configuration
@EnableTransactionManagement
@ComponentScan({"br.com.fatec.cursoTestes.service"})
public class TestAppWebConfiguration {

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
        // Diferença Aqui
        ds.setUrl("jdbc:hsqldb:men:testDB");
        // Diferença Aqui
        ds.setUsername("test");
        ds.setPassword("test");

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
        // Diferença Aqui
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.show_sql.", "true");
        return properties;
    }

}
