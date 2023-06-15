package Backend;

public class TimeToHeadPosition {
    int time;
    int headPosition;

    public TimeToHeadPosition(int time, int headPosition) {
        this.time = time;
        this.headPosition = headPosition;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public void setHeadPosition(int headPosition) {
        this.headPosition = headPosition;
    }
}
