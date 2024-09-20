package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Role;
import fr.eni.pizza.bo.Utilisateur;

import java.util.List;

public interface IDAOUtilisateur {

    public List<Utilisateur> findAllSimple();

    public Utilisateur findUtilisateurByIdSimple(Long id);

    List<Utilisateur> findAll();

    Utilisateur findById(Long id);

    Utilisateur findByEmail(String email);

    List<Utilisateur> findAllUtilisateurs ();

    Utilisateur findUtilisateurById (Long id_utilisateur);

    void addUtilisateurToDB (Utilisateur utilisateur);

    void updateUtilisateurToDB (Utilisateur utilisateur);

    void deleteUtilisateurToDB (Utilisateur utilisateur);

    void updateRoleUtilisateursToDB(Utilisateur utilisateur);

    void deleteRoleUtilisateursToDB (Utilisateur utilisateur);
}
