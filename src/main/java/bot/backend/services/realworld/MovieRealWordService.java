package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.MovieDescription;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.MovieEvent;
import bot.backend.nodes.restriction.DateRestriction;
import bot.backend.nodes.restriction.MovieSessionRestriction;
import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.restriction.utils.TypedEnum;
import bot.converters.LocationConverter;
import bot.entities.GenreEntity;
import bot.entities.MovieEntity;
import bot.services.GenreService;
import bot.services.LocationService;
import bot.services.MovieService;
import bot.services.TagService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class MovieRealWordService extends RealWorldService<MovieEvent, MovieDescription> {

    public MovieService movieService;

    public GenreService genreService;

    public MovieRealWordService(LocationService locationService, TagService tagService, MovieService movieService, GenreService genreService) {
        super(locationService, tagService);
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @Override
    public TablePredicateContainer createPredicate(MovieDescription description) {
        return null;
    }

    @Override
    public MovieEvent generateEvent(MovieDescription description)
    {
        TablePredicate predicate = new TablePredicate(Category.CULTURE, null,0, 24 * 60,
            this.getStartDay(description.getTypedRestrictions(DateRestriction.class)));
        this.setTimeInterval(predicate, description);

            LocalDate date = description.getTypedRestrictions(DateRestriction.class).get(0).getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int time = 0;
            try {
                time = (int)((sdf.parse(date.toString()).getTime())/ 1000);
            } catch (Exception e) {

            }
            List<Restriction<?, ?>> restrictions = new ArrayList<>(description.restrictions.values());

            Set<GenreEntity> tags = new HashSet<>();

            restrictions.forEach(res -> {
                if (res instanceof MovieSessionRestriction) {
                    var actualRes = (MovieSessionRestriction) res;
                    tags.addAll(addFromType(actualRes.getValue()));
                }
            });

        List<MovieEntity> movieEntities =
                movieService.findByStartEndAndGenres(
                        time + predicate.getTimeFrom() * 60,
                        time + predicate.getTimeTo() * 60,
                        tags
                );
        if (movieEntities.size() == 0) {
            return null;
        }
            MovieEntity entity = movieEntities.get(0);
            entity.location.locationName = entity.location.locationName + "\nФильм: " + entity.title;
            return new MovieEvent(
                    LocationConverter.convertToLocation(entity.location),
                    Category.CULTURE,
                    new Event.Time(((entity.startTime + 3 * 3600)/ 60) % (60 * 24), (((entity.startTime + entity.runningTime + 3 * 3600)/ 60)  % (24 * 60)))
            );
    }

    private  Set<GenreEntity> addFromType(Collection<? extends TypedEnum> res) {
        Set<GenreEntity> genres = new HashSet<>();


        res.forEach(type -> {
            genres.add(genreService.findByName(type.getTagName()).orElse(null));
        });

        return genres;
    }
}

