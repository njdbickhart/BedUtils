 package utils;
 
 import file.BedFileException;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 public class UCSCToStringArray
 {
   public static String[] UCSCToArray(String ucsc)
     throws BedFileException
   {
     String[] values = new String[3];
     Pattern style = Pattern.compile("(.+):(\\d+)-(\\d+)");
     Matcher match = style.matcher(ucsc);
 
     int x = 0;
     while (match.find()) {
       values[x] = match.group(x + 1);
       x++;
     }
     if ((x < 2) || (x > 2)) {
       throw new BedFileException("Error with UCSC conversion! Attempted UCSC to Bed coercion found only " + x + " matches!");
     }
     return values;
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     utils.UCSCToStringArray
 * JD-Core Version:    0.6.2
 */