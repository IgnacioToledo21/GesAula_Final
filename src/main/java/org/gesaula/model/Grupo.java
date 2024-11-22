package org.gesaula.model;

import java.io.File;
import java.time.LocalDate;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlType
@XmlRootElement
// Clase Grupo que representa un grupo de estudiantes y su configuración general.
public class Grupo {

    // Propiedad observable para la denominación del grupo.
    private StringProperty denominacion;
    // Propiedad observable para la fecha de inicio del curso.
    private ObjectProperty<LocalDate> iniCurso;
    // Propiedad observable para la fecha de fin del curso.
    private ObjectProperty<LocalDate> finCurso;
    // Propiedad observable para los pesos (configuración específica del grupo).
    private ObjectProperty<Pesos> pesos;
    // Propiedad observable para la lista de alumnos en el grupo.
    private ListProperty<Alumno> alumnos;

    // Constructor que inicializa todas las propiedades del grupo.
    public Grupo() {
        denominacion = new SimpleStringProperty(this, "denominacion");
        iniCurso = new SimpleObjectProperty<>(this, "iniCurso");
        finCurso = new SimpleObjectProperty<>(this, "finCurso");
        pesos = new SimpleObjectProperty<>(this, "pesos", new Pesos());
        alumnos = new SimpleListProperty<>(this, "alumnos", FXCollections.observableArrayList());
    }

    // Método para guardar el grupo en un archivo XML utilizando JAXB.
    public void save(File file) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Grupo.class); // Crea el contexto JAXB.
        Marshaller marshaller = context.createMarshaller(); // Crea el marshaller.
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Formatea el XML.
        marshaller.marshal(this, file); // Serializa el objeto Grupo en el archivo.
    }

    // Método estático para leer un grupo desde un archivo XML utilizando JAXB.
    public static Grupo read(File file) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Grupo.class); // Crea el contexto JAXB.
        Unmarshaller unmarshaller = context.createUnmarshaller(); // Crea el unmarshaller.
        return (Grupo) unmarshaller.unmarshal(file); // Deserializa el archivo a un objeto Grupo.
    }

    // Devuelve la propiedad observable de la denominación.
    public StringProperty denominacionProperty() {
        return this.denominacion;
    }

    // Devuelve el valor de la denominación. Es utilizado como atributo en XML.
    @XmlAttribute
    public String getDenominacion() {
        return this.denominacionProperty().get();
    }

    // Establece un nuevo valor para la denominación.
    public void setDenominacion(final String denominacion) {
        this.denominacionProperty().set(denominacion);
    }

    // Devuelve la propiedad observable de la fecha de inicio del curso.
    public ObjectProperty<LocalDate> iniCursoProperty() {
        return this.iniCurso;
    }

    // Devuelve la fecha de inicio del curso. Usa un adaptador para serializar LocalDate en XML.
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getIniCurso() {
        return this.iniCursoProperty().get();
    }

    // Establece un nuevo valor para la fecha de inicio del curso.
    public void setIniCurso(final LocalDate iniCurso) {
        this.iniCursoProperty().set(iniCurso);
    }

    // Devuelve la propiedad observable de la fecha de fin del curso.
    public ObjectProperty<LocalDate> finCursoProperty() {
        return this.finCurso;
    }

    // Devuelve la fecha de fin del curso. Usa un adaptador para serializar LocalDate en XML.
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFinCurso() {
        return this.finCursoProperty().get();
    }

    // Establece un nuevo valor para la fecha de fin del curso.
    public void setFinCurso(final LocalDate finCurso) {
        this.finCursoProperty().set(finCurso);
    }

    // Devuelve la propiedad observable de los pesos.
    public ObjectProperty<Pesos> pesosProperty() {
        return this.pesos;
    }

    // Devuelve el valor actual de los pesos. Es utilizado para serialización XML.
    @XmlElement
    public Pesos getPesos() {
        return this.pesosProperty().get();
    }

    // Establece un nuevo valor para los pesos.
    public void setPesos(final Pesos pesos) {
        this.pesosProperty().set(pesos);
    }

    // Devuelve la propiedad observable de la lista de alumnos.
    public ListProperty<Alumno> alumnosProperty() {
        return this.alumnos;
    }

    // Devuelve la lista observable de alumnos. Es utilizado para serialización XML.
    @XmlElement
    public ObservableList<Alumno> getAlumnos() {
        return this.alumnosProperty().get();
    }

    // Establece una nueva lista de alumnos.
    public void setAlumnos(final ObservableList<Alumno> alumnos) {
        this.alumnosProperty().set(alumnos);
    }
}
