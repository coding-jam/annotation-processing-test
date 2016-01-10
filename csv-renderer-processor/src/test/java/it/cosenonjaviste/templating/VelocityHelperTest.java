package it.cosenonjaviste.templating;

import it.cosenonjaviste.csv.annotations.CsvPosition;
import it.cosenonjaviste.processors.models.CsvMethod;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by acomo on 10/01/16.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class VelocityHelperTest extends TestCase {

    @Test
    public void shoudlBeASimpleMessage() throws Exception {
        StringWriter writer = new StringWriter();
        VelocityHelper.fill("test.vm")
                .with("name", "Andrea")
                .write(writer);

        assertNotNull(writer);
        assertEquals("Hello Andrea", writer.toString());
    }

    @Test
    public void shouldHasMultipleLines() {
        StringWriter writer = new StringWriter();
        VelocityHelper.fill("test-foreach.vm")
                .with("names", Arrays.asList("Andrea", "Giulio"))
                .write(writer);

        assertNotNull(writer);
        String[] rows = writer.toString().split("\n");
        assertEquals(2, rows.length);
        assertEquals("Hello Andrea", rows[0]);
        assertEquals("Hello Giulio", rows[1]);
    }

    @Test
    public void shouldGenerateAClass() {
        StringWriter writer = new StringWriter();
        VelocityHelper.fill("templates/CsvRenderer.vm")
                .with("package", "it.cosenonjaviste.renderer")
                .with("modelClass", "DomainModel")
                .with("methods", getMethods())
                .write(writer);

        assertNotNull(writer);
        String clazz = writer.toString();
        assertTrue(clazz.contains("it.cosenonjaviste.renderer"));
        assertTrue(clazz.contains("class DomainModelCsvRenderer"));
        assertTrue(clazz.contains("<DomainModel>"));
        assertTrue(clazz.contains("(DomainModel model)"));
        assertTrue(clazz.contains("model.getValue2()"));
        assertTrue(clazz.contains("model.getValue3()"));
        assertTrue(clazz.contains("model.getValue1()"));
        assertTrue(clazz.contains("model.getValue4()"));
    }

    public List<CsvMethod> getMethods() {
        List<CsvMethod> methods = new ArrayList<>();
        methods.add(getMethod(1, "getValue2"));
        methods.add(getMethod(2, "getValue3"));
        methods.add(getMethod(3, "getValue1"));
        methods.add(getMethod(4, "getValue4"));

        return methods;
    }

    public CsvMethod getMethod(int value, String name) {
        return new CsvMethod(new AbstractCsvPosition() {
            @Override
            public int value() {
                return value;
            }
        }, name);
    }

    static abstract class AbstractCsvPosition implements CsvPosition {

        @Override
        public Class<? extends Annotation> annotationType() {
            return CsvPosition.class;
        }
    }
}