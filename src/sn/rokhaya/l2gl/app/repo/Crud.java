package sn.rokhaya.l2gl.app.repo;

import sn.rokhaya.l2gl.app.model.Identifiable;
import java.util.List;
import java.util.Optional;

public interface Crud<T extends Identifiable> {

    void         create(T entite);
    T            read(Long id);
    Optional<T>  readOpt(Long id);
    void         update(T entite);
    void         delete(Long id);
    List<T>      findAll();


}
