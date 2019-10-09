import java.util.ArrayList;

/**
 * Population class holds a number of Individuals
 * The purpose of this class is to follow Object-Oriented design
 * I could have easily just used an ordinary ArrayList object to represent the Population
 */
public class Population extends ArrayList<int[]> {

    //ArrayList of Individuals that represents the current population
    private ArrayList<Individual> population;

    //Default Constructor
    public Population() {
        population = new ArrayList<>();
    }

    //add method that adds to the population ArrayList
    public void add(Individual individual) {
        population.add(individual);
    }

    //Get method to retrieve an instance of an Individuals given its index as a parameter
    public Individual getIndividual(int index) {
        return population.get(index);
    }

    //Method to print all individuals within the population
    public void printString() {
        for(Individual individual:population) {
            System.out.print("(");
            System.out.print(individual.toString());
            System.out.print(")" + "  Fitness: " + individual.getFitnessValue());
            System.out.println("");
        }
    }

    //Overrided method to return the size of the population
    @Override
    public int size() {
        return population.size();
    }

    //Overrided method to clear the population
    @Override
    public void clear() {
        population.clear();
    }

    //Setter method that shallow copies a Population's population ArrayList and replaces this instance with it
    public void setPopulation(Population population) {
        ArrayList<Individual> shallow = new ArrayList<Individual>(population.getPopulation());
        this.population = shallow;
    }

    //Getter method that retrieves the population ArrayList
    public ArrayList<Individual> getPopulation() {
        return population;
    }
}
