package fr.eni.pizza.bo;

public class Client {

    private Long id_client;
    private String prenom;
    private String nom;
    private String rue;
    private String code_postal;
    private String ville;

    public Client() {
        super();
    }

    public Client(String prenom, String nom, String rue, String code_postal, String ville) {
        this.prenom = prenom;
        this.nom = nom;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
    }

    public Client(long id_client, String prenom, String nom, String rue, String code_postal, String ville) {
        this.id_client = id_client;
        this.prenom = prenom;
        this.nom = nom;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id_client=" + id_client +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", rue='" + rue + '\'' +
                ", code_postal='" + code_postal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}

