package dao;

import java.util.List;
import modelo.Cliente;
import modelo.Cuenta;

/**
 * Interfaz que muestra los métodos a implementar por el DAO Cuenta.
 * @author Matias Alancay
 * @version 1.0
 */
public interface ICuentaDAO {  
    
    /**
     * Metodo que permite buscar una cuenta de un cliente en la base de datos segun su CBU
     * @param cbu que identifica a la cuenta a buscar
     * @return <code>Cuenta</code> con los datos correspondietes en caso de ser encontrada, o null en caso contrario.
     */ 
    Cuenta buscarCuentaPorCBU(String cbu);
    
    /**
     * Metodo que permite agregar una cuenta nueva a un cliente en la base de datos
     * @param cuenta objeto que contiene los datos pertenecientes a una cuenta correspondiente a un cliente     
     */ 
    void agregarCuenta(Cuenta cuenta);        
    
    /**
     * Metodo que permite modificar el saldo de una cuenta en la base de datos, luego de realizar un deposito.
     * @param cuenta objeto que contiene los datos pertenecientes a una cuenta correspondiente a un cliente     
     * @param monto cantidad de dinero a depositar en la cuenta.
     */ 
    void depositarDinero(Cuenta cuenta, Double monto);
    
    /**
     * Metodo que permite modificar el saldo de una cuenta en la base de datos, luego de realizar una extraccion.
     * @param cuenta objeto que contiene los datos pertenecientes a una cuenta correspondiente a un cliente     
     * @param monto cantidad de dinero a extraer de la cuenta.
     */ 
    void extraerDinero(Cuenta cuenta, Double monto);    
    
    /**
     * Metodo que permite modificar los saldos en la base de datos de las cuentas involucradas en una transferencia de dinero. Para ello, se utiliza una transaccion.     
     * @param cuentaOrigen es la cuenta desde la cual se extraera dinero
     * @param cuentaDestino es la cuenta a la cual se depositara dinero
     * @param monto es la cantidad a ser transferida
     */
    void transferirDinero(Cuenta cuentaOrigen, Cuenta cuentaDestino, Double monto);
    
    /**
     * Metodo que permite buscar las cuentas pertenecientes a un cliente en la base de datos por su Id
     * @param cliente objeto que contiene los datos pertenecientes al cliente a buscar 
     * @return Una lista de <code>Cuenta</code> del cliente, sin cuentas en caso que aun no se le hayan agregado o con todas sus cuentas si ya fueron agregadas.
     */ 
    List<Cuenta> buscarCuentasPorClienteId(Cliente cliente);
}
