package servicios;

import Exceptions.ErrorTipoCuentaYMonedaException;
import modelo.Cuenta;

/**
 * Interfaz que muestra los métodos a implementar por el servicio de Cuentas.
 * @author Matias Alancay
 * @version 1.0
 */
public interface ICuentaService {
    /**
     * Este metodo se comunica con la capa de base de datos para buscar una cuenta segun su CBU
     * @param cbuque identifica a la cuenta a buscar
     * @return <code>Cuenta</code> con los datos correspondietes en caso de ser encontrada, o null en caso contrario.
     */ 
    Cuenta buscarCuentaPorCBU(String cbu);
    
    /**
     * Este metodo se comunica con la capa de base de datos para agregar una cuenta
     * @param cuenta datos a agregar correspondientes a una cuenta.  
     */ 
    void agregarCuenta(Cuenta cuenta);    
    
    /**
     * Este metodo se comunica con la capa de base de datos para actualizar el saldo de una cuenta luego de extraer dinero
     * @param cuenta sobre la cual se realizara la extraccion de dinero
     * @param monto cantidad de dinero a extraer de la cuenta
     * @return <code>True</code> si se pudo realizar la extraccion o <code>False</code> en caso contrario.
     */ 
    boolean extraerDinero(Cuenta cuenta,Double monto);
    
    /**
     * Este metodo se comunica con la capa de base de datos para actualizar el saldo de una cuenta luego de depositar dinero
     * @param cuenta sobre la cual se realizara el deposito de dinero
     * @param monto cantidad de dinero a depositar en la cuenta
     * @return <code>True</code> si se pudo realizar el deposito o <code>False</code> en caso contrario.
     */ 
    boolean depositarDinero(Cuenta cuenta,Double monto);
    
     /**
     * Metodo que permite realizar una transferencia a otra cuenta utilizando una
     * transaccion. Para que se pueda transeferir las cuentas deben ser del
     * mismo tipo de cuenta y de moneda.
     *
     * @param cuentaOrigen es la cuenta desde la cual se extraera dinero
     * @param cuentaDestino es la cuenta a la cual se depositara dinero
     * @param monto es la cantidad a ser transferida
     * @throws ErrorTipoCuentaYMonedaException En caso que las cuentas no sean del mismo tipo de cuenta y tipo de moneda.
     * @return <code>True</code> si se logro realizar la transferencia. <code>False</code> en caso contrario.
     */
    boolean transferirDinero(Cuenta cuentaOrigen, String cbuCuentaDestino, Double monto)throws ErrorTipoCuentaYMonedaException;
}
