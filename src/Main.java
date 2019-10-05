import java.util.Random;

public class Main {
    public static void main(String args[]) {
        //generate 50 random initial states
        Population initialPopulation = new Population();
        Random rng = new Random();
        int counter = 0;
        for(int k = 0; k < 5; k++) {
            for(int i = 0; i <50; i++) {
                Individual individual = new Individual();
                for(int j = 0; j < individual.size(); j++) {
                    int rowPos = rng.nextInt(8); //generates position from 0-7
                    individual.add(j,rowPos);
                    //System.out.print(rowPos+":");
                }
                //System.out.println("");
                initialPopulation.add(individual);
            }
            //initialPopulation.printString();
            Individual indivForSA = initialPopulation.getIndividual(0);
            //System.out.println(indivForSA.toString());

            Solver solver = new Solver();
            Individual test = solver.simulatedAnnealing(indivForSA,2000,0.99999);
            if(test != null) {
                counter++;
            }
        }
        System.out.println(counter);

//        solver.geneticAlgorithm(initialPopulation);

    }
}