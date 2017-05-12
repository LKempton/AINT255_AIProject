/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AINT255;

import java.util.Random;

/**
 *
 * class to testing algorithms or fragments of code
 */
public class TestingClass {

    private void runTest() {

        Random rand = new Random();

        double randNumber;

        for (int i = 1; i <= 50; i++) {

            randNumber = (rand.nextDouble() * 2) - 1;
            System.out.println("randNumber " + randNumber);

//            randNumber = rand.nextGaussian();
//
//            while (randNumber >= 1.0 || randNumber <= -1.0) {
//                randNumber = rand.nextGaussian();
//            }
//            //      randNumber = rand.nextDouble();
//            System.out.println("randNumber " + randNumber);
        }

    }

    public static void main(String[] args) throws Exception {

        TestingClass testingClass = new TestingClass();

        testingClass.runTest();

        System.out.println("Testing class finishaed ");
    }
}
