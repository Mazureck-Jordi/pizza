package fr.eni.pizza.bll;


import fr.eni.pizza.bo.Client;

import java.util.List;

public interface IClientManager {

    List<Client> getAllClients();

    Client getClientByID(Long id_client);

    void addClient(Client client);

    void updateClient(Client client);

    void deleteClient(Client client);


}
