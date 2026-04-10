package sn.rokhaya.l2gl.app.service;

import sn.rokhaya.l2gl.app.model.*;
import sn.rokhaya.l2gl.app.repo.InMemoryCrud;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ParcAutoService {

    private final InMemoryCrud<Vehicule>   repoVehicule   = new InMemoryCrud<>();
    private final InMemoryCrud<Conducteur> repoConducteur = new InMemoryCrud<>();
    private final InMemoryCrud<Entretien>  repoEntretien  = new InMemoryCrud<>();
    private final InMemoryCrud<Location>   repoLocation   = new InMemoryCrud<>();

    public InMemoryCrud<Vehicule>   getRepoVehicule()   { return repoVehicule; }
    public InMemoryCrud<Conducteur> getRepoConducteur() { return repoConducteur; }
    public InMemoryCrud<Entretien>  getRepoEntretien()  { return repoEntretien; }
    public InMemoryCrud<Location>   getRepoLocation()   { return repoLocation; }

    public void demarrerLocation(Location loc) {
        Vehicule v = repoVehicule.read(loc.getVehiculeId());
        if (v.getEtat() != EtatVehicule.DISPONIBLE)
            throw new IllegalStateException("Vehicule #" + v.getId() + " non disponible (etat=" + v.getEtat() + ").");
        v.setEtat(EtatVehicule.LOUE);
        repoLocation.create(loc);
    }

    public void terminerLocation(Long locationId, LocalDate dateFin) {
        Location loc = repoLocation.read(locationId);
        loc.terminer(dateFin);
        Vehicule v = repoVehicule.read(loc.getVehiculeId());
        v.setEtat(EtatVehicule.DISPONIBLE);
    }

    public List<LigneRapport> genererRapport() {
        return repoVehicule.findAll().stream()
            .map(v -> new LigneRapport(v.getImmatriculation(), v.getMarque(), v.getEtat(), v.getKilometrage()))
            .collect(Collectors.toList());
    }

    public List<Vehicule> aReviser(Test<Vehicule> regle) {
        return repoVehicule.findAll().stream()
            .filter(regle::tester)
            .collect(Collectors.toList());
    }

    public List<Vehicule> filtrerVehicules(List<Vehicule> src, Test<Vehicule> regle) {
        List<Vehicule> res = new ArrayList<>();
        for (Vehicule v : src) if (regle.tester(v)) res.add(v);
        return res;
    }

    public List<String> mapperVehicules(List<Vehicule> src, Transformation<Vehicule, String> f) {
        List<String> res = new ArrayList<>();
        for (Vehicule v : src) res.add(f.transformer(v));
        return res;
    }

    public void appliquerSurVehicules(List<Vehicule> src, Action<Vehicule> action) {
        for (Vehicule v : src) action.executer(v);
    }

    public void trierVehicules(List<Vehicule> src, Comparaison<Vehicule> cmp) {
        for (int i = 0; i < src.size() - 1; i++)
            for (int j = i + 1; j < src.size(); j++)
                if (cmp.comparer(src.get(i), src.get(j)) > 0) {
                    Vehicule tmp = src.get(i); src.set(i, src.get(j)); src.set(j, tmp);
                }
    }
}
