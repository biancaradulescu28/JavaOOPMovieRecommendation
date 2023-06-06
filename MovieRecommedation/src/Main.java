import services.MainService;
import services.MenuService;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //TODO: Not case sensitive
        //TODO: Lists of available genres
        //TODO: interface cu butoane
        //TODO: exceptii
        //TODO: CSV audit

        var menuService = MenuService.getInstance();

        menuService.UserStart();


    }
}