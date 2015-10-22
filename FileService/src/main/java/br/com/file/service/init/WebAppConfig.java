package br.com.file.service.init;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;


@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan("br.com.file.service")
@PropertySource("classpath:application.properties")
@ImportResource("classpath:applicationContext.xml")
@EnableJpaRepositories("br.com.file.service.repository")
public class WebAppConfig {

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HBM2DDL = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
    private static final String PROPERTY_CACHE_PROVIDER_CLASS = "hibernate.cache.provider_class";
    private static final String PROPERTY_CACHE_USE_STRUCTURED_ENTRIES = "hibernate.cache.use_structured_entries";
    private static final String PROPERTY_CACHE_USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
    private static final String PROPERTY_CACHE_USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
    private static final String PROPERTY_CACHE_REGION_FACTORY_CLASS = "hibernate.cache.region.factory_class";

    @Resource
    private Environment env;

    @Autowired
    DataSource dataSource;

    @Bean(name = "JDBCDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan(env
                .getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
        entityManagerFactoryBean.setJpaProperties(hibProperties());
        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
                env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
                env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HBM2DDL, env.getRequiredProperty(PROPERTY_NAME_HBM2DDL));
        properties.put(PROPERTY_CACHE_PROVIDER_CLASS,
                env.getProperty(PROPERTY_CACHE_PROVIDER_CLASS));
        properties.put(PROPERTY_CACHE_USE_STRUCTURED_ENTRIES,
                env.getProperty(PROPERTY_CACHE_USE_STRUCTURED_ENTRIES));
        properties.put(PROPERTY_CACHE_USE_SECOND_LEVEL_CACHE,
                env.getProperty(PROPERTY_CACHE_USE_SECOND_LEVEL_CACHE));
        properties.put(PROPERTY_CACHE_USE_QUERY_CACHE,
                env.getProperty(PROPERTY_CACHE_USE_QUERY_CACHE));
        properties.put(PROPERTY_CACHE_REGION_FACTORY_CLASS,
                env.getProperty(PROPERTY_CACHE_REGION_FACTORY_CLASS));
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
    
    /*@Bean
    public VelocityConfigurer velocityConfig() {
        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        velocityConfigurer.setResourceLoaderPath("/WEB-INF/html/");
        return velocityConfigurer;
    }
    
    @Bean
    public VelocityViewResolver setupViewResolver() {
    	VelocityViewResolver resolver = new VelocityViewResolver();
	    resolver.setPrefix("");
	    resolver.setSuffix(".html");
	    resolver.setSuffix(".jsp");
	    resolver.setViewClass(VelocityView.class);
	    resolver.setCache(true);
	    resolver.setExposeSpringMacroHelpers(true);
	    return resolver;
    }*/

    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages");
        // if true, the key of the message will be displayed if the key is not
        // found, instead of throwing a NoSuchMessageException
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        // # -1 : never reload, 0 always reload
        messageSource.setCacheSeconds(0);
        return messageSource;
    }
    


}
