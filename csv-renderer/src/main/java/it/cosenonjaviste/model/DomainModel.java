package it.cosenonjaviste.model;


import it.cosenonjaviste.csv.annotations.CsvPosition;
import it.cosenonjaviste.csv.annotations.CsvRendered;

import java.util.Date;

/**
 * Created by acomo on 09/01/16.
 */
@CsvRendered
public class DomainModel {

    private String value1;

    private Date value2;

    private int value3;

    private float value4;

//    @CsvPosition(1)
//    public void myMethod() {}

    @CsvPosition(3)
    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    @CsvPosition(1)
    public Date getValue2() {
        return value2;
    }

    public void setValue2(Date value2) {
        this.value2 = value2;
    }

    @CsvPosition(0)
    public int getValue3() {
        return value3;
    }

    public void setValue3(int value3) {
        this.value3 = value3;
    }

    @CsvPosition(2)
    public float getValue4() {
        return value4;
    }

    public void setValue4(float value4) {
        this.value4 = value4;
    }
}
