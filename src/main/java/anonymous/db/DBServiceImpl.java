package anonymous.db;

import anonymous.base.Host;
import anonymous.base.DBService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class DBServiceImpl implements DBService {

    public enum DBType {H2, MySQL, PostgreSQL}

    private static final Logger LOGGER = LogManager.getLogger(DBService.class.getName());

    private static final String HIBERNATE_SHOW_SQL = "false";
    private static final String HIBERNATE_HBM2DDL_AUTO = "validate";
    private static final int FETCH_SIZE = 100;

    private final SessionFactory sessionFactory;

    public DBServiceImpl() {
        Configuration configuration = getConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    public DBServiceImpl(DBType dbType, String username, String password, String url) {
        Configuration configuration = getConfiguration(dbType, username, password, url);
        sessionFactory = createSessionFactory(configuration);
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
        standardServiceRegistryBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = standardServiceRegistryBuilder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getConfiguration() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        DBType dbType = DBType.valueOf(resourceBundle.getString("type"));
        String username = resourceBundle.getString("username");
        String password = resourceBundle.getString("password");
        String url = resourceBundle.getString("url");
        return getConfiguration(dbType, username, password, url);
    }

    private Configuration getConfiguration(DBType dbType, String username, String password, String url) {
        Configuration configuration;

        switch (dbType) {
            case MySQL:
                configuration = getMySQLConfiguration(url);
                break;
            case PostgreSQL:
                configuration = getPostgreSQLConfiguration(url);
                break;
            default:
                configuration = getH2Configuration(url);
        }

        configuration.setProperty("hibernate.connection.username", username);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        configuration.setProperty("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);

        configuration.addAnnotatedClass(Host.class);

        return configuration;
    }

    private Configuration getH2Configuration(String url) {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", String.format("jdbc:h2:%s", url));
        return configuration;
    }

    private Configuration getMySQLConfiguration(String url) {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", String.format("jdbc:mysql:%s", url));
        return configuration;
    }

    private Configuration getPostgreSQLConfiguration(String url) {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", String.format("jdbc:postgresql:%s", url));
        return configuration;
    }

    public Collection<Host> getAllHosts() {
        try (
                Session session = sessionFactory.openSession();
                ScrollableResults scrollableResults = session.createCriteria(Host.class)
                        .setReadOnly(true)
                        .setFetchSize(FETCH_SIZE)
                        .scroll(ScrollMode.FORWARD_ONLY)
        ) {
            Collection<Host> Hosts = new ArrayList<>();
            while (scrollableResults.next()) {
                Host Host = (Host) scrollableResults.get(0);
                Hosts.add(Host);
            }
            return Hosts;
        } catch (HibernateException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}