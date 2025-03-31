package com.utd.ti.soa.ebs_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    @JsonProperty("Nombre")
    private String nombre;

    @JsonProperty("Correo")
    private String correo;

    @JsonProperty("Telefono")
    private String telefono;

    @JsonProperty("Apellidos")
    private String apellidos;

    @JsonProperty("Fecha_nacimiento")
    private String fechaNacimiento;

    @JsonProperty("Direccion")
    private String direccion;
}
