package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Utilisateur;
import fr.eni.pizza.dao.IDAOUtilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("mysql")
@Repository
public class DAOUtilisateurMySQL implements IDAOUtilisateur {
    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        return List.of();
    }

    @Override
    public Utilisateur findUtilisateurById(Long id) {
        return null;
    }

    @Override
    public void addUtilisateurToDB(Utilisateur utilisateur) {

    }

    @Override
    public void updateUtilisateurToDB(Utilisateur utilisateur) {

    }

    @Override
    public void deleteUtilisateurToDB(Utilisateur utilisateur) {

    }
}
