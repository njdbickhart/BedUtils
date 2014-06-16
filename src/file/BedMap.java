 package file;
 
 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.nio.charset.Charset;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.Collections;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Set;
 import java.util.concurrent.ConcurrentHashMap;
 import utils.BinBed;
 
 public class BedMap <T extends BedAbstract>
 {
   protected Map<String, ConcurrentHashMap<Integer, ArrayList<T>>> bedFile;
   private Path inFile;
   private int type;
 
   public BedMap()
   {
     this.bedFile = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, ArrayList<T>>>();
   }
   public BedMap(Path inFile, int type) {
     this.bedFile = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, ArrayList<T>>>();
     this.inFile = inFile;
     this.type = type;
     try {
       loadFileContents(type);
     } catch (BedFileException ex) {
       System.out.println(ex);
       System.out.println(ex.error());
     } catch (IOException io) {
       System.out.println(io);
     }
   }
 
   private void loadFileContents()
     throws BedFileException, IOException
   {
     Charset charset = Charset.forName("UTF-8");
     BufferedReader reader = null;
     try {
       reader = Files.newBufferedReader(this.inFile, charset);
       String line;
       while ((line = reader.readLine()) != null) {
         BedAbstract bedObject = new BedEntry(line);
         String[] segments = line.split("\\t");
         int bin = BinBed.getBin(bedObject.Start(), bedObject.End());
         if (this.bedFile.containsKey(segments[0])) {
           if (((ConcurrentHashMap)this.bedFile.get(segments[0])).containsKey(Integer.valueOf(bin))) {
             ((ArrayList)((ConcurrentHashMap)this.bedFile.get(segments[0])).get(Integer.valueOf(bin))).add(bedObject);
           } else {
             ArrayList temp = new ArrayList(100);
             temp.add(bedObject);
             ((ConcurrentHashMap)this.bedFile.get(segments[0])).put(Integer.valueOf(bin), temp);
           }
         } else {
           ArrayList temp = new ArrayList(100);
           temp.add(bedObject);
           ConcurrentHashMap thash = new ConcurrentHashMap(3000);
           thash.put(Integer.valueOf(bin), temp);
           this.bedFile.put(segments[0], thash);
         }
       }
     } catch (IOException e) {
       System.out.println(e);
     } catch (NullPointerException e) {
       System.out.println(e);
     } finally {
       reader.close();
     }
   }
 
   private void loadFileContents(int type) throws BedFileException, IOException { 
       Charset charset = Charset.forName("UTF-8");
     BufferedReader reader = null;
     try {
       reader = Files.newBufferedReader(this.inFile, charset);
       String line;
       while ((line = reader.readLine()) != null)
       {
         BedAbstract bedObject;
         String[] segments;
         if ((type == 0) || (type == 1)) {
           segments = line.split("\\t");
           if (type == 0)
             bedObject = new BedSimple(segments[0], Integer.parseInt(segments[1]), Integer.parseInt(segments[2]));
           else
             bedObject = new BedSimple(segments[0], Integer.parseInt(segments[1]), Integer.parseInt(segments[2]), segments[3]);
         }
         else {
           segments = line.split("\\t");
           bedObject = new BedEntry(line, type);
         }
 
         int bin = BinBed.getBin(bedObject.Start(), bedObject.End());
         if (this.bedFile.containsKey(segments[0])) {
           if (((ConcurrentHashMap)this.bedFile.get(segments[0])).containsKey(Integer.valueOf(bin))) {
             ((ArrayList)((ConcurrentHashMap)this.bedFile.get(segments[0])).get(Integer.valueOf(bin))).add(bedObject);
           } else {
             ArrayList temp = new ArrayList(100);
             temp.add(bedObject);
             ((ConcurrentHashMap)this.bedFile.get(segments[0])).put(Integer.valueOf(bin), temp);
           }
         } else {
           ArrayList temp = new ArrayList(100);
           temp.add(bedObject);
           ConcurrentHashMap thash = new ConcurrentHashMap(3000);
           thash.put(Integer.valueOf(bin), temp);
           this.bedFile.put(segments[0], thash);
         }
       }
     } catch (IOException e) {
       System.out.println(e);
     } catch (NullPointerException e) {
       System.out.println(e);
     } finally {
       reader.close();
     }
   }
 
   private T loadLine(String line)
   {
     T bedObject = null;
     String[] segments = line.split("\\t");
     if (this.type == 0) {
       bedObject = (T) new BedSimple(segments[0], Integer.parseInt(segments[1]), Integer.parseInt(segments[2]));
     }
     return bedObject;
   }
 
   public void setFile(Path file)
   {
     this.inFile = file;
   }
   public void addBedData(String chr, Integer bin, T bed) {
     if (this.bedFile.containsKey(chr)) {
       if (((ConcurrentHashMap)this.bedFile.get(chr)).containsKey(bin)) {
         ((ArrayList)((ConcurrentHashMap)this.bedFile.get(chr)).get(bin)).add(bed);
       } else {
         ArrayList temp = new ArrayList(100);
         temp.add(bed);
         ((ConcurrentHashMap)this.bedFile.get(chr)).put(bin, temp);
       }
     } else {
       ArrayList temp = new ArrayList(100);
       temp.add(bed);
       ConcurrentHashMap thash = new ConcurrentHashMap(3000);
       thash.put(bin, temp);
       this.bedFile.put(chr, thash);
     }
   }
 
   public void addBedData(String chr, T bed) { int bin = BinBed.getBin(bed.Start(), bed.End());
     if (this.bedFile.containsKey(chr)) {
       if (((ConcurrentHashMap)this.bedFile.get(chr)).containsKey(Integer.valueOf(bin))) {
         ((ArrayList)((ConcurrentHashMap)this.bedFile.get(chr)).get(Integer.valueOf(bin))).add(bed);
       } else {
         ArrayList temp = new ArrayList(100);
         temp.add(bed);
         ((ConcurrentHashMap)this.bedFile.get(chr)).put(Integer.valueOf(bin), temp);
       }
     } else {
       ArrayList temp = new ArrayList(100);
       temp.add(bed);
       ConcurrentHashMap thash = new ConcurrentHashMap(3000);
       thash.put(Integer.valueOf(bin), temp);
       this.bedFile.put(chr, thash);
     } }
 
   public void addBedData(T bed) {
     String chr = bed.Chr();
     int bin = BinBed.getBin(bed.Start(), bed.End());
     if (this.bedFile.containsKey(chr)) {
       if (((ConcurrentHashMap)this.bedFile.get(chr)).containsKey(Integer.valueOf(bin))) {
         ((ArrayList)((ConcurrentHashMap)this.bedFile.get(chr)).get(Integer.valueOf(bin))).add(bed);
       } else {
         ArrayList temp = new ArrayList(100);
         temp.add(bed);
         ((ConcurrentHashMap)this.bedFile.get(chr)).put(Integer.valueOf(bin), temp);
       }
     } else {
       ArrayList temp = new ArrayList(100);
       temp.add(bed);
       ConcurrentHashMap thash = new ConcurrentHashMap(3000);
       thash.put(Integer.valueOf(bin), temp);
       this.bedFile.put(chr, thash);
     }
   }
 
   public void combineBedMaps(BedMap<T> map) { 
       String chr;
       int bins;
       for (Iterator i$ = map.getListChrs().iterator(); i$.hasNext(); ) { 
           chr = (String)i$.next();
       for (i$ = map.getBins(chr).iterator(); i$.hasNext(); ) { 
           bins = ((Integer)i$.next()).intValue();
           for (T bed : map.getBedAbstractList(chr, Integer.valueOf(bins)))
            addBedData(chr, Integer.valueOf(bins), bed);  }  
       }  
   } 
   public void removeBedData(T bed, int bin, int i) throws BedFileException {
     String chr = bed.Chr();
     if (this.bedFile.containsKey(chr)) {
       if (((ConcurrentHashMap)this.bedFile.get(chr)).containsKey(Integer.valueOf(bin)))
         ((ArrayList)((ConcurrentHashMap)this.bedFile.get(chr)).get(Integer.valueOf(bin))).remove(i);
       else
         throw new BedFileException("Could not remove entry from BedMap! Does not have current bin of bed.");
     }
     else
       throw new BedFileException("Could not remove entry from BedMap! Bad Chr request.");
   }
 
   public Path getFile()
   {
     return this.inFile;
   }
   public T getNextBed(String chr, Integer bin, int iterator) {
     return this.bedFile.get(chr).get(bin).get(iterator);
   }
   public int getNumBedEntries(String chr, Integer bin) {
     if (this.bedFile.containsKey(chr)) {
       if (((ConcurrentHashMap)this.bedFile.get(chr)).containsKey(bin)) return ((ArrayList)((ConcurrentHashMap)this.bedFile.get(chr)).get(bin)).size();
       return 0;
     }return 0;
   }
   public Set<String> getListChrs() {
     Set<String> chrs = this.bedFile.keySet();
     return chrs;
   }
 
   public ArrayList<T> getBedAbstractList(String chr, Integer bin) {
     if (this.bedFile.containsKey(chr)) {
       if (((ConcurrentHashMap)this.bedFile.get(chr)).containsKey(bin)) 
           return (ArrayList<T>)this.bedFile.get(chr).get(bin);
       return null;
     }return null;
   }
 
   public ArrayList<T> getSortedBedAbstractList(String chr) {
     if (this.bedFile.containsKey(chr)) {
       ArrayList<T> sorted = new ArrayList();
       Set bins = ((ConcurrentHashMap)this.bedFile.get(chr)).keySet();
       for (Iterator i$ = bins.iterator(); i$.hasNext(); ) { int b = ((Integer)i$.next()).intValue();
         sorted.addAll((Collection)((ConcurrentHashMap)this.bedFile.get(chr)).get(Integer.valueOf(b)));
       }
       Collections.sort(sorted);
       return sorted;
     }
     return null;
   }
 
   public Set<Integer> getBins(String chr) {
     Set bins = ((ConcurrentHashMap)this.bedFile.get(chr)).keySet();
     return bins;
   }
   public Map<String, ConcurrentHashMap<Integer, ArrayList<T>>> mapDump() {
     return this.bedFile;
   }
 
   public boolean containsBin(String chr, int i)
   {
     if (((ConcurrentHashMap)this.bedFile.get(chr)).containsKey(Integer.valueOf(i))) return true;
     return false;
   }
   public boolean containsChr(String chr) {
     if (this.bedFile.containsKey(chr)) return true;
     return false;
   }
   public boolean isEmpty() {
     return this.bedFile.isEmpty();
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     file.BedMap
 * JD-Core Version:    0.6.2
 */