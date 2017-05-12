package AINT255;

import simplerace.Simulation;
import simplerace.Vector2d;

/**
 * Created by IntelliJ IDEA.
 * User: jtogel
 * Date: 10-Oct-2006
 * Time: 17:56:10
 */
public interface SensorModel {

    // First-person sensors

    public double getSpeed ();

    public double getAngleToNextWaypoint ();

    public double getDistanceToNextWaypoint ();

    public double getAngleToNextNextWaypoint ();

    public double getDistanceToNextNextWaypoint ();

    public double getAngleToOtherVehicle ();

    public double getDistanceToOtherVehicle ();

    public boolean otherVehicleIsPresent ();

    public boolean justPassedAWaypoint ();

    public boolean otherVehicleJustPassedAWaypoint ();

    public boolean justCollidedWithOtherVehicle ();

    // Third-person sensors

    public Vector2d getPosition ();

    public Vector2d getVelocity ();

    public double getOrientation ();

    public double getAngularVelocity ();

    public Vector2d getOtherVehiclePosition ();

    public Vector2d getOtherVehicleVelocity ();

    public double getOtherVehicleOrientation ();

    public double getOtherVehicleAngularVelocity();

    public Vector2d getNextWaypointPosition ();

    public Vector2d getNextNextWaypointPosition ();

    // Convenience method for state-based control. Returns a copy of the active simulation
    // IMPORTANT: The calling controller always controls car number 1 (the red car) in
    // the simulation copy, even if it controls the blue car in the actual game
    public Simulation getSimulation ();
}
