package servicios;

import dao.BancoDAO_;
import dao.IBancoDAO;
import modelo.Banco;

/**
 * Clase que se comunica con la capa de datos de Banco.
 * @author Matias Alancay
 * @version 1.0
 */
public class BancoService implements IBancoService{
    IBancoDAO bancosDao;
    
    public BancoService()
    {
        this.bancosDao=BancoDAO_.getInstance();
    }
    
    @Override
    public Banco buscarBancoPorId(int id) {
        return bancosDao.buscarBancoPorId(id);
    }
    
}
