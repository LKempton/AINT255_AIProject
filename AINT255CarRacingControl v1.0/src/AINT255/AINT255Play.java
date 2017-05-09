package AINT255;

import simplerace.BasicTrack;
import simplerace.Controller;
import simplerace.Evaluator;
import controllers.GreedyController;
import controllers.HeuristicAggressiveController;
import controllers.HeuristicSensibleController;
import simplerace.Play;
import simplerace.Track;
import wox.serial.Easy;


/**
 * Used to explore the strategies of the
 * various built in controllers
 *
 * @author Martin Beck
 */
public class AINT255Play {

    final static String AINT255EvolvedController = "AINT255Evolved.xml";

    public static void main(String[] args) {

        Controller firstController;  // first controller is the red car;
        Controller secondController; // second controller is the blue car;

        // either load in the evolved controller
      //  firstController = Play.load(AINT255EvolvedController);

        // or choose one of the options below
        // options for controllers are: 
        //          KeyboardControllerOne()  // use arrow keys to control
        //          HeuristicSensibleController()
        //          HeuristicCombinedController()
        //          HeuristicAggressiveController()
        //          GreedyController()
        //          HeuristicUnderdogController()
        
        
       // firstController = new  HeuristicAggressiveController();
         
         firstController = new  HeuristicSensibleController();
               
        secondController = new GreedyController();

        Track track = new BasicTrack();

        if (secondController == null) { // solo run
            System.out.println("Score: " + Evaluator.evaluateSolo(firstController, track, true));
        } else {  // competition!
            double[] scores = Evaluator.evaluateDuo(firstController, secondController, track, true);

            System.out.println("Red car (first controller): \n" + scores[0] + "\n");
            System.out.print("Blue car (second controller): \n" + scores[1] + "\n");;
        }
    }

    public static Controller load(String name) {
        Controller controller;
        try {
            controller = (Controller) (Object) Class.forName(name).newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println(name + " is not a class name; trying to load a wox definition with that name.");
            controller = (Controller) Easy.load(name);
        } catch (Exception e) {
            e.printStackTrace();
            controller = null;
            System.exit(0);
        }
        return controller;
    }

}
