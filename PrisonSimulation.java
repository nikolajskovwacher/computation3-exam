/* === Imports === */
import java.util.Random; // For simulating randomness in days
import java.util.ArrayList; // For handling data on prisoners
import java.util.Arrays;
import java.util.List;
import java.util.HashMap; // For counting contraband items found
import java.util.Map;
import java.util.Scanner; // For user input

/* === PRISON SIMULATION CLASS === */
class PrisonSimulation {
    // Variables
    static ArrayList<Prisoner> Prisoners = new ArrayList<Prisoner>();
    static int contrabandSearches = 0;
    static Map<String, Integer> confiscatedItems = new HashMap<String, Integer>();
    static int caughtIllegalActivity = 0;
    static int dayCount = 0;
    static int totalFights = 0;
    static int totalMoneyEarned = 0;

    // Main function
    public static void main(String args[])
    {
        // Welcome and begin simulation
        System.out.println("===============================================================================");
        System.out.println(" _____      _                    _____ _                 _       _             ");
        System.out.println("|  __ \\    (_)                  / ____(_)               | |     | |            ");
        System.out.println("| |__) | __ _ ___  ___  _ __   | (___  _ _ __ ___  _   _| | __ _| |_ ___  _ __ ");
        System.out.println("|  ___/ '__| / __|/ _ \\| '_ \\   \\___ \\| | '_ ` _ \\| | | | |/ _` | __/ _ \\| '__|");
        System.out.println("| |   | |  | \\__ \\ (_) | | | |  ____) | | | | | | | |_| | | (_| | || (_) | |   ");
        System.out.println("|_|   |_|  |_|___/\\___/|_| |_| |_____/|_|_| |_| |_|\\__,_|_|\\__,_|\\__\\___/|_|   ");
        System.out.println("                                                                               ");
        System.out.println("===============================================================================");
        System.out.println("\n===== Welcome to Prison Simulator - Comp3 Fall 2021 =====");
        System.out.println("-- Created by: Nikolaj Skov Wacher --\n");
        System.out.println("=== RULES ===");
        System.out.println("- Prisoners individual mood begin at 3 out of 5");
        System.out.println("- Their mood increases daily, unless they are caught with contraband, spend a day in isolation, or are found as instigators of a fight");
        System.out.println("- Some prisoners have jobs which they will go to each day, prisoners of higher intelligence have a higher chance of earning more money");
        System.out.println("- Searches are done randomly during the day, and randomly in the morning");
        System.out.println("- Weapons are considered more severe contraband, and will send prisoners in isolation for 3 days");
        System.out.println("- Cell block A is the default block for prisoners, Cell block B is for prisoners who have commited more severe crimes and/or have a higher temper\n");
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        System.out.println("- To begin simulating a new prison, press enter: \n");
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        enterToContinue();
        // Create random prisoners
        System.out.println("Creating 10 prisoners ...");
        sleep(1000);
        for (int i = 0; i < 10; i++) {
            Prisoners.add(new Prisoner(Prisoners.size()));
        };
        // Add more prisoners?
        Scanner userScanner = new Scanner(System.in);
        int makeMore = 1;
        while (makeMore == 1) {
            System.out.println("Do you want to add another prisoner? (Y/N): ");
            String userMakeMore = userScanner.nextLine();
            if (userMakeMore.toUpperCase().equals("YES") || userMakeMore.toUpperCase().equals("Y")) {
                // Make another prisoner
                System.out.println("Randomly generating another prisoner ...");
                Prisoners.add(new Prisoner(Prisoners.size()));
                sleep(1000);
                String prisonerCount = String.valueOf(Prisoners.size());
                System.out.println("Prisoner added! There are now " + prisonerCount + " prisoners in the prison");
            }
            else if (userMakeMore.toUpperCase().equals("NO") || userMakeMore.toUpperCase().equals("N")) {
                makeMore = 0;
            }
            else {
                System.out.println("Please only type, \"yes\" or \"no\"");
            };
        };
        // Generate new prison "roleplay" line printing
        System.out.println("-\nWe have the prisoners, now we need a prison ...!");
        System.out.println("What should our prison be called?: ");
        String prisonName = userScanner.nextLine();
        System.out.println("Generating prison \"" + prisonName + "\" ...");
        sleep(1000);
        System.out.println("Setting up walls between cell blocks ...");
        sleep(1000);
        System.out.println("Creating hallways ...");
        sleep(1000);
        System.out.println("Adding tables in the cafeteria ...");
        sleep(1000);
        System.out.println("Double-checking fences ...");
        sleep(1000);
        System.out.println("Hiring warden and correctional officers (COs) ...");
        sleep(1000);
        System.out.println("Performing ribbon cutting ceremony ...");
        sleep(1000);
        System.out.println("Prison generated!\n-");
        sleep(1000);
        System.out.println("================ Visual Representation ===============\n");
        System.out.println("|‾‾‾‾‾‾|‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|‾Laundry|");
        System.out.println("|  WO  |       Cafeteria and Kitchen        |  Room  |");
        System.out.println("|‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|");
        System.out.println("|                      Hall A1                       |");
        System.out.println("|      |‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾     ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|      |");
        System.out.println("| Hall |           Recreation Area            | Hall |");
        System.out.println("|  B1  |                                      |  B2  |");
        System.out.println("|      ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾       |");
        System.out.println("|                      Hall A2                       |");
        System.out.println("|‾‾‾‾‾‾|‾‾‾‾‾‾‾‾‾|‾Cell‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|‾Cell‾‾‾‾|");
        System.out.println("|  IS  | Showers | Block A                 | Block B |");
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        System.out.println("WO = Wardens office; IS = Isolation");
        System.out.println("=========================================================");
        // Final messages before simulating days
        System.out.println("Ready to begin simulating prison life!");
        int cbAcount = 0;
        int cbBcount = 0;
        for (int i = 0; i < Prisoners.size(); i++) {
            if (Prisoners.get(i).cellBlock.equals("A")) {
                cbAcount++;
            } else if (Prisoners.get(i).cellBlock.equals("B")) {
                cbBcount++;
            };
        };
        System.out.println("There are " + String.valueOf(cbAcount) + " prisoners in cell block A, and " +
            String.valueOf(cbBcount) + " in cell block B");
        System.out.println("Overall prisoner and staff mood is currently 3 out of 5");
        // Simulating days
        System.out.println("To begin simulating days, press enter: ");
        enterToContinue();
        simulateDay();
        while (dayCount < 10) {
            System.out.println("-\nBegin next day? Press enter: ");
            enterToContinue();
            simulateDay();
        };
        // Summary
        System.out.println("-\n10 days have passed in the prison: \"" + prisonName + "\"!");
        System.out.println("Press enter for a full summary of events: ");
        enterToContinue();
        System.out.println("\n===== SUMMARY =====");
        System.out.println(String.valueOf(contrabandSearches) + " contraband searches were performed");
        sleep(1000);
        if (confiscatedItems.isEmpty()) {
            System.out.println("There are no confiscated items, as no contraband was found during searches");
        } else if (confiscatedItems.isEmpty() == false) {
            System.out.println("Confiscated items were: ");
            sleep(1000);
            for (HashMap.Entry<String, Integer> entry: confiscatedItems.entrySet()) {
                System.out.println(entry.getKey() + ": " + String.valueOf(entry.getValue()));
            };
        };
        sleep(1000);
        System.out.println("Prisoners were caught doing illegal activities (i.e. fighting/hiding contraband) a total of " +
            String.valueOf(caughtIllegalActivity) + " times");
        sleep(1000);
        System.out.println("There was a total of " + String.valueOf(totalFights) + " fights");
        sleep(1000);
        int jobCounter = 0;
        for (int i = 0; i < Prisoners.size(); i++) {
            if (Prisoners.get(i).job == 1) {
                jobCounter += 1;
            };
        };
        System.out.println(String.valueOf(jobCounter) + " prisoners with jobs, earned a total of $" + String.valueOf(totalMoneyEarned) + " from their efforts");
        sleep(1000);
        double currentMood = calculateMood();
        System.out.printf("Overall prisoner and staff mood ended at %.1f on a scale of 1 to 5\n", currentMood);
        int viewPrisoners = 1;
        while (viewPrisoners == 1) {
            System.out.println("Do you want to see detailed information on each prisoner before exiting? (Y/N): ");
            String viewPrisonerData = userScanner.nextLine();
            if (viewPrisonerData.toUpperCase().equals("YES") || viewPrisonerData.toUpperCase().equals("Y")) {
                // Make another prisoner
                System.out.println("Printing detailed information ...");
                for (int i = 0; i < Prisoners.size(); i++) {
                    Prisoners.get(i).printPrisonerInformation();
                };
                sleep(2000);
                viewPrisoners = 0;
            }
            else if (viewPrisonerData.toUpperCase().equals("NO") || viewPrisonerData.toUpperCase().equals("N")) {
                viewPrisoners = 0;
            }
            else {
                System.out.println("Please only type, \"yes\" or \"no\"");
            };
        };
        System.out.println("Exit simulation? Press enter: ");
        enterToContinue();
        System.out.println("Exiting prison simulation ...");
        sleep(2000);
    };

