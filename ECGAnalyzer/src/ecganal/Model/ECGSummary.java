package ecganal.Model;

public class ECGSummary {
    private int bpm;
    //private int P,Q,R,S,T wave whatever
    
    public void setBPM(int bpm){
        this.bpm = bpm;
    }
    
    public int getBPM(){
        return bpm;
    }
}
