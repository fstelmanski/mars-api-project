package pl.fifi.MarsRoverApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.fifi.MarsRoverApp.dto.BaseDto;
import pl.fifi.MarsRoverApp.repository.MarsApiRepository;
import pl.fifi.MarsRoverApp.response.Photo;
import pl.fifi.MarsRoverApp.response.RoverApiResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class MarsRoverApiService {
    private static final String API_KEY = "etRNWeDX6bxjU6CIr5BBKhBNy9XCBgfx9GYYjQ5T";

    private Map<String, List<String>> validCameras = new HashMap<>();

    @Autowired
    MarsApiRepository marsApiRepository;

    public MarsRoverApiService () {
        validCameras.put("Opportunity", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
        validCameras.put("Curiosity", Arrays.asList("FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM"));
        validCameras.put("Spirit", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
    }

    public RoverApiResponse getRoverData(BaseDto baseDto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        RestTemplate rt = new RestTemplate();

        List<String> apiUrlEnpoints = getApiUrlEnpoints(baseDto);
        List<Photo> photos = new ArrayList<>();
        RoverApiResponse response = new RoverApiResponse();

        apiUrlEnpoints.stream()
                .forEach(url -> {
                    RoverApiResponse apiResponse = rt.getForObject(url, RoverApiResponse.class);
                    assert apiResponse != null;
                    photos.addAll(apiResponse.getPhotos());
                });

        response.setPhotos(photos);

        return response;
    }

    public Map<String, List<String>> getValidCameras() {
        return validCameras;
    }

    public void setValidCameras(Map<String, List<String>> validCameras) {
        this.validCameras = validCameras;
    }

    public List<String> getApiUrlEnpoints (BaseDto baseDto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<String> urls = new ArrayList<>();

        Method[] methods = baseDto.getClass().getMethods();

        // Pobiera wszystkie metody zaczynające się od getCamera* i jeśli zwraca prawdę to buduje API url
        // by filtrować i wyświetlać wszystkie zdjęcia po danym łaziku, kamerze i solu.
        for (Method method : methods) {
            if (method.getName().contains("getCamera") && Boolean.TRUE.equals(method.invoke(baseDto))) {
                String cameraName = method.getName().split("getCamera")[1].toUpperCase();
                if (validCameras.get(baseDto.getMarsApiRoverData()).contains(cameraName)) {
                    urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+baseDto.getMarsApiRoverData()+"/photos?sol="+baseDto.getMarsSol()+"&api_key=" + API_KEY + "&camera=" + cameraName);
                }
            }
        }

        return urls;
    }

    public BaseDto save(BaseDto homeDto) {
        return marsApiRepository.save(homeDto);
    }

    public BaseDto findByUserId(Long userId) {
        return marsApiRepository.findByUserId(userId);
    }
}
