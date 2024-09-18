package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.*;
import fr.eni.pizza.dao.IDAOCommande;
import fr.eni.pizza.dao.IDAOProduit;
import org.apache.catalina.security.SecurityListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Profile("mysql")
@Repository
public class DAOCommandeMySQL implements IDAOCommande {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    IDAOProduit daoProduit;

    static final RowMapper<Commande> COMMANDE_ROW_MAPPER = new RowMapper<Commande>() {
        @Override
        public Commande mapRow(ResultSet rs, int rowNum) throws SQLException {
            Commande commande = new Commande();
            commande.setId_commande(rs.getLong("id_commande"));
            commande.setDate_heure_livraison(rs.getTimestamp("date_heure_livraison").toLocalDateTime());
            commande.setLivraison(rs.getInt("livraison"));
            commande.setEst_paye(rs.getInt("est_paye"));
            commande.setPrix_total(rs.getDouble("prix_total"));

            if (rs.getLong("ETAT_id_etat") != 0) {
                Etat etat = new Etat();
                etat.setId_etat(rs.getLong("ETAT_id_etat"));
                etat.setLibelle(rs.getString("libelle"));

                commande.setId_etat(etat);

            }

            if (rs.getLong("UTILISATEUR_id_utilisateur") != 0) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId_utilisateur(rs.getLong("UTILISATEUR_id_utilisateur"));
                utilisateur.setNom(rs.getString("utilisateur_nom"));
                utilisateur.setPrenom(rs.getString("utilisateur_prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setMot_de_passe("mot_de_passe");

                commande.setId_utilisateur(utilisateur);
            }

            if (rs.getLong("CLIENT_id_client") != 0) {
                Client client = new Client();
                client.setId_client(rs.getLong("CLIENT_id_client"));
                client.setPrenom(rs.getString("client_prenom"));
                client.setNom(rs.getString("client_nom"));
                client.setRue(rs.getString("rue"));
                client.setCode_postal(rs.getString("code_postal"));
                client.setVille(rs.getString("ville"));

                commande.setId_client(client);

            }

            return commande;
        }
    };

    static final RowMapper<Commande> IDCOMMANDE_ROW_MAPPER = new RowMapper<Commande>() {
        @Override
        public Commande mapRow(ResultSet rs, int rowNum) throws SQLException {
            Commande commande = new Commande();
            commande.setId_commande(rs.getLong("id_commande"));
            return commande;
        }
    };

    private MapSqlParameterSource map(Commande commande) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idCommande", commande.getId_commande());
        mapSqlParameterSource.addValue("dateCommande", commande.getDate_heure_livraison());
        mapSqlParameterSource.addValue("livraisonCommande", commande.getLivraison());
        mapSqlParameterSource.addValue("prixTotalCommande", commande.getPrix_total());
        mapSqlParameterSource.addValue("estPayeCommande", commande.getEst_paye());
        mapSqlParameterSource.addValue("etatCommande", commande.getId_etat().getId_etat());
        mapSqlParameterSource.addValue("clientCommande", commande.getId_client().getId_client());
        mapSqlParameterSource.addValue("utilisateurCommande", commande.getId_utilisateur().getId_utilisateur());

        return mapSqlParameterSource;
    }

