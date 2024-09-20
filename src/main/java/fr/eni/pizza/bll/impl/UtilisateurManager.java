package fr.eni.pizza.bll.impl;

import fr.eni.pizza.bll.IUtilisateurManager;
import fr.eni.pizza.bo.Role;
import fr.eni.pizza.bo.Utilisateur;
import fr.eni.pizza.dao.IDAORole;
import fr.eni.pizza.dao.IDAOUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurManager implements IUtilisateurManager {

    @Autowired
    private IDAOUtilisateur daoUtilisateur;

    @Autowired
    private IDAORole daoRole;

    @Override
    public List<Utilisateur> getAll() {

        List<Utilisateur> users = daoUtilisateur.findAllSimple();

        for( Utilisateur user : users ) {
            user.setRoles(daoRole.findRolesByIdUtilisateur(user.getId_utilisateur()));
        }

        return users;
    }

    @Override
    public Utilisateur getById(Long id) {

        Utilisateur user = daoUtilisateur.findUtilisateurByIdSimple(id);

        user.setRoles(daoRole.findRolesByIdUtilisateur(id));

        return user;
    }

    @Override
    public Utilisateur getByEmail(String email) {
        return daoUtilisateur.findByEmail(email);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {

        List<Utilisateur> users = daoUtilisateur.findAllUtilisateurs();

        for( Utilisateur user : users ) {
            user.setRoles(daoRole.findRolesByIdUtilisateur(user.getId_utilisateur()));
        }

        return users;
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) {

       Utilisateur user = daoUtilisateur.findUtilisateurById(id);

       user.setRoles(daoRole.findRolesByIdUtilisateur(id));

        return user;
    }

    @Override
    public List<Role> getAllRoles() {

        return daoRole.findAllRoles();
    }

    @Override
    public Role getRoleById(Long id) {

        return daoRole.findRoleById(id);
    }

    @Override
    public void addUtilisateur(Utilisateur utilisateur) {

        utilisateur.setMot_de_passe(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(utilisateur.getMot_de_passe()));

        daoUtilisateur.addUtilisateurToDB(utilisateur);
        // daoRole.addRoleUtilisateur();
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {

        utilisateur.setMot_de_passe(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(utilisateur.getMot_de_passe()));

        daoUtilisateur.updateUtilisateurToDB(utilisateur);

        if(utilisateur.getRoles() != null) {
            boolean SameRoleFound = false;
            for (Role role2 : utilisateur.getRoles()) {
                for (Role role1 : daoRole.findRolesByIdUtilisateur(utilisateur.getId_utilisateur())) {
                    if (role1.getId_role() == role2.getId_role()) {
                        SameRoleFound = true;
                    }
                }
            }
            if (!SameRoleFound) {
                daoUtilisateur.updateRoleUtilisateursToDB(utilisateur);
            }
        }
    }

    @Override
    public void deleteUtilisateur(Utilisateur utilisateur) {
        daoUtilisateur.deleteRoleUtilisateursToDB(utilisateur);
        daoUtilisateur.deleteUtilisateurToDB(utilisateur);
    }

    @Override
    public void addRole(Role role) {
        daoRole.addRoleToDB(role);
    }

    @Override
    public void updateRole(Role role) {
    daoRole.updateRoleToDB(role);
    }

    @Override
    public void deleteRole(Role role) {
    daoRole.deleteRoleToDB(role);
    }

    @Override
    public void deteteRoleUtilisateur (Long idUtilisateur, Long idRole) {
        daoRole.deleteRoleUtilisateurToDB(idUtilisateur, idRole);
    }
}
