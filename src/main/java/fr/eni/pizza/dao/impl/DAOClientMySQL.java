package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Client;
import fr.eni.pizza.dao.IDAOClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("mysql")
@Repository
public class DAOClientMySQL implements IDAOClient {
    @Override
    public List<Client> findAllClients() {
        return List.of();
    }

    @Override
    public Client findClientById(Long id_client) {
        return null;
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
