import java.util.Random;

public class Main {
    public static long saTime = 0;
    public static long gTime = 0 ;
    public static void main(String args[]) {
        final double numberOfRuns = 500;
        Population initialPopulation = new Population();
        Random rng = new Random();
        int saCounter = 0;
        int gCounter = 0;
        for(int k = 0; k < numberOfRuns; k++) {
            for(int i = 0; i <50; i++) {
                Individual individual = new Individual();
                for(int j = 0; j < individual.size(); j++) {
                    int rowPos = rng.nextInt(25);
                    individual.add(j,rowPos);
                }
                individual.setFitnessValue(individual.findFitness());
                //System.out.println(individual.toString() + " Fitness: " + individual.getFitnessValue());
                initialPopulation.add(individual);
            }
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
                System.out.println("Genetic solution: " + solution2.toString());
            }

            long gEndTime = System.currentTimeMillis();
            gTime += gEndTime - gStartTime;


        }
        System.out.println("");
        System.out.println("Simulated Annealing Time: " + saTime*.001 + "s");
        System.out.println("Simulated Annealing Solution Rate: " + saCounter/numberOfRuns);
        System.out.println("");
        System.out.println("Genetic Algorithm Time: " + gTime*.001 + "s");
        System.out.println("Genetic Algorithm Solution Rate: " + gCounter/numberOfRuns);
    }
}