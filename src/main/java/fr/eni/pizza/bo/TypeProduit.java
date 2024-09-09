package fr.eni.pizza.bo;

public class TypeProduit {

    private Long id_type_produit;
    private String libelle;

    public TypeProduit() {
    }

    public TypeProduit(String libelle) {
        this.libelle = libelle;
    }

    public TypeProduit(Long id_type_produit, String libelle) {
        this.id_type_produit = id_type_produit;
        this.libelle = libelle;
    }

    public Long getId_type_produit() {
        return id_type_produit;
    }

    public void setId_type_produit(Long id_type_produit) {
        this.id_type_produit = id_type_produit;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "TypeProduit{" +
                "id_type_produit=" + id_type_produit +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
