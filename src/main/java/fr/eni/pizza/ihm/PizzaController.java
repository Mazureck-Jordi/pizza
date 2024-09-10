package fr.eni.pizza.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PizzaController {

    @GetMapping("")
    public String showAccueil() {

        return "accueil";
    }
}
