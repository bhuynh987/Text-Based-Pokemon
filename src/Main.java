import java.util.Random;
import java.awt.Point;
import java.util.Scanner;
/**
 * The main method to run the game.
 * @param args The command line argument
 * @throws java.io.FileNotFoundException when map is not able to be read
 */
class Main {
  public static void main(String[] args) {
    int level = 1;
    int determineMap = 1;
    Pokemon pokemon = null;
    Map map = Map.getInstance();
    boolean valid = false;
    PokemonGenerator pokemonGenerator = PokemonGenerator.getInstance();
    map.loadMap( determineMap );

    System.out.println("Prof. Oak: Hello there! Welcome to the world of Pokemon!\nThis world is inhabited by creatures called Pokemon! For some people, Pokemon ar... Huh?\nThis isn't the actual game? So I don't need to go through this long intro?\nOh in that case, what is your name?");
    Scanner in = new Scanner(System.in);
    String trainerName = in.nextLine();
    System.out.println("\nYour name is " + trainerName + "? A splendid name!\nWelcome to your Pokemon adventure!");
    System.out.println("Please choose a partner to acommpany you on your adventuer!\n1. Charmander\n2. Bulbasaur\n3. Squirtle");

    int choosePokemon = 0;
    while(!valid) {
      if(in.hasNextInt()) {
        choosePokemon = in.nextInt();
        if(choosePokemon <= 3 && choosePokemon >= 1) {
          valid = true;
        }
        else {
          System.out.println("Choose a valid Pokemon!");
        }
      }
      else {
        in.next();
        System.out.println("Invalid input.");
      }
    }
    System.out.println("");

    if( choosePokemon == 1 ) {
      pokemon = pokemonGenerator.getPokemon("Charmander");
    }
    else if( choosePokemon == 2 ) {
      pokemon = pokemonGenerator.getPokemon("Bulbasaur");
    }
    else if( choosePokemon == 3 ) {
      pokemon = pokemonGenerator.getPokemon("Squirtle");
    }

    //Initalizes new Trainer 
    Trainer trainer = new Trainer( trainerName, pokemon );

    /*
     * While loop executes while trainer is not dead
     * This while loop allows Trainer to traverse map or quit game
     */
    while( trainer.getHP() != 0 ) {
      System.out.println( trainer );
      int chooseDirection = mainMenu();
      System.out.println( "" );
      
      //spotEvent represents the char that Trainer is on
      char spotEvent = '\0';

      if( chooseDirection == 1 ) {
        spotEvent = trainer.goNorth();
      }

      else if( chooseDirection == 2 ) {
        spotEvent = trainer.goSouth();
      }

      else if( chooseDirection == 3 ) {
        spotEvent = trainer.goEast();
      }

      else if( chooseDirection == 4 ) {
        spotEvent = trainer.goWest();
      }

      else if( chooseDirection == 5 ) {
        System.out.println("Quitting game...");
        break;
      }

      //Code that executes when Trainer is on a 'f' char
      if( spotEvent == 'f' ) {
        boolean beatGym = gymBattle(trainer, level);
        if(beatGym) {
          System.out.println( "Congratulations! You've won the gym battle!\nAll of your pokemon have leveled up!\nYou move on to the next area.\n" );
          Point p = new Point(100,100);
          map.reveal( p );
          //map.reveal( trainer.getLocation() );
          if( determineMap == 1 ) {
            determineMap = 2;
            map.loadMap(2);
          } 
          else if( determineMap == 2 ) {
            determineMap = 3;
            map.loadMap(3);
          }
          else if( determineMap == 3 ) {
            determineMap = 1;
            map.loadMap(1);
          }
          trainer.buffAllPokemon();
          level++;
        }
        else if(!beatGym) {
          System.out.println("All your pokemon have fainted...\nYou exit the gym.\n");
        }
      }

      //Code that executes when Trainer is on a 'i' char
      else if( spotEvent == 'i' ) {
        int chooseFind = (int)Math.floor(Math.random()*(2-1+1)+1);

        if( chooseFind == 1 ) {
          trainer.receivePokeball();
          System.out.println( "You found a pokeball!\n" );
        }
        else if( chooseFind == 2 ) {
          trainer.receivePotion();
          System.out.println( "You found a potion!\n" );
        }
        map.removeCharAtLoc( trainer.getLocation() );
      }

      //Code that executes when Trainer is on a 'w' char
      else if( spotEvent == 'w' ) {
        Pokemon wildPokemon = pokemonGenerator.generateRandomPokemon(level);
        boolean isCaught = false;
        int trainerOriginalHP = trainer.getHP();
        System.out.print( "A wild " + wildPokemon + " showed up!\nWhat do you want to do?\n" );

        /*
         * While loop lets user input whether they want to fight, use potion, etc... 
         * Executes while:
         *  Wild Pokemon is not at 0 HP
         *  Wild Pokemon is not caught
         *  Trainer is not at 0 HP
         *  Trainer has not taken damage
         */
        while( wildPokemon.getHP() != 0 && !isCaught && trainer.getHP() != 0 && trainerOriginalHP == trainer.getHP() ) {
          System.out.println( "1. Fight\n2. Use potion\n3. Throw pokeball\n4. Run away" );
          int fightChoose = 0;
          valid = false;
          while(!valid) {
            if(in.hasNextInt()) {
              fightChoose = in.nextInt();
              if(fightChoose <= 4 && fightChoose >= 1) {
                valid = true;
              }
              else {
                System.out.println("Invalid range.");
              }
            }
            else {
              in.next();
              System.out.println("Invalid input.");
            }
          }
          System.out.println("");

          //If user inputs 1, execute fight
          if( fightChoose == 1 ) {
            wildPokemon = trainerAttack( trainer, wildPokemon );
          }

          //If user inputs 2, use potion on specified Pokemon
          else if( fightChoose == 2 ) {
            if( !trainer.hasPotions() ) {
              System.out.println( "You don't have any potions left!\n" );
            }
            else {
              System.out.println( "Which pokemon are you giving a potion to?" );
              System.out.println( trainer.getPokemonList() );
              int choosePotionPokemon = 0;
              valid = false;
              while(!valid) {
                if(in.hasNextInt()) {
                  choosePotionPokemon = in.nextInt();
                  if(choosePotionPokemon <= trainer.getNumPokemon() + 1 && choosePotionPokemon >= 1) {
                    valid = true;
                  }
                  else {
                    System.out.println("Invalid range.");
                  }
                }
                else {
                  in.next();
                  System.out.println("Invalid input.");
                }
              }

              trainer.usePotion( choosePotionPokemon-1 );
              System.out.println( trainer.getPokemon(choosePotionPokemon-1) + " has been healed to full health and buffed!\n" );
            }
          }

          //If user inputs 3, attempt to catch wild Pokemon
          else if( fightChoose == 3 ) {
            if( !trainer.hasPokeballs() ) {
              System.out.println( "You don't have any pokeballs to throw!\n" );
            }
            else {
              isCaught = trainer.catchPokemon( wildPokemon );
              if( isCaught ) {
                System.out.println( "\nShake... Shake... Shake...\nYou have captured " + wildPokemon.getName() + "\n" );
                map.removeCharAtLoc( trainer.getLocation() );
              }
              else if( !isCaught ) {
                System.out.println( "\nShake... Shake... Shake...\nOh no! The " + wildPokemon.getName() + " has escaped\n" );
              }
              if( trainer.getNumPokemon() == 7 ) {
                System.out.println("You have too many pokemon!\nPlease choose one to release.");
                System.out.print(trainer.getPokemonList());
                choosePokemon = 0;
                valid = false;
                while(!valid) {
                  if(in.hasNextInt()) {
                    choosePokemon = in.nextInt();
                    if(choosePokemon <= 7 && choosePokemon >= 1) {
                      valid = true;
                    }
                    else {
                      System.out.println("Invalid range.");
                    }
                  }
                  else {
                    in.next();
                    System.out.println("Invalid input.");
                  }
                }
                System.out.println("");
                Pokemon removedPokemon = trainer.removePokemon(choosePokemon);
                System.out.println("You have removed " + removedPokemon.getName() + " from your party.\n");
                
              }
            }
          }

          //If user inputs 4, run away in a random direction
          else if( fightChoose == 4 ) {
            int randomDirection = (int)Math.floor(Math.random()*(4-1+1)+1);
            if( randomDirection == 1 ) {
              spotEvent = trainer.goNorth();
              System.out.println( "You ran away north.\n" );
            }
            else if( randomDirection == 2 ) {
              spotEvent = trainer.goSouth();
              System.out.println( "You ran away south.\n" );
            }
            else if( randomDirection == 3 ) {
              spotEvent = trainer.goEast();
              System.out.println( "You ran away east.\n" );
            }
            else if( randomDirection == 4 ) {
              spotEvent = trainer.goWest();
              System.out.println( "You ran away west.\n" );
            }
            break;
          }
        }

        /*
         * Once out of fightMenu while-loop, check to see if wild Pokemon is dead or if Trainer is dead.
         *  If wild Pokemon is dead remove 'w' char from map and go back to traversing map
         *  If Trainer is dead, end the game
         */
        if( wildPokemon.getHP() == 0 ) {
          System.out.println( "The wild " + wildPokemon.getName() + " has fainted.\n" );
          map.removeCharAtLoc( trainer.getLocation() );
        }
        else if( trainer.getHP() == 0 ) {
          System.out.println( "You have reached 0 HP...\nGame over" );
          break;
        }
      }

      //Code that executes if Trainer is on 'p' char
      else if( spotEvent == 'p' ) {
        int randomPerson = (int)Math.floor(Math.random()*(6-1+1)+1);

        //Randomly generates a number that determines which event will be used
        if( randomPerson == 1 ) {
          System.out.println( "You see Team Rocket in the middle of one of their schemes\nYou decide to stop them but take 5 damage while doing so.\n" );
          trainer.takeDamage(5);
        }
        else if( randomPerson == 2 ) {
          System.out.println( "You were walking along the road, when you see $10 has dropped from a stranger's pocket.\n1. Return the money to them\n2. Take the money for yourself" );
          int choice = 0;
          valid = false;
          while(!valid) {
            if(in.hasNextInt()) {
              choice = in.nextInt();
              if (choice <= 2 && choice >= 1) {
                valid = true;
              } 
              else {
                System.out.println("Invalid range.");
              }
            }
            else {
              in.next();
              System.out.println("Invalid input.");
            }
          }
          System.out.println("");
          if( choice == 1 ) {
            System.out.println( "You pick up the money and go to return it to the stranger.\nIt turns out he was Brock, the rock type gym leader!\nHe thanks you for your kindness and gives you a reward of $20!\n" );
            trainer.receiveMoney(20);
          }
          else if( choice == 2 ) {
            System.out.println( "You swipe the money off the ground and keep it for yourself.\n" );
            trainer.receiveMoney(10);
          }
        }
          else if( randomPerson == 3 ) {
            System.out.println( "You come across Gary Oak and he trips you as you're walking by.\nHe cackles with laughter. You take 3 damage.\n" );
            trainer.takeDamage(3);
          }
          else if( randomPerson == 4 ){
            System.out.println( "You are confronted by Officer Jenny while walking through a trail...\nShe ended up misstaking you as a perp and you are whacked in the head.\nYou take 4 damage.\n" );
            trainer.takeDamage(4);
          }
          else if( randomPerson == 5  ){
            System.out.println( "You cross paths with Brock and wave hi.\nBrock waves hi back and gives you a spare pokeball.\n" );
            trainer.receivePokeball();
          }
          else if( randomPerson == 6 ){
            System.out.println( "You come across Professor Oak as he is doing research in the field.\nHe hands you two potions he had crafted himself.\n" );
            trainer.receivePotion();
            trainer.receivePotion();
          }
        map.removeCharAtLoc( trainer.getLocation() );
        //add another else if statement here to add more people events
      }

      //Code that executes if Trainer is on 'c' char
      else if( spotEvent == 'c' ) {
        System.out.println( "You have entered the city\nWhere do you want to go?\n1. Store\n2. Pokemon Center" );
        int chooseCity = 0;
        valid = false;
        while(!valid) {
          if(in.hasNextInt()) {
            chooseCity = in.nextInt();
            if(chooseCity <= 2 && chooseCity >= 1) {
              valid = true;
            }
            else {
              System.out.println("Invalid range.");
            }
          }
          else {
            in.next();
            System.out.println("Invalid input.");
          }
        }
        System.out.println("");

        if( chooseCity == 1 ) {
          store( trainer );
        }
        else if( chooseCity == 2 ) {
          System.out.println( "Nurse Joy: Welcome to the Pokemon Center! I'll heal your pokemon right up!" );
          trainer.healAllPokemon();
          System.out.println( "Nurse Joy: All your pokemon have been healed! We hope to see you again!\n" );
        }
      }

      //Code that executes if Trainer is on 'n' char
      else if( spotEvent == 'n' ) {
        System.out.println( "There's nothing here.\n" );
      }

      //Code that executes if Trainer is on 'm' char
      else if( spotEvent == 'm' ) {
        System.out.println( "You can't go this way.\n" );
      }
    }
    if( trainer.getHP() == 0 ) {
      System.out.println("You have reached 0 HP...\nGame over");
    }
  }

