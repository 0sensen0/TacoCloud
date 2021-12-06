package tacos.sensen.dao;

import tacos.sensen.model.Taco;

public interface TacoRepository {
    Taco saveTaco(Taco taco);
}
