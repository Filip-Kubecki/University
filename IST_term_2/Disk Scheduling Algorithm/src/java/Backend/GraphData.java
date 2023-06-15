package Backend;

import java.util.ArrayList;

public class GraphData {
//    Suma przesunięć głowicy w ciągu czasu trwania algorytmu
    int allHeadMovements;
//    Średni czas oczekiwania na zasoby
    int avgWaitingTime;
//    Procesy z deadlinem które wygasły
    int amountOfKilledRequest;
//    Pozycje głowicy w czasie
    ArrayList<TimeToHeadPosition> timeToHeadPositionArray;
    //    Suma przesunięć głowicy w ciągu czasu trwania algorytmu
    int totalTime;

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public GraphData() {}

    public int getAllHeadMovements() {
        return allHeadMovements;
    }

    public void setAllHeadMovements(int allHeadMovements) {
        this.allHeadMovements = allHeadMovements;
    }

    public int getAvgWaitingTime() {
        return avgWaitingTime;
    }

    public void setAvgWaitingTime(int avgWaitingTime) {
        this.avgWaitingTime = avgWaitingTime;
    }

    public int getAmountOfKilledRequest() {
        return amountOfKilledRequest;
    }

    public void setAmountOfKilledRequest(int amountOfKilledRequest) {
        this.amountOfKilledRequest = amountOfKilledRequest;
    }

    public ArrayList<TimeToHeadPosition> getTimeToHeadPositionArray() {
        return timeToHeadPositionArray;
    }

    public void setTimeToHeadPositionArray(ArrayList<TimeToHeadPosition> timeToHeadPositionArray) {
        this.timeToHeadPositionArray = timeToHeadPositionArray;
    }
}
