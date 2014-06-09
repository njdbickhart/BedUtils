 package utils;
 
 import java.util.HashSet;
 import java.util.Set;
 
 public class BinBed
 {
   public static int getBin(int chromStart, int chromEnd)
   {
     int genomicLength = getMaxGenomicLengthLevel();
     return calcBin(chromStart, chromEnd, 0, 0, 0, 0, 1, 0, genomicLength);
   }
 
   protected static int getMaxGenomicLengthLevel() {
     return 536870912;
   }
 
   protected static int getMaxLevel() {
     return 4;
   }
 
   protected static int getChildrenCount() {
     return 8;
   }
   private static int calcBin(int chromStart, int chromEnd, int binId, int level, int binRowStart, int rowIndex, int binRowCount, int genomicPos, int genomicLength) {
     if ((chromStart >= genomicPos) && (chromEnd <= genomicPos + genomicLength)) {
       if (level >= getMaxLevel()) return binId;
       int childLength = genomicLength / getChildrenCount();
       int childBinRowCount = binRowCount * getChildrenCount();
       int childRowBinStart = binRowStart + binRowCount;
       int firstChildIndex = rowIndex * getChildrenCount();
       int firstChildBin = childRowBinStart + firstChildIndex;
       for (int i = 0; i < getChildrenCount(); i++) {
         int n = calcBin(chromStart, chromEnd, firstChildBin + i, level + 1, childRowBinStart, firstChildIndex + i, childBinRowCount, genomicPos + i * childLength, childLength);
         if (n != -1) {
           return n;
         }
       }
       return binId;
     }
     return -1;
   }
 
   private static Set<Integer> collectBins(int chromStart, int chromEnd, int binId, int level, int binRowStart, int rowIndex, int binRowCount, int genomicPos, int genomicLength, Set<Integer> set)
   {
     set.add(Integer.valueOf(binId));
     if (level >= getMaxLevel()) return set;
 
     int childLength = genomicLength / getChildrenCount();
     int childBinRowCount = binRowCount * getChildrenCount();
     int childRowBinStart = binRowStart + binRowCount;
     int firstChildIndex = rowIndex * getChildrenCount();
     int firstChildBin = childRowBinStart + firstChildIndex;
     for (int i = 0; i < getChildrenCount(); i++)
     {
       int childStart = genomicPos + i * childLength;
 
       if ((chromStart <= childStart + childLength) && (chromEnd >= childStart))
       {
         collectBins(chromStart, chromEnd, firstChildBin + i, level + 1, childRowBinStart, firstChildIndex + i, childBinRowCount, childStart, childLength, set);
       }
 
     }
 
     return set;
   }
 
   public static Set<Integer> getBins(int chromStart, int chromEnd) {
     int genomicLength = getMaxGenomicLengthLevel();
     return collectBins(chromStart, chromEnd, 0, 0, 0, 0, 1, 0, genomicLength, new HashSet());
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     utils.BinBed
 * JD-Core Version:    0.6.2
 */