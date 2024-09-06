package edu.dandaev_it.dao;

import java.util.List;
import java.util.Optional;;

public interface DAO<K, E> {
	public List<E> selectAll();

	public Optional<E> select(K ticketID);

	public void update(E ticket);

	public E insert(E ticket);

	public boolean delete(K ticketId);
}
