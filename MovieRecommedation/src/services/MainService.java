package services;

import classes.cast.Actor;
import classes.cast.Director;
import classes.title.*;
import classes.user.Preference;
import classes.user.User;
import repositories.*;
import classes.title.comparator.*;

import javax.swing.event.HyperlinkEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class MainService {
    private MainService(){}
    private static class SingletonHolder{
        private static final MainService INSTANCE = new MainService();
    }

    public static MainService getInstance(){
        return SingletonHolder.INSTANCE;
    }

    AuditService auditService = AuditService.getInstance();

    private List<Actor> actors = new ArrayList<>();
    private List<Director> directors = new ArrayList<>();
    private List<TitleCollection> collections = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private List<Series> seriesL = new ArrayList<>();
    private Set<User> users = new HashSet<User>();
    private  List<Title> titles = new ArrayList<>();

    public List<Actor> getActors() {
        return actors;
    }
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    public List<Director> getDirectors() {
        return directors;
    }
    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }
    public List<TitleCollection> getCollections() {
        return collections;
    }
    public void setCollections(List<TitleCollection> collections) {
        this.collections = collections;
    }
    public List<Movie> getMovies() {
        return movies;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
    public List<Series> getSeriesL() {
        return seriesL;
    }
    public void setSeriesL(List<Series> seriesL) {
        this.seriesL = seriesL;
    }
    public Set<User> getUsers() {return users;}
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    public List<Title> getTitles() {return titles;}
    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public List<Title> getRecommendations() {
        return Recommendations;
    }

    public void setRecommendations(List<Title> recommendations) {
        Recommendations = recommendations;
    }



    public void createDBTables(){
        UserRepository.createTable();
        ActorRepository.createTable();
        DirectorRepository.createTable();
        TitleRepository.createTable();
        MovieRepository.createTable();
        SeriesRepository.createTable();
        TitleCollectionRepository.createTable();
        try{
            auditService.logAction("Create Tables");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv create table!");
        }
    }
    public void initializeDataFromDB() {//iau datele din baza de date

        users.clear();
        actors.clear();
        directors.clear();
        titles.clear();
        movies.clear();
        seriesL.clear();
        collections.clear();
        UserRepository.transformDBToLists();
        ActorRepository.transformDBToLists();
        DirectorRepository.transformDBToLists();
        TitleRepository.transformDBToLists();
        MovieRepository.transformDBToLists();
        SeriesRepository.transformDBToLists();
        TitleCollectionRepository.transformDBToLists();

        try{
            auditService.logAction("Data from Tables");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv data from tables!");
        }

    }

    private User loggedInUser;
    private List<Title>Recommendations;
    private Title selected;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public List<Title> getrecommendations() {
        return Recommendations;
    }

    public void setrecommendations(List<Title> recommendations) {
        Recommendations = recommendations;
    }

    public Title getSelected() {
        return selected;
    }
    public void setSelected(Title selected) {
        this.selected = selected;
    }



    //PREFERENCE
    public void insertPreference(User user, Scanner sc){
        System.out.println("Insert your preference. Insert <<no>> where you don't want to complete the field.");
        sc.nextLine();
        String type = getInput(sc,"Type (movie/collection/series/no): ");
        String genre = getInput(sc,"Genre: ");
        int releaseYear = getReleaseYearInput(sc);
        String language = getInput(sc, "Language: ");
        Actor PrefActor = getActorInput(sc);
        Director PrefDirector = getDirectorInput(sc);

        Preference preference = new Preference(type, genre, PrefActor, PrefDirector, releaseYear, language);
        user.setPreference(preference);

        try{
            auditService.logAction("Insert Preference");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Insert Preference!");
        }
    }

    private String getInput(Scanner sc, String prompt){
        System.out.println(prompt);
        String str = sc.nextLine();
        if(Objects.equals(str, "no")){
            return "";
        }
        else{
            return str;
        }
    }

    private int getReleaseYearInput(Scanner sc){
        System.out.println("Enter release year: ");
        String str = sc.nextLine();
        if(Objects.equals(str, "no")){
            return 0;
        }
        else{
            return Integer.parseInt(str);
        }
    }

    private Actor getActorInput(Scanner sc){
        Actor PrefActor=null;
        String firstname, lastname;
        System.out.println("Actor:(yes/no)");
        if(Objects.equals(sc.nextLine(), "yes")){
            System.out.println("Enter actor's firstname: ");
            firstname = sc.nextLine();
            System.out.println("Enter actor's lastname: ");
            lastname = sc.nextLine();
            int ok=0;
            for(Actor actor: actors){
                if(Objects.equals(firstname, actor.getFirstName()) && Objects.equals(lastname, actor.getLastName())){
                    ok=1;
                    PrefActor = actor;
                    break;
                }
            }
            if(ok==0){
                System.out.println("Actor not in database!");
            }
        }
        return PrefActor;
    }

    private Director getDirectorInput(Scanner sc){
        Director PrefDirector = null;
        String firstname, lastname;
        System.out.println("Director:(yes/no)");
        if(Objects.equals(sc.nextLine(), "yes")){
            System.out.println("Enter director's firstname: ");
            firstname = sc.nextLine();
            System.out.println("Enter director's lastname: ");
            lastname = sc.nextLine();
            int ok=0;
            for(Director director: directors){
                if(Objects.equals(firstname, director.getFirstName()) && Objects.equals(lastname, director.getLastName())){
                    ok=1;
                    PrefDirector = director;
                    break;
                }
            }
            if(ok==0){
                System.out.println("Director not in database!");
            }
        }

        return PrefDirector;
    }

    public int getRecommandations(User user){

        int ok=1;
        Preference preference = user.getPreference();
        List<Title>recommendations = new ArrayList<>();

        //Sortare pt a avea filmele si serialele cele mai bune ca rating recomandate primele

        movies.sort(new MovieRatingComparator());
        seriesL.sort(new SeriesRatingComparator());

        List<Title> Hy = HistoryRepository.getHistoryByUsername(user.getUsername());
        List<Integer> Ids = new ArrayList<>();
        for(Title t: Hy){
            Ids.add(t.getId());
        }

        if(preference.getType().equals("movie") || preference.getType().equals("")){
            for(Movie movie: movies){

                if(movie.matchesMoviePreference(preference) && !Ids.contains(movie.getId())){
                    recommendations.add(movie);
                }
            }
        }
        if(preference.getType().equals("collection") || preference.getType().equals("")){
            for(TitleCollection collection: collections){
                if(collection.matchesCollectionPreference(preference) && !Ids.contains(collection.getId())){
                    recommendations.add(collection);
                }
            }
        }
        if(preference.getType().equals("series") || preference.getType().equals("")){
            for(Series series: seriesL){
                if(series.matchesSeriesPreference(preference) && !Ids.contains(series.getId())){
                    recommendations.add(series);
                }
            }
        }

        if(recommendations.size()==0){
            System.out.println("There are no titles that match your preference! Please insert another preference.");
            ok=0;
        }
        else{
            for(Title item: recommendations){
                System.out.println(item.toString());
                System.out.println("\n\n");
            }
        }
        this.Recommendations = recommendations;

        try{
            auditService.logAction("Get Recommendation");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Get Recommendation!");
        }

        return ok;
    }

    public int chooseItemFromRecommendations(Scanner sc, User user, List<Title>recommendations){
        Title selection = null;
        while(true){
            System.out.println("Enter the title for the movie/series/movie collection you want(or none if you don't like the recommendations): ");
            String title = sc.nextLine().trim();
            if(title.equals("none")){
                return 0;
            }
            for(Title item: recommendations){
                if(Objects.equals(title, item.getTitle())){
                    selection = item;
                    break;
                }
            }
            if(selection == null){
                System.out.println("Title is not in recommendations!");
            }
            else{
                break;
            }
        }

        this.selected = selection;
        try{
            auditService.logAction("Choose Action for Title");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Choose action for title!");
        }
        return 1;

    }
    public void watchNow(User user, Title selection, Scanner sc){
        if(selection instanceof TitleCollection c){
            for(int i=1;i<=c.getNumberMovies();i++){
                System.out.println(i+". " + c.getMoviesInCollection().get(i-1).getTitle());
            }
            sc.nextLine();
            System.out.println("Choose the number of the movie you want to watch now!");
            int nr = Integer.parseInt(sc.nextLine());
            selection = c.getMoviesInCollection().get(nr-1);

        }
        addToHistory(user, selection);

        System.out.println("You watched " + selection.getTitle() + "!");
        System.out.println("Do you want to add " + selection.getTitle() + " to Favorites?(yes/no)");
        String str = sc.next();
        sc.nextLine();
        if(Objects.equals(str, "yes")){
            addToFavorites(user, selection);
        }
        System.out.println("Do you want to add review to " + selection.getTitle() + "?(yes/no)");
        String str2 = sc.next();
        sc.nextLine();
        if (Objects.equals(str2, "yes")) {
            addReviewToItem(sc, user, selection);
        }
    }



    public void addToHistory(User user, Title selection){
        List<Title>Hy = HistoryRepository.getHistoryByUsername(user.getUsername());
        List<Integer>Ids = new ArrayList<>();
        for(Title t: Hy){
            Ids.add(t.getId());
        }
        if(!Ids.contains(selection.getId())){
            HistoryRepository.insertHistory(user.getUsername(), selection.getId());
        }
        try{
            auditService.logAction("Watch title");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Watch title!");
        }

    }

    public void addToFavorites(User user, Title selection){
        List<Title>Fav = FavoritesRepository.getFavoritesByUsername(user.getUsername());
        List<Integer>Ids = new ArrayList<>();
        for(Title t: Fav){
            Ids.add(t.getId());
        }
        if(!Ids.contains(selection.getId())){
            FavoritesRepository.insertFavorites(user.getUsername(), selection.getId());
            System.out.println(selection.getTitle() + " successfully added to favorites list!");
        }
        else{
            System.out.println(selection.getTitle() + " already in favorites list!");
        }
        try{
            auditService.logAction("Add Title to Favorites");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Add title to Favorites!");
        }

    }

    public void addReviewToItem(Scanner sc, User user, Title item){

        System.out.println("Enter rating(from 1 to 5): ");
        int rating = Integer.parseInt(sc.next());
        sc.nextLine();
        System.out.println("Enter review text: ");
        String comment = sc.nextLine();
        LocalDateTime dateAdd = LocalDateTime.now();
        Review review = null;
        int id = ReviewRepository.getMaxReviewId()+1;
        for(Movie m: movies){
            if(item.getId() == m.getId()){
                review = new Review(id, user, m, rating, comment, dateAdd);
                addReviewToMovie(m,review);
                break;
            }
        }
        for(Series s: seriesL){
            if(item.getId() == s.getId()){
                review = new Review(id, user, s, rating, comment, dateAdd);
                addReviewToSeries(s,review);
                break;
            }
        }

        System.out.println("Review created successfully!");
        System.out.println(review.toString());

        ReviewRepository.insertReview(id, user.getUsername(), item.getId(), rating, comment, dateAdd);

        try{
            auditService.logAction("Add Review");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Add Review!");
        }
    }

    public void addReviewToMovie(Movie movie, Review review){

        movie.getReviews().add(review);

    }

    public void addReviewToSeries(Series series, Review review){
        series.getReviewsS().add(review);
    }


    public void listAllMovies(){
        movies.clear();
        MovieRepository.transformDBToLists();
        System.out.println("Movies: {");
        for(Movie movie:movies){
            System.out.println(movie.toString());
            System.out.println("\n");
        }
        System.out.println("}");
        try{
            auditService.logAction("Show Movies");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Show Movies!");
        }
    }

    public void listAllCollections(){
        collections.clear();
        TitleCollectionRepository.transformDBToLists();
        System.out.println("Collections: {");
        for(TitleCollection collection:collections){
            System.out.println(collection.toString());
            System.out.println("\n");
        }
        System.out.println("}");

        try{
            auditService.logAction("Show Title Collections");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Show Title Collections!");
        }
    }

    public void listAllSeries(){
        seriesL.clear();
        SeriesRepository.transformDBToLists();
        System.out.println("Series: {");
        for(Series series:seriesL){
            System.out.println(series.toString());
            System.out.println("\n");
        }
        System.out.println("}");

        try{
            auditService.logAction("Show Series");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Show Series!");
        }
    }

    public void showTitle(Scanner sc){
        System.out.println("What title do you want to see?");
        sc.nextLine();
        String title = sc.next();
        sc.nextLine();

        int ok=0;
        for(Title item: titles){
            if(Objects.equals(title, item.getTitle())){
                for(Movie m: movies){
                    if(item.getId() == m.getId()){
                        System.out.println(m);
                        ok=1;
                        break;
                    }
                }
                for(TitleCollection c: collections){
                    if(item.getId() == c.getId()){
                        System.out.println(c);
                        ok=1;
                        break;
                    }
                }
                for(Series s: seriesL){
                    if(item.getId() == s.getId()){
                        System.out.println(s);
                        ok=1;
                        break;
                    }
                }
            }
        }
        if(ok==0){
            System.out.println("Title doesn't exit!");
        }

        try{
            auditService.logAction("Show Title Details");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Show title details!");
        }
    }

    public void listHistory(User user){
        users.clear();
        UserRepository.transformDBToLists();
        List<Title>Hy = HistoryRepository.getHistoryByUsername(user.getUsername());
        for(Title t : Hy){
            System.out.println(t);
        }
        System.out.println("History: ");
        for(Title item: Hy){
            for(Movie m: movies){
                if(item.getId() == m.getId()){
                    System.out.println(m);
                    break;
                }
            }
            for(TitleCollection c: collections){
                if(item.getId() == c.getId()){
                    System.out.println(c);
                    break;
                }
            }
            for(Series s: seriesL){
                if(item.getId() == s.getId()){
                    System.out.println(s);
                    break;
                }
            }
        }

    }

    public void listFavorites(User user, Scanner sc){
        users.clear();
        UserRepository.transformDBToLists();
        List<Title>Fav = FavoritesRepository.getFavoritesByUsername(user.getUsername());

        System.out.println("Favorites: ");
        sc.nextLine();
        int ok = 0;
        for(Title item: Fav){
            for(Movie m: movies){
                if(item.getId() == m.getId()){
                    System.out.println(m);
                    ok=1;
                    break;
                }
            }
            for(TitleCollection c: collections){
                if(item.getId() == c.getId()){
                    System.out.println(c);
                    ok=1;
                    break;
                }
            }
            for(Series s: seriesL){
                if(item.getId() == s.getId()){
                    System.out.println(s);
                    ok=1;
                    break;
                }
            }

        }
        if(ok==0){
            System.out.println("No Favorites yet!");
        }
        else{
            System.out.println("Do you want to remove a title from favorites?(yes/no)");
            if(Objects.equals(sc.nextLine(), "yes")){
                removeFromFavorites(sc,user);
            }
        }
    }

    public void removeFromFavorites(Scanner sc, User user){
        System.out.println("What title do you want to remove from Favorites?");
        String title = sc.nextLine();
        List<Title>Fav = FavoritesRepository.getFavoritesByUsername(user.getUsername());
        int ok=0;
        Title t = null;
        for(Title item:Fav){
            if(Objects.equals(title, item.getTitle())){
                FavoritesRepository.deleteFavoritesByUsernameAndTitle(user.getUsername(), item.getId());
                ok=1;
                break;
            }
        }
        if(ok==0){
            System.out.println("This title is not in your Favorites!");
        }
    }


    public void listAllItemReviews(Title item){

        if(item instanceof Movie movie){
            listAllReviewsFromMovie(movie);
        }
        else if(item instanceof Series series){
            listAllReviewsFromSeries(series);
        }
        else if(item instanceof TitleCollection collection){
            for(Movie m: collection.getMoviesInCollection()){
                List<Review>R = ReviewRepository.getReviewsByTitle(m.getId());
                if(R.size()==0){
                    System.out.println("No reviews yet!");
                }
                else{
                    for (Review review : R) {
                        System.out.println(review);
                    }
                }
            }
        }
    }

    public void listAllReviewsFromMovie(Movie movie){
        if(movie.getReviews().size()==0){
            System.out.println("No reviews yet!");
        }
        else{
            System.out.println("{");
            for(Review review: movie.getReviews()){
                System.out.println(review.toString());
            }
            System.out.println("}");
        }

    }

    public void listAllReviewsFromSeries(Series series){
        if(series.getReviewsS().size()==0){
            System.out.println("No reviews yet!");
        }
        else{
            System.out.println("{");
            for(Review review: series.getReviewsS()){
                System.out.println(review.toString());
            }
            System.out.println("}");
        }

    }



    //Add data




}


