package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.ICommandeManager;
import fr.eni.pizza.bll.IDetailCommandeManager;
import fr.eni.pizza.bll.IProduitManager;
import fr.eni.pizza.bll.impl.CommandeManager;
import fr.eni.pizza.bll.impl.UtilisateurManager;
import fr.eni.pizza.bo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DetailCommandeController {

    @Autowired
    private IDetailCommandeManager detailCommandeManager;

    @Autowired
    private IProduitManager produitManager;

    @Autowired
    private ICommandeManager commandeManager;
    @Autowired
    private UtilisateurManager utilisateurManager;

    @GetMapping("/list-detail-commande")
    public String ShowListDetailCommande(Model model) {

        List<DetailCommande> detailCommandes = detailCommandeManager.getAllDetailCommandes();
        model.addAttribute("detailCommandes", detailCommandes);


        return "list/list-detail-commande";
    }

    @GetMapping("/details-detail-commande/{id}")
    public String ShowDetailCommande(@PathVariable Long id, Model model) {

        List<DetailCommande> detailCommandes = detailCommandeManager.getAllDetailCommandeByIdCommande(id);
        if (detailCommandes == null) {
            return "redirect:/";
        }
        model.addAttribute("detailCommandes", detailCommandes);

        Commande commande = commandeManager.getCommandeById(id);
        model.addAttribute("commande", commande);

        double prixTotal = 0.0;
        for (DetailCommande d : detailCommandeManager.getAllDetailCommandeByIdCommande(id)) {
            prixTotal += d.getQuantite() * d.getId_produit().getPrix();
        }
        commande.setPrix_total(prixTotal);
        commandeManager.updteCommandeById(commande);


        return "details/details-detail-commande";
    }

    @GetMapping("/detail-commande-pizzaiolo/{id}")
    public String ShowDetailCommandePizzaiolo(@AuthenticationPrincipal UserDetails loggedUser, @PathVariable Long id, Model model) {

        List<DetailCommande> detailCommandes = detailCommandeManager.getAllDetailCommandeByIdCommande(id);
        if (detailCommandes == null) {
            return "redirect:/";
        }
        model.addAttribute("detailCommandes", detailCommandes);

        Commande commande = commandeManager.getCommandeById(id);
        model.addAttribute("commande", commande);

        double prixTotal = 0.0;
        for (DetailCommande d : detailCommandeManager.getAllDetailCommandeByIdCommande(id)) {
            prixTotal += d.getQuantite() * d.getId_produit().getPrix();
        }
        commande.setPrix_total(prixTotal);
        commandeManager.updteCommandeById(commande);

        Utilisateur user = utilisateurManager.getByEmail(loggedUser.getUsername());
        Utilisateur userWithRole = utilisateurManager.getUtilisateurById(user.getId_utilisateur());
        boolean isPizzaiolo = false;
        boolean isLivreur = false;
        for (Role role : userWithRole.getRoles()) {
            if (role.getId_role() == 1){
                isPizzaiolo = true;
            }
            if (role.getId_role() == 3){
                isLivreur = true;
            }
        }
        if(isPizzaiolo) {
            return "details/details-commande-pizzaiolo";
        }
        if(isLivreur) {
            return "details/details-commande-livreur";
        }
        return "redirect:/";
    }

    @GetMapping({"/show-detail-commande-form/{id}", "/show-detail-commande-form"})
    public String ShowDetailCommandeForm(@PathVariable(required = false) Long id, Model model) {

        DetailCommande detailCommande = new DetailCommande();
        if (id != null) {
            List<DetailCommande> detailCommandes = detailCommandeManager.getAllDetailCommandeByIdCommande(id);
        }
        model.addAttribute("detailCommande", detailCommande);

        List<Produit> produits = produitManager.getAllProduits();
        model.addAttribute("produits", produits);

        List<Commande> commandes = commandeManager.getAllCommandes();
        model.addAttribute("commandes", commandes);

        return "form/details-commande-form";
    }
    @PostMapping("detail-commande-form")
    public String detailCommandeForm(@ModelAttribute DetailCommande detailCommande) {
        if (detailCommande.getId_commande() == null) {
            detailCommandeManager.addDetailCommande(detailCommande);
        }
        if (detailCommande.getId_produit() != null) {
            detailCommandeManager.updateDetailCommande(detailCommande);
        }
        return "redirect:/list-detail-commande";
    }

    @GetMapping("/delete-detail-commande/{id}")
    public String deleteDetailCommande(@PathVariable Long id) {
//        if (detailCommandeManager.getDetailCommandeByIdCommande(id) == null){
//            System.out.println("Erreur");
//        }
//        if (detailCommandeManager.getDetailCommandeByIdCommande(id) != null){
//            detailCommandeManager.deleteDetailCommande(detailCommandeManager.getDetailCommandeByIdCommande(id));
//        }
        return "redirect:/list-detail-commande";
    }

    @GetMapping("/change-etat/{idCommande}")
    public String changeEtatCommande (@PathVariable Long idCommande){
        Commande commandeToChange = commandeManager.getCommandeById(idCommande);
        commandeToChange.getId_etat().setId_etat(2L);
        commandeManager.updateCommande(commandeToChange);
        return "redirect:/list-commande-by-etat";
    }

}


