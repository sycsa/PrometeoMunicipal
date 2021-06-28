package ar.gov.mendoza.PrometeoMuni.utils;

public interface Filter<T,E> {
    boolean isMatched(T object, E text);
}