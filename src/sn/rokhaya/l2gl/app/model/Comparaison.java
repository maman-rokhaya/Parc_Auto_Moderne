package sn.rokhaya.l2gl.app.model;

@FunctionalInterface
public interface Comparaison<T> {
    int comparer(T t1, T t2);
}
