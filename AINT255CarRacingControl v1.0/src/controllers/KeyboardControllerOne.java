package controllers;

import AINT255.Constants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import simplerace.Controller;
import AINT255.SensorModel;

/**
 * Created by IntelliJ IDEA.
 * User: jtogel
 * Date: 17-Oct-2006
 * Time: 23:09:19
 */
public class KeyboardControllerOne extends KeyAdapter implements Controller, Constants {

    private int drivingAction = 1;
    private int steeringAction = 1;

    public void reset() {}

    public int control (SensorModel inputs) {
        int action = drivingAction * 3 + steeringAction;
       // System.out.println (inputs.getAngleToNextWaypoint () + " " + inputs.getDistanceToNextWaypoint () + "  " +
       //     inputs.getSpeed ());
       // System.out.println (action);
        return action;
    }

    public void keyPressed (KeyEvent e) {
        int key = e.getKeyCode ();
        switch (key) {
            case KeyEvent.VK_DOWN:
                drivingAction = 0;
                break;
            case KeyEvent.VK_UP:
                drivingAction = 2;
                break;
            case KeyEvent.VK_LEFT:
                steeringAction = 0;
                break;
            case KeyEvent.VK_RIGHT:
                steeringAction = 2;
                break;
        }

    }

    public void keyReleased (KeyEvent e) {
        int key = e.getKeyCode ();
        switch (key) {
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_UP:
                drivingAction = 1;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                steeringAction = 1;
                break;
        }

    }

}
