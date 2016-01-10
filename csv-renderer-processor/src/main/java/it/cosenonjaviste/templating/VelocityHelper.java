package it.cosenonjaviste.templating;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by acomo on 10/01/16.
 */
public class VelocityHelper {

    public static TemplateBuilder fill(String template) {
        return new TemplateBuilder(template);
    }

    public static class TemplateBuilder {

        private static final Logger LOGGER = Logger.getLogger(TemplateBuilder.class.getName());

        private static final String CONF = "velocity.properties";

        private VelocityEngine engine;

        private Template template;

        private VelocityContext model = new VelocityContext();

        public TemplateBuilder(String template) {
            try (InputStream config = this.getClass().getClassLoader().getResourceAsStream(CONF)) {
                Properties properties = new Properties();
                properties.load(config);

                this.engine = new VelocityEngine(properties);
                this.template = this.engine.getTemplate(template);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                throw new IllegalStateException("Unable to find config file " + CONF, e);
            }
        }

        public TemplateBuilder with(String key, Object value) {
            model.put(key, value);

            return this;
        }

        public void write(Writer writer) {
            this.template.merge(model, writer);
        }
    }
}
