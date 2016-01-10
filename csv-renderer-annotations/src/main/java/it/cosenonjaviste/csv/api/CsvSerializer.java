package it.cosenonjaviste.csv.api;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by acomo on 10/01/16.
 */
public abstract class CsvSerializer<T> {

    private final RowRenderer<T> renderer;

    public CsvSerializer(RowRenderer<T> renderer) {
        this.renderer = renderer;
    }

    public void serializeTo(List<T> rows, OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new PrintWriter(out));
        Iterator<T> iterator = rows.iterator();
        while (iterator.hasNext()) {
            StringBuilder rowSb = renderer.doRender(iterator.next());
            writer.write(rowSb.toString());
            if (iterator.hasNext()) {
                writer.newLine();
            }
        }
    }
}
