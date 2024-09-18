package fr.eni.pizza.bll;

import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.bo.Etat;

import java.util.List;

public interface ICommandeManager {

    List<Commande> getCommandesByIdEtat(Long idEtat);

    List<Commande> getAllCommandes() ;

    Commande getCommandeById(Long id) ;

    Commande getLastCommande() ;

    List<Etat> getAllEtats() ;

    Etat getEtatById(Long id) ;

    void addCommande(Commande commande) ;

    void updateCommande(Commande commande) ;

    void updteCommandeById(Commande commande) ;

    void deleteCommande(Commande commande) ;

    void addEtat(Etat etat) ;

    void updateEtat(Etat etat) ;

    void deleteEtat(Etat etat) ;
}
