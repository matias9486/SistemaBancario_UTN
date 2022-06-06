package modelo;

/**
 * Clase que representa una Cuanta de tipo Caja de ahorro con sus datos correspondientes. Hereda de la clase <code>Cuenta</code>
 * @author Matias Alancay
 * @version 1.0
 */
public class CajaDeAhorro extends Cuenta {	
	
	// COSNTRUCTOR
	public CajaDeAhorro() {
	}

        public CajaDeAhorro(Double saldo, String cbu,Cliente cliente,Banco banco, String tipoCuenta, String tipoMoneda) {
		super(saldo, cbu,cliente,banco,tipoCuenta,tipoMoneda);		
	}
        
	public CajaDeAhorro(Integer numCuenta, Double saldo, String cbu,Cliente cliente,Banco banco, String tipoCuenta, String tipoMoneda) {
		super(numCuenta, saldo, cbu,cliente,banco,tipoCuenta,tipoMoneda);		
	}
    
    @Override
    public String toString() {
        return "*Caja De Ahorro:\n\t" +super.toString();
    }        
}
