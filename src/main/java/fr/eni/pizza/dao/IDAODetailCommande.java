package fr.eni.pizza.dao;

import fr.eni.pizza.bo.DetailCommande;

import java.util.List;

public interface IDAODetailCommande {

    List<DetailCommande>findAllDetailCommandes();

    List<DetailCommande> findAllDetailCommandeByIdCommande(Long id);

    DetailCommande findDetailCommandeByIdCommande(Long id);

    DetailCommande findSommePrixByIdCommande(Long id);

    void addDetailCommandeToDB(DetailCommande detailCommande);

    void updateDetailCommandeToDB(DetailCommande detailCommande);

    void deleteDetailCommandeToDB(DetailCommande detailCommande);
}
