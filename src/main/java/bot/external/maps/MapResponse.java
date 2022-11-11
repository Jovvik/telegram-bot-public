package bot.external.maps;

import java.util.List;

public class MapResponse {
    String type;
    Properties properties;
    List<Feature> features;

    public static class Properties {

        ResponseMetaData responseMetaData;

        public static class ResponseMetaData {

            SearchResponse searchResponse;
            SearchRequest searchRequest;

            public static class SearchResponse {
                Integer found;

            }
            public static class SearchRequest {}
        }
    }

    public static class Feature {
        String type;
        Geometry geometry;
        Properties properties;

        public static class Geometry {}
        public static class Properties {}
    }
}
