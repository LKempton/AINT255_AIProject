package controllers;

import AINT255.Constants;
import simplerace.Controller;
import AINT255.SensorModel;
import simplerace.Utils;
import simplerace.Vector2d;

/**
 * Created by IntelliJ IDEA.
 * User: julian
 * Date: Jun 24, 2007
 * Time: 2:43:26 AM
 */
public class HeuristicAggressiveController implements Controller, Constants {

    private double oldDistance = 0;


    public void reset() {
    }

    public int control(SensorModel inputs) {
        double realDistance = inputs.getPosition ().dist (inputs.getOtherVehiclePosition ());
        double approachSpeed =  oldDistance - realDistance;
        Vector2d opponentPosition = new Vector2d (inputs.getOtherVehiclePosition ());
        Vector2d otherVehicleVelocity = new Vector2d (inputs.getOtherVehicleVelocity ());
        double lookAhead = Math.max (0, realDistance / approachSpeed);
        otherVehicleVelocity.mul (lookAhead);
        opponentPosition.add (otherVehicleVelocity);
        //double projectedDistance = inputs.getPosition ().dist (opponentPosition);
        int limit = 5;
        if (approachSpeed > 0)
            limit = 10;
        int steering = angleToOpponentPosition
                (inputs.getPosition (), inputs.getOrientation (), opponentPosition) > 0 ? 0 : 2;
        double speed = inputs.getSpeed ();
        int driving = speed > (2 * limit) ? 0 : (speed > limit ? 1 : 2);
        oldDistance = realDistance;
        return driving * 3 + steering;
    }

    public double angleToOpponentPosition (Vector2d thisPosition, double orientation, Vector2d otherPosition) {
            double xDiff = otherPosition.x - thisPosition.x;
            double yDiff = otherPosition.y - thisPosition.y;
            double angle = Math.atan2 (yDiff, xDiff);
            angle = orientation - angle;
            return Utils.correctAngle (angle);
    }
    
}
