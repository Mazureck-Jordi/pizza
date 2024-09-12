package fr.eni.pizza.dao;

import fr.eni.pizza.bo.DetailCommande;

import java.util.List;

public interface IDAODetailCommande {

    List<DetailCommande>findallDetailCommandes();

    DetailCommande findDetailCommandeByIdCommande(Long id);

    void addDetailCommandeToDB(DetailCommande detailCommande);

    void updateDetailCommandeToDB(DetailCommande detailCommande);

    void deleteDetailCommandeToDB(DetailCommande detailCommande);
}
