package controllers;

import AINT255.Constants;
import simplerace.Controller;
import AINT255.SensorModel;

/**
 * Created by IntelliJ IDEA.
 * User: jtogel
 * Date: 24-Oct-2006
 * Time: 03:49:12
 * To change this template use File | Settings | File Templates.
 */
public class GreedyController implements Controller, Constants {

    public void reset() {}

    public int control (SensorModel inputs) {
        return inputs.getAngleToNextWaypoint () > 0 ? forwardleft : forwardright;
    }
}
