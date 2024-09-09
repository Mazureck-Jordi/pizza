package fr.eni.pizza.bll.impl;

import fr.eni.pizza.bll.IProduitManager;
import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.bo.TypeProduit;
import fr.eni.pizza.dao.IDAOProduit;
import fr.eni.pizza.dao.IDAOTypeProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitManager implements IProduitManager {

    @Autowired
    IDAOTypeProduit daoTypeProduit;

    @Autowired
    IDAOProduit daoProduit;

    @Override
    public List<TypeProduit> getAllTypeProduits() {
        return daoTypeProduit.findAllTypeProduits();
    }

    @Override
    public TypeProduit getTypeProduitById(Long id_type_produit) {
        return daoTypeProduit.findTypeProduitByID(id_type_produit);
    }

    @Override
    public List<Produit> getAllProduits() {
        return daoProduit.findAllProduits();
    }

    @Override
    public Produit getProduitById(Long id_produit) {
        return daoProduit.findProduitById(id_produit);
    }

    @Override
    public void addProduit(Produit produit) {
        daoProduit.addProduitToDB(produit);
    }

    @Override
    public void updateProduit(Produit produit) {
        daoProduit.updateProduitToDB(produit);
    }

    @Override
    public void deleteProduit(Produit produit) {
        daoProduit.deleteProduitToDB(produit);
    }


}


