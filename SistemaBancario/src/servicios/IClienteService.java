package servicios;

import java.util.List;
import modelo.Cliente;

/**
 * Interfaz que muestra los métodos a implementar por el servicio Cliente.
 * @author Matias Alancay
 * @version 1.0
 */
public interface IClienteService {

    /**
     * Este metodo se comunica con la capa de base de datos para buscar un cliente segun su Dni
     * @param dni perteneciente al cliente a buscar
     * @return <code>Cliente</code> con sus datos en caso de encontrarlo o null en caso contrario.
     */ 
    Cliente buscarClientePorDni(String dni);    
    
    /**
     * Este metodo se comunica con la capa de base de datos para agregar un cliente
     * @param cliente datos a agregar correspondientes a un cliente.     
     */ 
    void agregarCliente(Cliente cliente);
    
    
    List<Cliente> obtenerClientes();

}
