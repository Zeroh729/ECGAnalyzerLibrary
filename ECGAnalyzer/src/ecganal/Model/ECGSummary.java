package ecganal.Model;

import java.util.ArrayList;

public class ECGSummary {
    private double[][] originalData;
    private ArrayList<Integer> pIndices = new ArrayList<>();
    private ArrayList<Integer> qIndices = new ArrayList<>();
    private ArrayList<Integer> rIndices = new ArrayList<>();
    private ArrayList<Integer> sIndices = new ArrayList<>();
    private ArrayList<Integer> tIndices = new ArrayList<>();
    private ArrayList<Integer> rrIntervals = new ArrayList<>();
    private int bpm;
    private int pqInterval;
    private int qrsDuration;
    private int deviatingRRIcount;
    private static final int RRI_MS_MIN = 600;
    private static final int RRI_MS_MAX = 1200;

    public ECGSummary(double[][] originalData) {
        this.originalData = originalData;
    }

    public double[][] getOriginalData() {
        return originalData;
    }
    
    public ArrayList<Integer> getpIndices() {
        return pIndices;
    }

    public void setpIndices(ArrayList<Integer> pIndices) {
        this.pIndices = pIndices;
    }

    public ArrayList<Integer> getqIndices() {
        return qIndices;
    }

    public void setqIndices(ArrayList<Integer> qIndices) {
        this.qIndices = qIndices;
    }

    public ArrayList<Integer> getrIndices() {
        return rIndices;
    }

    public void setrIndices(ArrayList<Integer> rIndices) {
        this.rIndices = rIndices;
        deviatingRRIcount = 0;
        for(int i = 1; i < rIndices.size(); i++){
            int interval = (int)(originalData[rIndices.get(i)][0] - originalData[rIndices.get(i-1)][0]);
            if(interval < RRI_MS_MIN || interval > RRI_MS_MAX){
                deviatingRRIcount++;
            }
            this.rrIntervals.add(interval);
        }
    }
    
    public ArrayList<Integer> getRRIntervals(){
        return rrIntervals;
    }
    
    public int getDeviatingRRIcount(){
        return deviatingRRIcount;
    }

    public ArrayList<Integer> getsIndices() {
        return sIndices;
    }

    public void setsIndices(ArrayList<Integer> sIndices) {
        this.sIndices = sIndices;
    }

    public ArrayList<Integer> gettIndices() {
        return tIndices;
    }

    public void settIndices(ArrayList<Integer> tIndices) {
        this.tIndices = tIndices;
    }
    
    
    public void setBPM(int bpm){
        this.bpm = bpm;
    }
    
    public int getBPM(){
        return bpm;
    }

    public int getPqInterval() {
        return pqInterval;
    }

    public void setPqInterval(int pqInterval) {
        this.pqInterval = pqInterval;
    }

    public int getQrsDuration() {
        return qrsDuration;
    }

    public void setQrsDuration(int qrsDuration) {
        this.qrsDuration = qrsDuration;
    }
    
}
