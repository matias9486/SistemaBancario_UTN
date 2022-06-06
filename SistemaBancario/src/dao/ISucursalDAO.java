package dao;

import java.util.List;
import modelo.Sucursal;

/**
 * Interfaz que muestra los métodos a implementar por el DAO Sucursales.
 * @author Matias Alancay
 * @version 1.0
 */
public interface ISucursalDAO {
    /**
     * Metodo que permite buscar una sucursal por su numero de sucursal en la base de datos.
     * @param nroSucursal numero de la sucursal a buscar
     * @return <code>null</code> en caso que no encuentre la sucursal o un objeto <code>Sucursal<code> con sus datos en caso de encontrarla.
     */ 
    Sucursal buscarSucursalPorId(Integer nroSucursal);            
    
    /**
     * Metodo que permite buscar todas las sucursales de un banco en la base de datos
     * @param bancoId id que identifica al banco del cual obtendremos sus sucursales
     * @return una lista de <code>Sucursal<code> con las sucursules del banco o una lista vacia.
     */ 
    List<Sucursal> buscarSucursalesPorBanco(int bancoId);  
    
    /**
     * Metodo que permite agregar una sucursal en la base de datos
     * @param sucursal datos correspondientes de la sucursal a agregar.     
     */ 
    void agregarSucursal(Sucursal sucursal);	    
                
    /**
     * Metodo que permite eliminar una sucursal en la base de datos y actualizar la sucursal de los clientes de la sucursal eliminada.
     * @param sucursalEliminarId id de la sucursal a eliminar
     * @param sucursalDestinoId id de la sucursal a la cual se transeferiran los clientes de la sucursal a eliminar
     */ 
    void eliminarSucursal(int sucursalEliminarId,int sucursalDestinoId);
    
    /**
     * Metodo que permite comprobar si existe una sucursal de un banco en la base de datos
     * @param nroSucursal id que identifica a la sucursal a buscar
     * @return <code>True</code> si la sucursal existe. <code>False</code> en caso contrario.
     */ 
    boolean comprobarExistenciaSucursal(int nroSucursal);    
}
