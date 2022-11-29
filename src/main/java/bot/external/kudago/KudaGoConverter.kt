package bot.external.kudago

import bot.backend.nodes.location.Location
import org.json.JSONObject

class KudaGoConverter {

    val genres : List<String> = listOf(
        "drama", "comedy", "musical", "adventure", "thriller", "horror", "crime", "fantasy", "documentary",
    );

    fun convertToLocation(location: JSONObject?): Location? {
        return null
//        return Location(
//            name=location.get("title"),
//            tags=Set.of(),
//
//        );
    }
}
