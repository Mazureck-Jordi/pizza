package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Role;

import java.util.List;

public interface IDAORole {

    List<Role> findRolesByIdUtilisateur(long idUtilisateur);

    List<Role> findAllRoles();

    Role findRoleById(Long id_role);


    void addRoleToDB (Role role);

    void updateRoleToDB(Role role);

    void deleteRoleToDB(Role role);
}

