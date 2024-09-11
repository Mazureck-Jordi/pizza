package fr.eni.pizza.ihm.converter;

import fr.eni.pizza.bll.IClientManager;
import fr.eni.pizza.bo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToClientConverter implements Converter<String, Client> {
    @Autowired
    IClientManager clientManager;

    @Override
    public Client convert(String IdClient) {
        return clientManager.getClientByID(Long.parseLong(IdClient));
    }
}
