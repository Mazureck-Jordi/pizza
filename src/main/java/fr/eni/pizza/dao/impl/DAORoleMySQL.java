package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Role;
import fr.eni.pizza.dao.IDAORole;
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
public class DAORoleMySQL implements IDAORole {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    static final RowMapper<Role> ROLE_ROW_MAPPER = new RowMapper<Role>() {
        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Role role = new Role();
            role.setId_role(rs.getLong("id_role"));
            role.setLibelle(rs.getString("libelle"));

            return role;
        }
    };

    private MapSqlParameterSource map(Role role) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id_role", role.getId_role());
        mapSqlParameterSource.addValue("libelle", role.getLibelle());

        return mapSqlParameterSource;
    }

    private String getSqlSelectRoleByIdUtilisateur = "SELECT r.id_role, r.libelle FROM role r JOIN role_utilisateur ru ON r.id_role = ru.ROLE_id_role WHERE ru.UTILISATEUR_id_utilisateur = :idUtilisateur";
    private String sqlSelectAllRoles = " SELECT r.id_role, r.libelle FROM role r";
    private String sqlSelectRoleById = "SELECT r.id_role, r.libelle FROM role r WHERE r.id_role = ?";
    private String sqlInsertRole = "INSERT INTO role (id_role,libelle) VALUES ( :id_role, :libelle ) ";
    private String sqlUpdateRole = "UPDATE role SET libelle = :libelle WHERE id_role = :id_role";
    private String sqlDeleteRole = "DELETE FROM role WHERE id_role = :id_role";

    @Override
    public List<Role> findRolesByIdUtilisateur(long idUtilisateur) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("idUtilisateur", idUtilisateur);
        return namedParameterJdbcTemplate.query(getSqlSelectRoleByIdUtilisateur, map, ROLE_ROW_MAPPER);
    }

    @Override
    public List<Role> findAllRoles() {
        return jdbcTemplate.query(sqlSelectAllRoles, ROLE_ROW_MAPPER);
    }

    @Override
    public Role findRoleById(Long id_role) {
        return jdbcTemplate.queryForObject(sqlSelectRoleById, ROLE_ROW_MAPPER, id_role);
    }

    @Override
    public void addRoleToDB(Role role) {

        namedParameterJdbcTemplate.update(sqlInsertRole, map(role));
    }

    @Override
    public void updateRoleToDB(Role role) {
        namedParameterJdbcTemplate.update(sqlUpdateRole, map(role));
    }

    @Override
    public void deleteRoleToDB(Role role) {
        namedParameterJdbcTemplate.update(sqlDeleteRole, map(role));

    }
}
