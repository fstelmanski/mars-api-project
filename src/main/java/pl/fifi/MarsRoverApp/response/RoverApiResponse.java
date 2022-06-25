package pl.fifi.MarsRoverApp.response;

import pl.fifi.MarsRoverApp.response.Photo;

import java.util.ArrayList;
import java.util.List;

public class RoverApiResponse {

    List<Photo> photos = new ArrayList<Photo>();

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "RoverApiResponse{" +
                "photos=" + photos +
                '}';
    }
}
