package festivos.api.core.interfaces.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import festivos.api.core.dominio.entidades.Festivo;

@Service
public interface IFestivoServicio {

    List<Festivo> listar();
    List<Festivo> ListarPorAño(int año);
    boolean validar(LocalDate fecha);


}
