package main.Application;

public class Building {
    private int id;
    private String geoposition, name,adres;
    Building(int id, String geoposition, String adres, String name){
        this.id = id;
        this.geoposition = geoposition;
        this.adres = adres;
        this.name = name;
    }
    void setId(int id){
        this.id = id;
    }
    void setName(String name){
        this.name = name;
    }
    void setGeoposition(String geoposition){
        this.geoposition = geoposition;
    }
    void setAdres(String adres){
        this.adres = adres;
    }
    int getId(){
        return id;
    }
    String getName(){
        return name;
    }
    String getAdres(){
        return adres;
    }
    String getGeoposition(){
        return geoposition;
    }
    Building(){
        id = 0;
        name = geoposition = name = null;
    }


}
