/*
 * Author: Peter Lee
 * ID: 18040190
 */

package AIKEN;

import AIKEN.Game.GameState;

// This class represents the pet in the game.
// A pet is a Thread that continously runs throughout the game and changes its attributes based on its current status.
// A pet has 5 attributes: isAlive, hunger, happiness, health, and a name.
// The hunger, happiness and health values determine the pet's state.
// Once a pet's health value reaches 0, it dies.
public class Pet extends Thread {
    private static final int MAX_VALUE = 10;
    boolean isAlive;
    private int hunger, happiness, health;
    private GameState state;
    private String name;

    // Default parameter used when starting a new game
    public Pet(String name) {
        hunger = 5;
        happiness = 5;
        health = 5;
        isAlive = true;
        this.state = GameState.MAIN;
        this.name = name;
    }

    // Second parameter used when loading a saved game.
    public Pet(String name, int hunger, int happiness, int health) {
        this.state = GameState.MAIN;
        this.name = name;
        this.hunger = hunger;
        this.happiness = happiness;
        this.health = health;
        isAlive = true;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    // While the pet is alive, every 10 seconds:
    // If health is below 3, reduce happiness by 2, and if health is below 6 reduce happiness by 1,
    // If hunger or happiness is below 3, reduce health by 2, and if hunger or happiness is below 6, reduce happiness by 1,
    // else increase health by 1 if it below 10,
    // reduce hunger by 1,
    // If health is 0 or below or game state is TERMINATED, set isAlive to false,
    // If game state is not MAIN, then wait.
    @Override
    public void run() {
        try {
            while (isAlive) {
                if (health <= 0)
                    isAlive = false;
                
                Thread.sleep(10 * 1000);
                
                if (state == GameState.TERMINATED) {
                    break;
                }

                if (state != GameState.MAIN) {
                    synchronized (this) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                if (health < 3) {
                    happiness -= 2;
                } else if (health < 6) {
                    happiness--;
                }

                if (hunger < 3 || happiness < 3) {
                    health -= 2;
                } else if (hunger < 6 || happiness < 6) {
                    health--;
                } else if (health < 10) {
                    health++;
                }

                hunger--;
                if(health < 0) {
                    health = 0;
                }
                
                if(hunger < 0) {
                    hunger = 0;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void feed(Food food) {
        hunger += food.getRestoreLevel();
        if(hunger >= 10) {
            hunger = 10;
        }
    }

    public void play(Toy toy) {
        happiness += toy.getFunLevel();
        hunger -= toy.getTiringLevel();
        if(happiness >= 10)
            happiness = 10;
        
        if(hunger < 0) {
            hunger = 0;
        }

    }

    // The toString() method for the Pet class returns its state represented visually.
    // The pet's state is based on its lowest value, e.g. if the lowest value is hunger and the value is 4, then return "( o ~ o )" which is the state for slightly hungry.
    @Override
    public String toString() {
        int min = 10;
        if (min > hunger)
            min = hunger;
        if (min > happiness)
            min = happiness;
        if (min > health)
            min = health;

        if (min <= 3) {
            if (hunger == min) {
                return "( > 3 < )";
            } else if (happiness == min) {
                return "( ; ^ ; )";
            } else {
                return "( x o x )";
            }
        } else if (min >= 4 && min <= 6) {
            if (hunger == min) {
                return "( o ~ o )";
            } else if (happiness == min) {
                return "( - ^ - )";
            } else {
                return "( o - o )";
            }
        } else {
            if (hunger == min) {
                return "( ^ 3 ^ )";
            } else if (happiness == min) {
                return "( ^ 0 ^ )";
            } else {
                return "( ^ - ^ )";
            }
        }
    }

    public String getPetName() {
        return this.name;
    }

    public int getHunger() {
        return this.hunger;
    }

    public int getHappiness() {
        return this.happiness;
    }

    public int getHealth() {
        return this.health;
    }
}
