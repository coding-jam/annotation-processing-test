package it.cosenonjaviste.csv.api;

/**
 * Created by acomo on 10/01/16.
 */
public interface RowRenderer<T> {

    StringBuilder doRender(T row);
}
