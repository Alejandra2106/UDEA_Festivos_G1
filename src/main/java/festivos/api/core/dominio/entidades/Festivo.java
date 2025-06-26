package festivos.api.core.dominio.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "festivo")
public class Festivo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "festivo_id_seq")
    @SequenceGenerator(name="festivo_id_seq", sequenceName = "festivo_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;
    @Column(name = "nombre", length = 100, unique = true)
    private String nombre;
    @Column(name = "dia")
    private int dia;
    @Column(name = "mes")
    private int mes;
    @Column(name = "diaspascua")
    private int diaspascua;
    @Id
    @ManyToOne
    @JoinColumn(name = "idtipo", referencedColumnName = "id", nullable = false)
    private Tipo tipo;
    
    public Festivo(int id, String nombre, int dia, int mes, int diaspascua, Tipo tipo) {
        this.id = id;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.diaspascua = diaspascua;
        this.tipo = tipo;
    }
    
    public Festivo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
    public int getDiasPascua() {
        return diaspascua;
    }

    public void setDiasPascuas(int diaspascua) {
        this.diaspascua = diaspascua;
    }

    public Tipo getPais() {
        return tipo;
    }

    public void setPais(Tipo tipo) {
        this.tipo = tipo;
    }
    
}
