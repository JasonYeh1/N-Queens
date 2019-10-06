import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

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

    public boolean contains(Individual i) {
        return population.contains(i);
    }

    public void prune() {
        population.sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if(o1.getFitnessValue() < o2.getFitnessValue())
                {
                    return -1;
                }
                else if(o1.getFitnessValue() > o2.getFitnessValue())
                {
                    return 1;
                }
                else
                    return 0;
            }
        });

        this.population = new ArrayList<>(population.subList(0,50));
        size = 50;
    }
}
