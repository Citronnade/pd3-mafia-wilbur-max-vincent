import java.util.*;
import java.io.File;

public class Driver{
    
    public static Game game;
    
    public static void startUp(){//initial things to do before the game starts
    	System.out.print("Welcome to Mafia! How many players? ");
    	Scanner s = new Scanner(System.in);
        
        String in = "";
        int numPlayers = 0;
        while (numPlayers <= 0){
            in = s.nextLine();
            try {
		numPlayers = Integer.parseInt(in);
		if (numPlayers <= 0){
                    System.out.print("Please input a positive integer: ");
		}
            } catch (NumberFormatException E){
                System.out.print("Please input an integer: ");
            }
        }
    	
    	System.out.println();
    	game = new Game();
    	int i = 0;
    	while (i < numPlayers){
            System.out.println("Player " + (i + 1));
	    addPlayer();
	    i++;
            System.out.println();
    	}
    }

    public static void startUpRoleChooser(){
        System.out.print("Welcome to Mafia! How many players? ");
        Scanner s = new Scanner(System.in);
        
        String in = "";
        int numPlayers = 0;
        while (numPlayers <= 0){
            in = s.nextLine();
            try {
		numPlayers = Integer.parseInt(in);
		if (numPlayers <= 0){
                    System.out.print("Please input a positive integer: ");
		}
            } catch (NumberFormatException E){
                System.out.print("Please input an integer: ");
            }
        }
        
        System.out.println();

        game = new Game();
        
        int numRolesRemaining = numPlayers;//number of roles left to assign
        String[] roles = {"mafia","godfathers","framers","cops","doctors","bombs","drunks","vigilantes","grannies","fools","hookers","serialkillers","villager"};
        int[] numOfEachRole = new int[roles.length];//array for the number of people with that role 
        int i = 0;
        while (numRolesRemaining > 0){
            if (i == roles.length-1){//in case gone through all the roles and still have extras 
                numOfEachRole[i] = numRolesRemaining;
                numRolesRemaining = 0;
            } else {
                System.out.print("How many " + roles[i] + "? ");

                int temp = -1;
                while (temp < 0 || temp > numRolesRemaining){
                    in = s.nextLine();
                    try {
                        temp = Integer.parseInt(in);
                        if (temp < 0){
                            System.out.print("Please input a nonnegative integer: ");
                        }
                    } catch (NumberFormatException E){
                        System.out.print("Please input an integer: ");
                    }
                }
                numOfEachRole[i] = temp;
                numRolesRemaining -= temp;
            }
            i++;

        }
	System.out.println("numofEachRole:" + Arrays.toString(numOfEachRole));

        String[] names = new String[numPlayers];
        int index = 0;
        while (index < names.length){
            System.out.println("Player " + (index+1));
            System.out.print("What is your name? ");
            String name = s.nextLine();
            while (Arrays.asList(names).contains(name)){
                System.out.print("That name has already been chosen. Please choose another name: ");
                name = s.nextLine();
            }

            names[index] = name;
            index++;
        }

        ArrayList<String> rolesInGame = new ArrayList<String>();//all the valid roles in game
        for (i = 0; i < numOfEachRole.length;i++){
            for (int j = numOfEachRole[i]; j > 0; j--){
                rolesInGame.add(roles[i]);//j roles of type i
            }
        }
	System.out.println(rolesInGame);

        for (i = 0; i < names.length; i++){
            int rand = (int)(Math.random() * rolesInGame.size());
            String roleForI =  rolesInGame.get(rand);
            rolesInGame.remove(rand);
	    System.out.println("roleForI: " + roleForI);
	    System.out.println("rolesInGame: " + rolesInGame);
            switch (roleForI){
                //"mafia","godfathers","framers","cops","doctors","bombs","drunks","vigilantes","grannies","fools","hookers","serialkillers","villager"
	    case "mafia": game.addPlayer(new Mafia(names[i]));
		break;
	    case "godfathers": game.addPlayer(new Godfather(names[i]));
		break;
	    case "framers": game.addPlayer(new Framer(names[i]));
		break;
	    case "cops": 
		game.addPlayer(new Cop(names[i]));
		/*
		double rando = Math.random();
		if (rando < 0.25){
		    game.addPlayer(new Cop(names[i]));
		} 
		else if (rando < 0.5){
		    game.addPlayer(new NaiveCop(names[i]));
		} 
		else if (rando < 0.75){
		    game.addPlayer(new ParanoidCop(names[i]));
		} 
		else {
		    game.addPlayer(new InsaneCop(names[i]));
		}
		break;
		*/
		break;
	    case "doctors": game.addPlayer(new Doctor(names[i]));
		break;
	    case "bombs": game.addPlayer(new Bomb(names[i]));
		break;
	    case "drunks": game.addPlayer(new Drunk(names[i]));
		break;
	    case "vigilantes": game.addPlayer(new Vigilante(names[i]));
		break;
	    case "grannies": game.addPlayer(new Granny(names[i]));
		break;
	    case "fools": game.addPlayer(new Fool(names[i]));
		break;
	    case "hookers": game.addPlayer(new Hooker(names[i]));
		break;
	    case "serialkillers": game.addPlayer(new SerialKiller(names[i]));
		break;
	    default: game.addPlayer(new Villager(names[i]));
		break;
            }

        }

    }

