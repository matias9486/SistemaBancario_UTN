
package constants;

/**
 *
 * @author Hatake
 */
public enum TipoCuenta {

	CAJA_AHORRO("Caja de Ahorro", 1), CUENTA_CORRIENTE("Cuenta Corriente", 2);

	private Integer nroCuenta;
	private String descripcion;

	TipoCuenta(String descripcion, Integer nroCuenta) {
		this.nroCuenta = nroCuenta;
		this.descripcion = descripcion;
	}

	public Integer getNroCuenta() {
		return nroCuenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
