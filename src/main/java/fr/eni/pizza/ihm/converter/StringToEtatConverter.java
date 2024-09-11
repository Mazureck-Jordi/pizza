package fr.eni.pizza.ihm.converter;

import fr.eni.pizza.bll.ICommandeManager;
import fr.eni.pizza.bo.Etat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEtatConverter implements Converter<String, Etat> {
    @Autowired
    ICommandeManager commandeManager;

    @Override
    public Etat convert(String IdEtat) {
        return commandeManager.getEtatById(Long.parseLong(IdEtat));
    }

}
