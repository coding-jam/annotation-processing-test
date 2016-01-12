# annotation-processing-test
Test project on **Annotation Processing**: this project aims to *generate source code* processing annotations on **compile time**.

This is just an example on how annotation processing can be used.

## Problem
I would like to render in CSV format a list of POJO, but I don't want to use reflection or to write a boilerplate code such as creating a *StringBuilder* for each item of the list.

All I want is to annotate my POJO like this:

```java
@CsvRendered
public class DomainModel {

    private String value1;

    private Date value2;

    private int value3;

    private float value4;

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
```

and someone that generates this boilerplate code for me:

```java
public class DomainModelCsvRenderer implements RowRenderer<DomainModel> {

    @Override
    public StringBuilder doRender(DomainModel model) {

        StringBuilder sb = new StringBuilder(100);
        sb.append(model.getValue3());
        sb.append(model.getValue2());
        sb.append(model.getValue4());
        sb.append(model.getValue1());
    
        return sb;
    }
}

public class DomainModelCsvSerializer extends CsvSerializer<DomainModel> {

    public DomainModelCsvSerializer() {
        super(new DomainModelCsvRenderer());
    }
}
```

## Project Details ##

There are 3 modules:

* **csv-renderer-annotations**: this project defines annotations that will be used on POJO and searched by annotation processor
* **csv-renderer-processor**: contains actual annotation processor logic and a template used for generating classes. Template system is [Apache Velocity](http://velocity.apache.org/)
* **csv-renderer**: a project that defines POJOs and uses generated classes for CSV serialization
