package sg.ssf.visa.day18_ssfworkshop.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order implements Serializable{
    private static final long serialVersionUID=1L;

    private float totalCost = -1;
    private String orderId;
    private Pizza pizza;
    private Delivery delivery;

    public Order(Pizza p, Delivery d) {
        this.pizza = p;
        this.delivery = d;
        setOrderId(generatedOrderId());
        this.setTotalCost(calculateCost());
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public String generatedOrderId() {
        String orderId = UUID.randomUUID().toString().substring(0,8);
        return orderId;
    }

    public String getName() { return this.getDelivery().getName();}
    public String getPizzaName() { return this.getPizza().getPizza(); }
    public String getAddress() { return this.getDelivery().getAddress();}
    public String getPhone() { return this.getDelivery().getPhone();}
    public boolean getRush() { return this.getDelivery().isRush();}
    public String getComments() { return this.getDelivery().getComments();}
    public String getSize() { return this.getPizza().getSize();}
    public int getQuantity() { return this.getPizza().getQuantity();}

    public float getPizzaCost() {
        return this.getRush() ? this.getTotalCost() - 2: this.getTotalCost();
    }

    public float calculateCost(){
        float total = 0f;
        switch(this.getPizzaName()){
            case "margherita":
                total+=22;
                break;

            case "trioformaggio":
                total+=25;
                break;

            case "bella", "" , "marinara", "spianatacalabrese":
                total+=30;
                break;
        }

        switch(this.getSize()) {
            case "md":
                total*=1.2;
                break;
            case "lg":
                total*=1.5;
                 break;
            case "sm":
            default:    
        }

        total *=this.getQuantity();
        if(this.getRush())
            total +=2;
        return total;
    }

    public static JsonObject toJSON(String json){
        JsonReader r = Json.createReader(new StringReader(json));
        return r.readObject();
    }

    public static Order create(String jsonStr){
        JsonObject o = toJSON(jsonStr);
        Pizza p = Pizza.create(o);
        Delivery d = Delivery.create(o);
        Order ord= new Order(p, d);
        ord.setOrderId(o.getString("orderId"));
        ord.setTotalCost((float)o.getJsonNumber("total").doubleValue());
        return ord;
    }

    public JsonObject toJSON(){
        return Json.createObjectBuilder()
                .add("orderId", this.orderId)
                .add("name", this.getName())
                .add("address", this.getAddress())
                .add("phone", this.getPhone())
                .add("rush", this.getRush())
                .add("comments", this.getComments())
                .add("pizza", this.getPizzaName())
                .add("size", this.getSize())
                .add("quantity", this.getQuantity())
                .add("total", this.getTotalCost())
                .build();
    }
}
