 package file;
 
 public abstract class BedAbstract
   implements Comparable<BedAbstract>
 {
   protected String chr;
   protected int start;
   protected int end;
   protected String name;
   protected int type;
 
   protected boolean isNumeric(String s)
   {
     return s.matches("-?\\d+(.\\d+)?");
   }
 
   protected boolean containsPeriods(String s) {
     boolean period = false;
     if (s.indexOf('.') != -1) period = true;
     return period;
   }
 
   protected boolean hasCommas(String s) {
     return s.matches(",");
   }
 
   protected void initialVals(String seg1, String seg2, String seg3) throws BedFileException {
     this.chr = seg1;
     try {
       this.start = Integer.parseInt(seg2);
     } catch (NumberFormatException i) {
       throw new BedFileException("start", seg2);
     }
     try {
       this.end = Integer.parseInt(seg3);
     } catch (NumberFormatException i) {
       throw new BedFileException("end", seg3);
     }
   }
 
   public String Chr()
   {
     return this.chr;
   }
   public int Start() {
     return this.start;
   }
   public int End() {
     return this.end;
   }
   public String Name() {
     return this.name;
   }
   public int Type() {
     return this.type;
   }
 
   public void setChr(String c)
   {
     this.chr = c;
   }
   public void setStart(int s) {
     this.start = s;
   }
   public void setEnd(int e) {
     this.end = e;
   }
   public void setName(String name) {
     this.name = name;
   }
   public void setType(int t) {
     this.type = t;
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     file.BedAbstract
 * JD-Core Version:    0.6.2
 */