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

    public List<TouristAttraction> getAll() {
        String sql = "SELECT ta.name, ta.description, c.name as city FROM tourist_attraction ta JOIN city c ON ta.city_id =c.id";

        return jdbcTemplate.query(sql, new TouristAttractionRowMapper());
    }

    public TouristAttraction findByName(String name) {
        String sql = "SELECT ta.name, ta.description, c.name AS city FROM tourist_attraction ta JOIN city c ON ta.city_id = c.id WHERE ta.name = ?";

        return jdbcTemplate.queryForObject(sql, new TouristAttractionRowMapper(), name);
    }

    public void add(TouristAttraction attraction) {

        String findCitySql = "SELECT id FROM city WHERE name = ?";
        Integer cityId = jdbcTemplate.queryForObject(findCitySql, Integer.class, attraction.getCity());

        String insertSql = "INSERT INTO tourist_attraction (name, description, city_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql,
                attraction.getName(),
                attraction.getDescription(),
                cityId);
    }


    public void delete(TouristAttraction attraction) {
        String sql = "DELETE FROM tourist_attraction WHERE name = ?";
        jdbcTemplate.update(sql, attraction.getName());
    }


    public void update(TouristAttraction updated) {

        String findCitySql = "SELECT id FROM city WHERE name = ?";
        int cityId = jdbcTemplate.queryForObject(findCitySql, Integer.class, updated.getCity());


        String sql = "UPDATE tourist_attraction SET description = ?, city_id = ? WHERE name = ?";
        jdbcTemplate.update(
                sql,
                updated.getDescription(),
                cityId,
                updated.getName());
    }

    public List<String> getCities() {
        String sql = "SELECT name FROM city";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}