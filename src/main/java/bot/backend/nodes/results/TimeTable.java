package bot.backend.nodes.results;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.location.Location;
import bot.external.maps.MapRequest;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TimeTable {
    public List<Event> events;

    public <E extends Event> List<E> getTypedEvents(Class<E> eventClass) {
        return events.stream()
                .filter(eventClass::isInstance)
                .map(eventClass::cast)
                .collect(Collectors.toList());
    }

    private List<Point> getPoints() {
        List<Point> points = new ArrayList<>();
        for (Event event : events) {
            Location location = event.location;
            Point point = new Point(location.getLatitude(), location.getLongitude());
            points.add(point);
        }

        return points;
    }

    private String getLL(boolean withRadius) {
        MapRequest mapRequest = new MapRequest();
        StringBuilder res = new StringBuilder();
        res.append("&ll=").append(mapRequest.getUserLong())
                .append(",").append(mapRequest.getUserLati());

        if (withRadius) {
            res.append("&spn=").append(mapRequest.getRadiusLong())
                    .append(",").append(mapRequest.getRadiusLati());
        }

        return res.toString();
    }

    private String getColor(int id) {
        Category category = events.get(id).location.getCategory();

        String res = "wt";
        switch (category) {
            case FOOD:
                res = "yw";
                break;
            case PARTY:
                res = "vv";
                break;
            case ACTIVE:
                res = "lb";
                break;
            case HEALTH:
                res = "gn";
                break;
            case CULTURE:
                res = "db";
                break;
            case EXTREME:
                res = "rd";
                break;
            case DEFAULT:
                res = "wt";
                break;
        }

        return res;
    }

    private String createMap() {
        StringBuilder res = new StringBuilder();

        res.append("https://static-maps.yandex.ru/1.x/?size=450,450&l=map")
                .append(getLL(true)).append("&pt=");

        List<Point> points = getPoints();
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            res.append(p.longitude).append(",")
                    .append(p.latitude).append(",")
                    .append("pm2").append(getColor(i)).append("l")
                    .append(i + 1).append("~");
        }
        res.delete(res.length() - 1, res.length());

        return res.toString();
    }

    private String createRoute() {
        StringBuilder res = new StringBuilder();

        res.append("\nhttps://yandex.ru/maps/2/saint-petersburg/?mode=routesrtt=pd&ruri=~")
                .append("&z=").append(13.64) // TODO вести в константу
                .append(getLL(false))
                .append("&rtext=");

        List<Point> points = getPoints();
        points.forEach(p -> res.append(p.latitude).append(",").append(p.longitude).append("~"));
        res.delete(res.length() - 1, res.length());

        return res.append("\n").toString();
    }

    @Override
    public String toString() {
        StringBuilder eventsOut = new StringBuilder();
        events.forEach(event -> eventsOut.append(event.toString()));

        return "**Расписание вашего мероприятия:**" +
                eventsOut +
                createRoute() +
                createMap();
    }

    public void getPhotoOfMap() {
        String mapReq = createMap();

    }

    private static class Point {
        double latitude;
        double longitude;

        public Point(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
