package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.TypeProduit;
import fr.eni.pizza.dao.IDAOTypeProduit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Profile("type-produit-mock")
@Repository
public class DAOTypeProduitMock implements IDAOTypeProduit {

    List<TypeProduit> typeProduits = Arrays.asList(
            new TypeProduit(1l, "pizza"),
            new TypeProduit(2L, "boisson")
    );

    @Override
    public List<TypeProduit> findAllTypeProduits() {
        return typeProduits;
    }

    @Override
    public TypeProduit findTypeProduitByID(Long id_type_produit) {

        TypeProduit typeProduitToFound = typeProduits.stream().filter(type -> type.getId_type_produit() == id_type_produit).findFirst().orElse(null);

        return typeProduitToFound;
    }
}