  /**
   * Method prints out the main menu and then asks for an input from user
   * @return The int representing which menu option the user chose
   */
  public static int mainMenu()
  {
    String menuOption = "Main Menu:\n1. Go North\n2. Go South\n3. Go East\n4. Go West\n5. Exit";
    System.out.println( menuOption );
    Scanner in = new Scanner(System.in);
    int chosenInput = 0;
    boolean valid = false;
    while(!valid) {
      if(in.hasNextInt()) {
        chosenInput = in.nextInt();
        valid = true;
      }
      else {
        in.next();
        System.out.println("Invalid Input.");
      }
    }
    // int chosenInput = Integer.valueOf( CheckInput.getInt() );
    return chosenInput;
  }
  
  /**
   * Method starts an attack sequence between Trainer Pokemon and wild Pokemon
   * @param t The Trainer that is executing the attack sequence
   * @param wild The wild Pokemon that is being battled
   * @return 
   */
  public static Pokemon trainerAttack( Trainer t, Pokemon wild ) {
    Scanner in = new Scanner(System.in);
    char spotEvent = Map.getInstance().getCharAtLoc(t.getLocation());
    boolean valid = false;

    if( wild.getHP() != 0 && t.getHP() > 0 ) {
      System.out.println( "Which Pokemon would you like to choose?" );
      System.out.print( t.getPokemonList() );
      int choosePokemon = 0;
      valid = false;
      while(!valid) {
        if(in.hasNextInt()) {
          choosePokemon = in.nextInt();
          if(choosePokemon <= t.getNumPokemon() && choosePokemon >= 1) {
            valid = true;
          }
          else {
            System.out.println("Invalid range.");
          }
        }
        else {
          in.next();
          System.out.println("Invalid input.");
        }
      }

      System.out.println( "" );
      Pokemon battlePokemon = t.getPokemon( choosePokemon-1 );
      System.out.println( "Go " + battlePokemon.getName() + ", I choose you!" );

      //Trainer Pokemon attacks as long as it's alive
      if( battlePokemon.getHP() != 0 ) {
        System.out.println( battlePokemon.getAttackTypeMenu() );
        int chooseMenu = 0;
        valid = false;
        while(!valid) {
          if(in.hasNextInt()) {
            chooseMenu = in.nextInt();
            if(chooseMenu <= battlePokemon.getNumAttackTypeMenuItems() && chooseMenu >= 1) {
              valid = true;
            }
            else {
              System.out.println("Invalid range.");
            }
          }
          else {
            in.next();
            System.out.println("Invalid input.");
          }
        }
        System.out.println( "" );

        //If user chooses 1 then select basic attacks
        if( chooseMenu == 1 ) {
          System.out.println( battlePokemon.getAttackMenu(1) );
          int chooseAttack = 0;
          valid = false;
          while(!valid) {
            if(in.hasNextInt()) {
              chooseAttack = in.nextInt();
              if(chooseAttack <= battlePokemon.getNumAttackMenuItems(1) && chooseAttack >= 1) {
                valid = true;
              }
              else {
                System.out.println("Invalid range.");
              }
            }
            else {
              in.next();
              System.out.println("Invalid input.");
            }
          }
          System.out.println( "" );
          System.out.println( battlePokemon.attack(wild, 1, chooseAttack) );
        }

        //If user chooses 2 then do special attacks
        else if( chooseMenu == 2 ) {
          System.out.println( battlePokemon.getAttackMenu(2) );
          int chooseAttack = 0;
          valid = false;
          while(!valid) {
            if(in.hasNextInt()) {
              chooseAttack = in.nextInt();
              if(chooseAttack <= battlePokemon.getNumAttackMenuItems(2) && chooseAttack >= 1) {
                valid = true;
              }
              else {
                System.out.println("Invalid range.");
              }
            }
            else {
              in.next();
              System.out.println("Invalid input.");
            }
          }
          System.out.println( "" );
          System.out.println( battlePokemon.attack(wild, 2, chooseAttack) );
        }

        //25% chance to debuff wild pokemon
        boolean debuff = new Random().nextInt(4) == 0;
        if(debuff) {
          System.out.println("Your pokemon debuffed the enemy pokemon!\n");
          wild = PokemonGenerator.getInstance().addRandomDeBuff(wild);
        }
      }
      
      //Wild Pokemon attacks Trainer Pokemon as long as Trainer Pokemon is not dead
      if( battlePokemon.getHP() != 0 ) {
        int randomMenu = (int)Math.floor(Math.random()*(wild.getNumAttackTypeMenuItems()-1+1)+1);
        //Randomly chooses whether wild does basic or special attack
        if( randomMenu == 1 ) {
          int randomAttack = (int)Math.floor(Math.random()*(wild.getNumAttackMenuItems(1)-1+1)+1);
          System.out.println(wild.attack(battlePokemon, 1, randomAttack));
          }
        else if( randomMenu == 2 ) {
          int randomAttack = (int)Math.floor(Math.random()*(wild.getNumAttackMenuItems(2)-1+1)+1);
          System.out.println( wild.attack(battlePokemon, 2, randomAttack));
        }

        //10% chance to debuff trainer pokemon
        boolean debuff = new Random().nextInt(10) == 0;
        if(debuff) {
          System.out.println("The enemy pokemon debuffed your pokemon!\n");
          t.debuffPokemon(choosePokemon-1);
          battlePokemon = t.getPokemon(choosePokemon-1);
        }

        //Prints information about Trainer, Trainer's pokemon, and pokemon 
        System.out.println( t.getName() + " HP: " + t.getHP() + "/" + t.getMaxHP() );
        System.out.println( "Your " + battlePokemon );
        System.out.println( "Enemy " + wild + "\n" );
      }

      //If Trainer Pokemon is dead then enemy pokemon attacks Trainer
      else if( battlePokemon.getHP() == 0 && spotEvent != 'f' ) { 
        System.out.println( battlePokemon.getName() + " is at 0 HP!" );
        int randomDMG = (int)Math.floor(Math.random()*(5-1+1)+1);
        t.takeDamage( randomDMG );
        System.out.println( "The " + wild.getName() + " hit you and took off!\nYou've taken " + randomDMG + " damage." + "\n" );
        return wild;
      }
      else if( battlePokemon.getHP() == 0 && spotEvent == 'f' ) {
        System.out.println(battlePokemon.getName() + " has fainted and can't battle!\n");
      }
    }
    return wild;
  }
  
