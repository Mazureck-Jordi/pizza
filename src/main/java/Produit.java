public class Produit {

    private Long id_produit
    private String nom;
    private String description;
    private Double prix;
    private String image_url;
    private TypeProduit id_type_produit;

    public Produit() {
        super();
    }

    public Produit(String nom, String description, Double prix, String image_url, TypeProduit id_type_produit) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image_url = image_url;
        this.id_type_produit = id_type_produit;
    }

    public Produit(Long id_produit, String nom, String description, Double prix, String image_url, TypeProduit id_type_produit) {
        this.id_produit = id_produit;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image_url = image_url;
        this.id_type_produit = id_type_produit;
    }

    public Long getId_produit() {
        return id_produit;
    }

    public void setId_produit(Long id_produit) {
        this.id_produit = id_produit;
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

    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + id_produit +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", image_url='" + image_url + '\'' +
                ", id_type_produit=" + id_type_produit +
                '}';
    }
}

