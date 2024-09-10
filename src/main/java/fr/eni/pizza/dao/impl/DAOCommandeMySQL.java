package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.dao.IDAOCommande;

import java.util.List;

public class DAOCommandeMySQL implements IDAOCommande {


    @Override
    public List<Commande> findAllCommandes() {
        return List.of();
    }

    @Override
    public Commande findCommandeById(Long id) {
        return null;
    }

    @Override
    public void addCommandeToDB(Commande commande) {

    }

    @Override
    public void updateCommandeToDB(Commande commande) {

    }

    @Override
    public void deleteCommandeToDB(Commande commande) {

    }
}
