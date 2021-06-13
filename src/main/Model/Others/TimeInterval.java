package main.Model.Others;

public class TimeInterval{
    private int startHour, startMinute, endHour, endMinute;
    public TimeInterval(){
        startHour = startMinute = endHour = endMinute = 0;
    }
    public TimeInterval(int sHour, int sMinute, int eHour, int eMinute) throws Exception{
        if(!(0 <= sHour && sHour < 24 && 0 <= sMinute && sMinute < 60)){
            throw new Exception("Bad time");
        }
        if(!(0 <= eHour && eHour < 24 && 0 <= eMinute && eMinute < 60)){
            throw new Exception("Bad time");
        }
        this.startHour = sHour;
        this.endHour = eHour;
        this.startMinute = sMinute;
        this.endMinute = eMinute;
    }

    public int getStartHour(){return this.startHour;}
    public int getEndHour(){return this.endHour;}
    public int getStartMinute(){return this.startMinute;}
    public int getEndMinute(){return this.endMinute;}


    public void setStartHour(int hour) throws Exception{
        if(!(0 <= hour && hour < 24)){
            throw new Exception("Bad hour");
        }
        this.startHour = hour;
    }

    public void setEndHour(int hour) throws Exception{
        if(!(0 <= hour && hour < 24)){
            throw new Exception("Bad hour");
        }
        this.endHour = hour;
    }

    public void setStartMinute(int minute) throws Exception{
        if(!(0 <= minute && minute < 24)){
            throw new Exception("Bad Minute");
        }
        this.startMinute = minute;
    }

    public void setEndMinute(int minute) throws Exception{
        if(!(0 <= minute && minute < 24)){
            throw new Exception("Bad Minute");
        }
        this.endMinute = minute;
    }
}
