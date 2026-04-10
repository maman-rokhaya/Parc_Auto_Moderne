package sn.rokhaya.l2gl.app.app;

import sn.rokhaya.l2gl.app.model.*;
import sn.rokhaya.l2gl.app.service.ParcAutoService;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        ParcAutoService service = new ParcAutoService();

        System.out.println("ETAPE 11");

        Vehicule v1 = new Vehicule(1L, "AA-001-BB", "Toyota", "Corolla", 2015, 120000, EtatVehicule.DISPONIBLE);
        Vehicule v2 = new Vehicule(2L, "CC-002-DD", "Renault", "Clio", 2019, 45000, EtatVehicule.PANNE);
        Vehicule v3 = new Vehicule(3L, "EE-003-FF", "Peugeot", "208", 2021, 30000, EtatVehicule.DISPONIBLE);
        Vehicule v4 = new Vehicule(4L, "GG-004-HH", "Honda", "Civic", 2012, 200000, EtatVehicule.DISPONIBLE);

        Conducteur c1 = new Conducteur(1L, "Aly Diallo", "B-12345");
        Conducteur c2 = new Conducteur(2L, "Amina Ndiaye", "A-67890");

        Entretien e1 = new Entretien(1L, "Vidange", 50000, 1L);
        Entretien e2 = new Entretien(2L, "Freinage", 80000, 2L);

        Location loc1 = new sn.rokhaya.l2gl.app.model.Location(1L, 3L, 1L, LocalDate.of(2025, 1, 10));

        List<Entite> entites = Arrays.asList(v1, v2, v3, v4, c1, c2, e1, e2);
        for (Entite e : entites) e.afficher();

        System.out.println("\n ETAPE 12");

        service.getRepoVehicule().create(v1);
        service.getRepoVehicule().create(v2);
        service.getRepoVehicule().create(v3);
        service.getRepoVehicule().create(v4);
        service.getRepoConducteur().create(c1);
        service.getRepoConducteur().create(c2);
        service.getRepoEntretien().create(e1);
        service.getRepoEntretien().create(e2);

        System.out.println("Vehicules en base : " + service.getRepoVehicule().findAll().size());
        Vehicule lu = service.getRepoVehicule().read(1L);
        System.out.println("Lu par id=1 : " + lu.getImmatriculation());

        try {
            service.getRepoVehicule().create(v1);
        } catch (IllegalStateException ex) {
            System.out.println("Erreur attendue (doublon) : " + ex.getMessage());
        }
        try {
            service.getRepoVehicule().read(99L);
        } catch (NoSuchElementException ex) {
            System.out.println("Erreur attendue (id inconnu) : " + ex.getMessage());
        }

        System.out.println("\n ETAPE 13 ");


        Vehicule vOpt = service.getRepoVehicule().readOpt(1L).orElse(null);
        System.out.println("orElse   -> " + (vOpt != null ? vOpt.getImmatriculation() : "absent"));

        service.getRepoVehicule().readOpt(2L).ifPresent(v -> System.out.println("ifPresent -> " + v.getImmatriculation()));

        try {
            service.getRepoVehicule().readOpt(999L)
                    .orElseThrow(() -> new NoSuchElementException("Vehicule 999 introuvable via Optional."));
        } catch (NoSuchElementException ex) {
            System.out.println("orElseThrow -> " + ex.getMessage());
        }

        System.out.println("\n ETAPE 14 ");

        List<LigneRapport> rapport = service.genererRapport();
        rapport.forEach(lr -> System.out.println("  " + lr));

        System.out.println("\n ETAPE 15 ");

        service.demarrerLocation(loc1);
        System.out.println("Apres demarrage : v3 etat=" + v3.getEtat());

        try {
            Location loc1bis = new Location(5L, 3L, 2L, LocalDate.of(2025, 2, 1));
            service.demarrerLocation(loc1bis);
        } catch (IllegalStateException ex) {
            System.out.println("Erreur attendue (vehicule loue) : " + ex.getMessage());
        }

        service.terminerLocation(1L, LocalDate.of(2025, 1, 20));
        System.out.println("Apres fin      : v3 etat=" + v3.getEtat());
        loc1.afficher();

        try {
            loc1.terminer(LocalDate.of(2025, 2, 1));
        } catch (IllegalStateException ex) {
            System.out.println("Erreur attendue (deja terminee) : " + ex.getMessage());
        }

        System.out.println("\n--- Rapport final ---");
        service.genererRapport().forEach(lr -> System.out.println("  " + lr));

        int seuilKm = 100000;
        int seuilAnnee = 2016;
        Test<Vehicule> regleRevision = v ->
                v.getKilometrage() > seuilKm || v.getAnnee() < seuilAnnee;

        System.out.println("\nVehicules a reviser (km>" + seuilKm + " OU annee<" + seuilAnnee + ") ");
        service.aReviser(regleRevision).forEach(Vehicule::afficher);


    }
}