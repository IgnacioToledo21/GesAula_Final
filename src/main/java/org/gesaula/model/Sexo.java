package org.gesaula.model;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum Sexo {
    HOMBRE,
    MUJER
}