  /**
   * Method used to create the store when entering the city on the map
   * @param t Passes in the Trainer that will be acessing store
   */
  public static void store(Trainer t) {
    Scanner in = new Scanner(System.in);
    boolean chooseExit = false;
    boolean valid = false;

    //Executes while user does not input 3 or "Exit"
    while( !chooseExit ) {
      int trainerMoney = t.getMoney();
      System.out.println( "1. Buy Pokeballs - $3\n2. Buy Potions - $5\n3. Exit" );
      System.out.println("I have $" + trainerMoney + " to spend.");
      int chooseStore = 0;
      valid = false;
      while(!valid) {
        if(in.hasNextInt()) {
          chooseStore = in.nextInt();
          if(chooseStore <= 3 && chooseStore >= 1) {
            valid = true;
          }
          else {
            System.out.println("Invalid range.");
          }
        }
        else {
          in.next();
          System.out.println("Invalid input.");
        }
      }
      System.out.println( "" );
      if( chooseStore == 1 ) {
        if(t.spendMoney(3)) {
          t.receivePokeball();
          System.out.println("Here's your pokeball. That'll be $3.\n");
          System.out.println("Would you like anything else?\n");
        }
        else {
          System.out.println("You do not have enough for a pokeball.");
        }
      }
      else if( chooseStore == 2 ) {
        if(t.spendMoney(5)) {
          t.receivePotion();
          System.out.println("Here's your potion. That'll be $5.\n");
          System.out.println("Would you like anything else?\n");
        }
        else {
          System.out.println( "You do not have enough for a potion." );
        }
      }
      else if( chooseStore == 3 ) {
        System.out.print( "Thank you for shopping here, we hope to see you again!\n\n" );
        chooseExit = true;
      }
    }
  }
  /**
   * Method used to initiate gym battle when at finish char
   * @param t Passes in trainer that will be battling gym leader
   * @param level Passes in the current level of trainer 
   * @return Whether or not trainer defeats the gym as true or false
   */
  public static boolean gymBattle(Trainer t, int level) {
    Scanner in = new Scanner(System.in);
    boolean valid = false;
    boolean pokemonAlive = false;
    boolean exitGym = false;
    System.out.println( "You have entered the gym.\nPrepare to battle the gym leader!" );
    //Creates gym pokemon that is 2 levels higher than trainer
    Pokemon gymPokemon = PokemonGenerator.getInstance().generateRandomPokemon( level+2 );
    System.out.println( "The gym leader throws out their " + gymPokemon.getName() + "!" );
    
    while(!exitGym) {
      pokemonAlive = trainerTeamStatus(t);
      //If gym pokemon is dead, exit gym
      if(gymPokemon.getHP() == 0) {
        exitGym = true;
      }
      else if(pokemonAlive) {
        System.out.println( "1. Fight\n2. Use potion" );
        int userInput = 0;
        valid = false;
        while(!valid) {
          if(in.hasNextInt()) {
            userInput = in.nextInt();
            if(userInput <= 2 && userInput >= 1) {
              valid = true;
            }
            else {
              System.out.println("Invalid range.");
            }
          }
          else {
            in.next();
            System.out.println("Invalid input.");
          }
        }
        System.out.println( "" );

        //If user inputs 1 then initiate fight 
        if(userInput == 1) {
          gymPokemon = trainerAttack( t, gymPokemon );
        }

        //If user inputs 2 then use potion on selected pokemon
        else if(userInput == 2) {
          if(!t.hasPotions()) {
              System.out.println( "You don't have any potions left!\n" );
          }
          else {
            System.out.println( "Which pokemon are you giving a potion to?" );
            System.out.print( t.getPokemonList() );
            int choosePotionPokemon = 0;
            valid = false;
            while(!valid) {
              if(in.hasNextInt()) {
                choosePotionPokemon = in.nextInt();
                if(choosePotionPokemon <= t.getNumPokemon() + 1 && choosePotionPokemon >= 1) {
                  valid = true;
                }
                else {
                  System.out.println("Invalid range.");
                }
              }
              else {
                in.next();
                System.out.println("Invalid input.");
              }
            }
            System.out.println( "" );
            t.usePotion( choosePotionPokemon-1 );
            System.out.println(t.getPokemon( choosePotionPokemon-1 ) + " has been healed to full health!\n");
          }
        }
      }
      
      //If trainer pokemon are dead then exit gym battle
      else if( !pokemonAlive ) {
        break;
      }
    }
    return exitGym;
  }

  /**
   * Method that checks whether or not trainer team is dead
   * @param t Passes in trainer to be able to check their team
   * @return If pokemon team is all dead return false otherwise return true
   */
  public static boolean trainerTeamStatus( Trainer t ) {
    boolean teamAlive = true;
    int deadPokemon = 0;
    for( int i = 0; i < t.getNumPokemon(); i++ ) {
      //Checks to see if any pokemon in trainer team is alive
      if(t.getPokemon(i).getHP() == 0) {
        deadPokemon++;
      }
    }
    if(deadPokemon == t.getNumPokemon()) {
      teamAlive = false;
    }
    return teamAlive;
  }
}