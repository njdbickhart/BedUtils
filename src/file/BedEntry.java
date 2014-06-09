 package file;
 
 import java.util.Arrays;
 import java.util.concurrent.atomic.AtomicInteger;
 
 public class BedEntry extends BedAbstract
 {
   private int score;
   private char strand;
   private int thickStart;
   private int thickEnd;
   private String color;
   private int blockCount;
   private int[] blockSizes;
   private int[] blockStarts;
   private String[] otherinfo;
   private float gc;
   AtomicInteger hits;
   private float cn;
 
   public BedEntry(String chr, int start, int end)
   {
     this.chr = chr;
     this.start = start;
     this.end = end;
     this.type = 0;
   }
 
   public BedEntry(String chr, int start, int end, String name) {
     this.chr = chr;
     this.start = start;
     this.end = end;
     this.name = name;
     this.type = 1;
   }
 
   public BedEntry(String chr, int start, int end, int score) {
     this.chr = chr;
     this.start = start;
     this.end = end;
     this.score = score;
     this.type = 2;
   }
 
   public BedEntry(String chr, int start, int end, String name, int score) {
     this.chr = chr;
     this.start = start;
     this.end = end;
     this.name = name;
     this.score = score;
     this.type = 3;
   }
 
   public BedEntry(String chr, int start, int end, String name, int score, char strand, int thickStart, int thickEnd, String color, int blockCount, int[] blockSizes, int[] blockStarts) {
     this.chr = chr;
     this.start = start;
     this.end = end;
     this.name = name;
     this.strand = strand;
     this.thickStart = thickStart;
     this.thickEnd = thickEnd;
     this.color = color;
     this.blockCount = blockCount;
     this.blockSizes = blockSizes;
     this.blockStarts = blockStarts;
     this.type = 4;
   }
 
   public BedEntry(String chr, int start, int end, String[] otherinfo) {
     this.chr = chr;
     this.start = start;
     this.end = end;
     this.otherinfo = otherinfo;
     this.type = 5;
   }
   public BedEntry(String chr, int start, int end, float gc, AtomicInteger hits) {
     this.chr = chr;
     this.start = start;
     this.end = end;
     this.gc = gc;
     this.hits = hits;
     this.type = 6;
   }
   public BedEntry(String chr, int start, int end, float gc) {
     this.chr = chr;
     this.start = start;
     this.end = end;
     this.gc = gc;
     this.type = 7;
   }
 
   public BedEntry(String line) throws BedFileException {
     line = line.replace("\n", "");
     line = line.replace("\r", "");
     String[] segments = line.split("\\t");
     if (segments.length < 3)
       throw new BedFileException(segments.length);
     if (segments.length > 12) {
       String[] subsegs = (String[])Arrays.copyOfRange(segments, 3, segments.length);
       initialVals(segments[0], segments[1], segments[2]);
       this.otherinfo = subsegs;
       this.type = 5;
     } else if (segments.length == 3) {
       initialVals(segments[0], segments[1], segments[2]);
       this.type = 0;
     } else if (segments.length == 4) {
       initialVals(segments[0], segments[1], segments[2]);
       if ((isNumeric(segments[3])) && (!containsPeriods(segments[3]))) {
         this.score = Integer.parseInt(segments[3]);
         this.type = 2;
       } else {
         this.name = segments[3];
         this.type = 1;
       }
     } else if (segments.length == 5) {
       initialVals(segments[0], segments[1], segments[2]);
       if ((isNumeric(segments[3])) || (isNumeric(segments[4]))) {
         if (isNumeric(segments[3])) {
           this.score = Integer.parseInt(segments[3]);
           this.name = segments[4];
           this.type = 3;
         } else if (isNumeric(segments[4])) {
           this.score = Integer.parseInt(segments[4]);
           this.name = segments[3];
           this.type = 3;
         }
       } else {
         String[] subsegs = (String[])Arrays.copyOfRange(segments, 3, segments.length);
         this.otherinfo = subsegs;
         this.type = 5;
       }
     } else if ((segments.length > 5) && (segments.length <= 12)) {
       if ((isNumeric(segments[4])) && (isNumeric(segments[6])) && (isNumeric(segments[7])) && (isNumeric(segments[9])) && (((hasCommas(segments[10])) && (hasCommas(segments[11]))) || ((isNumeric(segments[10])) && (isNumeric(segments[11])))))
       {
         this.name = segments[3];
         this.score = Integer.parseInt(segments[4]);
         this.strand = segments[5].charAt(0);
         this.thickStart = Integer.parseInt(segments[6]);
         this.thickEnd = Integer.parseInt(segments[7]);
         this.color = segments[8];
         this.blockCount = Integer.parseInt(segments[9]);
         this.blockSizes = getIntArray(segments[10]);
         this.blockStarts = getIntArray(segments[11]);
         this.type = 4;
       } else {
         String[] subsegs = (String[])Arrays.copyOfRange(segments, 3, segments.length);
         this.otherinfo = subsegs;
         this.type = 5;
       }
     }
   }
 
   public BedEntry(String line, int type) {
     try {
       switch (type) {
       case 0:
         typeZero(line);
         break;
       case 1:
         typeOne(line);
       }
     }
     catch (BedFileException bedFileException) {
       bedFileException.error();
     }
   }
 
   private void typeZero(String line)
     throws BedFileException
   {
     line = line.replace("\n", "");
     line = line.replace("\r", "");
     String[] segments = line.split("\\t");
     initialVals(segments[0], segments[1], segments[2]);
     this.type = 0;
   }
   private void typeOne(String line) throws BedFileException {
     line = line.replace("\n", "");
     line = line.replace("\r", "");
     String[] segments = line.split("\\t");
     initialVals(segments[0], segments[1], segments[2]);
     this.name = segments[3];
     this.type = 1;
   }
 
   private int[] getIntArray(String s)
   {
     String[] segs = s.split(",");
     int[] rval = new int[segs.length];
     for (int i = 0; i < segs.length; i++) {
       rval[i] = Integer.parseInt(segs[i]);
     }
     return rval;
   }
 
   public void incHits()
   {
     this.hits.getAndIncrement();
   }
 
   public int Score()
   {
     return this.score;
   }
   public String[] Other() {
     return this.otherinfo;
   }
   public int Type() {
     return this.type;
   }
   public float Gc() {
     return this.gc;
   }
   public AtomicInteger Hits() {
     return this.hits;
   }
   public float CN() {
     return this.cn;
   }
 
   public void setOther(String[] s)
   {
     this.otherinfo = s;
   }
   public void setCN(float cn) {
     this.cn = cn;
   }
 
   public int compareTo(BedAbstract o)
   {
     return this.start - o.Start();
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     file.BedEntry
 * JD-Core Version:    0.6.2
 */