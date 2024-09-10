package fr.eni.pizza.bll;

import fr.eni.pizza.bo.Role;
import fr.eni.pizza.bo.Utilisateur;

import java.util.List;

public interface IUtilisateurManager {

    List<Utilisateur> getAllUtilisateurs();

    Utilisateur getUtilisateurById(Long id);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    void addUtilisateur(Utilisateur utilisateur);

    void updateUtilisateur(Utilisateur utilisateur);

    void deleteUtilisateur(Utilisateur utilisateur);


}