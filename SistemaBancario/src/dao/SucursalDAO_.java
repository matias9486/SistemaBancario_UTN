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
 * Clase que permite realizar consultas sobre sucursales en la base de datos
 * @author Matias Alancay
 * @version 1.0
 */
public class SucursalDAO_ implements ISucursalDAO{

    private final String URL = "jdbc:mysql://localhost:3306/sistemaBancario_MatiasAlancay";
    private final String USER = "root";
    private final String CLAVE = "admin";           
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";        
    private Connection con;
        
    private PreparedStatement psSucursales;    
    private ResultSet rsSucursales;
      
    private static SucursalDAO_ sucursalDaoInstance = new SucursalDAO_();
    
    private SucursalDAO_() {}
    public static SucursalDAO_ getInstance() {
        return sucursalDaoInstance;
    }

    @Override
    public Sucursal buscarSucursalPorId(Integer nroSucursal) {
        Sucursal sucursalBuscada = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE); 
            con.setAutoCommit(false); //deshabilito los commit automaticos
            
            psSucursales = con.prepareStatement("select sucursalId,nombre,bancoId from sucursales where sucursalId=?");
            psSucursales.setInt(1, nroSucursal);
            rsSucursales = psSucursales.executeQuery();
            
            int sucursalId;
            String nombreSucursal;
            int bancoId;
            if (rsSucursales.next()) {
                sucursalId = rsSucursales.getInt(1);
                nombreSucursal = rsSucursales.getString(2);
                bancoId = rsSucursales.getInt(3);
                sucursalBuscada = new Sucursal(sucursalId, nombreSucursal, bancoId);            
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
        return sucursalBuscada;
    }
      
    @Override
    public List<Sucursal> buscarSucursalesPorBanco(int bancoId) {
        List<Sucursal> sucursales = new ArrayList<>();

        List<Cliente> clientes;
        PreparedStatement psSucursal;
        ResultSet rsSucursal;

        PreparedStatement psClientes;
        ResultSet rsClientes;

        PreparedStatement psCuentas;
        ResultSet rsCuentas;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE); 
            con.setAutoCommit(false); //deshabilito los commit automaticos
            
            //buscar sucursales por banco id=1
            psSucursales = con.prepareStatement("select sucursalId,nombre,bancoId from sucursales where bancoId=?");
            psSucursales.setInt(1, bancoId);
            rsSucursales = psSucursales.executeQuery();

            while (rsSucursales.next()) {
                //armo sucursal
                int sucursalId = rsSucursales.getInt(1);
                String nombreSucursal = rsSucursales.getString(2);
                bancoId = rsSucursales.getInt(3);
                Sucursal sucursal = new Sucursal(sucursalId, nombreSucursal, bancoId);

                //armo lista cliente
                clientes = new ArrayList<>();
                psClientes = con.prepareStatement("select clienteId,dni,nombre,apellido,telefono,email,SucursalId,domicilio,altaCliente from clientes where SucursalId=?;");
                psClientes.setInt(1, sucursalId);
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

                    Cliente cliente = new Cliente(clienteId, domicilio, altaCliente, sucursal, dni, nombre, apellido, telefono, email);

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
                sucursal.setListaClientes(clientes);
                sucursales.add(sucursal);

            }//fin while sucursales

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
        return sucursales;
    }
    
    @Override
    public void agregarSucursal(Sucursal sucursal) {
        try {                  
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE); 
            con.setAutoCommit(false); //deshabilito los commit automaticos
            
            PreparedStatement ps = con.prepareStatement("insert into sucursales(nombre, bancoId) values(?,?);");            
            ps.setString(1,sucursal.getNombreSucursal() );            
            ps.setInt(2, sucursal.getBancoId());
                                                                    
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
    public void eliminarSucursal(int sucursalEliminarId, int sucursalDestinoId) {
        try {                  
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE); 
            con.setAutoCommit(false); //deshabilito los commit automaticos
            
            PreparedStatement ps = con.prepareStatement("update clientes SET sucursalId =? where sucursalId=?;");            
            ps.setInt(1, sucursalDestinoId);            
            ps.setInt(2, sucursalEliminarId);
            
            PreparedStatement psEmpleados=con.prepareStatement("update empleados SET sucursalId =? where sucursalId=?;");            
            psEmpleados.setInt(1, sucursalDestinoId);            
            psEmpleados.setInt(2, sucursalEliminarId);
            
            //update sucursal destino
            PreparedStatement psEliminarSucursal;                        
            psEliminarSucursal = con.prepareStatement("delete from sucursales where sucursalId=?;");                        
            psEliminarSucursal.setInt(1, sucursalEliminarId);                        
                                             
            ps.executeUpdate(); 
            psEmpleados.executeUpdate();
            psEliminarSucursal.executeUpdate();
              
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
    public boolean comprobarExistenciaSucursal(int nroSucursal) {
        boolean existe=false;
        try {                  
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE); 
            con.setAutoCommit(false); //deshabilito los commit automaticos
            
            PreparedStatement ps = con.prepareStatement("select sucursalId from sucursales WHERE sucursalId=?;");            
            ps.setInt(1, nroSucursal);                                                                    
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                existe=true;
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
        return existe;
    }
            
}
