package tacos.sensen.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.sensen.model.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class IngredientRepositoryImpl implements IngredientRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Ingredient> findAll() {
        String sql = "select * from ingredient";
        List<Ingredient> ingredientList = jdbcTemplate.query(sql, this::mapRowToIngredient);
        return ingredientList;
    }

    @Override
    public Ingredient findOne(String id) {
        String sql = "select * from ingredient where id = ?";
        Ingredient ingredient = jdbcTemplate.queryForObject(sql, this::mapRowToIngredient, id);
        return ingredient;
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        String sql = "insert into ingredient(name, type) value(?,?)";
        jdbcTemplate.update(sql, ingredient.getName(), ingredient.getName());
        return ingredient;
    }
    private  Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
