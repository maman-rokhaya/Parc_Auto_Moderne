package sn.rokhaya.l2gl.app.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Location extends Entite {

    private final Long vehiculeId;
    private final Long conducteurId;
    private final LocalDate dateDebut;
    private LocalDate dateFin;

    public Location(Long id, Long vehiculeId, Long conducteurId, LocalDate dateDebut) {
        super(id);
        if (vehiculeId == null)   throw new IllegalArgumentException("vehiculeId ne peut pas etre null.");
        if (conducteurId == null) throw new IllegalArgumentException("conducteurId ne peut pas etre null.");
        if (dateDebut == null)    throw new IllegalArgumentException("La date de debut ne peut pas etre null.");
        this.vehiculeId   = vehiculeId;
        this.conducteurId = conducteurId;
        this.dateDebut    = dateDebut;
        this.dateFin      = null;
    }

    public Long       getVehiculeId()   { return vehiculeId; }
    public Long       getConducteurId() { return conducteurId; }
    public LocalDate  getDateDebut()    { return dateDebut; }
    public LocalDate  getDateFin()      { return dateFin; }
    public boolean    estTerminee()     { return dateFin != null; }

    public void terminer(LocalDate fin) {
        if (fin == null)
            throw new IllegalArgumentException("La date de fin ne peut pas etre null.");
        if (!fin.isAfter(dateDebut))
            throw new IllegalArgumentException("La date de fin (" + fin + ") doit etre apres la date de debut (" + dateDebut + ").");
        if (this.dateFin != null)
            throw new IllegalStateException("La location #" + getId() + " est deja terminee.");
        this.dateFin = fin;
    }

    public long dureeJours() {
        LocalDate fin = (dateFin != null) ? dateFin : LocalDate.now();
        return ChronoUnit.DAYS.between(dateDebut, fin);
    }

    @Override
    public void afficher() {
        System.out.println("Location #" + getId() + " vehicule" + vehiculeId
            + " conducteur#=" + conducteurId
            + " debut=" + dateDebut
            + "  fin=" + (dateFin != null ? dateFin : "en cours")
            + "  duree=" + dureeJours() + "j");
    }
}
