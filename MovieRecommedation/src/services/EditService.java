package services;

import classes.cast.Actor;
import classes.cast.Director;
import classes.title.Movie;
import classes.title.Title;
import classes.title.Review;
import classes.title.Series;
import classes.title.TitleCollection;
import repositories.*;

import java.io.IOException;
import java.util.*;

public class EditService {
    public EditService() {
    }
    static MainService mainService = MainService.getInstance();

    AuditService auditService = AuditService.getInstance();
    

    public void createItem(Scanner sc){
        mainService.initializeDataFromDB();
        System.out.println("What item do you want to create? (movie/collection/series): ");
        String str = sc.next();
        sc.nextLine();
        if(Objects.equals(str, "movie")){
            Movie movie = createMovie(sc);
            mainService.getTitles().add(movie);
            System.out.println("Movie created successfully!\n");
            System.out.println(movie.toString());
        }
        else if(Objects.equals(str, "collection")){
            TitleCollection collection = createCollection(sc);
            mainService.getTitles().add(collection);
            System.out.println("Title collection created successfully!\n");
            System.out.println(collection.toString());
        }
        else if(Objects.equals(str, "series")){
            Series series = createSeries(sc);
            mainService.getTitles().add(series);
            System.out.println("Series created successfully!\n");
            System.out.println(series.toString());
        }

        try{
            auditService.logAction("Create Title");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Create Title!");
        }

    }

