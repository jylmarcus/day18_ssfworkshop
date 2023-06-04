package sg.ssf.visa.day18_ssfworkshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.ssf.visa.day18_ssfworkshop.model.Delivery;
import sg.ssf.visa.day18_ssfworkshop.model.Order;
import sg.ssf.visa.day18_ssfworkshop.model.Pizza;
import sg.ssf.visa.day18_ssfworkshop.service.PizzaService;

@Controller
public class PizzaController {

    @Autowired
    PizzaService pizzaSvc;

    @GetMapping(path={"/", "/index.html"})
    public String getIndex(Model model, HttpSession sess) {
        sess.invalidate();
        model.addAttribute("pizza", new Pizza());
        return "index";
    }

    @PostMapping(path = "/pizza", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postOrder(Model model, @Valid Pizza pizza, HttpSession sess, BindingResult binding) {
        if(binding.hasErrors()) {
            return "index";
        }

        List<ObjectError> errors = pizzaSvc.validatePizzaOrder(pizza);
        if(!errors.isEmpty()){
            for(ObjectError e: errors)
                binding.addError(e);
            return "index";
        } 

        sess.setAttribute("pizza", pizza);
        model.addAttribute("delivery", new Delivery());
        return "delivery";
    }
    
    @PostMapping(path = "/pizza/order", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postPizzaOrder(Model model, @Valid Delivery delivery, HttpSession sess, BindingResult bindings) {
        if(bindings.hasErrors()) {
            return "delivery";
        }

        Pizza p = (Pizza)sess.getAttribute("pizza");
        Order o = new Order(p, delivery);
        pizzaSvc.saveOrder(o);
        model.addAttribute("order", o);
        return "order";
    }
}
