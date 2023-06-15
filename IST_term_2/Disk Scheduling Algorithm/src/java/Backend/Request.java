package Backend;

public class Request implements Comparable<Request> {
    int lp;
    int czasOczekiwania;
    int sektorDysku;
    int momentZgloszenia;
    int czasWykonania;
    int deadline;
    boolean haveDeadline;

    public boolean isHaveDeadline() {
        return haveDeadline;
    }

    public void setHaveDeadline(boolean haveDeadline) {
        this.haveDeadline = haveDeadline;
    }

    public Request(int lp, int sektorDysku, int momentZgloszenia, int czasWykonania, boolean haveDeadline) {
        this.lp = lp;
        this.czasOczekiwania = 0;
        this.sektorDysku = sektorDysku;
        this.momentZgloszenia = momentZgloszenia;
        this.czasWykonania = czasWykonania;
        this.haveDeadline = haveDeadline;
    }
    public Request(int lp, int sektorDysku, int momentZgloszenia, int czasWykonania, int deadline, boolean haveDeadline) {
        this.lp = lp;
        this.czasOczekiwania = 0;
        this.sektorDysku = sektorDysku;
        this.momentZgloszenia = momentZgloszenia;
        this.czasWykonania = czasWykonania;
        this.deadline = deadline;
        this.haveDeadline = haveDeadline;
    }
    public int getCzasOczekiwania() {
        return czasOczekiwania;
    }
    public void setCzasOczekiwania(int czasOczekiwania) {
        this.czasOczekiwania = czasOczekiwania;
    }
    public void IncreaseCzasOczekiwania(int waitingTime){
        czasOczekiwania += waitingTime;
    }
    public int getSektorDysku() {
        return sektorDysku;
    }
    public int getMomentZgloszenia() {
        return momentZgloszenia;
    }
    public int getCzasWykonania() {
        return czasWykonania;
    }
    public int getDeadline() {
        return deadline;
    }
    public boolean endOfDeadline(){
        return this.deadline <= 0;
    }
    public void decreaseDeadline(int waitingTime){
        this.deadline -= waitingTime;
    }
    @Override
    public int compareTo(Request o) {
        return Integer.compare(this.deadline, o.getDeadline());
    }
}
