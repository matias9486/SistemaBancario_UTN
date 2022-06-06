package servicios;

import modelo.Empleado;

/**
 * Interfaz que muestra los métodos a implementar por el servicio Empleado.
 * @author Matias Alancay
 * @version 1.0
 */
public interface IEmpleadoService {
    
    /**
     * Este metodo se comunica con la capa de base de datos para agregar un empleado
     * @param empleado datos a agregar correspondientes a un empleado.     
     */ 
    void agregarEmpleado(Empleado empleado);
}
