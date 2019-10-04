public class Node {
    private int[] state;
    private String stateAsString;

    public Node(int[] state) {
        this.state = state;
    }

    public void setStateAsString(String stateAsString) {
        this.stateAsString = stateAsString;
    }

    public String getStateAsString() {
        return stateAsString;
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public int[] getState() {
        return state;
    }
}
