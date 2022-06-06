package modelo;

/**
 * Clase abstracta que representa una Persona con sus datos correspondientes.
 * @author Matias Alancay
 * @version 1.0
 */
public abstract class Persona {
    // Atributos
    protected Integer id;
    protected String dni;
    protected String nombre;
    protected String apellido;
    protected String telefono;
    protected String email;
    protected Sucursal sucursal;

    public Persona(){};
    // Constructor
    
    public Persona(String dni, String nombre,String apellido, String telefono, String email, Sucursal sucursal) {        
        this.dni = dni;
        this.nombre=nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.sucursal=sucursal;
    }
    
    public Persona(Integer id, String dni, String nombre,String apellido, String telefono, String email, Sucursal sucursal) {
        this.id = id;
        this.dni = dni;
        this.nombre=nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.sucursal=sucursal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
    
    

}
