package bot.external.maps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class MapResponse {
    public String type;
    public Properties properties;
    public List<Feature> features;

    @ToString
    public static class Properties {

        @JsonProperty("ResponseMetaData")
        public ResponseMetaData responseMetaData;

        @ToString
        public static class ResponseMetaData {

            @JsonProperty("SearchResponse")
            public SearchResponse searchResponse;

            @JsonProperty("SearchRequest")
            public SearchRequest searchRequest;

            @ToString
            public static class SearchResponse {
                public Integer found;
                public String display;
                public List<List<Double>> boundedBy;
            }
            @ToString
            public static class SearchRequest {
                public String request;
                public Integer skip;
                public Integer results;
                public List<List<Double>> boundedBy;
            }
        }
    }

    @ToString
    public static class Feature {
        public String type;
        public Geometry geometry;
        public Properties properties;

        @ToString
        public static class Geometry {
            public String type;
            public List<Double> coordinates;

            public List<Double> getCoordinates() {
                List<Double> swappedCoordinates = new ArrayList<>();
                swappedCoordinates.add(coordinates.get(1));
                swappedCoordinates.add(coordinates.get(0));
                return swappedCoordinates;
            }
        }

        @ToString
        public static class Properties {
            public String name;
            public String description;
            public List<List<Double>> boundedBy;

            @JsonProperty("CompanyMetaData")
            public CompanyMetaData companyMetaData;

            @ToString
            public static class CompanyMetaData {
                public String id;
                public String name;
                public String address;
                public String url;

                @JsonProperty("Phones")
                public List<Phone> phones;

                @JsonProperty("Categories")
                public List<Category> categories;

                @JsonProperty("Hours")
                public Hours hours;

                @ToString
                public static class Phone {
                    public String type;
                    public String formatted;
                }

                @ToString
                public static class Category {

                    @JsonProperty("class")
                    public String className;

                    public String name;
                }

                @ToString
                public static class Hours {
                    public String text;

                    @JsonProperty("Availabilities")
                    public List<Availability> availabilities;

                    @ToString
                    public static class Availability {

                        @JsonProperty("Intervals")
                        public List<Interval> intervals;

                        @JsonProperty("TwentyFourHours")
                        public Boolean twentyFourHour = false;

                        @JsonProperty("Everyday")
                        public Boolean everyDay = false;

                        @JsonProperty("Monday")
                        public Boolean monday = false;

                        @JsonProperty("Tuesday")
                        public Boolean tuesday = false;

                        @JsonProperty("Wednesday")
                        public Boolean wednesday = false;

                        @JsonProperty("Thursday")
                        public Boolean thursday = false;

                        @JsonProperty("Friday")
                        public Boolean friday = false;

                        @JsonProperty("Saturday")
                        public Boolean saturday = false;

                        @JsonProperty("Sunday")
                        public Boolean sunday = false;

                        @ToString
                        public static class Interval {
                            public String to;
                            public String from;

                            public Interval(String from, String to) {
                                this.to = to;
                                this.from = from;
                            }
                        }
                    }
                }
            }
        }
    }
}
