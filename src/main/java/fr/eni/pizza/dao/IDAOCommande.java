package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Commande;

import java.util.List;

public interface IDAOCommande {

    List<Commande> findCommandesByIdEtat(Long idEtat);

    List<Commande> findAllCommandes();

    Commande findCommandeById(Long id);

    Commande findLastCommande();

    void addCommandeToDB(Commande commande);

    void updateCommandeToDB(Commande commande);

    void updateCommandeToDBById(Commande commande);

    void deleteCommandeToDB(Commande commande);
}
