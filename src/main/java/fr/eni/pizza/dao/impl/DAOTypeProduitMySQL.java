package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.TypeProduit;
import fr.eni.pizza.dao.IDAOTypeProduit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("mysql")
@Repository
public class DAOTypeProduitMySQL implements IDAOTypeProduit {
    @Override
    public List<TypeProduit> findAllTypeProduits() {
        return List.of();
    }

    @Override
    public TypeProduit findTypeProduitByID(Long id) {
        return null;
    }
}
