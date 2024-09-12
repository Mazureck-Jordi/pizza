package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.ICommandeManager;
import fr.eni.pizza.bll.IDetailCommandeManager;
import fr.eni.pizza.bll.IProduitManager;
import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.bo.DetailCommande;
import fr.eni.pizza.bo.Produit;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/list-detail-commande")
    public String ShowListDetailCommande(Model model) {

        List<DetailCommande> detailCommandes = detailCommandeManager.getAllDetailCommandes();
        model.addAttribute("detailCommandes", detailCommandes);


        return "list/list-detail-commande";
    }

    @GetMapping({"/show-detail-commande-form/{id}", "/show-detail-commande-form"})
    public String ShowDetailCommandeForm(@PathVariable(required = false) Long id, Model model) {

        DetailCommande detailCommande = new DetailCommande();
        if (id != null) {
            detailCommande = detailCommandeManager.getDetailCommandeByIdCommande(id);
        }
        model.addAttribute("detailCommande", detailCommande);

        List<Produit> produits = produitManager.getAllProduits();
        model.addAttribute("produits", produits);

        List<Commande> commandes = commandeManager.getAllCommandes();
        model.addAttribute("commandes", commandes);

        return "form/detail-commande-form";
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
        if (detailCommandeManager.getDetailCommandeByIdCommande(id) == null){
            System.out.println("Erreur");
        }
        if (detailCommandeManager.getDetailCommandeByIdCommande(id) != null){
            detailCommandeManager.deleteDetailCommande(detailCommandeManager.getDetailCommandeByIdCommande(id));
        }
        return "redirect:/list-detail-commande";
    }
}


