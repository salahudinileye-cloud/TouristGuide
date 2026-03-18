package com.example.touristguide.repository;

import com.example.touristguide.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class TouristRepository {

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class TouristAttractionRowMapper implements RowMapper<TouristAttraction> {
        @Override
        public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TouristAttraction(
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("city"),
                    List.of()
            );
        }
    }

    private final List < String > cities = List.of("København" , "Odense" , "Aarhus" , "Aalborg");
    private final List < String > tags = List.of("Familie" , "Historie" , "Seværdighed" , "Have" , "Gåtur" , "Mad" , "Museum");

    public List < TouristAttraction > getAll( ) {
        String sql = "SELECT name, description, city FROM tourist_attraction";

        return jdbcTemplate.query(sql, new TouristAttractionRowMapper());
    }

    public TouristAttraction findByName( String name ) {
        String sql = "SELECT name, description, city FROM tourist_attraction WHERE name = ?";

        return jdbcTemplate.queryForObject(sql, new TouristAttractionRowMapper(), name);
    }

    public void add( TouristAttraction attraction ) {
        String sql = "INSERT INTO tourist_attraction (name, description, city) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, attraction.getName(), attraction.getDescription(), attraction.getCity());
    }

    public void delete(TouristAttraction attraction){
            String sql = "DELETE FROM tourist_attraction WHERE name = ?";
            jdbcTemplate.update(sql, attraction.getName());
    }

    public List < String > getCities( ) {
        return cities;
    }

    public List < String > getTags( ) {
        return tags;
    }

    public void update( TouristAttraction updated ) {
        String sql = "UPDATE tourist_attraction SET description = ?, city = ? WHERE name = ?";
        jdbcTemplate.update(
                sql,
                updated.getDescription(),
                updated.getCity(),
            updated.getName());
    }
}