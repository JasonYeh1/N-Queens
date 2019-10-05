import java.util.ArrayList;

public class Population extends ArrayList<int[]> {
    private ArrayList<Individual> population;
    private int size;

    public Population() {
        population = new ArrayList<>();
    }

    public void add(Individual individual) {
        size++;
        population.add(individual);
    }

    public Individual getIndividual(int index) {
        return population.get(index);
    }

    public void printString() {
        for(Individual individual:population) {
            System.out.print("(");
            System.out.print(individual.toString());
            System.out.print(")");
        }
    }

    @Override
    public int size() {
        return size;
    }
}
