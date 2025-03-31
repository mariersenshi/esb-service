package com.utd.ti.soa.ebs_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    @JsonProperty("userName")  // Mapea "userName" del JSON a "username"
    private String username;

    private String phone;
    private String password;
}
