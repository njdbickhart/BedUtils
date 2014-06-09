 package file;
 
 public class BedFileException extends Exception
 {
   private int bedlength;
   private String field;
   private String value;
   private String msg = "Error with bedfile!";
 
   public BedFileException(int bedlength) {
     this.bedlength = bedlength;
   }
 
   public BedFileException(String field, String value) {
     this.field = field;
     this.value = value;
     this.bedlength = 0;
   }
   public BedFileException(String msg) {
     this.msg = msg;
   }
   public String error() {
     if ((this.bedlength == 0) && (this.field.length() > 0) && (this.value.length() > 0))
       return "This field: " + this.field + " had a value of: " + this.value + "\n";
     if (this.bedlength > 0) {
       return "Bed input length error! Found: " + String.valueOf(this.bedlength) + " fields. Minimum of 3 fields is required!\n";
     }
     return this.msg;
   }
 }

/* Location:           C:\SharedFolders\netbeans_workspace\BedUtils\dist\BedUtils.jar
 * Qualified Name:     file.BedFileException
 * JD-Core Version:    0.6.2
 */