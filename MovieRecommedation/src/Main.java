import services.MainService;
import services.MenuService;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var menuService = MenuService.getInstance();

        menuService.UserStart();


    }
}