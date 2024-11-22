package org.gesaula.model;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
// Clase Alumno que representa un modelo de datos para un estudiante.
public class Alumno {
    // Propiedad observable para el nombre del alumno.
    private StringProperty nombre;
    // Propiedad observable para los apellidos del alumno.
    private StringProperty apellidos;
    // Propiedad observable para la fecha de nacimiento del alumno.
    private ObjectProperty<LocalDate> fechaNacimiento;
    // Propiedad observable para el sexo del alumno.
    private ObjectProperty<Sexo> sexo;
    // Propiedad observable para indicar si el alumno repite curso.
    private BooleanProperty repite;

    // Constructor que inicializa todas las propiedades del alumno.
    public Alumno() {
        nombre = new SimpleStringProperty(this, "nombre");
        apellidos = new SimpleStringProperty(this, "apellidos");
        fechaNacimiento = new SimpleObjectProperty<LocalDate>(this, "fechaNacimiento");
        sexo = new SimpleObjectProperty<Sexo>(this, "sexo");
        repite = new SimpleBooleanProperty(this, "repite", false);
    }

    // Devuelve la propiedad observable del nombre.
    public StringProperty nombreProperty() {
        return this.nombre;
    }

    // Devuelve el valor actual del nombre. Es utilizado para serialización XML.
    @XmlElement
    public String getNombre() {
        return this.nombreProperty().get();
    }

    // Establece un nuevo valor para el nombre.
    public void setNombre(final String nombre) {
        this.nombreProperty().set(nombre);
    }

    // Devuelve la propiedad observable de los apellidos.
    public StringProperty apellidosProperty() {
        return this.apellidos;
    }

    // Devuelve el valor actual de los apellidos. Es utilizado para serialización XML.
    @XmlElement
    public String getApellidos() {
        return this.apellidosProperty().get();
    }

    // Establece un nuevo valor para los apellidos.
    public void setApellidos(final String apellidos) {
        this.apellidosProperty().set(apellidos);
    }

    // Devuelve la propiedad observable de la fecha de nacimiento.
    public ObjectProperty<LocalDate> fechaNacimientoProperty() {
        return this.fechaNacimiento;
    }

    // Devuelve el valor actual de la fecha de nacimiento.
    // Usa un adaptador para serializar LocalDate en XML.
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFechaNacimiento() {
        return this.fechaNacimientoProperty().get();
    }

    // Establece un nuevo valor para la fecha de nacimiento.
    public void setFechaNacimiento(final LocalDate fechaNacimiento) {
        this.fechaNacimientoProperty().set(fechaNacimiento);
    }

    // Devuelve la propiedad observable del sexo.
    public ObjectProperty<Sexo> sexoProperty() {
        return this.sexo;
    }

    // Devuelve el valor actual del sexo. Es utilizado para serialización XML.
    @XmlElement
    public Sexo getSexo() {
        return this.sexoProperty().get();
    }

    // Establece un nuevo valor para el sexo.
    public void setSexo(final Sexo sexo) {
        this.sexoProperty().set(sexo);
    }

    // Devuelve la propiedad observable de si el alumno repite curso.
    public BooleanProperty repiteProperty() {
        return this.repite;
    }

    // Devuelve si el alumno repite curso. Es utilizado para serialización XML.
    @XmlElement
    public boolean isRepite() {
        return this.repiteProperty().get();
    }

    // Establece si el alumno repite curso.
    public void setRepite(final boolean repite) {
        this.repiteProperty().set(repite);
    }

    // Devuelve una representación en forma de texto del alumno (nombre completo).
    @Override
    public String toString() {
        return (getNombre() + " " + getApellidos()).trim();
    }
}
