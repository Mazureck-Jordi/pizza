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
    public List<DetailCommande> getAllDetailCommandeByIdEtat(Long idEtat) {
        return detailCommandeDAO.findAllDetailCommandeByIdEtat(idEtat);
    }

    @Override
    public List<DetailCommande> getAllDetailCommandes() {
        return detailCommandeDAO.findAllDetailCommandes();
    }

    @Override
    public List<DetailCommande> getAllDetailCommandeByIdCommande(Long id) {
        return detailCommandeDAO.findAllDetailCommandeByIdCommande(id);
    }

    @Override
    public DetailCommande getDetailCommandeByIdCommandeAndIdProduit(Long idCommande, Long idProduit) {
        return detailCommandeDAO.findDetailCommandeByIdCommande(idCommande, idProduit);
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
    public void deleteDetailCommande(Long idCommande, Long idProduit) {
       detailCommandeDAO.deleteDetailCommandeToDB(idCommande, idProduit);
    }

    @Override
    public void deleteDetailCommandeByIdCommande(Long idCommande) {
        detailCommandeDAO.deleteDetailCommandesToDBByIdCommande(idCommande);
    }
}
