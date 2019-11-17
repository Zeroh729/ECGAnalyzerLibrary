# ECGAnalyzerLibrary
This is the library used in the ECGGrapher Mobile app.

## What does this library do?
When the ECGGrapher Mobile app receives ECG signals from the Arduino device, the app calls this library to analyze the ECG signals with the following:
1. Count BPM
2. Signal Processing (not yet working; for finding R peaks)
3. Finding R Peaks (implemented using simple threshold for now)
4. Count RR intervals that are not in normal range (0.6 - 1.2 seconds)
