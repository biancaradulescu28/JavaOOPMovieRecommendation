import Cast.Actor;
import Cast.Director;
import Movie.Collection;
import Movie.Movie;
import Movie.Review;
import Movie.Series;
import Movie.Item;
import User.User;
import User.Preference;
import Movie.MovieRatingComparator;
import Movie.SeriesRatingComparator;

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


    //Lists
    private List<Actor> actors = new ArrayList<>();
    private List<Director> directors = new ArrayList<>();
    private List<Collection> collections = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private List<Series> seriesL = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private Set<User> users = new HashSet<User>();
    private  List<Item> allItems = new ArrayList<>();

    private int ItemId = 0;
    private int ReviewId = 1000;
    private int ActorId = 10000;
    private int DirectorId = 30000;


    //Getters/Setters


    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int getReviewId() {
        return ReviewId;
    }

    public void setReviewId(int reviewId) {
        ReviewId = reviewId;
    }

    public int getActorId() {
        return ActorId;
    }

    public void setActorId(int actorId) {
        ActorId = actorId;
    }

    public int getDirectorId() {
        return DirectorId;
    }

    public void setDirectorId(int directorId) {
        DirectorId = directorId;
    }

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

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Item> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<Item> allItems) {
        this.allItems = allItems;
    }




    //USER ACTIONS

    private User loggedInUser;
    private List<Item>Recommendations;
    private Item selected;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public List<Item> getrecommendations() {
        return Recommendations;
    }

    public void setrecommendations(List<Item> recommendations) {
        Recommendations = recommendations;
    }

    public Item getSelected() {
        return selected;
    }
    public void setSelected(Item selected) {
        this.selected = selected;
    }

    public int signIn(Scanner sc){
        int nr = 3;//3 incercari
        int rez=0;
        sc.nextLine();
        User user = null;
        while(nr!=0){
            System.out.println("Enter username: ");
            String username = sc.nextLine();

            for(User u:users){
                if(Objects.equals(u.getUsername(), username)){
                    user = u;
                    break;
                }
            }
            if(user==null){
                System.out.println("Username incorect!");
                nr--;
            }
            else{
                while(true){
                    System.out.println("Enter password: ");
                    String password = sc.nextLine();
                    if(!Objects.equals(user.getPassword(), password)){
                        System.out.println("Incorect password!");
                    }
                    else{
                        break;
                    }
                }
                break;
            }
        }
        if(user==null){
            System.out.println("Please create account before signing in!");
        }
        else{
            System.out.println("Signed in successfully!");
            rez=1;
            this.loggedInUser = user;
        }
        return rez;
    }


    public User createUser(Scanner sc) {
        // Get user input
        sc.nextLine();
        String username = getUsernameInput(sc);
        System.out.println("Enter firstname: ");
        String firstname = sc.nextLine();
        System.out.println("Enter lastname: ");
        String lastname = sc.nextLine();
        String email = getEmailInput(sc);
        String password = getPasswordInput(sc);

        // Create new user
        List<Review> userReviews = new ArrayList<Review>();
        Preference preference = new Preference();
        List<Item>WatchHistory = new ArrayList<>();
        List<Item>WatchLater = new ArrayList<>();
        List<Item>Favorites = new ArrayList<>();
        User newUser = new User(username, firstname, lastname, email, password, userReviews, preference, WatchHistory, WatchLater, Favorites);
        users.add(newUser);
        System.out.println("User created successfully!");
        System.out.println(newUser.toString());

        this.loggedInUser = newUser;

        return newUser;
    }

    private String getUsernameInput(Scanner sc) {
        String username = "";
        while(true){
            System.out.println("Enter username: ");
            username = sc.nextLine();
            int ok=1;
            for(User user: users){
                if(Objects.equals(user.getUsername(), username)){
                    ok=0;
                    System.out.println("Username is already used!");
                    break;
                }
            }
            if(ok==1){
                break;
            }

        }
        return username;
    }

    private String getEmailInput(Scanner sc) {
        String email;
        while (true) {
            System.out.println("Enter email: ");
            email = sc.nextLine();
            if (!email.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")) {
                System.out.println("Invalid email address!");
            } else {
                break;
            }
        }
        return email;
    }

    private String getPasswordInput(Scanner sc) {
        String password;
        while (true) {
            System.out.println("Enter password: ");
            password = sc.nextLine();
            if (!password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")) {
                System.out.println("Password must be at least 8 characters long and contain a mix of upper and lower case letters, numbers, and special characters!");
            } else {
                break;
            }
        }
        return password;
    }


    public void insertPreference(User user, Scanner sc){
        System.out.println("Insert your preference. Insert <<no>> where you don't want to complete the field.");
        sc.nextLine();
        String type = getInput(sc,"Enter type (movie/collection/series/no): ");
        String genre = getInput(sc,"Enter genre: ");
        int releaseYear = getReleaseYearInput(sc);
        String language = getInput(sc, "Enter language: ");
        Actor PrefActor = getActorInput(sc);
        Director PrefDirector = getDirectorInput(sc);

        Preference preference = new Preference(type, genre, PrefActor, PrefDirector, releaseYear, language);
        user.setPreference(preference);
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
        System.out.println("Enter actor:(yes/no)");
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
        System.out.println("Enter director:(yes/no)");
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

    public void getRecommandations(User user){
        Preference preference = user.getPreference();
        List<Item>recommendations = new ArrayList<>();

        //Sortare pt a avea filmele si serialele cele mai bune ca rating recomandate primele

        movies.sort(new MovieRatingComparator());
        seriesL.sort(new SeriesRatingComparator());

        if(preference.getType().equals("movie") || preference.getType().equals("")){
            for(Movie movie: movies){
                if(movie.matchesMoviePreference(preference) && !user.getWatchHistory().contains(movie) && !user.getWatchLater().contains(movie)){
                    recommendations.add(movie);
                }
            }
        }
        if(preference.getType().equals("collection") || preference.getType().equals("")){
            for(Collection collection: collections){
                if(collection.matchesCollectionPreference(preference) && !user.getWatchHistory().contains(collection) && !user.getWatchLater().contains(collection)){
                    recommendations.add(collection);
                }
            }
        }
        if(preference.getType().equals("series") || preference.getType().equals("")){
            for(Series series: seriesL){
                if(series.matchesSeriesPreference(preference) && !user.getWatchHistory().contains(series) && !user.getWatchLater().contains(series)){
                    recommendations.add(series);
                }
            }
        }
        for(Item item: recommendations){
            System.out.println(item.toString());
            System.out.println("\n\n");
        }

        if(recommendations.size()==0){
            System.out.println("There are no titles that match your preference! Please insert another preference.");
        }
        this.Recommendations = recommendations;
    }

    public void chooseItemFromRecommendations(Scanner sc, User user, List<Item>recommendations){
        Item selection = null;
        while(true){
            System.out.println("Enter the title for the movie/series/movie collection you want(or none if you don't like the recommendations): ");
            String title = sc.nextLine().trim();
            if(title.equals("none")){
                return;
            }
            for(Item item: recommendations){
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

    }
    public void watchNow(User user, Item selection, Scanner sc){
        if(selection instanceof Collection c){
            for(int i=1;i<=c.getMoviesInCollection().size();i++){
                System.out.println(i+". " + c.getMoviesInCollection().get(i-1).getTitle());
            }
            sc.nextLine();
            System.out.println("Choose the number of the movie you want to watch now!");
            int nr = Integer.parseInt(sc.nextLine());
            selection = c.getMoviesInCollection().get(nr-1);

        }
        user.getWatchHistory().add(selection);
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

    public void watchLater(User user, Item selection){
        user.getWatchLater().add(selection);
        System.out.println(selection.getTitle() + " successfully added to WatchLater list!");
    }

    public void addToFavorites(User user, Item selection){
        user.getFavorites().add(selection);
        System.out.println(selection.getTitle() + " successfully added to favorites list!");
    }
    public void listAllItems(){
        for(Item item: allItems){
            System.out.println(item.toString());
        }
    }
    public void listAllMovies(){
        System.out.println("Movies: {");
        for(Movie movie:movies){
            System.out.println(movie.toString());
        }
        System.out.println("}");
    }

    public void listAllCollections(){
        System.out.println("Collections: {");
        for(Collection collection:collections){
            System.out.println(collection.toString());
        }
        System.out.println("}");
    }

    public void listAllSeries(){
        System.out.println("Series: {");
        for(Series series:seriesL){
            System.out.println(series.toString());
        }
        System.out.println("}");
    }

    public void showTitle(Scanner sc){
        System.out.println("What title do you want to see?");
        sc.nextLine();
        String title = sc.nextLine();
        int ok=0;
        for(Item item: allItems){
            if(Objects.equals(title, item.getTitle())){
                if(item instanceof Movie movie){
                    System.out.println(movie.toString());
                }
                else if(item instanceof Collection collection){
                    System.out.println(collection.toString());
                }
                else if(item instanceof Series series){
                    System.out.println(series.toString());
                }
                ok = 1;
            }
        }
        if(ok==0){
            System.out.println("Title doesn't exit!");
        }
    }

    public void listHistory(User user){
        System.out.println("History: ");
        for(Item item: user.getWatchHistory()){
            if(item instanceof Movie movie){
                System.out.println(movie.toString());
            }
            else if(item instanceof Collection collection){
                System.out.println(collection.toString());
            }
            else if(item instanceof Series series){
                System.out.println(series.toString());
            }
        }

    }
    public void listWatchLater(User user,Scanner sc){
        System.out.println("Watch Later: ");
        for(Item item: user.getWatchLater()){
            if(item instanceof Movie movie){
                System.out.println(movie.toString());
            }
            else if(item instanceof Collection collection){
                System.out.println(collection.toString());
            }
            else if(item instanceof Series series){
                System.out.println(series.toString());
            }
        }
        System.out.println("Do you want to watch a title?(yes/no)");
        if(Objects.equals(sc.nextLine(), "yes")){
            chooseFromWatchLater(sc, user);
        }

    }

    public void chooseFromWatchLater(Scanner sc, User user){
        Item item = null;
        while(true){
            System.out.println("Enter the title you want to watch: ");
            String title = sc.nextLine();
            for(Item i: user.getWatchLater()){
                if(Objects.equals(title,i.getTitle())){
                    item = i;
                    break;
                }
            }
            if(item == null){
                System.out.println("Title not in Watch Later list");
            }
            else{
                break;
            }
        }
        user.getWatchLater().remove(item);
        watchNow(user, item, sc);
    }

    public void listFavorites(User user, Scanner sc){
        System.out.println("Favorites: ");
        for(Item item: user.getWatchHistory()){
            if(item instanceof Movie movie){
                System.out.println(movie.toString());
            }
            else if(item instanceof Collection collection){
                System.out.println(collection.toString());
            }
            else if(item instanceof Series series){
                System.out.println(series.toString());
            }
        }
        System.out.println("Do you want to remove a title from favorites?(yes/no)");
        if(Objects.equals(sc.nextLine(), "yes")){
            removeFromFavorites(sc,user);
        }
    }

    public void removeFromFavorites(Scanner sc, User user){
        System.out.println("What title do you want to remove from Favorites?");
        String title = sc.nextLine();
        int ok=0;
        for(Item item: user.getFavorites()){
            if(Objects.equals(title, item.getTitle())){
                user.getFavorites().remove(item);
                ok=1;
                break;
            }
        }
        if(ok==0){
            System.out.println("This title is not in your Favorites!");
        }
    }

    public void addReviewToItem(Scanner sc, User user, Item item){
        int id = ReviewId+1;
        System.out.println("Enter rating(from 1 to 5): ");
        int rating = Integer.parseInt(sc.next());
        sc.nextLine();
        System.out.println("Enter review text: ");
        String comment = sc.nextLine();
        LocalDateTime dateAdd = LocalDateTime.now();
        Review review = null;
        if(item instanceof Movie m){
            review = new Review(id, user, m, rating, comment, dateAdd);
            addReviewToMovie(m,review);
        }
        if(item instanceof Series s){
            review = new Review(id, user, s, rating, comment, dateAdd);
            addReviewToSeries(s,review);
        }
        addReview(review);
        user.getUserReviews().add(review);
        System.out.println("Review created successfully!");
        System.out.println(review.toString());
    }


    public void listAllItemReviews(Item item){
        if(item instanceof Movie movie){
            listAllReviewsFromMovie(movie);
        }
        else if(item instanceof Series series){
            listAllReviewsFromSeries(series);
        }
        else if(item instanceof Collection collection){
            for(Movie m: collection.getMoviesInCollection()){
                listAllReviewsFromMovie(m);
            }
        }
    }













    //EDIT ACTIONS

    public void createCastMember(Scanner sc){
        System.out.println("What cast member do you want to create? (actor/director): ");
        String str = sc.next();
        if(Objects.equals(str, "actor")){
            createActor(sc);
        }
        else{
            createDirector(sc);
        }

    }

    public void createActor(Scanner sc){
        int id=0;
        if(actors.size()>=1){
            id = ActorId + actors.get(actors.size()-1).getCastId() + 1;
        }
        else{
            id = ActorId + 1;
        }
        sc.nextLine();
        System.out.println("Enter actor's firstname: ");
        String firstname = sc.nextLine();
        System.out.println("Enter actor's lastname: (or enter <<no>> if it is a stage name");
        String lastname = sc.nextLine();
        Actor actor = new Actor(firstname, lastname, id);
        if(!actors.contains(actor)){
            addActor(actor);
        }
    }

    public void createActorS(Scanner sc){
        int id=0;
        System.out.println("Enter actors: (Enter done when you've inserted all actors)");
        sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.equals("done")) {
                break;
            }
            String[] words = line.split(" ",2);
            if(actors.size()>=1){
                id = ActorId + actors.get(actors.size()-1).getCastId() + 1;
            }
            else{
                id = ActorId + 1;
            }
            String firstName = words[0];
            String lastName = words[1];
            Actor actor = new Actor(firstName, lastName, id);
            if(!actors.contains(actor)){
                addActor(actor);
            }

        }

    }

    public void createDirectorS(Scanner sc){
        int id=0;
        System.out.println("Enter directors: (Enter done when you've inserted all directors)");
        sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.equals("done")) {
                break;
            }
            String[] words = line.split(" ",2);
            if(actors.size()>=1){
                id = ActorId + actors.get(actors.size()-1).getCastId() + 1;
            }
            else{
                id = ActorId + 1;
            }
            String firstName = words[0];
            String lastName = words[1];

            Director director = new Director(firstName, lastName, id);
            if(!directors.contains(director)){
                addDirector(director);
            }

        }

    }


    public void createDirector(Scanner sc){
        int id=0;
        if(actors.size()>=1){
            id = ActorId + actors.get(actors.size()-1).getCastId() + 1;
        }
        else{
            id = ActorId + 1;
        }
        sc.nextLine();
        System.out.println("Enter director's firstname: ");
        String firstname = sc.nextLine();
        System.out.println("Enter actor's lastname: (or enter <<no>> if it is a stage name");
        String lastname = sc.nextLine();
        Director director = new Director(firstname, lastname, id);
        if(!directors.contains(director)){
            addDirector(director);
        }
    }
    public void createItem(Scanner sc){
        System.out.println("What item do you want to create? (movie/collection/series): ");
        String str = sc.next();
        sc.nextLine();
        if(Objects.equals(str, "movie")){
            Movie movie = createMovie(sc);
            allItems.add(movie);
            System.out.println("Movie created successfully!\n");
            System.out.println(movie.toString());
        }
        else if(Objects.equals(str, "collection")){
            Collection collection = createCollection(sc);
            allItems.add(collection);
            System.out.println("Movie collection created successfully!\n");
            System.out.println(collection.toString());
        }
        else if(Objects.equals(str, "series")){
            Series series = createSeries(sc);
            allItems.add(series);
            System.out.println("Series created successfully!\n");
            System.out.println("Series created successfully!\n");
            System.out.println(series.toString());
        }

    }

    public Movie createMovie(Scanner sc){
        int id = 0;
        if(allItems.size()>=1){
            id = ItemId + allItems.get(allItems.size()-1).getId() + 1;
        }
        else{
            id = ItemId + 1;
        }
        System.out.println("Enter movie title: ");
        String title = sc.nextLine();
        List<String>Genre = new ArrayList<>();
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
            for(Actor actor:actors){
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

            for(Director director: directors){
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
        addMovie(movie);

        return movie;
    }

    public Collection createCollection(Scanner sc){
        int id = 0;
        if(allItems.size()>=1){
            id = ItemId + allItems.get(allItems.size()-1).getId() + 1;
        }
        else{
            id = ItemId + 1;
        }
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
            for(Movie movie: movies){
                if(Objects.equals(movieTitle, movie.getTitle())){
                    m = movie;
                    break;
                }
            }
            if(m == null){
                System.out.println("Movie doesn't exist!");
            }
            moviesInCollection.add(m);
        }
        int numberMovies = moviesInCollection.size();
        Collection collection = new Collection(id, title, Genre, language, numberMovies, moviesInCollection);
        addCollection(collection);

        return collection;
    }

    public Series createSeries(Scanner sc){
        int id = 0;
        if(allItems.size()>=1){
            id = ItemId + allItems.get(allItems.size()-1).getId() + 1;
        }
        else{
            id = ItemId + 1;
        }
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
            for(Actor actor:actors){
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
            for(Director director: directors){
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
        addSeries(series);

        return series;
    }

    public void removeItem(Scanner sc){
        System.out.println("What title do you want to remove?");
        String title = sc.nextLine();
        int ok=0;
        for(Item item: allItems){
            if(Objects.equals(title, item.getTitle())){
                allItems.remove(item);
                if(item instanceof Movie movie){
                    movies.remove(movie);
                }
                else if(item instanceof Collection collection){
                    collections.remove(collection);
                }
                else if(item instanceof Series series){
                    seriesL.remove(series);
                }
                ok=1;
                break;
            }
        }
        if(ok==0){
            System.out.println("Title doesn't exist!");
        }
    }

    public void addMovieInCollection(Scanner sc){
        System.out.println("In which movie collection do you want to add a movie?");
        String collectionTitle = sc.nextLine();
        System.out.println("What movie do you want to add to " + collectionTitle + "?");
        String movieTitle = sc.nextLine();
        Collection collection = null;
        Movie movie = null;
        for(Collection c: collections){
            if(Objects.equals(collectionTitle, c.getTitle())){
                collection = c;
                break;
            }
        }
        for(Movie m: movies){
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
        }
    }

    public void ChangeSeriesDetails(Scanner sc){
        System.out.println("Which series do you want to edit?");
        String title = sc.nextLine();
        Series series = null;
        for(Series s:seriesL){
            if(Objects.equals(s.getTitle(), title)){
                series = s;
                break;
            }
        }
        if(series==null){
            System.out.println("Series doesn't exist!");
        }
        else{
            System.out.println("Do you want to add another season?(yes/no)");
            if(Objects.equals(sc.nextLine(), "yes")){
                int seasons = series.getNrSeasons();
                seasons += 1;
                series.setNrSeasons(seasons);
            }
            System.out.println("Did the series end?(yes/no)");
            if(Objects.equals(sc.nextLine(), "yes")){
                System.out.println("What year did the series end?");
                int finish = Integer.parseInt(sc.next());
                sc.nextLine();
                series.setFinishYear(finish);
            }
            System.out.println("Is a new actor starring in the series?(yes/no)");
            if(Objects.equals(sc.nextLine(), "yes")){
               System.out.println("Enter actor's firstname: ");
                String firstname = sc.nextLine();
                System.out.println("Enter actor's lastname: (or enter <<no>> if it is a stage name");
                String lastname = sc.nextLine();
                Actor a = null;
                for(Actor actor:actors){
                    if(Objects.equals(actor.getFirstName(), firstname) && Objects.equals(actor.getLastName(), lastname)){
                        a = actor;
                        break;
                    }
                }
                if(a == null){
                    System.out.println("Actor doesn't exist!");
                }
                else {
                    addActorToSeries(series,a);
                }
            }
        }
    }


    //Helper Activities

    //Movies
    public void addMovie(Movie movie){
        movies.add(movie);
    }
    //Actors in Movie
    public void addActorToMovie(Movie movie, Actor actor){
        movie.getActors().add(actor);
    }
    public void removeActorFromMovie(Movie movie, Actor actor){
        if(movie.getActors().contains(actor)){
            movie.getActors().remove(actor);
        }
        else{
            System.out.println(actor.getFirstName() + " " + actor.getLastName() + " didn't play in this movie!");
        }
    }
    public void listAllActorsFromMovie(Movie movie){
        System.out.println("{");
        for(Actor actor: movie.getActors()){
            System.out.println(actor.getFirstName() + " " + actor.getLastName());
        }
        System.out.println("}");
    }

    //Movie Reviews
    public void addReviewToMovie(Movie movie, Review review){
        movie.getReviews().add(review);
    }

    public void removeReviewFromMovie(Movie movie, Review review){
        if(movie.getReviews().contains(review)){
            movie.getReviews().remove(review);
        }
        else{
            System.out.println("Review doesn't exist for movie" + movie.getTitle() + "!");
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

    //Collections
    public void addCollection(Collection collection){
        collections.add(collection);
    }
    public void removeCollection(Collection collection){
        if(collections.contains(collection)){
            collections.remove(collection);
        }
        else{
            System.out.println("Collection doesn't exit!");
        }
    }

    //In Collection
    public void removeMovieFromCollection(Collection collection, Movie movie){
        if(collection.getMoviesInCollection().contains(movie)){
            collection.getMoviesInCollection().remove(movie);
        }
        else{
            System.out.println("Movie not in collection " + collection.getTitle() + "!");
        }
    }
    public void listAllMoviesInCollection(Collection collection){
        System.out.println(collection.toString());
    }


    //Series
    public void addSeries(Series series){
        seriesL.add(series);
    }

    public void removeSeries(Series series){
        if(seriesL.contains(series)){
            seriesL.remove(series);
        }
        else{
            System.out.println("Series doesn't exit!");
        }
    }


    //Actors in Series
    public void addActorToSeries(Series series, Actor actor){series.getActorsS().add(actor);
    }
    public void removeActorFromSeries(Series series, Actor actor){
        if(series.getActorsS().contains(actor)){
            series.getActorsS().remove(actor);
        }
        else{
            System.out.println(actor.getFirstName() + " " + actor.getLastName() + " didn't play in this series!");
        }
    }
    public void listAllActorsFromSeries(Series series){
        System.out.println("{");
        for(Actor actor: series.getActorsS()){
            System.out.println(actor.getFirstName() + " " + actor.getLastName());
        }
        System.out.println("}");
    }

    //Series Reviews
    public void addReviewToSeries(Series series, Review review){series.getReviewsS().add(review);
    }

    public void removeReviewFromSeries(Series series, Review review){
        if(series.getReviewsS().contains(review)){
            series.getReviewsS().remove(review);
        }
        else{
            System.out.println("Review doesn't exist for movie" + series.getTitle() + "!");
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


    //Reviews
    public void addReview(Review review){
        reviews.add(review);
    }
    public void removeReview(Review review){
        if(reviews.contains(review)){
            reviews.remove(review);
        }
        else{
            System.out.println("Review doesn't exist!");
        }
    }
    public void listAllReviews(){
        System.out.println("{");
        for(Review review:reviews){
            System.out.println(review.getItem() + ": " + review.toString());
        }
        System.out.println("}");
    }


    //Actors
    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void removeActor(Actor actor) {
        if(actors.contains(actor)){
            actors.remove(actor);
        }
        else{
            System.out.println("Actor not found!");
        }
    }

    public void listAllActors(){
        System.out.println("{");
        for(Actor actor: actors){
            System.out.println(actor.getFirstName() + " " + actor.getLastName());
        }
        System.out.println("}");
    }



    //Director
    public void addDirector(Director director) {
        directors.add(director);
    }

    public void removeDirector(Director director) {
        if(directors.contains(director)){
            directors.remove(director);
        }
        else{
            System.out.println("Director not found!");
        }
    }

    public void listAllDirectors(){
        System.out.println("{");
        for(Director director: directors){
            System.out.println(director.getFirstName() + " " + director.getLastName());
        }
        System.out.println("}");
    }

    //User
    public void addUser(User user){
        users.add(user);
    }

    public void listAllUser(){
        System.out.println("{");
        for(User user: users){
            System.out.println(user.toString());
        }
        System.out.println("}");
    }

    public void addUserReview(User user, Review review){
        user.getUserReviews().add(review);
    }
    public void listAllUserReviews(User user){
        System.out.println("{");
        for(Review review: user.getUserReviews()){
            System.out.println("Review: " + review.getComment() + "\n" +
                    "Rating: " + review.getRating() + "\n" +
                    "Added at: " + review.getDateAdd() + "\n");
        }
        System.out.println("}");
    }











    //PRINT
    public void printActor(Actor actor){
        System.out.println("actors.add(new Actor(" + "\"" + actor.getFirstName() + "\", \""
                + actor.getLastName() + "\", " + actor.getCastId() + "));");
    }

    public void printActors(){
        for(Actor actor:actors){
            printActor(actor);
        }
    }

    public void printDirector(Director director){
        System.out.println("directors.add(new Director(" + "\"" + director.getFirstName() + "\", \""
                + director.getLastName() + "\", " + director.getCastId() + "));");
    }

    public void printDirectors(){
        for(Director director:directors){
            printDirector(director);
        }
    }



    public void printMovie(Movie movie){
        StringBuilder sb = new StringBuilder();
        sb.append("movies.add(new Movie(").append(movie.getId()).append(", \"")
                .append(movie.getTitle()).append("\", Arrays.asList(");
        for(int i=0;i<movie.getGenre().size()-1;i++){
            sb.append("\"").append(movie.getGenre().get(i)).append("\",");
        }
        sb.append("\"").append(movie.getGenre().get(movie.getGenre().size()-1)).append("\"), \"")
                .append(movie.getLanguage()).append("\", ");
        sb.append("Arrays.asList(");
        for (int i = 0; i < movie.getActors().size(); i++) {
            Actor actor = movie.getActors().get(i);
            for (int j = 0; j < actors.size(); j++) {
                if (Objects.equals(actor, actors.get(j))) {
                    sb.append("actors.get(").append(j).append(")");
                    if (i < movie.getActors().size() - 1) {
                        sb.append(", ");
                    }
                    break;
                }
            }
        }
        sb.append("), ");
        for(int i=0;i<directors.size();i++){
            if(Objects.equals(directors.get(i),movie.getDirector())){
                sb.append("directors.get(").append(i).append("),");
                break;
            }
        }
        sb.append(movie.getReleaseYear()).append(", new ArrayList<>()));");
        System.out.println(sb.toString());

    }
    public void printMovies(){
        //movies.sort(new MovieIdComparator());
        for(Movie movie: movies){
            printMovie(movie);
        }
    }


    public void printSeries(Series series){
        StringBuilder sb = new StringBuilder();
        sb.append("seriesL.add(new Series(").append(series.getId()).append(", \"")
                .append(series.getTitle()).append("\", Arrays.asList(");
        for(int i=0;i<series.getGenre().size()-1;i++){
            sb.append("\"").append(series.getGenre().get(i)).append("\",");
        }
        sb.append("\"").append(series.getGenre().get(series.getGenre().size()-1)).append("\"), \"")
                .append(series.getLanguage()).append("\", ");
        sb.append("Arrays.asList(");
        for (int i = 0; i < series.getActorsS().size(); i++) {
            Actor actor = series.getActorsS().get(i);
            for (int j = 0; j < actors.size(); j++) {
                if (Objects.equals(actor, actors.get(j))) {
                    sb.append("actors.get(").append(j).append(")");
                    if (i < series.getActorsS().size() - 1) {
                        sb.append(", ");
                    }
                    break;
                }
            }
        }
        sb.append("), ");
        for(int i=0;i<directors.size();i++){
            if(Objects.equals(directors.get(i),series.getDirectorS())){
                sb.append("directors.get(").append(i).append("),");
                break;
            }
        }
        sb.append(series.getReleaseYearS()).append(", ").append(series.getFinishYear()).append(", ").append(series.getNrSeasons()).append(", new ArrayList<>()));");
        System.out.println(sb.toString());

    }
    public void printSeriesL(){
        for(Series series: seriesL){
            printSeries(series);
        }
    }

    public void printCollection(Collection collection){
        StringBuilder sb = new StringBuilder();
        sb.append("collections.add(new Collection(").append(collection.getId()).append(", \"")
                .append(collection.getTitle()).append("\", Arrays.asList(");
        for(int i=0;i<collection.getGenre().size()-1;i++){
            sb.append("\"").append(collection.getGenre().get(i)).append("\",");
        }
        sb.append("\"").append(collection.getGenre().get(collection.getGenre().size()-1)).append("\"), \"")
                .append(collection.getLanguage()).append("\", ").append(collection.getNumberMovies())
                .append(", Arrays.asList(");
        for(int i=0;i<collection.getMoviesInCollection().size();i++){
            for(int j=0;j<movies.size();j++){
                if(Objects.equals(collection.getMoviesInCollection().get(i),movies.get(j))){
                    sb.append("movies.get(").append(j).append(")");
                    if(i<collection.getMoviesInCollection().size()-1){
                        sb.append(", ");
                    }
                    break;
                }

            }
        }
        sb.append(")));");
        System.out.println(sb.toString());
    }

    public void printCollections(){
        for(Collection collection:collections){
            printCollection(collection);
        }
    }








//DATE LISTE

    {
        actors.add(new Actor("Tom", "Holland", 10001));
        actors.add(new Actor("Michael", "Keaton", 10002));
        actors.add(new Actor("Jon", "Favreau", 10003));
        actors.add(new Actor("Gwyneth", "Paltrow", 10004));
        actors.add(new Actor("Zendaya", " ", 10005));
        actors.add(new Actor("Donald", "Glover", 10006));
        actors.add(new Actor("Jacob", "Batalon", 10007));
        actors.add(new Actor("Laura", "Harrier", 10008));
        actors.add(new Actor("Robert", "Downey Jr.", 10009));
        actors.add(new Actor("Samuel", "L. Jackson", 10011));
        actors.add(new Actor("Cobie", "Smulders", 10013));
        actors.add(new Actor("J.B.", "Smoove", 10015));
        actors.add(new Actor("Martin", "Starr", 10017));
        actors.add(new Actor("Tony", "Revolori", 10018));
        actors.add(new Actor("Marisa", "Tomei", 10019));
        actors.add(new Actor("Jake", "Gyllenhaal", 10020));
        actors.add(new Actor("Benedict", "Cumberbatch", 10023));
        actors.add(new Actor("Jamie", "Foxx", 10026));
        actors.add(new Actor("Willem", "Dafoe", 10027));
        actors.add(new Actor("Alfred", "Molina", 10028));
        actors.add(new Actor("Benedict", "Wong", 10029));
        actors.add(new Actor("Andrew", "Garfield", 10032));
        actors.add(new Actor("Tobey", "Maguire", 10033));
        actors.add(new Actor("Meryl", "Streep", 10034));
        actors.add(new Actor("Anne", "Hathaway", 10035));
        actors.add(new Actor("Emily", "Blunt", 10036));
        actors.add(new Actor("Stanley", "Tucci", 10037));
        actors.add(new Actor("Simon", "Baker", 10038));
        actors.add(new Actor("Adrian", "Grenier", 10039));
        actors.add(new Actor("Leonardo", "DiCaprio", 10040));
        actors.add(new Actor("Kate", "Winslet", 10041));
        actors.add(new Actor("Billy", "Zane", 10042));
        actors.add(new Actor("Kathy", "Bates", 10043));
        actors.add(new Actor("Frances", "Fisher", 10044));
        actors.add(new Actor("Bernard", "Hill", 10045));
        actors.add(new Actor("Jonathan", "Hyde", 10046));
        actors.add(new Actor("Danny", "Nucci", 10047));
        actors.add(new Actor("David", "Warner", 10048));
        actors.add(new Actor("Bill", "Paxton", 10049));
        actors.add(new Actor("Scarlett", "Johansson", 10050));
        actors.add(new Actor("Morgan", "Freeman", 10051));
        actors.add(new Actor("Choi", "Min-sik", 10052));
        actors.add(new Actor("Amr", "Waked", 10053));
        actors.add(new Actor("Chloë", "Grace Moretz", 10054));
        actors.add(new Actor("Nick", "Robinson", 10055));
        actors.add(new Actor("Ron", "Livingston", 10056));
        actors.add(new Actor("Maggie", "Siff", 10057));
        actors.add(new Actor("Alex", "Roe", 10058));
        actors.add(new Actor("Maria", "Bello", 10059));
        actors.add(new Actor("Maika", "Monroe", 10060));
        actors.add(new Actor("Liev", "Schreiber", 10061));
        actors.add(new Actor("Cher", " ", 10062));
        actors.add(new Actor("Christina", "Aguilera", 10063));
        actors.add(new Actor("Eric", "Dane", 10064));
        actors.add(new Actor("Cam", "Gigandet", 10065));
        actors.add(new Actor("Julianne", "Hough", 10066));
        actors.add(new Actor("Alan", "Cumming", 10067));
        actors.add(new Actor("Sarah", "Snook", 10068));
        actors.add(new Actor("Mark", "Webber", 10069));
        actors.add(new Actor("David", "Andrews", 10070));
        actors.add(new Actor("Joelle", "Carter", 10071));
        actors.add(new Actor("Ana", "de la Reguera", 10072));
        actors.add(new Actor("Benicio", "Del Toro", 10073));
        actors.add(new Actor("Tim", "Robbins", 10074));
        actors.add(new Actor("Olga", "Kurylenko", 10075));
        actors.add(new Actor("Mélanie", "Thierry", 10076));
        actors.add(new Actor("Shailene", "Woodley", 10077));
        actors.add(new Actor("Theo", "James", 10078));
        actors.add(new Actor("Ashley", "Judd", 10079));
        actors.add(new Actor("Jai", "Courtney", 10080));
        actors.add(new Actor("Ray", "Stevenson", 10081));
        actors.add(new Actor("Zoë", "Kravitz", 10082));
        actors.add(new Actor("Miles", "Teller", 10083));
        actors.add(new Actor("Octavia", "Spencer", 10086));
        actors.add(new Actor("Ansel", "Elgort", 10091));
        actors.add(new Actor("Jeff", "Daniels", 10094));
        actors.add(new Actor("Maggie", "Q", 10098));
        actors.add(new Actor("Adam", "Sandler", 10099));
        actors.add(new Actor("Jennifer", "Aniston", 10100));
        actors.add(new Actor("Luke", "Evans", 10101));
        actors.add(new Actor("Terence", "Stamp", 10102));
        actors.add(new Actor("Gemma", "Arterton", 10103));
        actors.add(new Actor("David", "Walliams", 10104));
        actors.add(new Actor("Mark", "Strong", 10107));
        actors.add(new Actor("Mélanie", "Laurent", 10108));
        actors.add(new Actor("Jodie", "Turner-Smith", 10109));
        actors.add(new Actor("John", "Kani", 10110));
        actors.add(new Actor("Reese", "Witherspoon", 10111));
        actors.add(new Actor("Sam", "Worthington", 10117));
        actors.add(new Actor("Zoe", "Saldana", 10118));
        actors.add(new Actor("Stephen", "Lang", 10119));
        actors.add(new Actor("Michelle", "Rodriguez", 10120));
        actors.add(new Actor("Sigourney", "Weaver", 10121));
        actors.add(new Actor("Zoe", "Saldaña", 10123));
        actors.add(new Actor("Úrsula", "Corberó", 20124));
        actors.add(new Actor("Álvaro", "Morte", 30125));
        actors.add(new Actor("Itziar", "Ituño", 40126));
        actors.add(new Actor("Pedro", "Alonso", 50127));
        actors.add(new Actor("Paco", "Tous", 60128));
        actors.add(new Actor("Alba", "Flores", 70129));
        actors.add(new Actor("Miguel", "Herrán", 80130));
        actors.add(new Actor("Jaime", "Lorente", 90131));
        actors.add(new Actor("Esther", "Acebo", 100132));
        actors.add(new Actor("Enrique", "Arce", 110133));
        actors.add(new Actor("María", "Pedraza", 120134));
        actors.add(new Actor("Darko", "Perić", 130135));
        actors.add(new Actor("Kiti", "Mánver", 140136));
        actors.add(new Actor("Sean", "Bean", 150137));
        actors.add(new Actor("Nikolaj", "Coster-Waldau", 160138));
        actors.add(new Actor("Michelle", "Fairley", 170139));
        actors.add(new Actor("Lena", "Headey", 180140));
        actors.add(new Actor("Emilia", "Clarke", 190141));
        actors.add(new Actor("Iain", "Glen", 200142));
        actors.add(new Actor("Kit", "Harington", 210143));
        actors.add(new Actor("Richard", "Madden", 220144));
        actors.add(new Actor("Sophie", "Turner", 230145));
        actors.add(new Actor("Maisie", "Williams", 240146));
        actors.add(new Actor("Peter", "Dinklage", 250147));
        actors.add(new Actor("Nina", "Dobrev", 260148));
        actors.add(new Actor("Paul", "Wesley", 270149));
        actors.add(new Actor("Ian", "Somerhalder", 280150));
        actors.add(new Actor("Steven", "R. McQueen", 290151));
        actors.add(new Actor("Sara", "Canning", 300152));
        actors.add(new Actor("Kat", "Graham", 310153));
        actors.add(new Actor("Candice", "King", 320154));
        actors.add(new Actor("Zach", "Roerig", 330155));
        actors.add(new Actor("Kayla", "Ewell", 340156));
        actors.add(new Actor("Michael", "Trevino", 350157));
        actors.add(new Actor("Matt", "Davis", 360158));
        actors.add(new Actor("Joseph", "Morgan", 370159));
        actors.add(new Actor("Michael", "Malarkey", 380160));
        actors.add(new Actor("Steve", "Carell", 390161));
        actors.add(new Actor("Rainn", "Wilson", 400162));
        actors.add(new Actor("John", "Krasinski", 410163));
        actors.add(new Actor("Jenna", "Fischer", 420164));
        actors.add(new Actor("B.", "J. Novak", 430165));
        actors.add(new Actor("Melora", "Hardin", 440166));
        actors.add(new Actor("David", "Denman", 450167));
        actors.add(new Actor("Leslie", "David Baker", 460168));
        actors.add(new Actor("Brian", "Baumgartner", 470169));
        actors.add(new Actor("Kate", "Flannery", 480170));
        actors.add(new Actor("Angela", "Kinsey", 490171));
        actors.add(new Actor("Oscar", "Nunez", 500172));
        actors.add(new Actor("Phyllis", "Smith", 510173));
        actors.add(new Actor("Ed", "Helms", 520174));
        actors.add(new Actor("Connie", "Britton", 530175));
        actors.add(new Actor("Dylan", "McDermott", 540176));
        actors.add(new Actor("Evan", "Peters", 550177));
        actors.add(new Actor("Taissa", "Farmiga", 560178));
        actors.add(new Actor("Denis", "O'Hare", 570179));
        actors.add(new Actor("Jessica", "Lange", 580180));
        actors.add(new Actor("Zachary", "Quinto", 590181));
        actors.add(new Actor("Joseph", "Fiennes", 600182));
        actors.add(new Actor("Sarah", "Paulson", 610183));
        actors.add(new Actor("Lily", "Rabe", 620184));
        actors.add(new Actor("Lizzie", "Brocheré", 630185));
        actors.add(new Actor("James", "Cromwell", 640186));
        actors.add(new Actor("Frances", "Conroy", 650187));
        actors.add(new Actor("Emma", "Roberts", 660188));
        actors.add(new Actor("Jenna", "Ortega", 670189));
        actors.add(new Actor("Gwendoline", "Christie", 680190));
        actors.add(new Actor("Riki", "Lindhome", 690191));
        actors.add(new Actor("Jamie", "McShane", 700192));
        actors.add(new Actor("Hunter", "Doohan", 710193));
        actors.add(new Actor("Percy", "Hynes White", 720194));
        actors.add(new Actor("Emma", "Myers", 730195));
        actors.add(new Actor("Joy", "Sunday", 740196));
        actors.add(new Actor("Georgie", "Farmer", 750197));
        actors.add(new Actor("Naomi", "J. Ogawa", 760198));
        actors.add(new Actor("Moosa", "Mostafa", 770199));
        actors.add(new Actor("Christina", "Ricci", 780200));
        actors.add(new Actor("Itzan", "Escamilla", 790201));
        actors.add(new Actor("Miguel", "Bernardeau", 800202));
        actors.add(new Actor("Álvaro", "Rico", 810203));
        actors.add(new Actor("Arón", "Piper", 820204));
        actors.add(new Actor("Mina", "El Hammani", 830205));
        actors.add(new Actor("Ester", "Expósito", 840206));
        actors.add(new Actor("Omar", "Ayuso", 850207));
        actors.add(new Actor("Danna", "Paola", 860208));
        actors.add(new Actor("Jorge", "López", 870209));
        actors.add(new Actor("Claudia", "Salas", 880210));
        actors.add(new Actor("Georgina", "Amorós", 890211));
        actors.add(new Actor("Carla", "Díaz", 900212));
        actors.add(new Actor("Martina", "Cariddi", 910213));
        actors.add(new Actor("Pol", "Granch", 920214));
        actors.add(new Actor("Manu", "Ríos", 930215));
        actors.add(new Actor("Tyler", "Posey", 940216));
        actors.add(new Actor("Crystal", "Reed", 950217));
        actors.add(new Actor("Dylan", "O'Brien", 960218));
        actors.add(new Actor("Tyler", "Hoechlin", 970219));
        actors.add(new Actor("Holland", "Roden", 980220));
        actors.add(new Actor("Colton", "Haynes", 990221));
        actors.add(new Actor("Shelley", "Hennig", 1000222));
        actors.add(new Actor("Arden", "Cho", 1010223));
        actors.add(new Actor("Dylan", "Sprayberry", 1020224));
        actors.add(new Actor("Linden", "Ashby", 1030225));
        actors.add(new Actor("Melissa", "Ponzio", 1040226));
        actors.add(new Actor("JR", "Bourne", 1050227));

    }

    {
        directors.add(new Director("Jon", "Watts", 10127));
        directors.add(new Director("Wendy", "Finerman", 10128));
        directors.add(new Director("James", "Cameron", 10129));
        directors.add(new Director("Jeremy", "Garelick", 10130));
        directors.add(new Director("Kyle", "Newacheck", 10131));
        directors.add(new Director("Robert", "Schwentke", 10132));
        directors.add(new Director("Neil", "Burger", 10133));
        directors.add(new Director("Fernando", "León de Aranoa", 10134));
        directors.add(new Director("Kevin", "Greutert", 10135));
        directors.add(new Director("Steven", "Antin", 10136));
        directors.add(new Director("Jonathan", "Blakeson", 10137));
        directors.add(new Director("Luc", "Besson", 10138));
        directors.add(new Director("Álex", "Pina", 260148));
        directors.add(new Director("David", "Benioff", 260148));
        directors.add(new Director("Kevin", "Williamson", 1060228));
        directors.add(new Director("Greg", "Daniels", 1060228));
        directors.add(new Director("Ryan", "Murphy", 1060228));
        directors.add(new Director("Tim", "Burton", 1060228));
        directors.add(new Director("Carlos", "Montero", 1060228));
        directors.add(new Director("Jeff", "Davis", 1060228));

    }

    {
        movies.add(new Movie(1, "Spider-Man: Homecoming", List.of("Action", "Superhero", "Adventure", "Fantasy", "Comedy"), "English", Arrays.asList(actors.get(0), actors.get(1), actors.get(2), actors.get(3), actors.get(4), actors.get(5), actors.get(6), actors.get(7), actors.get(8)), directors.get(0),2017, new ArrayList<>()));
        movies.add(new Movie(2, "Spider-Man: Far From Home", List.of("Action", "Superhero", "Adventure", "Fantasy", "Comedy"), "English", Arrays.asList(actors.get(0), actors.get(9), actors.get(4), actors.get(10), actors.get(2), actors.get(11), actors.get(6), actors.get(12), actors.get(13), actors.get(14), actors.get(15)), directors.get(0),2019, new ArrayList<>()));
        movies.add(new Movie(3, "Spider-Man: No Way Home", List.of("Action", "Superhero", "Adventure", "Fantasy", "Comedy"), "English", Arrays.asList(actors.get(0), actors.get(4), actors.get(16), actors.get(6), actors.get(2), actors.get(17), actors.get(18), actors.get(19), actors.get(20), actors.get(13), actors.get(14), actors.get(21), actors.get(22)), directors.get(0),2021, new ArrayList<>()));
        movies.add(new Movie(4, "The Devil Wears Prada", List.of("Comedy", "Drama"), "English", Arrays.asList(actors.get(23), actors.get(24), actors.get(25), actors.get(26), actors.get(27), actors.get(28)), directors.get(1),2006, new ArrayList<>()));
        movies.add(new Movie(5, "Titanic", List.of("Romance", "Drama"), "English", Arrays.asList(actors.get(29), actors.get(30), actors.get(31), actors.get(32), actors.get(33), actors.get(34), actors.get(35), actors.get(36), actors.get(37), actors.get(38)), directors.get(2),1997, new ArrayList<>()));
        movies.add(new Movie(6, "Lucy", List.of("Action", "Thriller", "Sci-Fi"), "English", Arrays.asList(actors.get(39), actors.get(40), actors.get(41), actors.get(42)), directors.get(11),2016, new ArrayList<>()));
        movies.add(new Movie(7, "The 5th Wave", List.of("Action", "Sci-Fi"), "English", Arrays.asList(actors.get(43), actors.get(44), actors.get(45), actors.get(46), actors.get(47), actors.get(48), actors.get(49), actors.get(50)), directors.get(10),2016, new ArrayList<>()));
        movies.add(new Movie(8, "Burlesque ", List.of("Musical", "Romance", "Drama"), "English", Arrays.asList(actors.get(51), actors.get(52), actors.get(53), actors.get(54), actors.get(55), actors.get(56)), directors.get(9),2010, new ArrayList<>()));
        movies.add(new Movie(9, "Jessabelle", List.of("Horror", "Thriller", "Mystery"), "English", Arrays.asList(actors.get(57), actors.get(58), actors.get(59), actors.get(60), actors.get(61)), directors.get(8),2014, new ArrayList<>()));
        movies.add(new Movie(10, "A Perfect Day", List.of("Comedy", "Drama"), "Spanish", Arrays.asList(actors.get(62), actors.get(63), actors.get(64), actors.get(65)), directors.get(7),2015, new ArrayList<>()));
        movies.add(new Movie(11, "Divergent", List.of("Action", "Thriller", "Sci-Fi", "Adventure", "Mystery"), "English", Arrays.asList(actors.get(66), actors.get(67), actors.get(68), actors.get(69), actors.get(70), actors.get(71), actors.get(72)), directors.get(6),2014, new ArrayList<>()));
        movies.add(new Movie(12, "Insurgent", List.of("Action", "Thriller", "Sci-Fi", "Adventure", "Mystery"), "English", Arrays.asList(actors.get(66), actors.get(67), actors.get(73), actors.get(69), actors.get(70), actors.get(71), actors.get(72), actors.get(74)), directors.get(5),2015, new ArrayList<>()));
        movies.add(new Movie(13, "Allegiant", List.of("Action", "Thriller", "Sci-Fi", "Adventure", "Mystery"), "English", Arrays.asList(actors.get(66), actors.get(67), actors.get(75), actors.get(72), actors.get(74), actors.get(71), actors.get(76)), directors.get(5),2016, new ArrayList<>()));
        movies.add(new Movie(14, "Murder Mystery", List.of("Comedy", "Mystery", "Action", "Detective"), "English", Arrays.asList(actors.get(77), actors.get(78), actors.get(79), actors.get(80), actors.get(81), actors.get(82)), directors.get(4),2019, new ArrayList<>()));
        movies.add(new Movie(15, "Murder Mystery 2", List.of("Comedy", "Mystery", "Action", "Detective"), "English", Arrays.asList(actors.get(77), actors.get(78), actors.get(83), actors.get(84), actors.get(85), actors.get(86)), directors.get(3),2023, new ArrayList<>()));
        movies.add(new Movie(16, "Avatar", List.of("Action", "Sci-Fi", "Adventure", "Fantasy", "Mystery"), "English", Arrays.asList(actors.get(88), actors.get(89), actors.get(90), actors.get(91), actors.get(92)), directors.get(2),2009, new ArrayList<>()));
        movies.add(new Movie(17, "Avatar: The Way of Water", List.of("Action", "Sci-Fi", "Adventure", "Fantasy", "Mystery"), "English", Arrays.asList(actors.get(88), actors.get(93), actors.get(92), actors.get(90), actors.get(30)), directors.get(2),2022, new ArrayList<>()));

    }

    {
        seriesL.add(new Series(18, "La casa de papel", Arrays.asList("Thriller","Heist","Detective"), "Spanish", Arrays.asList(actors.get(94), actors.get(95), actors.get(96), actors.get(97), actors.get(98), actors.get(99), actors.get(100), actors.get(101), actors.get(102), actors.get(103), actors.get(104), actors.get(105), actors.get(106)), directors.get(12),2017, 2021, 5, new ArrayList<>()));
        seriesL.add(new Series(19, "Game of Thrones", Arrays.asList("Action","Adventure","Fantasy","Drama", "Tragedy"), "English", Arrays.asList(actors.get(107), actors.get(108), actors.get(109), actors.get(110), actors.get(111), actors.get(112), actors.get(113), actors.get(114), actors.get(115), actors.get(116), actors.get(117)), directors.get(13),2011, 2019, 8, new ArrayList<>()));
        seriesL.add(new Series(20, "The Vampire Diaries", Arrays.asList("Drama","Supernatural","Horror","Fantasy"), "English", Arrays.asList(actors.get(118), actors.get(119), actors.get(120), actors.get(121), actors.get(122), actors.get(123), actors.get(124), actors.get(125), actors.get(126), actors.get(127), actors.get(128), actors.get(129), actors.get(130)), directors.get(14),2009, 2017, 8, new ArrayList<>()));
        seriesL.add(new Series(21, "The Office", Arrays.asList("Mockumentary","Comedy","Sitcom"), "English", Arrays.asList(actors.get(131), actors.get(132), actors.get(133), actors.get(134), actors.get(135), actors.get(136), actors.get(137), actors.get(138), actors.get(139), actors.get(140), actors.get(141), actors.get(142), actors.get(143), actors.get(144)), directors.get(15),2005, 2013, 9, new ArrayList<>()));
        seriesL.add(new Series(22, "American Horror Story", Arrays.asList("Horror","Anthology","Thriller"), "English", Arrays.asList(actors.get(145), actors.get(146), actors.get(147), actors.get(148), actors.get(149), actors.get(150), actors.get(151), actors.get(152), actors.get(153), actors.get(154), actors.get(155), actors.get(156), actors.get(157), actors.get(158)), directors.get(16),2011, 0, 11, new ArrayList<>()));
        seriesL.add(new Series(23, "Wednesday", Arrays.asList("Comedy","Horror","Supernatural"), "English", Arrays.asList(actors.get(159), actors.get(160), actors.get(161), actors.get(162), actors.get(163), actors.get(164), actors.get(165), actors.get(166), actors.get(167), actors.get(168), actors.get(169), actors.get(170)), directors.get(17),2022, 0, 1, new ArrayList<>()));
        seriesL.add(new Series(24, "Elite", Arrays.asList("Thriller", "Drama"), "Spanish", Arrays.asList(actors.get(171), actors.get(172), actors.get(173), actors.get(174), actors.get(175), actors.get(176), actors.get(177), actors.get(178), actors.get(179), actors.get(180), actors.get(181), actors.get(182), actors.get(183), actors.get(184), actors.get(185)), directors.get(18),2018, 0, 6, new ArrayList<>()));
        seriesL.add(new Series(25, "Teen Wolf", Arrays.asList("Action","Romance","Supernatural","Thriller","Teen drama"), "English", Arrays.asList(actors.get(186), actors.get(187), actors.get(188), actors.get(189), actors.get(190), actors.get(191), actors.get(192), actors.get(193), actors.get(194), actors.get(195), actors.get(196), actors.get(197)), directors.get(19),2011, 2017, 6, new ArrayList<>()));

    }

    {
        collections.add(new Collection(26, "Spider-Man", Arrays.asList("Action","Superhero","Adventure","Fantasy","Comedy"), "English", 3, Arrays.asList(movies.get(0), movies.get(1), movies.get(2))));
        collections.add(new Collection(27, "Divergent", Arrays.asList("Action","Sci-Fi","Adventure","Thriller","Mystery"), "English", 3, Arrays.asList(movies.get(10), movies.get(11), movies.get(12))));
        collections.add(new Collection(28, "Murder Mystery", Arrays.asList("Comedy","Mystery","Action","Detective"), "English", 2, Arrays.asList(movies.get(13), movies.get(14))));
        collections.add(new Collection(29, "Avatar", Arrays.asList("Action","Sci-Fi","Adventure","Fantasy","Mystery"), "English", 2, Arrays.asList(movies.get(15), movies.get(16))));
    }


    {
        allItems.add(new Movie(1, "Spider-Man: Homecoming", List.of("Action", "Superhero", "Adventure", "Fantasy", "Comedy"), "English", Arrays.asList(actors.get(0), actors.get(1), actors.get(2), actors.get(3), actors.get(4), actors.get(5), actors.get(6), actors.get(7), actors.get(8)), directors.get(0),2017, new ArrayList<>()));
        allItems.add(new Movie(2, "Spider-Man: Far From Home", List.of("Action", "Superhero", "Adventure", "Fantasy", "Comedy"), "English", Arrays.asList(actors.get(0), actors.get(9), actors.get(4), actors.get(10), actors.get(2), actors.get(11), actors.get(6), actors.get(12), actors.get(13), actors.get(14), actors.get(15)), directors.get(0),2019, new ArrayList<>()));
        allItems.add(new Movie(3, "Spider-Man: No Way Home", List.of("Action", "Superhero", "Adventure", "Fantasy", "Comedy"), "English", Arrays.asList(actors.get(0), actors.get(4), actors.get(16), actors.get(6), actors.get(2), actors.get(17), actors.get(18), actors.get(19), actors.get(20), actors.get(13), actors.get(14), actors.get(21), actors.get(22)), directors.get(0),2021, new ArrayList<>()));
        allItems.add(new Movie(4, "The Devil Wears Prada", List.of("Comedy", "Drama"), "English", Arrays.asList(actors.get(23), actors.get(24), actors.get(25), actors.get(26), actors.get(27), actors.get(28)), directors.get(1),2006, new ArrayList<>()));
        allItems.add(new Movie(5, "Titanic", List.of("Romance", "Drama"), "English", Arrays.asList(actors.get(29), actors.get(30), actors.get(31), actors.get(32), actors.get(33), actors.get(34), actors.get(35), actors.get(36), actors.get(37), actors.get(38)), directors.get(2),1997, new ArrayList<>()));
        allItems.add(new Movie(6, "Lucy", List.of("Action", "Thriller", "Sci-Fi"), "English", Arrays.asList(actors.get(39), actors.get(40), actors.get(41), actors.get(42)), directors.get(11),2016, new ArrayList<>()));
        allItems.add(new Movie(7, "The 5th Wave", List.of("Action", "Sci-Fi"), "English", Arrays.asList(actors.get(43), actors.get(44), actors.get(45), actors.get(46), actors.get(47), actors.get(48), actors.get(49), actors.get(50)), directors.get(10),2016, new ArrayList<>()));
        allItems.add(new Movie(8, "Burlesque ", List.of("Musical", "Romance", "Drama"), "English", Arrays.asList(actors.get(51), actors.get(52), actors.get(53), actors.get(54), actors.get(55), actors.get(56)), directors.get(9),2010, new ArrayList<>()));
        allItems.add(new Movie(9, "Jessabelle", List.of("Horror", "Thriller", "Mystery"), "English", Arrays.asList(actors.get(57), actors.get(58), actors.get(59), actors.get(60), actors.get(61)), directors.get(8),2014, new ArrayList<>()));
        allItems.add(new Movie(10, "A Perfect Day", List.of("Comedy", "Drama"), "Spanish", Arrays.asList(actors.get(62), actors.get(63), actors.get(64), actors.get(65)), directors.get(7),2015, new ArrayList<>()));
        allItems.add(new Movie(11, "Divergent", List.of("Action", "Thriller", "Sci-Fi", "Adventure", "Mystery"), "English", Arrays.asList(actors.get(66), actors.get(67), actors.get(68), actors.get(69), actors.get(70), actors.get(71), actors.get(72)), directors.get(6),2014, new ArrayList<>()));
        allItems.add(new Movie(12, "Insurgent", List.of("Action", "Thriller", "Sci-Fi", "Adventure", "Mystery"), "English", Arrays.asList(actors.get(66), actors.get(67), actors.get(73), actors.get(69), actors.get(70), actors.get(71), actors.get(72), actors.get(74)), directors.get(5),2015, new ArrayList<>()));
        allItems.add(new Movie(13, "Allegiant", List.of("Action", "Thriller", "Sci-Fi", "Adventure", "Mystery"), "English", Arrays.asList(actors.get(66), actors.get(67), actors.get(75), actors.get(72), actors.get(74), actors.get(71), actors.get(76)), directors.get(5),2016, new ArrayList<>()));
        allItems.add(new Movie(14, "Murder Mystery", List.of("Comedy", "Mystery", "Action", "Detective"), "English", Arrays.asList(actors.get(77), actors.get(78), actors.get(79), actors.get(80), actors.get(81), actors.get(82)), directors.get(4),2019, new ArrayList<>()));
        allItems.add(new Movie(15, "Murder Mystery 2", List.of("Comedy", "Mystery", "Action", "Detective"), "English", Arrays.asList(actors.get(77), actors.get(78), actors.get(83), actors.get(84), actors.get(85), actors.get(86)), directors.get(3),2023, new ArrayList<>()));
        allItems.add(new Movie(16, "Avatar", List.of("Action", "Sci-Fi", "Adventure", "Fantasy", "Mystery"), "English", Arrays.asList(actors.get(88), actors.get(89), actors.get(90), actors.get(91), actors.get(92)), directors.get(2),2009, new ArrayList<>()));
        allItems.add(new Movie(17, "Avatar: The Way of Water", List.of("Action", "Sci-Fi", "Adventure", "Fantasy", "Mystery"), "English", Arrays.asList(actors.get(88), actors.get(93), actors.get(92), actors.get(90), actors.get(30)), directors.get(2),2022, new ArrayList<>()));
        allItems.add(new Series(18, "La casa de papel", Arrays.asList("Thriller","Heist","Detective"), "Spanish", Arrays.asList(actors.get(94), actors.get(95), actors.get(96), actors.get(97), actors.get(98), actors.get(99), actors.get(100), actors.get(101), actors.get(102), actors.get(103), actors.get(104), actors.get(105), actors.get(106)), directors.get(12),2017, 2021, 5, new ArrayList<>()));
        allItems.add(new Series(19, "Game of Thrones", Arrays.asList("Action","Adventure","Fantasy","Drama", "Tragedy"), "English", Arrays.asList(actors.get(107), actors.get(108), actors.get(109), actors.get(110), actors.get(111), actors.get(112), actors.get(113), actors.get(114), actors.get(115), actors.get(116), actors.get(117)), directors.get(13),2011, 2019, 8, new ArrayList<>()));
        allItems.add(new Series(20, "The Vampire Diaries", Arrays.asList("Drama","Supernatural","Horror","Fantasy"), "English", Arrays.asList(actors.get(118), actors.get(119), actors.get(120), actors.get(121), actors.get(122), actors.get(123), actors.get(124), actors.get(125), actors.get(126), actors.get(127), actors.get(128), actors.get(129), actors.get(130)), directors.get(14),2009, 2017, 8, new ArrayList<>()));
        allItems.add(new Series(21, "The Office", Arrays.asList("Mockumentary","Comedy","Sitcom"), "English", Arrays.asList(actors.get(131), actors.get(132), actors.get(133), actors.get(134), actors.get(135), actors.get(136), actors.get(137), actors.get(138), actors.get(139), actors.get(140), actors.get(141), actors.get(142), actors.get(143), actors.get(144)), directors.get(15),2005, 2013, 9, new ArrayList<>()));
        allItems.add(new Series(22, "American Horror Story", Arrays.asList("Horror","Anthology","Thriller"), "English", Arrays.asList(actors.get(145), actors.get(146), actors.get(147), actors.get(148), actors.get(149), actors.get(150), actors.get(151), actors.get(152), actors.get(153), actors.get(154), actors.get(155), actors.get(156), actors.get(157), actors.get(158)), directors.get(16),2011, 0, 11, new ArrayList<>()));
        allItems.add(new Series(23, "Wednesday", Arrays.asList("Comedy","Horror","Supernatural"), "English", Arrays.asList(actors.get(159), actors.get(160), actors.get(161), actors.get(162), actors.get(163), actors.get(164), actors.get(165), actors.get(166), actors.get(167), actors.get(168), actors.get(169), actors.get(170)), directors.get(17),2022, 0, 1, new ArrayList<>()));
        allItems.add(new Series(24, "Elite", Arrays.asList("Thriller", "Drama"), "Spanish", Arrays.asList(actors.get(171), actors.get(172), actors.get(173), actors.get(174), actors.get(175), actors.get(176), actors.get(177), actors.get(178), actors.get(179), actors.get(180), actors.get(181), actors.get(182), actors.get(183), actors.get(184), actors.get(185)), directors.get(18),2018, 0, 6, new ArrayList<>()));
        allItems.add(new Series(25, "Teen Wolf", Arrays.asList("Action","Romance","Supernatural","Thriller","Teen drama"), "English", Arrays.asList(actors.get(186), actors.get(187), actors.get(188), actors.get(189), actors.get(190), actors.get(191), actors.get(192), actors.get(193), actors.get(194), actors.get(195), actors.get(196), actors.get(197)), directors.get(19),2011, 2017, 6, new ArrayList<>()));
        allItems.add(new Collection(26, "Spider-Man", Arrays.asList("Action","Superhero","Adventure","Fantasy","Comedy"), "English", 3, Arrays.asList(movies.get(0), movies.get(1), movies.get(2))));
        allItems.add(new Collection(27, "Divergent", Arrays.asList("Action","Sci-Fi","Adventure","Thriller","Mystery"), "English", 3, Arrays.asList(movies.get(10), movies.get(11), movies.get(12))));
        allItems.add(new Collection(28, "Murder Mystery", Arrays.asList("Comedy","Mystery","Action","Detective"), "English", 2, Arrays.asList(movies.get(13), movies.get(14))));
        allItems.add(new Collection(29, "Avatar", Arrays.asList("Action","Sci-Fi","Adventure","Fantasy","Mystery"), "English", 2, Arrays.asList(movies.get(15), movies.get(16))));


    }

    {

    }


}
