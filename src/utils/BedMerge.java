 package utils;
 
 import file.BedMap;
 import file.BedMapNoBin;
 import file.BedMapNoBin.bedData;
 import file.BedSimple;
 import java.io.BufferedWriter;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.nio.charset.Charset;
 import java.nio.file.Files;
 import java.nio.file.OpenOption;
 import java.nio.file.Path;
 import java.util.ArrayList;
import java.util.Arrays;
 import java.util.Collection;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 
 public class BedMerge
 {
   private int threads;
   protected BedMap[] maps;
   protected int type;
   protected Map<String, ArrayList<BedMapNoBin.bedData>> mergeStore;
 
   public BedMerge(int type, int threads, Path outfile, BedMap[] a)
   {
     this.threads = threads;
     BedMapNoBin noBin = new BedMapNoBin();
     noBin.mergeIntoMap(type, a);
 
     ExecutorService executor = Executors.newFixedThreadPool(this.threads);
     Set<String> chrs = noBin.getChrs();
     this.mergeStore = Collections.synchronizedMap(new HashMap(30));
     for (String c : chrs) {
       executor.execute(new threadSimpleMerge(c, type, this.mergeStore, noBin));
     }
 
     executor.shutdown();
     while (!executor.isTerminated());
     printOutMergeBed(outfile, type);
   }
 
   public BedMerge(int type, int threads, BedMap[] a) {
     this.threads = threads;
     BedMapNoBin noBin = new BedMapNoBin();
     noBin.mergeIntoMap(type, a);
 
     ExecutorService executor = Executors.newFixedThreadPool(this.threads);
     Set<String> chrs = noBin.getChrs();
     this.mergeStore = Collections.synchronizedMap(new HashMap(30));
     for (String c : chrs) {
       executor.execute(new threadSimpleMerge(c, type, this.mergeStore, noBin));
     }
 
     executor.shutdown();
     while (!executor.isTerminated());
   }
 
   public BedMerge(int threads, BedMap[] a)
   {
     this.threads = threads;
     this.maps = a;
     this.type = 1;
   }
 
   public void RunMerger() {
     BedMapNoBin noBin = new BedMapNoBin();
     noBin.mergeIntoMap(this.type, this.maps);
 
     ExecutorService executor = Executors.newFixedThreadPool(this.threads);
     Set<String> chrs = noBin.getChrs();
     this.mergeStore = Collections.synchronizedMap(new HashMap(30));
     for (String c : chrs) {
       executor.execute(new threadSimpleMerge(c, this.type, this.mergeStore, noBin));
     }
     executor.shutdown();
     while (!executor.isTerminated());
   }
 
   private <T extends Comparable<? super T>> List<T> asSortedChrs(Collection<T> c)
   {
     List list = new ArrayList(c);
     Comparator chrComp = new chrCompare();
     Collections.sort(list, chrComp);
     return list;
   }
 
   private void printOutMergeBed(Path outFile, int type)
   {
     BufferedWriter writer = null;
     try {
       Charset charset = Charset.forName("UTF-8");
       writer = Files.newBufferedWriter(outFile, charset, new OpenOption[0]);
       Iterator i$;
       String c;
       Collection chrs = this.mergeStore.keySet();
       List sortChrs = asSortedChrs(chrs);
       for (i$ = sortChrs.iterator(); i$.hasNext(); ) { c = (String)i$.next();
         for (BedMapNoBin.bedData b : (ArrayList<BedMapNoBin.bedData>)this.mergeStore.get(c))
           if (type == 1) writer.write(c + "\t" + b.getStart() + "\t" + b.getEnd() + "\t" + b.getName() + "\n"); else
             writer.write(c + "\t" + b.getStart() + "\t" + b.getEnd() + "\n");
       }
     }
     catch (IOException i)
     {
       
       System.out.println(i);
     } finally {
       try {
         writer.close();
       } catch (IOException i) {
         System.out.println(i);
       }
     }
   }
 
   private boolean isNumeric(String c) { return c.matches("-?\\d+"); }
 
 
   public BedMap getBedMap()
   {
     BedMap ret = new BedMap();
     String c;
     for (Iterator i$ = this.mergeStore.keySet().iterator(); i$.hasNext(); ) { c = (String)i$.next();
       for (BedMapNoBin.bedData b : (ArrayList<BedMapNoBin.bedData>)this.mergeStore.get(c))
         ret.addBedData(new BedSimple(c, b.getStart(), b.getEnd(), b.getName()));
     }
     
     return ret;
   }
 
   public void close()
   {
     this.maps = null;
     this.mergeStore = null;
   }
 
   public class chrCompare
     implements Comparator<String>
   {
     public chrCompare()
     {
     }
 
     public int compare(String o1, String o2)
     {
       if ((o1.contains("chr")) && (o2.contains("chr"))) {
         String v1 = o1.substring(3);
         String v2 = o2.substring(3);
         int i1 = BedMerge.this.isNumeric(v1) ? Integer.parseInt(v1) : 500;
         int i2 = BedMerge.this.isNumeric(v2) ? Integer.parseInt(v2) : 500;
 
         return i1 - i2;
       }
       return o1.compareTo(o2);
     }
   }
 
   public class bedCompare
     implements Comparator<BedMapNoBin.bedData>
   {
     public bedCompare()
     {
     }
 
     public int compare(BedMapNoBin.bedData o1, BedMapNoBin.bedData o2)
     {
       return o1.getStart() - o2.getStart();
     }
   }
 
   private class threadSimpleMerge
     implements Runnable
   {
     private ArrayList<BedMapNoBin.bedData> bArray;
     private int type;
     private String chr;
     private Map<String, ArrayList<BedMapNoBin.bedData>> mergeFinal;
     private ArrayList<BedMapNoBin.bedData> mergeStore;
 
     public threadSimpleMerge(String chr, int i, Map<String, ArrayList<BedMapNoBin.bedData>> type, BedMapNoBin mergestore)
     {
       if (mergestore.containschr(chr)) 
           this.bArray = mergestore.getEntries(chr); 
       else
         throw new NullPointerException();
       this.type = i;
       this.chr = chr;
       this.mergeFinal = type;
     }
 
     public void run()
     {
       Comparator bcomp = new BedMerge.bedCompare();
       Collections.sort(this.bArray, bcomp);
 
       this.mergeStore = new ArrayList(this.bArray.size());
       int prev = -1;
       boolean overlap = false;
       int minStart = 2147483647;
       int maxEnd = 0;
       Map names = new HashMap();
       if (this.type == 1) names.put(((BedMapNoBin.bedData)this.bArray.get(0)).getName(), Integer.valueOf(1));
       for (int i = 0; i < this.bArray.size(); i++) {
         if (prev < 0) {
           prev = i;
         }
         else
         {
           if (ovCount(((BedMapNoBin.bedData)this.bArray.get(prev)).getStart(), ((BedMapNoBin.bedData)this.bArray.get(prev)).getEnd(), ((BedMapNoBin.bedData)this.bArray.get(i)).getStart(), ((BedMapNoBin.bedData)this.bArray.get(i)).getEnd()) >= 0) {
             overlap = true;
             minStart = soonest(((BedMapNoBin.bedData)this.bArray.get(prev)).getStart(), soonest(minStart, ((BedMapNoBin.bedData)this.bArray.get(i)).getStart()));
             maxEnd = latest(((BedMapNoBin.bedData)this.bArray.get(prev)).getEnd(), latest(maxEnd, ((BedMapNoBin.bedData)this.bArray.get(i)).getEnd()));
 
             if (this.type == 1) names.put(((BedMapNoBin.bedData)this.bArray.get(prev)).getName(), Integer.valueOf(1));
             if (this.type == 1) names.put(((BedMapNoBin.bedData)this.bArray.get(i)).getName(), Integer.valueOf(1));
           }
           else if (ovCount(minStart, maxEnd, ((BedMapNoBin.bedData)this.bArray.get(i)).getStart(), ((BedMapNoBin.bedData)this.bArray.get(i)).getEnd()) >= 0) {
             minStart = soonest(minStart, ((BedMapNoBin.bedData)this.bArray.get(i)).getStart());
             maxEnd = latest(maxEnd, ((BedMapNoBin.bedData)this.bArray.get(i)).getEnd());
             if (this.type == 1) names.put(((BedMapNoBin.bedData)this.bArray.get(i)).getName(), Integer.valueOf(1)); 
           }
           else { if ((overlap) && (this.type == 1))
               this.mergeStore.add(new BedMapNoBin.bedData(minStart, maxEnd, catNames(names)));
             else if ((overlap) && (this.type != 1))
               this.mergeStore.add(new BedMapNoBin.bedData(minStart, maxEnd));
             else if ((!overlap) && (this.type == 1))
               this.mergeStore.add(new BedMapNoBin.bedData(((BedMapNoBin.bedData)this.bArray.get(prev)).getStart(), ((BedMapNoBin.bedData)this.bArray.get(prev)).getEnd(), catNames(names)));
             else {
               this.mergeStore.add(new BedMapNoBin.bedData(((BedMapNoBin.bedData)this.bArray.get(prev)).getStart(), ((BedMapNoBin.bedData)this.bArray.get(prev)).getEnd()));
             }
 
             overlap = false;
             minStart = 2147483647;
             maxEnd = 0;
             names.clear();
             if (this.type == 1) names.put(((BedMapNoBin.bedData)this.bArray.get(i)).getName(), Integer.valueOf(1));
           }
           prev = i;
         }
       }
       if ((overlap) && (this.type == 1))
         this.mergeStore.add(new BedMapNoBin.bedData(minStart, maxEnd, catNames(names)));
       else if ((overlap) && (this.type != 1))
         this.mergeStore.add(new BedMapNoBin.bedData(minStart, maxEnd));
       else if ((!overlap) && (this.type == 1))
         this.mergeStore.add(new BedMapNoBin.bedData(((BedMapNoBin.bedData)this.bArray.get(prev)).getStart(), ((BedMapNoBin.bedData)this.bArray.get(prev)).getEnd(), catNames(names)));
       else {
         this.mergeStore.add(new BedMapNoBin.bedData(((BedMapNoBin.bedData)this.bArray.get(prev)).getStart(), ((BedMapNoBin.bedData)this.bArray.get(prev)).getEnd()));
       }
 
       this.mergeFinal.put(this.chr, this.mergeStore);
     }
 
     protected int ovCount(int start1, int end1, int start2, int end2) {
       return soonest(end1, end2) - latest(start1, start2);
     }
     protected int soonest(int a, int b) {
       return a >= b ? b : a;
     }
     protected int latest(int a, int b) {
       return a >= b ? a : b;
     }
     protected String catNames(Map<String, Integer> names) {
       int i = 0;
       String cat = null;
       StringBuilder val = new StringBuilder();
       ArrayList<String> narray = new ArrayList<>();
       narray.addAll(names.keySet());
       Collections.sort(narray);
       for (i = 0; i < narray.size(); i++) {
         if (i == names.size() - 1) {
           val.append(narray.get(i));
         } else {
           val.append(narray.get(i));
           val.append(";");
         }
       }
       cat = val.toString();
       return cat;
     }
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     utils.BedMerge
 * JD-Core Version:    0.6.2
 */