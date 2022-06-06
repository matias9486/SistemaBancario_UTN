package modelo;

import java.sql.Date;

/**
 * Clase que representa un Empleado con sus datos correspondientes. Hereda de la clase <code>Persona</code>
 * @author Matias Alancay
 * @version 1.0
 */
public class Empleado extends Persona {

    private Integer legajo;
    private Date fechaIngreso;//es sql.Date   
    

    public Empleado(Integer legajo, Date fechaIngreso, Sucursal sucursal, String dni, String nombre,String apellido, String telefono, String email) {
        super(dni, nombre,apellido, telefono, email,sucursal);
        this.legajo = legajo;
        this.fechaIngreso = fechaIngreso;
        
    }
    
    public Empleado(Integer id,Integer legajo, Date fechaIngreso, Sucursal sucursal, String dni, String nombre,String apellido, String telefono, String email) {
        super(id,dni, nombre,apellido, telefono, email,sucursal);
        this.legajo = legajo;
        this.fechaIngreso = fechaIngreso;        
    }

    public Integer getLegajo() {
        return legajo;
    }

    public void setLegajo(Integer legajo) {
        this.legajo = legajo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

}
