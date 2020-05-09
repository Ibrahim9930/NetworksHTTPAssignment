/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.sql.*;
import javax.sql.*;
import org.json.simple.JSONObject;

/**
 *
 * @author Game
 */
public class responseServlet extends HttpServlet {

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
           //grtting the parameters
           
           int id = 2;
           String new_pass = "123";
           int time = 2000;
           
           //Always the same
           Class.forName("com.mysql.jdbc.Driver");
           String jdbcUrl = "jdbc:mysql://localhost:3306/inventory_system?useLegacyDatetimeCode=false&serverTimezone=UTC";
           Connection connection = DriverManager.getConnection(jdbcUrl,"root","");
          
           
           //UPDATE
           /*
           Statement statement = connection.createStatement();
           String sql = "update employees set password = '"+new_pass+"' where id = '"+id+"' ";
           statement.executeUpdate(sql);
           connection.close();
           */
           //READ
           Statement statement = connection.createStatement();
           String sql = "select * from products ";
           //String sql = "select * from products where id = '"+id+"' ";
           ResultSet result = statement.executeQuery(sql);
           int size = 0;
           while(result.next()){
               size ++;
               out.println(result.getInt("id"));
               out.println(result.getString("name"));
               out.println(result.getInt("amount"));
               out.println("");
           }           
           

           //Always the same
           connection.close();
           out.println(size);
           /*
           int v1 = Integer.parseInt(request.getParameter("T1"));
           int v2 = Integer.parseInt(request.getParameter("T2"));
           int sum = v1 + v2;
           String str = Integer.toString(sum);
           out.println(sum);
           
           int visits = 0;
           
           Cookie [] cs = request.getCookies();
           if(cs != null){
               for(int i = 0;i<cs.length;i++){
                   Cookie c = cs[i];
                   if(c.getName().equals("visits")){
                       visits = Integer.parseInt(c.getValue());
                   }
               }
           }
           
           String my_str = Integer.toString(visits+1);
           Cookie cookie = new Cookie("visits",my_str);
           cookie.setMaxAge(60);
           response.addCookie(cookie);
           out.println(my_str);
           */
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
