package fr.eni.pizza.bll.impl;

import fr.eni.pizza.bll.IClientManager;
import fr.eni.pizza.bo.Client;
import fr.eni.pizza.dao.IDAOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientManager implements IClientManager {

    @Autowired
    IDAOClient daoClient;


    @Override
    public List<Client> getAllClients() {
        return daoClient.findAllClients();
    }

    @Override
    public Client getClientByID(Long id_client) {
        return daoClient.findClientById(id_client);
    }

    @Override
    public void addClient(Client client) {
        daoClient.addClientToDB(client);
    }

    @Override
    public void updateClient(Client client) {
        daoClient.updateClientToDB(client);
    }

    @Override
    public void deleteClient(Client client) {
        daoClient.deleteClientToDB(client);
    }
}
