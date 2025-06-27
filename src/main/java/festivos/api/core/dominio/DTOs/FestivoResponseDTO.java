package festivos.api.core.dominio.DTOs;

import festivos.api.core.dominio.entidades.Tipo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class FestivoResponseDTO {
    public FestivoResponseDTO(String nombre2, int dia2, int mes2, Tipo tipo2) {
        //TODO Auto-generated constructor stub
    }
    private String nombre;
    private int dia;
    private int mes;
    private Tipo tipo;
}
