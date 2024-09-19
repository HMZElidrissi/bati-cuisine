package model;

public class Client {
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;
    private boolean estProfessionnel;

    public Client(Long id, String nom, String adresse, String telephone, boolean estProfessionnel) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.estProfessionnel = estProfessionnel;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isEstProfessionnel() {
        return estProfessionnel;
    }

    public void setEstProfessionnel(boolean estProfessionnel) {
        this.estProfessionnel = estProfessionnel;
    }

    @Override
    public String toString() {
        return "\nNom: " + nom + "\nAdresse: " + adresse + "\nTéléphone: " + telephone;
    }
}
