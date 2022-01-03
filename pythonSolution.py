# === Modules ===
import random
from time import sleep
import statistics

# === Global variables ===
prisoners = []
contrabandSearches = 0
confiscatedItems = {}
numberofPrisonersCaughtIllegalActivity = 0
dayCount = 0
totalFights = 0

# === Functions ===
def simulatePrison():
    # Welcome & begin simulation
    print('\n===== Welcome to Prison Simulator v 1.0 =====')
    print('-- Created by: Nikolaj Skov Wacher --\n')
    try:
        input('To begin simulating a new prison, press enter: ')
    except:
        pass
    # Create random prisoners
    print('-\nCreating 10 prisoners ...')
    sleep(1)
    for x in range(10):
        createNewPrisoner()
    # Add more prisoners?
    makeMore = True
    while makeMore == True:
        anotherPrisoner = input('Do you want to add another prisoner? (Y/N): ')
        if anotherPrisoner.upper() == 'YES' or anotherPrisoner.upper() == 'Y':
            # Make another prisoner
            print("Randomly generating another prisoner ...")
            #sleep(1)
            createNewPrisoner()
            print("Prisoner added! There are now " + str(len(prisoners)) + " prisoners in the prison")
        elif anotherPrisoner.upper() == 'NO' or anotherPrisoner.upper() == 'N':
            makeMore = False
        else:
            print('Please only type, \"yes\" or \"no\"')
            continue
    # Generate new prison
    print('-\nWe have the prisoners, now we need a prison ...!')
    prisonName = input('What should our prison be called?: ')
    print('Generating prison \"' + str(prisonName) + '\" ...')
    sleep(1)
    print('Setting up walls ...')
    sleep(1)
    print('Creating hallways ...')
    sleep(1)
    print('Adding tables in the cafeteria ...')
    sleep(1)
    print('Double-checking fences ...')
    sleep(1)
    print('Hiring warden and correctional officers (COs) ...')
    sleep(1)
    print('Performing ribbon cutting ceremony ...')
    sleep(1)
    print('Prison generated!\n-')
    sleep(1)
    # Begin simulating days
    print('Ready to begin simulating prison life!')
    print('Overall prisoner and staff mood is currently 3 out of 5')
    try:
        input('To begin simulating days, press enter: ')
    except:
        pass
    global dayCount
    while dayCount < 10:
        try:
            input('-\nBegin next day? Press enter: ')
        except:
            pass
        simulateDay(dayCount)
        dayCount += 1
        continue
    print('-\n10 days have passed in the prison: \"' + prisonName + '\"!')
    try:
        input('Press enter for a full summary of events: ')
    except:
        pass
    print('\n===== SUMMARY =====\n')
    print(str(contrabandSearches) + ' contraband searches were performed')
    sleep(1)
    print('Confiscated items were: ')
    sleep(1)
    for k, v in confiscatedItems.items():
        print(k + ': ' + str(v))
    sleep(1)
    print(str(numberofPrisonersCaughtIllegalActivity) + ' prisoners were caught doing illegal activities')
    sleep(1)
    print('There was a total of ' + str(totalFights) + ' fights')
    mood = calculateMood()
    print('Overall prisoner and staff mood ended at ' + str(mood) + ' on a scale of 1 to 5')
    viewPrisoners = True
    while viewPrisoners == True:
        seePrisoners = input('Do you want to see detailed information on each prisoner before exiting? (Y/N): ')
        if seePrisoners.upper() == 'YES' or seePrisoners.upper() == 'Y':
            print('Printing detailed information ...')
            sleep(2)
            viewPrisoners = False
            printPrisoners()
        elif seePrisoners.upper() == 'NO' or seePrisoners.upper() == 'N':
            viewPrisoners = False
        else:
            print('Please only type, \"yes\" or \"no\"')
            continue
    try:
        input('Exit simulation? (Press enter): ')
    except:
        pass
    print('Exiting prison simulation ...')
    sleep(2)

def simulateDay(dayCount):
    #Schedule: wake up, shower, eat, job (if existant), outside time, eat, recreation, curfew
    # Morning (wake up, shower, eat)
    print('-\nBeginning day ' + str(dayCount+1) + ' ...')
    print('The prisoners have woken up, and are getting ready to leave their cells')
    cellSearch()
    print('The prisoners are going to the showers')
    calculateFight('hallway to the showers')
    calculateFight('showers')
    print('The prisoners are heading to the cafeteria to eat')
    calculateFight('cafeteria')
    # Midday (job, outside time)
    print('Prisoners with jobs are going to work, others are going to their cells')
    simulateJobs()
    searchBeforeRecreation()
    print('The prisoners are heading outside for recreation time')
    calculateFight('yard')
    # Afternoon (eat, recreation)
    print('The prisoners are heading to the cafeteria to eat dinner')
    calculateFight('hallway to the cafeteria')
    calculateFight('cafeteria')
    # Night (curfew)
    print('Following eating dinner, the prisoners are shown back to their cells')
    print('Curfew for the day, the prisoners have gone to bed. Day ' + str(dayCount+1) + ' has passed.')
    # Mood changes
    for p in prisoners:
        if p.moodChangeToday == False:
            if p.mood <= 5:
                p.mood += 1
    return

def createNewPrisoner():
    name = "Prisoner#" + str(len(prisoners)+1)
    prisoners.append(Prisoner(name))

def printPrisoners():
    for p in prisoners:
        p.printPrisonerInformation()
        print('\n')

