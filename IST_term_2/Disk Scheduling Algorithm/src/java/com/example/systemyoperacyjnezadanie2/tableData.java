package com.example.systemyoperacyjnezadanie2;

public class tableData {
    String algorythmName;
    int allHeadMovements;

    public tableData(String algorythmName, int allHeadMovements) {
        this.algorythmName = algorythmName;
        this.allHeadMovements = allHeadMovements;
    }

    public String getAlgorythmName() {
        return algorythmName;
    }

    public void setAlgorythmName(String algorythmName) {
        this.algorythmName = algorythmName;
    }

    public int getAllHeadMovements() {
        return allHeadMovements;
    }

    public void setAllHeadMovements(int allHeadMovements) {
        this.allHeadMovements = allHeadMovements;
    }
}
