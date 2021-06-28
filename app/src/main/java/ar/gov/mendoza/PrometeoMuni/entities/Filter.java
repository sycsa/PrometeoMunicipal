package ar.gov.mendoza.PrometeoMuni.entities;

public interface Filter<T,E> {
    boolean isMatched(T object, E text);
}