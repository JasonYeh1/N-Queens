import java.util.ArrayList;
import java.util.Comparator;

public class Population extends ArrayList<int[]> {
    private ArrayList<Individual> population;

    public Population() {
        population = new ArrayList<>();
    }

    public void add(Individual individual) {
        population.add(individual);
    }

    public Individual getIndividual(int index) {
        return population.get(index);
    }

    public void printString() {
        for(Individual individual:population) {
            System.out.print("(");
            System.out.print(individual.toString());
            System.out.print(")" + "  Fitness: " + individual.getFitnessValue());
            System.out.println("");
        }
    }

    @Override
    public int size() {
        return population.size();
    }

    @Override
    public void clear() {
        population.clear();
    }

    public void setPopulation(Population population) {
        ArrayList<Individual> shallow = new ArrayList<Individual>(population.getPopulation());
        this.population = shallow;
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public boolean contains(Individual i) {
        return population.contains(i);
    }

}
