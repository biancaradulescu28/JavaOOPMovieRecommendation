import Movie.Item;
import Movie.Movie;
import Movie.Collection;
import Movie.Series;
import User.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var mainService = MainService.getInstance();
        var sc = new Scanner(System.in);


        while(true){
            System.out.println("\n");
            System.out.println("Choose one of the following options.");
            System.out.println("1. Create account");
            System.out.println("2. Sign in");//TODO:Sign out

            int start = sc.nextInt();
            switch (start){
                case 1 -> mainService.createUser(sc);
                case 2 -> {
                    int ok=mainService.signIn(sc);
                    if(ok==1){
                        while (true) {
                            System.out.println("\n");
                            System.out.println("MENU");
                            System.out.println("Choose one of the following options.");
                            System.out.println("1. See Profile.");
                            System.out.println("2. Insert preference and get recommendation.");
                            System.out.println("3. See History.");
                            System.out.println("4. See WatchLater.");
                            System.out.println("5. See Favorites.");
                            System.out.println("6. See all titles available.");
                            System.out.println("7. See all movies available.");
                            System.out.println("8. See all collections available.");
                            System.out.println("9. See all series available.");
                            System.out.println("10. Add review to title.");
                            System.out.println("11. Show title details.");
                            System.out.println("12. Edit data.");
                            System.out.println("13. Exit.");

                            int option = sc.nextInt();
                            switch (option) {
                                case 1 -> System.out.println(mainService.getLoggedInUser());
                                case 2 -> {
                                    mainService.insertPreference(mainService.getLoggedInUser(), sc);
                                    mainService.getRecommandations(mainService.getLoggedInUser());
                                    mainService.chooseItemFromRecommendations(sc, mainService.getLoggedInUser(), mainService.getrecommendations());
                                    ok = 0;
                                    while (ok == 0) {
                                        System.out.println("\n");
                                        System.out.println("What do you want to do with this title?");
                                        System.out.println("Choose one of the following options.");
                                        System.out.println("1. See title reviews");
                                        System.out.println("2. Watch now.");
                                        System.out.println("3. Watch later.");
                                        System.out.println("4. Go back to menu.");

                                        int w = sc.nextInt();
                                        switch (w) {
                                            case 1 -> mainService.listAllItemReviews(mainService.getSelected());
                                            case 2 -> mainService.watchNow(mainService.getLoggedInUser(), mainService.getSelected(), sc);
                                            case 3 -> mainService.watchLater(mainService.getLoggedInUser(), mainService.getSelected());
                                            case 4 -> {
                                                ok = 1;
                                            }
                                        }
                                    }
                                }
                                case 3 -> mainService.listHistory(mainService.getLoggedInUser());
                                case 4 -> mainService.listWatchLater(mainService.getLoggedInUser(), sc);
                                case 5 -> mainService.listFavorites(mainService.getLoggedInUser(), sc);
                                case 6 -> mainService.listAllItems();
                                case 7 -> mainService.listAllMovies();
                                case 8 -> mainService.listAllCollections();
                                case 9 -> mainService.listAllSeries();
                                case 10 -> {
                                    System.out.println("For which title do you want to add a review?");
                                    sc.nextLine();
                                    String title = sc.nextLine();
                                    ok = 0;
                                    for (Item item : mainService.getAllItems()) {
                                        if (Objects.equals(title, item.getTitle())) {
                                            mainService.addReviewToItem(sc, mainService.getLoggedInUser(), item);
                                            ok = 1;
                                        }
                                    }
                                    if (ok == 0) {
                                        System.out.println("Title doesn't exit!");
                                    }
                                }
                                case 11 -> mainService.showTitle(sc);
                                case 12 -> {
                                    ok = 0;
                                    while (ok == 0) {
                                        System.out.println("\n");
                                        System.out.println("EDIT");
                                        System.out.println("Choose one of the following options.");
                                        System.out.println("1. Add new cast member");
                                        System.out.println("2. Add new title");
                                        System.out.println("3. Add movie in movie collection.");
                                        System.out.println("4. Go back to menu.");
//                        System.out.println("5. Print Actors");
//                        System.out.println("6. Print Directors");
//                        System.out.println("7. Create Actors");
//                        System.out.println("8. Create Directors");
//                        System.out.println("9. Print Movies.");
//                        System.out.println("10. Print Series.");
//                        System.out.println("11. Print Collections.");


                                        int e = sc.nextInt();
                                        switch (e) {
                                            case 1 -> mainService.createCastMember(sc);
                                            case 2 -> mainService.createItem(sc);
                                            case 3 -> mainService.addMovieInCollection(sc);
                                            case 4 -> {
                                                ok = 1;
                                            }
//                            case 5 -> mainService.printActors();
//                            case 6 -> mainService.printDirectors();
//                            case 7 -> mainService.createActorS(sc);
//                            case 8 -> mainService.createDirectorS(sc);
//                            case 9 -> mainService.printMovies();
//                            case 10 -> mainService.printSeriesL();
//                            case 11 -> mainService.printCollections();
                                        }
                                    }
                                }
                                case 13 -> System.exit(0);
                            }
                        }
                    }

                }


            }
        }


    }
}