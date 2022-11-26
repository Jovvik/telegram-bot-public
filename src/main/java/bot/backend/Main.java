package bot.backend;

import bot.backend.nodes.description.FoodDescription;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            new FoodDescription(Map.of());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
