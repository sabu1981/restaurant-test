package mx.sindelantal.test.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import mx.sindelantal.test.model.Restaurant;
import mx.sindelantal.test.dao.RestaurantDAO;
import mx.sindelantal.test.model.filter.RestaurantFilter;

@Service
public class RestaurantService {
    private RestaurantDAO restaurantDAO;
    private OrderService orderService;
    
    @Autowired
    public RestaurantService(RestaurantDAO restaurantDAO, OrderService orderService) {
        this.restaurantDAO = restaurantDAO;
        this.orderService = orderService;
    }
    public List<Restaurant> listRestaurants(){
        return restaurantDAO.listRestaurants();
    }
    public List<Restaurant> listRestaurantsWithOrders(){
        List<Restaurant> restaurants = restaurantDAO.listRestaurantsWithOrders();
        for(Restaurant restaurant : restaurants){
            restaurant.setOrders(orderService.listOrdersByRestaurantId(restaurant.getId()));
        }
        return restaurants;
    }
    public List<Restaurant> searchRestaurants(RestaurantFilter restaurantFilter){
        return restaurantDAO.searchRestaurants(restaurantFilter);
    }
    public void create(Restaurant restaurant){
    	restaurantDAO.create(restaurant);
    }
    public Restaurant findByName(String name){
    	try {
    		return restaurantDAO.findByName(name);
    	}catch(EmptyResultDataAccessException e){
    		return null;
    	}
    }
    
    public List<Restaurant>listRestaurants3rdExercise(){
        return restaurantDAO.searchRestaurants3erExercise();
    }
}
