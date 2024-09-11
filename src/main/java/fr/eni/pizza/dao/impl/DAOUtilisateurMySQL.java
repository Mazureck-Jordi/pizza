package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.bo.Utilisateur;
import fr.eni.pizza.dao.IDAOUtilisateur;
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

@Profile("utilisateur-mysql")
@Repository
public class DAOUtilisateurMySQL implements IDAOUtilisateur {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final RowMapper<Utilisateur> UTILISATEUR_ROW_MAPPER = new RowMapper<Utilisateur>() {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur utilisateur = new Utilisateur();

            utilisateur.setId_utilisateur(rs.getLong("id_utilisateur"));
            utilisateur.setNom(rs.getString("nom"));
            utilisateur.setPrenom(rs.getString("prenom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setMot_de_passe(rs.getString("mot_de_passe"));

            Commande commande = new Commande();
            commande.setId_commande(rs.getLong("id_commande"));
            commande.setDate_heure_livraison(rs.getDate("date_heure_livraison").toLocalDate());
            commande.setLivraison(rs.getInt("livraison"));
            commande.setPrix_total(rs.getDouble("prix_total"));
            commande.setEst_paye(rs.getInt("est_paye"));

            utilisateur.setId_commande(commande);


            return utilisateur;
        }
    };

    private MapSqlParameterSource map(Utilisateur utilisateur) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id_utilisateur", utilisateur.getId_utilisateur());
        mapSqlParameterSource.addValue("nomUtilisateur", utilisateur.getNom());
        mapSqlParameterSource.addValue("prenomUtilisateur", utilisateur.getPrenom());
        mapSqlParameterSource.addValue("emailUtilisateur", utilisateur.getEmail());
        mapSqlParameterSource.addValue("motdepasseUtilisateur", Utilisateur.getMot_de_passe());
        mapSqlParameterSource.addValue("commandeUtilisateur", utilisateur.getId_commande().getId_commande());
        return mapSqlParameterSource;

    }

    private String sqlSelectAllUtilisateurs = "SELECT u.id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe, u.COMMANDE_id_commande,\n" +
            "c.id_commande, c.date_heure_livraison, c.CLIENT_id_client, c.livraison, c.ETAT_id_etat, c.UTILISATEUR_id_utilisateur, c.prix_total, c.est_paye\n" +
            "FROM utilisateur u JOIN commande c ON u.COMMANDE_id_commande = c.id_commande";
    private String sqlSelectUtilisateurById = "SELECT u.id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe, u.COMMANDE_id_commande,\n" +
            "c.id_commande, c.date_heure_livraison, c.CLIENT_id_client, c.livraison, c.ETAT_id_etat, c.UTILISATEUR_id_utilisateur, c.prix_total, c.est_paye\n" +
            "FROM utilisateur u JOIN commande c ON u.COMMANDE_id_commande = c.id_commande WHERE u.id_utilisateur = ?";
    private String sqlInsertUtilisateur = "INSERT INTO utilisateur(id_utilisateur, nom, prenom, email, mot_de_passe, COMMANDE_id_commande) " +
            "VALUES ( :id_Utilisateur, :nomUtilisateur, :prenomUtilisateur, :emailUtilisateur, :motdepasseUtilisateur, :commandeUtilisateur)";
    private String sqlUpdateUtilisateur = "UPDATE utilisateur SET nom = :nomUtilisateur, prenom = :prenomUtilisateur, email = :emailUtilisateur, mot_de_passe = :motdepasseUtilisateur, COMMANDE_id_commande = :commandeUtilisateur WHERE id_utilisateur = :idUtilisateur";
    private String sqlDeleteUtilisateur = "DELETE FROM utilisateur WHERE id_utilisateur = :id_utilisateur";

    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        return jdbcTemplate.query(sqlSelectAllUtilisateurs, UTILISATEUR_ROW_MAPPER);
    }

    @Override
    public Utilisateur findUtilisateurById(Long id_utilisateur) {
        return jdbcTemplate.queryForObject(sqlSelectUtilisateurById, UTILISATEUR_ROW_MAPPER, id_utilisateur);
    }

    @Override
    public void addUtilisateurToDB(Utilisateur utilisateur) {
        namedParameterJdbcTemplate.update(sqlInsertUtilisateur, map(utilisateur));

    }

    @Override
    public void updateUtilisateurToDB(Utilisateur utilisateur) {
        namedParameterJdbcTemplate.update(sqlUpdateUtilisateur, map(utilisateur));
    }

    @Override
    public void deleteUtilisateurToDB(Utilisateur utilisateur) {
        namedParameterJdbcTemplate.update(sqlDeleteUtilisateur, map(utilisateur));

    }
}
