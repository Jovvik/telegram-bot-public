package bot.external.maps;

public class Main {
    public static void main(String[] args) {
        MapService service = new MapService();
        service.setText("кафе");

        String response = service.sendRequest();

        System.out.println(response);
    }
}
