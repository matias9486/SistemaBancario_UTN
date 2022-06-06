package Exceptions;

/**
 * Excepcion personalizada que se produce al buscar una sucursal y no encontrarla..
 * @author Matias Alancay
 * @version 1.0
 */
public class NoExisteSucursalException extends Exception{
    public NoExisteSucursalException(String mensaje) {
           super("Se ha producido un error:" + mensaje);
        }
}
