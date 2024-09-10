package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.dao.IDAOProduit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("mysql")
@Repository
public class DAOProduitMySQL implements IDAOProduit {
    @Override
    public List<Produit> findAllProduits() {
        return List.of();
    }

    @Override
    public Produit findProduitById(Long id_produit) {
        return null;
    }

    @Override
    public void addProduitToDB(Produit produit) {

    }

    @Override
    public void updateProduitToDB(Produit produit) {

    }

    @Override
    public void deleteProduitToDB(Produit produit) {

    }
}
