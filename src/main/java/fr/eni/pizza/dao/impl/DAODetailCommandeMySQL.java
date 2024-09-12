package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.*;
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

@Profile("mysql")
@Repository
public class DAODetailCommandeMySQL implements IDAODetailCommande {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final RowMapper<DetailCommande>DETAIL_COMMANDE_ROW_MAPPER = new RowMapper<DetailCommande>() {


        @Override
        public DetailCommande mapRow(ResultSet rs, int rowNum) throws SQLException {
            DetailCommande detailCommande = new DetailCommande();
            detailCommande.setQuantite(rs.getInt("quantite"));


            Produit produit = new Produit();
            //On détermine les colonnes de la table produit qui seront mapper
            produit.setId_produit(rs.getLong("PRODUIT_id_produit"));
            produit.setNom(rs.getString("nom"));
            produit.setDescription(rs.getString("description"));
            produit.setPrix(rs.getDouble("prix"));
            produit.setImage_url(rs.getString("image_url"));

            //On instancie un Type_Produit à mapper
            TypeProduit typeProduit = new TypeProduit();
            //On détermine les colonnes de la table Type_Produit qui seront mapper
            typeProduit.setId_type_produit(rs.getLong("TYPE_PRODUIT_id_type_produit"));
            typeProduit.setLibelle(rs.getString("libelle"));

            //Maintenant on peut mapper la colonne "TYPE_PRODUIT_id_type_produit" de Produit
            // qui est de type TypeProduit
            produit.setId_type_produit(typeProduit);

            detailCommande.setId_produit(produit);

            Commande commande = new Commande();
            commande.setId_commande(rs.getLong("COMMANDE_id_commande"));
            commande.setDate_heure_livraison(rs.getTimestamp("date_heure_livraison").toLocalDateTime());
            commande.setLivraison(rs.getInt("livraison"));
            commande.setEst_paye(rs.getInt("est_paye"));
            commande.setPrix_total(rs.getDouble("prix_total"));

            Etat etat = new Etat();
            etat.setId_etat(rs.getLong("ETAT_id_etat"));
            etat.setLibelle(rs.getString("libelle"));

            commande.setId_etat(etat);

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId_utilisateur(rs.getLong("UTILISATEUR_id_utilisateur"));
            utilisateur.setNom(rs.getString("utilisateur_nom"));
            utilisateur.setPrenom(rs.getString("utilisateur_prenom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setMot_de_passe("mot_de_passe");

            commande.setId_utilisateur(utilisateur);

            Client client = new Client();
            client.setId_client(rs.getLong("CLIENT_id_client"));
            client.setPrenom(rs.getString("client_prenom"));
            client.setNom(rs.getString("client_nom"));
            client.setRue(rs.getString("rue"));
            client.setCode_postal(rs.getString("code_postal"));
            client.setVille(rs.getString("ville"));

            commande.setId_client(client);

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

        private String sqlSelectAllDetailCommande ="SELECT dc.quantite, co.id_commande AS COMMANDE_id_commande , co.date_heure_livraison, co.livraison, co.prix_total, co.est_paye, cl.id_client AS CLIENT_id_client, cl.prenom AS client_prenom, cl.nom AS client_nom, cl.rue, cl.code_postal, cl.ville, e.id_etat AS ETAT_id_etat , e.libelle, u.id_utilisateur AS UTILISATEUR_id_utilisateur , u.nom AS utilisateur_nom, u.prenom AS utilisateur_prenom, u.email, u.mot_de_passe , p.id_produit AS PRODUIT_id_produit , p.nom, p.description, p.prix, p.image_url, t.id_type_produit AS TYPE_PRODUIT_id_type_produit, t.libelle FROM detail_commande dc JOIN commande co ON co.id_commande = dc.COMMANDE_id_commande JOIN client cl ON co.CLIENT_id_client = cl.id_client JOIN etat e ON co.ETAT_id_etat = e.id_etat JOIN utilisateur u ON co.UTILISATEUR_id_utilisateur = u.id_utilisateur JOIN produit p ON p.id_produit = dc.PRODUIT_id_produit JOIN type_produit t ON p.TYPE_PRODUIT_id_type_produit = t.id_type_produit";
        private String sqlSelectDetailCommandeByIdCommande = "SELECT dc.quantite, co.id_commande AS COMMANDE_id_commande , co.date_heure_livraison, co.livraison, co.prix_total, co.est_paye, cl.id_client AS CLIENT_id_client, cl.prenom AS client_prenom, cl.nom AS client_nom, cl.rue, cl.code_postal, cl.ville, e.id_etat AS ETAT_id_etat , e.libelle, u.id_utilisateur AS UTILISATEUR_id_utilisateur , u.nom AS utilisateur_nom, u.prenom AS utilisateur_prenom, u.email, u.mot_de_passe , p.id_produit AS PRODUIT_id_produit , p.nom, p.description, p.prix, p.image_url, t.id_type_produit AS TYPE_PRODUIT_id_type_produit, t.libelle FROM detail_commande dc JOIN commande co ON co.id_commande = dc.COMMANDE_id_commande JOIN client cl ON co.CLIENT_id_client = cl.id_client JOIN etat e ON co.ETAT_id_etat = e.id_etat JOIN utilisateur u ON co.UTILISATEUR_id_utilisateur = u.id_utilisateur JOIN produit p ON p.id_produit = dc.PRODUIT_id_produit JOIN type_produit t ON p.TYPE_PRODUIT_id_type_produit = t.id_type_produit WHERE dc.COMMANDE_id_commande = ?";
        private String sqlSelectSommePrixProduitsByIdCommande = "SELECT co.id_commande AS COMMANDE_id_commande, SUM(p.prix) AS somme_totale FROM detail_commande dc JOIN commande co ON co.id_commande = dc.COMMANDE_id_commande JOIN produit p ON p.id_produit = dc.PRODUIT_id_produit WHERE co.id_commande = ?";
        private String sqlInsertDetailCommande ="INSERT INTO detail_commande (quantite, COMMANDE_id_commande, PRODUIT_id_produit) VALUES ( :detailCommandeQuantite, :detailCommandeIdCommande, :detailCommandeIdProduit)";
        private String sqlUpdateDetailCommande ="UPDATE detail_commande SET quantite = :detailCommandeQuantite, COMMANDE_id_commande = :detailCommandeIdCommande, PRODUIT_id_produit = :detailCommandeIdProduit";
        private String sqlDeleteDetailCommande = "DELETE FROM detail_commande WHERE id_commande = :detailCommandeIdCommande";


    @Override
    public List<DetailCommande> findAllDetailCommandes() {
        return jdbcTemplate.query(sqlSelectAllDetailCommande, DETAIL_COMMANDE_ROW_MAPPER);
    }

    @Override
    public List<DetailCommande> findAllDetailCommandeByIdCommande(Long id) {
        return jdbcTemplate.query(sqlSelectDetailCommandeByIdCommande, DETAIL_COMMANDE_ROW_MAPPER, id);
    }

    @Override
    public DetailCommande findDetailCommandeByIdCommande(Long id) {
        return jdbcTemplate.queryForObject(sqlSelectDetailCommandeByIdCommande, DETAIL_COMMANDE_ROW_MAPPER, id);
    }

    @Override
    public DetailCommande findSommePrixByIdCommande(Long id) {
        return jdbcTemplate.queryForObject(sqlSelectSommePrixProduitsByIdCommande, DETAIL_COMMANDE_ROW_MAPPER, id);
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
