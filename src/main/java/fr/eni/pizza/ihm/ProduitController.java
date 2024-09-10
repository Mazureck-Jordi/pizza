package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IProduitManager;
import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.bo.TypeProduit;
import fr.eni.pizza.dao.IDAOTypeProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class ProduitController {

    @Autowired
    private IProduitManager produitManager;


    @GetMapping("list-produits")
    public String showListProduits(Model model) {

        //On récupère la liste des produit et des types produits
        List<Produit> produits = produitManager.getAllProduits();
        List<TypeProduit> typeProduits = produitManager.getAllTypeProduits();

        //On injecte la liste des produits et des types produits dans le model
        model.addAttribute("produits", produits);
        model.addAttribute("typeProduits", typeProduits);

        return "/list-produits";
    }

    @GetMapping("details-produit/{id}")
    public String showDetailProduit(@PathVariable Long id, Model model) {

        //On récupère un produit en fonction de son id
        Produit produit = produitManager.getProduitById(id);

        //Si le produit n'existe pas on redirige vers la page d'accueil
        if (produit == null) {
            return "redirect:/";
        }

        //Sinon On injecte le produit dans le model
        model.addAttribute("produit", produit);

        //On récupère le type de produit du produit
       TypeProduit typeProduit = produitManager.getTypeProduitById(produit.getId_produit());

       //On injecte le type de produit du produit dans le model
       model.addAttribute("typeProduit", typeProduit);

       return "/details-produit";
    }




    @GetMapping({"produit-form/{id}", "produit-form"})
    public String showFormProduit(@PathVariable(required = false) Long id, Model model) {

        //Instancier un produit par default
        Produit produit = new Produit();


        //S'il y a deja un produit existant avec cet id, on recupère le produit par son id
        //PS : On écrase le produit vide qu'on voulait afficher dans le form
        //Donc on affichera un produit existant dans le formulaire
        if (produit.getId_produit() != null) {
            produit = produitManager.getProduitById(produit.getId_produit());
        }

        //On injecte le produit dans le modèle
        model.addAttribute("produit", produit);

        //On récupère la liste de type de produit
        List<TypeProduit> typeProduits = produitManager.getAllTypeProduits();

        //On injecte la liste de type de produit
        model.addAttribute("typeProduits", typeProduits);

        return "/produit-form";
    }



}
