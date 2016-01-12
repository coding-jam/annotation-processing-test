package it.cosenonjaviste.processors;

import it.cosenonjaviste.csv.annotations.CsvPosition;
import it.cosenonjaviste.processors.models.CsvMethod;
import it.cosenonjaviste.templating.VelocityHelper;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

/**
 * Generates CsvRenderer class for rendering in csv a single row
 *
 * Created by acomo on 10/01/16.
 */
class CsvRendererGeneratorHelper extends AbstractGeneratorHelper {

    private static final String TEMPLATE = "templates/CsvRenderer.vm";

    private static final String METHODS = "methods";

    public static Builder newRenderer(ProcessingEnvironment processingEnv, TypeElement classElement) {
        return new Builder(processingEnv, classElement);
    }

    public static class Builder extends AbstractGeneratorHelper.Builder {

        private final List<CsvMethod> csvMethods;

        public Builder(ProcessingEnvironment processingEnv, TypeElement classElement) {
            super(classElement);
            this.csvMethods = getMethodsSortedByPosition(processingEnv, classElement);
        }

        public void generate(Writer writer) {
            VelocityHelper.fill(TEMPLATE)
                    .with(METHODS, this.csvMethods)
                    .with(PACKAGE, this.packageName)
                    .with(MODEL_CLASS, this.className)
                    .write(writer);
        }

        /**
         * Extract method names annotated with {@link CsvPosition} sorted by {@link CsvPosition#value()}
         *
         * @param processingEnv
         * @param classElement
         * @return
         */
        private List<CsvMethod> getMethodsSortedByPosition(ProcessingEnvironment processingEnv, TypeElement classElement) {

            List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(classElement);

            return allMembers.stream()
                    .filter(member -> member.getAnnotation(CsvPosition.class) != null)
                    .map(member -> {
                        CsvPosition position = member.getAnnotation(CsvPosition.class);
                        return new CsvMethod(position, member.getSimpleName().toString());
                    })
                    .sorted((member1, member2) -> Integer.valueOf(member1.getPosition().value()).compareTo(member2.getPosition().value()))
                    .collect(toCollection(ArrayList::new));
        }
    }
}
