package sg.ssf.visa.day18_ssfworkshop.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import sg.ssf.visa.day18_ssfworkshop.model.Order;
import sg.ssf.visa.day18_ssfworkshop.model.Pizza;
import sg.ssf.visa.day18_ssfworkshop.repository.PizzaRepository;

@Service
public class PizzaService {
    @Autowired
    PizzaRepository pizzaRepo;

    @Value("${revision.pizza.api.url}")
    private String restPizzaUrl;

    public static final String[] PIZZA_NAMES = {"bella", "margherita", "marinara", "spinatacalabrese", "trioformaggio"}; 

    public static final String[] PIZZA_SIZES = {"sm", "md", "lg"};
    
    private Set<String> pizzaNames;
    private Set<String> pizzaSizes;

    public PizzaService() {
        pizzaNames = new HashSet<String>(Arrays.asList(PIZZA_NAMES));
        pizzaSizes = new HashSet<String>(Arrays.asList(PIZZA_SIZES));
    }

    public void saveOrder(Order o) {
        pizzaRepo.save(o);
    }

    public List<ObjectError> validatePizzaOrder(Pizza pizza) {
        List<ObjectError> errors = new LinkedList<>();
        FieldError error;

        if(!pizzaNames.contains(pizza.getPizza().toLowerCase())) {
            error = new FieldError("pizza", "pizza", "We do not have the %s pizza.".formatted(pizza.getPizza()));
            errors.add(error);
        }

        if(!pizzaSizes.contains(pizza.getSize().toLowerCase())) {
            error = new FieldError("pizza", "size", "We do not have the %s pizza size.".formatted(pizza.getSize()));
            errors.add(error);
        }

        return errors;
    }

    public Optional<Order> getOrderByOrderId(String orderId) {
        return pizzaRepo.get(orderId);
    }
    
}
