package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.*;
import fr.eni.pizza.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes("")
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

    @GetMapping("/show-creation-commande/{id}")
    public String showCreationCommande(@PathVariable Long id, Model model) {

        List<DetailCommande> detailCommandes = detailCommandeManager.getAllDetailCommandeByIdCommande(id);
        if (detailCommandes == null) {
            return "redirect:/";
        }
        model.addAttribute("detailCommandes", detailCommandes);

        Commande commande = commandeManager.getCommandeById(id);
        model.addAttribute("commande", commande);


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

        Long lastIdCommande = commandeManager.getLastCommande().getId_commande();
        model.addAttribute("lastIdCommande", lastIdCommande);

        return "form/creation-commande-form";
    }

    @PostMapping("/creation-commande")
    public String creationCommande(DetailCommande detailCommande) {
        Commande lastCommande = commandeManager.getLastCommande();
        detailCommande.setId_commande(commandeManager.getCommandeById(6L));

double prixTotal = 0.0;
        for (DetailCommande d : detailCommandeManager.getAllDetailCommandeByIdCommande(6L)) {
          prixTotal += d.getQuantite() * d.getId_produit().getPrix();
    }
Commande commande = commandeManager.getCommandeById(6L);
        commande.setPrix_total(prixTotal);
        commandeManager.updteCommandeById(commande);
        detailCommandeManager.addDetailCommande(detailCommande);



        return "redirect:/show-creation-commande/" + detailCommande.getId_commande().getId_commande();
    }

}
