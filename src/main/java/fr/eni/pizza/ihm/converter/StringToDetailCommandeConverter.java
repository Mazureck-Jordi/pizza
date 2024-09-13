package fr.eni.pizza.ihm.converter;

import fr.eni.pizza.bll.impl.DetailCommandeManager;
import fr.eni.pizza.bo.DetailCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDetailCommandeConverter implements Converter<String, DetailCommande> {
   @Autowired
    DetailCommandeManager detailCommandeManager;

    @Override
    public DetailCommande convert(String idDetailsCommande) {
        return detailCommandeManager.getDetailCommandeByIdCommande(Long.parseLong(idDetailsCommande));
    }
}
