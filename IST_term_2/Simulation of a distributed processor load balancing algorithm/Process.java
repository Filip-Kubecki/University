package Backend;

public class Process {
    private int lp;
    private int load;//Obciążenie procesora
    private int executionTime;//Czas potrzebny na wykonanie
    private int reportTime;//Moment pojawienia się w kolejce
    private int waitingTime;//Czas oczekiwania

    public Process(int lp, int load, int executionTime, int reportTime) {
        this.lp = lp;
        this.load = load;
        this.executionTime = executionTime;
        this.reportTime = reportTime;
        this.waitingTime = 0;
    }

    public int getLp() {
        return lp;
    }

    public int getLoad() {
        return load;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public int getReportTime() {
        return reportTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void increaseWaitingTime(int value){
        this.waitingTime += value;
    }

    public void increaseWaitingTime(){
        this.waitingTime++;
    }
    public void decreaseExecutionTime(){
        this.executionTime--;
    }
}
