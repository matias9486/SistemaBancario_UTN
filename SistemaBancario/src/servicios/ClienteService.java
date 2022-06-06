package servicios;

import dao.ClienteDAO_;
import dao.CuentaDAO_;
import dao.IClienteDAO;
import dao.ICuentaDAO;
import java.util.List;
import modelo.Cliente;

/**
 * Clase que se comunica con la capa de datos de Clientes.
 * @author Matias Alancay
 * @version 1.0
 */
public class ClienteService implements IClienteService {

    private IClienteDAO clientesDao;    
    private ICuentaDAO cuentasDao;
    
    public ClienteService() {        
        this.clientesDao = ClienteDAO_.getInstance();        
        this.cuentasDao=CuentaDAO_.getInstance();
    }
                
    @Override
    public Cliente buscarClientePorDni(String dni) {
        Cliente clienteBuscado=clientesDao.buscarClientePorDni(dni);
        if(clienteBuscado!=null)
        {
            clienteBuscado.setCuentas(cuentasDao.buscarCuentasPorClienteId(clienteBuscado));
        }
        return clienteBuscado;
    }
    
    @Override
    public void agregarCliente(Cliente cliente) {
        clientesDao.agregarCliente(cliente);
    }
        

    @Override
    public List<Cliente> obtenerClientes() {
        return clientesDao.obtenerClientes();
    }

}
