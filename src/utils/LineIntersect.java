 package utils;
 
 import file.BedAbstract;
 import file.BedMap;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.Set;
 
 public class LineIntersect
 {
   public static boolean doesIntersect(BedMap a, String chr, int start, int end)
   {
     Iterator i$;
     if (a.containsChr(chr)) {
       Set bins = BinBed.getBins(start, end);
       for (i$ = bins.iterator(); i$.hasNext(); ) { int bin = ((Integer)i$.next()).intValue();
         if (a.containsBin(chr, bin)) {
           for (Object c : a.getBedAbstractList(chr, Integer.valueOf(bin))) {
               BedAbstract bed = (BedAbstract) c;
             if (ovCount(bed.Start(), bed.End(), start, end) > 0)
               return true;
           }
         }
         else {
           return false;
         }
       }
     }
     return false;
   }
 
   public static ArrayList<BedAbstract> returnIntersect(BedMap a, String chr, int start, int end) {
     ArrayList r = new ArrayList();
     Iterator i$;
     if (a.containsChr(chr)) {
       Set bins = BinBed.getBins(start, end);
       for (i$ = bins.iterator(); i$.hasNext(); ) { int bin = ((Integer)i$.next()).intValue();
         if (a.containsBin(chr, bin)) {
           for (Object c : a.getBedAbstractList(chr, Integer.valueOf(bin))) {
               BedAbstract bed = (BedAbstract) c;
             if (ovCount(bed.Start(), bed.End(), start, end) > 0) {
               r.add(bed);
             }
           }
         }
       }
     }
     return r;
   }
 
   public static int ovCount(int start1, int end1, int start2, int end2) {
     return soonest(end1, end2) - latest(start1, start2);
   }
   public static int soonest(int a, int b) {
     return a >= b ? b : a;
   }
   public static int latest(int a, int b) {
     return a >= b ? a : b;
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     utils.LineIntersect
 * JD-Core Version:    0.6.2
 */