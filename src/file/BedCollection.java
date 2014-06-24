 package file;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import utils.BinBed;
 
 /**
 * This is a specialized construct that is lighter weight than the BedMap class.
 * Yet to be implemented fully.
 * @author bickhart
 */
public class BedCollection
 {
    /**
     * The main data structure that contains the simple bed data
     */
    protected Map<String, HashMap<Integer, ArrayList<bedStore>>> collection;
 
   BedCollection(BedEntry b)
   {
     this.collection = new HashMap(30);
   }
 
    /**
     * 
     * @param b
     */
    public void insertIntoCollection(BedEntry b)
   {
     int bin = BinBed.getBin(b.Start(), b.End());
   }
 
    /**
     *
     * @return
     */
    public Set<String> getChrs()
   {
     return this.collection.keySet();
   }
    /**
     *
     * @param chr
     * @param bin
     * @param index
     * @return
     */
    public int getStart(String chr, int bin, int index) {
     return ((bedStore)((ArrayList)((HashMap)this.collection.get(chr)).get(Integer.valueOf(bin))).get(index)).start;
   }
    /**
     *
     * @param chr
     * @param bin
     * @param index
     * @return
     */
    public int getEnd(String chr, int bin, int index) {
     return ((bedStore)((ArrayList)((HashMap)this.collection.get(chr)).get(Integer.valueOf(bin))).get(index)).end;
   }
    /**
     *
     * @param chr
     * @return
     */
    public Set<Integer> getBins(String chr) {
     return ((HashMap)this.collection.get(chr)).keySet();
   }
    /**
     *
     * @param chr
     * @param bin
     * @return
     */
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
 
    /**
     *
     */
    protected class bedStore
   {
     int bin;
     int start;
     int end;
     ArrayList<String> names;
 
        /**
         *
         * @param start
         * @param end
         * @param name
         */
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