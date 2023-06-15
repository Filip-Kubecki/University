package Backend;

import java.util.ArrayList;

public class CPU {
    private int lp;
    private int resourcesInUse;//Poziom wykorzystania procesora — zakres od 0 do 100
    private ArrayList<Process> tasks;//Lista procesów czekających na wykonanie
    private ArrayList<Process> inProgress;//Lista procesów w trakcie wykonywania

    public CPU(int lp, ArrayList<Process> tasks) {
        this.lp = lp;
        this.resourcesInUse = 0;
        this.tasks = tasks;
        this.inProgress = new ArrayList<>();
    }

    public int getLp() {
        return lp;
    }

    public int getResourcesInUse() {
        return resourcesInUse;
    }

    public ArrayList<Process> getTasks() {
        return tasks;
    }

    public ArrayList<Process> getInProgress() {
        return inProgress;
    }

    public void setResourcesInUse(int resourcesInUse) {
        this.resourcesInUse = resourcesInUse;
    }

    public void setTasks(ArrayList<Process> tasks) {
        this.tasks = tasks;
    }

    public void setInProgress(ArrayList<Process> inProgress) {
        this.inProgress = inProgress;
    }

    public void addToInProgress(Process p){
        inProgress.add(p);
        resourcesInUse+=p.getLoad();
    }

    public void doOneCycle(){
        for (int i = inProgress.size()-1; i > -1; i--) {
            inProgress.get(i).decreaseExecutionTime();
            if (inProgress.get(i).getExecutionTime() <= 0){
                resourcesInUse -= inProgress.get(i).getLoad();
                inProgress.remove(inProgress.get(i));
            }
        }
    }

    public Process giveBackProcess(){
        resourcesInUse-=inProgress.get(0).getLoad();
        return inProgress.remove(0);
    }
}
