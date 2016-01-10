package it.cosenonjaviste.model;

import it.cosenonjaviste.csv.api.CsvSerializer;

/**
 * Created by acomo on 11/01/16.
 */
public class DomainModelCsvSerializer extends CsvSerializer<DomainModel> {

    public DomainModelCsvSerializer() {
        super(new DomainModelCsvRenderer());
    }
}
