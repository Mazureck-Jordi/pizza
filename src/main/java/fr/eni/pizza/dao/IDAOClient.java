package fr.eni.pizza.dao;

import fr.eni.pizza.bo.Client;

import java.util.List;

public interface IDAOClient {

    List<Client> findAllClients();

    Client findClientById(Long id_client);

    void addClientToDB(Client client);

    void updateClientToDB(Client client);
    void deleteClientToDB(Client client);
}
