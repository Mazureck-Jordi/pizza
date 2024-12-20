package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.*;
import fr.eni.pizza.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"loggedUser"})
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
    public String showAccueil(@AuthenticationPrincipal UserDetails loggedUser, Model model) {
model.addAttribute("loggedUser", loggedUser);
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

        Client client = clientManager.getClientByID(commande.getId_client().getId_client());
        model.addAttribute("client", client);

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
    public String creationCommande(DetailCommande detailCommande, RedirectAttributes redirectAttributes) {

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

        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "Le(s) produit(s) a(ont) été(s) ajouté(s) avec succès"));

        return "redirect:/show-creation-commande/" + detailCommande.getId_commande().getId_commande();
    }

    @GetMapping("/delete-produit-details-commande/{idCommande}/{idProduit}")
    public String deleteProduitOfDetailCommande(@PathVariable Long idProduit, @PathVariable Long idCommande, RedirectAttributes redirectAttributes) {

        detailCommandeManager.deleteDetailCommande(idCommande, idProduit);
        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_WARNING, "Le detail de la commande a été supprimé avec succès"));

        return "redirect:/show-creation-commande/" + idCommande;
    }

}
