package festivos.api.aplicacion.servicios;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import festivos.api.core.dominio.DTOs.FestivoResponseDTO;
import festivos.api.core.dominio.entidades.Festivo;
import festivos.api.core.interfaces.servicios.IFestivoServicio;
import festivos.api.infraestructura.repositorios.IFestivoRepositorio;

@Service
public class FestivoServicio implements IFestivoServicio {

    private IFestivoRepositorio repositorio;

    public FestivoServicio(IFestivoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Festivo> listar() {
        return repositorio.findAll();
    }

    public static LocalDate getInicioSemanaSanta(int año) {
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        int dia = 15 + dias;
        int mes = 3;
        if (dia > 31) {
            mes = 4;
            dia -= 31;
        }
        return LocalDate.of(año, mes, dia);
    }

    public static LocalDate siguienteLunes(LocalDate fecha) {
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        if (diaSemana != DayOfWeek.MONDAY) {
            return fecha.plusDays(8 - diaSemana.getValue());
        }
        return fecha;
    }

    public static LocalDate agregarDias(LocalDate fecha, int dias) {
        return fecha.plusDays(dias);

    }

    @Override
    public List<FestivoResponseDTO> ListarPorAño(int año) {
        List<Festivo> festivosDelAño = new ArrayList<>();
        List<Festivo> festivos = repositorio.findAll();
        LocalDate domingoPascua = agregarDias(getInicioSemanaSanta(año), 7);

        for (Festivo festivo : festivos) {
            LocalDate fechaFestivo = null;

            if (festivo.getTipo().getTipo().equals("Fijo")) {
                fechaFestivo = LocalDate.of(año, festivo.getMes(), festivo.getDia());
            } else if (festivo.getTipo().getTipo().equals("Ley Puente Festivo")) {
                fechaFestivo = siguienteLunes(LocalDate.of(año, festivo.getMes(), festivo.getDia()));
            } else if (festivo.getTipo().getTipo().equals("Basado en Pascua")) {
                fechaFestivo = agregarDias(domingoPascua, festivo.getDiasPascua());
            } else if (festivo.getTipo().getTipo().equals("Basado en Pascua y Ley Puente Festivo")) {
                fechaFestivo = siguienteLunes(agregarDias(domingoPascua, festivo.getDiasPascua()));
            }

            if (fechaFestivo != null) {
                Festivo festivoAñoEspecifico = new Festivo();
                festivoAñoEspecifico.setNombre(festivo.getNombre());
                festivoAñoEspecifico.setDia(fechaFestivo.getDayOfMonth());
                festivoAñoEspecifico.setMes(fechaFestivo.getMonthValue());
                festivoAñoEspecifico.setTipo(festivo.getTipo());
                festivosDelAño.add(festivoAñoEspecifico);
            }
            List<FestivoResponseDTO> responseList = new ArrayList<>();

            Festivo[] festivosGuardados;
            for (Festivo diafestivo : festivosGuardados) {
                if (fechaFestivo.containsKey(diafestivo.getNombre())) {
                    int offset = fechaFestivo.get(diafestivo.getNombre());
                    LocalDate actualDate = domingoPascua.plusDays(offset);
                    diafestivo.setDia(actualDate.getDayOfMonth());
                    diafestivo.setMes(actualDate.getMonthValue());
                }
                FestivoResponseDTO dto = new FestivoResponseDTO(
                        diafestivo.getNombre(),
                        diafestivo.getDia(),
                        diafestivo.getMes(),
                        diafestivo.getTipo());
                responseList.add(dto);
            }
        }

        return responseList;
    }

    @Override
    public boolean validar(LocalDate fecha) {
        int dia = fecha.getDayOfMonth();
        int mes = fecha.getMonthValue();
        int año = fecha.getYear();
        List<Festivo> festivos = repositorio.findAll();
        for (Festivo festivo : festivos) {
            LocalDate fechaFestivo = null;
            // Festivos (Tipo 1)
            if (festivo.getTipo().getTipo().equals("Fijo") && festivo.getDia() == dia && festivo.getMes() == mes) {
                fechaFestivo = LocalDate.of(año, festivo.getMes(), festivo.getDia());
                if (fechaFestivo.equals(fecha)) {
                    return true;
                }
            }

            // Festivos (Tipo 2)
            if (festivo.getTipo().getTipo().equals("Ley Puente Festivo") && festivo.getDia() == dia
                    && festivo.getMes() == mes) {
                fechaFestivo = siguienteLunes(LocalDate.of(año, festivo.getMes(), festivo.getDia()));
                if (fechaFestivo.equals(fecha)) {
                    return true;
                }
            }
            // Calcular Pascua
            LocalDate domingoPascua = agregarDias(getInicioSemanaSanta(año), 7);
            // Festivos (Tipo 3)
            if (festivo.getTipo().getTipo().equals("Basado en Pascua")) {
                fechaFestivo = agregarDias(domingoPascua, festivo.getDiasPascua());
                if (fechaFestivo.equals(fecha)) {
                    return true;
                }
            }
            // Festivos (Tipo 4)
            if (festivo.getTipo().getTipo().equals("Basado en Pascua y Ley Puente Festivo")) {
                fechaFestivo = agregarDias(domingoPascua, festivo.getDiasPascua());
                fechaFestivo = siguienteLunes(fechaFestivo);
                if (fechaFestivo.equals(fecha)) {
                    return true;
                }
            }
        }
        return false;
    }
}
