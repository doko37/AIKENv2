/*
 * Author: Peter Lee
 * ID: 18040190
 */

package AIKEN;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

import AIKEN.Game.GameState;

// This class is a Thread that continuously prints the game to the terminal.
// What it prints depends on the state of the game.
class PrintRunnable implements Runnable {
    final private Game game;
    final private User user;

    public PrintRunnable(Game game, User user) {
        this.game = game;
        this.user = user;
    }

    private static void clearTerminal() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    @Override
    public void run() {
        try {
            while (game.state != GameState.TERMINATED) {
                clearTerminal();

                // If the user's pet dies, alert the user.
                if (!user.getPet().isAlive()) {
                    synchronized (this) {
                        try {
                            System.out.println("Your pet has died!");
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                // Prints the shop and prompts the user to enter the name of the item they would
                // like to purchase.
                if (game.state == GameState.SHOP) {
                    synchronized (this) {
                        try {
                            System.out.println(
                                    "\n\n======SHOP======\n\n" + game.shop + "\n" + "Money: " + user.getMoney());
                            System.out.println(
                                    "Please enter the name of the item you would like to purchase (or press \"x\" to exit the shop.) ");
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                // Prints the user's inventory. If the user's inventory is empty notify the
                // user.
                if (game.state == GameState.INVENTORY) {
                    synchronized (this) {
                        try {
//                            System.out.println("\n\n======INVENTORY======\n\n" + user.getInventory(game.shop));
//                            if (user.getInventory(game.shop).isEmpty())
                                System.out.println("Your Inventory is empty!");
                            System.out.println("Press enter to exit your inventory.");
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                // Feeding and Playing State: prints the user's inventory and prompts them to
                // choose what item they would like to use.
                // The items that are printed correspond to the game state, e.g. if the game
                // state is "feeding", then the program will only print Food items.
                if (game.state == GameState.FEEDING) {
                    synchronized (this) {
                        try {
                            System.out.println("\n\n======INVENTORY======\n\n" + user.getFoods(game.shop));
                            if (user.getFoods(game.shop).isEmpty())
                                System.out.println("Your Inventory is empty!");
                            System.out.println(
                                    "Please enter the item you would like to feed your pet (or press \"x\" to exit your inventory.)");
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                if (game.state == GameState.PLAYING) {
                    synchronized (this) {
                        try {
                            System.out.println("\n\n======INVENTORY======\n\n" + user.getToys(game.shop));
                            if (user.getToys(game.shop).isEmpty())
                                System.out.println("Your Inventory is empty!");
                            System.out.println(
                                    "Please enter the item you would like your pet to play with (or press \"x\" to exit your inventory.)");
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                // The Casino: prompts the user to enter the amount they would like to bet.
                if (game.state == GameState.CASINO) {
                    synchronized (this) {
                        try {
                            System.out.println("=====CASINO=====\n\nMoney: " + user.getMoney());
                            System.out.print("\nEnter your bet (or press \"x\" to exit): ");
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                // The main menu: Prints the name and status of the user's pet.
                // Unlike the other game states, this state will not wait for user input to
                // continue.
                if (game.state == GameState.MAIN) {
                    System.out.println(user.getPet().getPetName().toUpperCase() + ":\n");

                    System.out.print("Hunger: ");
                    String str = "";
                    for (int i = 0; i < user.getPet().getHunger(); i++) {
                        str += "[]";
                    }
                    System.out.print(str + "\n");
                    System.out.print("Happiness: ");
                    str = "";
                    for (int i = 0; i < user.getPet().getHappiness(); i++) {
                        str += "[]";
                    }
                    System.out.print(str + "\n");
                    System.out.print("Health: ");
                    str = "";
                    for (int i = 0; i < user.getPet().getHealth(); i++) {
                        str += "[]";
                    }
                    System.out.print(str + "\n\n");

                    System.out.println(user.getPet() + "\n");
                    
                    if(user.getPet().getHealth() == 10) {
                        user.setMoney(user.getMoney() + 1);
                        System.out.println(user.getPet().getPetName() + " is full health! \n+ 2 money every second!");
                    }
                    
                    System.out.println("Money: " + user.getMoney());

                    System.out.println(
                            "Please choose: Feed " + user.getPet().getPetName() + " (1) | Play with "
                                    + user.getPet().getPetName()
                                    + " (2) | Inventory (3) | Shop (4) | Casino (5) | Quit (6)");
                    System.out.print("What would you like to do? ");
                    
                    Thread.sleep(500);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

// This class is the main class for the game.
// Contains all the game information such as User, GameState, Shop, etc.
// This class is also a Thread that continuously scans user input.
public class Game implements Runnable {
    enum GameState {
        MAIN,
        SHOP,
        INVENTORY,
        FEEDING,
        PLAYING,
        TERMINATED,
        CASINO
    }

    Shop shop;
    final Scanner scanner;
    GameState state;
    final PrintRunnable pr;
    User user;
    Casino casino;

    public Game(User user, Scanner scanner) {
        this.state = GameState.MAIN;
        this.shop = new Shop();
        this.scanner = new Scanner(System.in);
        this.user = user;
        this.casino = new Casino();
        this.pr = new PrintRunnable(this, this.user);
    }

    // Start all of the threads, and stock the shop with items.
    public void startGame() {
        user.getPet().start();
        Thread print = new Thread(pr);
        print.start();
        shop.stock();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(
                "Welcome to AIKEN: A virtual pet game!\n================================================================");

        Thread.sleep(1000);

        User user = null;
        Game game = null;

        Scanner scanner = new Scanner(System.in);

        // This block of code loads the savefile and creates a new game with the
        // savefile's information.
        // If the savefile is not formatted correctly, or does not exist, the user will
        // be prompted to enter the name of their pet and a new game begins with default
        // settings.
        try {
            File file = new File("./savefile.txt");
            Scanner fileScanner = new Scanner(file);
            boolean first = true;
            String[] petData = null;
            HashMap<String, Integer> inventoryData = new HashMap<>();
            if (fileScanner.hasNext()) {
                while (fileScanner.hasNext()) {
                    if (first) {
                        petData = fileScanner.nextLine().split(" ");
                        if (petData.length != 5)
                            throw new IndexOutOfBoundsException();
                        first = false;
                    } else {
                        String[] itemData = fileScanner.nextLine().split(" ");
                        inventoryData.put(itemData[0], Integer.valueOf(itemData[1]));
                    }
                }

                System.out.println("Loading existing savefile...");
                Thread.sleep(1000);
                System.out.println("Welcome back! " + petData[0] + " missed you.");
                Thread.sleep(1000);
                user = new User(new Pet(petData[0], Integer.valueOf(petData[1]), Integer.valueOf(petData[2]),
                        Integer.valueOf(petData[3])),
                        Integer.valueOf(petData[4]), inventoryData);

                game = new Game(user, scanner);
            } else
                throw new IndexOutOfBoundsException();
        } catch (Exception e) {
            System.out.println("Save file does not exist or is not formatted properly, creating a new game...");
            Thread.sleep(1000);
            System.out.print("Please enter the name of your pet: ");
            String petName = scanner.nextLine();
            game = new Game(new User(new Pet(petName)), scanner);
        }

        game.startGame();
        Thread read = new Thread(game);
        read.start();

        // While the pet is alive AND the game state is not TERMINATED, wait.
        while (game.user.getPet().isAlive && game.state != GameState.TERMINATED) {
            Thread.sleep(500);
        }

        // Once the pet is dead or the game is terminated prompt the user if they would
        // like to save their game.
        // If they answer "Y" (or "y"), then create a new file named "savefile.txt" in
        // the current directory (if the file already exists, then it will overwritten).
        // To this new file, write the game information. The savefile is formatted as
        // such:
        // The first line contains the information of the pet, each piece of information
        // of seperated by a single space (' ').
        // The pet's information is written in the following order:
        // 1. The name of the pet,
        // 2. Hunger level,
        // 3. Happiness level,
        // 4. Health level,
        // 5. User's money amount.

        // After the first line, every other line is an item in the user's inventory.
        // Each item is formatted as such:
        // 1. The name of the item in lowercase,
        // 2. The amount of that item the user owned.
        // e.g. "ball 2"
        // If the user's inventory is empty, then nothing is written.
        read.interrupt();
        if(!game.user.getPet().isAlive) {
            System.out.println("\n" + game.user.getPet().getPetName() + " has died!");
            game.state = GameState.TERMINATED;
            FileWriter fr = new FileWriter(new File("./savefile.txt"));
            fr.write("");
            fr.close();
        } else {
            System.out.println("Would you like to save your game (Y/N)?");
            if (scanner.nextLine().trim().toLowerCase().equals("y")) {
                System.out.println("Saving current game to file...");
                Thread.sleep(1000);

                FileWriter fr = new FileWriter(new File("./savefile.txt"));
                fr.write(game.user.toString());
                fr.close();
            }
        }

        System.out.println("Goodbye");
        System.exit(0);
    }

    // While this thread is running, continously scan the user's input and make
    // actions based on the game state and the user's input.
    @Override
    public void run() {
        while (state != GameState.TERMINATED && user.getPet().isAlive) {
            String input = scanner.nextLine();

            // If the game state is MAIN, then the user can input an integer from 1 - 6.
            // Each number will change the game's state to the state that corresponds to
            // that number.
            // e.g. Press 3 to change to inventory state
            // Any other input besides 1 - 6 will prompt the user that the input was
            // invalid.
            if (state == GameState.MAIN) {
                try {
                    switch (Integer.valueOf(input.trim())) {
                        case 1:
                            state = GameState.FEEDING;
                            user.getPet().setState(state);
                            break;
                        case 2:
                            state = GameState.PLAYING;
                            user.getPet().setState(state);
                            break;
                        case 3:
                            state = GameState.INVENTORY;
                            user.getPet().setState(state);
                            break;
                        case 4:
                            state = GameState.SHOP;
                            user.getPet().setState(state);
                            break;
                        case 5:
                            state = GameState.CASINO;
                            user.getPet().setState(state);
                            break;
                        case 6:
                            state = GameState.TERMINATED;
                            user.getPet().setState(state);
                            break;
                        default:
                            System.out.println("Please enter a value between 1 - 6.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }

                // The Shop: the user will input the name of the item they would like to
                // purchase.
                // The input needs to be exactly the same as the name of the item (not
                // case-sensitive).
                // If the item exists in the shop, then check if the user can afford the item.
                // If they can afford the item, alert the user that they have purchased the
                // item,
                // and set the game state back to main. Notify the threads that have been
                // waiting.
            } else if (state == GameState.SHOP) {
                if (!input.trim().toLowerCase().equals("x")) {
                    if (shop.get(input.trim()) == null) {
                        System.out.println("Item could not be found, please try again.");
                    } else {
                        if (!user.buyItem(input, shop)) {
                            System.out.println("You cannot afford this item.");
                        } else {
                            System.out.println("Purchased item: " + shop.get(input).getName());
                            System.out.print("Would you like to purchase another item (Y/N)? ");
                            if(!scanner.nextLine().trim().toLowerCase().equals("y")) {
                                state = GameState.MAIN;
                                user.getPet().setState(state);
                                synchronized (pr) {
                                    pr.notify();
                                }

                                synchronized (user.getPet()) {
                                    user.getPet().notify();
                                }
                            } else {
                                System.out.println("Money: " + user.getMoney() + "\nPlease enter the name of the item you would like to purchase (or press \"x\" to exit the shop.) ");
                            }
                        }
                    }
                } else {
                    // If the user inputs "x" (or "X"), then set the game state to MAIN and notify
                    // the waiting threads.
                    state = GameState.MAIN;
                    user.getPet().setState(state);
                    synchronized (pr) {
                        pr.notify();
                    }

                    synchronized (user.getPet()) {
                        user.getPet().notify();
                    }
                }

                // If the game state is inventory, then wait for any user input and set the game
                // state back to main and notify all waiting threads.
            } else if (state == GameState.INVENTORY) {
                state = GameState.MAIN;
                user.getPet().setState(state);
                synchronized (pr) {
                    pr.notify();
                }

                synchronized (user.getPet()) {
                    user.getPet().notify();
                }

                // Feeding and Playing state: The user will be prompted to enter the name of the
                // item they would like to use from their inventory.
                // If the item exists, and the item is the correct type (e.g. item needs to be
                // of type Food if they are Feeding) and the item exists in their inventory then
                // use that item on the pet.
            } else if (state == GameState.FEEDING) {
                if (!input.trim().toLowerCase().equals("x")) {
                    if (shop.get(input.trim()) == null || !(shop.get(input.trim()) instanceof Food)
                            || !(user.ownsItem(input.trim().toLowerCase()))) {
                        System.out.println("Item could not be found, please try again.");
                    } else {
                        user.feed(input.trim().toLowerCase(), shop);
                        state = GameState.MAIN;
                        user.getPet().setState(state);
                        synchronized (pr) {
                            pr.notify();
                        }

                        synchronized (user.getPet()) {
                            user.getPet().notify();
                        }
                    }
                } else {
                    state = GameState.MAIN;
                    user.getPet().setState(state);
                    synchronized (pr) {
                        pr.notify();
                    }

                    synchronized (user.getPet()) {
                        user.getPet().notify();
                    }
                }
            } else if (state == GameState.PLAYING) {
                if (!input.trim().toLowerCase().equals("x")) {
                    if (shop.get(input.trim()) == null || !(shop.get(input.trim()) instanceof Toy)
                            || !(user.ownsItem(input.trim().toLowerCase()))) {
                        System.out.println("Item could not be found, please try again.");
                    } else {
                        user.play(input.trim().toLowerCase(), shop);
                        state = GameState.MAIN;
                        user.getPet().setState(state);
                        synchronized (pr) {
                            pr.notify();
                        }

                        synchronized (user.getPet()) {
                            user.getPet().notify();
                        }
                    }
                } else {
                    state = GameState.MAIN;
                    user.getPet().setState(state);
                    synchronized (pr) {
                        pr.notify();
                    }

                    synchronized (user.getPet()) {
                        user.getPet().notify();
                    }
                }

                // The Casino: The user enters an amount they would like to bet, then guesses
                // heads or tails.
                // The program will flip a coin and if the user guesses correctly, they win the
                // amount they bet, and vise versa if they guess incorrectly.
            } else if (state == GameState.CASINO) {
                try {
                    int bet = Integer.valueOf(input.trim());
                    while (bet > user.getMoney()) {
                        System.out.print("You cannot afford that!\nPlease enter a lower bet: ");
                        bet = Integer.valueOf(scanner.nextLine().trim());
                    }
                    System.out.println("The game master asks: Heads or Tails?");
                    System.out.print("Enter \"head\" for heads and \'tail\" for tails: ");
                    boolean result = false;
                    String guess = "";
                    while (true) {
                        guess = scanner.nextLine().trim().toLowerCase();
                        if (guess.equals("head") || guess.equals("tail")) {
                            result = casino.flip();
                            if (result == guess.equals("head")) {
                                user.setMoney(user.getMoney() + bet);
                                break;
                            } else {
                                user.setMoney(user.getMoney() - bet);
                                break;
                            }
                        } else {
                            System.out.println("Invalid answer. Please enter either \"head\" or \"tail\".");
                        }
                    }

                    System.out.println("You entered: " + guess);
                    System.out.println("Flipping coin in");
                    Thread.sleep(500);
                    System.out.println("3...");
                    Thread.sleep(500);
                    System.out.println("2...");
                    Thread.sleep(500);
                    System.out.println("1...");
                    Thread.sleep(500);
                    System.out.println("The coin landed " + (result ? "heads" : "tails") + "!");
                    System.out.println("Your new balance is: " + user.getMoney());

                    System.out.println("Would you like to make another bet (Y/N)? ");
                    if (!scanner.nextLine().trim().toLowerCase().equals("y")) {
                        state = GameState.MAIN;
                        user.getPet().setState(state);

                        synchronized (pr) {
                            pr.notify();
                        }

                        synchronized (user.getPet()) {
                            user.getPet().notify();
                        }
                    } else {
                        System.out.print("Enter your bet (or press \"x\" to exit): ");
                    }

                } catch (NumberFormatException e) {
                    if (input.trim().toLowerCase().equals("x")) {
                        state = GameState.MAIN;
                        user.getPet().setState(state);

                        synchronized (pr) {
                            pr.notify();
                        }

                        synchronized (user.getPet()) {
                            user.getPet().notify();
                        }
                    }
                    System.out.println("Please enter a number.");
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
