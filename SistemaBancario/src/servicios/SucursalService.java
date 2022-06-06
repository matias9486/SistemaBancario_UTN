package servicios;

import Exceptions.NoExisteSucursalException;
import dao.IClienteDAO;
import dao.ICuentaDAO;
import dao.ISucursalDAO;
import dao.SucursalDAO_;
import dao.ClienteDAO_;
import dao.CuentaDAO_;
import java.util.List;
import modelo.Cliente;
import modelo.Sucursal;

/**
 * Clase que se comunica con la capa de datos de Sucursales.
 * @author Matias Alancay
 * @version 1.0
 */
public class SucursalService implements ISucursalService{

    private ISucursalDAO sucursalesDao;
    private IClienteDAO clientesDao;
    private ICuentaDAO cuentasDao;

    public SucursalService() {        
        this.sucursalesDao = SucursalDAO_.getInstance();
        this.clientesDao= ClienteDAO_.getInstance();
        this.cuentasDao= CuentaDAO_.getInstance();
    }
    
    @Override
    public Sucursal buscarSucursalPorId(Integer nroSucursal) {
        Sucursal sucursalBuscada=sucursalesDao.buscarSucursalPorId(nroSucursal);
        
        if(sucursalBuscada!=null)
        {            
            //agrego clientes
            List<Cliente> clientes= clientesDao.buscarClientesPorSucursal(sucursalBuscada.getNumeroSucursal());                
            sucursalBuscada.setListaClientes(clientes);
        }        
        return sucursalBuscada;
    }        
    
    @Override
    public List<Sucursal> buscarSucursalesPorBanco(int bancoId) {
        return sucursalesDao.buscarSucursalesPorBanco(bancoId);
    }

    
    @Override
    public void agregarSucursal(Sucursal sucursal) {
        sucursalesDao.agregarSucursal(sucursal);
    }   
    
    @Override
    public void eliminarSucursal(int sucursalEliminarId, int sucursalDestinoId) throws NoExisteSucursalException{
        boolean existeSucursalEliminada=sucursalesDao.comprobarExistenciaSucursal(sucursalDestinoId);
        boolean existeSucursalDestino=sucursalesDao.comprobarExistenciaSucursal(sucursalEliminarId);
        if(existeSucursalEliminada && existeSucursalDestino )
            sucursalesDao.eliminarSucursal(sucursalEliminarId, sucursalDestinoId);
        else
            throw new NoExisteSucursalException("No se encontro sucursal.");
    }
                
}
