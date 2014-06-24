 package file;
 
 /**
 * This is the abstract superclass for all BedUtils objects. It is designed to 
 * be compatible with all BedUtils methods and tools. 
 * @author bickhart
 */
public abstract class BedAbstract
   implements Comparable<BedAbstract>
 {
    /**
     * Object chromosome
     */
    protected String chr;
    /**
     * Object start bp coordinate
     */
    protected int start;
    /**
     * Object end bp coordinate
     */
    protected int end;
    /**
     * Object String value or name (ie. gene name)
     */
    protected String name;
    /**
     * An integer denoting different types. Default is to have a "named bed" be a "1"
     */
    protected int type;
 
    /**
     * Determines if the value is numeric or is a String. Useful prior to conversion
     * @param s The string to check
     * @return
     */
    protected boolean isNumeric(String s)
   {
     return s.matches("-?\\d+(.\\d+)?");
   }
 
    /**
     * Determines if the String has periods in it. Useful for determining floating point values.
     * @param s The String to check
     * @return
     */
    protected boolean containsPeriods(String s) {
     boolean period = false;
     if (s.indexOf('.') != -1) period = true;
     return period;
   }
 
    /**
     * Determines if the String has commas in it. This helps in integer conversion.
     * @param s The String to check
     * @return
     */
    protected boolean hasCommas(String s) {
     return s.matches(",");
   }
 
    /**
     * An initializer method that can take the first three line segments and populate the 
     * chr, start and end values of the bed object.
     * @param seg1 the "chromosome" segment of the input file
     * @param seg2 the "start coordinate" String
     * @param seg3 the "end coordinate" string
     * @throws BedFileException
     */
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
 
    /**
     * Getter for the chromosome field
     * @return The object's chromosome
     */
    public String Chr()
   {
     return this.chr;
   }
    /**
     * Getter for the start value
     * @return The object's start coordinate
     */
    public int Start() {
     return this.start;
   }
    /**
     * Getter for the end value
     * @return The object's end coordinate
     */
    public int End() {
     return this.end;
   }
    /**
     * Getter for the name attribute
     * @return The String "name" of the object
     */
    public String Name() {
     return this.name;
   }
    /**
     * Getter for the integer value type of the bed object
     * @return A simple integer denoting bed type. Dependent on object implementation.
     */
    public int Type() {
     return this.type;
   }
 
    /**
     * Setter for the chromosome attribute
     * @param c The string to set for the chromosome
     */
    public void setChr(String c)
   {
     this.chr = c;
   }
    /**
     * Setter for the start coordinate
     * @param s An integer bp start value
     */
    public void setStart(int s) {
     this.start = s;
   }
    /**
     * Setter for the end coordinate
     * @param e An integer bp end value
     */
    public void setEnd(int e) {
     this.end = e;
   }
    /**
     * Setter for the name of the object
     * @param name A String name for this object
     */
    public void setName(String name) {
     this.name = name;
   }
    /**
     * Setter for the Bed Type
     * @param t A small integer. 0 is a simple bed, 1 is a named bed, etc.
     */
    public void setType(int t) {
     this.type = t;
   }
 }