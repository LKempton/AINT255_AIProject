package AINT255;

import java.util.Random;

public class AINT255MLP {

    private double[][] inputToHiddenWeights;

    private double[][] hiddenToOutputWeights;
    private double[] hiddenNodes;
    private double[] inputNodes;
    private double[] outputNodes;
    
    protected double mutationMagnitude = 0.1;

    private Random random;

    final static int SIGMOID = 0;
    final static int TANH = 1;

    private int activationFunctionType = TANH;

    public AINT255MLP(int numberOfInputs, int numberOfHidden, int numberOfOutputs, Random random) {
        inputNodes = new double[numberOfInputs];
        inputToHiddenWeights = new double[numberOfInputs][numberOfHidden];
        hiddenToOutputWeights = new double[numberOfHidden][numberOfOutputs];
        hiddenNodes = new double[numberOfHidden];
        outputNodes = new double[numberOfOutputs];

        this.random = random;
        initialiseWeights();
    }

    public AINT255MLP(double[][] firstConnectionLayer, double[][] secondConnectionLayer, int numberOfHidden,
            int numberOfOutputs, Random random) {
        inputNodes = new double[firstConnectionLayer.length];
        inputToHiddenWeights = firstConnectionLayer;
        hiddenToOutputWeights = secondConnectionLayer;
        hiddenNodes = new double[numberOfHidden];
        outputNodes = new double[numberOfOutputs];

        this.random = random;
        
        initialiseWeights();
    }

    /**
     * Copy constructor: Creates a new class with same values as parameter
     *
     * @param mlp : the object to copy
     */
    public AINT255MLP(AINT255MLP mlp) {
        inputToHiddenWeights = copy(mlp.inputToHiddenWeights);
        hiddenToOutputWeights = copy(mlp.hiddenToOutputWeights);

        mutationMagnitude = mlp.mutationMagnitude;
        
        inputNodes = new double[mlp.getNumberInputNodes()];
        hiddenNodes = new double[mlp.getNumberHiddenNodes()];
        outputNodes = new double[mlp.getNumberOuputNodes()];
        
        random = mlp.random;

    }

    // ====================================
    // methods to be completed
    /**
     * Assuming this is parent 1, method performs crossover on the weights
     * between input to hidden and hidden to output
     *
     * @param parent2
     */
    public AINT255MLP crossOver(AINT255MLP parent2) {
     
        int ihCutPointI = getRand(1, inputToHiddenWeights.length);
        int ihCutPointJ = getRand(1, inputToHiddenWeights[0].length);
        
        int hoCutPointI = getRand(1, hiddenToOutputWeights.length);
        int hoCutPointJ = getRand(1, hiddenToOutputWeights[0].length);
        
        for (int i = 0; i < ihCutPointI; i++)
        {
            for (int j = 0; j < ihCutPointJ; j++)
            {
                this.inputToHiddenWeights[i][j] = parent2.inputToHiddenWeights[i][j];
            }
        }
        
        for (int i = 0; i < hoCutPointI; i++)
        {
            for (int j = 0; j < hoCutPointJ; j++)
            {
                this.hiddenToOutputWeights[i][j] = parent2.hiddenToOutputWeights[i][j];
            }
        }
        
        return this;
    }
    
    private int getRand(int min, int max)
    {
        Random rand = new Random();
        int result = rand.nextInt(max - min + 1) + min;
        
        return result;
    }

    /**
     * Generic method to mutate the values of the array passed as parameter
     * @param array 
     */
    protected void mutate(double[] array) {
        
        Random rand = new Random();
        Random noise = new Random();
        double rngProbability;
        
        for (int i = 0; i < array.length; i++)
        {
            rngProbability = rand.nextDouble();
            if (rngProbability < mutationMagnitude)
            {
                array[i] = array[i] * noise.nextGaussian();
            }
        }

    }

