package main.Model.Restaurants;

public class Building {
    private int id;
    private String geoposition, name,adres;
    Building(int id, String geoposition, String adres, String name){
        this.id = id;
        this.geoposition = geoposition;
        this.adres = adres;
        this.name = name;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setGeoposition(String geoposition){
        this.geoposition = geoposition;
    }
    public void setAdres(String adres){
        this.adres = adres;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getAdres(){
        return adres;
    }
    public String getGeoposition(){
        return geoposition;
    }
    Building(){
        id = 0;
        name = geoposition = name = null;
    }


}