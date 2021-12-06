package tacos.sensen.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.sensen.model.Ingredient;
import tacos.sensen.model.Taco;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

@Repository
public class TacoRepositoryImpl implements TacoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Taco saveTaco(Taco taco) {
        // 保存taco信息获取主键
        long id = saveTacoInfo(taco);
        taco.setId(id);
        // 保存taco相关配料
        taco.getIngredients().stream().forEach(item ->{
            saveIngredientToTaco(item,id);
        });
        return taco;
    }

    private long saveTacoInfo(Taco taco){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:hh:mm");
        String format = now.format(dateTimeFormatter);
        taco.setCreatedAt(format);
        final String sql= "insert into taco(name,createdAt) values (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, taco.getName());
                ps.setString(2, taco.getCreatedAt());
                return ps;
            }
        }, keyHolder);
        // 返回插入的主键
        return keyHolder.getKey().longValue();
    }
    // 保存taco对于的配料方法
    private void  saveIngredientToTaco(String ingredient, long tacoId){
        String sql = "insert into taco_ingredients(taco,ingredient) value(?,?)";
        jdbcTemplate.update(sql, tacoId, ingredient);
    }
}
