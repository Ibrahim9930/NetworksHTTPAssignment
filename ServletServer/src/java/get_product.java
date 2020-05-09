/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
/**
 *
 * @author Game
 */
public class get_product extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{

           int id = Integer.parseInt(request.getParameter("productID"));
           

           Class.forName("com.mysql.jdbc.Driver");
           String jdbcUrl = "jdbc:mysql://localhost:3306/inventory_system?useLegacyDatetimeCode=false&serverTimezone=UTC";
           Connection connection = DriverManager.getConnection(jdbcUrl,"root","");
           
           Statement statement = connection.createStatement();
           String sql = " SELECT * from products where id = '"+id+"'";
           ResultSet result = statement.executeQuery(sql);
           int size = 0;
           JSONObject obj = new JSONObject();;
           while(result.next()){
               if(size != 0) out.print(",");
               size ++;
               obj.put("id", result.getInt("id"));
               obj.put("name", result.getString("name"));
               obj.put("pricePerItem", result.getDouble("price"));
               obj.put("amount", result.getInt("amount"));
               
           }  
           if(size == 0){
               out.print("{\"success\":false}");
           }
           else{
               out.print("{\"success\":true,\"product\":");
               out.print(obj);
               out.print("}");
           }

           connection.close();
           
        }
        catch(ClassNotFoundException e){
            out.println("class not found");
        }
        catch(SQLException e){
            out.println("Cannot connect to database");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
