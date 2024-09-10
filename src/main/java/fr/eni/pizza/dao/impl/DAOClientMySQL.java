package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Client;
import fr.eni.pizza.bo.Produit;
import fr.eni.pizza.dao.IDAOClient;
import fr.eni.pizza.ihm.ClientController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Profile("client-mysql")
@Repository
public class DAOClientMySQL implements IDAOClient {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    static final RowMapper<Client>CLIENT_ROW_MAPPER = new RowMapper<Client>() {

        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setId_client(rs.getInt("id_client"));
            client.setPrenom(rs.getString("prenom"));
            client.setNom(rs.getString("nom"));
            client.setRue(rs.getString("rue"));
            client.setCode_postal(rs.getString("code_postal"));
            client.setVille(rs.getString("ville"));

            return client;
        }
    };

    private String sqlSelectAllClients = "SELECT c.id_client, c.prenom, c.nom,c.rue, c.code_postal, c.ville FROM client c";
    private String sqlSelectClientById = "SELECT c.id_client, c.prenom, c.nom,c.rue, c.code_postal, c.ville FROM client c WHERE c.id_client = ?";


    @Override
    public List<Client> findAllClients() {

        return jdbcTemplate.query(sqlSelectAllClients, CLIENT_ROW_MAPPER);
    }

    @Override
    public Client findClientById(Long id_client) {

        return jdbcTemplate.queryForObject(sqlSelectClientById, CLIENT_ROW_MAPPER, id_client);
    }

    @Override
    public void addClientToDB(Client client) {

    }

    @Override
    public void updateClientToDB(Client client) {

    }

    @Override
    public void deleteClientToDB(Client client) {

    }
}