    private String sqlSelectCommandesByIdEtat ="SELECT co.id_commande, co.date_heure_livraison, co.livraison, co.prix_total, co.est_paye, co.CLIENT_id_client, cl.id_client, cl.prenom AS client_prenom, cl.nom AS client_nom, cl.rue, cl.code_postal, cl.ville, co.ETAT_id_etat, e.id_etat, e.libelle, co.UTILISATEUR_id_utilisateur, u.id_utilisateur, u.nom AS utilisateur_nom, u.prenom AS utilisateur_prenom, u.email, u.mot_de_passe FROM commande co JOIN client cl ON co.CLIENT_id_client = cl.id_client JOIN etat e ON co.ETAT_id_etat = e.id_etat JOIN utilisateur u ON co.UTILISATEUR_id_utilisateur = u.id_utilisateur WHERE co.ETAT_id_etat = :idEtat";
    private String sqlSelectAllCommande = "SELECT co.id_commande, co.date_heure_livraison, co.livraison, co.prix_total, co.est_paye, co.CLIENT_id_client, cl.id_client, cl.prenom AS client_prenom, cl.nom AS client_nom, cl.rue, cl.code_postal, cl.ville, co.ETAT_id_etat, e.id_etat, e.libelle, co.UTILISATEUR_id_utilisateur, u.id_utilisateur, u.nom AS utilisateur_nom, u.prenom AS utilisateur_prenom, u.email, u.mot_de_passe FROM commande co JOIN client cl ON co.CLIENT_id_client = cl.id_client JOIN etat e ON co.ETAT_id_etat = e.id_etat JOIN utilisateur u ON co.UTILISATEUR_id_utilisateur = u.id_utilisateur";
    private String sqlSelectCommandeById = "SELECT co.id_commande, co.date_heure_livraison, co.livraison, co.prix_total, co.est_paye, co.CLIENT_id_client, cl.id_client, cl.prenom AS client_prenom, cl.nom AS client_nom, cl.rue, cl.code_postal, cl.ville, co.ETAT_id_etat, e.id_etat, e.libelle, co.UTILISATEUR_id_utilisateur, u.id_utilisateur, u.nom AS utilisateur_nom, u.prenom AS utilisateur_prenom, u.email, u.mot_de_passe FROM commande co JOIN client cl ON co.CLIENT_id_client = cl.id_client JOIN etat e ON co.ETAT_id_etat = e.id_etat JOIN utilisateur u ON co.UTILISATEUR_id_utilisateur = u.id_utilisateur WHERE co.id_commande = ?";
    private String sqlInsertCommande = "INSERT INTO commande (id_commande, date_heure_livraison, CLIENT_id_client, livraison, ETAT_id_etat, UTILISATEUR_id_utilisateur, prix_total, est_paye) VALUES (:idCommande, :dateCommande, :clientCommande, :livraisonCommande, :etatCommande, :utilisateurCommande, :prixTotalCommande, :estPayeCommande)";
    private String sqlUpdateCommande = "UPDATE commande SET date_heure_livraison = :dateCommande, CLIENT_id_client = :clientCommande, livraison = :livraisonCommande, ETAT_id_etat =:etatCommande, UTILISATEUR_id_utilisateur = :utilisateurCommande, prix_total = :prixTotalCommande, est_paye = :estPayeCommande WHERE id_commande = :idCommande";
    private String sqlUpdateCommandeById = "UPDATE commande SET date_heure_livraison = :dateCommande, CLIENT_id_client = :clientCommande, livraison = :livraisonCommande, ETAT_id_etat =:etatCommande, UTILISATEUR_id_utilisateur = :utilisateurCommande, prix_total = :prixTotalCommande, est_paye = :estPayeCommande WHERE id_commande = :idCommande";
    private String sqlDeleteCommande = "DELETE FROM commande WHERE id_commande = :idCommande";
    private String sqlSelectByLastId = "SELECT id_commande FROM commande ORDER BY id_commande DESC LIMIT 1";

    @Override
    public List<Commande> findCommandesByIdEtat(Long idEtat) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("idEtat", idEtat);
        return namedParameterJdbcTemplate.query(sqlSelectCommandesByIdEtat, map , COMMANDE_ROW_MAPPER);
    }

    @Override
    public List<Commande> findAllCommandes() {
        return jdbcTemplate.query(sqlSelectAllCommande, COMMANDE_ROW_MAPPER);
    }

    @Override
    public Commande findCommandeById(Long id) {
        return jdbcTemplate.queryForObject(sqlSelectCommandeById, COMMANDE_ROW_MAPPER, id);
    }

    @Override
    public Commande findLastCommande() {
        return jdbcTemplate.queryForObject(sqlSelectByLastId, IDCOMMANDE_ROW_MAPPER);
    }


    @Override
    public void addCommandeToDB(Commande commande) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlInsertCommande, map(commande), keyHolder);
        if (keyHolder.getKey() != null) {
            commande.setId_commande(keyHolder.getKey().longValue());
        }
    }

    @Override
    public void updateCommandeToDB(Commande commande) {
        namedParameterJdbcTemplate.update(sqlUpdateCommande, map(commande));
    }

    @Override
    public void updateCommandeToDBById(Commande commande) {
        namedParameterJdbcTemplate.update(sqlUpdateCommandeById, map(commande));
    }


    @Override
    public void deleteCommandeToDB(Commande commande) {
        namedParameterJdbcTemplate.update(sqlDeleteCommande, map(commande));
    }

    private void updateDetailCommandeByComande (Commande commmande) {
        String sqlInsertDetailsCommande = "INSERT INTO detail_commande (quantite, COMMANDE_id_commande, PRODUIT_id_produit) VALUES (:quantiteDetails, :idCommandeDetails, :idProduitDetails)";
        for (DetailCommande detailCommande : commmande.getDetail_commandes()) {
            MapSqlParameterSource mapParticipantSource = new MapSqlParameterSource();
            mapParticipantSource.addValue("idCommandeDetails", commmande.getId_commande());
            mapParticipantSource.addValue("idProduitDetails", detailCommande.getId_produit());
            mapParticipantSource.addValue("quantiteDetails", detailCommande.getQuantite());
            namedParameterJdbcTemplate.update(sqlInsertDetailsCommande, mapParticipantSource);
        }
    }

}
