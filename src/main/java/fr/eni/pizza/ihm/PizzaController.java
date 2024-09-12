package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.*;
import fr.eni.pizza.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PizzaController {

    @Autowired
    private ICommandeManager commandeManager;

    @Autowired
    private IUtilisateurManager utilisateurManager;

    @Autowired
    private IClientManager clientManager;

    @Autowired
    private IProduitManager produitManager;

    @Autowired
    private IDetailCommandeManager detailCommandeManager;



    @GetMapping("")
    public String showAccueil() {

        return "accueil";
    }

    @GetMapping("/show-creation-commande")
    public String showCreationCommande(Model model) {
        DetailCommande detailCommande = new DetailCommande();
        model.addAttribute("detailCommande", detailCommande);

        List<Commande> commandes = commandeManager.getAllCommandes();
        model.addAttribute("commandes", commandes);

        List<Etat> etats = commandeManager.getAllEtats();
        model.addAttribute("etats", etats);

        List<Produit> produits = produitManager.getAllProduits();
        model.addAttribute("produits", produits);

        List<TypeProduit> TypeProduits = produitManager.getAllTypeProduits();
        model.addAttribute("TypeProduits", TypeProduits);

        List<Client> clients = clientManager.getAllClients();
        model.addAttribute("clients", clients);

        List<Utilisateur> utilisateurs = utilisateurManager.getAllUtilisateurs();
        model.addAttribute("utilisateurs", utilisateurs);

        List<Role> roles = utilisateurManager.getAllRoles();
        model.addAttribute("roles", roles);

        return "form/creation-commande-form";
    }

@PostMapping ("/creation-commande")
    public String creationCommande (@ModelAttribute DetailCommande detailCommande) {

        detailCommandeManager.addDetailCommande(detailCommande);
       return  "redirect:/" ;
}

}
