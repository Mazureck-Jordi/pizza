package fr.eni.pizza.bo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DetailCommande {

    @NotNull(message = "La quantité doit être renseignée")
    @Positive(message = "La quantité doit être supérieur à 0")
    private int quantite;
    private Commande id_commande;
    private Produit id_produit;

    public DetailCommande() {
    }

    public DetailCommande(int quantite, Commande id_commande, Produit id_produit) {
        this.quantite = quantite;
        this.id_commande = id_commande;
        this.id_produit = id_produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Commande getId_commande() {
        return id_commande;
    }

    public void setId_commande(Commande id_commande) {
        this.id_commande = id_commande;
    }

    public Produit getId_produit() {
        return id_produit;
    }

    public void setId_produit(Produit id_produit) {
        this.id_produit = id_produit;
    }

    @Override
    public String toString() {
        return "DetailCommande{" +
                "quantite=" + quantite +
                ", id_commande=" + id_commande +
                ", id_produit=" + id_produit +
                '}';
    }
}
