package fr.eni.pizza.ihm.converter;

import fr.eni.pizza.bll.IClientManager;
import fr.eni.pizza.bll.IUtilisateurManager;
import fr.eni.pizza.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUtilisateurConverter implements Converter<String, Utilisateur> {
    @Autowired
    IUtilisateurManager utilisateurManager;

    @Override
    public Utilisateur convert(String IdUtilisateur) {
        return utilisateurManager.getUtilisateurById(Long.parseLong(IdUtilisateur));

    }
}
