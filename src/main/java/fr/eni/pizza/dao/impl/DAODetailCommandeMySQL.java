package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.bo.DetailCommande;
import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.dao.IDAODetailCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Profile("detail-commande-sql")
@Repository
public class DAODetailCommandeMySQL implements IDAODetailCommande {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final RowMapper<DetailCommande>DETAIL_COMMANDE_ROW_MAPPER = new RowMapper<DetailCommande>() {


        @Override
        public DetailCommande mapRow(ResultSet rs, int rowNum) throws SQLException {
            DetailCommande detailCommande = new DetailCommande();
            detailCommande.setQuantite(rs.getInt("quantite"));


            Produit produit = new Produit();
            produit.setId_produit(rs.getLong("id_produit"));
            produit.setNom(rs.getString("nom"));
            produit.setDescription(rs.getString("description"));
            produit.setPrix(rs.getDouble("prix"));
            produit.setImage_url(rs.getString("image_url"));

            detailCommande.setId_produit(produit);

            Commande commande = new Commande();
            commande.setId_commande(rs.getLong("id_commande"));
            commande.setDate_heure_livraison(rs.getTimestamp("date_heure_livraison").toLocalDateTime());
            commande.setLivraison(rs.getInt("livraison"));
            commande.setEst_paye(rs.getInt("est_paye"));
            commande.setPrix_total(rs.getDouble("prix_total"));

            detailCommande.setId_commande(commande);

            return detailCommande;
        }
    };
        private MapSqlParameterSource map(DetailCommande detailCommande) {

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("detailCommandeQuantite", detailCommande.getQuantite());
            mapSqlParameterSource.addValue("detailCommandeIdProduit", detailCommande.getId_produit().getId_produit());
            mapSqlParameterSource.addValue("detailCommandeIdCommande", detailCommande.getId_commande().getId_commande());

            return mapSqlParameterSource;
        }

        private String sqlSelectAllDetailCommande ="SELECT c.id_commande AS COMMANDE_id_commande , dc.quantite, p.id_produit AS PRODUIT_id_produit , p.nom, p.description, p.prix, p.image_url, c.date_heure_livraison, c.livraison, c.est_paye, c.prix_total FROM detail_commande dc JOIN commande c ON dc.COMMANDE_id_commande = c.id_commande JOIN produit p ON dc.PRODUIT_id_produit = p.id_produit";
        private String sqlSelectDetailCommandeByIdCommande = "SELECT c.id_commande AS COMMANDE_id_commande , dc.quantite, p.id_produit AS PRODUIT_id_produit , p.nom, p.description, p.prix, p.image_url, c.date_heure_livraison, c.livraison, c.est_paye, c.prix_total FROM detail_commande dc JOIN commande c ON dc.COMMANDE_id_commande = c.id_commande JOIN produit p ON dc.PRODUIT_id_produit = p.id_produit WHERE dc.COMMANDE_id_commande = ?";
        private String sqlInsertDetailCommande ="INSERT INTO detailCommande (quantite, COMMANDE_id_commande, PRODUIT_id_produit) VALUES ( :detailCommandeQuantite, :detailCommandeIdProduit, :detailCommandeIdCommande)";
        private String sqlUpdateDetailCommande ="UPDATE detail_commande SET quantite = :detailCommandeQuantite, COMMANDE_id_commande = :detailCommandeIdProduit, PRODUIT_id_produit = :detailCommandeIdCommande";
        private String sqlDeleteDetailCommande = "DELETE FROM detail_commande WHERE id_commande = :detailCommandeIdCommande";


    @Override
    public List<DetailCommande> findallDetailCommandes() {
        return jdbcTemplate.query(sqlSelectAllDetailCommande, DETAIL_COMMANDE_ROW_MAPPER);
    }

    @Override
    public DetailCommande findDetailCommandeByIdCommande(Long id) {
        return jdbcTemplate.queryForObject(sqlSelectDetailCommandeByIdCommande, DETAIL_COMMANDE_ROW_MAPPER, id);
    }

    @Override
    public void addDetailCommandeToDB(DetailCommande detailCommande) {
        namedParameterJdbcTemplate.update(sqlInsertDetailCommande, map(detailCommande));

    }

    @Override
    public void updateDetailCommandeToDB(DetailCommande detailCommande) {
        namedParameterJdbcTemplate.update(sqlUpdateDetailCommande, map(detailCommande));
    }

    @Override
    public void deleteDetailCommandeToDB(DetailCommande detailCommande) {
        namedParameterJdbcTemplate.update(sqlDeleteDetailCommande, map(detailCommande));
    }
}
