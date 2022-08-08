/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;
import CLASS.Producto;
import MODELO.conexionsql;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author USUARIO
 */
public class Inventario extends conexionsql {
    
    java.sql.Statement st;
    ResultSet rs;
    
    
    
    public boolean insertar(String id_prod,String nombre,String cantidad,String precio,String estado,String descripcion) {
        try {
            Connection conexion = conectar();
            st = conexion.createStatement();
            String sql = "insert into \"Inventario\".\"Productos\"(id_prod, nombre, cantidad, precio, estado, descripcion) values (" + 
                                id_prod + ",'" + nombre + "'," + cantidad + ","+precio+",'"+estado+"','"+descripcion+"');";
            st.execute(sql);
            st.close();
            conexion.close();
            JOptionPane.showMessageDialog(null, "El registro se guardo correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El registro no se guardo " + e, "Mensaje", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    public ArrayList<Producto> mostrar(String id_prod) {
        ArrayList<Producto> arrProd = new ArrayList<>();
        try {
            Connection conexion = conectar();
            st = conexion.createStatement();
            String sql = "select * from \"Inventario\".\"Productos\" where id_prod=" + id_prod + ";";
            rs = st.executeQuery(sql);
            
            
            if (rs.next()) {
                Producto prod =  new Producto();
                prod.setId_producto(rs.getString("id_prod"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setCantidad(rs.getString("cantidad"));
                prod.setPrecio(rs.getString("precio"));
                prod.setEstado(rs.getString("estado"));
                //JOptionPane.showMessageDialog(null, prod.getId_producto()+"Nombre: " +prod.getNombre() ,"con registro" , JOptionPane.INFORMATION_MESSAGE);
                arrProd.add(prod);
                
            } else {
                
                JOptionPane.showMessageDialog(null, "No se encontro registro", "Sin registro", JOptionPane.INFORMATION_MESSAGE);
            }
            st.close();
            conexion.close();
            return arrProd;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encontro registro", "Sin registro", JOptionPane.INFORMATION_MESSAGE);
        }
        return arrProd;
    }
    
    public boolean eliminar(String id){
        try {
            Connection conexion = conectar();
            st = conexion.createStatement();
            String sql = "delete from \"Inventario\".\"Productos\" where id_prod = ?";//" + id + " ;";
            //rs = st.executeQuery(sql);
            int affectedRows;
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, Integer.valueOf(id));
            affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Se eliminó el registro con id: " +id ,"Eliminacion exitosa" , JOptionPane.INFORMATION_MESSAGE);
                return true;               
            }
            st.close();
            conexion.close();
        }catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "No se encontro registro", "Sin registro", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }
    
    public ArrayList<Producto> mostrarTodos() {
        ArrayList<Producto> arrProd = new ArrayList<>();
        try {
            Connection conexion = conectar();
            st = conexion.createStatement();
            String sql = "select * from \"Inventario\".\"Productos\" order by 1 desc;";
            rs = st.executeQuery(sql);
            //if (rs.next()) {
                while(rs.next()){
                Producto prod =  new Producto();
                prod.setId_producto(rs.getString("id_prod"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setCantidad(rs.getString("cantidad"));
                prod.setPrecio(rs.getString("precio"));
                prod.setEstado(rs.getString("estado"));
                arrProd.add(prod);
                }
            if(arrProd.isEmpty()){
               JOptionPane.showMessageDialog(null, "No se encontro registro", "Sin registro", JOptionPane.INFORMATION_MESSAGE);
            }
            st.close();
            conexion.close();
            return arrProd;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encontro registro", "Sin registro", JOptionPane.INFORMATION_MESSAGE);
        }
        return arrProd;
    }
}
