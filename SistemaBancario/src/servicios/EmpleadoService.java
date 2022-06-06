package servicios;

import dao.EmpleadoDAO_;
import dao.IEmpleadoDAO;
import modelo.Empleado;

/**
 * Clase que se comunica con la capa de datos de Empleados.
 * @author Matias Alancay
 * @version 1.0
 */
public class EmpleadoService implements IEmpleadoService{

    private IEmpleadoDAO empleadosDao;    

    public EmpleadoService() {        
        this.empleadosDao = EmpleadoDAO_.getInstance();        
    }
    @Override
    public void agregarEmpleado(Empleado empleado) {
        empleadosDao.agregarEmpleado(empleado);
    }        
}
