/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implement;

import file.BedAbstract;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author desktop
 */
public class BedVariable extends BedAbstract{
    public ArrayList<String> fields = null;

    public BedVariable(String chr, int start, int end){
        this.chr = chr;
        this.start = start;
        this.end = end;
    }
    
    public BedVariable(String chr, int start, int end, String ... a){
        this(chr, start, end);
        fields = new ArrayList<>(a.length);
        fields.addAll(Arrays.asList(a));
    }
    
    public String getOutStr(){
        StringBuilder str = new StringBuilder();
        str.append(chr).append("\t").append(start).append("\t").append(end);
        if(fields != null){
            for(String a : fields){
                str.append("\t").append(a);
            }
        }
        return str.toString();
    }
    
    @Override
    public int compareTo(BedAbstract t) {
        return Start() - t.Start();
    }
    
}
