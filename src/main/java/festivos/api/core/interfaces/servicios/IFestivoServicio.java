package festivos.api.core.interfaces.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import festivos.api.core.dominio.DTOs.FestivoResponseDTO;
import festivos.api.core.dominio.entidades.Festivo;

@Service
public interface IFestivoServicio {

    List<Festivo> listar();
    List<FestivoResponseDTO> ListarPorAño(int año);
    boolean validar(LocalDate fecha);


}
