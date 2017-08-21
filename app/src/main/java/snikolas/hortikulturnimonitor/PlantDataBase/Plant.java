package snikolas.hortikulturnimonitor.PlantDataBase;

import android.graphics.drawable.Drawable;

/**
 * Created by Sara on 30.5.2017..
 */

public class Plant {
    private long id;
    private String title;
    private String description;
    private byte[] image;

    private String time;
    private int temperature;
    private int soil;
    private int sun;

    //novo za opis
    private String seed;
    private String watering;
    private String sunlight;
    private String bestTemperature;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTime(){return time;}
    public void setTime(String time){
        this.time=time;
    }
    public int getTemperature(){
        return temperature;
    }
    public void setTemperature(int temperature){
        this.temperature=temperature;
    }
    public int getSoil(){return soil;}
    public void setSoil(int soil){
        this.soil=soil;
    }
    public int getSun(){return sun;}
    public void setSun(int sun){
        this.sun=sun;
    }

    public String getSeed(){return seed;}
    public void setSeed(String seed){
        this.seed=seed;
    }
    public String getWatering(){return watering;}
    public void setWatering(String watering){
        this.watering=watering;
    }
    public String getSunlight(){return sunlight;}
    public void setSunlight(String sunlight){
        this.sunlight=sunlight;
    }
    public String getBestTemperature(){return bestTemperature;}
    public void setBestTemperature(String bestTemperature){
        this.bestTemperature=bestTemperature;
    }

}
