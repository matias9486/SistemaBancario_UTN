package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un Banco con sus datos correspondientes
 * @author Matias Alancay
 * @version 1.0
 */
public class Banco {
	// Atributos
	private Integer id;
	private String nombre;
	private List<Sucursal> sucursales;
	private List<Cuenta> cuentas;

	public Banco(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.sucursales = new ArrayList<>();
		this.cuentas = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}	

    @Override
    public String toString() {
        return "Banco Id:" + id + "\tNombre:" + nombre;
    }
        
        
}
