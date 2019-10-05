public class Individual {
    private int[] state = new int[8];
    private int fitnessValue;

    public Individual() {
        state = new int[8];
    }

    public Individual(int[] state) {
        //this.state = new int[8];
        this.state = state.clone();
        fitnessValue = this.findFitness();
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public int[] getState() {
        return state;
    }

    public int getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(int fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public void add(int index, int position) {
        state[index] = position;
    }

    public int size(){
        return state.length;
    }

    public void move(int queen, int position) {
        state[queen] = position;
    }

    @Override
    public String toString() {
        String result = ":";
        for(int i:state) {
            result += i+":";
        }
        return result;
    }

    //TODO negative sloped diagonals are not being counted
    public int findFitness() {
        int conflicts = 0;
        for(int i = 0; i < state.length-1; i++) {
            for(int j = i+1; j < state.length; j++) {
                if(i != j) {
                    if((state[i] == state[j]) || (Math.abs((state[i]+1) - (i+1)) == Math.abs((state[j]+1) - (j+1)) || (state[i] + i == state[j] + j))) {
                        conflicts++;
                    }
                }
            }
        }
        return conflicts;
    }
}
