package ar.gov.mendoza.PrometeoMuni.utils;
import java.util.List;
import java.util.ArrayList;

public class FilterList<E> {
    public  <T> List filterList(List<T> originalList, Filter filter, E text) {
        List<T> filterList = new ArrayList<T>();
        for (T object : originalList) {
            if (filter.isMatched(object, text)) {
                filterList.add(object);
            } else {
                continue;
            }
        }
        return filterList;
    }
} 