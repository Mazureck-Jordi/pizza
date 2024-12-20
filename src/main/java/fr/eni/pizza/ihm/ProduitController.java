package fr.eni.pizza.ihm;

import fr.eni.pizza.bll.IProduitManager;
import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.bo.TypeProduit;
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
public class ProduitController {

    @Autowired
    private IProduitManager produitManager;


    @GetMapping("/list-produits")
    public String showListProduits(Model model) {

        //On récupère la liste des produit et des types produits
        List<Produit> produits = produitManager.getAllProduits();

        //On injecte la liste des produits et des types produits dans le model
        model.addAttribute("produits", produits);

        return "list/list-produits";
    }

    @GetMapping("/details-produit/{id}")
    public String showDetailProduit(@PathVariable Long id, Model model) {

        //On récupère un produit en fonction de son id
        Produit produit = produitManager.getProduitById(id);

        //Si le produit n'existe pas on redirige vers la page d'accueil
        if (produit == null) {
            return "redirect:/";
        }

        //Sinon On injecte le produit dans le model
        model.addAttribute("produit", produit);

        return "details/details-produit";
    }


    @GetMapping({"/show-produit-form/{id}", "/show-produit-form"})
    public String showFormProduit(@PathVariable(required = false) Long id, Model model) {

        //Instancier un produit par default
        Produit produit = new Produit();

        //S'il y a deja un produit existant avec cet id, on recupère le produit par son id
        //PS : On écrase le produit vide qu'on voulait afficher dans le form
        //Donc on affichera un produit existant dans le formulaire
        if (id != null) {
            produit = produitManager.getProduitById(id);
        }

        //On injecte le produit dans le modèle
        model.addAttribute("produit", produit);

        //On récupère la liste de type de produit
        List<TypeProduit> typeProduits = produitManager.getAllTypeProduits();

        //On injecte la liste de type de produit
        model.addAttribute("typeProduits", typeProduits);

        return "form/produit-form";
    }

    @PostMapping("/produit-form")
    public String produitForm(@Valid @ModelAttribute Produit produit, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //On récupère la liste de type de produit
        List<TypeProduit> typeProduits = produitManager.getAllTypeProduits();

        //On injecte la liste de type de produit
        model.addAttribute("typeProduits", typeProduits);

        if (bindingResult.hasErrors()) {
            return "form/produit-form";
        }

        //Si le produit n'existe pas on l'ajoute
        if (produit.getIdProduit() == null) {
            produitManager.addProduit(produit);
            redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "Le produit a été ajouté à la liste des produits"));
            return "redirect:/list-produits";
        }

        //Si le produit existe on le modifie
        if (produit.getIdProduit() != null) {
            produitManager.updateProduit(produit);
            redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_SUCCES, "Le produit a été modifié avec succès"));
        }


        return "redirect:/list-produits";
    }

    @GetMapping("/delete-produit/{id}")
    public String deleteProduit(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        if (produitManager.getProduitById(id) == null) {
            System.out.println("erreur");
        }
        if (produitManager.getProduitById(id) != null) {
            //Supprime le produit
            produitManager.deleteProduit(produitManager.getProduitById(id));
        }

        redirectAttributes.addFlashAttribute("flashMessage", new PizzaFlashMessage(PizzaFlashMessage.TYPE_FLASH_WARNING, "Le produit a été supprimé avec succès"));

        return "redirect:/list-produits";
    }

}