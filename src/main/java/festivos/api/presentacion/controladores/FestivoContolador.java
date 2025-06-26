package festivos.api.presentacion.controladores;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import festivos.api.core.dominio.entidades.Festivo;
import festivos.api.core.interfaces.servicios.IFestivoServicio;

@RestController
@RequestMapping("/api/festivos")
public class FestivoContolador {

    private IFestivoServicio servicio;

    public FestivoContolador(IFestivoServicio servicio) {
        this.servicio = servicio;
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Festivo> listar() {
        return servicio.listar();
    }

    @GetMapping("/listar/{año}")
    public List<Festivo> ListarPorAño(@PathVariable int año) {
        return servicio.ListarPorAño(año);
    }

    @GetMapping("/validar/{año}/{mes}/{dia}")
    public ResponseEntity<String> validar(@PathVariable int año, @PathVariable int mes, @PathVariable int dia) {
        try {
            LocalDate fecha = LocalDate.of(año, mes, dia);
            boolean esFestivo = servicio.validar(fecha);
            if (esFestivo) {
                return ResponseEntity.ok("Es Festivo");
            } else {
                return ResponseEntity.ok("No es Festivo");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fecha no válida");
        }
    }

}
