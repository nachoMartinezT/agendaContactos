package com.contactBook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Please enter a valid name. This field is required.")
    private String name;
    @NotEmpty(message = "Please enter a valid email.")
    @Email
    private String email;
    @NotBlank(message = "Please enter a valid phone number. This field is required.")
    private String phoneNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past
    @NotNull(message = "Please enter birthday")
    private LocalDate birthday;
    private LocalDateTime registrationDate;

    @PrePersist
    public void dateOfRegister(){
        registrationDate = LocalDateTime.now();
    }


}
