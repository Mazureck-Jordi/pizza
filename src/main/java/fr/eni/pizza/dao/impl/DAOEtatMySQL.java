package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Etat;
import fr.eni.pizza.dao.IDAOEtat;
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
public class DAOEtatMySQL implements IDAOEtat {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final RowMapper<Etat> ETAT_ROW_MAPPER = new RowMapper<Etat>() {
        @Override
        public Etat mapRow(ResultSet rs, int rowNum) throws SQLException {
            Etat etat = new Etat();
            etat.setId_etat(rs.getLong("id_etat"));
            etat.setLibelle(rs.getString("libelle"));

            return etat;
        }
    };

    private MapSqlParameterSource map(Etat etat) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id_etat", etat.getId_etat());
        mapSqlParameterSource.addValue("libelle", etat.getLibelle());

        return mapSqlParameterSource;
    }

    String sqlSelectAllClients = "SELECT id_etat, libelle FROM etat";
    String sqlSelectClientById = "SELECT id_etat, libelle FROM etat WHERE id_etat = ?";
    String sqlInsertEtat = "INSERT INTO etat (id_etat, libelle) VALUES (:idEtat, :libelleEtat)";
    String sqlUpdateEtat = "UPDATE etat SET (libelle = :libelleEtat) WHERE id_etat = :idEtat)";
    String sqlDeleteEtat = "DELETE FROM etat WHERE id_etat = :idEtat";

    @Override
    public List<Etat> findAllEtats() {
        return jdbcTemplate.query(sqlSelectAllClients, ETAT_ROW_MAPPER);
    }

    @Override
    public Etat findEtatById(Long id) {
        return jdbcTemplate.queryForObject(sqlSelectClientById, ETAT_ROW_MAPPER, id);
    }

    @Override
    public void addEtatToDB(Etat etat) {
        namedParameterJdbcTemplate.update(sqlInsertEtat, map(etat));
    }

    @Override
    public void updateEtatToDB(Etat etat) {
        namedParameterJdbcTemplate.update(sqlUpdateEtat, map(etat));
    }

    @Override
    public void deleteEtatToDB(Etat etat) {
        namedParameterJdbcTemplate.update(sqlDeleteEtat, map(etat));
    }
}
