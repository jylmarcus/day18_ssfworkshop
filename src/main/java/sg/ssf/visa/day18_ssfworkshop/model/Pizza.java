package sg.ssf.visa.day18_ssfworkshop.model;

import java.io.Serializable;

import jakarta.json.JsonObject;

public class Pizza implements Serializable{
    private String pizza;
    private String size;
    private Integer quantity;
    
    public Pizza() {
    }

    public String getPizza() {
        return pizza;
    }

    public void setPizza(String pizza) {
        this.pizza = pizza;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return "pizza=" + this.getPizza() + " size=" 
                + this.size + " quantity:" + this.getQuantity();
    }

    public static Pizza create(JsonObject o){
        Pizza p = new Pizza();
        p.setPizza(o.getString("pizza"));
        p.setSize(o.getString("size"));
        p.setQuantity(o.getInt("quantity"));
        return p;
    }

}
