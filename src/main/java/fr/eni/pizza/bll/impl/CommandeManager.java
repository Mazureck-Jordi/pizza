package fr.eni.pizza.bll.impl;

import fr.eni.pizza.bll.ICommandeManager;
import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.bo.Etat;
import fr.eni.pizza.dao.IDAOCommande;
import fr.eni.pizza.dao.IDAOEtat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeManager implements ICommandeManager {


    @Autowired
    IDAOCommande commandeDAO;

    @Autowired
    IDAOEtat etatDAO;


    @Override
    public List<Commande> getAllCommandes() {
        return commandeDAO.findAllCommandes();
    }

    @Override
    public Commande getCommandeById(Long id) {
        return commandeDAO.findCommandeById(id);
    }

    @Override
    public Commande getLastCommande() {
        return commandeDAO.findLastCommande();
    }

    @Override
    public List<Etat> getAllEtats() {
        return etatDAO.findAllEtats();
    }

    @Override
    public Etat getEtatById(Long id) {
        return etatDAO.findEtatById(id);
    }

    @Override
    public void addCommande(Commande commande) {
        commandeDAO.addCommandeToDB(commande);
    }

    @Override
    public void updateCommande(Commande commande) {
        commandeDAO.updateCommandeToDB(commande);
    }

    @Override
    public void updteCommandeById(Commande commande) {
        commandeDAO.updateCommandeToDBById(commande);

    }

    @Override
    public void deleteCommande(Commande commande) {
        commandeDAO.deleteCommandeToDB(commande);
    }

    @Override
    public void addEtat(Etat etat) {
        etatDAO.addEtatToDB(etat);
    }

    @Override
    public void updateEtat(Etat etat) {
        etatDAO.updateEtatToDB(etat);
    }

    @Override
    public void deleteEtat(Etat etat) {
        etatDAO.deleteEtatToDB(etat);
    }
}
