package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IUtilisateurManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UtilisateurController {

    @Autowired
    private IUtilisateurManager utilisateurManager;

}