def simulateJobs():
    for p in prisoners:
        if p.job == True:
            if p.intelligence == 'low':
                p.money += random.randint(1, 2)
            elif p.intelligence == 'medium':
                p.money += random.randint(2, 3)
            elif p.intelligence == 'high':
                p.money += random.randint(3, 4)
            elif p.intelligence == 'extreme':
                p.money += random.randint(5, 7)
    return

def cellSearch():
    global contrabandSearches
    global dayCount
    calculateCellSearch = random.choice([True, False, False, False, False])
    if contrabandSearches < 2 and dayCount > 7:
        calculateCellSearch = True
    if calculateCellSearch == True:
        print('The Warden has called for a random cell search today!')
        print('Correctional officers are searching cells and prisoners ...')
        sleep(2)
        search()
    elif calculateCellSearch == False:
        print('There will be no random search today')
    return

def searchBeforeRecreation():
    global contrabandSearches
    global dayCount
    calculateSearchChance = random.choice([True, False, False, False, False])
    if contrabandSearches < 2 and dayCount > 7:
        calculateSearchChance = True
    if calculateSearchChance == True:
        print('Before heading ouside, the prisoners are searched!')
        print('Correctional officers are searching prisoners ...')
        sleep(2)
        search()
    elif calculateSearchChance == False:
        print('There will be no search today before recreation time')
    return

def search():
    contrabandFound = False
    global numberofPrisonersCaughtIllegalActivity
    global confiscatedItems
    for p in prisoners:
        if p.hiding_contraband == True:
            if p.intelligence == 'low':
                chanceOfFinding = random.randint(0, 2)
            elif p.intelligence == 'medium':
                chanceOfFinding = random.randint(0, 4)
            elif p.intelligence == 'high':
                chanceOfFinding = random.randint(0, 8)
            elif p.intelligence == 'extreme':
                chanceOfFinding = random.randint(0, 16)
            if chanceOfFinding == 0:
                contrabandFound = True
                numberofPrisonersCaughtIllegalActivity += 1
                p.moodChangeToday = True
                if p.mood >= 2:
                    p.mood -= 1
                print('The officers have found a ' + p.contraband + ' on ' + p.name + '!')
                if p.contraband not in confiscatedItems.keys():
                    confiscatedItems[p.contraband] = 1
                elif p.contraband in confiscatedItems.keys():
                    confiscatedItems[p.contraband] += 1
                sleep(1)
                print(p.name + ' sentence has been increased from ' + str(p.sentence_duration) + ' months to ' + str(p.sentence_duration + 3) + ' months!')
                if p.parole_status == 'eligible':
                    print(p.name + ' was previously eligible for parole, that status has been revoked!')
                    p.parole_status = 'in-eligible'
                sleep(1)
                p.hiding_contraband = False
    if contrabandFound == False:
        print('No contraband was found during the search')
        sleep(1)
    global contrabandSearches
    contrabandSearches += 1
    return

def calculateFight(location):
    global totalFights
    global numberofPrisonersCaughtIllegalActivity
    calculateFightChance = random.choice([True, False, False, False, False, False, False, False, False, False])
    if calculateFightChance == True:
        print('A fight has broken out in the ' + location + '!')
        sleep(1)
        prisonersInFight = []
        for p in prisoners:
            if p.temper == 'mild':
                chanceOfFighting = random.randint(0, 2)
            elif p.temper == 'non-existant' or 'none':
                chanceOfFighting = random.randint(0, 3)
            elif p.temper == 'aggressive':
                chanceOfFighting = random.randint(0, 1)
            if chanceOfFighting == 0:
                p.moodChangeToday = True
                if p.mood >= 1:
                    p.mood -= 1
                prisonersInFight.append(p.name)
                numberofPrisonersCaughtIllegalActivity += 1
        print('Prisoner(s) who instigated the fight are: ')
        print(', '.join(prisonersInFight))
        sleep(1)
        totalFights += 1
    return

def calculateMood():
    moodCount = []
    for p in prisoners:
        moodCount.append(p.mood)
    avgMood = statistics.mean(moodCount)
    return round(avgMood, 1)

# === Classes ===
class Prisoner:
    def __init__(self, name):
        self.name = name
        self.intelligence = random.choice(['low', 'medium', 'high', 'extreme'])
        self.temper = random.choice(['non-existant', 'mild', 'none', 'aggressive'])
        self.food_pref = random.choice(['vegan', 'vegetarian', 'none'])
        self.sentence_duration = random.randint(1, 48)
        self.crime = random.choice(['thievery', 'murder', 'financial', 'assault'])
        self.crime_severity = random.choice(['low', 'medium', 'high'])
        self.parole_status = random.choice(['eligible', 'in-eligible'])
        self.contraband = ''
        self.hiding_contraband = random.choice([False, True])
        if self.hiding_contraband == True:
            self.contraband = random.choice(['knife', 'grenade', 'shiv', 'cell phone', 'pack of cigarettes'])
        self.job = random.choice([False, True])
        self.moodChangeToday = False
        self.mood = 3 # From 1 (very low) to 5 (very high)
        self.money = 0

    def printPrisonerInformation(self):
        print('-- ' + self.name + ' information:')
        for k, v in self.__dict__.items():
            print(str(k) + ': ' + str(v))
        return

# === START PROGRAM ===
if __name__ == "__main__":
    # If this file is the main file, run main function
    simulatePrison()
