package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.impl.ClientManager;
import fr.eni.pizza.bo.Client;
import fr.eni.pizza.bo.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@SessionAttributes({"loggedUser"})
@SessionAttributes("commande")
@Controller
public class TestController {

    @Autowired
    private ClientManager clientManager;

    @GetMapping("/etape1")
    public String showEtapeUne(Model model) {

        List<Client> clients = clientManager.getAllClients();
        model.addAttribute("clients", clients);

        return "creation-commande/etape1";
    }



}
