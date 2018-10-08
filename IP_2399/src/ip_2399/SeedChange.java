package ip_2399;

/**
 *
 * @author Konstantina Papadpoulou
 * @aem 2399
 * @email kdpapado@csd.auth.gr
 */

/**
 * The coin change algorithm
 * @author kon
 * @version 2015.06.07
 */
public class SeedChange {
    public SeedChange(){
    
    }
    /**
     * This method finds out each item's minimum number of seeds that are need to fill the red ant's bucket.
     * For any amount in the space [1,change amount] it finds out which is the minimum number of the items that are used and which item is used.  
     * @param seeds The array of seed values-weight
     * @param differentItems The number of different seed items
     * @param maxChange The redAnt's bucket's capacity.
     * @param seedsUsed The minimum number of items that can be used for a specific amount
     * @param lastSeed The item that is used to defray the specific amount
     */
    public  void makeChange( int [ ] seeds, int differentItems,
                int maxChange, int [ ] seedsUsed, int [ ] lastSeed )
    {
        seedsUsed[ 0 ] = 0; lastSeed[ 0 ] = 1;

        for( int s = 1; s <= maxChange; s++ )
        {
            int minSeeds = s;
            int newSeed  = 1;

            for( int j = 0; j < differentItems; j++ )//for each item...
            {
                if( seeds[ j ] > s )   // if item's j seed is heavier than s,then seed j can not be used
                    continue;
                if( seedsUsed[ s - seeds[ j ] ] + 1 < minSeeds )/*if the number of items that have been used 
                is the minimum then initialize the variable of minimum seeds with this number*/
                    
                {
                    minSeeds = seedsUsed[ s - seeds[ j ] ] + 1;
                    newSeed  = seeds[ j ];//the item that has been used
                }
            }

            seedsUsed[ s ] = minSeeds;
            lastSeed[ s ]  = newSeed;
        }
}
}
