package it.cosenonjaviste.processors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Validates that {@link it.cosenonjaviste.csv.annotations.CsvPosition} is on getter method
 *
 * Created by acomo on 11/02/16.
 */
@SupportedAnnotationTypes("it.cosenonjaviste.csv.annotations.CsvPosition")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CsvPositionValidatorProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .forEach(elements -> elements.stream()
                        .filter(isMethodReturningVoid())
                        .forEach(logErrorMessage()));

        return true;
    }

    private Consumer<Element> logErrorMessage() {
        return element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("%s is not returning a value", element), element);
    }

    private Predicate<Element> isMethodReturningVoid() {
        return element -> element.getKind() == ElementKind.METHOD && ((ExecutableElement) element).getReturnType().getKind() == TypeKind.VOID;
    }
}
