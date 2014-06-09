 package utils;
 
 import file.BedAbstract;
 import file.BedEntry;
 import file.BedFileException;
 import file.BedMap;
 import file.BedSimple;
 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.nio.charset.Charset;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.util.ArrayList;
 import java.util.Set;
 
 public class BedIntersect
 {
   public static BedMap intersectMemBed(BedMap a, BedMap b)
   {
     BedMap intersectedBed = new BedMap();
 
     Set<String> achrs = a.getListChrs();
     Set<String> bchrs = b.getListChrs();
     Set<String> uchrs = null;
     boolean found = getMergeSet(achrs, bchrs, uchrs);
     if (!found) uchrs = achrs;
 
     for (String chr : uchrs) {
       for (int binlevel = 0; binlevel < BinBed.getChildrenCount(); binlevel++)
       {
         BedAbstract comp;
         for (int iterator = 0; iterator < a.getNumBedEntries(chr, Integer.valueOf(binlevel)); iterator++) {
           comp = a.getNextBed(chr, Integer.valueOf(binlevel), iterator);
           Set<Integer> binlist = BinBed.getBins(comp.Start(), comp.End());
           for (Integer i : binlist) {
             if (b.containsBin(chr, i.intValue())) {
               for (BedAbstract z : b.getBedAbstractList(chr, i)) {
                 if (checkOverlap(comp.Start(), z.Start(), comp.End(), z.End())) addIntersectionToPool(comp, z, chr, intersectedBed);
               }
             }
           }
         }
       }
     }
     return intersectedBed;
   }
 
   public static BedMap intersectMemBed(BedMap a, BedMap b, boolean wa, boolean wb)
     throws BedFileException
   {
     BedMap intersectedBed = new BedMap();
 
     Set achrs = a.getListChrs();
     Set bchrs = b.getListChrs();
     Set<String> uchrs = null;
     boolean found = getMergeSet(achrs, bchrs, uchrs);
     if (!found) uchrs = achrs;
 
     for (String chr : uchrs) {
       for (int binlevel = 0; binlevel < BinBed.getChildrenCount(); binlevel++)
       {
         BedAbstract comp;
         for (int iterator = 0; iterator < a.getNumBedEntries(chr, Integer.valueOf(binlevel)); iterator++) {
           comp = a.getNextBed(chr, Integer.valueOf(binlevel), iterator);
           if (comp.Type() != 1) throw new BedFileException("IntersectMemBed error: Bed comp must be type 1 (name variety); line 79");
           Set<Integer> binlist = BinBed.getBins(comp.Start(), comp.End());
           for (Integer i : binlist) {
             if (b.containsBin(chr, i.intValue())) {
               for (BedAbstract z : b.getBedAbstractList(chr, i)) {
                 if (checkOverlap(comp.Start(), z.Start(), comp.End(), z.End())) {
                   if (z.Type() != 1) throw new BedFileException("IntersectMemBed error: Bed z must be type 1 (name variety); line 86");
                   addIntersectionToPool(comp, z, chr, intersectedBed, wa, wb);
                 }
               }
             }
           }
         }
       }
     }
     return intersectedBed;
   }
 
   public static BedMap intersectFileBed(Path inFile, BedMap b)
   {
     BedMap intersectedBed = new BedMap();
     Charset charset = Charset.forName("UTF-8");
     BufferedReader reader = null;
     try {
       reader = Files.newBufferedReader(inFile, charset);
       String line;
       String[] segments;
       int lstart;
       int lend;
       while ((line = reader.readLine()) != null)
       {
         if (!line.isEmpty()) {
           segments = line.split("\\t");
           lstart = 0;
           lend = 0;
           if ((isNumeric(segments[1])) && (isNumeric(segments[2]))) {
             lstart = Integer.parseInt(segments[1]);
             lend = Integer.parseInt(segments[2]);
           } else {
             System.out.println("Could not parse " + segments[1] + " " + segments[2]);
             continue;
           }
           Set<Integer> binlist = BinBed.getBins(lstart, lend);
           for (Integer i : binlist)
             if (b.getBedAbstractList(segments[0], i) != null)
               for (BedAbstract z : b.getBedAbstractList(segments[0], i))
                 if (checkOverlap(lstart, z.Start(), lend, z.End())) {
                   BedAbstract comp = new BedEntry(line);
                   addIntersectionToPool(comp, z, segments[0], intersectedBed);
                 }
         }
       }
     }
     catch (IOException io)
     {
       System.out.println(io);
     } catch (BedFileException bex) {
       System.out.println(bex.error());
     } finally {
       try {
         reader.close();
       } catch (IOException ex) {
         System.out.println(ex);
       }
     }
     return intersectedBed;
   }
 
   private static boolean getMergeSet(Set<String> achrs, Set<String> bchrs, Set<String> uchrs)
   {
     boolean found = false;
     if ((!achrs.containsAll(bchrs)) && (!bchrs.containsAll(achrs))) {
       found = true;
       for (String as : achrs) {
         if (bchrs.contains(as)) {
           uchrs.add(as);
         }
       }
       for (String bs : bchrs) {
         if (achrs.contains(bs)) {
           uchrs.add(bs);
         }
       }
     }
     return found;
   }
 
   private static boolean checkOverlap(int starta, int startb, int enda, int endb) {
     boolean found = false;
     if ((starta < endb) && (enda > startb)) found = true;
     return found;
   }
 
   private static void addIntersectionToPool(BedAbstract a, BedAbstract b, String chr, BedMap intersect) {
     BedAbstract z = null;
     int newStart = 0;
     int newEnd = 0;
     String name = null;
     float gc = 0.0F;
     int score = 0;
 
     if ((a.Type() == 0) && (b.Type() == 0)) {
       newStart = getRefineCoords(a.Start(), b.Start(), true);
       newEnd = getRefineCoords(a.End(), b.End(), false);
       z = new BedSimple(chr, newStart, newEnd);
       intersect.addBedData(chr, z);
     } else if ((a.Type() == 1) && (b.Type() == 1)) {
       newStart = getRefineCoords(a.Start(), b.Start(), true);
       newEnd = getRefineCoords(a.End(), b.End(), false);
       name = catString(a.Name(), b.Name());
       z = new BedSimple(chr, newStart, newEnd, name);
       intersect.addBedData(chr, z);
     }
     else
     {
       newStart = getRefineCoords(a.Start(), b.Start(), true);
       newEnd = getRefineCoords(a.End(), b.End(), false);
       z = new BedEntry(chr, newStart, newEnd);
       intersect.addBedData(chr, z);
     }
   }
 
   private static void addIntersectionToPool(BedAbstract a, BedAbstract b, String chr, BedMap intersect, boolean wa, boolean wb)
   {
     BedAbstract z = null;
     int newStart = 0;
     int newEnd = 0;
 
     newStart = getRefineCoords(a.Start(), b.Start(), true);
     newEnd = getRefineCoords(a.End(), b.End(), false);
     if ((wa) && (wb)) {
       String tmp = catString(a.Name(), b.Name());
       z = new BedSimple(chr, newStart, newEnd, tmp);
     } else if ((wa) && (!wb)) { z = new BedSimple(chr, newStart, newEnd, a.Name());
     } else if ((wb) && (!wa)) { z = new BedSimple(chr, a.Start(), a.End(), b.Name());
     } else {
       z = new BedSimple(chr, newStart, newEnd);
     }
     intersect.addBedData(chr, z);
   }
 
   private static int getRefineCoords(int a, int b, boolean start)
   {
     if (!start) return least(a, b);
     if (start) return most(a, b);
     return -1;
   }
   private static int least(int a, int b) {
     if (a < b) return a;
     return b;
   }
   private static int most(int a, int b) {
     if (a > b) return a;
     return b;
   }
   private static String catString(String a, String b) {
     return a + ";" + b;
   }
   private static boolean isNumeric(String s) {
     return s.matches("-?\\d+(.\\d+)?");
   }
 
   private static class mergerRefs
   {
     protected int bin;
     protected int aiter;
   }
 
   private static class mergerInfo
   {
     protected String chr;
     protected ArrayList<BedIntersect.mergerRefs> store;
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     utils.BedIntersect
 * JD-Core Version:    0.6.2
 */