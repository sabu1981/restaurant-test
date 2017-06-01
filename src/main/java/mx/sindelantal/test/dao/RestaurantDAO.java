package mx.sindelantal.test.dao;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.dao.EmptyResultDataAccessException;

import mx.sindelantal.test.model.Restaurant;
import mx.sindelantal.test.model.filter.RestaurantFilter;

public interface RestaurantDAO {
    
   
   /** 
      * This is the method to be used to create
      * a record in the restaurant table.
   */
   public void create(Restaurant restaurant);
   
   /** 
      * This is the method to be used to list down
      * a record from the restaurant table corresponding
      * to a passed restaurant id.
   */
   public Restaurant getRestaurant(Long id);
   
   public Restaurant getRestaurantByName(String name);
   
   /** 
      * This is the method to be used to list down
      * all the records from the restaurant table.
   */
   public List<Restaurant> listRestaurants();
   
   /** 
      * This is the method to be used to list down
      * all the records from the restaurant table with its
      * associated orders.
   */
   public List<Restaurant> listRestaurantsWithOrders();

   /** 
      * This is the method to be used to search a list
      * of records from the restaurant table.
   */
   public List<Restaurant> searchRestaurants(RestaurantFilter restaurantFilter);

   /** 
      * This is the method to be used to delete
      * a record from the restaurant table corresponding
      * to a passed restaurant id.
   */
   public void delete(Long id);
   
   /** 
      * This is the method to be used to update
      * a record into the restaurant table.
   */
   public void update(Restaurant restaurant);
   
   public Restaurant findByName(String name) throws EmptyResultDataAccessException;
   
   /** 
    * 3rd Exercise
    * 
    */
   public List<Restaurant> searchRestaurants3erExercise();
}
