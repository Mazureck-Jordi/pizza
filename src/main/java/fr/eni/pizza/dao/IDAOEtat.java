package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Etat;

import java.util.List;

public interface IDAOEtat {

    List<Etat> findAllEtats ();

    Etat findEtatById (Long id);

    void addEtatToDB (Etat etat);

    void updateEtatToDB (Etat etat);

    void deleteEtatToDB (Etat etat);
}
