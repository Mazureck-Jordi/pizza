package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IProduitManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProduitController {

    @Autowired
    private IProduitManager produitManager;



}
