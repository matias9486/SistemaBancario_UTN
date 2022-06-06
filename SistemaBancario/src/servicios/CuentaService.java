package servicios;

import Exceptions.ErrorTipoCuentaYMonedaException;
import dao.CuentaDAO_;
import dao.ICuentaDAO;
import modelo.Cuenta;

/**
 * Clase que se comunica con la capa de datos de Clientes.
 * @author Matias Alancay
 * @version 1.0
 */
public class CuentaService implements ICuentaService {
        
    private ICuentaDAO cuentasDao;

    public CuentaService() {        
        this.cuentasDao = CuentaDAO_.getInstance();
    }
    
    @Override
    public Cuenta buscarCuentaPorCBU(String cbu) {
        return cuentasDao.buscarCuentaPorCBU(cbu);
    }
    
    @Override
    public void agregarCuenta(Cuenta cuenta) {
        cuentasDao.agregarCuenta(cuenta);        
    }  
    
    @Override
    public boolean extraerDinero(Cuenta cuenta, Double monto) {
        Cuenta cuentaBuscada= cuentasDao.buscarCuentaPorCBU(cuenta.getCbu());
        if(cuentaBuscada!=null)
        {
            cuentasDao.extraerDinero(cuentaBuscada, monto);
            return true;
        }
        return false;
    }

    @Override
    public boolean depositarDinero(Cuenta cuenta,Double monto) {        
        Cuenta cuentaBuscada= cuentasDao.buscarCuentaPorCBU(cuenta.getCbu());
        if(cuentaBuscada!=null)
        {
            cuentasDao.depositarDinero(cuentaBuscada, monto);
            return true;
        }
        return false;
    }
        

    @Override
    public boolean transferirDinero(Cuenta cuentaOrigen, String cbuCuentaDestino, Double monto) throws ErrorTipoCuentaYMonedaException{
        //buscar cuenta destino
        Cuenta cuentaDestino=cuentasDao.buscarCuentaPorCBU(cbuCuentaDestino);
        if(cuentaDestino!=null)
        {
            //si existe buscar la cuenta origen
            Cuenta cOrigen=cuentasDao.buscarCuentaPorCBU(cuentaOrigen.getCbu());
            //comparar que sean del mismo tipo de moneda y tipo de cuenta            
            if(cOrigen!=null && cOrigen.getTipoCuenta().equals(cuentaDestino.getTipoCuenta()) && cOrigen.getTipoMoneda().equals(cuentaDestino.getTipoMoneda()))
            {
                //realizar transferencia
                cuentasDao.transferirDinero(cOrigen, cuentaDestino, monto);                                                
                return true;
            }      
            else
                throw new ErrorTipoCuentaYMonedaException("No coincide el tipo de cuenta o moneda.");
        }        
        return false;
    }   
}
