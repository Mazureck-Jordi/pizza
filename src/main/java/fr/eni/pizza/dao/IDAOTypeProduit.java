package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.bo.TypeProduit;

import java.util.List;

public interface IDAOTypeProduit {

    List<TypeProduit> findAllTypeProduits();

    TypeProduit findTypeProduitByID(Long id);
}
