package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Utilisateur;
import fr.eni.pizza.dao.IDAOUtilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Profile("utlisateur-mock")
@Repository
public class DAOUtilisateurMock implements IDAOUtilisateur {

    //On instancie une fausse liste d'utilisateur
    List<Utilisateur> utilisateurs = Arrays.asList(
            new Utilisateur(1L, "Cutraro", "Giuseppe", "gcutraro@gmail.com", "pepe"),
            new Utilisateur(2L, "Bradley", "Andrew", "abradley@gmail.com", "drewdrew"),
            new Utilisateur(3L, "Hubert", "hubert", "hubert@gmail.com", "bebert"));


    @Override
    public List<Utilisateur> findAll() {
        return List.of();
    }

    @Override
    public Utilisateur findById(Long id) {
        return null;
    }

    @Override
    public List<Utilisateur> findAllUtilisateurs() {

        return utilisateurs;
    }

    @Override
    public Utilisateur findUtilisateurById(Long id) {

        Utilisateur utilisateurToFound = utilisateurs.stream().filter(utilisateur -> utilisateur.getId_utilisateur() == id).findFirst().orElse(null);

        return utilisateurToFound;
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
