 package utils;
 
 import file.BedFileException;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 public class UCSCToStringArray
 {
   public static String[] UCSCToArray(String ucsc)
     throws BedFileException
   {
       ucsc = ucsc.trim();
     String[] values = new String[3];
     Pattern style = Pattern.compile("(.+):(\\d+)-(\\d+)");
     Matcher match = style.matcher(ucsc);
 
     int x = 0;
     while (match.find()) {
       values[0] = match.group(1);
       values[1] = match.group(2);
       values[2] = match.group(3);
       x++;
     }
     if (x < 1) {
       throw new BedFileException("Error with UCSC conversion! Attempted UCSC to Bed coercion found only: " + values[0] + " " + values[1] + " " + values[2] + "!");
     }
     return values;
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     utils.UCSCToStringArray
 * JD-Core Version:    0.6.2
 */