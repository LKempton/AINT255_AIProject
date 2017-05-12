package controllers;

import AINT255.Constants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import simplerace.Controller;
import AINT255.SensorModel;

/**
 * Created by IntelliJ IDEA.
 * User: jtogel
 * Date: 24-Oct-2006
 * Time: 01:15:41
 */
public class KeyboardControllerTwo extends KeyAdapter implements Controller, Constants {

    private int drivingAction = 1;
    private int steeringAction = 1;

    public void reset() {}

    public int control (SensorModel inputs) {
        int action = drivingAction * 3 + steeringAction;
        //System.out.println (action);
        return action;
    }

    public void keyPressed (KeyEvent e) {
        int key = e.getKeyCode ();
        switch (key) {
            case KeyEvent.VK_S:
                drivingAction = 0;
                break;
            case KeyEvent.VK_W:
                drivingAction = 2;
                break;
            case KeyEvent.VK_A:
                steeringAction = 0;
                break;
            case KeyEvent.VK_D:
                steeringAction = 2;
                break;
        }

    }

    public void keyReleased (KeyEvent e) {
        int key = e.getKeyCode ();
        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                drivingAction = 1;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                steeringAction = 1;
                break;
        }

    }

}
