package fr.eni.pizza.ihm.converter;

import fr.eni.pizza.bll.IUtilisateurManager;
import fr.eni.pizza.bo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    @Autowired
    IUtilisateurManager utilisateurManager;

    @Override
    public Role convert(String idRole) {
        return utilisateurManager.getRoleById(Long.parseLong(idRole));
    }
}
