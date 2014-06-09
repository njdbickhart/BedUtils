 package file;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Set;
 
 public class BedMapNoBin
 {
   protected Map<String, ArrayList<bedData>> mapNoBin;
 
   public BedMapNoBin()
   {
     this.mapNoBin = new HashMap(30);
   }
 
   public void mergeIntoMap(int type, BedMap[] a)
   {
     Iterator i$;
     String chr;
     for (int i = 0; i < a.length; i++) {
       Set chrs = a[i].getListChrs();
       for (i$ = chrs.iterator(); i$.hasNext(); ) { chr = (String)i$.next();
         Set<Integer> bins = a[i].getBins(chr);
         for (Integer b : bins) {
           ArrayList<BedAbstract> bentries = a[i].getBedAbstractList(chr, b);
           for (BedAbstract entry : bentries)
             if (type == 1)
               addToMap(chr, entry.Start(), entry.End(), entry.Name());
             else
               addToMap(chr, entry.Start(), entry.End());
         }
       }
     }
   }
 
   private void addToMap(String chr, int start, int end)
   {
     bedData tmp = new bedData(start, end);
     if (this.mapNoBin.containsKey(chr)) {
       ((ArrayList)this.mapNoBin.get(chr)).add(tmp);
     } else {
       ArrayList tarray = new ArrayList(5000);
       tarray.add(tmp);
       this.mapNoBin.put(chr, tarray);
     }
   }
 
   private void addToMap(String chr, int start, int end, String name) {
     bedData tmp = new bedData(start, end, name);
     if (this.mapNoBin.containsKey(chr)) {
       ((ArrayList)this.mapNoBin.get(chr)).add(tmp);
     } else {
       ArrayList tarray = new ArrayList(5000);
       tarray.add(tmp);
       this.mapNoBin.put(chr, tarray);
     }
   }
 
   public Set<String> getChrs()
   {
     return this.mapNoBin.keySet();
   }
   public ArrayList<bedData> getEntries(String chr) {
     if (this.mapNoBin.containsKey(chr))
       return (ArrayList)this.mapNoBin.get(chr);
     return null;
   }
   public boolean containschr(String chr) {
     if (this.mapNoBin.containsKey(chr)) {
       return true;
     }
     return false;
   }
 
   public int getSizeEntries(String chr) {
     return ((ArrayList)this.mapNoBin.get(chr)).size();
   }
 
   public void dereferenceChr(String chr)
   {
     this.mapNoBin.remove(chr);
   }
 
   public static class bedData
   {
     protected int start;
     protected int end;
     protected String names;
 
     public bedData(int start, int end, String name)
     {
       this.start = start;
       this.end = end;
       this.names = name;
     }
 
     public bedData(int start, int end) {
       this.start = start;
       this.end = end;
       this.names = null;
     }
 
     public int getStart()
     {
       return this.start;
     }
     public int getEnd() {
       return this.end;
     }
     public String getName() {
       return this.names;
     }
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     file.BedMapNoBin
 * JD-Core Version:    0.6.2
 */