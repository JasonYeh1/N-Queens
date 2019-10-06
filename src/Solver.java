import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Solver class contains both algorithms. Takes in a populations of Nodes and applies the two algorithms
 */
public class Solver {

    //Default constructor
    public Solver() {
    }

    public Individual simulatedAnnealing(Individual individual, double temp, double coolingRate, double threshold) {

        Individual currentIndiv = individual;
        double T = temp;

        while(true) {
            if(T < threshold) {
                System.out.println("T has reached 0");
                long endTime = System.nanoTime();
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

        newChild.setFitnessValue(newChild.findFitness());
        int fitnessValue = newChild.getFitnessValue();
//        System.out.println("New State Fitness Value: " + fitnessValue);
        if(fitnessValue < currentBest) {
//            System.out.println("BETTER ONE FOUND");
            return newChild;
        }
//        System.out.println("WORSE ONE FOUND");
        int diff = currentBest - fitnessValue;
//        System.out.println(currentBest + " - " + fitnessValue + " = " + diff);
//        System.out.println("TEMP: " + temp);
        double probability = Math.exp(diff/temp);
//        System.out.println("PROBABILITY: " + probability);
        if(Math.random() < probability) {
//            System.out.println("PROBABILITY HIT");
            return newChild;
        }

//        System.out.println("SHOULD RETURN ORIGINAL: " + original.toString());
        return original;
    }

    public Individual geneticAlgorithm(Population initialPopulation) {
        Population population = initialPopulation;
        double mutationRate = .99;
        int counter = 0;
        while(true) {
            Individual parent1 = selectParent(population);
            Individual parent2 = selectParent(population);
            Individual[] children = mate(parent1, parent2);

            for(Individual i: children) {
//                System.out.println("CHILD:        " + i.toString());
                if(i.getFitnessValue() == 0) {
                    return i;
                }
                tryToMutate(i, mutationRate);

                if(!population.contains(i)) {
                    population.add(i);
//                    System.out.println("ADDED INTO POPULATION");
                }
            }
            population.prune();
//            System.out.println("Population Size: " + population.size());
            counter++;
        }
    }

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

        //System.out.println(min.toString() + " Fitness: " + min.getFitnessValue());
        return min;
    }

    private static Individual[] mate(Individual x, Individual y) {
//        System.out.println("");
//        System.out.println("Parent1:  " + x.toString() + " Fitness: " + x.getFitnessValue());
//        System.out.println("Parent2:  " + y.toString() + " Fitness: " + y.getFitnessValue());
        int[] child1 = x.getState().clone();
        int[] child2 = y.getState().clone();
        int[] temp = new int[25];
        int crossOver = (int) ((Math.random() * 24) + 1);
//        System.out.println("Crossover: " + crossOver);


        System.arraycopy(child1,0, temp,0,crossOver);
        System.arraycopy(child2, 0, child1, 0, crossOver);
        System.arraycopy(temp, 0, child2, 0,crossOver);

//        x.setState(child1);
//        x.setFitnessValue(x.findFitness());
//        y.setState(child2);
//        y.setFitnessValue(y.getFitnessValue());

        Individual one = new Individual(child1);
        Individual two = new Individual(child2);
        return new Individual[]{one, two};
    }

    private void tryToMutate(Individual individual, double mutationRate) {
        int[] state = individual.getState();
        if(Math.random() < mutationRate) {
            state[(int)(Math.random()*25)] = (int)(Math.random()*25)+1;
//            System.out.println("Mutation hit: " + individual.toString());
        }
    }

}
