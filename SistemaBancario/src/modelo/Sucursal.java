package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una Sucursal de un banco con sus datos correspondientes.
 * @author Matias Alancay
 * @version 1.0
 */
public class Sucursal {
    // Atributos
    private String nombreSucursal;
    private Integer numeroSucursal;
    private List<Cliente> listaClientes;
    private List<Empleado> listaEmpleados;
    private int bancoId;
    
    public Sucursal(String nombreSucursal,int bancoId) {
        this.nombreSucursal = nombreSucursal;        
        this.bancoId=bancoId;
        this.listaClientes = new ArrayList<Cliente>();
        this.listaEmpleados = new ArrayList<Empleado>();
    }
    
    public Sucursal(Integer numeroSucursal,String nombreSucursal,int bancoId) {
        this.nombreSucursal = nombreSucursal;
        this.numeroSucursal = numeroSucursal;
        this.bancoId=bancoId;
        this.listaClientes = new ArrayList<Cliente>();
        this.listaEmpleados = new ArrayList<Empleado>();
    }

    // METODOS
    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public Integer getNumeroSucursal() {
        return numeroSucursal;
    }

    public void setNumeroSucursal(Integer numeroSucursal) {
        this.numeroSucursal = numeroSucursal;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public int getBancoId() {
        return bancoId;
    }

    public void setBancoId(int bancoId) {
        this.bancoId = bancoId;
    }
    

    
    /**
     * Método utilizado para buscar un Empleado por Dni
     * @param dni nro de documento perteneciente al empleado
     * @return Empleado buscado por <code>dni</code>. Null en caso que no lo encuentre.
     */
    public Empleado obtenerEmpleado(String dni) {
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getDni().equals(dni)) {
                return empleado;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return nombreSucursal + ", Nro:" + numeroSucursal;
    }
    
    
}
