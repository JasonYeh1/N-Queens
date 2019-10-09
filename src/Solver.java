import java.util.Random;

/**
 * Solver class contains all algorithm logic and houses the code for Simulated Annealing and Genetic Algorithm
 */
public class Solver {

    //Default constructor
    public Solver() {
    }

    //This method takes in a single Individual, an initial temperature, a coolingRate, and a threshold
    public Individual simulatedAnnealing(Individual individual, double temp, double coolingRate, double threshold) {
        Individual currentIndiv = individual;
        double T = temp;

        while(true) {
            //If T has reached beyond the threshold, the time has run out for the algorithm and a failure is returned
            if(T < threshold) {
                System.out.println("T has reached 0");
                return null;
            }
            currentIndiv = findSuccessor(currentIndiv, currentIndiv.findFitness(), T);
            currentIndiv.setFitnessValue(currentIndiv.findFitness());
            if(currentIndiv.findFitness() == 0) {
                return currentIndiv;
            }
            T *= coolingRate;

        }
    }

    //Method to find a random successor to the current Individual
    private Individual findSuccessor(Individual individual, int currentBest, double temp) {
        Individual original = new Individual(individual.getState());
//        System.out.println("______________________________________________________________________________");
//        System.out.println("Initial State: " + individual.toString());
//        System.out.println("Initial State Fitness Value: " + individual.getFitnessValue());
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
//        System.out.println("New State: " +  newChild.toString());

        //Calculate the new state's fitness score and compare with the old score
        newChild.setFitnessValue(newChild.findFitness());
        int fitnessValue = newChild.getFitnessValue();
//        System.out.println("New State Fitness Value: " + fitnessValue);
        //If the new fitness score is lower than the older fitness score return the new Individual
        if(fitnessValue < currentBest) {
//            System.out.println("BETTER ONE FOUND");
            return newChild;
        }
        //If the new fitness score is greater than or equal the the older fitness score we calculate the acceptance probability
//        System.out.println("WORSE ONE FOUND");
        int diff = currentBest - fitnessValue;
//        System.out.println(currentBest + " - " + fitnessValue + " = " + diff);
//        System.out.println("TEMP: " + temp);
        //Acceptance probability is e^(diff/temp)
        double probability = Math.exp(diff/temp);
//        System.out.println("PROBABILITY: " + probability);
        //If the probability is hit, we return the worse child
        if(Math.random() < probability) {
//            System.out.println("PROBABILITY HIT");
            return newChild;
        }

//        System.out.println("SHOULD RETURN ORIGINAL: " + original.toString());
        return original;
    }

    //Method that houses the genetic algorithm logic, takes in a Population of many Individuals
    //This algorithm closely resembles the real life natural selection process and mating
    public Individual geneticAlgorithm(Population initialPopulation) {
        Population population = initialPopulation;
        Population newPopulation = new Population();
        double mutationRate = .99;
        while(true) {

            //Select two parents based on a random or semi-random selection
            Individual parent1 = selectParent(population);
            Individual parent2 = selectParent(population);

            //Have the two parents mate and create children
            Individual[] children = mate(parent1, parent2);

            //For each child, we have a chance to mutate then add it into the population
            for(Individual i: children) {
//                System.out.println("CHILD:        " + i.toString() + " Fitness: " + i.getFitnessValue());

                tryToMutate(i, mutationRate);
                if(i.getFitnessValue() == 0) {
                    return i;
                }
                newPopulation.add(i);
//                System.out.println("ADDED INTO NEW POPULATION");

            }

            //Once the newPopulation reaches the correct size, we replace our old population with our new one
            //and begin selecting parents to mate from the new population. This way our algorithm can converge.
            if(newPopulation.size() == 50) {
                population.setPopulation(newPopulation);
                newPopulation.clear();
//                System.out.println("Switched to new pop");
            }
//            System.out.println("Population Size: " + population.size());
        }
    }

    //This parent selection uses a tournament selection in which a random number of Individuals
    //are chosen and a tournament is held among these subset of Individuals. The Individual
    //with the best fitness score in this subset is returned as the parent
    private Individual selectParent(Population population) {
        Random rand = new Random(System.nanoTime());
        Individual min = population.getIndividual(rand.nextInt(population.size()));
        min.setFitnessValue(min.findFitness());
        int randInt =0;
        Individual node = new Individual();

        for(int i = 1; i< rand.nextInt(population.size()); i++)
        {
            node = new Individual();
            randInt = rand.nextInt(population.size());
            node.setState(population.getIndividual(randInt).getState());
            node.setFitnessValue(population.getIndividual(randInt).findFitness());
            if(node.getFitnessValue()<min.getFitnessValue())
            {
                min = new Individual(node.getState());
                min.setFitnessValue(node.getFitnessValue());
            }
        }

//        System.out.println(min.toString() + " Fitness: " + min.getFitnessValue());
        return min;
    }

    //This method of mating returns two children as it does a double crossover
    private static Individual[] mate(Individual x, Individual y) {
//        System.out.println("");
//        System.out.println("Parent1:  " + x.toString() + " Fitness: " + x.getFitnessValue());
//        System.out.println("Parent2:  " + y.toString() + " Fitness: " + y.getFitnessValue());
        //Cloning the two state arrays for altering
        int[] child1 = x.getState().clone();
        int[] child2 = y.getState().clone();
        int[] temp = new int[25];
        //Select a random crossover point in the state
        int crossOver = (int) ((Math.random() * 24) + 1);
//        System.out.println("Crossover: " + crossOver);

        //Swap the values from parent1 and parent2 to create two children
        System.arraycopy(child1,0, temp,0,crossOver);
        System.arraycopy(child2, 0, child1, 0, crossOver);
        System.arraycopy(temp, 0, child2, 0,crossOver);

        //Calculate the new fitness scores for both children and set it
        x.setState(child1);
        x.setFitnessValue(x.findFitness());
        y.setState(child2);
        y.setFitnessValue(y.getFitnessValue());

        Individual one = new Individual(child1);
        Individual two = new Individual(child2);
        return new Individual[]{one, two};
    }

    //Method to mutate the state given a certain probability
    //Will choose a random Queen and move it to a random position
    private void tryToMutate(Individual individual, double mutationRate) {
        int[] state = individual.getState();
        if(Math.random() < mutationRate) {
            state[(int)(Math.random()*25)] = (int)(Math.random()*25);
            individual.setFitnessValue(individual.findFitness());
//            System.out.println("Mutation hit: " + individual.toString() + " Fitnesss: " + individual.getFitnessValue());
        }
    }

}
