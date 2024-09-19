package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IClientManager;
import fr.eni.pizza.bll.impl.ProduitManager;
import fr.eni.pizza.bo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@SessionAttributes({"loggedUser"})
@Controller
public class ClientController {

    @Autowired
    private IClientManager clientManager;
    @Autowired
    private ProduitManager produitManager;

    @GetMapping("/list-clients")
    public String showListClients(Model model) {


        List<Client> clients = clientManager.getAllClients();
        model.addAttribute("clients", clients);


        return "list/list-clients";


    }

    @GetMapping("/details-client/{id}")

    public String showDetailClient(@PathVariable Long id, Model model) {

        Client client = clientManager.getClientByID(id);
        if(client == null) {
            return "redirect:/";
        }
        model.addAttribute("client", client);

        return "details/details-client";
    }
    @GetMapping({"/show-client-form/{id}", "/show-client-form"})
public String showClientForm(@PathVariable(required = false) Long id, Model model) {

        Client client = new Client();
        if (id != null) {
            client = clientManager.getClientByID(id);
        }
        model.addAttribute("client", client);

        return "form/client-form";
    }

    @PostMapping ("/client-form")
public String clientForm(Client client) {

        if (client.getId_client() == null) {
            clientManager.addClient(client);
        }
        if (client.getId_client() != null) {
            clientManager.updateClient(client);
        }
        return "redirect:/show-commande-form";
    }
    @GetMapping("/delete-client/{id}")
    public String deleteClient(@PathVariable Long id) {

        if (clientManager.getClientByID(id) == null) {

        }
        if (clientManager.getClientByID(id) != null) {
            clientManager.deleteClient(clientManager.getClientByID(id));
        }
        return "redirect:/list-clients";
    }
}