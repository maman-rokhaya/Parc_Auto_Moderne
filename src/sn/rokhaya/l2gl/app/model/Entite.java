package sn.rokhaya.l2gl.app.model;

public abstract class Entite implements Identifiable {

    private final Long id;

    public Entite(Long id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("L'id doit être un entier strictement positif, reçu : " + id);
        this.id = id;
    }

    @Override
    public Long getId() { return id; }

    public abstract void afficher();


}
