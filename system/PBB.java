package system;

public class PBB {
    private String name;
    private int startTime;
    private int endTime;
    private int finishTime=0;
    private int turnoverTime;
    private double responseTime=0;
    private double powerTime;

    public PBB(String name, int startTime, int endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getTurnoverTime() {
        return turnoverTime;
    }

    public void setTurnoverTime(int turnoverTime) {
        this.turnoverTime = turnoverTime;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    public double getPowerTime() {
        return powerTime;
    }

    public void setPowerTime(double powerTime) {
        this.powerTime = powerTime;
    }

    @Override
    public String toString() {
        return "PBB{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", finishTime=" + finishTime +
                ", turnoverTime=" + turnoverTime +
                ", responseTime=" + responseTime +
                ", powerTime=" + powerTime +
                '}';
    }
}
