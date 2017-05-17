package controllers;

import AINT255.Constants;
import simplerace.Controller;
import AINT255.SensorModel;

/**
 * Created by IntelliJ IDEA.
 * User: julian
 * Date: Jun 28, 2007
 * Time: 9:13:54 PM
 */
public class HeuristicCombinedController implements Controller, Constants {

    //HeuristicAggressiveController aggressive = new HeuristicAggressiveController ();
    HeuristicSensibleController sensible = new HeuristicSensibleController ();
    HeuristicUnderdogController underdog = new HeuristicUnderdogController ();


    public void reset() {}

    public int control(SensorModel inputs) {
        
        double myDistanceToCurrent = inputs.getPosition ().dist (inputs.getNextWaypointPosition ());
        double otherDistanceToCurrent = inputs.getOtherVehiclePosition ().dist (inputs.getNextWaypointPosition ());
        if (otherDistanceToCurrent < myDistanceToCurrent) {
           // double myDistanceToOther = inputs.getPosition ().dist (inputs.getOtherVehiclePosition ());
           // double myDistanceToNext = inputs.getPosition ().dist (inputs.getNextNextWaypointPosition ());
            //if ((myDistanceToOther * 2) < myDistanceToNext) {
            //    return aggressive.control (inputs);
            //}
           // else {
                return underdog.control (inputs);
           // }
        }
        return sensible.control (inputs);
    }
}
