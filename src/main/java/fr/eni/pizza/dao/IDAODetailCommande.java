package fr.eni.pizza.dao;

import fr.eni.pizza.bo.DetailCommande;

import java.util.List;

public interface IDAODetailCommande {

    List<DetailCommande>findAllDetailCommandes();

    List<DetailCommande> findAllDetailCommandeByIdCommande(Long id);

    public DetailCommande findDetailCommandeByIdCommande(Long idCommande, Long idProduit);

    void addDetailCommandeToDB(DetailCommande detailCommande);

    void updateDetailCommandeToDB(DetailCommande detailCommande);

    void deleteDetailCommandeToDB(Long idCommande, Long idProduit);
}
