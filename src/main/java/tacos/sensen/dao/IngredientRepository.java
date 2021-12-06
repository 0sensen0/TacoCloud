package tacos.sensen.dao;

import tacos.sensen.model.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
