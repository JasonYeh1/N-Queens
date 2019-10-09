import java.util.Random;

/**
 * Main class consisting of entry point into the program, the number of runs can be adjusted with the numberOfRuns value
 */
public class Main {
    //Global variables to keep track of the run times for both Simulated Annealing and Genetic
    private static long saTime = 0;
    private static long gTime = 0 ;
    public static void main(String args[]) {
        final double numberOfRuns = 500;
        Random rng = new Random();
        int saCounter = 0;
        int gCounter = 0;
        System.out.println("Number of Runs: " + numberOfRuns);
        System.out.println("");

        //For the numberOfRuns stated above, create that many Populations
        for(int k = 0; k < numberOfRuns; k++) {
            Population initialPopulation = new Population();
            //Create 50 random Individuals to populate the Population
            for(int i = 0; i <50; i++) {
                Individual individual = new Individual();
                //For each queen, select a random row position for it
                for(int j = 0; j < individual.size(); j++) {
                    int rowPos = rng.nextInt(25);
                    individual.add(j,rowPos);
                }
                individual.setFitnessValue(individual.findFitness());
//                System.out.println(individual.toString() + " Fitness: " + individual.getFitnessValue());
                initialPopulation.add(individual);
            }
            //System.out.println("--------------------------------------------------------------------------------------------------");
            Individual indivForSA = initialPopulation.getIndividual(0);

            Solver solver = new Solver();
            long saStartTime = System.currentTimeMillis();
            Individual solution1 = solver.simulatedAnnealing(indivForSA,1,0.999,.0000000001);
            if(solution1 != null) {
                System.out.println("Simulated Annealing solution: " + solution1.toString());
                saCounter++;
            }
            long saEndTime = System.currentTimeMillis();
            saTime += saEndTime - saStartTime;

            long gStartTime = System.currentTimeMillis();
            Individual solution2 = solver.geneticAlgorithm(initialPopulation);
            if(solution2 != null) {
                gCounter++;
                System.out.println("Genetic solution:-------------" + solution2.toString());
            }

            long gEndTime = System.currentTimeMillis();
            gTime += gEndTime - gStartTime;


        }
        System.out.println("");
        System.out.println("Simulated Annealing Total Time: " + saTime*.001 + "s");
        System.out.println("Simulated Annealing Solution Rate: " + saCounter/numberOfRuns);
        System.out.println("");
        System.out.println("Genetic Algorithm Total Time: " + gTime*.001 + "s");
        System.out.println("Genetic Algorithm Solution Rate: " + gCounter/numberOfRuns);
    }
}