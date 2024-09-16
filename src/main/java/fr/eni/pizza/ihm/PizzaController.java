package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.*;
import fr.eni.pizza.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        Commande commande = commandeManager.getCommandeById(id);
        model.addAttribute("commande", commande);


        List<DetailCommande> detailCommandes = new ArrayList<DetailCommande>();
        List<Produit> produits = produitManager.getAllProduits();

        for (Produit produit : produits) {
            DetailCommande d = new DetailCommande();
            d.setId_produit(produit);
            detailCommandes.add(d);
        }

        model.addAttribute("detailCommandes", detailCommandes);

        List<Commande> commandes = commandeManager.getAllCommandes();
        model.addAttribute("commandes", commandes);

        List<Etat> etats = commandeManager.getAllEtats();
        model.addAttribute("etats", etats);

        List<TypeProduit> TypeProduits = produitManager.getAllTypeProduits();
        model.addAttribute("TypeProduits", TypeProduits);

        List<Client> clients = clientManager.getAllClients();
        model.addAttribute("clients", clients);

        List<Utilisateur> utilisateurs = utilisateurManager.getAllUtilisateurs();
        model.addAttribute("utilisateurs", utilisateurs);

        List<Role> roles = utilisateurManager.getAllRoles();
        model.addAttribute("roles", roles);

        Long lastIdCommande = id;
        model.addAttribute("lastIdCommande", lastIdCommande);

        List<DetailCommande> detailCommandeDone = detailCommandeManager.getAllDetailCommandeByIdCommande(id);
        model.addAttribute("detailCommandeUpdate", detailCommandeDone);

        return "form/creation-commande-form";
    }

    @PostMapping("/creation-commande")
    public String creationCommande(DetailCommande detailCommande) {

        Long lastId = detailCommande.getId_commande().getId_commande();
        detailCommande.setId_commande(commandeManager.getCommandeById(lastId));
        List<DetailCommande> detailCommandesActual = detailCommandeManager.getAllDetailCommandeByIdCommande(lastId);
        if (detailCommandesActual.isEmpty()) {
            detailCommandeManager.addDetailCommande(detailCommande);
        } else {
            boolean detailTrouve = false;
            for (DetailCommande d : detailCommandesActual) {
                if (detailCommande.getId_produit().getIdProduit() == d.getId_produit().getIdProduit()) {
                    detailTrouve = true;
                }
            }
            if (detailTrouve) {
                detailCommandeManager.updateDetailCommande(detailCommande);
            } else {
                detailCommandeManager.addDetailCommande(detailCommande);
            }

        }
        double prixTotal = 0.0;
        for (DetailCommande d : detailCommandeManager.getAllDetailCommandeByIdCommande(lastId)) {
            prixTotal += d.getQuantite() * d.getId_produit().getPrix();
        }
        Commande commande = commandeManager.getCommandeById(lastId);
        commande.setPrix_total(prixTotal);
        commandeManager.updteCommandeById(commande);


        return "redirect:/show-creation-commande/" + detailCommande.getId_commande().getId_commande();
    }

    @GetMapping("/delete-produit-details-commande/{idCommande}/{idProduit}")
    public String deleteProduitOfDetailCommande(@PathVariable Long idProduit, @PathVariable Long idCommande) {

        detailCommandeManager.deleteDetailCommande(idCommande, idProduit);

        return "redirect:/show-creation-commande/" + idCommande;
    }

}