    public static void addPlayer(){//temporary thing for testing purposes
    	String[] types = {"villager","mafia","godfather","framer", "cop","doctor","bomb","drunk","vigilante","granny","fool","hooker","serialkiller"};

    	Scanner s = new Scanner(System.in);
    	System.out.println("What kind of player would you like to be? ");
    	for (String str : types){
	    System.out.print(str + " " 
			     //+ "[" + str.substring(0,1) + "]"
			     + " ");
    	}
    	System.out.println();
    	String in = s.nextLine();
        in = in.toLowerCase();
    	System.out.print("What is your name? ");
    	String name = s.nextLine();
        while (!(validateName(name))){
            System.out.print("That name has already been chosen. Please choose another name: ");
            name = s.nextLine();
        }

    	Player temp = null;


    	if (in.equals("villager") || in.equals("v")){
	    temp = new Villager(name);
    	} 
	else if (in.equals("mafia") || in.equals("m")){
	    temp = new Mafia(name);
    	} 
        else if (in.equals("godfather")) {
            temp = new Godfather(name);
        }
	else if (in.equals("cop") || in.equals("c")){
            double rand = Math.random();
            if (rand < 0.25){
                temp = new Cop(name);
            } 
            else if (rand < 0.5){
                temp = new NaiveCop(name);
            } 
            else if (rand < 0.75){
                temp = new ParanoidCop(name);
            } 
            else {
                temp = new InsaneCop(name);
            }
    	} 
	else if (in.equals("doctor") || in.equals("d")){
	    temp = new Doctor(name);
    	} 
	else if (in.equals("bomb")) {
	    temp = new Bomb (name);
	} else if (in.equals("drunk")){
	    temp = new Drunk(name);
	}
	else if (in.equals("vigilante")) {
	    temp = new Vigilante (name);
	}
	else if (in.equals("granny")) {
	    temp = new Granny (name);
	}
	else if (in.equals("fool")) {
	    temp = new Fool (name);
	}
	else if (in.equals("hooker")) {
	    temp = new Hooker (name);
	}
	else if (in.equals("SerialKiller")) {
	    temp = new SerialKiller (name);
	}
	else {
	    temp = new Villager(name);
    	}
	//temporary final assignment
	//there's gotta be a better way to do this

    	game.addPlayer(temp);
    }

    public static boolean validateName(String name){//to make sure nobody has the same name
        for (Player p : game.getPlayers()){
            if (name.equals(p.getName())){//muy mal
                return false;
            }
        }
        return true;
    }

    public static void loopThroughPlayers(){
	String playersstr = ""; //contains all playernames
	ArrayList<Player> players = game.getPlayers(); //assuming this is sorted
	for (Player currentP: players){
	    playersstr += currentP.getName() + " ";
	}
	playersstr = playersstr.substring(0,playersstr.length()-1);//just to remove the last space
	System.out.println(playersstr);
    }

