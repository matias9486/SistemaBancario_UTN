package modelo;

/**
 * Clase que representa una Cuenta de tipo Cuenta Corriente con sus datos correspondientes. Hereda de la clase <code>Cuenta</code>
 * @author Matias Alancay
 * @version 1.0
 */
public class CuentaCorriente extends Cuenta {

        public CuentaCorriente(Double saldo, String cbu,Cliente cliente,Banco banco,String tipoCuenta,String tipoMoneda) {
		super(saldo, cbu,cliente,banco,tipoCuenta,tipoMoneda);
	}
        
	public CuentaCorriente(Integer numCuenta, Double saldo, String cbu,Cliente cliente,Banco banco,String tipoCuenta,String tipoMoneda) {
		super(numCuenta, saldo, cbu,cliente,banco,tipoCuenta,tipoMoneda);
	}

    @Override
    public String toString() {
        return "*Cuenta Corriente:\n\t" +super.toString();
    }

        
}
