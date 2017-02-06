/**
 */
class Report {
    private long fullTime;
    private long averageTime;
    private int total;
    private int passed;

    public String createReport() {
        String result = "--------------Report----------------\n";
        result += "Total tests: " + this.getTotal() + "\n";
        result += "Passed/Failed: " + this.getPassed() + "/" + (this.getTotal() - this.getPassed()) + "\n";
        result += "Total time:" + this.getFullTime() + "\n";
        result += "Average time: " + this.getFullTime() / this.getTotal() + "\n";
        return result;

    }

    //-----getters and setters---------
    public double getAverageTime() {
        return milsToSec(averageTime);
    }

    public void setAverageTime(long averageTime) {
        this.averageTime = averageTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPassed() {
        return passed;
    }


    public void setPassed(int passed) {
        this.passed = passed;
    }


    public double getFullTime() {
        return milsToSec(fullTime);
    }

    public void addTime(long time){
        this.fullTime+=time;
    }

    public void setFullTime(long fullTime) {
        this.fullTime = fullTime;
    }

    public double milsToSec(long milliseconds) {
        return ((double) milliseconds / 1000);
    }


}
