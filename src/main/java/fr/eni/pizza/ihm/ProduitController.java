package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IProduitManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProduitController {

    @Autowired
    private IProduitManager produitManager;

    @GetMapping("")
public String showAccueil() {

        produitManager.getAllProduits();


    return "accueil";
}

}
