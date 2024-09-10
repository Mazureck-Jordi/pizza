package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Role;
import fr.eni.pizza.dao.IDAORole;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("mysql")
@Repository
public class DAORoleMySQL implements IDAORole {
    @Override
    public List<Role> findAllRoles() {
        return List.of();
    }

    @Override
    public Role findRoleById(Long id_role) {
        return null;
    }
}
