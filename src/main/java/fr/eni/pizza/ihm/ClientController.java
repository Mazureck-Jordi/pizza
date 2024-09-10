package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IClientManager;
import fr.eni.pizza.bo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private IClientManager clientManager;

    @GetMapping("list-clients")
    public String showListClients(Model model) {


        List<Client> clients = clientManager.getAllClients();
        model.addAttribute("clients", clients);


        return "/list-clients";


    }

    @GetMapping("details-client/{id}")

    public String showDetailClient(@PathVariable Long id, Model model) {

        Client client = clientManager.getClientByID(id);
        if(client == null) {
            return "redirect:/";
        }
        model.addAttribute("client", client);

        return "/details-client";
    }
}
