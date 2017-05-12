package AINT255;

import controllers.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import simplerace.BasicTrack;
import simplerace.Controller;
import simplerace.Evaluator;
import simplerace.Evolvable;
import wox.serial.Easy;

public class AINT255Evolutionary {

    private int populationSize;

    // number of seperate times an agent is placed in simulator
    // fitness of the agent is the average score obtained 
    private int evaluationRepetitions = 5;

    private int numberGenerations;

    private double mutationMagnitude;

    private double crossOverProbability;

    // private AINT255MLPController[] populationOLD;
    private ArrayList<AINT255MLPController> population;

    //   private double[] fitness;
    private Controller opponentController;

    private StatsCollector statsCollector;

    private Random random;

    public AINT255Evolutionary() {

        statsCollector = new StatsCollector();

        random = new Random();

        configurePopulation();
    }

    public void configurePopulation() {

        int numInputs;
        int numhiddenNodes;

        AINT255MLPController newIndividual;

        populationSize = 100;

        mutationMagnitude = 0.1;

        crossOverProbability = 0.7;

        population = new ArrayList<>();

        // number of inputs depends on the measures used
        // but always add one for the bias
        numInputs = 1;

        // this can be experimentally varied 
        numhiddenNodes = 5;

        for (int i = 0; i < populationSize; i++) {

            newIndividual = new AINT255MLPController(numInputs, numhiddenNodes, random);

            // options are:  AINT255MLP.TANH  or AINT255MLP.SIGMOID
            newIndividual.setActivationFunctionType(AINT255MLP.TANH);

            newIndividual.setMutationMagnitude(mutationMagnitude);

            population.add(newIndividual);
        }
    }

    public void runEvolution() {

        // number of seperate times an agent is placed in simulator
        // fitness of the agent is the average score obtained 
        numberGenerations = 10;
        evaluationRepetitions = 5;

        //----------------------------------------------------------
        // optionally can also evolve against a prescripted controller
        // options are: 
        //          null i.e. no opponent
        //          new HeuristicSensibleController()
        //          new HeuristicCombinedController()
        //          new HeuristicAggressiveController()
        //          new GreedyController()
        opponentController = new HeuristicAggressiveController();

        evaluatePopulation();

        for (int i = 1; i <= numberGenerations; i++) {

            System.out.println("Starting generation " + i);

            selectIndividualsRWS(population);

            crossoverIndividuals();

            mutatePopulation();

            evaluatePopulation();

            // save stats at each generation, which will later be written to file
            statsCollector.collectStats(population);

            statsCollector.saveBestIndividualSoFar(population);
        }
        System.out.println(" Evolution Finished ");
    }

    // =============================================
    // methods to be completed / modified
    private int selectIndividualsRWS(ArrayList<AINT255MLPController> individuals) {

        // empty method as placeholder for a selection method 
        int total = 0;
        int index = 0;
        
        double spinValue = getSpinValue(individuals);
 
        while (total < spinValue)
        {
            total += individuals.get(index).getFitness();
            index++;
        }
        
        if (index > 0)
        {
            index--;
        }
        
        return index;
        
    }
    
    private double getSpinValue(ArrayList<AINT255MLPController> individuals)
    {
        Random rand = new Random();
        double spinValue = 0;
        double sumFitness = 0;
        
        for (int i = 0; i < individuals.size(); i++)
        {
            sumFitness += individuals.get(i).getFitness();
        }
        
        spinValue = rand.nextInt() * sumFitness;
        
        return spinValue;
    }

    private void crossoverIndividuals() {

        // empty method as placeholder for 
        // a crossover (aka recombination) 
    }

    private void mutatePopulation() {

        for (AINT255MLPController individual : population) {
            // mutate the individual using individual's mutationMagnitude
            individual.mutate();

            // optionally set a mutation magnitude
            //  individual.mutate(mutationMagnitude);
        }

    }
    // =============================================

    private void evaluatePopulation() {

        for (AINT255MLPController controller : population) {

            if (opponentController == null) {

                evaluateSolo(controller);

            } else {

                evaluateDuo(controller);
            }
        }
    }

    public void saveEvolutionStats(String fileName) {

        if (statsCollector != null) {

            statsCollector.saveStats(fileName);
        }
    }

    //--------------------------------------------------------------------------
    // evaluation methods
    // do not modify
    private void evaluateSolo(AINT255MLPController individual) {

        double evolvedControllerScore;

        evolvedControllerScore = 0;

        for (int i = 0; i < evaluationRepetitions; i++) {

            individual.reset();

            evolvedControllerScore += Evaluator.evaluateSolo((Controller) individual, new BasicTrack());
        }

        // The finess of an individual is the average score obtained for the number of evaluations
        // return evolvedControllerScore / evaluationRepetitions;
        individual.setFitness(evolvedControllerScore / evaluationRepetitions);
    }

    private void evaluateDuo(AINT255MLPController individual) {

        double[] bothScores;

        double evolvedControllerScore;
        double opponentScore; // not realy used, but here for completness

        evolvedControllerScore = 0;

        opponentScore = 0;

        for (int i = 0; i < evaluationRepetitions; i++) {

            individual.reset();

            bothScores = Evaluator.evaluateDuo((Controller) individual, opponentController,
                    new BasicTrack(), false);

            evolvedControllerScore += bothScores[0];
            opponentScore += bothScores[1];
        }

        individual.setFitness(evolvedControllerScore / evaluationRepetitions);

    }

}  // end class
