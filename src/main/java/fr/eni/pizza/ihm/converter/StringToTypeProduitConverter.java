package fr.eni.pizza.ihm.converter;

import fr.eni.pizza.bll.IProduitManager;
import fr.eni.pizza.bo.TypeProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTypeProduitConverter implements Converter<String, TypeProduit> {

   @Autowired
    IProduitManager produitManager;

    @Override
    public TypeProduit convert(String idTypeProduit) {

        return produitManager.getTypeProduitById(Long.parseLong(idTypeProduit));
    }
}
