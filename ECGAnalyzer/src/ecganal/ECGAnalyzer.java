package ecganal;

import ecganal.Callback.ECGAnalysisCallback;
import ecganal.Model.ECGSummary;
import java.util.ArrayList;

public class ECGAnalyzer {
    private static double[][] data;
    private static DSPFunction sigProcess;
    private static final ECGAnalyzer instance = new ECGAnalyzer();

    public ECGAnalyzer() {
        System.out.println("ECGAnalyzer says: \"Hello world! I am built!\"");
        sigProcess = new DSPFunction();
    }
    
    public static ECGAnalyzer getInstance(){
        return instance;
    }
    
    public void analyzeData(String s, ECGAnalysisCallback callback){
        ECGSummary summary = new ECGSummary();
        
        setData(s);                                                 //1. Signal Processing
        sigProcess.execute(DSPFunction.Type.ALL, getData());
        
        sigProcess.detectRPeaks();                                  //2. Detection

        summary.setBPM(sigProcess.calculateBPM());                  //3. ECG Summary
        callback.onSuccess(summary);
    }
    
    private static void setData(String s){
        String[] dataline = s.split("\n");
        data = new double[dataline.length][2];
        int i = 0;
        for(String line : dataline){
            String[] xydata = line.split(",");
            data[i][0] = Integer.parseInt(xydata[0]);
            data[i][1] = Integer.parseInt(xydata[1])/1024f * 5;
//            System.out.println("data loaded: " + data[i][0] + " " + data[i][1]);
            i++;
        }
    }
    
    private static void setData(double[][] data){
        ECGAnalyzer.data = data;
    }
    
    private static double[][] getData(){
        return data;
    }
}
