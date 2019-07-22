package ecganal;

import java.util.ArrayList;

public class DSPFunction {
    private static double[][] input;
    private static double[][] output;
    private static ArrayList<Integer> rpeaksI;
    
    public enum Type{
        LOWPASSFILTER, HIGHPASSFILTER, DERIVATIVE, SQUARING, ALL
    }
    
    public double[][] execute(Type type, double[][] data) {
        input = data;
        output = initializeOutputArray();
        switch(type){
            case LOWPASSFILTER:
                System.out.println("LOW PASS FILTER APPLIED: \t\tOriginal\t\tFiltered");
                return lowPassFilter();
            case HIGHPASSFILTER:
                System.out.println("\n\nHIGH PASS FILTER APPLIED: \t\tOriginal\t\tFiltered");
                return highPassFilter();
            case DERIVATIVE:
                System.out.println("\n\nDERIVATIVE APPLIED: \t\tOriginal\t\tFiltered");
                return derivative();
            case SQUARING:
                System.out.println("\n\nSQUARING APPLIED: \t\tOriginal\t\tFiltered");
                return squaring();
            default:
                allFilter();
                return output;
        }
    }
    
    private double[][] allFilter(){
//        output = derivative();
//        lowPassFilter();
        output = highPassFilter();
        Filter filter = new Filter();
        for(int i = 0; i < input.length; i++){
//            output[i][1] =  filter.diffFilterNext(output[i][1]);
//            output[i][1] =  filter.LowPassFilter(input[i][1]);
//            output[i][1] =  filter.HighPassFilter(output[i][1]);

            output[i][1] =  filter.squareNext(output[i][1]);
//            output[i][1] =  filter.movingWindowNext(output[i][1]);

//              System.out.println(output[i][1] +"");
      }
        return output;
    }
    
    private double[][] lowPassFilter(){
        double[] x = new double[26];
        double ydelay1 = 0;
        double ydelay2 = 0;
        int n = 12;

        for(int i=0; i < input.length; i++){
            x[n] = x[n+13] = input[i][1];

            output[i][1] = input[i][1] + (-2 * x[n+6]) + (1 * x[n+12])
                                      + (2 * ydelay1) + (-1 * ydelay2);
            ydelay2 = ydelay1;
            ydelay1 = output[i][1]; 
            output[i][1] /= 36;

            if(--n < 0){
                n = 12;
            }
            logSignal(i);
        }
        return output;
    }

    private double[][] highPassFilter() {
        double[] x = new double[66];
        double ydelay1 = 0;
        int n = 32;
        
        for(int i = 0; i < input.length; i++){
            x[n] = x[n+33] = input[i][1];
            output[i][1] = ydelay1 + input[i][1] - x[n+32];
            
            ydelay1 = output[i][1];
            output[i][1] = x[n+16] - (output[i][1]/32);
            
            if(--n < 0){
                n = 32;
            }                
            logSignal(i);
        }
        return output;
    }
    
    private double[][] derivative(){ //Found in Java code
        double[] diffCoeff = {-0.1250, -0.2500, 0, 0.2500, 0.1250} ;
        double[] x = new double[diffCoeff.length];
        System.arraycopy(diffCoeff, 0, x, 1, diffCoeff.length-1);
        for(int i = 0; i < input.length; i++){
            output[i][1] = 0;
            x[0] = input[i][1];
 
            for(int j = 0; j < diffCoeff.length; j++){
                output[i][1] = output[i][1] + (float)x[j] * (float)diffCoeff[j];
            }
//            logSignal(i);
        }
        return output;
    }
    
    private double[][] derivative2(){ //Found in Paper
        double xdelay1 = 0;
        double xdelay2 = 0;
        double xdelay3 = 0;
        for(int i = 0; i < input.length; i++){
            if(i >= 1){
                xdelay1 = input[i-1][1];
            }
            if(i >= 3){
                xdelay2 = input[i-3][1]; 
           }
            if(i >= 4){
                xdelay3 = input[i-4][1];
            }
            output[i][1] = (0.1f) * (2 + xdelay1 - xdelay2 - (2 * xdelay3));
//            logSignal(i);
        }
        return output;
    }
    
    private double[][] squaring(){
        for(int i = 0; i < input.length; i++){
            output[i][1] = (float)Math.pow((double)input[i][1],2);
            logSignal(i);
        }
        return output;
    }

    
    private void logSignal(int i){
//        System.out.println("signal["+i+"]" + "\t\t\t" + input[i][1] + "\t\t"+ output[i][1]);
    }
    
    private double[][] initializeOutputArray() {
        output = new double[input.length][2];
        for(int i = 0; i < input.length; i++){
            output[i][0] = input[i][0];
        }
        return output;
    }
    
    public ArrayList<Integer> detectRPeaks(){
        int upflag = 0;
        rpeaksI = new ArrayList<>();
        ArrayList<Integer> rpeaksInterval = new ArrayList<>();
        
        for(int i = 0; i < input.length; i++){
            if(upflag == 0){
                if(output[i][1] > 1){
                    rpeaksI.add(i);
                    upflag = 20;
                }
            }else upflag--;
        }
        System.out.println("R peaks: " + rpeaksI);
        
        return rpeaksI;
    }
    
    public int calculateBPM(){
        int bpm = 0;
        double firstRpeakTime = input[rpeaksI.get(0)][0];
        double lastRpeakTime = input[rpeaksI.get(rpeaksI.size()-1)][0];
        double duration = lastRpeakTime - firstRpeakTime;
        int beats = rpeaksI.size() - 1;  
        bpm = (int)Math.round((beats*60)/(duration/1000.0));
        return bpm;
    }
}
