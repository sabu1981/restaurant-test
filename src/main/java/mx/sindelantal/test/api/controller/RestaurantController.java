package mx.sindelantal.test.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import mx.sindelantal.test.annotation.RestApiController;
import mx.sindelantal.test.model.Restaurant;
import mx.sindelantal.test.model.filter.RestaurantFilter;
import mx.sindelantal.test.model.response.JsonResponse;
import mx.sindelantal.test.service.RestaurantService;

@RestApiController("apiRestaurants")
@RequestMapping("/restaurant")
public class RestaurantController {
    
    private final RestaurantService restaurantService;
    
    @Autowired
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JsonResponse list() {
    	
        JsonResponse result = new JsonResponse();

        List<Restaurant> restaurants = restaurantService.listRestaurants();
        if (restaurants.isEmpty()) {
            result.setMessage("No restaurants found!");
        } else {
            result.setMessage("List of restaurants");
        }
        result.setBinaryStatus("success");
        result.setStatus(HttpStatus.OK);
        result.setResult(restaurants);
        return result;
    }

    @PostMapping("/search")
    public JsonResponse search(@RequestBody RestaurantFilter restaurantFilter) {
        JsonResponse result = new JsonResponse();

        List<Restaurant> restaurants = restaurantService.searchRestaurants(restaurantFilter);
        if (restaurants.isEmpty()) {
            result.setMessage("No restaurants found!");
        } else {
            result.setMessage("List of restaurants");
        }
        result.setBinaryStatus("success");
        result.setStatus(HttpStatus.OK);
        result.setResult(restaurants);
        return result;

    }
    
    @PostMapping("/create")
    public JsonResponse create(@RequestBody Restaurant restaurant) {

        JsonResponse result = new JsonResponse();

        restaurantService.create(restaurant);
        result.setStatus(HttpStatus.CREATED);
    	result.setBinaryStatus("success");
    	result.setMessage("Restaurant succesfully created");

        return result;

    }
    
    @RequestMapping(value = "/thirdExercise", method = RequestMethod.GET)
    public JsonResponse thirdExercise() {
    	
        JsonResponse result = new JsonResponse();
        
        List<Restaurant> restaurants = restaurantService.listRestaurants3rdExercise();
        if (restaurants.isEmpty()) {
            result.setMessage("No restaurants found!");
        } else {
            result.setMessage("List of restaurants");
        }
        result.setBinaryStatus("success");
        result.setStatus(HttpStatus.OK);
        result.setResult(restaurants);
        return result;
    }

}