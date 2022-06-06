package servicios;

import modelo.Banco;

/**
 * Interfaz que muestra los métodos a implementar por el servicio Banco.
 * @author Matias Alancay
 * @version 1.0
 */
public interface IBancoService {
    /**
     * Este metodo se comunica con la capa de base de datos para buscar un Banco segun su <code>Id</code>
     * @param id perteneciente al banco a buscar
     * @return <code>Banco</code> con sus datos en caso de encontrarlo o null en caso contrario.
     */ 
    Banco buscarBancoPorId(int id);
}
