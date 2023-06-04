package sg.ssf.visa.day18_ssfworkshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.ssf.visa.day18_ssfworkshop.model.Order;
import sg.ssf.visa.day18_ssfworkshop.service.PizzaService;

@RestController
public class PizzaRestController {
    @Autowired
    PizzaService pizzaSvc;
    
    @GetMapping(path = "/order/{orderId}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOrder(@PathVariable String orderId) {
        Optional<Order> op = pizzaSvc.getOrderByOrderId(orderId);
        if(op.isEmpty()){
            JsonObject error = Json.createObjectBuilder()
                .add("message", "Order %s not found".formatted(orderId))
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error.toString());
        }
        return ResponseEntity.ok(op.get().toJSON().toString());
    }
}
