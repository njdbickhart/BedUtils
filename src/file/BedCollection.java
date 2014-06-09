 package file;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Set;
 import utils.BinBed;
 
 public class BedCollection
 {
   protected Map<String, HashMap<Integer, ArrayList<bedStore>>> collection;
 
   BedCollection(BedEntry b)
   {
     this.collection = new HashMap(30);
   }
 
   public void insertIntoCollection(BedEntry b)
   {
     int bin = BinBed.getBin(b.Start(), b.End());
   }
 
   public Set<String> getChrs()
   {
     return this.collection.keySet();
   }
   public int getStart(String chr, int bin, int index) {
     return ((bedStore)((ArrayList)((HashMap)this.collection.get(chr)).get(Integer.valueOf(bin))).get(index)).start;
   }
   public int getEnd(String chr, int bin, int index) {
     return ((bedStore)((ArrayList)((HashMap)this.collection.get(chr)).get(Integer.valueOf(bin))).get(index)).end;
   }
   public Set<Integer> getBins(String chr) {
     return ((HashMap)this.collection.get(chr)).keySet();
   }
   public ArrayList<bedStore> getList(String chr, int bin) {
     return (ArrayList)((HashMap)this.collection.get(chr)).get(Integer.valueOf(bin));
   }
 
   private void insertNovelCollection(BedEntry b, int bin)
   {
     bedStore bstore = new bedStore(b.Start(), b.End(), b.Name());
     if (this.collection.containsKey(b.Chr())) {
       if (((HashMap)this.collection.get(b.Chr())).containsKey(Integer.valueOf(bin))) {
         ((ArrayList)((HashMap)this.collection.get(b.Chr())).get(Integer.valueOf(bin))).add(bstore);
       } else {
         ArrayList temp = new ArrayList(100);
         temp.add(bstore);
         ((HashMap)this.collection.get(b.Chr())).put(Integer.valueOf(bin), temp);
       }
     } else {
       ArrayList temp = new ArrayList(100);
       temp.add(bstore);
       HashMap thash = new HashMap(2000);
       thash.put(Integer.valueOf(bin), temp);
       this.collection.put(b.Chr(), thash);
     }
   }
 
   private void extendExistingCollection(BedEntry b, int bin) {
   }
 
   private boolean overlapsExistingData(BedEntry b) { Set bins = BinBed.getBins(b.Start(), b.End());
     boolean overlapped = false;
     for (Iterator i$ = bins.iterator(); i$.hasNext(); ) { int i = ((Integer)i$.next()).intValue();
       for (bedStore s : getList(b.Chr(), i)) {
         if ((s.start < b.End()) && (s.end > b.Start())) {
           overlapped = true;
         }
       }
     }
 
     return overlapped;
   }
 
   protected class bedStore
   {
     int bin;
     int start;
     int end;
     ArrayList<String> names;
 
     protected bedStore(int start, int end, String name)
     {
       this.start = start;
       this.end = end;
       int b = BinBed.getBin(start, end);
       this.bin = b;
       this.names = new ArrayList(5);
       this.names.add(name);
     }
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     file.BedCollection
 * JD-Core Version:    0.6.2
 */