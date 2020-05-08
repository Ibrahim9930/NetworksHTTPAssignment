/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpassignmentclient;

import java.awt.List;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import modules.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author masri
 */
public class HTTPAssignmentClient {
    public static String PHPServerAddress = "http://localhost/NetProject";
    public static String servletServerAddress = "http://localhost:15000/HTTPAssignmentServlet";
    private String method = "POST";
    private String serverURL = "http://localhost/NetProject";

    public void setMethod(String method) {
        this.method = method;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public static void main(String[] args) {
        HTTPAssignmentClient client = new HTTPAssignmentClient();
        LoginFrame lf = new LoginFrame(client);
        lf.setVisible(true);
    }
    
    String sendDataGet(String resource, String[] keys, String[] values) {
        String response = "";
        int responseByte;
        String connectionAddress = serverURL + "/" + resource + "?";
        
        for (int i = 0; i < keys.length; i++) {

            connectionAddress += keys[i] + "=" + values[i];
            if (i != 0 || i != (keys.length - 1)) {
                connectionAddress += "&";
            }
        }
        try {
            URL url = new URL(connectionAddress);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            while ((responseByte = is.read()) != -1) {
                if ((char) responseByte == '\r') {
                    response += "\n";
                } else {
                    response = response + (char) responseByte;
                }
            }
            return response;
        } catch (Exception e) {
            System.err.println("Exception occured while establishing a connection to " + resource);
            System.err.println(e);
            return null;
        }
    }

    String sendDataPost(String resource, String[] keys, String[] values) {
        String response = "";
        int responseByte;
        String requestBody = "";

        String connectionAddress = serverURL + "/" + resource;
        for (int i = 0; i < keys.length; i++) {
            try {
                requestBody += keys[i] + "=" + URLEncoder.encode(values[i], "ASCII");
            } catch (UnsupportedEncodingException ex) {
                System.err.println("Exception occured while encoding data : " + values[i]);
                System.err.println(ex);
            }
            if (i != 0 || i != (keys.length - 1)) {
                requestBody += "&";
            }
        }
        try {
            URL url = new URL(connectionAddress);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            BufferedOutputStream bos = new BufferedOutputStream(con.getOutputStream());
            bos.write(requestBody.getBytes());
            bos.close();

            InputStream is = con.getInputStream();
            while ((responseByte = is.read()) != -1) {
                if ((char) responseByte == '\r') {
                    response += "\n";
                } else {
                    response = response + (char) responseByte;
                }
            }
            return response;
        } catch (Exception e) {
            System.err.println("Exception occured while establishing a connection to " + resource);
            System.err.println(e);
            return null;
        }
    }
    
    public JSONObject authenticate(String employeeID,String password){
        String serverResponse;
        JSONParser parser = new JSONParser();
        if(method.equals("get"))
            serverResponse = sendDataGet("index.php",new String[]{"id","password"},new String[]{employeeID,password});
        else
            serverResponse = sendDataPost("index.php",new String[]{"id","password"},new String[]{employeeID,password});

        JSONObject response = new JSONObject();
        try {
            
            response = (JSONObject) parser.parse(serverResponse);
        } catch (ParseException ex) {
            Logger.getLogger(HTTPAssignmentClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //{"success":true,"lastLoginDate":56462132151}
        return response;
    }
    
    public boolean changePassword(String employeeID,String newPassword){
        String serverResponse;
        JSONParser parser = new JSONParser();
        if(method.equals("get"))
            serverResponse = sendDataGet("change_password.php",new String[]{"id","newPassword"},new String[]{employeeID,newPassword});
        else
            serverResponse = sendDataPost("change_password.php",new String[]{"id","newPassword"},new String[]{employeeID,newPassword});
        JSONObject response = new JSONObject();
        try {
            response = (JSONObject) parser.parse(serverResponse);
        } catch (ParseException ex) {
            Logger.getLogger(HTTPAssignmentClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //{"success":false}
        return (boolean)response.get("success");
    }
    
    public Product lookUpProduct(String productID){
        Product product = null;
        String serverResponse;
        JSONParser parser = new JSONParser();
        if(method.equals("get"))
            serverResponse = sendDataGet("get_product.php",new String[]{"productID"},new String[]{productID});
        else
            serverResponse = sendDataPost("get_product.php",new String[]{"productID"},new String[]{productID});
        JSONObject response = new JSONObject();
        try {
            System.out.println(serverResponse);
            response = (JSONObject) parser.parse(serverResponse);
        } catch (ParseException ex) {
            Logger.getLogger(HTTPAssignmentClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if((boolean)response.get("success")){
            JSONObject obj = new JSONObject();
            try {
                obj = (JSONObject) parser.parse(response.get("product").toString());
            } catch (ParseException ex) {
                Logger.getLogger(HTTPAssignmentClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            product = new Product(obj);
        }
        //{"success":true,"product":{"id":"512","name":"252","pricePerItem":3.0, "amount":"200"}}
        return product;
    }

    public boolean insertProduct(String productID,String amount){
        String serverResponse;
        JSONParser parser = new JSONParser();
        if(method.equals("get"))
            serverResponse = sendDataGet("add_product.php",new String[]{"productID","amount"},new String[]{productID,amount});
        else
            serverResponse = sendDataPost("add_product.php",new String[]{"productID","amount"},new String[]{productID,amount});
        JSONObject response = new JSONObject();
        try {
            System.out.println(serverResponse);
            response = (JSONObject) parser.parse(serverResponse);
        } catch (ParseException ex) {
            Logger.getLogger(HTTPAssignmentClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       //{"success":true}
        return (boolean)response.get("success");
    }

    public boolean withdrawProduct(String productID,String amount){
        String serverResponse;
        JSONParser parser = new JSONParser();
        if(method.equals("get"))
            serverResponse = sendDataGet("withdraw_product.php",new String[]{"productID","amount"},new String[]{productID,amount});
        else
            serverResponse = sendDataPost("withdraw_product.php",new String[]{"productID","amount"},new String[]{productID,amount});
        JSONObject response = new JSONObject();
        try {
            response = (JSONObject) parser.parse(serverResponse);
        } catch (ParseException ex) {
            Logger.getLogger(HTTPAssignmentClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       //{"success":false}
        return (boolean)response.get("success");
    }
    
    public ArrayList<Product> showAllProducts(){
        ArrayList<Product> products = new ArrayList<Product>();
        String serverResponse;
        JSONParser parser = new JSONParser();
        if(method.equals("get"))
            serverResponse = sendDataGet("get_all_products.php",new String[]{},new String[]{});
        else
            serverResponse = sendDataPost("get_all_products.php",new String[]{},new String[]{});
        Object response = new Object();
        try {
            System.out.println(serverResponse);
            response =  parser.parse(serverResponse);
        } catch (ParseException ex) {
            Logger.getLogger(HTTPAssignmentClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONArray array = (JSONArray)response;
        for (int i = 0; i < array.size(); i++) {
            products.add( new Product((JSONObject) array.get(i)));
        }
        //[{"id":"512","name":"252","pricePerItem":3.0, "amount":"200"},{"id":"512","name":"252","pricePerItem":3.0, "amount":"200"}]
        return products;
    }
}
