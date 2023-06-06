/*
 * Author: Peter Lee
 * ID: 18040190
 */

package AIKEN;

import AIKEN.Data.GameState;

// This class represents the pet in the game.
// A pet is a Thread that continously runs throughout the game and changes its attributes based on its current status.
// A pet has 5 attributes: isAlive, hunger, happiness, health, and a name.
// The hunger, happiness and health values determine the pet's state.
// Once a pet's health value reaches 0, it dies.
public class Pet extends Thread {
    private static final int MAX_VALUE = 100;
    boolean isAlive;
    private int hunger, happiness, health;
    public GameState state;
    private String name;
    Model model;

    // Default parameter used when starting a new game
    public Pet(String name) {
        hunger = 50;
        happiness = 50;
        health = 50;
        isAlive = true;
        this.state = GameState.MAIN_MENU;
        this.name = name;
    }

    // Second parameter used when loading a saved game.
    public Pet(String name, int hunger, int happiness, int health) {
        this.state = GameState.MAIN_MENU;
        this.name = name;
        this.hunger = hunger;
        this.happiness = happiness;
        this.health = health;
        isAlive = true;
    }

    public void setState(GameState state) {
        this.state = state;
    }
    
    public void takeDamage(int health) {
        this.health -= health;
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
                
                if (state == GameState.QUIT) {
                    break;
                }

                if (state != GameState.MAIN_MENU) {
                    synchronized (this) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                
                Thread.sleep(10 * 1000);

                if (health < 30) {
                    happiness -= 6;
                } else if (health < 6) {
                    happiness -= 3;
                }

                if (hunger < 30 || happiness < 30) {
                    health -= 6;
                } else if (hunger < 60 || happiness < 60) {
                    health -= 3;
                } else if (health < 100) {
                    health += 10;
                    if(health > 100) 
                        health = 100;
                }

                hunger -= 5;
                if(health < 0) {
                    health = 0;
                }
                
                if(hunger < 0) {
                    hunger = 0;
                }
                
                model.updatePetStatus();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void feed(Food food) {
        hunger += food.getRestoreLevel();
        if(hunger > 100) {
            hunger = 100;
        }
    }

    public void play(Toy toy) {
        happiness += toy.getFunLevel();
        hunger -= toy.getTiringLevel();
        if(happiness > 100)
            happiness = 100;
        
        if(hunger < 0) {
            hunger = 0;
        }

    }

    // The toString() method for the Pet class returns its state represented visually.
    // The pet's state is based on its lowest value, e.g. if the lowest value is hunger and the value is 4, then return "( o ~ o )" which is the state for slightly hungry.
    @Override
    public String toString() {
        int min = 100;
        if (min > hunger)
            min = hunger;
        if (min > happiness)
            min = happiness;
        if (min > health)
            min = health;

        if (min <= 30) {
            if (hunger == min) {
                return "( > 3 < )";
            } else if (happiness == min) {
                return "( ; ^ ; )";
            } else {
                return "( x o x )";
            }
        } else if (min >= 40 && min <= 60) {
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
