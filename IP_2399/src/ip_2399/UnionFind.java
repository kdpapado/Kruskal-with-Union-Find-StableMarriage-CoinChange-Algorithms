package ip_2399;

/**
 *
 * @author Konstantina Papadpoulou
 * @aem 2399
 * @email kdpapado@csd.auth.gr
 */

/**
 * Union Find Node definition.
 * @author kon
 * @version 2015.06.07
 */


class Node {
 int parent; // parent of Vertex at i in the Holder
 int r; // Number of object present in the tree/ cluster

 /**
  * UFNode constructor
  * @param parent-The node's parent
  * @param rank-The node's rank
  */
 Node(int parent, int r) {
  this.parent = parent;
  this.r = r;
 }
}

/**
 * Union-Find Data structure 
 * @author kon
 */
public class UnionFind {
 // Node Holder haveing UFNode
 private Node[] Holder;

 // number of node
 private int count;
 
/**
 * UnioFind constructor
 * @param size The number of tree's vertices
 */
 public UnionFind(int size) {
  if (size < 0)
   throw new IllegalArgumentException();
  count = size;
  Holder = new Node[size];
  for (int i = 0; i < size; i++) {  
   Holder[i] = new Node(i, 1); // default values, node points to
            // itself and rank is 1
  }
 }

 /**
  * Finds the parent of a given vertex, using recursion
  * 
  * @param vertex A vertex
  * @return The parent of the given vertex(Holder[vertex].parent)
  */
 public int Find(int vertex) {
  if (vertex < 0 || vertex >= Holder.length)
   throw new IndexOutOfBoundsException();
  if (Holder[vertex].parent != vertex)
   Holder[vertex].parent = Find(Holder[vertex].parent);
  return Holder[vertex].parent;
 }

 /**
  * Finds out if two clusters have the same parent
  * @param v1
  *            : vertex 1 of some cluster
  * @param v2
  *            : vertex 2 of some cluster
  * @return true if both vertex have same parent
  */
 public boolean isConnected(int v1, int v2) {
  return Find(v1) == Find(v2);
 }

 /**
  * Unions two cluster of two vertices
  * @param v1 vertex No1
  * @param v2 vertex No2
  */
 public void Union(int v1, int v2) {
  int i = Find(v1);
  int j = Find(v2);

  if (i == j)
   return;

  if (Holder[i].r < Holder[j].r) {
   Holder[i].parent = j;
   Holder[j].r = Holder[j].r + Holder[i].r;
  } else {
   Holder[j].parent = i;
   Holder[i].r = Holder[i].r + Holder[j].r;
  }
  count--;
 }
}