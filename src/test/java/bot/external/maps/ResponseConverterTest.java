package bot.external.maps;

import java.util.ArrayList;
import java.util.List;

import bot.backend.nodes.events.Event;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import bot.external.maps.MapResponse.Feature.Properties.CompanyMetaData.Hours.Availability.Interval;

public class ResponseConverterTest {

    private final MapResponse mapResponse = new MapResponse();

    private final ResponseConverter converter = new ResponseConverter(mapResponse);

    @Test
    public void testParseTime() {
        assertEquals(converter.parseTime("00:00"), 0);
        assertEquals(converter.parseTime("02:28"), 148);
        assertEquals(converter.parseTime("2:28"), 148);
        assertEquals(converter.parseTime("10:00"), 600);
        assertEquals(converter.parseTime("10:01"), 601);
        assertEquals(converter.parseTime("10:10"), 610);
        assertEquals(converter.parseTime("21:21"), 21 * 60 + 21);
        assertEquals(converter.parseTime("23:59"), 23 * 60 + 59);
    }

    @Test
    public void testInterval() {
        final List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval("14:00", "16:00"));
        intervals.add(new Interval("16:00", "20:00"));
        intervals.add(new Interval("20:00", "02:00"));

        assertEquals(converter.getInterval(intervals), new Event.Time(14 * 60, 26 * 60));
    }
}
