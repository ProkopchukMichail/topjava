package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static org.springframework.format.annotation.DateTimeFormat.ISO.*;

@RestController
@RequestMapping(MealRestController.MEAL_REST_URI)
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    static final String MEAL_REST_URI="/rest/user/meals";

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Meal create(@RequestBody Meal meal) {
        int userId = AuthorizedUser.id();
        checkNew(meal);
        LOG.info("create {} for User {}", meal, userId);
        return service.save(meal, userId);
    }

    @PostMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        int userId = AuthorizedUser.id();
        checkIdConsistent(meal, id);
        LOG.info("update {} for User {}", meal, userId);
        service.update(meal, userId);
    }

    @GetMapping(value = "/{between}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestParam("startDate") @DateTimeFormat(iso = DATE) LocalDate startDate,
                                           @RequestParam("startTime") @DateTimeFormat(iso = TIME) LocalTime startTime,
                                           @RequestParam("endDate") @DateTimeFormat(iso = DATE)LocalDate endDate,
                                           @RequestParam("endTime") @DateTimeFormat(iso = TIME)LocalTime endTime) {
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates({} - {}) time({} - {}) for User {}", startDate, endDate, startTime, endTime, userId);

        return MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
    }
}