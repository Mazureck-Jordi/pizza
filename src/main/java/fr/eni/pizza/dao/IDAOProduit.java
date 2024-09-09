package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Produit;

import java.util.List;

public interface IDAOProduit {

    List<Produit> findAllProduits();

    Produit findProduitById(Long id_produit);

    void addProduitToDB(Produit produit);

    void updateProduitToDB(Produit produit);

    void deleteProduitToDB(Produit produit);


}
