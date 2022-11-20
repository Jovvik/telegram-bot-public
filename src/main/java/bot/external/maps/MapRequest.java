package bot.external.maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MapRequest {
    private String text;
    private String lang = "ru_RU";
    private double userLong = 30.313729;
    private double userLati = 59.954380;
    private double radiusLong = 0.03;
    private double radiusLati = 0.03;
    private int resultsSize = 10;

    public MapRequest(String text) {
        this.text = text;
    }
}
