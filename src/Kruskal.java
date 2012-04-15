/* Kruskal.java */

import java.util.Hashtable;

import dict.HashTableChained;
import graph.*;
import set.*;
import graph.*;
import graph.*
;
import graph.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g){
	  WUGraph t = new WUGraph();
	  Object[] vertices = g.getVertices();
	  int x = 0;
	  EdgeWeight[] edges = new EdgeWeight[g.edgeCount()];
	  HashTableChained edgeHash = new HashTableChained(2*g.edgeCount());
	  for (Object o: vertices){	// adds all vertices to t + puts all edges in "edges" array
		  t.addVertex(o);
		  if (edges[-1] == null) {
			  Neighbors n = g.getNeighbors(o);
			  for (int i=0; i < n.neighborList.length; i++) {
				  EdgeWeight e = new EdgeWeight(o, n.neighborList[i], n.weightList[i]);
				  if (edgeHash.find(e) == null) {
					  edgeHash.insert(e, e);
					  edges[x] = e;
					  x++;
				  }
			  }
		  }
	  }
	  quicksort(edges, 0, edges.length - 1); // sorts "edges" array
	  
	  HashTableChained treeHash = new HashTableChained(edges.length);
	  DisjointSets set = new DisjointSets(edges.length);
	  for (EdgeWeight ed: edges) {
		  
	  }
	  
	  
	  
	  return t;
  }
  
  
  public static void quicksort(EdgeWeight[] a, int low, int high) {
	  // If there's fewer than two items, do nothing.
	  if (low < high) {
	    int pivotIndex = low + (int) (Math.random()*((high-low)+1)); // <- random int in [low, high]
	    Comparable pivotw = (Comparable) a[pivotIndex].weight();
	    EdgeWeight pivot = a[pivotIndex];
	    a[pivotIndex] = a[high];                       // Swap pivot with last item
	    a[high] = pivot;

	    int i = low - 1;
	    int j = high;
	    do {
	      do { i++; } while (((Comparable) a[i].weight()).compareTo(pivotw) < 0);
	      do { j--; } while ((((Comparable) a[j].weight()).compareTo(pivotw) > 0) && (j > low));
	      if (i < j) {
	        EdgeWeight placeholder = a[i]; //swap a[i] and a[j];
	        a[i] = a[j];
	        a[j] = placeholder;
	      }
	    } while (i < j);

	    a[high] = a[i];
	    a[i] = pivot;                   // Put pivot in the middle where it belongs
	    quicksort(a, low, i - 1);                     // Recursively sort left list
	    quicksort(a, i + 1, high);                   // Recursively sort right list
	  }
	}

}