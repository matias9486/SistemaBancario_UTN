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
 * Clase que permite realizar consultas sobre clientes en la base de datos
 * @author Matias Alancay
 * @version 1.0
 */
public class ClienteDAO_ implements IClienteDAO {

    private final String URL = "jdbc:mysql://localhost:3306/sistemaBancario_MatiasAlancay";
    private final String USER = "root";
    private final String CLAVE = "admin";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection con;

    private PreparedStatement ps;
    private ResultSet rs;

    private static ClienteDAO_ instance = new ClienteDAO_();

    private ClienteDAO_() {
    }
    
    public static ClienteDAO_ getInstance() {
        return instance;
    }

    @Override
    public Cliente buscarClientePorDni(String dni) {
        Cliente clienteBuscado = null;

        PreparedStatement psSucursal;
        ResultSet rsSucursal;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            ps = con.prepareStatement("select clienteId,dni,nombre,apellido,telefono,email,SucursalId,domicilio,altaCliente from clientes where dni=?");
            ps.setString(1, dni);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String dni_ = rs.getString(2);
                String domicilio = rs.getString(8);
                
                Date altaCliente = rs.getDate(9);
                String nombre = rs.getString(3);
                String apellido = rs.getString(4);
                String telefono = rs.getString(5);
                String email = rs.getString(6);

                //busco sucursal
                int idSucursal = rs.getInt(7);
                Sucursal sucursal = null;
                psSucursal = con.prepareStatement("select sucursalId,nombre,bancoId from sucursales where sucursalId=?");
                psSucursal.setInt(1, idSucursal);
                rsSucursal = psSucursal.executeQuery();

                if (rs.next()) {
                    sucursal = new Sucursal(rsSucursal.getInt(1), rsSucursal.getString(2), rsSucursal.getInt(3));
                }
                clienteBuscado = new Cliente(id, domicilio, altaCliente, sucursal, dni_, nombre, apellido, telefono, email);              
            }
            
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
        return clienteBuscado;

    }

    @Override
    public void agregarCliente(Cliente cliente) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            ps = con.prepareStatement("insert into Clientes(dni,nombre,apellido,telefono,email,SucursalId,domicilio,altaCliente,bancoId) values(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEmail());
            ps.setInt(6, cliente.getSucursal().getNumeroSucursal());
            ps.setString(7, cliente.getDomicilio());
            
            ps.setDate(8, cliente.getAltaCliente());

            ps.setInt(9, cliente.getSucursal().getBancoId());

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
    public List<Cliente> obtenerClientes() {
        List<Cliente> lista = new ArrayList<>();
        PreparedStatement psSucursal;
        ResultSet rsSucursal;
        PreparedStatement psCuentas;
        ResultSet rsCuentas;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            ps = con.prepareStatement("select clienteId,dni,nombre,apellido,telefono,email,SucursalId,domicilio,altaCliente from clientes;");
            rs = ps.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt(1);
                String dni = rs.getString(2);
                String domicilio = rs.getString(8);
                Date altaCliente = rs.getDate(9);
                String nombre = rs.getString(3);
                String apellido = rs.getString(4);
                String telefono = rs.getString(5);
                String email = rs.getString(6);

                int idSucursal = rs.getInt(7);
                Sucursal sucursal = null;//buscar sucursal
                psSucursal = con.prepareStatement("select sucursalId,nombre,bancoId from sucursales where sucursalId=?");
                psSucursal.setInt(1, idSucursal);
                rsSucursal = psSucursal.executeQuery();

                if (rsSucursal.next()) {
                    sucursal = new Sucursal(rsSucursal.getInt(1), rsSucursal.getString(2), rsSucursal.getInt(3));
                }

                Cliente aux = new Cliente(id, domicilio, altaCliente, sucursal, dni, nombre, apellido, telefono, email);

                List<Cuenta> cuentas = new ArrayList<>();
                psCuentas = con.prepareStatement("select cuentaId,cbu,saldo,clienteId,bancoId,tipoCuenta,tipoMoneda from cuentas where clienteId=?;");
                psCuentas.setInt(1, aux.getId());
                rsCuentas = psCuentas.executeQuery();
                while (rsCuentas.next()) {
                    int cuentaId = rsCuentas.getInt(1);
                    String cbu = rsCuentas.getString(2);
                    double saldo = rsCuentas.getDouble(3);
                    String tipoCuenta = rsCuentas.getString(6);
                    String tipoMoneda = rsCuentas.getString(7);
                    Cuenta cuenta;
                    if (tipoCuenta.equals(TipoCuenta.CAJA_AHORRO.getDescripcion())) {
                        cuenta = new CajaDeAhorro(cuentaId, saldo, cbu, aux, null, tipoCuenta, tipoMoneda);
                    } else {
                        cuenta = new CuentaCorriente(cuentaId, saldo, cbu, aux, null, tipoCuenta, tipoMoneda);
                    }
                    cuentas.add(cuenta);
                }
                aux.setCuentas(cuentas);
                lista.add(aux);
            }            
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
        return lista;
    }

    @Override
    public List<Cliente> buscarClientesPorSucursal(Integer numeroSucursal) {
        List<Cliente> clientes = new ArrayList<>();
        
        
        PreparedStatement psClientes;
        ResultSet rsClientes;

        PreparedStatement psCuentas;
        ResultSet rsCuentas;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE); 
            con.setAutoCommit(false); //deshabilito los commit automaticos
            //armo lista cliente
            
            psClientes = con.prepareStatement("select clienteId,dni,nombre,apellido,telefono,email,SucursalId,domicilio,altaCliente from clientes where SucursalId=?;");
            psClientes.setInt(1, numeroSucursal);
            rsClientes = psClientes.executeQuery();

            while (rsClientes.next()) {
                Integer clienteId = rsClientes.getInt(1);
                String dni = rsClientes.getString(2);
                String domicilio = rsClientes.getString(8);
                Date altaCliente = rsClientes.getDate(9);
                String nombre = rsClientes.getString(3);
                String apellido = rsClientes.getString(4);
                String telefono = rsClientes.getString(5);
                String email = rsClientes.getString(6);

                Cliente cliente = new Cliente(clienteId, domicilio, altaCliente, null, dni, nombre, apellido, telefono, email);

                List<Cuenta> cuentas = new ArrayList<>();
                psCuentas = con.prepareStatement("select cuentaId,cbu,saldo,clienteId,bancoId,tipoCuenta,tipoMoneda from cuentas where clienteId=?;");
                psCuentas.setInt(1, clienteId);
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
                }//fin while cuentas

                cliente.setCuentas(cuentas);
                clientes.add(cliente);

            }//fin while clientes            
            
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
        return clientes;

    }
}
