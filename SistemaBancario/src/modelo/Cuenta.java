package modelo;

/**
 * Clase abstracta que representa a una Cuenta bancaria con sus datos correspondientes
 * @author Matias Alancay
 * @version 1.0
 */
public abstract class Cuenta {

    protected Integer numCuenta;
    protected Double saldo;
    protected String cbu;
    protected Cliente cliente;
    protected Banco banco;
    protected String tipoCuenta;
    protected String tipoMoneda;
    // Constructor
    public Cuenta() {
    }

    public Cuenta(Double saldo, String cbu, Cliente cliente,Banco banco, String tipoCuenta, String tipoMoneda) {        
        this.saldo = saldo;
        this.cbu = cbu;
        this.cliente = cliente;
        this.banco=banco;
        this.tipoCuenta=tipoCuenta;
        this.tipoMoneda=tipoMoneda;
    }
    
    public Cuenta(Integer numCuenta, Double saldo, String cbu, Cliente cliente,Banco banco, String tipoCuenta, String tipoMoneda) {
        this.numCuenta = numCuenta;
        this.saldo = saldo;
        this.cbu = cbu;
        this.cliente = cliente;
        this.banco=banco;
        this.tipoCuenta=tipoCuenta;
        this.tipoMoneda=tipoMoneda;
    }   

    public Integer getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(Integer numCuenta) {
        this.numCuenta = numCuenta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }
    
    
    
    @Override
    public String toString() {
        return " Nro Cuenta: "+numCuenta + "\tSaldo:" + saldo + "\tCBU:" + cbu+"\tMoneda:"+tipoMoneda+ "\tCliente=" + cliente.getApellido()+", "+cliente.getNombre();
    }

    
}
