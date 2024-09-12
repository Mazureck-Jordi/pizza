package fr.eni.pizza.bll;

import fr.eni.pizza.bo.DetailCommande;

import java.util.List;

public interface IDetailCommandeManager {

    List<DetailCommande> getAllDetailCommandes();

    DetailCommande getDetailCommandeByIdCommande(Long id);

    void addDetailCommande(DetailCommande detailCommande);

    void updateDetailCommande(DetailCommande detailCommande);

    void deleteDetailCommande(DetailCommande detailCommande);
}
