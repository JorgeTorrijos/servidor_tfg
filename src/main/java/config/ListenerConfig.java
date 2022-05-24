package config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class ListenerConfig implements ServletContextListener {

    Configuration configuration;

    @Inject
    public ListenerConfig(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        configuration.cargar(sce.getServletContext().getResourceAsStream(ConstantesConfiguration.WEB_INF_CONFIG_CONFIG_YAML));


    }

}
