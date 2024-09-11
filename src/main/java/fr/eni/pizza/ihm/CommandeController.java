package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IClientManager;
import fr.eni.pizza.bll.ICommandeManager;
import fr.eni.pizza.bll.IProduitManager;
import fr.eni.pizza.bll.IUtilisateurManager;
import fr.eni.pizza.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommandeController {

    @Autowired
    private ICommandeManager commandeManager;

    @Autowired
    private IUtilisateurManager utilisateurManager;

    @Autowired
    private IClientManager clientManager;

    @Autowired
    private IProduitManager produitManager;

    @GetMapping("/list-commandes")
    public String ShowListCommandes(Model model) {
        List<Commande> commandes = commandeManager.getAllCommandes();

        model.addAttribute("commandes", commandes);



        return "list-commandes";
    }

    @GetMapping("/details-commande/{id}")
    public String ShowDetailCommande(@PathVariable Long id, Model model) {
        Commande commande = commandeManager.getCommandeById(id);
        if (commande == null) {
            return "redirect:/";
        }
        model.addAttribute("commande", commande);



        return "details-commande";
    }


    @GetMapping({"/show-commande-form/{id}", "/show-commande-form"})
    public String ShowFormCommande(@PathVariable(required = false) Long id, Model model) {

        Commande commande = new Commande();
        if (id != null) {
            commande = commandeManager.getCommandeById(id);
        }

        model.addAttribute("commande", commande);

        List<Etat> etats = commandeManager.getAllEtats();

        model.addAttribute("etats", etats);

        List<Utilisateur> utilisateurs = utilisateurManager.getAllUtilisateurs();

        model.addAttribute("utilisateurs", utilisateurs);

        List<Role> roles = utilisateurManager.getAllRoles();

        model.addAttribute("roles", roles);

        List<Client> clients = clientManager.getAllClients();

        model.addAttribute("clients", clients);

        List<Produit> Produits = produitManager.getAllProduits();

        model.addAttribute("Produits", Produits);

        List<TypeProduit> TypeProduits = produitManager.getAllTypeProduits();

        return "commande-form";
    }

    @PostMapping("/commande-form")
    public String commandeForm (@ModelAttribute Commande commande) {
        if (commande.getId_commande() == null) {
            commandeManager.addCommande(commande);
        }
        if (commande.getId_commande() != null) {
            commandeManager.updateCommande(commande);
        }
        return "redirect:/list-commandes";
    }

    @GetMapping("/delete-commande/{id}")
    public String deleteCommande (@PathVariable Long id) {
        if (commandeManager.getCommandeById(id) == null) {
            System.out.println("Erreur");
        }
        if (commandeManager.getCommandeById(id) != null) {
            commandeManager.deleteCommande(commandeManager.getCommandeById(id));
        }
    return "redirect:/list-commandes";
    }


}