    // ==================================================
    private void initialiseWeights() {

        initialise(inputToHiddenWeights);
        initialise(hiddenToOutputWeights);
    }
/**
 * Initialise values of the elements in the array 
 * passed as parameter by  assigning random 
 * values in range -1...1
 * @param array 
 */
    private void initialise(double[][] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = (random.nextDouble() * 2) - 1;
            }
        }
    }

    public double[] propagate(double[] inputIn) {
        if (inputNodes == null) {
            inputNodes = new double[inputIn.length];
        }
        if (inputNodes != inputIn) {
            if (inputIn.length > inputNodes.length) {
                System.out.println("MLP given " + inputIn.length + " inputs, but only intialized for "
                        + inputNodes.length);
            }
            System.arraycopy(inputIn, 0, this.inputNodes, 0, inputIn.length);
        }
        if (inputIn.length < inputNodes.length) {
            System.out.println("NOTE: only " + inputIn.length + " inputs out of " + inputNodes.length + " are used in the network");
        }

        clear(hiddenNodes);
        clear(outputNodes);

        propagateOneStep(inputNodes, hiddenNodes, inputToHiddenWeights);

        applyActivationFunction(hiddenNodes);

        propagateOneStep(hiddenNodes, outputNodes, hiddenToOutputWeights);

        applyActivationFunction(outputNodes);

        return outputNodes;
    }

    public AINT255MLP copy() {
        return new AINT255MLP(copy(inputToHiddenWeights), copy(hiddenToOutputWeights),
                hiddenNodes.length, outputNodes.length, random);
    }

    protected void mutate(double[][] array) {
        for (int i = 0; i < array.length; i++) {
            mutate(array[i]);
        }
    }

    public void mutateWeights() {
        mutate(inputToHiddenWeights);
        mutate(hiddenToOutputWeights);
    }

    protected void applyActivationFunction(double[] nodes) {

        if (activationFunctionType == AINT255MLP.SIGMOID) {
            sigmoid(nodes);

        } else if (activationFunctionType == AINT255MLP.TANH) {
            tanh(nodes);
        }
    }

    public void reset() {
        // not used, but needed to comply with other classes
    }

    private double[][] copy(double[][] original) {
        double[][] copy = new double[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    protected void propagateOneStep(double[] fromLayer, double[] toLayer, double[][] connections) {
        for (int from = 0; from < fromLayer.length; from++) {
            for (int to = 0; to < toLayer.length; to++) {
                toLayer[to] += fromLayer[from] * connections[from][to];
            }
        }
    }

    protected void clear(double[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    protected void tanh(double[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = Math.tanh(nodes[i]);
        }
    }

    protected void sigmoid(double[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = 1.0 / (1.0 + Math.exp(-nodes[i]));
        }
    }

    public void writeMLP() {

        System.out.println("----------------------------------------\n");

        for (int i = 0; i < inputToHiddenWeights.length; i++) {

            System.out.print("|");

            for (int j = 0; j < inputToHiddenWeights[i].length; j++) {
                System.out.print(" " + inputToHiddenWeights[i][j]);
            }

            System.out.print(" |\n");
        }

        System.out.println("----------------------------------------\n");

        for (int i = 0; i < hiddenToOutputWeights.length; i++) {

            System.out.print("|");

            for (int j = 0; j < hiddenToOutputWeights[i].length; j++) {
                System.out.print(" " + hiddenToOutputWeights[i][j]);
            }
            System.out.print(" |\n");
        }

        System.out.println("----------------------------------------\n");

    }

    public double[][] getInputToHiddenWeights() {
        return inputToHiddenWeights;
    }

    public double[][] getHiddenToOutputWeights() {
        return hiddenToOutputWeights;
    }

    public int getNumberInputNodes() {
        return inputNodes == null ? 0 : inputNodes.length;
    }

    public int getNumberHiddenNodes() {
        return hiddenNodes == null ? 0 : hiddenNodes.length;
    }

    public int getNumberOuputNodes() {
        return outputNodes == null ? 0 : outputNodes.length;
    }

    public int getActivationFunctionType() {
        return activationFunctionType;
    }

    public void setActivationFunctionType(int activationFunctionType) {
        this.activationFunctionType = activationFunctionType;
    }

    public double getMutationMagnitude() {
        return mutationMagnitude;
    }

    public void setMutationMagnitude(double mutationMagnitude) {
        this.mutationMagnitude = mutationMagnitude;
    }

    public String toString() {
        return "MLP:" + inputToHiddenWeights.length + "/" + hiddenToOutputWeights.length + "/" + outputNodes.length;
    }
}
