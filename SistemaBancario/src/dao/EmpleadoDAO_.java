package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import modelo.Empleado;

/**
 * Clase que permite realizar consultas sobre empleados en la base de datos
 * @author Matias Alancay
 * @version 1.0
 */
public class EmpleadoDAO_ implements IEmpleadoDAO{
    private final String URL = "jdbc:mysql://localhost:3306/sistemaBancario_MatiasAlancay";
    private final String USER = "root";
    private final String CLAVE = "admin";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection con;

    private PreparedStatement ps;    
    private ResultSet rs;

    private static EmpleadoDAO_ instance = new EmpleadoDAO_();

    private EmpleadoDAO_() {
    }
    
    public static EmpleadoDAO_ getInstance() {
        return instance;
    }
    
    @Override
    public void agregarEmpleado(Empleado empleado) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            ps = con.prepareStatement("INSERT INTO empleados(nroLegajo,dni,nombre,apellido,telefono,email,sucursalId,fechaIngreso,bancoId)VALUES(?,?,?,?,?,?,?,?,?);");
            ps.setInt(1, empleado.getLegajo());
            ps.setString(2, empleado.getDni());
            ps.setString(3, empleado.getNombre());
            ps.setString(4, empleado.getApellido());
            ps.setString(5, empleado.getTelefono());
            ps.setString(6, empleado.getEmail());
            ps.setInt(7, empleado.getSucursal().getNumeroSucursal());                        
            ps.setDate(8, empleado.getFechaIngreso());
                        
            ps.setInt(9, empleado.getSucursal().getBancoId());

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
}
