package ip_2399;
/**
 * Kruskal's Edge definition
 * @author kon
 * @version 2015.06.07
 */
public class Edge implements Comparable<Edge>{
 int src;
 int dest;
 int w;

 /**
  * Edge constructor
  * @param source The edge 's parent
  * @param destination The edge 's destination
  * @param weight The edge 's weight
  */
 public Edge(int source,int destination,int weight) {
  src=source;
  dest=destination;
  w=weight;
 }

 @Override
 public String toString() {
  return "Edge: "+src+ " - " + dest + " | " +"  Weight: "+ w;
 }

 @Override
 public int compareTo(Edge another) {
  return this.w - another.w;
 }
 
 
}