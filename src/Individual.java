/**
 * Individual class that represents a single instance of an N-Queens problem
 * The state is represented with a 1-D array of size 25.
 * On creation of an individual, a fitness score is calculated and assigned
 */
public class Individual {
    private int[] state = new int[8];
    private int fitnessValue;

    //Default constructor
    public Individual() {
        state = new int[25];
    }

    //Constructor that takes in a int[] state and shallow copies it to this instance's state
    public Individual(int[] state) {
        this.state = state.clone();
        fitnessValue = this.findFitness();
    }

    //Setter method to set the state of an Individual
    public void setState(int[] state) {
        this.state = state;
    }

    //Getter method to get the state array of this Individual
    public int[] getState() {
        return state;
    }

    //Getter method that retrieves the fitness score of this Individual
    public int getFitnessValue() {
        return fitnessValue;
    }

    //Setter method that sets the fitness value
    public void setFitnessValue(int fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    //Method that adds an integer to a position within the state array
    public void add(int index, int position) {
        state[index] = position;
    }

    //Method that returns the size of the state array
    public int size(){
        return state.length;
    }

    //Method that moves a queen to a position by updating its integer value at that index
    public void move(int queen, int position) {
        state[queen] = position;
    }

    //Overrided method that converts the Individual's state to an printable String
    @Override
    public String toString() {
        String result = "(";
        for(int i = 0; i < state.length; i++) {
            result += state[i];
            if(i != state.length-1) {
                result += ", ";
            }
        }
        result += ")";
        return result;
    }

    //Method that calculates the fitness score of this individual
    public int findFitness() {
        int conflicts = 0;
        for(int i = 0; i < state.length-1; i++) {
            for(int j = i+1; j < state.length; j++) {
                //Fitness score is calculated by checking horizontal and diagonal conflicts
                //If the two positions equal each other in int value, then there is a horizontal conflict
                //If the two positions difference in values equal to the difference in indexes, a diagonal conflict exists
                //The smaller the value, the better the fitness
                if(i != j) {
                    if((state[i] == state[j]) || (j - i == Math.abs(state[j] - state[i]))) {
                        conflicts++;
                    }
                }
            }
        }
        return conflicts;
    }

    //Overrided method to help contains() method find the correct Individual
    @Override
    public boolean equals(Object obj) {
        Individual i = (Individual) obj;
        if(!(obj instanceof Individual)) {
            return false;
        }
        return this.toString().equals(i.toString());
    }
}
