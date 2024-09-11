package fr.eni.pizza.ihm.converter;

import fr.eni.pizza.bll.ICommandeManager;
import fr.eni.pizza.bll.IUtilisateurManager;
import fr.eni.pizza.bo.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCommandeConverter implements Converter<String, Commande> {
    @Autowired
    ICommandeManager commandeManager;

    @Override
    public Commande convert(String idCommande) {
        return commandeManager.getCommandeById(Long.parseLong(idCommande));
    }
}
