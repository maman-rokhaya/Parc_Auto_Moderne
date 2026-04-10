package sn.rokhaya.l2gl.app.model;

public class Conducteur extends Entite {

    private final String nom;
    private final String numeroPermis;

    public Conducteur(Long id, String nom, String numeroPermis) {
        super(id);
        if (nom == null || nom.isBlank())
            throw new IllegalArgumentException("Le nom du conducteur ne peut pas etre vide.");
        if (numeroPermis == null || numeroPermis.isBlank())
            throw new IllegalArgumentException("Le numero de permis ne peut pas etre vide.");
        this.nom = nom;
        this.numeroPermis = numeroPermis;
    }

    public String getNom()          { return nom; }
    public String getNumeroPermis() { return numeroPermis; }

    @Override
    public void afficher() {
        System.out.println("Conducteur " + getId() + "] " + nom + "  permis=" + numeroPermis);
    }
}
