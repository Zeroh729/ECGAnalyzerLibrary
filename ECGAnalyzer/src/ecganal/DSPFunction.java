package ecganal;

public class DSPFunction {

    public enum Type{
        LOWPASSFILTER
    }
    public static float[][] execute(Type type, float[][] data) {
        switch(type){
            case LOWPASSFILTER:
                System.out.println("LOW PASS FILTER APPLIED: \t\tOriginal\t\tFiltered");
                return lowPassFilter(data);
            default:
                return initializeOutputArray(0);
        }
    }
    
    private static float[][] lowPassFilter(float[][] data){
        float[][] output = initializeOutputArray(data.length);
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
            logSignal(i, data[i][1], output[i][1]);
        }
        return output;
    }
    
    private static void logSignal(int i, float input, float output){
        System.out.println("signal["+i+"]" + "\t\t\t" + input + "\t\t"+ output);
    }
    
    private static float[][] initializeOutputArray(int length) {
        return new float[length][2];
    }
}
