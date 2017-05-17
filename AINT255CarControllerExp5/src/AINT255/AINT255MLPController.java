package AINT255;

import java.util.Random;
import simplerace.Controller;
import simplerace.Evolvable;

public class AINT255MLPController implements Controller, Evolvable {

    public AINT255MLP mlp;

    private double fitness;

    // there are always 2 output nodes
    private final int numberOutputNodes = 2;

    public AINT255MLPController() {

        // assume some default values when using default constructor
        mlp = new AINT255MLP(1, 6, numberOutputNodes, new Random());
    }

    public AINT255MLPController(int numInputs, int numHidden, Random random) {
        mlp = new AINT255MLP(numInputs, numHidden, numberOutputNodes, random);
    }

    /**
     * Copy constructor: Creates a new class with same values as parameter
     *
     * @param controller : the object to copy
     */
    public AINT255MLPController(AINT255MLPController controller) {
        mlp = new AINT255MLP(controller.mlp);
        fitness = controller.fitness;
    }

    private AINT255MLPController(AINT255MLP mlp) {
        this.mlp = mlp;
    }

    public int control(SensorModel model) {

        // get the current sensor values
        double[] inputs = readSesors(model);

        // propagte those values to get an output
        double[] output = mlp.propagate(inputs);

        // now determine the actual steering and driving values
        // will need to modify this depending on the activation function used
        int driving;

        if (output[0] < -0.3) {
            driving = 0;
        } else if (output[0] > 0.3) {
            driving = 2;
        } else {
            driving = 1;
        }

        int steering;
        
        if (output[1] < -0.3) {
            steering = 0;
        } else if (output[1] > 0.3) {
            steering = 2;
        } else {
            steering = 1;
        }

        // suggest do not change this
        return driving * 3 + steering;
    }

    // ====================================
    // method to be modified
    private double[] readSesors(SensorModel model) {

        double sensors[] = new double[mlp.getNumberInputNodes()];

        // first input is the bias do not chnage this
        sensors[0] = 1.0;
        sensors[1] = model.getSpeed();
        sensors[2] = model.getDistanceToNextWaypoint();
        sensors[3] = model.getAngleToNextWaypoint();
        //sensors[4] = model.getDistanceToOtherVehicle();
        //sensors[5] = model.getAngleToOtherVehicle();

   
        // add more inputs
        
        return sensors;
    }

    public void setMutationMagnitude(double mutationMagnitude) {
        mlp.setMutationMagnitude(mutationMagnitude);
    }

    public void mutate(double mutationMagnitude) {
        mlp.setMutationMagnitude(mutationMagnitude);
        mlp.mutateWeights();
    }

    public void mutate() {
        mlp.mutateWeights();
    }

    public void setActivationFunctionType(int type) {
        mlp.setActivationFunctionType(type);
    }

    public AINT255MLP crossOver(AINT255MLP parent2) {
        return mlp.crossOver(parent2);
    }

    @Override
    public Evolvable copy() {
        return new AINT255MLPController(mlp.copy());
    }

    @Override
    public void reset() {
        // Not needed here, as we have a reactive controller
    }

    public int getNumberInputNodes() {
        return mlp.getNumberInputNodes();
    }

    public int getNumberHiddenNodes() {
        return mlp.getNumberHiddenNodes();
    }

    public int getNumberOuputNodes() {
        return mlp.getNumberOuputNodes();
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
