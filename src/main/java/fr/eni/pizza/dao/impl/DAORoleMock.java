package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Role;
import fr.eni.pizza.dao.IDAORole;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Profile("mock")
@Repository
public class DAORoleMock implements IDAORole {

    //On instancie une fausse liste de rôle
    List<Role> roles = Arrays.asList(
            new Role(1L, "Pizzaiolo"),
            new Role(2L, "Gérant"),
            new Role(3L, "Livreur"));

    @Override
    public List<Role> findAllRoles() {

        return roles;
    }

    @Override
    public Role findRoleById(Long id_role) {

        Role roleToFound = roles.stream().filter(role -> role.getId_role() == id_role).findFirst().orElse(null);

        return roleToFound;
    }
}
