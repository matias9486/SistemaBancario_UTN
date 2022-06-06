package dao;

import java.util.List;
import modelo.Cliente;


/**
 * Interfaz que muestra los métodos a implementar por el DAO Cliente.
 * @author Matias Alancay
 * @version 1.0
 */
public interface IClienteDAO {	
    
    /**
     * Metodo que permite buscar un cliente en la base de datos por su DNI
     * @param dni perteneciente al cliente a buscar
     * @return <code>Cliente</code> con sus datos en caso de encontrarlo o null en caso contrario.
     */ 
    Cliente buscarClientePorDni(String dni);    
    
    /**
     * Metodo que permite agregar un cliente en la base de datos
     * @param cliente objeto con los datos a agregar correspondientes a un cliente.     
     */ 
    void agregarCliente(Cliente cliente);
        
    /**
     * Metodo que permite buscar clientes por sucursal utilizando su numero Sucursal
     * @param numeroSucursal id de la sucursal de la cual se buscaran sus clientes
     * @return Lista de <code>Cliente</code> vacia en caso que no tenga clientes o una lista con Clientes y sus datos correspondientes.
     */ 
    List<Cliente> buscarClientesPorSucursal(Integer numeroSucursal);
    
    List<Cliente> obtenerClientes();
}