    public static void simulateDay() {
        // Schedule: wake up, shower, eat, job (if existant), outside time, eat, recreation, curfew
        // Morning (wake up, shower, eat)
        System.out.println("-\nBeginning day " + String.valueOf(dayCount + 1) + " ...");
        System.out.println("The prisoners have woken up, and are getting ready to leave their cells");
        cellSearch();
        System.out.println("The prisoners are going to the showers");
        calculateFight("hallway to the showers");
        calculateFight("showers");
        System.out.println("The prisoners are heading to the cafeteria to eat");
        calculateFight("cafeteria");
        // Midday (job, outside time)
        System.out.println("Prisoners with jobs are going to work, others are going to their cells");
        simulateJobs();
        searchBeforeRecreation();
        System.out.println("The prisoners are heading outside for recreation time");
        calculateFight("yard");
        // Afternoon (eat, recreation)
        System.out.println("The prisoners are heading to the cafeteria to eat dinner");
        calculateFight("hallway to the cafeteria");
        calculateFight("cafeteria");
        // Night (curfew)
        System.out.println("Following eating dinner, the prisoners are shown back to their cells");
        System.out.println("Curfew for the day, the prisoners have gone to bed. Day " + String.valueOf(dayCount + 1) + " has passed.");
        // Mood changes & isolation countdown
        for (int i = 0; i < Prisoners.size(); i++) {
            if (Prisoners.get(i).inIsolation == 1) {
                if (Prisoners.get(i).isolationLeft == 0) {
                    Prisoners.get(i).inIsolation = 0;
                    System.out.println(Prisoners.get(i).name + " has been moved back from isolation, to their cell");
                };
                if (Prisoners.get(i).isolationLeft > 0) {
                    Prisoners.get(i).isolationLeft--;
                };
            };
            if (Prisoners.get(i).moodChangeToday == 0) {
                if (Prisoners.get(i).mood <= 4 && Prisoners.get(i).inIsolation == 0) {
                    Prisoners.get(i).mood++;
                } else if (Prisoners.get(i).mood >= 2 && Prisoners.get(i).inIsolation == 1) {
                    Prisoners.get(i).mood--;
                };
            Prisoners.get(i).moodChangeToday = 0;
            };
        };
        dayCount++;
    };

