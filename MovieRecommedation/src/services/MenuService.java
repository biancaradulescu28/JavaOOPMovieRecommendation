package services;

import java.util.Objects;
import java.util.Scanner;

import classes.title.Title;
import classes.user.User;
import services.*;

public class MenuService {

    private MenuService(){}
    private static class SingletonHolder{
        private static final MenuService INSTANCE = new MenuService();
    }

    public static MenuService getInstance(){
        return MenuService.SingletonHolder.INSTANCE;
    }

    UserService userService = new UserService();
    EditService editService = new EditService();

    static MainService mainService = MainService.getInstance();

    public void UserStart(){
        var sc = new Scanner(System.in);
        mainService.createDBTables();
        mainService.initializeDataFromDB();


        while(true){
            System.out.println("\n");
            System.out.println("Choose one of the following options.");
            System.out.println("1. Create account");
            System.out.println("2. Sign in");

            int start = sc.nextInt();
            switch (start){
                case 1 -> {
                    userService.createUser(sc);
                    Menu(1, sc);

                }
                case 2 -> {
                    int ok=userService.signIn(sc);
                    Menu(ok, sc);
                }
            }
        }
    }

    public void Menu(int ok, Scanner sc){
        if(ok==1){
            while (true) {
                System.out.println("\n");
                System.out.println("MENU");
                System.out.println("Choose one of the following options.");
                System.out.println("1. See Profile.");
                System.out.println("2. Insert preference and get recommendation.");
                System.out.println("3. See History.");
                System.out.println("4. See Favorites.");
                System.out.println("5. See all movies available.");
                System.out.println("6. See all collections available.");
                System.out.println("7. See all series available.");
                System.out.println("8. Show title details.");
                System.out.println("9. Add review to title.");
                System.out.println("10. Edit data.");
                System.out.println("11. Exit.");

                int option = sc.nextInt();
                switch (option) {
                    case 1 -> System.out.println(mainService.getLoggedInUser());
                    case 2 -> {
                        int q = 0;
                        int c = 0;
                        while(q == 0){
                            mainService.insertPreference(mainService.getLoggedInUser(), sc);
                            int nr = mainService.getRecommandations(mainService.getLoggedInUser());
                            if(nr == 1){
                                c = mainService.chooseItemFromRecommendations(sc, mainService.getLoggedInUser(), mainService.getrecommendations());
                                q=1;
                            }
                        }
                        if(c==1){
                            ok = 0;
                            while (ok == 0) {
                                System.out.println("\n");
                                System.out.println("What do you want to do with this title?");
                                System.out.println("Choose one of the following options.");
                                System.out.println("1. See title reviews.");
                                System.out.println("2. Watch now.");
                                System.out.println("3. Go back to menu.");

                                int w = sc.nextInt();
                                switch (w) {
                                    case 1 -> mainService.listAllItemReviews(mainService.getSelected());
                                    case 2 -> mainService.watchNow(mainService.getLoggedInUser(), mainService.getSelected(), sc);
                                    case 3 -> {
                                        ok = 1;
                                    }
                                }
                            }
                        }

                    }
                    case 3 -> mainService.listHistory(mainService.getLoggedInUser());
                    case 4 -> mainService.listFavorites(mainService.getLoggedInUser(), sc);
                    case 5 -> mainService.listAllMovies();
                    case 6 -> mainService.listAllCollections();
                    case 7 -> mainService.listAllSeries();
                    case 8 -> mainService.showTitle(sc);
                    case 9 -> {
                        System.out.println("For which title do you want to add a review?");
                        sc.nextLine();
                        String title = sc.nextLine();
                        ok = 0;
                        for (Title item : mainService.getTitles()) {
                            if (Objects.equals(title, item.getTitle())) {
                                mainService.addReviewToItem(sc, mainService.getLoggedInUser(), item);
                                ok = 1;
                            }
                        }
                        if (ok == 0) {
                            System.out.println("Title doesn't exit!");
                        }
                    }
                    case 10 -> {
                        ok = 0;
                        while (ok == 0) {
                            System.out.println("\n");
                            System.out.println("EDIT");
                            System.out.println("Choose one of the following options.");
                            System.out.println("1. Add cast member");
                            System.out.println("2. Add title");
                            System.out.println("3. Remove title.");
                            System.out.println("4. Add movie in collection");
                            System.out.println("5. Go back to menu.");


                            int e = sc.nextInt();
                            switch (e) {
                                case 1 -> editService.createCastMember(sc);
                                case 2 -> editService.createItem(sc);
                                case 3 -> editService.removeItem(sc);
                                case 4 -> editService.addMovieInCollection(sc);
                                case 5 -> {
                                    ok = 1;
                                }
                            }
                        }
                    }
                    case 11 -> System.exit(0);
                }
            }
        }

    }

}


