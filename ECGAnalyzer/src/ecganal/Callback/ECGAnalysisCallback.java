package ecganal.Callback;

import ecganal.Model.ECGSummary;

public interface ECGAnalysisCallback {
    public void onSuccess(ECGSummary summary);
    public void onFail(String message);
}