    public static void simulateJobs() {
        Random rand = new Random(); // To simulate randomness of job effectiveness
        for (int i = 0; i < Prisoners.size(); i++) {
            int moneyEarned = 0;
            if (Prisoners.get(i).job == 1) {
                if (Prisoners.get(i).intelligence.equals("low")) {
                    moneyEarned += rand.nextInt(3);
                } else if (Prisoners.get(i).intelligence.equals("medium")) {
                    moneyEarned += rand.nextInt(3) + 1;
                } else if (Prisoners.get(i).intelligence.equals("high")) {
                    moneyEarned += rand.nextInt(3) + 2;
                } else if (Prisoners.get(i).intelligence.equals("extreme")) {
                    moneyEarned += rand.nextInt(3) + 4;
                };
            Prisoners.get(i).money += moneyEarned;
            totalMoneyEarned += moneyEarned;
            };
        };
    };

    public static void cellSearch() {
        Random rand = new Random(); // To simulate randomness of whether there will be a cell search
        int searchToday = rand.nextInt(5); // If 0, do a cell search
        if (contrabandSearches < 2 && dayCount > 7) {
            searchToday = 0;
        };
        if (searchToday == 0) {
            System.out.println("The Warden has called for a random cell search today!");
            System.out.println("Correctional officers are searching cells and prisoners ...");
            sleep(1000);
            search();
        } else if (searchToday > 0) {
            System.out.println("There will be no random morning search today");
        };
    };

    public static void searchBeforeRecreation() {
        Random rand = new Random(); // To simulate randomness of whether there will be a search
        int searchToday = rand.nextInt(5); // If 0, do a cell search
        if (contrabandSearches < 2 && dayCount > 7) {
            searchToday = 0;
        };
        if (searchToday == 0) {
            System.out.println("Before heading ouside, the prisoners are searched!");
            System.out.println("Correctional officers are searching prisoners ...");
            sleep(1000);
            search();
        } else if (searchToday > 0) {
            System.out.println("There will be no search today before recreation time");
        };
    };

