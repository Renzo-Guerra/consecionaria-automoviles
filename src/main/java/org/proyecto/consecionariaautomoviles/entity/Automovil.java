package org.proyecto.consecionariaautomoviles.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Automovil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String modelo;
    @NotBlank
    private String marca;
    @NotBlank
    private String motor;
    @NotBlank
    private String color;
    @NotBlank
    @Length(min = 6, max = 7)
    @Pattern(regexp = "^([a-zA-Z]{3}\\d{3}|[a-zA-Z]{2}\\d{3}[a-zA-Z]{2})$", message = "Por favor ingrese una patente válida, por ej: xxx111 | xx111xx")
    @Column(unique = true)
    // Acepta el formato de patentes viejas como modernas
    private String patente;

}
