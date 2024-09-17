package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Commande;
import fr.eni.pizza.bo.Role;
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
import java.util.Map;

@Profile("mysql")
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
            commande.setDate_heure_livraison(rs.getTimestamp("date_heure_livraison").toLocalDateTime());
            commande.setLivraison(rs.getInt("livraison"));
            commande.setPrix_total(rs.getDouble("prix_total"));
            commande.setEst_paye(rs.getInt("est_paye"));

            utilisateur.setId_commande(commande);


            return utilisateur;
        }
    };


    static final RowMapper<Utilisateur> SIMPLE_UTILISATEUR_ROW_MAPPER = new RowMapper<Utilisateur>() {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur utilisateur = new Utilisateur();

            utilisateur.setId_utilisateur(rs.getLong("UTILISATEUR_id_utilisateur"));
            utilisateur.setNom(rs.getString("nom"));
            utilisateur.setPrenom(rs.getString("prenom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setMot_de_passe(rs.getString("mot_de_passe"));

            Role role = new Role();
            role.setId_role(rs.getLong("ROLE_id_role"));
            role.setLibelle(rs.getString("libelle"));

            utilisateur.setRole(role);

            return utilisateur;
        }
    };

    private MapSqlParameterSource map(Utilisateur utilisateur) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("idUtilisateur", utilisateur.getId_utilisateur());
        mapSqlParameterSource.addValue("nomUtilisateur", utilisateur.getNom());
        mapSqlParameterSource.addValue("prenomUtilisateur", utilisateur.getPrenom());
        mapSqlParameterSource.addValue("emailUtilisateur", utilisateur.getEmail());
        mapSqlParameterSource.addValue("motdepasseUtilisateur", utilisateur.getMot_de_passe());
        mapSqlParameterSource.addValue("commandeUtilisateur", utilisateur.getId_commande().getId_commande());
        return mapSqlParameterSource;
    }


    private String sqlSelectAll = "SELECT u.id_utilisateur AS UTILISATEUR_id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe, r.id_role AS ROLE_id_role, r.libelle FROM role_utilisateur ru JOIN utilisateur u ON ru.UTILISATEUR_id_utilisateur = u.id_utilisateur JOIN role r ON ru.ROLE_id_role = r.id_role";
    private String sqlSelectByIdUtilisateur = "SELECT u.id_utilisateur AS UTILISATEUR_id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe, r.id_role AS ROLE_id_role, r.libelle FROM role_utilisateur ru JOIN utilisateur u ON ru.UTILISATEUR_id_utilisateur = u.id_utilisateur JOIN role r ON ru.ROLE_id_role = r.id_role WHERE ru.UTILISATEUR_id_utilisateur = ?";
    private String sqlSelectAllUtilisateurs = "SELECT u.id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe, u.COMMANDE_id_commande,\n" +
            "c.id_commande, c.date_heure_livraison, c.CLIENT_id_client, c.livraison, c.ETAT_id_etat, c.UTILISATEUR_id_utilisateur, c.prix_total, c.est_paye\n" +
            "FROM utilisateur u JOIN commande c ON u.COMMANDE_id_commande = c.id_commande";
    private String sqlSelectUtilisateurById = "SELECT u.id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe, u.COMMANDE_id_commande,\n" +
            "c.id_commande, c.date_heure_livraison, c.CLIENT_id_client, c.livraison, c.ETAT_id_etat, c.UTILISATEUR_id_utilisateur, c.prix_total, c.est_paye\n" +
            "FROM utilisateur u JOIN commande c ON u.COMMANDE_id_commande = c.id_commande WHERE u.id_utilisateur = ?";
    private String sqlSelectUtilisateurByEmail = "SELECT u.id_utilisateur AS UTILISATEUR_id_utilisateur, u.nom, u.prenom, u.email, u.mot_de_passe, r.id_role AS ROLE_id_role, r.libelle FROM role_utilisateur ru JOIN utilisateur u ON ru.UTILISATEUR_id_utilisateur = u.id_utilisateur JOIN role r ON ru.ROLE_id_role = r.id_role WHERE u.email = ?";
    private String sqlInsertUtilisateur = "INSERT INTO utilisateur(id_utilisateur, nom, prenom, email, mot_de_passe, COMMANDE_id_commande) " +
            "VALUES ( :idUtilisateur, :nomUtilisateur, :prenomUtilisateur, :emailUtilisateur, :motdepasseUtilisateur, null)";
    private String sqlUpdateUtilisateur = "UPDATE utilisateur SET nom = :nomUtilisateur, prenom = :prenomUtilisateur, email = :emailUtilisateur, mot_de_passe = :motdepasseUtilisateur, COMMANDE_id_commande = :commandeUtilisateur WHERE id_utilisateur = :idUtilisateur";
    private String sqlDeleteUtilisateur = "DELETE FROM utilisateur WHERE id_utilisateur = :idUtilisateur";


    @Override
    public List<Utilisateur> findAll() {

        return jdbcTemplate.query(sqlSelectAll, SIMPLE_UTILISATEUR_ROW_MAPPER);
    }

    @Override
    public Utilisateur findById(Long id) {
        return jdbcTemplate.queryForObject(sqlSelectByIdUtilisateur, SIMPLE_UTILISATEUR_ROW_MAPPER, id);
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return jdbcTemplate.queryForObject(sqlSelectUtilisateurByEmail, SIMPLE_UTILISATEUR_ROW_MAPPER, email);
    }

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
        Long utilisateurId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", new MapSqlParameterSource(), Long.class);
        utilisateur.setId_utilisateur(utilisateurId);
        updateRoleUtilisateurs(utilisateur);
    }

    @Override
    public void updateUtilisateurToDB(Utilisateur utilisateur) {
        namedParameterJdbcTemplate.update(sqlUpdateUtilisateur, map(utilisateur));
        updateRoleUtilisateurs(utilisateur);
    }

    @Override
    public void deleteUtilisateurToDB(Utilisateur utilisateur) {
        namedParameterJdbcTemplate.update(sqlDeleteUtilisateur, map(utilisateur));

    }

    private void updateRoleUtilisateurs(Utilisateur utilisateur) {
        String sqlInsertRoleUtilisateur = "INSERT INTO role_utilisateur (UTILISATEUR_id_utilisateur, ROLE_id_role) VALUES ( :idUtilisateur, :idRole)";
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("idUtilisateur", utilisateur.getId_utilisateur());
        map.addValue("idRole", utilisateur.getRole().getId_role());
        namedParameterJdbcTemplate.update(sqlInsertRoleUtilisateur, map);
    }
}


