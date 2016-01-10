package it.cosenonjaviste.processors;

import it.cosenonjaviste.csv.annotations.CsvRendered;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * Created by acomo on 09/01/16.
 */
@SupportedAnnotationTypes("it.cosenonjaviste.csv.annotations.CsvRendered")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CsvRendererGenerator extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Annotation processing: " + roundEnv);

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(CsvRendered.class);


        elements.stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .forEach(element -> {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("Found %s, creating a new Csv Renderer", element));
                    TypeElement classElement = (TypeElement) element;
                    try {
                        String newClassName = classElement.getQualifiedName() + "CsvRenderer";
                        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(newClassName);
                        try (Writer writer = sourceFile.openWriter()) {
                            CsvRendererGeneratorHelper.newRenderer(processingEnv, classElement).generate(writer);
                            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format("%s created", newClassName));
                        }
                    } catch (IOException e) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), classElement);
                        e.printStackTrace();
                    }
                });
        return true;
    }
}
