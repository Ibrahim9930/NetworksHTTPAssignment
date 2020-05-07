/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import httpassignmentclient.HTTPAssignmentClient;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author masri
 */
public class Product {

    private String id;
    private String name;
    private double pricePerItem;
    private double pricePerItemEUR;

    public double getPricePerItemEUR() {
        return pricePerItemEUR;
    }

    public void setPricePerItemEUR(int pricePerItemEUR) {
        this.pricePerItemEUR = pricePerItemEUR;
    }
    private int amount;

    public Product(String id, String name, int price, int amount) {
        this.id = id;
        this.name = name;
        this.pricePerItem = price;
        this.amount = amount;
    }

    public Product() {
    }

    public Product(JSONObject obj) {
        this.id = (String) obj.get("id");
        this.name = (String) obj.get("name");
        this.amount = (int) (long) obj.get("amount");
        this.pricePerItem = (double) obj.get("pricePerItem");
        getEURPrice();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(int pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "{" + "\"id\":" + id + ", \"name\":" + name + ", \"pricePerItem\":" + pricePerItem + ", \"amount\":" + amount + '}';
    }

    private void getEURPrice() {
        String response = "";
        JSONParser parser = new JSONParser();
        int responseByte;
        String connectionAddress = "https://api.exchangeratesapi.io/latest?symbols=EUR&base=USD";
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
            JSONObject obj = new JSONObject();
            try {
                obj = (JSONObject) parser.parse(response);
            } catch (ParseException ex) {
                Logger.getLogger(HTTPAssignmentClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            JSONObject rates = (JSONObject) obj.get("rates");
            this.pricePerItemEUR = (this.pricePerItem * (double) rates.get("EUR"));
        } 
        catch (Exception e) {
            System.err.println("Exception occured while converting currencies");
            System.err.println(e);
            this.pricePerItemEUR = 0;
        }
    }
}
