
package constants;

/**
 *
 * @author Hatake
 */
public enum TipoMoneda {

	PESOS("Pesos", 1), DOLARES("Dolares", 2);

	private Integer tipoMonedaId;
	private String descripcion;

	TipoMoneda(String descripcion, Integer tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
		this.descripcion = descripcion;
	}

	public Integer getTipoMonedaId() {
		return tipoMonedaId;
	}

	public String getDescripcion() {
		return descripcion;
	}

}