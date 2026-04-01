package org.proyecto.concesionariaautomoviles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AutomovilDTOReq {
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
    // Acepta el formato de patentes viejas como modernas
    private String patente;
    @Positive
    private int cantPuertas;
}
