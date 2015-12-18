/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import file.BedAbstract;
import file.BedMap;
import file.BedSimple;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Adding a very crude implementation right now only to speed up the process
 * @author Derek.Bickhart
 */
public class BedClosest <T extends BedSimple>{
    private final int regionExpand;
    private final LineIntersect<T> intersect = new LineIntersect<>();
    
    public BedClosest(int distance){
        this.regionExpand = distance;
    }
    
    public BedMap<BedCompare> RetrieveClosestNameComp(BedMap<T> db, Path file){
        BedMap<BedCompare> data = new BedMap<>();
        try(BufferedReader input = Files.newBufferedReader(file, Charset.defaultCharset())){
            String line;
            while((line = input.readLine()) != null){
                line = line.trim();
                String[] segs = line.split("\t");
                
                BedSimple bed = new BedSimple(segs[0], Integer.parseInt(segs[1]), Integer.parseInt(segs[2]), segs[3]);
                int rStart = bed.Start() - regionExpand;
                int rEnd = bed.End() + regionExpand;
                
                if(rStart < 0){
                    rStart = 0;
                }
                
                List<T> query = this.intersect.returnTypeIntersect(db, bed.Chr(), rStart, rEnd);
                T closest = null; 
                int maxov = (regionExpand + 2) * -1;
                
                // get number of overlapping bases and keep only the entry that has the highest overlap count
                for(T b : query){
                    if(!b.Name().equals(bed.Name()))
                        continue;
                    int current = LineIntersect.ovCount(bed.Start(), bed.End(), b.Start(), b.End());
                    if(current > maxov){
                        closest = b;
                        maxov = current;
                    }
                }
                
                if(closest == null){
                    closest = (T) new BedSimple("NA", 0, 0, "NA");
                }
                
                data.addBedData(new BedCompare(bed.Chr(), bed.Start(), bed.End(), bed.Name(), closest));
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return data;
    }
    
    public BedMap<BedCompare> RetrieveClosest(BedMap<T> db, Path file){
        BedMap<BedCompare> data = new BedMap<>();
        try(BufferedReader input = Files.newBufferedReader(file, Charset.defaultCharset())){
            String line;
            while((line = input.readLine()) != null){
                line = line.trim();
                String[] segs = line.split("\t");
                
                BedSimple bed = new BedSimple(segs[0], Integer.parseInt(segs[1]), Integer.parseInt(segs[2]), segs[3]);
                int rStart = bed.Start() - regionExpand;
                int rEnd = bed.End() + regionExpand;
                
                if(rStart < 0){
                    rStart = 0;
                }
                
                List<T> query = this.intersect.returnTypeIntersect(db, bed.Chr(), rStart, rEnd);
                T closest = null; 
                int maxov = (regionExpand + 2) * -1;
                
                // get number of overlapping bases and keep only the entry that has the highest overlap count
                for(T b : query){
                    int current = LineIntersect.ovCount(bed.Start(), bed.End(), b.Start(), b.End());
                    if(current > maxov){
                        closest = b;
                        maxov = current;
                    }
                }
                
                if(closest == null){
                    closest = (T) new BedSimple("NA", 0, 0, "NA");
                }
                
                data.addBedData(new BedCompare(bed.Chr(), bed.Start(), bed.End(), bed.Name(), closest));
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return data;
    }
    
    public class BedCompare <T extends BedSimple> extends BedAbstract {
        private final BedSimple bed;
        
        public BedCompare(String chr, int start, int end, String name, T bed){
            this.chr = chr;
            this.start = start;
            this.end = end;
            this.name = name;
            this.bed = bed;
        }

        @Override
        public int compareTo(BedAbstract t) {
            return this.start - t.Start();
        }
        
        public List<String> getOutStringList(boolean getDistance){
            List<String> values = new ArrayList<>();
            values.add(chr); 
            values.add(String.valueOf(start));
            values.add(String.valueOf(end));
            values.add(name);
            
            values.add(bed.Chr());
            values.add(String.valueOf(bed.Start()));
            values.add(String.valueOf(bed.End()));
            values.add(bed.Name());
            
            if(getDistance){
                if(bed.Start() == 0 && bed.End() == 0)
                    values.add(String.valueOf(-1));
                else{
                    int min = (start > bed.End())? bed.End() : end;
                    int max = (end > bed.Start())? start : bed.Start();
                    values.add(String.valueOf(max - min));
                }
            }
            
            return values;
        }
    }
}
