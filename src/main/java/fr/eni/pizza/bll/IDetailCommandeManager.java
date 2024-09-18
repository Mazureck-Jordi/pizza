package fr.eni.pizza.bll;

import fr.eni.pizza.bo.DetailCommande;

import java.util.List;

public interface IDetailCommandeManager {

    List<DetailCommande> getAllDetailCommandeByIdEtat(Long idEtat);

    List<DetailCommande> getAllDetailCommandes();

    List<DetailCommande> getAllDetailCommandeByIdCommande(Long id);

    DetailCommande getDetailCommandeByIdCommandeAndIdProduit(Long idCommande, Long idProduit);

    void addDetailCommande(DetailCommande detailCommande);

    void updateDetailCommande(DetailCommande detailCommande);

    void deleteDetailCommande(Long idCommande, Long idProduit);
}
