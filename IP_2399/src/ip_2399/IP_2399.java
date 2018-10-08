package ip_2399;

import integrationproject.algorithms.Algorithms;
import integrationproject.model.Ant;
import integrationproject.model.BlackAnt;
import integrationproject.model.RedAnt;
import integrationproject.utils.InputHandler;
import integrationproject.utils.Visualize;
import java.util.ArrayList;

/**
 *
 * @author Konstantina Papadpoulou
 * @aem 2399
 * @email kdpapado@csd.auth.gr
 */

/**
 * This class contains the main method.
 * @author kon
 * @version 2015.06.07
 */

public class IP_2399 extends Algorithms {


    private int[][] matchings;
    

    public static void main(String[] args) {
        checkParameters(args);
        
        //create Lists of Red and Black Ants
        int flag = Integer.parseInt(args[1]);
        ArrayList<RedAnt> redAnts = new ArrayList<>();
        ArrayList<BlackAnt> blackAnts = new ArrayList<>();
        
        
        if (flag == 0) {
            InputHandler.createRandomInput(args[0], Integer.parseInt(args[2]));
        }
        InputHandler.readInput(args[0], redAnts, blackAnts);

        
        IP_2399 algs = new IP_2399();
        
        //debugging options
        boolean visualizeMST = true;
        boolean visualizeSM = true;
        boolean printCC = true;
        boolean evaluateResults = true;

        if(visualizeMST){
            int[][] mst = algs.findMST(redAnts, blackAnts);
            if (mst != null) {
                Visualize sd = new Visualize(redAnts, blackAnts, mst, null, "Minimum Spanning Tree");
                sd.drawInitialPoints();
            }
        }

        if(visualizeSM){
            int[][] matchings = algs.findStableMarriage(redAnts, blackAnts);
            if (matchings != null) {
                Visualize sd = new Visualize(redAnts, blackAnts, null, matchings, "Stable Marriage");
                sd.drawInitialPoints();
            }
        }

        if(printCC){
            int[] coinChange = algs.coinChange(redAnts.get(0), blackAnts.get(0)); 
            System.out.println("Capacity: " + redAnts.get(0).getCapacity());
            for(int i = 0; i < blackAnts.get(0).getObjects().length; i++){
                System.out.println(blackAnts.get(0).getObjects()[i] + ": " + coinChange[i]);
            }
        }
        
        if(evaluateResults){
            System.out.println("\nEvaluation Results");
            algs.evaluateAll(redAnts, blackAnts);
        }
    }
    
    /**
    Implementing the Kruskal algorithm using union-find implementation
    *@param redAnts - The red ants population
    *@param blackAnts - The black ants population
    * Returns a 2d array int[red_population+black_population-1][4]. In each row there is the ant ID and an identifier whether it is from red (0) or black population (1) I.e., ant id - identifier - ant id -identifier E.g. [21,0,4,1] -- means that the red ant with ID 21 is connected with the black ant with ID 4.
    *@return MST 
    **/
    @Override
    public int[][] findMST(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts) {
        ArrayList<Ant> ants = new ArrayList<>();//an ArrayList with all ants
        ants.addAll(redAnts);
        ants.addAll(blackAnts);
        int numberOfVertices = redAnts.size()+blackAnts.size();//number of vertices in the graph
        MSTGraph graph=new MSTGraph(numberOfVertices, numberOfVertices-1);
        int[][] MST=graph.FillEdgesList(numberOfVertices,ants);//finding the MST
        return MST;
    }

    /**
     Implementing the Stable Matching algorithm, considering that the red ants (0) do the proposals.
    *@param redAnts - The red ants population
    *@param blackAnts - The black ants population
    *Returns a 2d array int[red_population][2]. The 2nd dimension is of size of 2 as it expects values in the form [ant_id_1,ant_id_2] where ant_id is the id of the ant. This means that ant_id_1 is matched with ant_id_2. ant_id_1 should be a red ant!
    *@return marriage
    **/
    @Override
    public int[][] findStableMarriage(ArrayList<RedAnt> redAnts, ArrayList<BlackAnt> blackAnts) {
        int couples = redAnts.size();//the couples number
        StableMarriage sm = new StableMarriage(couples,redAnts,blackAnts);
	int[][] matchings = sm.marriage();
        return matchings;
    }

    /**
    Implementing the Coin Change algorithm. This is developed like the change return dynamic programming problem.
    * Returns an array of size 5 int[5] (as every black ant holds seeds of up to 5 kinds) with the counts of every item.
    *@param redAnt - A red ant
    *@param blackAnt - A black ant
    *
    *@return minSeedsTable
    **/
    @Override
    public int[] coinChange(RedAnt redAnt, BlackAnt blackAnt) {        
        int[] seeds=new int[5];
        int change = redAnt.getCapacity();
        int numSeeds=5;
        for(int i=0;i<5;i++){
            seeds[i] = blackAnt.getObjects()[i];
        }
        int[] used=new int[change+1];
        int [ ] last = new int[ change + 1 ];
        SeedChange sc=new SeedChange();
        sc.makeChange(seeds,numSeeds,change,used,last);
        int[] minSeedsTable=new int[5];
        for( int i = change; i > 0; )
        {
            for(int j=0;j<5;j++){
                if(seeds[j]==last[i]){//if the seed of item j is used to defray the amount i..
                    minSeedsTable[j]++;//then increase the item' s j number of seeds that are used.
                }
            }
            i -= last[ i ];//deduct the amount-the seeds weight that has been used from the total amount i
        }
        return minSeedsTable;    
    }
    
    /**
     * It checks the parameters
     * @param args 
     */
    private static void checkParameters(String[] args) {
        if (args.length == 0 || args.length < 2 || (args[1].equals("0") && args.length < 3)) {
            if (args.length > 0 && args[1].equals("0") && args.length < 3) {
                System.out.println("3rd argument is mandatory. Represents the population of the Ants");
            }
            System.out.println("Usage:");
            System.out.println("1st argument: name of filename");
            System.out.println("2nd argument: 0 create random file, 1 input file is given as input");
            System.out.println("3rd argument: number of ants to create (optional if 1 is given in the 2nd argument)");
            System.exit(-1);
        }
    }
    
}
