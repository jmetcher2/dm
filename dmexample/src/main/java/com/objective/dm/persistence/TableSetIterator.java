package com.objective.dm.persistence;

import java.util.Iterator;

import com.objective.dm.base.BaseDomainObject;

public class TableSetIterator<T extends BaseDomainObject> implements Iterator<T> {
	
	private TableSet<T> parentSet;
	private Iterator<T> iterator;
	private T currentItem;
	
	public TableSetIterator(TableSet<T> parent) {
		this.parentSet = parent;
		this.iterator = parentSet.queryListIterator();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public T next() {
		currentItem = iterator.next();
		return currentItem;
	}

	@Override
	public void remove() {
		parentSet.remove(currentItem);
	}

}
