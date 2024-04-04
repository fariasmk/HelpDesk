package com.maikon.userserviceapi.entity;

import com.maikon.hdcommonslib.models.enums.ProfileEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@With//Permite que possa ser modificado apenas em atributo. Lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document//Documento do MongoDB
@Builder
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Set<ProfileEnum> profiles;
}