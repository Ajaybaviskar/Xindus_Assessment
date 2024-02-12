package com.xindus.ecommerce.entity;

import com.xindus.ecommerce.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

//    First Annotation for Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Here We generate id using auto_increment type
    private Long id;
    // Here We Take id

    private String email;
    private  String password;
    private String name;
    private UserRole role;

    // Second Annotation for img
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;













}
