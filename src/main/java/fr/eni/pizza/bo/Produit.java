package fr.eni.pizza.bo;

import jakarta.validation.constraints.*;

public class Produit {

    private Long idProduit;
    @NotBlank(message = "Le prénom doit être renseigné")
    private String nom;
    @NotBlank(message = "Le nom doit être renseigné")
    private String description;
    @NotNull(message = "Le prix doit être renseigné")
    @PositiveOrZero(message = "Le prix doit être supérieur ou égal à 0.0 €")
    @DecimalMin(value = "0.0", message = "Le prix doit être supérieur ou égal à 0.0 €")
    private Double prix;
    @NotBlank(message = "L''image doit être renseignée")
    private String image_url;
    private TypeProduit id_type_produit;
    private int quantite;
    private double prixTotal;

    public Produit() {
        super();
    }

    public Produit(String nom, String description, Double prix, String image_url, TypeProduit id_type_produit, int quantite, double prixTotal) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image_url = image_url;
        this.id_type_produit = id_type_produit;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
    }

    public Produit(Long idProduit, String nom, String description, Double prix, String image_url, int quantite, double prixTotal) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image_url = image_url;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
    }

    public Produit(Long idProduit, String nom, String description, Double prix, String image_url, TypeProduit id_type_produit, int quantite, double prixTotal) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image_url = image_url;
        this.id_type_produit = id_type_produit;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public TypeProduit getId_type_produit() {
        return id_type_produit;
    }

    public void setId_type_produit(TypeProduit id_type_produit) {
        this.id_type_produit = id_type_produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + idProduit +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", image_url='" + image_url + '\'' +
                ", id_type_produit=" + id_type_produit +
                ", quantite=" + quantite +
                ", prixTotal=" + prixTotal +
                '}';
    }
}

