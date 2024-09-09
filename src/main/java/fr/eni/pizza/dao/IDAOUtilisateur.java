package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Utilisateur;

import java.util.List;

public interface IDAOUtilisateur {

    List<Utilisateur> findAllUtilisateurs ();

    Utilisateur findUtilisateurById (Long id);

    void addUtilisateurToDB (Utilisateur utilisateur);

    void updateUtilisateurToDB (Utilisateur utilisateur);

    void deleteUtilisateurToDB (Utilisateur utilisateur);

}
