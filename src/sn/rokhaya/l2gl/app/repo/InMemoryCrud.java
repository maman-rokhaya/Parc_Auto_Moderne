package sn.rokhaya.l2gl.app.repo;

import sn.rokhaya.l2gl.app.model.Identifiable;
import java.util.*;

public class InMemoryCrud<T extends Identifiable> implements Crud<T> {

    private final Map<Long, T> storage = new HashMap<>();

    @Override
    public void create(T entite) {
        if (entite == null)
            throw new IllegalArgumentException("Impossible de creer une entite null.");
        if (storage.containsKey(entite.getId()))
            throw new IllegalStateException("Une entite avec l'id " + entite.getId() + " existe deja.");
        storage.put(entite.getId(), entite);
    }

    @Override
    public T read(Long id) {
        if (!storage.containsKey(id))
            throw new NoSuchElementException("Aucune entite avec l'id " + id + " trouvee.");
        return storage.get(id);
    }

    @Override
    public Optional<T> readOpt(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void update(T entite) {
        if (entite == null)
            throw new IllegalArgumentException("Impossible de mettre a jour une entite null.");
        if (!storage.containsKey(entite.getId()))
            throw new NoSuchElementException("Mise a jour impossible : id " + entite.getId() + " introuvable.");
        storage.put(entite.getId(), entite);
    }

    @Override
    public void delete(Long id) {
        if (!storage.containsKey(id))
            throw new NoSuchElementException("Suppression impossible : id " + id + " introuvable.");
        storage.remove(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }


}
