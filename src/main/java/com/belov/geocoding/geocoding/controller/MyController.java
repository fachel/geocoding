package com.belov.geocoding.geocoding.controller;

import com.belov.geocoding.geocoding.entity.Exception_;
import com.belov.geocoding.geocoding.entity.Request;
import com.belov.geocoding.geocoding.service.ExceptionService;
import com.belov.geocoding.geocoding.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class MyController {

    private RestTemplate restTemplate;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ExceptionService exceptionService;

    @Value("${api.geocoding.key}")
    private String APIKEY;

    private final String URL = "https://geocode-maps.yandex.ru/1.x/?apikey=";

    @GetMapping("/api/address/{address}")
    public JSONObject[] getCoordinates(@PathVariable String address,
                                              @RequestParam (value = "results", required = false) String results,
                                              @RequestParam (value = "skip", required = false) String skip) throws ParseException {
        log.info("[MyController] getCoordinates() starts with parameters: results = {}, skip = {}", results, skip);
        restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(URL + APIKEY + "&format=json&geocode=" + address, String.class);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        jsonObject = (JSONObject) jsonObject.get("response");
        jsonObject = (JSONObject) jsonObject.get("GeoObjectCollection");
        JSONArray array = (JSONArray) jsonObject.get("featureMember");
        if (results == null && skip == null){
            JSONObject[] objects = new JSONObject[array.size()];
            for (int i = 0; i < array.size(); i++){
                objects[i] = (JSONObject) ((JSONObject) ((JSONObject) array.get(i)).get("GeoObject")).get("Point");
                requestService.save(new Request(address, objects[i].toString().substring(8, objects[i].toString().length()-2)));
            }
            log.info("[MyController] getCoordinates() finished without parameters");
            return objects;
        }
        else if (results != null && skip == null){
            int len;
            if (Integer.parseInt(results) >= array.size())
                len = array.size();
            else
                len = Integer.parseInt(results);
            JSONObject[] objects = new JSONObject[len];
            for (int i = 0; i < Integer.parseInt(results); i++) {
                if (i == array.size())
                    return objects;
                objects[i] = (JSONObject) ((JSONObject) ((JSONObject) array.get(i)).get("GeoObject")).get("Point");
                requestService.save(new Request(address, objects[i].toString().substring(8, objects[i].toString().length()-2)));
            }
            log.info("[MyController] getCoordinates() finished with parameter results = {}", results);
            return objects;
        }
        else {
            int len;
            if (Integer.parseInt(results) + Integer.parseInt(skip) > array.size())
                return null;
            else
                len = Integer.parseInt(results);
            JSONObject[] objects = new JSONObject[len];
            int j = 0;
            for (int i = Integer.parseInt(skip); i < Integer.parseInt(skip) + Integer.parseInt(results); i++) {
                objects[j] = (JSONObject) ((JSONObject) ((JSONObject) array.get(i)).get("GeoObject")).get("Point");
                requestService.save(new Request(address, objects[j].toString().substring(8, objects[j].toString().length()-2)));
                j++;
            }
            log.info("[MyController] getCoordinates() finished with parameters results = {}, skip = {}", results, skip);
            return objects;
        }
    }


    @GetMapping("/api/coordinates/{coordinates}")
    public String[] getAddress(@PathVariable String coordinates,
                               @RequestParam (value = "results", required = false) String results,
                               @RequestParam (value = "skip", required = false) String skip) throws ParseException {
        log.info("[MyController] getAddress() starts with parameters: results = {}, skip = {}", results, skip);
        restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(URL + APIKEY + "&format=json&geocode=" + coordinates, String.class);
        JSONParser parser = new JSONParser();
        int a = 2 / 0;
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        jsonObject = (JSONObject) jsonObject.get("response");
        jsonObject = (JSONObject) jsonObject.get("GeoObjectCollection");
        JSONArray array = (JSONArray) jsonObject.get("featureMember");
        if (results == null && skip == null) {
            String[] addresses = new String[array.size()];
            for (int i = 0; i < array.size(); i++) {
                jsonObject =  (JSONObject) array.get(i);
                jsonObject = (JSONObject) jsonObject.get("GeoObject");
                jsonObject = (JSONObject) jsonObject.get("metaDataProperty");
                jsonObject = (JSONObject) jsonObject.get("GeocoderMetaData");
                String address = (String) jsonObject.get("text");
                addresses[i] = address;
                requestService.save(new Request(coordinates, addresses[i]));
            }
            log.info("[MyController] getCoordinates() finished without parameters");
            return addresses;
        } else if (results != null && skip == null) {
            int len;
            if (Integer.parseInt(results) >= array.size())
                len = array.size();
            else
                len = Integer.parseInt(results);
            String[] addresses = new String[len];
            for (int i = 0; i < Integer.parseInt(results); i++) {
                if (i == array.size())
                    return addresses;
                jsonObject = (JSONObject) array.get(i);
                jsonObject = (JSONObject) jsonObject.get("GeoObject");
                jsonObject = (JSONObject) jsonObject.get("metaDataProperty");
                jsonObject = (JSONObject) jsonObject.get("GeocoderMetaData");
                String address = (String) jsonObject.get("text");
                addresses[i] = address;
                requestService.save(new Request(coordinates, addresses[i]));
            }
            log.info("[MyController] getCoordinates() finished with parameter results = {}", results);
            return addresses;
        }
        else {
            int len;
            if (Integer.parseInt(results) + Integer.parseInt(skip) > array.size())
                return null;
            else
                len = Integer.parseInt(results);
            String[] addresses = new String[len];
            int j = 0;
            for (int i = Integer.parseInt(skip); i < Integer.parseInt(skip) + Integer.parseInt(results); i++) {
                jsonObject = (JSONObject) array.get(i);
                jsonObject = (JSONObject) jsonObject.get("GeoObject");
                jsonObject = (JSONObject) jsonObject.get("metaDataProperty");
                jsonObject = (JSONObject) jsonObject.get("GeocoderMetaData");
                String address = (String) jsonObject.get("text");
                addresses[j] = address;
                requestService.save(new Request(coordinates, addresses[j]));
                j++;
            }
            log.info("[MyController] getCoordinates() finished with parameters results = {}, skip = {}", results, skip);
            return addresses;
        }
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        log.error(e.getClass() + " Message: " + e.getMessage());
        exceptionService.save(new Exception_(e.getClass().toString(), e.getMessage()));
    }
}
