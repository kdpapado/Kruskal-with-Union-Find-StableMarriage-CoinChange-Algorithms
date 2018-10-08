package ip_2399;

import integrationproject.model.*;
import java.util.*;

/**
 *
 * @author Konstantina Papadpoulou
 * @aem 2399
 * @email kdpapado@csd.auth.gr
 */


/**
 * the marriage algorithm.
 * @author kon
 * @version 2015.06.07
 */


public class StableMarriage
{
    // Number of red ants-men (=number of black ants-women)
    private int n ;//the number of couples

    //RedAnts Preferences table (size nxn)
    private BlackAnt[][] RAPref;    
    private ArrayList<RedAnt> redAnts;
    private ArrayList<BlackAnt> blackAnts;
    
    /**
     * Creates and solves a stable marriage problem of
     * size n.
     */

    /**
     * St_mar constructor.
     * @param n The number of couples.
     * @param redAnts The red ants population
     * @param blackAnts The black ants population
     */
    public StableMarriage(int n,ArrayList<RedAnt> redAnts,ArrayList<BlackAnt> blackAnts) {
	this.n = n;
        this.redAnts=redAnts;
        this.blackAnts=blackAnts;
        RAPref=new BlackAnt[n][n];
        
        int count=0;
        for(int i=0;i<n;i++){
            int j=0;
            for(BlackAnt ba:blackAnts){
                count++;
                RAPref[i][j]=ba;
                j++;
            }
        }
        int c=0;
        //initializing te preferences table with this norm:The woman which is closer is more desirable
        do{
            for(int c1=1;c1<RAPref.length;c1++){
                for(int c2=0;c2<(RAPref.length-c1);c2++){
                    if(RAPref[c][c2].getDistanceFrom(redAnts.get(c))>RAPref[c][c2+1].getDistanceFrom(redAnts.get(c))){
                        BlackAnt temp=RAPref[c][c2];
                        RAPref[c][c2]=RAPref[c][c2+1];
                        RAPref[c][c2+1]=temp;
                    }
                }
            }
        c++;
        }while(c<n);
    }
    
    /**
     * This method marries a red ant with a black ant depending on their preferences.The red ants do the proposal.
     * While there are red ants, which are still free,if the black ant,which red ant prefers,is not engaged yet,betroth them.If it is already engaged,
     * then test which red ant prefers the black ant (it prefers the ones which is closer) and betroth the black ant with this one.After that put the free ant in the free ants list.
     * @return table-The array with the marriage matching.
     */

    public int[][] marriage() {
	// Indicates that woman with ID i is currently engaged to
	// the man now[i].
	RedAnt[] now = new RedAnt[n];
	for (BlackAnt black:blackAnts)
	    now[black.getID()] = null;

	// List of men that are not currently engaged.
	LinkedList<RedAnt> freeMen = new LinkedList<RedAnt>();
        for(RedAnt r:redAnts){
            freeMen.add(r);
        }
        int g=1;
        int[] next = new int[n];
        while (!freeMen.isEmpty()) {//while there are red ants, which are still free...
            RedAnt m=freeMen.remove();
            
            BlackAnt w=RAPref[m.getID()][next[m.getID()]];
            next[m.getID()]++;
            if(now[w.getID()]==null){//if black ant w is not engaged yet, betroth the red ant m with the black ant w
                now[w.getID()]=m;
            }
            else{//else test which red ant prefers the black ant w(the ones which is closer) and betroth w with this ant
                RedAnt m1=now[w.getID()];
                if(w.getDistanceFrom(m) < w.getDistanceFrom(m1)){
                    now[w.getID()]=m;
                    freeMen.add(m1);
                }
                else{
                    freeMen.add(m);//put the free ant in the free ants list
                }
            }
	g++;
        }
        int[][] table=new int[n][2];
        Iterator<BlackAnt> it=blackAnts.iterator();
        BlackAnt woman_ant;
        for(int count=0;count<n;count++){
            woman_ant=it.next();
            table[count][0]=now[woman_ant.getID()].getID();//the first column contains the red ant's ID 
            table[count][1]=woman_ant.getID();//the second column contains the black ant's ID which is married with the red ant in the first column
            
        }
        return table;	
    }
}