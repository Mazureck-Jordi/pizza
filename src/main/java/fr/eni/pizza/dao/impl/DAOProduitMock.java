package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.dao.IDAOProduit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Profile("produit-mock")
@Repository
public class DAOProduitMock implements IDAOProduit {

    List<Produit> produits = Arrays.asList(
            new Produit(1L, "La Margherita", "Tomate, Mozzarella, Basilic", 11.00, "https://lafrenchpizza.fr/wp-content/uploads/2024/04/Pizza-Napolitaine-Marguerita-magnifique-1024x682.jpg"),
            new Produit(2L, "La Calzone", "Tomate, Mozzarella, Jambon, Champignons", 13.00, "https://lafrenchpizza.fr/wp-content/uploads/2024/04/Calzone-Napolitain-1024x576.jpg"),
            new Produit(3L, "La Pizza Hawaïenne", "jambon, ananas, fromage", 12.00, "https://lafrenchpizza.fr/wp-content/uploads/2024/04/Pizza-Hawaienne-1-1024x682.jpg"),
            new Produit(4L, "Coca-Cola", "Canette 33cl", 1.50, "https://www.coin-aubrac.fr/wp-content/uploads/2023/04/coca-cola-canette-slim-33cl.jpg"),
            new Produit(5L, "Ice-Tea", "Canette 33cl", 1.50, "https://i0.wp.com/commechezmams.fr/wp-content/uploads/2021/01/ICE-TEA-33.jpg?fit=2000%2C2000&ssl=1"),
            new Produit(6L, "Perrier", "Canette 33cl", 1.50, "https://maxifournitures.fr/12364-large_default/perrier-canette-d-eau-petillante-33-cl-minerale.jpg")
    );

    @Override
    public List<Produit> findAllProduits() {
        return produits;
    }

    @Override
    public Produit findProduitById(Long id_produit) {

    Produit produitTofound = produits.stream().filter(produit -> produit.getId_produit() == id_produit).findFirst().orElse(null);

    return produitTofound;
    }

    @Override
    public void addProduitToDB(Produit produit) {
        produits.add(produit);
    }

    @Override
    public void updateProduitToDB(Produit produit) {

    }

    @Override
    public void deleteProduitToDB(Produit produit) {

    }
}
