package controllers;

import AINT255.Constants;
import simplerace.Controller;
import AINT255.SensorModel;

/**
 * Created by IntelliJ IDEA.
 * User: jtogel
 * Date: 24-Oct-2006
 * Time: 04:32:57
 */
public class HeuristicSensibleController implements Controller, Constants {

    public void reset() {}

    public int control(SensorModel inputs) {
        int steering = inputs.getAngleToNextWaypoint () > 0 ? 0 : 2;
        int limit = 7;
        double speed = inputs.getSpeed ();
        int driving = speed > (2 * limit) ? 0 : (speed > limit ? 1 : 2);
        return driving * 3 + steering;
    }


}
