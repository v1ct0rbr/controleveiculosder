package br.gov.pb.der.controleveiculosder.service;

import java.util.List;

public interface GenericService<T, V> {

	public void save(T entity);
	public T findById(V id);
	public List<T> findAll();
	public void delete(V id);
	public boolean exists(V id);
	
}
