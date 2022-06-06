package dao;

import constants.TipoCuenta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import modelo.CajaDeAhorro;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.CuentaCorriente;
import modelo.Sucursal;

/**
 * Clase que permite realizar consultas sobre cuentas en la base de datos
 * @author Matias Alancay
 * @version 1.0
 */
public class CuentaDAO_ implements ICuentaDAO {

    private final String URL = "jdbc:mysql://localhost:3306/sistemaBancario_MatiasAlancay";
    private final String USER = "root";
    private final String CLAVE = "admin";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection con;

    private PreparedStatement ps;
    private ResultSet rs;

    private static CuentaDAO_ instance = new CuentaDAO_();

    private CuentaDAO_() {
    }

    public static CuentaDAO_ getInstance() {
        return instance;
    }
   
    @Override
    public Cuenta buscarCuentaPorCBU(String cbu) {
        Cuenta cuenta = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            ps = con.prepareStatement("select cuentaId,cbu,saldo,clienteId,bancoId,tipoCuenta,tipoMoneda from cuentas where cbu=?;");
            ps.setString(1, cbu);
            rs = ps.executeQuery();
            if (rs.next()) {
                int cuentaId = rs.getInt(1);
                String cbu_ = rs.getString(2);
                double saldo = rs.getDouble(3);
                int clienteId = rs.getInt(4);
                String tipoCuenta = rs.getString(6);
                String tipoMoneda = rs.getString(7);

                Cliente clienteBuscado = null;

                PreparedStatement psClientes = con.prepareStatement("select clienteId,dni,nombre,apellido,telefono,email,SucursalId,domicilio,altaCliente from clientes where clienteId=?;");
                psClientes.setInt(1, clienteId);
                ResultSet rsClientes = psClientes.executeQuery();

                if (rsClientes.next()) {
                    String dni = rsClientes.getString(2);
                    String domicilio = rsClientes.getString(8);
                    Date altaCliente = rsClientes.getDate(9);
                    String nombre = rsClientes.getString(3);
                    String apellido = rsClientes.getString(4);
                    String telefono = rsClientes.getString(5);
                    String email = rsClientes.getString(6);

                    Sucursal sucursal = null;
                    clienteBuscado = new Cliente(clienteId, domicilio, altaCliente, sucursal, dni, nombre, apellido, telefono, email);
                }

                if (tipoCuenta.equals(TipoCuenta.CAJA_AHORRO.getDescripcion())) {
                    cuenta = new CajaDeAhorro(cuentaId, saldo, cbu, clienteBuscado, null, tipoCuenta, tipoMoneda);
                } else {
                    cuenta = new CuentaCorriente(cuentaId, saldo, cbu, clienteBuscado, null, tipoCuenta, tipoMoneda);
                }
            }

            con.commit();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return cuenta;
    }
    
    @Override
    public void agregarCuenta(Cuenta cuenta) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            ps = con.prepareStatement("insert into Cuentas(cbu,saldo,clienteId,bancoId,tipoCuenta,tipoMoneda) values(?,?,?,?,?,?)");
            ps.setString(1, cuenta.getCbu());
            ps.setDouble(2, cuenta.getSaldo());
            ps.setInt(3, cuenta.getCliente().getId());
            ps.setInt(4, cuenta.getBanco().getId());
            ps.setString(5, cuenta.getTipoCuenta());
            ps.setString(6, cuenta.getTipoMoneda());

            int i = ps.executeUpdate();

            con.commit();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
  
    @Override
    public void depositarDinero(Cuenta cuenta, Double monto) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            ps = con.prepareStatement("UPDATE cuentas SET saldo =saldo+? WHERE (cuentaId = ?);");
            ps.setDouble(1, monto);
            ps.setLong(2, cuenta.getNumCuenta());

            int i = ps.executeUpdate();
            con.commit();
        } catch (SQLException | ClassNotFoundException ex) {
            try {
                con.rollback();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(ex.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @Override
    public void extraerDinero(Cuenta cuenta, Double monto) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            ps = con.prepareStatement("UPDATE cuentas SET saldo =saldo-? WHERE (cuentaId = ?);");
            ps.setDouble(1, monto);
            ps.setLong(2, cuenta.getNumCuenta());

            int i = ps.executeUpdate();
            con.commit();
        } catch (SQLException | ClassNotFoundException ex) {
            try {
                con.rollback();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(ex.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    @Override
    public void transferirDinero(Cuenta cuentaOrigen, Cuenta cuentaDestino, Double monto) {
        try {
            String URL = "jdbc:mysql://localhost:3306/sistemaBancario_MatiasAlancay";
            String USER = "root";
            String CLAVE = "admin";
            Connection con;
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            //actualizo cuenta origen
            ps = con.prepareStatement("UPDATE cuentas SET saldo =saldo-? WHERE (cbu = ?);");
            ps.setDouble(1, monto);
            ps.setString(2, cuentaOrigen.getCbu());

            //actualizo cuenta destino
            PreparedStatement psCuentaDestino;
            psCuentaDestino = con.prepareStatement("UPDATE cuentas SET saldo =saldo+? WHERE (cbu = ?);");
            psCuentaDestino.setDouble(1, monto);
            psCuentaDestino.setString(2, cuentaDestino.getCbu());

            psCuentaDestino.executeUpdate();
            ps.executeUpdate();
            
            //confirmar cambios
            con.commit();
        } catch (SQLException | ClassNotFoundException ex) {
            try {
                //revertir cambios
                con.rollback();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(ex.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public List<Cuenta> buscarCuentasPorClienteId(Cliente cliente) {
        List<Cuenta> cuentas = new ArrayList<>();

        PreparedStatement psCuentas;
        ResultSet rsCuentas;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            psCuentas = con.prepareStatement("select cuentaId,cbu,saldo,clienteId,bancoId,tipoCuenta,tipoMoneda from cuentas where clienteId=?;");
            psCuentas.setInt(1, cliente.getId());
            rsCuentas = psCuentas.executeQuery();
            while (rsCuentas.next()) {
                int cuentaId = rsCuentas.getInt(1);
                String cbu = rsCuentas.getString(2);
                double saldo = rsCuentas.getDouble(3);
                String tipoCuenta = rsCuentas.getString(6);
                String tipoMoneda = rsCuentas.getString(7);
                Cuenta cuenta;
                if (tipoCuenta.equals(TipoCuenta.CAJA_AHORRO.getDescripcion())) {
                    cuenta = new CajaDeAhorro(cuentaId, saldo, cbu, cliente, null, tipoCuenta, tipoMoneda);
                } else {
                    cuenta = new CuentaCorriente(cuentaId, saldo, cbu, cliente, null, tipoCuenta, tipoMoneda);
                }
                cuentas.add(cuenta);
            }

            //confirmar cambios
            con.commit();
        } catch (SQLException | ClassNotFoundException ex) {
            try {
                //revertir cambios
                con.rollback();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(ex.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return cuentas;

    }

}
