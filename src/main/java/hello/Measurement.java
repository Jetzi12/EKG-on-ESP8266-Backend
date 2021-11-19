package hello;
public class Measurement {
    public String[] x;
    public String[] t;
    public String name;
    public String date;
    public Object[] bpm;
    public Object[] qrs;
    //====================================
    public Object[] getBpm() {
        return bpm;
    }

    public void setBpm(Object[] bpm) {
        this.bpm = bpm;
    }
    //====================================

    public Object[] getQrs() {
        return qrs;
    }

    public void setQrs(Object[] qrs) {
        this.qrs = qrs;
    }
    //====================================

    public String[] getX() {
        return x;
    }

    public void setX(String[] x) {
        this.x = x;
    }

    //====================================

    public String[] getT() {
        return t;
    }

    public void setT(String[] t) {
        this.t = t;
    }

    //====================================

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    //====================================

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }
}
