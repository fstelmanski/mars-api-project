package pl.fifi.MarsRoverApp.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Photo {

    private Long id;
    private Integer sol;
    private Camera camera;
    @JsonProperty("img_src")
    private String imgSrc;
    @JsonProperty("earth_date")
    private String earthDate;
    private Rover rover;

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", sol=" + sol +
                ", camera=" + camera +
                ", imgSrc='" + imgSrc + '\'' +
                ", earthDate='" + earthDate + '\'' +
                ", rover=" + rover +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSol() {
        return sol;
    }

    public void setSol(Integer sol) {
        this.sol = sol;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }


}
