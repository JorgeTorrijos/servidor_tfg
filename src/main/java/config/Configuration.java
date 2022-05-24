package config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Getter
@Log4j2
@Singleton
public class Configuration {

    private String ruta;
    private String user;
    private String password;
    private String driver;

    void cargar(InputStream file) {

        try {
            Yaml yaml = new Yaml();

            Iterable<Object> it = null;

            it = yaml
                    .loadAll(this.getClass().getClassLoader().getResourceAsStream(ConstantesConfiguration.CONFIG_CONFIG_YAML));

            Map<String, String> m = (Map) it.iterator().next();
            this.ruta = m.get(ConstantesConfiguration.RUTA);
            this.password = m.get(ConstantesConfiguration.PASSWORD);
            this.user = m.get(ConstantesConfiguration.USER);
            this.driver = m.get(ConstantesConfiguration.DRIVER);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
