public class Proces implements Comparable<Proces>{
    private int lp;
    private int phaseLength;
    private int reportTime;
    private int waitingTime;

    public Proces(int numer, int dlugoscFazy, int momentZgloszenia) {
        this.lp = numer;
        this.phaseLength = dlugoscFazy;
        this.reportTime = momentZgloszenia;
        this.waitingTime = 0;
    }

    public int getNumer() {
        return lp;
    }

    public int getPhaseLength() {
        return phaseLength;
    }

    public int getMomentZgloszenia() {
        return reportTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setPhaseLength(int phaseLength) {
        this.phaseLength = phaseLength;
    }

    @Override
    public String toString() {
        return this.lp + " " + this.phaseLength + " " + this.reportTime;
    }

    @Override
    public int compareTo(Proces o) {
        return Integer.compare(getPhaseLength(), o.getPhaseLength());
    }
}
