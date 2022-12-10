package bot.controllers;

import bot.app.TelegramBot;
import bot.app.utils.data.questions.GeneratedQuestionResult;
import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.events.*;
import bot.backend.nodes.restriction.*;
import bot.backend.services.predict.PredictService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class TestApplication {

    private final PredictService predictService;

    @GetMapping("/testMovie")
    public void testMovie() {

            var result = predictService.generateTimeTable(List.of(
                    new GeneratedQuestionResult(new TimeRestriction(new Event.Time(16 * 60, Integer.MAX_VALUE))),
                    new GeneratedQuestionResult(new TimeRestriction(new Event.Time(Integer.MIN_VALUE, 20 * 60))),
                    new GeneratedQuestionResult(new CountRestriction(3)),
                    new GeneratedQuestionResult(new FoodPlaceTypeRestriction(List.of(FoodEvent.FoodPlaceType.RESTAURANT))),
                    new GeneratedQuestionResult(new KitchenRestriction(List.of(FoodEvent.KitchenType.JAPANESE))),
                    new GeneratedQuestionResult(new BudgetRestriction(new Event.Budget(100, 1000))),
                    new GeneratedQuestionResult(new DateRestriction(LocalDate.now().plusDays(1))),
                    new GeneratedQuestionResult(new CultureRestriction(Set.of(CultureEvent.CultureType.MOVIE))),
                    new GeneratedQuestionResult(new MovieSessionRestriction(Set.of(MovieEvent.GenreType.DRAMA)))
            ));
            System.out.println(result);
            System.out.println("---------------------------");
    }

    @GetMapping("/testFoodAndCulture")
    public void testPredict() {
        for (CultureEvent.CultureType cur : CultureEvent.CultureType.values()) {

            var result = predictService.generateTimeTable(List.of(
                new GeneratedQuestionResult(new TimeRestriction(new Event.Time(16 * 60, Integer.MAX_VALUE))),
                new GeneratedQuestionResult(new TimeRestriction(new Event.Time(Integer.MIN_VALUE, 20 * 60))),
                new GeneratedQuestionResult(new CountRestriction(3)),
                new GeneratedQuestionResult(new FoodPlaceTypeRestriction(List.of(FoodEvent.FoodPlaceType.RESTAURANT))),
                new GeneratedQuestionResult(new KitchenRestriction(List.of(FoodEvent.KitchenType.JAPANESE))),
                new GeneratedQuestionResult(new BudgetRestriction(new Event.Budget(100, 1000))),
                new GeneratedQuestionResult(new DateRestriction(LocalDate.now())),
                new GeneratedQuestionResult(new CultureRestriction(Set.of(cur)))
            ));
            System.out.println(result);
            System.out.println("---------------------------");
        }
    }

    @GetMapping("/testSport")
    public void testActive() {
        for (ActiveEvent.ActiveType cur : ActiveEvent.ActiveType.values()) {
            var result = predictService.generateTimeTable(List.of(
                    new GeneratedQuestionResult(new TimeRestriction(new Event.Time(16 * 60, Integer.MAX_VALUE))),
                    new GeneratedQuestionResult(new TimeRestriction(new Event.Time(Integer.MIN_VALUE, 20 * 60))),
                    new GeneratedQuestionResult(new CountRestriction(3)),
                    new GeneratedQuestionResult(new FoodPlaceTypeRestriction(List.of(FoodEvent.FoodPlaceType.RESTAURANT))),
                    new GeneratedQuestionResult(new KitchenRestriction(List.of(FoodEvent.KitchenType.JAPANESE))),
                    new GeneratedQuestionResult(new BudgetRestriction(new Event.Budget(100, 1000))),
                    new GeneratedQuestionResult(new DateRestriction(LocalDate.now())),
                    new GeneratedQuestionResult(new ActiveRestriction(List.of(cur)))
            ));
            System.out.println(result);
            System.out.println("---------------------------");
        }
    }

    @GetMapping("/testOut")
    public void testOut() {
        var result = predictService.generateTimeTable(List.of(
                new GeneratedQuestionResult(new TimeRestriction(new Event.Time(16 * 60, Integer.MAX_VALUE))),
                new GeneratedQuestionResult(new TimeRestriction(new Event.Time(Integer.MIN_VALUE, 20 * 60))),
                new GeneratedQuestionResult(new CountRestriction(3)),
                new GeneratedQuestionResult(new FoodPlaceTypeRestriction(List.of(FoodEvent.FoodPlaceType.RESTAURANT))),
                new GeneratedQuestionResult(new KitchenRestriction(List.of(FoodEvent.KitchenType.JAPANESE))),
                new GeneratedQuestionResult(new BudgetRestriction(new Event.Budget(100, 1000))),
                new GeneratedQuestionResult(new DateRestriction(LocalDate.now()))
        ));
    }

    @GetMapping("/testFood2")
    public void testFood2() {
        var result = predictService.generateTimeTable(List.of(
                new GeneratedQuestionResult(new TimeRestriction(new Event.Time(16 * 60, Integer.MAX_VALUE))),
                new GeneratedQuestionResult(new TimeRestriction(new Event.Time(Integer.MIN_VALUE, 20 * 60))),
                new GeneratedQuestionResult(new CountRestriction(3)),
                new GeneratedQuestionResult(new FoodPlaceTypeRestriction(List.of(FoodEvent.FoodPlaceType.RESTAURANT))),
                new GeneratedQuestionResult(new KitchenRestriction(List.of(FoodEvent.KitchenType.EUROPEAN))),
                new GeneratedQuestionResult(new KitchenRestriction(List.of(FoodEvent.KitchenType.ITALIAN))),
                new GeneratedQuestionResult(new BudgetRestriction(new Event.Budget(100, 1000))),
                new GeneratedQuestionResult(new DateRestriction(LocalDate.now()))
        ));
    }


}
