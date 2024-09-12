package fr.eni.pizza.bll.impl;

import fr.eni.pizza.bll.IDetailCommandeManager;
import fr.eni.pizza.bo.DetailCommande;
import fr.eni.pizza.dao.IDAODetailCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DetailCommandeManager implements IDetailCommandeManager {

    @Autowired
    private IDAODetailCommande detailCommandeDAO;


    @Override
    public List<DetailCommande> getAllDetailCommandes() {
        return detailCommandeDAO.findAllDetailCommandes();
    }

    @Override
    public List<DetailCommande> getAllDetailCommandeByIdCommande(Long id) {
        return detailCommandeDAO.findAllDetailCommandeByIdCommande(id);
    }

    @Override
    public DetailCommande getDetailCommandeByIdCommande(Long id) {
        return detailCommandeDAO.findDetailCommandeByIdCommande(id);
    }

    @Override
    public DetailCommande getSommePrixByIdCommande(Long id) {
        return detailCommandeDAO.findSommePrixByIdCommande(id);
    }


    @Override
    public void addDetailCommande(DetailCommande detailCommande) {
        detailCommandeDAO.addDetailCommandeToDB(detailCommande);
    }

    @Override
    public void updateDetailCommande(DetailCommande detailCommande) {
        detailCommandeDAO.updateDetailCommandeToDB(detailCommande);
    }

    @Override
    public void deleteDetailCommande(DetailCommande detailCommande) {
       detailCommandeDAO.deleteDetailCommandeToDB(detailCommande);
    }
}
