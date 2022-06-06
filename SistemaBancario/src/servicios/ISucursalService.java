package servicios;

import Exceptions.NoExisteSucursalException;
import java.util.List;
import modelo.Sucursal;

/**
 * Interfaz que muestra los métodos a implementar por el servicio Sucursales.
 * @author Matias Alancay
 * @version 1.0
 */
public interface ISucursalService {
    
    /**
     * Este metodo busca una sucursal por su numero de sucursal.
     * @param nroSucursal numero de la sucursal a buscar
     * @return <code>null</code> en caso que no encuentre la sucursal o la sucursal con sus datos en caso de encontrarla.
     */ 
    Sucursal buscarSucursalPorId(Integer nroSucursal);
            
    /**
     * Este metodo obtiene las sucusales de un banco
     * @param bancoId id que identifica al banco del cual se obtendran sus sucursales
     * @return una lista de <code>Sucursal</code> con las sucursales del banco o una lista vacia.
     */ 
    List<Sucursal> buscarSucursalesPorBanco(int bancoId);    
    
    /**
     * Metodo que permite agregar una sucursal
     * @param sucursal datos correspondientes de la sucursal a agregar.     
     */ 
    void agregarSucursal(Sucursal sucursal);	
    
    /**
     * Este metodo elimina una sucursal de un banco.
     * @param sucursalEliminarId id de la sucursal a eliminar
     * @param sucursalDestinoId id de la sucursal a la cual se transeferiran los clientes de la sucursal a eliminar
     * @throws NoExisteSucursalException error que se produce en caso que no exista una sucursal.
     */ 
    void eliminarSucursal(int sucursalEliminarId, int sucursalDestinoId)  throws NoExisteSucursalException;        
}
