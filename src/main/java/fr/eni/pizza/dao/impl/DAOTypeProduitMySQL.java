package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.TypeProduit;
import fr.eni.pizza.dao.IDAOTypeProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("type-produit-mysql")
@Repository
public class DAOTypeProduitMySQL implements IDAOTypeProduit {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String sqlSelectAllTypeProduits = "SELECT id_type_produit, libelle FROM type_produit";
    private String sqlSelectTypeProduitById = "SELECT id_type_produit, libelle FROM type_produit WHERE id_type_produit = ?";

    @Override
    public List<TypeProduit> findAllTypeProduits() {
        return jdbcTemplate.query(sqlSelectAllTypeProduits, new BeanPropertyRowMapper<>(TypeProduit.class));
    }

    @Override
    public TypeProduit findTypeProduitByID(Long id) {
        return jdbcTemplate.queryForObject(sqlSelectTypeProduitById, new BeanPropertyRowMapper<>(TypeProduit.class), id);
    }
}