    public static void search() {
        Random rand = new Random(); // To simulate randomness of whether items will be found
        int contrabandFound = 0;
        for (int i = 0; i < Prisoners.size(); i++) {
            int searchChance = rand.nextInt(2);
            if (Prisoners.get(i).hiding_contraband.equals("hiding") && searchChance == 0 && Prisoners.get(i).inIsolation == 0) {
                System.out.println(Prisoners.get(i).name + " is being thouroughly searched ...");
                sleep(1000);
                int chanceOfFinding = 0;
                if (Prisoners.get(i).intelligence.equals("low")) {
                    chanceOfFinding = rand.nextInt(2);
                } else if (Prisoners.get(i).intelligence.equals("medium")) {
                    chanceOfFinding = rand.nextInt(3);
                } else if (Prisoners.get(i).intelligence.equals("high")) {
                    chanceOfFinding = rand.nextInt(5);
                } else if (Prisoners.get(i).intelligence.equals("extreme")) {
                    chanceOfFinding = rand.nextInt(10);
                };
                if (chanceOfFinding == 0) {
                    contrabandFound = 1;
                    caughtIllegalActivity++;
                    Prisoners.get(i).moodChangeToday = 1;
                    if (Prisoners.get(i).mood >= 2) {
                        Prisoners.get(i).mood--;
                    };
                    System.out.println("The officers have found a " + Prisoners.get(i).contraband +
                        " on " + Prisoners.get(i).name + "!");
                    if (confiscatedItems.containsKey(Prisoners.get(i).contraband) == true) {
                        // Confiscated item already found before, increase count
                        int count = confiscatedItems.get(Prisoners.get(i).contraband);
                        confiscatedItems.put(Prisoners.get(i).contraband, count + 1);
                    } else if (confiscatedItems.containsKey(Prisoners.get(i).contraband) == false) {
                        // Confiscated item not found before, add to HashMap
                        confiscatedItems.put(Prisoners.get(i).contraband, 1);
                    };
                    sleep(1000);
                    System.out.println(Prisoners.get(i).name + " sentence has been increased from " +
                        String.valueOf(Prisoners.get(i).sentence) + " months to " +
                        String.valueOf(Prisoners.get(i).sentence + 3) + " months!");
                    if (Prisoners.get(i).parole.equals("eligible")) {
                        System.out.println(Prisoners.get(i).name + " was previously eligible for parole, that status has been revoked!");
                        Prisoners.get(i).parole = "in-eligible";
                    if (Prisoners.get(i).contraband.equals("grenade") || Prisoners.get(i).contraband.equals("knife") || Prisoners.get(i).contraband.equals("shiv")) {
                        System.out.println("As the confiscated item is a weapon, the prisoner has been moved to isolation for 3 days!");
                        Prisoners.get(i).inIsolation = 1;
                        Prisoners.get(i).isolationLeft = 3;
                    };
                    sleep(1000);
                    Prisoners.get(i).hiding_contraband = "not_hiding";
                    };
                };
            };
        };
        if (contrabandFound == 0) {
            System.out.println("No contraband was found during the search");
            sleep(1000);
        };
        contrabandSearches++;
    };

    public static void calculateFight(String location) {
        Random rand = new Random(); // To simulate randomness of whether there will be a fight
        int fightChance = rand.nextInt(10); // If 0, simulate a fight
        if (fightChance == 0) {
            System.out.println("A fight has broken out in the " + location + "!");
            sleep(1000);
            ArrayList<String> PrisonersInFight = new ArrayList<String>();
            int prisonerFightChance = 0;
            for (int i = 0; i < Prisoners.size(); i++) {
                if (Prisoners.get(i).temper.equals("mild")) {
                    prisonerFightChance = rand.nextInt(4);
                } else if (Prisoners.get(i).temper.equals("non-existant") || Prisoners.get(i).temper.equals("none")) {
                    prisonerFightChance = rand.nextInt(6);
                } else if (Prisoners.get(i).temper.equals("agressive")) {
                    prisonerFightChance = rand.nextInt(3);
                };
                if (prisonerFightChance == 0 && Prisoners.get(i).inIsolation == 0) {
                    Prisoners.get(i).moodChangeToday = 1;
                    if (Prisoners.get(i).mood >= 2) {
                        Prisoners.get(i).mood--;
                    };
                    PrisonersInFight.add(Prisoners.get(i).name);
                    caughtIllegalActivity++;
                };
            };
            if (PrisonersInFight.isEmpty() == false) {
                System.out.println("Prisoner(s) who instigated the fight were: ");
                String instigators = Arrays.toString(PrisonersInFight.toArray()).replace("[", "").replace("]", "");
                System.out.println(instigators);
            } else if (PrisonersInFight.isEmpty() == true) {
                System.out.println("The CO's were unable to find the instigators of the fight");
            };
            sleep(1000);
            totalFights++;
        };
    };

