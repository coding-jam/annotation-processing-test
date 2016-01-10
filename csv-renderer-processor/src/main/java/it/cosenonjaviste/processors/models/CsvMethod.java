package it.cosenonjaviste.processors.models;

import it.cosenonjaviste.csv.annotations.CsvPosition;

/**
 * Created by acomo on 10/01/16.
 */
public class CsvMethod {

    private CsvPosition position;

    private String name;

    public CsvMethod(CsvPosition position, String methodName) {
        this.position = position;
        this.name = methodName;
    }

    public CsvPosition getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}
