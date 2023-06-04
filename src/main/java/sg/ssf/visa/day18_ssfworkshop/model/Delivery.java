package sg.ssf.visa.day18_ssfworkshop.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Delivery implements Serializable{

    @Min(value = 3, message = "Minimum of 3 characters")
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Address must not be empty")
    private String address;

    @NotEmpty(message = "Phone number must not be empty")
    @Size(min = 8, max = 8, message = "Invalid phone number")
    @Pattern(regexp = "[8-9]{1}[0-9]{7}", message = "Invalid phone number")
    private String phone;

    private Boolean rush = false;
    private String comments;
    
    public Delivery() {
    }

    public String getName() {return name;}
    public String getAddress() {return address;}
    public String getPhone() {return phone;}
    public Boolean isRush() {return rush;}
    public String getComments() {return comments;}

    public void setName(String name) {
        this.name = name;
    }    

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRush(Boolean rush) {
        this.rush = rush;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString(){
        return "name: " + this.name + " address:" + this.address +
            " Phone: " + this.phone + " rush:" + this.rush + " comments:" + comments;
    }

    public static Delivery create(JsonObject o){
        Delivery d = new Delivery();
        d.setName(o.getString("name"));
        d.setAddress(o.getString("address"));
        d.setPhone(o.getString("phone"));
        d.setRush(o.getBoolean("rush"));
        d.setComments(o.getString("comments"));
        
        return d;
    }
}
