package AINT255;

import simplerace.BasicTrack;
import simplerace.Controller;
import simplerace.Evaluator;
import simplerace.Play;
import simplerace.StatisticalSummary;
import controllers.GreedyController;
import controllers.HeuristicAggressiveController;
import controllers.HeuristicCombinedController;
import controllers.HeuristicSensibleController;
import controllers.HeuristicUnderdogController;
import controllers.KeyboardControllerOne;


public class AINT255CompareControllers {

    final static int numberOfTrials = 500;
    
    final static String AINT255EvolvedController = "AINT255Evolved.xml";

    public static void main (String[] args) {
        
        // the first controller should be the one evolved
        // this is the red car
        Controller firstController = Play.load (AINT255EvolvedController);
        
        
        // options for the 2nd controller is: 
        //          KeyboardControllerOne()  // use arrow keys to control
        //          HeuristicSensibleController()
        //          HeuristicCombinedController()
        //          HeuristicAggressiveController()
        //          GreedyController()
        //          HeuristicUnderdogController()
        
       // this is the blue car
        Controller secondController = new HeuristicSensibleController();
        
        if (secondController != null) {
            StatisticalSummary[] results = duoStats (firstController, secondController);
            System.out.println ("\nRed car (first controller): " + results[0] + "\n");
            System.out.print ("Blue car (second controller): \n" + results[1] + "\n");
            

        }
        else {
            StatisticalSummary ss = soloStats (firstController);
            System.out.println (ss);

        }
    }

    public static StatisticalSummary soloStats (Controller firstController) {
        StatisticalSummary ss = new StatisticalSummary();
        double result;
        
        for (int i = 0; i < numberOfTrials; i++) {
          
            firstController.reset ();
            
            result  = Evaluator.evaluateSolo (firstController, new BasicTrack (), false);
              System.out.println ("trial " + i  + " result " + result);
            ss.add (result);
        }
        return ss;
    }

    public static StatisticalSummary[] duoStats (Controller firstController, Controller secondController) {
        StatisticalSummary ssred = new StatisticalSummary();
        StatisticalSummary ssblue = new StatisticalSummary();
        for (int i = 0; i < numberOfTrials; i++) {
            firstController.reset ();
            secondController.reset ();
            double[] results = Evaluator.evaluateDuo (firstController, secondController, new BasicTrack (), false);
            ssred.add (results[0]);
            ssblue.add (results[1]);
        }
        return new StatisticalSummary[]{ssred, ssblue};
    }

}
