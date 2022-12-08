package bot.external.kudago;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.location.Location;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class KudaGoConverter {

    public Location convertToLocation(JSONObject location, Category category, Integer rating) {
        JSONObject coords = location.getJSONObject("coords");
        return new Location(
                /*name=*/ new String(location.getString("title").getBytes(), StandardCharsets.UTF_8),
                /*tags=*/ location.getJSONArray("categories").toList().stream().map(Object::toString).collect(Collectors.toSet()),
                /*category=*/ category,
                /*latitude=*/ coords.getDouble("lat"),
                /*longitude=*/ coords.getDouble("lon"),
                /*phoneNumber=*/ location.getString("phone"),
                /*url=*/ location.getString("site_url"),
                /*address=*/ location.getString("address"),
                /*times=*/ List.of(new Event.Time(0, 0)),
                /*rating=*/ rating
        );
    }
}
