package it.cosenonjaviste.processors;

import it.cosenonjaviste.csv.annotations.CsvRendered;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Annotation processor for {@link CsvRendered}
 *
 * Created by acomo on 09/01/16.
 */
@SupportedAnnotationTypes("it.cosenonjaviste.csv.annotations.CsvRendered")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CsvRenderedProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Annotation processing: " + roundEnv);

        annotations.stream()
                .map(element -> roundEnv.getElementsAnnotatedWith(element))
                .forEach(elements -> elements.stream()
                        .filter(element -> element.getKind() == ElementKind.CLASS)
                        .forEach(element -> {
                            TypeElement classElement = (TypeElement) element;

                            generateJavaSources(classElement);
                        }));

        return true;
    }

    private void generateJavaSources(TypeElement classElement) {
        String csvRendererSourceName = classElement.getQualifiedName() + "CsvRenderer";
        createSourceFile(classElement, csvRendererSourceName, writer -> CsvRendererGeneratorHelper.newRenderer(processingEnv, classElement).generate(writer));

        String csvSerializerSourceName = classElement.getQualifiedName() + "CsvSerializer";
        createSourceFile(classElement, csvSerializerSourceName, writer -> CsvSerializerGeneratorHelper.newRenderer(classElement).generate(writer));
    }

    private void createSourceFile(TypeElement element, String sourceFileName, Consumer<Writer> generator) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("Found %s, creating %s", element, sourceFileName));
        try {
            JavaFileObject newSourceFile = processingEnv.getFiler().createSourceFile(sourceFileName);
            try (Writer writer = newSourceFile.openWriter()) {
                generator.accept(writer);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("%s created", sourceFileName));
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
            e.printStackTrace();
        }
    }
}