    public static void queryPlayers(String allPlayers){
	Scanner s = new Scanner(System.in);
	System.out.println();

	String in = "";
        
	ArrayList<Player> players = game.getPlayers();

	Mafia mafiaVisitor = new Mafia("I'm going to be gone soon");

	for (Player currentP: players){
	    int actValue = currentP.act();
	    if(1 == 1){//if != -1, then that person didn't complete a night action yet
		//don't want to screw up the logic any more
		//System.out.println("mafiawent:" + game.mafiaWent);
		if (currentP instanceof Mafia && !game.mafiaWent){
		    game.mafiaWent = true; //must be reset every tick
		    mafiaVisitor = (Mafia) currentP;
		   // System.out.println("currentP: " + currentP);
		}

		//System.out.println("current's class: " + currentP.getClass());
		//System.out.println("is it a mafia? " + (currentP instanceof Mafia));
  		if(currentP.priority <= 2 && !game.mafiaWent){ //this checks if we skipped past where the mafia should go, even if there are no vanilla mafia
		    //System.out.println("normal killing:");
  		    if (game.mafiaWent){
  		    	System.out.println("Mafia, choose someone to kill.  " + mafiaVisitor.getName() + " will visit.");

  		    	in = s.nextLine();

  		    	while (!(playerExists(in, players))){
			    System.out.println("Please choose someone in the game.");
			    in = s.nextLine();
			}

			for (Player target: players){    //find the target.  we can quickselect
  			    if (target.getName().equals(in)){
				mafiaVisitor.kill(target); //basically puts kill mark on target.
				game.mafiaDone = true;
				//mafia now kill manually, act() routines do nothing.
  			    }
			}
		    }
		} 
		if (actValue != -1){ //moved the skip to here
		    System.out.println(currentP.getName() + 
				       "(" + currentP.getClass().getName() + ")"
				       + ", please wake up.");
		    System.out.print(currentP.getActionText());
            
		    in = s.nextLine();
          
		    while (!(playerExists(in, players))){
			System.out.println("Please choose someone in the game.");
			in = s.nextLine();
		    }
		
		    currentP.act(game.getPlayerByName(in));
		    System.out.println(currentP.getName() + ", please go to sleep.");
		    System.out.println();
		
		}
	    }
	    else{
		if (currentP instanceof Mafia && !game.mafiaWent){
		    game.mafiaWent = true; //must be reset every tick
		    mafiaVisitor = (Mafia) currentP;
		}
	    }
	    
	    //mafia night action

	    in = "";
	}
	if (!game.mafiaDone && mafiaVisitor != null){ //mafia somehow still got skipped over and is not eliminated yet:
	    //System.out.println("alternative killing:");
	    System.out.println("Mafia, choose someone to kill.  " + mafiaVisitor.getName() + " will visit."); //put in killing
		
	    in = s.nextLine();

	    while (!(playerExists(in, players))){
		System.out.println("Please choose someone in the game.");
		in = s.nextLine();
	    }
	    
	    for (Player target: players){    //find the target.  we can quickselect
		if (target.getName().equals(in)){
		    mafiaVisitor.kill(target); //basically puts kill mark on target.
		    game.mafiaDone = true;
		    //mafia now kill manually, act() routines do nothing.
		}
	    }
	}
	game.mafiaWent = false;
	game.mafiaDone = false;

	
    }
    
    public static void dayTime(){//daytime phase
	Scanner s = new Scanner(System.in);

	System.out.println("It's daytime! Discuss who you wanna lynch, if anyone.");
	System.out.print("Do you want to lynch someone? (Y/N) ");

	String in = s.nextLine();
	in = in.toLowerCase();
	while (!(in.equals("y") || in.equals("n")
		 || in.equals("no") || in.equals("yes"))){
	    System.out.print("Please input Y/N/Yes/No: ");
	    in = s.nextLine();
	}

	if (in.equals("no") || in.equals("n")){
	    return;
	} else { //yes
	    System.out.print("Who do you wanna lynch? ");
	    printAll();
	    in = s.nextLine();
	    while (!(playerExists(in,game.getPlayers()))){
		System.out.print("Please choose someone in the game: ");
		in = s.nextLine();
	    }
	    game.removePlayer(in);
	}

    }

    public static void printAll(){// test function
	for (Player p : game.getPlayers()){
	    System.out.println(p);
	}
    }
    

    public static boolean playerExists(String name, ArrayList<Player> players){
	for (int x = 0; x <players.size();x++){
	    if (players.get(x).getName().equals(name)){
		return true;
	    }
	}
	return false;
    }

    public static void main(String[] args) {
	startUpRoleChooser();
	int night = 1;
	Collections.sort(game.getPlayers(),new PriorityComp());
	CardHash hash = new CardHash(game.getPlayers());
	System.out.println();
	System.out.println("These cards represent the different roles for the people.");
	System.out.println(hash);
	while (game.checkWinConditions() == 0){ //loop
	    //game.tick();
	    System.out.println();
	    System.out.println("Night " + night
			       + "\nEverybody go to sleep!");
      
	    night++;
	    loopThroughPlayers();
	    queryPlayers("");
	    System.out.println();
	    System.out.println("Everybody wake up!");

	    System.out.println();
	    printAll();//test
	    System.out.println();

	    System.out.println(game.processMarks());

	    System.out.println();
	    printAll();//test
	    System.out.println();

	    if (game.checkWinConditions() != 0){
		break;
	    }
	    dayTime();

	}
    }
    
}
 
