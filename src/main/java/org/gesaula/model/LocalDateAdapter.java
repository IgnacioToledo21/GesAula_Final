package org.gesaula.model;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    // Convierte un String (del archivo XML) a un objeto LocalDate.
    @Override
    public LocalDate unmarshal(String value) throws Exception {
        return LocalDate.parse(value); // Parsea el String a LocalDate.
    }

    // Convierte un objeto LocalDate a un String para guardarlo en el archivo XML.
    @Override
    public String marshal(LocalDate value) throws Exception {
        return value.toString(); // Convierte LocalDate a su representación en String (ISO-8601).
    }
}
