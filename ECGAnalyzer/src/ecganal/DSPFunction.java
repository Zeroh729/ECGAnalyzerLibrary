package ecganal;

public class DSPFunction {
    public enum Type{
        LOWPASSFILTER
    }
    public static float[][] execute(Type type, float[][] data) {
        float[][] output = new float[data.length][2];
        
        switch(type){
            case LOWPASSFILTER:
                System.out.println("LOW PASS FILTER APPLIED: \t\tOriginal\t\tFiltered");
                float[] x = new float[26];
                float ydelay1 = 0;
                float ydelay2 = 0;
                int n = 12;
                
                for(int i=0; i < data.length; i++){
                    output[i][0] = data[i][0];
                    
                    x[n] = x[n+13] = data[i][1];
                    
                    output[i][1] = data[i][1] + (-2 * x[n+6]) + (1 * x[n+12])
                                              + (2 * ydelay1) + (-1 * ydelay2);
                    ydelay2 = ydelay1;
                    ydelay1 = output[i][1]; 
                    output[i][1] /= 32;
                    
                    if(--n < 0){
                        n = 12;
                    }
                    
                    System.out.println("signal["+i+"]" + "\t\t\t" + data[i][1] + "\t\t"+output[i][1]);
                }
            break;
        }
        return output;
    }
}
