package services;

import classes.title.Review;
import classes.title.Title;
import classes.user.Preference;
import classes.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.sun.tools.javac.Main;
import repositories.UserRepository;
import services.*;

public class UserService {
    public UserService() {
    }

    static MainService mainService = MainService.getInstance();
    AuditService auditService = AuditService.getInstance();

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
        Preference preference = new Preference();
        User newUser = new User(username, firstname, lastname, email, password, preference);

        mainService.getUsers().add(newUser);
        UserRepository.insertUser(username, firstname, lastname, email, password);

        System.out.println("User created successfully!");
        System.out.println(newUser.toString());

        mainService.setLoggedInUser(newUser);

        try{
            auditService.logAction("Create Account");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Create Account!");
        }

        return newUser;
    }

    private String getUsernameInput(Scanner sc) {
        String username = "";
        while(true){
            System.out.println("Enter username: ");
            username = sc.nextLine();
            int ok=1;
            for(User user: mainService.getUsers()){
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
            if (!password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,}")) {
                System.out.println("Password must be at least 8 characters long and contain a mix of upper and lower case letters, numbers, and special characters!");
            } else {
                break;
            }
        }
        return password;
    }



    public int signIn(Scanner sc){
        int nr = 3;//3 incercari
        int rez=0;
        sc.nextLine();
        User user = null;
        String username = "";
        int k=0;
        while(nr!=0){
            System.out.println("Enter username: ");
            username = sc.nextLine();

            for(User u:mainService.getUsers()){
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
                int nr2 = 3;
                while(nr2!=0){
                    System.out.println("Enter password: ");
                    String password = sc.nextLine();
                    if(!Objects.equals(user.getPassword(), password)){
                        System.out.println("Incorect password!");
                        nr2--;
                    }
                    else{
                        k=1;
                        break;
                    }
                }
                break;
            }
        }
        if(user==null || k==0){
            System.out.println("Please create account before signing in!");
        }
        else{
            user = UserRepository.getUserByUsername(username);
            System.out.println("Signed in successfully!");
            rez=1;
            mainService.setLoggedInUser(user);
        }

        try{
            auditService.logAction("Sign In");
        }catch (IOException e){
            System.out.println("Eroare scriere in csv Sign in!");
        }
        return rez;
    }
}
