package org.gesaula.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

@XmlType
public class Pesos {

    // Propiedad observable para la actitud (porcentaje de peso en la evaluación).
    private DoubleProperty actitud;
    // Propiedad observable para los exámenes (porcentaje de peso en la evaluación).
    private DoubleProperty examenes;
    // Propiedad observable para las prácticas (porcentaje de peso en la evaluación).
    private DoubleProperty practicas;

    // Constructor que inicializa las propiedades con valores por defecto de 0.0.
    public Pesos() {
        actitud = new SimpleDoubleProperty(this, "actitud", 0.0);
        examenes = new SimpleDoubleProperty(this, "examenes", 0.0);
        practicas = new SimpleDoubleProperty(this, "practicas", 0.0);
    }

    // Devuelve la propiedad observable de actitud.
    public DoubleProperty actitudProperty() {
        return this.actitud;
    }

    // Devuelve el valor de actitud. Es serializado como atributo en XML.
    @XmlAttribute
    public double getActitud() {
        return this.actitudProperty().get();
    }

    // Establece un nuevo valor para actitud.
    public void setActitud(final double actitud) {
        this.actitudProperty().set(actitud);
    }

    // Devuelve la propiedad observable de exámenes.
    public DoubleProperty examenesProperty() {
        return this.examenes;
    }

    // Devuelve el valor de exámenes. Es serializado como atributo en XML.
    @XmlAttribute
    public double getExamenes() {
        return this.examenesProperty().get();
    }

    // Establece un nuevo valor para exámenes.
    public void setExamenes(final double examenes) {
        this.examenesProperty().set(examenes);
    }

    // Devuelve la propiedad observable de prácticas.
    public DoubleProperty practicasProperty() {
        return this.practicas;
    }

    // Devuelve el valor de prácticas. Es serializado como atributo en XML.
    @XmlAttribute
    public double getPracticas() {
        return this.practicasProperty().get();
    }

    // Establece un nuevo valor para prácticas.
    public void setPracticas(final double practicas) {
        this.practicasProperty().set(practicas);
    }
}
