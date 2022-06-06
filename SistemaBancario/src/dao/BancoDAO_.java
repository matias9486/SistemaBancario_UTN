package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Banco;

/**
 * Clase que permite realizar consultas sobre bancos en la base de datos
 * @author Matias Alancay
 * @version 1.0
 */
public class BancoDAO_ implements IBancoDAO{
    private final String URL = "jdbc:mysql://localhost:3306/sistemaBancario_MatiasAlancay";
    private final String USER = "root";
    private final String CLAVE = "admin";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection con;

    private PreparedStatement ps;    
    private ResultSet rs;

    private static BancoDAO_ instance = new BancoDAO_();

    private BancoDAO_() {
    }
    
    public static BancoDAO_ getInstance() {
        return instance;
    }

    @Override
    public Banco buscarBancoPorId(int id) {
        Banco bancoBuscado = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USER, CLAVE);
            con.setAutoCommit(false); //deshabilito los commit automaticos

            ps = con.prepareStatement("select bancoId,nombre from bancos where bancoId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Integer id_ = rs.getInt(1);
                String nombre = rs.getString(2);
                bancoBuscado = new Banco(id_, nombre);
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
        return bancoBuscado;

    }
}
