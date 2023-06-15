package Backend;

import java.util.ArrayList;
import java.util.List;

public class Results {
    private int avgLoad;
    private int deviation;
    private int migrationCount;
    private int queryCount;
    private int time;
    private List<Integer> allLoads;

    public Results(int avgLoad, int deviation, int migrationCount, int queryCount, int time, List<Integer> allLoads) {
        this.avgLoad = avgLoad;
        this.deviation = deviation;
        this.migrationCount = migrationCount;
        this.queryCount = queryCount;
        this.time = time;
        this.allLoads = allLoads;
    }

    public Results() {
    }

    public List<Integer> getAllLoads() {
        return allLoads;
    }

    public void setAllLoads(List<Integer> allLoads) {
        this.allLoads = allLoads;
    }

    public int getAvgLoad() {
        return avgLoad;
    }

    public int getDeviation() {
        return deviation;
    }

    public int getMigrationCount() {
        return migrationCount;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public int getTime() {
        return time;
    }

    public void setAvgLoad(int avgLoad) {
        this.avgLoad = avgLoad;
    }

    public void setDeviation(int deviation) {
        this.deviation = deviation;
    }

    public void setMigrationCount(int migrationCount) {
        this.migrationCount = migrationCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void increaseAvgLoad(int value){
        avgLoad+=value;
    }

    public void increaseMigrationCount(){
        migrationCount++;
    }
    public void increaseQueryCount(){
         queryCount++;
    }
    public void calculateDeviation(){
        int temp = 0;
        for (Integer value : allLoads) {
            temp += Math.pow(value-avgLoad,2);
        }
        deviation = (int) Math.sqrt((double) temp /allLoads.size());
    }

    @Override
    public String toString() {
        String out = "Avg CPU load:" + avgLoad+ " +- "+ deviation + "\nTime:" + time + "\nQueries:" + queryCount+ "\nMigrations:" + migrationCount;
        return out;
    }

    public String toCSV(){
        String out = avgLoad+ ","+ deviation + "," + time + "," + queryCount+ "," + migrationCount;
        return out;
    }


}
