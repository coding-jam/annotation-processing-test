package it.cosenonjaviste.processors;

import javax.lang.model.element.TypeElement;
import java.io.Writer;

/**
 * Created by acomo on 12/01/16.
 */
abstract class AbstractGeneratorHelper {

    protected static final String PACKAGE = "package";

    protected static final String MODEL_CLASS = "modelClass";

    protected AbstractGeneratorHelper() {}

    abstract static class Builder {

        protected final String packageName;

        protected final String className;

        Builder(TypeElement classElement) {
            this.packageName = classElement.getEnclosingElement().toString();
            this.className = classElement.getSimpleName().toString();
        }

        abstract void generate(Writer writer);
    }
}
