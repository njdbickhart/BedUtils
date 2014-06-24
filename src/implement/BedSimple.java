 package implement;

import file.*;

 public class BedSimple extends BedAbstract
 {
   public BedSimple(String c, int s, int e, String name)
   {
     this.chr = c;
     this.start = s;
     this.end = e;
     this.name = name;
     this.type = 1;
   }
   public BedSimple(String c, int s, int e) {
     this.chr = c;
     this.start = s;
     this.end = e;
     this.type = 0;
   }
 
   public int compareTo(BedAbstract o)
   {
     return Start() - o.Start();
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     file.BedSimple
 * JD-Core Version:    0.6.2
 */