package set;

import java.util.HashMap;
import java.util.Map;

/** 	CS515 Lab 8
 File: DisjointSet.java
 Name: Qifei Wang
 Section: 2
 Date: 10/17/2024
 Collaboration Declaration: None
 Lab Partner: None
 Collaboration: None
 */

public class DisjointSet<T extends Comparable<T>> {

	Map<T, T> parents;
	Map<T, Integer> depths;

	public DisjointSet() {
		parents = new HashMap<T, T>();
		depths = new HashMap<T, Integer>();
	}

	/**
	 * Creates a singleton from the parameter.
	 */
	public void createSet(T _t) {
        // TO IMPLEMENT
		parents.put(_t, _t);
		depths.put(_t, 0);
	}

	/**
	 * Finds and returns the representative of the set which contains the
	 * parameter. Implements path compression.
	 */
	public T findSet(T _t) {
		//TO IMPLEMENT
        if (!parents.get(_t).equals(_t)) {
			parents.put(_t, findSet(parents.get(_t)));
		}
		return parents.get(_t);
	}

	/**
	 * Combines the sets which contain the parameters.
	 */
	public void unionSets(T _u, T _v) {
	    //TO IMPLEMENT
		T rootU = findSet(_u);
		T rootV = findSet(_v);

		if (!rootU.equals(rootV)) {
			int rankU = depths.get(rootU);
			int rankV = depths.get(rootV);

			if (rankU > rankV) {
				parents.put(rootV, rootU);
			} else if (rankU < rankV) {
				parents.put(rootU, rootV);
			} else {
				parents.put(rootV, rootU);
				depths.put(rootU, rankU+1);
			}
		}
	}
}