    public Movie createMovie(Scanner sc){

        int id = mainService.getTitles().get(mainService.getTitles().size()-1).getId() + 1;

        System.out.println("Enter movie title: ");
        String title = sc.nextLine();
        List<String> Genre = new ArrayList<>();
        int ok=1;
        while(ok==1){
            System.out.println("Enter movie genre: ");
            String genre = sc.nextLine();
            Genre.add(genre);
            System.out.println("Enter another movie genre?(yes/no)");
            if (Objects.equals(sc.next(), "no")) {
                ok=0;
            }
            sc.nextLine();
        }
        System.out.println("Enter movie language: ");
        String language = sc.nextLine();

        System.out.println("Enter movie release year: ");
        int releaseYear = Integer.parseInt(sc.nextLine());

        List<Actor>actorsInMovie = new ArrayList<>();
        System.out.println("Enter actors: (Enter done when you have inserted all actors)");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.equals("done")) {
                break;
            }
            String[] words = line.split(" ",2);
            String firstname = words[0];
            String lastname = words[1];
            Actor a = null;
            for(Actor actor:mainService.getActors()){
                if(Objects.equals(actor.getFirstName(), firstname) && Objects.equals(actor.getLastName(), lastname)){
                    a = actor;
                    break;
                }
            }
            if(a == null){
                System.out.println("Actor doesn't exist!");
            }
            else {
                actorsInMovie.add(a);
            }
        }

        Director movieDirector = null;
        while(true){
            System.out.println("Enter director's full name: ");
            String line = sc.nextLine();

            String[] words = line.split(" ",2);
            String firstName = words[0];
            String lastName = words[1];

            for(Director director: mainService.getDirectors()){
                if(Objects.equals(director.getFirstName(), firstName) && Objects.equals(director.getLastName(), lastName)){
                    movieDirector = director;
                    break;
                }
            }
            if(movieDirector == null){
                System.out.println("Director doesn't exist!");
            }
            else{
                break;
            }
        }

        List<Review> reviewsForMovie = new ArrayList<>();
        Movie movie = new Movie(id,title,Genre,language,actorsInMovie,movieDirector, releaseYear, reviewsForMovie);
        mainService.getMovies().add(movie);

        MovieRepository.insertMovie(id,releaseYear, null, movieDirector.getCastId());
        TitleRepository.insertTitle(id, title,language);
        for(String g: Genre){
            GenreRepository.insertGenre(g, id);
        }
        for(Actor a: actorsInMovie){
            PlayRepository.insertPlay(a.getCastId(), id);
        }


        return movie;
    }

    public TitleCollection createCollection(Scanner sc){

        int id = mainService.getTitles().get(mainService.getTitles().size()-1).getId() + 1;

        System.out.println("Enter collection title: ");
        String title = sc.nextLine();
        List<String>Genre = new ArrayList<>();
        int ok=1;
        while(ok==1){
            System.out.println("Enter collection genre: ");
            String genre = sc.nextLine();
            Genre.add(genre);
            System.out.println("Enter another collection genre?(yes/no)");
            if (Objects.equals(sc.next(), "no")) {
                ok=0;
            }
            sc.nextLine();
        }
        System.out.println("Enter collection language: ");
        String language = sc.nextLine();
        List<Movie> moviesInCollection = new ArrayList<>();
        while(true){
            System.out.println("Add movie in collection(yes/no): ");
            if(Objects.equals(sc.next(), "no")){
                break;
            }
            sc.nextLine();
            System.out.println("Enter movie title: ");
            String movieTitle = sc.nextLine();
            Movie m = null;
            for(Movie movie: mainService.getMovies()){
                if(Objects.equals(movieTitle, movie.getTitle())){
                    m = movie;
                    break;
                }
            }
            if(m == null){
                System.out.println("Movie doesn't exist!");
            }
            else{
                moviesInCollection.add(m);
                MovieRepository.updateMovie(m.getTitle(),m.getLanguage(), m.getDirector().getCastId(), m.getReleaseYear(), id, m.getId());
            }
        }
        int numberMovies = moviesInCollection.size();
        TitleCollection collection = new TitleCollection(id, title, Genre, language, numberMovies, moviesInCollection);
        mainService.getCollections().add(collection);

        TitleCollectionRepository.insertTitleCollection(id,numberMovies);
        TitleRepository.insertTitle(id, title,language);
        for(String g: Genre){
            GenreRepository.insertGenre(g, id);
        }


        return collection;
    }

    public Series createSeries(Scanner sc){
        int id = mainService.getTitles().get(mainService.getTitles().size()-1).getId() + 1;

        System.out.println("Enter series title: ");
        String title = sc.nextLine();
        List<String>Genre = new ArrayList<>();
        int ok=1;
        while(ok==1){
            System.out.println("Enter series genre: ");
            String genre = sc.nextLine();
            Genre.add(genre);
            System.out.println("Enter another series genre?(yes/no)");
            if (Objects.equals(sc.next(), "no")) {
                ok=0;
            }
            sc.nextLine();
        }
        System.out.println("Enter series language: ");
        String language = sc.nextLine();
        System.out.println("Enter series release year: ");
        int releaseYear = Integer.parseInt(sc.next());
        sc.nextLine();
        System.out.println("Enter series finish year(or 0 if series is not finished): ");
        int finishYear = Integer.parseInt(sc.next());
        sc.nextLine();
        System.out.println("Enter series number of seasons : ");
        int nrSeasons = Integer.parseInt(sc.next());
        sc.nextLine();
        List<Actor>actorsInSeries = new ArrayList<>();
        System.out.println("Enter actors: (Enter done when you inserted all actors)");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.equals("done")) {
                break;
            }
            String[] words = line.split(" ",2);
            String firstname = words[0];
            String lastname = words[1];
            Actor a = null;
            for(Actor actor:mainService.getActors()){
                if(Objects.equals(actor.getFirstName(), firstname) && Objects.equals(actor.getLastName(), lastname)){
                    a = actor;
                    break;
                }
            }
            if(a == null){
                System.out.println("Actor doesn't exist!");
            }
            else {
                actorsInSeries.add(a);
            }
        }
        Director seriesDirector = null;
        while(true){
            System.out.println("Enter director's full name: ");
            String line = sc.nextLine();

            String[] words = line.split(" ",2);
            String firstName = words[0];
            String lastName = words[1];
            for(Director director: mainService.getDirectors()){
                if(Objects.equals(director.getFirstName(), firstName) && Objects.equals(director.getLastName(), lastName)){
                    seriesDirector = director;
                    break;
                }
            }
            if(seriesDirector == null){
                System.out.println("Director doesn't exist!");
            }
            else{
                break;
            }
        }
        List<Review> reviewsForSeries = new ArrayList<>();
        Series series = new Series(id,title,Genre,language,actorsInSeries,seriesDirector, releaseYear, finishYear, nrSeasons, reviewsForSeries);
        mainService.getSeriesL().add(series);

        SeriesRepository.insertSeries(id, releaseYear,finishYear, nrSeasons, null, seriesDirector.getCastId());
        TitleRepository.insertTitle(id, title,language);
        for(String g: Genre){
            GenreRepository.insertGenre(g, id);
        }
        for(Actor a: actorsInSeries){
            PlayRepository.insertPlay(a.getCastId(), id);
        }

        return series;
    }

    public void removeItem(Scanner sc){
        mainService.initializeDataFromDB();
        sc.nextLine();
        System.out.println("What title do you want to remove?");

        String title = sc.nextLine();

        Title ttl = null;
        for(Title item: mainService.getTitles()){
            if(Objects.equals(title, item.getTitle())){
                ttl = item;

                for(Actor a: mainService.getActors()){
                    PlayRepository.deletePlayByCast_idAndTitle(a.getCastId(), item.getId());

                }
                for(String g: item.getGenre()){
                    GenreRepository.deleteGenreByGenreAndTitle(g, item.getId());

                }
                FavoritesRepository.deleteFavoritesByTitle(item.getId());
                HistoryRepository.deleteHistoryByTitle(item.getId());


                Movie movie = null;
                for(Movie m: mainService.getMovies()){
                    if(item.getId() == m.getId()){
                        movie = m;

                        break;
                    }
                }
                if(movie != null){
                    mainService.getMovies().remove(movie);
                    MovieRepository.deleteMovieById(movie.getId());
                }
                else{

                    TitleCollection collection = null;
                    for(TitleCollection c: mainService.getCollections()){
                        if(item.getId() == c.getId()){
                            collection = c;
                            break;
                        }
                    }
                    if(collection != null){
                        mainService.getCollections().remove(collection);
                        TitleCollectionRepository.deleteTitleCollectionById(collection.getId());
                    }
                    else{
                        Series series = null;
                        for(Series s: mainService.getSeriesL()){
                            if(item.getId() == s.getId()){
                                series = s;
                                break;
                            }
                        }
                        if(series != null){
                            mainService.getSeriesL().remove(series);
                            SeriesRepository.deleteSeriesById(series.getId());
                        }
                        else{
                            System.out.println("Title doesn't exist!");
                        }
                    }
                }
                TitleRepository.deleteTitleById(item.getId());

            }
        }
        mainService.getTitles().remove(ttl);

        try{
            auditService.logAction("Remove Title");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Remove Title!");
        }
    }

    public void addMovieInCollection(Scanner sc){
        System.out.println("In which movie collection do you want to add a movie?");
        sc.nextLine();
        String collectionTitle = sc.nextLine();
        System.out.println("What movie do you want to add to " + collectionTitle + "?");
        String movieTitle = sc.nextLine();
        TitleCollection collection = null;
        Movie movie = null;
        for(TitleCollection c: mainService.getCollections()){
            if(Objects.equals(collectionTitle, c.getTitle())){
                collection = c;
                break;
            }
        }
        for(Movie m: mainService.getMovies()){
            if(Objects.equals(movieTitle, m.getTitle())){
                movie = m;
                break;
            }
        }
        if(collection==null){
            System.out.println("Collection doesn't exist!");
        }
        else if(movie==null){
            System.out.println("Movie doesn't exist!");
        }
        else{
            collection.getMoviesInCollection().add(movie);
            MovieRepository.updateMovie(movie.getTitle(), movie.getLanguage(), movie.getDirector().getCastId(), movie.getReleaseYear(), collection.getId(), movie.getId());
        }

        try{
            auditService.logAction("Add Title in Collection");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Add Title in Collection!");
        }
    }



    public void createCastMember(Scanner sc){
        System.out.println("What cast member do you want to create? (actor/director): ");
        String str = sc.next();
        if(Objects.equals(str, "actor")){
            createActor(sc);
        }
        else{
            createDirector(sc);
        }

        try{
            auditService.logAction("Create Cast Member");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Create cast member!");
        }

    }

    public void createActor(Scanner sc){
        int id = mainService.getActors().get(mainService.getActors().size()-1).getCastId() + 1;

        sc.nextLine();
        System.out.println("Enter actor's firstname: ");
        String firstname = sc.nextLine();
        System.out.println("Enter actor's lastname: ");
        String lastname = sc.nextLine();
        Actor actor = new Actor(id, firstname, lastname);
        if(!mainService.getActors().contains(actor)){
            mainService.getActors().add(actor);
            ActorRepository.insertActor(id, firstname, lastname);
        }
    }

    public void createDirector(Scanner sc){
        int id = mainService.getDirectors().get(mainService.getDirectors().size()-1).getCastId() + 1;

        sc.nextLine();
        System.out.println("Enter director's firstname: ");
        String firstname = sc.nextLine();
        System.out.println("Enter actor's lastname: ");
        String lastname = sc.nextLine();
        Director director = new Director(id, firstname, lastname);
        if(!mainService.getDirectors().contains(director)){
            mainService.getDirectors().add(director);
            DirectorRepository.insertDirector(id, firstname, lastname);

        }
    }

}
