package org.fsgt38.fsgt38.util;

import java.util.LinkedHashMap;

/**
 * Created by dsenmart on 18/02/2015.
 */
public class LRUMap<K,V> extends LinkedHashMap<K,V>
{
	private final int capacite;

	public LRUMap(int capacite) {
		this.capacite = capacite;
	}

	@Override
	protected boolean removeEldestEntry(Entry<K, V> eldest)
	{
		return size() > capacite;
	}
}
