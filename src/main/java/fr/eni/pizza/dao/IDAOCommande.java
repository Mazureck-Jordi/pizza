package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Commande;

import java.util.List;

public interface IDAOCommande {

    List<Commande> findAllCommandes();

    Commande findCommandeById(Long id);

    void addCommandeToDB(Commande commande);

    void updateCommandeToDB(Commande commande);

    void deleteCommandeToDB(Commande commande);
}
