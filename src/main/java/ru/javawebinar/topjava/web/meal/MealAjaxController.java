package ru.javawebinar.topjava.web.meal;


import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by comp on 21.06.2017.
 */
@RestController
@RequestMapping("/ajax/meals")
public class MealAjaxController extends AbstractMealController {

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping
    public void createOrUpdate(@RequestParam Integer id,
                               @RequestParam LocalDateTime datetime,
                               @RequestParam String description,
                               @RequestParam Integer calories) {
        Meal meal = new Meal(datetime, description, calories);
        if (meal.isNew()) super.create(meal);
        else super.update(meal, id);
    }
}
