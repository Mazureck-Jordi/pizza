package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IClientManager;
import fr.eni.pizza.bll.impl.ProduitManager;
import fr.eni.pizza.bo.Client;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (client == null) {
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

    @PostMapping("/client-form")
    public String clientForm(@Valid @ModelAttribute Client client, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "form/client-form";
        }

        if (client.getId_client() == null) {
            clientManager.addClient(client);
            redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "Le client a été ajouté à la liste des clients"));
            return "redirect:/list-clients";

        }
        if (client.getId_client() != null) {
            clientManager.updateClient(client);
            redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "Le client a été modifié avec succès"));

        }
        return "redirect:/list-clients";
    }

    @PostMapping("/client-form/{idCommande}")
    public String clientFormByidCommande(@Valid @ModelAttribute Client client, @PathVariable Long idCommande, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            return "form/client-form";
//        }
        clientManager.updateClient(client);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "Le client a été modifié avec succès"));
        return "redirect:/show-creation-commande/" + idCommande;
    }

    @GetMapping("/delete-client/{id}")
    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        if (clientManager.getClientByID(id) == null) {

        }
        if (clientManager.getClientByID(id) != null) {
            clientManager.deleteClient(clientManager.getClientByID(id));
        }
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_WARNING, "Le client a été supprimé avec succès"));


        return "redirect:/list-clients";
    }
}