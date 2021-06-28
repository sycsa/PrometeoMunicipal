package ar.gov.mendoza.PrometeoMuni.dao;

import java.util.ArrayList;

import android.content.ContentValues;

public interface IBaseDao<T> {
	ArrayList<T> getAll(String... pSort);
	T getItem(String id) ;
	ArrayList<T> getByFilter(ContentValues pContentValues, String... pSort);
	ArrayList<T> getByFilterLike(ContentValues pContentValues, String... pSort);
	ArrayList<T> getByFilterLikeOr(ContentValues pContentValues, String... pSort);
	T getNewObject();
	long getNextId();
	void configSecuence(long pValue);
}
