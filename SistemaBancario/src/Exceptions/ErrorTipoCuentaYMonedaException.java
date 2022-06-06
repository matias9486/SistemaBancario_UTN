package Exceptions;

/**
 * Excepcion personalizada que se produce cuando se intenta realizar una transferencia entre distintos tipos de cuenta y de moneda.
 * @author Matias Alancay
 * @version 1.0
 */
public class ErrorTipoCuentaYMonedaException  extends Exception {
    public ErrorTipoCuentaYMonedaException(String mensaje) {
           super("Se ha producido un error:" + mensaje);
        }
}
