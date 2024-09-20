package fr.eni.pizza.dao;

import fr.eni.pizza.bo.DetailCommande;

import java.util.List;

public interface IDAODetailCommande {

    List<DetailCommande> findAllDetailCommandeByIdEtat(Long idEtat);

    List<DetailCommande>findAllDetailCommandes();

    List<DetailCommande> findAllDetailCommandeByIdCommande(Long id);

    DetailCommande findDetailCommandeByIdCommande(Long idCommande, Long idProduit);

    void addDetailCommandeToDB(DetailCommande detailCommande);

    void updateDetailCommandeToDB(DetailCommande detailCommande);

    void deleteDetailCommandeToDB(Long idCommande, Long idProduit);

    void deleteDetailCommandesToDBByIdCommande(Long idCommande);
}
