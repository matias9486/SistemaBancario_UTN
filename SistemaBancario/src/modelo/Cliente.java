package modelo;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Clase que representa a un Cliente con sus datos correspondientes
 * @author Matias Alancay
 * @version 1.0
 */
public class Cliente extends Persona {
    // ATRIBUTOS

    private String domicilio;
    private Date altaCliente;  //es sql.Date   
    private List<Cuenta> cuentas;
        
    public Cliente() {        
        super();
    }
      
    public Cliente(String domicilio, Date altaCliente, Sucursal sucursal, String dni, String nombre,String apellido, String telefono, String email) {
        super(dni, nombre,apellido, telefono, email,sucursal);
        this.domicilio = domicilio;
        this.altaCliente = altaCliente;        
        this.cuentas = new ArrayList<>();
    }
    
    public Cliente(Integer id,String domicilio, Date altaCliente, Sucursal sucursal, String dni, String nombre,String apellido, String telefono, String email) {
        super(id,dni, nombre,apellido, telefono, email,sucursal);
        this.domicilio = domicilio;
        this.altaCliente = altaCliente;        
        this.cuentas = new ArrayList<>();
    }
            
    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Date getAltaCliente() {
        return altaCliente;
    }

    public void setAltaCliente(Date altaCliente) {
        this.altaCliente = altaCliente;
    }
    
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    @Override
    public String toString() {
        return "Cliente:"+this.getApellido()+", "+this.getNombre()+"\tDNI:"+ this.getDni()+ "\tDomicilio:" + domicilio +"\tFecha alta:"+this.getAltaCliente().toLocalDate().toString();
    }

    
}
