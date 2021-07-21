package com.example.demo.util;

import com.example.demo.model.UserMeal;
import com.example.demo.model.UserMealWithExceed;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfest", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Lunch", 1200),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 18, 0), "Dinner", 760),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 8, 0), "Breakfest", 800),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 14, 0), "Lunch", 400),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Dinner", 1500)
        );
        List<UserMealWithExceed> userMealWithExceed = getFilteredWithExcess(mealList, LocalTime.of(10, 0),
                                                                            LocalTime.of(22, 0), 1000);
        userMealWithExceed.forEach(System.out::println);

    }

    public static List<UserMealWithExceed> getFilteredWithExcess(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly excess field

        Map<LocalTime, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalTime(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal ->
                        new UserMealWithExceed(
                                meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                caloriesSumByDate.get(meal.getDateTime().toLocalTime()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}





/*
    public static List<UserMealWithExceed> getUserMealWithExceed(List<UserMeal> mealList, LocalTime beginTime,
                                                                 LocalTime endTime, int caloriesMax) {
        List<UserMealWithExceed> list = new ArrayList<>();
        for(UserMeal meal : mealList) {
            if(meal.getDateTime().getHour() > beginTime.getHour() &&
                    meal.getDateTime().getHour() < endTime.getHour()
                    && meal.getCalories() > caloriesMax) {
                list.add(
                        new UserMealWithExceed(meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories()-caloriesMax,
                                true)
                );
            }
        }
        return list;
    }

    public static List<UserMealWithExceed> getUserMealWithExceedNew(List<UserMeal> mealList, LocalTime beginTime,
                                                                    LocalTime endTime, int caloriesMax) {

        List<UserMealWithExceed> userMealWithExceeds = mealList.stream()
                .filter(meal ->
                        meal.getDateTime().getHour() > beginTime.getHour() &&
                                meal.getDateTime().getHour() < endTime.getHour() &&
                                meal.getCalories() > caloriesMax)
                .map(meal ->
                        new UserMealWithExceed(
                                meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories() - caloriesMax,
                                true))
                .collect(Collectors.toList());

        return userMealWithExceeds;
    }*/
