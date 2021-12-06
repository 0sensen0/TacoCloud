package tacos.sensen.controller;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.sensen.dao.IngredientRepository;
import tacos.sensen.dao.TacoRepository;
import tacos.sensen.model.Ingredient;
import tacos.sensen.model.Order;
import tacos.sensen.model.Taco;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private TacoRepository tacoRepository;

    @ModelAttribute(name = "taco")
    public void taco(Model model) {
        Taco taco = new Taco();
        taco.setId(9999l);
        model.addAttribute("taco", taco);
    }

    @ModelAttribute(name = "order")
    public void order(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
    }

    @GetMapping
    public String showDesginForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        Iterable<Ingredient> all = ingredientRepository.findAll();
        all.forEach(ingredients::add);
        Ingredient.Type[] values = Ingredient.Type.values();
        Arrays.stream(values).forEach(type -> {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        });
        return "design";
    }

    @PostMapping
    public String processDesgin(@ModelAttribute("taco") @Valid Taco design, Errors errors, @ModelAttribute Order order, Model model) {
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Proccessing design:" + design);
        Taco taco = tacoRepository.saveTaco(design);
        order.setDesign(taco);
        model.addAttribute("order", order);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        List<Ingredient> collect = ingredients.stream().filter(item -> item.getType().equals(type)).collect(Collectors.toList());
        return collect;
    }
}
