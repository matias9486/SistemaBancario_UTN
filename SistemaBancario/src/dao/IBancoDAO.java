package dao;

import modelo.Banco;

/**
 * Interfaz que muestra los métodos a implementar por el DAO Banco.
 * @author Matias Alancay
 * @version 1.0
 */
public interface IBancoDAO {
    /**
     * Metodo que permite buscar un banco en la base de datos por su Id
     * @param id perteneciente al banco a buscar
     * @return <code>Banco</code> con sus datos correspondientes en caso de encontrarlo o null en caso contrario.
     */ 
    Banco buscarBancoPorId(int id);    
}