    public static double calculateMood() {
        double total = 0.0;
        double amountofPrisoners = 0.0;
        amountofPrisoners = Prisoners.size();
        for (int i = 0; i < Prisoners.size(); i++) {
            total = total + Prisoners.get(i).mood;
        };
        double avg = total / amountofPrisoners;
        return avg;
    };

    public static void enterToContinue(){
        // User required to press enter to continue
        try {System.in.read();}
        catch (Exception e) {};
    };

    public static void sleep(int pauseTime){
        // Function sleeps program for x amount of milliseconds
        try {Thread.sleep(pauseTime);}
        catch(InterruptedException ex) {Thread.currentThread().interrupt();};
    };
};

// === PRISONER CLASS ===
class Prisoner {
    Random rand = new Random(); // To simulate randomness, used for random attributes
    String name;
    static List<String> intelligenceList = Arrays.asList("low", "medium", "high", "extreme");
    static List<String> temperList = Arrays.asList("non-existant", "mild", "none", "aggressive");
    static List<String> crimeList = Arrays.asList("thievery - light severity", "murder - high severity", "financial - light severity", "assault - medium severity");
    static List<String> paroleList = Arrays.asList("eligible", "in-eligible");
    static List<String> contrabandList = Arrays.asList("knife", "grenade", "shiv", "cell phone", "pack of cigarettes");
    static List<String> hidingContrabandList = Arrays.asList("not_hiding", "hiding");
    static List<String> jobList = Arrays.asList("Kitchen Help", "Cleaning", "Laundry Room Help");
    static List<Integer> jobStatus = Arrays.asList(0, 1);
    String intelligence = intelligenceList.get(rand.nextInt(intelligenceList.size()));
    String temper = temperList.get(rand.nextInt(temperList.size()));
    int sentence = rand.nextInt(48) + 1;
    String crime = crimeList.get(rand.nextInt(crimeList.size()));
    String parole = paroleList.get(rand.nextInt(paroleList.size()));
    String contraband = "";
    String hiding_contraband = hidingContrabandList.get(rand.nextInt(hidingContrabandList.size()));
    int job = jobStatus.get(rand.nextInt(jobStatus.size())); // 0 = True, 1 = False
    String jobTitle;
    String cellBlock; // Cell block A is default, cell block B is for more severe crime and temper
    int inIsolation = 0; // 0 = True, 1 = False
    int isolationLeft = 0; // Days left in isolation
    int moodChangeToday = 0; // 0 = True, 1 = False
    int mood = 3;
    int money = 0;

    public Prisoner(int currentPrisoners) {
        // Constructor, name variable is current amount of prisoners + 1
        name = "Prisoner#" + String.valueOf(currentPrisoners + 1);
        if (hiding_contraband.equals("hiding")) {
            contraband = contrabandList.get(rand.nextInt(contrabandList.size()));
        };
        if (job == 1) {
            jobTitle = jobList.get(rand.nextInt(jobList.size()));
        } else if (job == 0) {
            jobTitle = "None";
        };
        if (crime.contains("light")) {
            cellBlock = "A";
        } else if (crime.contains("medium")) {
            if (temper.equals("aggressive")) {
                cellBlock = "B";
            } else {
                cellBlock = "A";
            };
        } else if (crime.contains("high")) {
            cellBlock = "B";
        };
    };

    public void printPrisonerInformation() {
        // Prints relevant variables in a readable format
        System.out.println("-- " + name + " information:");
        System.out.println("Name: " + name);
        System.out.println("Intelligence: " + intelligence);
        System.out.println("Temper: " + temper);
        System.out.println("Sentence: " + String.valueOf(sentence) + " months remaining");
        System.out.println("Crime: " + crime);
        System.out.println("Cell block: " + cellBlock);
        System.out.println("Parole status: " + parole);
        System.out.println("Contraband not found: " + contraband);
        System.out.println("Mood (on a scale of 1 to 5): " + mood);
        System.out.println("Job: " + jobTitle);
        System.out.println("Money: " + money + "\n");
    };
};
