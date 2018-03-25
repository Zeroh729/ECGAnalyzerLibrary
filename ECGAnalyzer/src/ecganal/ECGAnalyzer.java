package ecganal;

import java.util.ArrayList;

public class ECGAnalyzer {
    private static int[][] data;

    public ECGAnalyzer() {
        System.out.println("Hello world! I am built!");
    }
    
    public static void setData(String s){
        String[] dataline = s.split("\n");
        data = new int[dataline.length][2];
        int i = 0;
        for(String line : dataline){
            String[] xydata = line.split(",");
            data[i][0] = Integer.parseInt(xydata[0]);
            data[i][1] = Integer.parseInt(xydata[1]);
            System.out.println("data loaded: " + data[i][0] + " " + data[i][1]);
            i++;
        }
    }
    
    public static void setData(int[][] data){
        ECGAnalyzer.data = data;
    }
    
    public static int[][] getData(){
        return data;
    }
}
