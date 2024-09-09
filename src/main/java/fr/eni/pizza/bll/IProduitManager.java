package fr.eni.pizza.bll;

import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.bo.TypeProduit;

import java.util.List;

public interface IProduitManager {

    List<TypeProduit> getAllTypeProduits();

    TypeProduit getTypeProduitById(Long id_type_produit);

    List<Produit> getAllProduits();

    Produit getProduitById(Long id_produit);

    void addProduit(Produit produit);

    void updateProduit(Produit produit);

    void deleteProduit(Produit produit);
}
