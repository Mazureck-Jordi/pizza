package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Role;

import java.util.List;

public interface IDAORole {

    List<Role> findAllRoles();

    Role findRoleById(Long id_role);
}
