package fr.eni.pizza.dao.impl;

import fr.eni.pizza.bo.Client;
import fr.eni.pizza.dao.IDAOClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Profile("client-mock")
@Repository
public class DAOClientMock implements IDAOClient {

    List<Client> clients = Arrays.asList(
            new Client(1L, "Mehdi", "Rochereau", "Rue de l'avenir", "85000", "La Roche-sur-Yon"),
            new Client(2L, "Jordi", "Mazureck", "Rue de la galère", "85500", "Les Herbiers"),
            new Client(3L, "Maxime", "Gilbert", "Rue du Front", "85300", "Soullans")
    );


    @Override
    public List<Client> findAllClients() {
        return clients;
    }


    @Override
    public Client findClientById(Long id_client) {

        Client clientTofound = clients.stream().filter(client -> client.getId_client() == id_client).findFirst().orElse(null);
        return clientTofound;

    }

    @Override
    public void addClientToDB(Client client) {
        clients.add(client);
    }

    @Override
    public void updateClientToDB(Client client) {

    }

    @Override
    public void deleteClientToDB(Client client) {

    }
}
