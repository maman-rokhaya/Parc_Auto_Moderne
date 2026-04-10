package sn.rokhaya.l2gl.app.model;

public class Vehicule extends Entite {

    private final String immatriculation;
    private final String marque;
    private final String modele;
    private final int annee;
    private int kilometrage;
    private EtatVehicule etat;

    public Vehicule(Long id, String immatriculation, String marque, String modele, int annee, int kilometrage, EtatVehicule etat) {
        super(id);
        if (immatriculation == null || immatriculation.isBlank())
            throw new IllegalArgumentException("L'immatriculation ne peut pas être vide.");
        if (marque == null || marque.isBlank())
            throw new IllegalArgumentException("La marque ne peut pas être vide.");
        if (annee < 1900 || annee > 2100)
            throw new IllegalArgumentException("Annee invalide : " + annee);
        if (kilometrage < 0)
            throw new IllegalArgumentException("Le kilometrage ne peut pas etre negatif : " + kilometrage);
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.kilometrage = kilometrage;
        this.etat = etat;
    }

    public String getImmatriculation() { return immatriculation; }
    public String getMarque()          { return marque; }
    public String getModele()          { return modele; }
    public int    getAnnee()           { return annee; }
    public int    getKilometrage()     { return kilometrage; }
    public EtatVehicule getEtat()      { return etat; }

    public void setKilometrage(int km) {
        if (km < this.kilometrage)
            throw new IllegalArgumentException("Le kilometrage ne peut pas diminuer.");
        this.kilometrage = km;
    }
    public void setEtat(EtatVehicule etat) {
        if (etat == null) throw new IllegalArgumentException("L'etat ne peut pas etre null.");
        this.etat = etat;
    }

    @Override
    public void afficher() {
        System.out.println("Vehicule #" + getId() + " " + marque + " " + modele
            + "  immat=" + immatriculation
            + "  annee=" + annee
            + "  km=" + kilometrage
            + "  etat=" + etat);
    }
}
