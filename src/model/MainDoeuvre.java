package model;

public class MainDoeuvre extends Composant {
    private double tauxHoraire;
    private double heuresTravail;
    private double productiviteOuvrier;

    public MainDoeuvre(Long id, String nom, double tauxTVA, Projet projet, double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        super(id, nom, TypeComposant.MAIN_DOEUVRE, tauxTVA, projet);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public MainDoeuvre(String nom, double tauxTVA, Projet projet, double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        super(nom, TypeComposant.MAIN_DOEUVRE, tauxTVA, projet);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
    }

    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public double getHeuresTravail() {
        return heuresTravail;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }

    @Override
    public double calculerCout() {
        return tauxHoraire * heuresTravail * productiviteOuvrier;
    }
}
