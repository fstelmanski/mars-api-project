package pl.fifi.MarsRoverApp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import pl.fifi.MarsRoverApp.dto.BaseDto;
import pl.fifi.MarsRoverApp.response.RoverApiResponse;
import pl.fifi.MarsRoverApp.service.MarsRoverApiService;

import java.lang.reflect.InvocationTargetException;

@Controller
public class BaseController {

    @Autowired
    MarsRoverApiService marsRoverApiService;
    @GetMapping("/")
    String getHomeLook(ModelMap model,Long userId, Boolean createUser) throws InvocationTargetException, IllegalAccessException {
        BaseDto baseDto = createDefaultBaseDto(userId);

        if(Boolean.TRUE.equals(createUser) && userId == null){
            baseDto = marsRoverApiService.save(baseDto);
        } else {
            baseDto = marsRoverApiService.findByUserId(userId);
            if (baseDto == null) {
                baseDto = createDefaultBaseDto(userId);
            }
        }


        RoverApiResponse roverData = marsRoverApiService.getRoverData(baseDto);
        model.put("data",roverData);
        model.put("baseDto",baseDto);
        model.put("validCameras",marsRoverApiService.getValidCameras().get(baseDto.getMarsApiRoverData()));
        if(!Boolean.TRUE.equals(baseDto.getRememberedPreferences()) && userId != null){
            BaseDto defaultBaseDto = createDefaultBaseDto(userId);
            marsRoverApiService.save(defaultBaseDto);
        }

        return "index";
    }

    @GetMapping("/savedPreferences")
    @ResponseBody
    public BaseDto getSavedPreferences (Long userId) {
        if (userId != null)
            return marsRoverApiService.findByUserId(userId);
        else
            return createDefaultBaseDto(userId);
    }

    private BaseDto createDefaultBaseDto(Long userId) {
        BaseDto baseDto = new BaseDto();
        baseDto.setMarsApiRoverData("Opportunity");
        baseDto.setMarsSol(1);
        baseDto.setUserId(userId);
        return baseDto;
    }

    @PostMapping("/")
    public String postHomeView (BaseDto baseDto) {
        baseDto = marsRoverApiService.save(baseDto);
        return "redirect:/?userId="+baseDto.getUserId();
    }

}
