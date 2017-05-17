package AINT255;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import wox.serial.Easy;


public class StatsCollector {
    /**
     * list to hold the average fitness across a generation
     */
    private ArrayList<Double> averageFitness;
    
    private double bestFitness;

     private String AINT255EvolvedControllerFileName = "AINT255Evolved.xml";

    public StatsCollector() {
        averageFitness = new ArrayList<Double>();
        
        // assign the lagest negative value possible
        bestFitness= -Double.MAX_VALUE;
    }


    public void collectStats(ArrayList<AINT255MLPController> population ) {
        
        double average = 0.0;
        
        for (AINT255MLPController individual : population) {
        
            average += individual.getFitness();
        }

        average = average / population.size();

        averageFitness.add(average);
    }

    public void saveBestIndividualSoFar(ArrayList<AINT255MLPController> population ) {
        

        // need to add / ammend code here to accuratly find the best indvidual so far
        // (so far the first individual is taken as the best.. this is not likely to be the case)
        int bestIndex = FindBestIndex(population);

        if (bestFitness < population.get(bestIndex).getFitness()) {
    
                AINT255MLPController bestIndividual = population.get(bestIndex);

                // this saves an .xml copy of bestIndividual
                Easy.save(bestIndividual, AINT255EvolvedControllerFileName);
                
               bestFitness = population.get(bestIndex).getFitness();
               
               System.out.printf("Fitness of best individual %.4f\n", bestFitness);
            }
        }
    
    private int FindBestIndex(ArrayList<AINT255MLPController> population)
    {
        int best = 0;
        double bestFitness = 0;
        
        for (int i = 0; i < population.size(); i++)
        {
            if (bestFitness < population.get(i).getFitness())
            {
                bestFitness = population.get(i).getFitness();
                best = i;
            }
        }
        
        return best;
    }
    
    
    public void writeStats() {

        System.out.println("Population stats");

        for (Double d : averageFitness) {

            System.out.println(d.toString());
        }
    }

    /**
     * Save the current collected statistics to a .csv file while fileName given
     * as a parameter
     *
     * @param fileName
     */
    public void saveStats(String fileName) {

        double[] average = new double[averageFitness.size()];

        for (int i = 0; i < averageFitness.size(); i++) {

            average[i] = averageFitness.get(i);
        }

        // save the average fitnesses
        saveArray(fileName, average);

    }

    private void saveArray(String fileName, double[] data) {

        Writer writer = null;

        try {
            writer = new FileWriter(fileName);

            for (Double d : data) {
                writer.write(d + ", ");
            }
        } catch (IOException e) {

            System.err.println("Error writing the file : ");
            e.printStackTrace();
        } finally {

            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error closing the file : ");
                    e.printStackTrace();
                }
            }
        }
    }


}
