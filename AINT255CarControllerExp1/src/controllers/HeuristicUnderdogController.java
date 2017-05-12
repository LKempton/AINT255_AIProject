package controllers;

import AINT255.Constants;
import simplerace.Controller;
import AINT255.SensorModel;

/**
 * Created by IntelliJ IDEA.
 * User: julian
 * Date: Jun 24, 2007
 * Time: 2:20:32 AM
 */

public class HeuristicUnderdogController implements Controller, Constants {

    public void reset() {
    }

    public int control(SensorModel inputs) {
        int steering = inputs.getAngleToNextNextWaypoint () > 0 ? 0 : 2;
        int limit = 7;
        double distance = inputs.getDistanceToNextNextWaypoint ();
        if (distance < 0.2)
            limit = 3;
        if (distance < 0.1)
            limit = 1;
        if (distance < 0.04)
            limit = 0;
        double speed = inputs.getSpeed ();
        int driving = speed > (2 * limit) ? 0 : (speed > limit ? 1 : 2);
        return driving * 3 + steering;
    }

}
