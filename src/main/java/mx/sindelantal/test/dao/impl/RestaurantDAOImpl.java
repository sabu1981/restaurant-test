package mx.sindelantal.test.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import mx.sindelantal.test.model.Restaurant;
import mx.sindelantal.test.dao.RestaurantDAO;
import mx.sindelantal.test.dao.BaseDAO;
import mx.sindelantal.test.mapper.RestaurantMapper;
import mx.sindelantal.test.model.filter.RestaurantFilter;

@Repository
public class RestaurantDAOImpl extends BaseDAO implements RestaurantDAO{

    public void create(Restaurant restaurant) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(restaurant.getOpenSince() == null){
            restaurant.setOpenSince(new Date());
        }
        String SQL = "insert into restaurant (name, open_since) values (:name, :open_since)";
        params.put("name", restaurant.getName());
        params.put("open_since", restaurant.getOpenSince());
        new NamedParameterJdbcTemplate(jdbcTemplate).update(SQL, params);
        return;
    }

    public Restaurant getRestaurant(Long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        String SQL = "select * from restaurant where id = :id";
        params.put("id", id);
        Restaurant restaurant = new NamedParameterJdbcTemplate(jdbcTemplate).queryForObject(SQL, 
            params, new RestaurantMapper());
        return restaurant;
    }

    public Restaurant getRestaurantByName(String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        String SQL = "select * from restaurant where name = :name";
        params.put("name", name);
        Restaurant restaurant = new NamedParameterJdbcTemplate(jdbcTemplate).queryForObject(SQL, 
            params, new RestaurantMapper());
        return restaurant;
    }

    public List<Restaurant> listRestaurants() {
        String SQL = "select r.id, r.name, r.open_since, sum(ro.rating)/count(ro.id) as rating"
                + " from  restaurant r"
                + " left join delivery_order o on o.restaurant_id = r.id"
                + " left join review_order ro on ro.order_id = o.id where 1=1"
                + " group by r.id"
                + " order by rating desc";
        List <Restaurant> restaurants = new NamedParameterJdbcTemplate(jdbcTemplate).query(SQL, new RestaurantMapper());
        return restaurants;
    }

    public List<Restaurant> listRestaurantsWithOrders(){
        String SQL = "select * from restaurant";
        List <Restaurant> restaurants = new NamedParameterJdbcTemplate(jdbcTemplate).query(SQL, new RestaurantMapper());
        return restaurants;
    }
    
    public List<Restaurant> searchRestaurants(RestaurantFilter restaurantFilter){
        Map<String, Object> params = new HashMap<String, Object>();
        String SQL = "select r.id, r.name, r.open_since, sum(ro.rating)/count(ro.id) as rating"
                + " from  restaurant r"
                + " left join delivery_order o on o.restaurant_id = r.id"
                + " left join review_order ro on ro.order_id = o.id"
                + " where name like :name "
                + " group by r.id"
                + " order by rating desc";
            params.put("name", "%"+restaurantFilter.getNameLike()+"%");
        List <Restaurant> restaurants = new NamedParameterJdbcTemplate(jdbcTemplate).query(SQL,
            params,
            new RestaurantMapper());
        return restaurants;
    }

    public void delete(Long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        String SQL = "delete from restaurant where id = :id";
        params.put("id", id);
        new NamedParameterJdbcTemplate(jdbcTemplate).update(SQL, params);
        return;
    }

    public void update(Restaurant restaurant){
        Map<String, Object> params = new HashMap<String, Object>();
        String SQL = "update restaurant set name = :name, where id = :id";
        params.put("name", restaurant.getName());
        params.put("id", restaurant.getId());
        new NamedParameterJdbcTemplate(jdbcTemplate).update(SQL, params);
        return;
    }

    public Restaurant findByName(String name) throws EmptyResultDataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        String SQL = "select * from restaurant where name = :name";
        params.put("name", name);
        Restaurant restaurant = new NamedParameterJdbcTemplate(jdbcTemplate).queryForObject(SQL, 
            params, new RestaurantMapper());
        return restaurant;
    }

	@Override
	public List<Restaurant> searchRestaurants3erExercise() {
		String SQL = " select r.id, r.name, r.open_since, sum(ro.rating)/count(ro.id) as rating,"
                + " IFNULL(("
                + " select count(*) as cancelados"
                + " from delivery_order doo"
                + " left join restaurant rest on rest.id = doo.restaurant_id"
                + " where doo.order_status = 'cancelled'"
                + " and r.id = rest.id"
                + " group by doo.restaurant_id"
                + " ),0) as totalCancelled"
                + " from  restaurant r"
                + " left join delivery_order o on o.restaurant_id = r.id"
                + " left join review_order ro on ro.order_id = o.id "
                + " group by r.id"
                + " order by totalCancelled, rating desc";
                
        List <Restaurant> restaurants = new NamedParameterJdbcTemplate(jdbcTemplate).query(SQL, new RestaurantMapper());
        return restaurants;
	}
}
