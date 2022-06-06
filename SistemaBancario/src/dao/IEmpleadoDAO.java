package dao;

import modelo.Empleado;

/**
 * Interfaz que muestra los métodos a implementar por el DAO Empleado.
 * @author Matias Alancay
 * @version 1.0
 */
public interface IEmpleadoDAO {
    /**
     * Este metodo agrega un empleado en la base de datos
     * @param empleado datos a agregar correspondientes a un empleado.     
     */ 
    void agregarEmpleado(Empleado empleado);
}
