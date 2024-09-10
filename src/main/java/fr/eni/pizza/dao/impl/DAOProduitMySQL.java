package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.bo.TypeProduit;
import fr.eni.pizza.dao.IDAOProduit;
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

@Profile("produit-mysql")
@Repository
public class DAOProduitMySQL implements IDAOProduit {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //Le code qui permet de savoir comment convertir/mapper un résultat en SQL en objet Film
    //Comment mapper un résultat SQL en Film
    static final RowMapper<Produit> PRODUIT_RAW_MAPPER = new RowMapper<Produit>() {
        @Override
        public Produit mapRow(ResultSet rs, int rowNum) throws SQLException {

            //On instancie un Produit à mapper
            Produit produit = new Produit();
            //On détermine les colonnes de la table produit qui seront mapper
            produit.setId_produit(rs.getLong("id_produit"));
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

            return produit;
        }
    };

   private MapSqlParameterSource map (Produit produit) {

       MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
       mapSqlParameterSource.addValue("idProduit", produit.getId_produit());
       mapSqlParameterSource.addValue("nomProduit", produit.getNom());
       mapSqlParameterSource.addValue("descriptionProduit", produit.getDescription());
       mapSqlParameterSource.addValue("prixProduit", produit.getPrix());
       mapSqlParameterSource.addValue("imageProduit", produit.getImage_url());
       mapSqlParameterSource.addValue("typeProduit", produit.getId_type_produit().getId_type_produit());

       return mapSqlParameterSource;
    }

    private String sqlSelectAllProduits = "SELECT p.id_produit, p.nom, p.description, p.prix, p.image_url, t.id_type_produit AS TYPE_PRODUIT_id_type_produit, t.libelle FROM produit p JOIN type_produit t ON p.TYPE_PRODUIT_id_type_produit = t.id_type_produit";
    private String sqlSelectProduitById = "SELECT p.id_produit, p.nom, p.description, p.prix, p.image_url, t.id_type_produit AS TYPE_PRODUIT_id_type_produit, t.libelle FROM produit p JOIN type_produit t ON p.TYPE_PRODUIT_id_type_produit = t.id_type_produit WHERE p.id_produit = ?";
    private String sqlInsertProduit = "INSERT INTO produit(id_produit, nom, description, prix, image_url, TYPE_PRODUIT_id_type_produit) VALUES (:idProduit, :nomProduit, :descriptionProduit, :prixProduit, :imageProduit, :typeProduit)";
    private String sqlUpdateProduit = "UPDATE produit SET nom = :nomProduit, description = :descriptionProduit, prix = :prixProduit, image_url = :imageProduit, TYPE_PRODUIT_id_type_produit = :typeProduit WHERE id_produit = :idProduit";
    private String sqlDeleteProduit = "DELETE FROM produit WHERE id_produit = :idProduit";

    @Override
    public List<Produit> findAllProduits() {
        return jdbcTemplate.query(sqlSelectAllProduits, PRODUIT_RAW_MAPPER);
    }

    @Override
    public Produit findProduitById(Long id_produit) {
        return jdbcTemplate.queryForObject(sqlSelectProduitById, PRODUIT_RAW_MAPPER, id_produit);
    }

    @Override
    public void addProduitToDB(Produit produit) {
        namedParameterJdbcTemplate.update(sqlInsertProduit, map(produit));
    }

    @Override
    public void updateProduitToDB(Produit produit) {
        namedParameterJdbcTemplate.update(sqlUpdateProduit, map(produit));
    }

    @Override
    public void deleteProduitToDB(Produit produit) {
        namedParameterJdbcTemplate.update(sqlDeleteProduit, map(produit));
    }
}
