package it.cosenonjaviste.processors;

import it.cosenonjaviste.templating.VelocityHelper;

import javax.lang.model.element.TypeElement;
import java.io.Writer;

/**
 * Generates csv serializer for rendering all POJO collection
 *
 * Created by acomo on 12/01/16.
 */
class CsvSerializerGeneratorHelper extends AbstractGeneratorHelper {

    private static final String TEMPLATE = "templates/CsvSerializer.vm";

    public static Builder newRenderer(TypeElement classElement) {
        return new Builder(classElement);
    }

    public static class Builder extends AbstractGeneratorHelper.Builder {

        public Builder(TypeElement classElement) {
            super(classElement);
        }

        public void generate(Writer writer) {
            VelocityHelper.fill(TEMPLATE)
                    .with(PACKAGE, this.packageName)
                    .with(MODEL_CLASS, this.className)
                    .write(writer);
        }
    }
}
