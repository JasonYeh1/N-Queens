/**
 * Solver class contains both algorithms. Takes in a populations of Nodes and applies the two algorithms
 */
public class Solver {

    //Default constructor
    public Solver() {
    }

    public Individual simulatedAnnealing(Individual individual, double temp, double coolingRate) {
        Individual currentIndiv = individual;
        double T = temp;

        while(true) {
            if(T < .001) {
                System.out.println("T has reached 0");
                return null;
            }
            currentIndiv = findSuccessor(currentIndiv, currentIndiv.findFitness(), T);
            if(currentIndiv.findFitness() == 0) {
                System.out.println("Solution Found");
                return currentIndiv;
            }
            T *= coolingRate;

        }
        //Choose the fittest individual in the population to start.
        //return currentIndiv;
    }

    private Individual findSuccessor(Individual individual, int currentBest, double temp) {
        Individual original = new Individual(individual.getState());
        System.out.println("______________________________________________________________________________");
        System.out.println("Initial State: " + individual.toString());
        System.out.println("Initial State Fitness Value: " + individual.findFitness());
        Individual newChild = new Individual(individual.getState());
        int size = individual.size();

        //Choose random queen, and choose the index it is moved to
        int queen = (int)(Math.random() * size);
        int originalPosition = individual.getState()[queen];
        int position = -1;
        do {
            position = (int)(Math.random() * size);
        } while(position == originalPosition);

        newChild.move(queen,position);
        System.out.println("New State: " +  newChild.toString());

        int fitnessValue = newChild.findFitness();
        System.out.println("New State Fitness Value: " + fitnessValue);
        if(fitnessValue < currentBest) {
            System.out.println("BETTER ONE FOUND");
            return newChild;
        }
        System.out.println("WORSE ONE FOUND");
        int diff = currentBest - fitnessValue;
        System.out.println(currentBest + " - " + fitnessValue + " = " + diff);
        System.out.println("TEMP: " + temp);
        double probability = Math.exp(diff/temp);
        System.out.println("PROBABILITY: " + probability);
        if(Math.random() < probability) {
            System.out.println("PROBABILITY HIT");
            return newChild;
        }

        System.out.println("SHOULD RETURN ORIGINAL: " + original.toString());
        return original;
    }
//
//    public Individual geneticAlgorithm(Population population) {
//
//    }

}
