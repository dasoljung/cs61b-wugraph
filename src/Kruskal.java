/* Kruskal.java */

import java.util.Hashtable;
import graph.*;
import set.*;
import graph.*;
import graph.*
;
import graph.*;
import graph.Neighbors;

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
	  Object[] edges = new Object[g.edgeCount()];
	  for (Object o: vertices){
		  t.addVertex(o);
		  if (edges[-1] == null) {
			  Neighbors n = g.getNeighbors(o);
			  for (int i=0; i < n.neighborList.length; i++) {
				  new EdgeWeight(o, n.neighborList[i], n.weightList[i]);
				  
			  }
		  }
	  }
	  
	  return t;
  }

}

class EdgeWeight {
	public Object v1;
	public Object v2;
	int weight;
	public EdgeWeight(Object o1, Object o2, int weight) {
		v1 = o1;
		v2 = o2;
		this.weight = weight;
	}
}
