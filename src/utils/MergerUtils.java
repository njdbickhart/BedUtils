/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author bickhart
 */
public class MergerUtils {
    public static int getRefineCoords (int a, int b, boolean start){
        // if start is true, select the "most" function; else select the "least" function
        if(!start) {
            return least(a,b);
        }
        if(start) {
            return most(a,b);
        }
        return -1; // In case the function ever gets to this point!
    }
    public static int least(int a, int b){
        if(a < b) {
            return a;
        }
        else {
            return b;
        }
    }
    public static int most(int a, int b){
        if(a > b) {
            return a;
        }
        else {
            return b;
        }
    }
    public static boolean isNumeric(String s){
         return(s.matches("-?\\d+(.\\d+)?"));
    }
    public static boolean checkOverlap(int starta, int startb, int enda, int endb){
        boolean found = false;
        if ((starta < endb) && (enda > startb)) {
            found = true;
        }
        return found;
    }
    public static boolean checkOverlap(int starta, int startb, int enda, int endb, double perc){
        int lena = most(enda, starta) - least(enda, starta);
        int lenb = most(endb, startb) - least(endb, startb);
        int ovlp = least(enda, endb) - most(starta, startb);
        if(ovlp > 0){
            if((double)ovlp >= (double)lena * perc && (double) ovlp >= (double)lenb * perc){
                return true;
            }
        }
        return false;
    }
    public static boolean isCloserLeft(int start, int end, int compstart){
        if(compstart - start < end - compstart){
            return true;
        }
        return false;
    }
}
