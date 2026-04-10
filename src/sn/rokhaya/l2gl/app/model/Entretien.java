package sn.rokhaya.l2gl.app.model;

public class Entretien extends Entite {

    private final String description;
    private final int cout;
    private final Long vehiculeId;

    public Entretien(Long id, String description, int cout, Long vehiculeId) {
        super(id);
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("La description de l'entretien ne peut pas etre vide.");
        if (cout < 0)
            throw new IllegalArgumentException("Le cout de l'entretien ne peut pas etre negatif : " + cout);
        this.description = description;
        this.cout = cout;
        this.vehiculeId = vehiculeId;
    }

    public String getDescription() { return description; }
    public int    getCout()        { return cout; }
    public Long   getVehiculeId()  { return vehiculeId; }

    @Override
    public void afficher() {
        System.out.println("Entretien #" + getId() + " " + description
            + "  cout=" + cout + " FCFA  vehicule=" + vehiculeId);
    }
}
