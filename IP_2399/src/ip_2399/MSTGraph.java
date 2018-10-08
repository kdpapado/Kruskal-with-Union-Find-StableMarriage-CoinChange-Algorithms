package ip_2399;

/**
 *
 * @author Konstantina Papadpoulou
 * @aem 2399
 * @email kdpapado@csd.auth.gr
 */
import integrationproject.model.Ant;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;




/**
 * Kruskal's MST graph
 * @author kon
 */
public class MSTGraph {
 
 private int vertices;   // Number of vertices in the graph
 private int EdgesNum;  // Number of edges in the graph
 
 // Input Edge List
 private ArrayList<Edge> edges;
 
 // Processed Edge List of Kruskal Algo
 private ArrayList<Edge> resultEdges;

 /**
  * MSTGraph constructor
  * @param numVertices The number of tree's vertices
  * @param numEdges The number of tree's edges
  */
 public MSTGraph(int numVertices,int numEdges) {
  this.vertices=numVertices;
  this.EdgesNum=numEdges;
  
  this.edges=new ArrayList<Edge>(numEdges);
  this.resultEdges=new ArrayList<Edge>(vertices-1);
 }
 
 
 
 
/**
 *Kruskal's algorithm to find MST.This method calls the EdgeComparator() in order to sort the edges list in increasing order. In addition it 
 * calls the Find() in order to find if two clusters have the same parent.If they have not the same parent it will be called the Union() method in order to union these two clusters.
 * Finally, it calls the findKruskalEdges() to find the final MST.
 *@param ants both red and black ants population
 *@returns MST-The Minimum Spanning Tree 
 */
 private  int[][] Kruskal(ArrayList<Ant> ants)
 {
     
  // Sort the edges list in increasing order
  Collections.sort(edges, new EdgeComparator());
  
  UnionFind uf=new UnionFind(vertices);
  
  // Iterating over the sorted input edgeList
   for(Edge edge:edges)
  {   
   int v1 = uf.Find(edge.src);  //parent vertex for source
         int v2 = uf.Find(edge.dest); //parent vertex for destinition
         
         // if parents do not match, consider edge list for MST and union the two vertex
         if(v1!=v2)
         {
          resultEdges.add(edge);
          uf.Union(v1, v2);
         }
         
         
  }
  // find the final MST
  int[][] MST=findKruskalEdges(ants);
  return MST;
 }
/**
  * Finding the Kruskal MST edges.This method creates the Minimum Spanning Tree's graph by adding the edges starting from the smallest edge.
  * The restriction is:the edge which is going to be added must not create a cycle.After that this method creates the array that contains each ant's ID and color and its connections.
  * To find out in which population an ant belongs,we divide the ants list in the middle.The first piece contains the red ants and the second piece contains the black ants. 
  * @param ants both red and black ants population
  * @return MST-The Minimum Spanning Tree
 */
 public int[][] findKruskalEdges(ArrayList<Ant> ants)
 {
     int[][] d = new int[vertices][vertices];
     for (int i=0;i<vertices;i++)//initialize table d with number -1(which means that the vertice i is not connected with the vertice j)
     {
         for(int j=0;j<vertices;j++)
         {
             d[i][j]=-1;
         }
     }
     
     //connecting two vertices
     for(Edge ed:resultEdges)
     {
         if(d[ed.src][ed.dest]==-1 && ed.src!=ed.dest)/*if vertice ed.src and the vertice ed.dest aren't connected yet and their connection
         will not create a cycle then connect these two vertices*/
         {
             d[ed.src][ed.dest]=1;
         }
     }
     
     int temp=vertices-1;
     int[][] MST=new int[temp][4];
     int count=0;//this variable holds the current number of row
     int color_i;//the first ant's color
     int color_j;//the second ant's color
     int id_i;//the first ant's ID
     int id_j;//the second ant's ID
     for (int i=0;i<vertices;i++)
     {
         for(int j=0;j<vertices;j++)
         {
             
             if(i!=j && d[i][j]==1)//finds out if two differnt vertices are connected
             {
                 //if we divide the ants list in the middle...the first piece contains the red ants and the second piece contains the black ants.
                 if(i>=(vertices/2)){ //finds out if a vertice belongs at the redAnts population or at the BlackAnts population  
                     color_i=1;//black
                     id_i=i-(vertices/2);
                 }
                 else{
                     color_i=0;//red
                     id_i=i;
                 }
                 
                 if(j>=(vertices/2)){
                     color_j=1;//black
                     id_j=j-(vertices/2);
                 }
                 else{
                     color_j=0;//red
                     id_j=j;
                 }
                 
                 MST[count][0]=id_i;//The first column contains the first ant's ID
                 MST[count][1]=color_i;//The second column contains the first ant's color
                 MST[count][2]=id_j;//The third column contains the second ant's ID
                 MST[count][3]=color_j;//The fourth column contains the second ant's color
                 count++;
             }
         }
     }
     return MST;
 }
 
 /**
  * This is a helper method.This method fills the List of Edges(edges) and calls the Kruskal() method.The edges weights are based in the first ant's distance from the second ant.
  * @param numberOfVertices The number of tree's vertices
  * @param ants both red and black ants population
  * @return MST-The Minimum Spanning Tree
  */
 public int[][] FillEdgesList(int numberOfVertices,ArrayList<Ant> ants)
 {
     for (int i=0;i<numberOfVertices;i++)
     {
         for(int j=0;j<numberOfVertices;j++)
         {
            if(i!=j)
            {
                edges.add(new Edge(i,j, (int) (10000 * ants.get(i).getDistanceFrom(ants.get(j)))));//the edges weights are based in the first ants distance from the second ant
            }
             
         }
         
     }     
     int[][] MST =Kruskal(ants);
     return MST; 
 }
 
 /**
  * This method sorts the List of Edges(edges) in increasing order
  */
 public class EdgeComparator implements Comparator<ip_2399.Edge>
    {

        @Override
        public int compare(ip_2399.Edge edge1, ip_2399.Edge edge2) {
            if (edge1.w<edge2.w)
                return -1;
            if (edge1.w>edge2.w)
                return 1;
            return 0;
        }
    
    }
}