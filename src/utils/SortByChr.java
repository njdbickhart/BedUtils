 package utils;
 
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.Set;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 public class SortByChr
 {
     public static class ChrComp implements Comparator<String>{

        @Override
        public int compare(String t, String t1) {
            Integer v1 = Integer.valueOf(0);
         Integer v2 = Integer.valueOf(0);
         Pattern chrnum = Pattern.compile("[cC]hr(.+)");
         Matcher m1 = chrnum.matcher(t);
         if (m1.find()) {
           v1 = Integer.valueOf(SortByChr.retVal(m1.group(1)));
         }
 
         Matcher m2 = chrnum.matcher(t1);
         if (m2.find()) {
           v2 = Integer.valueOf(SortByChr.retVal(m2.group(1)));
         }
         return v1.compareTo(v2);
        }
         
     }
     public static int GetChrOrder(String chr1, String chr2){
         Integer v1 = Integer.valueOf(0);
         Integer v2 = Integer.valueOf(0);
         Pattern chrnum = Pattern.compile("[cC]hr(.+)");
         Matcher m1 = chrnum.matcher(chr1);
         if (m1.find()) {
           v1 = Integer.valueOf(SortByChr.retVal(m1.group(1)));
         }
 
         Matcher m2 = chrnum.matcher(chr2);
         if (m2.find()) {
           v2 = Integer.valueOf(SortByChr.retVal(m2.group(1)));
         }
         return v1.compareTo(v2);
     }
     
   public static ArrayList<String> ascendingChr(Set<String> chrs)
   {
     Comparator<String> chrcomp = new ChrComp();
     ArrayList retVals = new ArrayList();
     retVals.addAll(chrs);
     Collections.sort(retVals, chrcomp);
     return retVals;
   }
 
   public static int retVal(String s) {
     Pattern chrnum = Pattern.compile("^\\d+");
     Matcher m = chrnum.matcher(s);
     if (m.find()) {
       return Integer.valueOf(s).intValue();
     }
     if ((s.equals("X")) || (s.equals("x")))
       return 500;
     if ((s.equals("Y")) || (s.equals("y")))
       return 501;
     if ((s.equals("M")) || (s.equals("m")))
       return 502;
     if ((s.equals("Z")) || (s.equals("z")))
       return 500;
     if ((s.equals("W")) || (s.equals("w"))) {
       return 501;
     }
     return 503;
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     utils.SortByChr
 * JD-Core Version:    0.6.2
 */