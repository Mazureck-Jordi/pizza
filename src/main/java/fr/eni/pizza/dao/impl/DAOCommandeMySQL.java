package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Client;
import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.bo.Etat;
import fr.eni.pizza.bo.Utilisateur;
import fr.eni.pizza.dao.IDAOCommande;
import org.apache.catalina.security.SecurityListener;
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

@Profile("commande-sql")
@Repository
public class DAOCommandeMySQL implements IDAOCommande {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final RowMapper<Commande> COMMANDE_ROW_MAPPER = new RowMapper<Commande>() {
        @Override
        public Commande mapRow(ResultSet rs, int rowNum) throws SQLException {
            Commande commande = new Commande();
            commande.setId_commande(rs.getLong("id"));
            commande.setDate_heure_livraison(rs.getDate("date_heure_livraison").toLocalDate());
            commande.setLivraison(rs.getInt("livraison"));
            commande.setEst_paye(rs.getInt("est_paye"));

            Etat etat = new Etat();
            etat.setId_etat(rs.getLong("ETAT_id_etat"));
            etat.setLibelle(rs.getString("libelle"));

            commande.setId_etat(etat);

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId_utilisateur(rs.getLong("UTILISATEUR_id_utilisateur"));
            utilisateur.setNom(rs.getString("nom"));
            utilisateur.setPrenom(rs.getString("prenom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setMot_de_passe("mot_de_passe");

            commande.setId_utilisateur(utilisateur);

            Client client = new Client();
            client.setId_client(rs.getLong("CLIENT_id_client"));
            client.setPrenom(rs.getString("prenom"));
            client.setNom(rs.getString("nom"));
            client.setRue(rs.getString("rue"));
            client.setCode_postal(rs.getString("code_postal"));
            client.setVille(rs.getString("ville"));

            commande.setId_client(client);

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



    private String sqlSelectAllCommande = "SELECT co.id_commande, co.date_heure_livraison, co.livraison, co.prix_total, co.est_paye\n" +
            ", co.CLIENT_id_client, cl.id_client, cl.prenom, cl.nom, cl.rue, cl.code_postal, cl.ville\n" +
            ", co.ETAT_id_etat, e.id_etat, e.libelle\n" +
//            ", co.UTILISATEUR_id_utilisateur, u.id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe\n" +
            "FROM commande co\n" +
            "JOIN client cl ON co.CLIENT_id_client = cl.id_client\n" +
            "JOIN etat e ON co.ETAT_id_etat = e.id_etat";
//            "JOIN utilisateur u ON co.UTILISATEUR_id_utilisateur = u.id_utilisateur";
    private String sqlSelectCommandeById = "SELECT co.id_commande, co.date_heure_livraison, co.livraison, co.prix_total, co.est_paye\n" +
            ", co.CLIENT_id_client, cl.id_client, cl.prenom, cl.nom, cl.rue, cl.code_postal, cl.ville\n" +
            ", co.ETAT_id_etat, e.id_etat, e.libelle\n" +
            ", co.UTILISATEUR_id_utilisateur, u.id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe\n" +
            "FROM commande co\n" +
            "JOIN client cl ON co.CLIENT_id_client = cl.id_client\n" +
            "JOIN etat e ON co.ETAT_id_etat = e.id_etat\n" +
            "JOIN utilisateur u ON co.UTILISATEUR_id_utilisateur = u.id_utilisateur WHERE co.id_commande = ?";
    private String sqlInsertCommande = "INSERT INTO commande (id_commande, date_heure_livraison, CLIENT_id_client, livraison, ETAT_id_etat, UTILISATEUR_id_utilisateur, prix_total, prix_total, est_paye) " +
            "VALUES (:idCommande, :dateCommande, :clientCommande, :livraisonCommande, :etatCommande, :utilisateurCommande, :prixTotalCommande, :estPayeCommande)";
    private String sqlUpdateCommande = "UPDATE commande SET id_commande = :idCommande, date_heure_livraison = :dateCommande, CLIENT_id_client = :clientCommande, livraison = :livraisonCommande, ETAT_id_etat =:etatCommande, UTILISATEUR_id_utilisateur = :utilisateurCommande, prix_total = :prixTotalCommande, est_paye = :estPayeCommande";
    private String sqlDeleteCommande = "DELETE FROM commande WHERE id_commande = :idCommande";

    @Override
    public List<Commande> findAllCommandes() {
        return jdbcTemplate.query(sqlSelectAllCommande, COMMANDE_ROW_MAPPER);
    }

    @Override
    public Commande findCommandeById(Long id) {
        return jdbcTemplate.queryForObject(sqlSelectCommandeById, COMMANDE_ROW_MAPPER, id);
    }

    @Override
    public void addCommandeToDB(Commande commande) {
        namedParameterJdbcTemplate.update(sqlInsertCommande, map(commande));
    }

    @Override
    public void updateCommandeToDB(Commande commande) {
        namedParameterJdbcTemplate.update(sqlUpdateCommande, map(commande));
    }

    @Override
    public void deleteCommandeToDB(Commande commande) {
        namedParameterJdbcTemplate.update(sqlDeleteCommande, map(commande));
    }
}
