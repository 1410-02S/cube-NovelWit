package com.example.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cube {
    static String[][][] cubeSolved = {
        {
            //{"R0", "R1", "R2"},
            //{"R3", "R4", "R5"},
            //{"R6", "R7", "R8"}

            {"r", "r", "r"},
            {"r", "r", "r"},
            {"r", "r", "r"}
        },
        {
            //{"B0", "B1", "B2"},
            //{"B3", "B4", "B5"},
            //{"B6", "B7", "B8"}

            {"b", "b", "b"},
            {"b", "b", "b"},
            {"b", "b", "b"}
        },
        {
            //{"O0", "O1", "O2"},
            //{"O3", "O4", "O5"},
            //{"O6", "O7", "O8"}

            {"o", "o", "o"},
            {"o", "o", "o"},
            {"o", "o", "o"}
        },
        {
            //{"G0", "G1", "G2"},
            //{"G3", "G4", "G5"},
            //{"G6", "G7", "G8"}

            {"g", "g", "g"},
            {"g", "g", "g"},
            {"g", "g", "g"}
        },
        {
            //{"Y0", "Y1", "Y2"},
            //{"Y3", "Y4", "Y5"},
            //{"Y6", "Y7", "Y8"}

            {"y", "y", "y"},
            {"y", "y", "y"},
            {"y", "y", "y"}
        },
        {
            //{"W0", "W1", "W2"},
            //{"W3", "W4", "W5"},
            //{"W6", "W7", "W8"}

            {"w", "w", "w"},
            {"w", "w", "w"},
            {"w", "w", "w"}
        },
    };

    static String[][][] cube = new String[6][3][3];

    static String[][][] cubeTemp = new String [6][3][3];

    static boolean argsCheck = false;

    static boolean proceedLoop = true;


    /**
     * Cube Face-Int Reference. IMPORTANT!
     * This is assuming one is looking at Red face (front) and using as reference point for other faces
     * 
     *      (front, f) RED - 0
     *      (right, r) BLUE - 1
     *      (back, b) ORANGE - 2
     *      (left, l) GREEN - 3
     *      (bottom, bo) YELLOW - 4
     *      (top, t) WHITE  - 5
     */


    /**
     * Cube moves to Focal Faces Reference. IMPORTANT!
     * This is for translating what face is 'focsued' for the different moves outlined in assignment
     * 
     *      e.g. Move-Letter -> Face (index of face)
     * 
     *      U/U' -> Yellow (4)
     *      D/D' -> White (5)
     *      R/R' -> Red (0)
     *      L/L' -> Orange (2)
     *      F/F' -> Blue (1)
     *      B/B' -> Green (3)
     */
    



    
    // Main method here!
    public static void main(String[] args) throws IOException {
        String userInput;

        // from 2D game..
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // copies solved cube to another cube array for user to change
        passCubeByValue(cubeSolved, cube);

        //System.out.println("Move Sequence to Solve: " + (scrambleCube(cube, 8) + "\n\n"));


        if (args.length > 0) {
            argsCheck = true;

        } else {
            // shows the initial cube state when not in args mode
            System.out.println("Beginning Cube State:");
            showCubeAll(cube);
            
        }

        // 'game' loop
        do {
            if (!argsCheck) {
                userInput = reader.readLine();
                userInput = userInput.toLowerCase();
                System.out.println(); // adds extra lines, looks nice..

                moveCubeFace(cube, userInput);
                showCubeAll(cube);

            } else {
                // in case there's args
                for (int index = 0; index < args.length; index++){
                    moveCubeFace(cube, args[index]);
                    //System.out.println("---------------------------------\n");
                }

                // shows only the final state of the cube for args mode (I'm uncertain if it needs to show all states)
                showCubeAll(cube);

                moveCubeFace(cube, "quit");
            }

        } while (proceedLoop);

        reader.close();
    }







    /**
     * Passes various 'cube' arrays by value, not reference.
     * @param referenceCube is the cube whose values are being passed to updatedCube
     * @param updateCube is the cube whose values are being updated by referenceCube's
     * 
     * Two ways method is used:
     * 
     *      passCubeByValue(cubeSolved, cube);
     * 
     *          Used in beginning to copy the solved state of the Rubik's cube into another array for user to change.
     * 
     * 
     *      passCubeByValue(cube, cubeTemp);
     * 
     *          Used repetively to copy the user's current cube state to a temporary 'cube' array to allow for
     *              changing of values.
     * 
     */
    static void passCubeByValue(String[][][] referenceCube, String[][][] updateCube) {
        for (int outerIndex = 0; outerIndex < 6; outerIndex++) {
            for (int midIndex = 0; midIndex < 3; midIndex++) {
                for (int innerIndex = 0; innerIndex < 3; innerIndex++) {
                    updateCube[outerIndex][midIndex][innerIndex] = referenceCube[outerIndex][midIndex][innerIndex];

                }
            }
        }
    }



    /**
     * Prints out the user's 'cube' array of the specified face.
     * @param userCube is the Rubik's Cube user wishes to use to print.
     * @param indexFace is the specific face of the cube that user wishes to print out, by int.
     */
    static void showCube(String[][][] userCube, int indexFace) {
 
         for (int outerIndex = 0; outerIndex < 3; outerIndex++) {
             String valueSpacer = "|";

             for (int innerIndex = 0; innerIndex < 3; innerIndex++) {
                 System.out.print(userCube[indexFace][outerIndex][innerIndex] + valueSpacer);
 
                 // For the spacer between the output face
                 if (innerIndex == 1) {
                     valueSpacer = " ";
                 }
             }
             System.out.println();
         }
         System.out.println();
    }



    /**
    * Prints out the user's 'cube' array in its entirety, using the showCube method
    * @param userCube is the Rubik's Cube user wishes to print.
    */
    static void showCubeAll(String[][][] userCube) {     
        
        for (int indexFace = 0; indexFace < 6; indexFace++) {
            showCube(userCube, indexFace);
        }
    }



    /**
     * Used to determine moves and other actions, such as "quit" or "scramble"
     * @param userCube is the Rubik's Cube user wishes to change.
     * @param moveInput is what move the user wishes the Rubik's Cube to do
     */
    static void moveCubeFace(String[][][] userCube, String moveInput) {

        passCubeByValue(userCube, cubeTemp);

        // what moves to do, does not necessarily match up with reference point above ^ front is blue for these, red is right, top is yellow.
        switch (moveInput) {
            // up, clockwise
            case "u": // YELLOW (4)
                rotateFaceClockwise(cubeTemp, userCube, 4);
                rotateEdgesClockwise(userCube, 4);
                break;

            // up, counterclockwise
            case "u'": // YELLOW (4)
                rotateFaceCounterClockwise(cubeTemp, userCube, 4);
                rotateEdgesCounterClockwise(userCube, 4);
                break;

            // down, clockwise
            case "d": // WHITE (5)
                rotateFaceClockwise(cubeTemp, userCube, 5);
                rotateEdgesClockwise(userCube, 5);
                break;

            // down, counterclockwise
            case "d'": // WHITE (5)
                rotateFaceCounterClockwise(cubeTemp, userCube, 5);
                rotateEdgesCounterClockwise(userCube, 5);
                break;

            // right, clockwise
            case "r": // RED (0)
                rotateFaceClockwise(cubeTemp, userCube, 0);
                rotateEdgesClockwise(userCube, 0);
                break;

            // right, counterclockwise
            case "r'": // RED (0)
                rotateFaceCounterClockwise(cubeTemp, userCube, 0);
                rotateEdgesCounterClockwise(userCube, 0);
                break;

            // left, clockwise
            case "l": // ORANGE (2)
                rotateFaceClockwise(cubeTemp, userCube, 2);
                rotateEdgesClockwise(userCube, 2);
                break;

            // left, counterclockwise
            case "l'": // ORANGE (2)
                rotateFaceCounterClockwise(cubeTemp, userCube, 2);
                rotateEdgesCounterClockwise(userCube, 2);
                break;

            // front, clockwise
            case "f": // BLUE (1)
                rotateFaceClockwise(cubeTemp, userCube, 1);
                rotateEdgesClockwise(userCube, 1);
                break;

            // front, counterclockwise
            case "f'": // BLUE (1)
                rotateFaceCounterClockwise(cubeTemp, userCube, 1);
                rotateEdgesCounterClockwise(userCube, 1);
                break;

            // back, clockwise
            case "b": // GREEN (3)
                rotateFaceClockwise(cubeTemp, userCube, 3);
                rotateEdgesClockwise(userCube, 3);
                break;

            // back, counterclockwise
            case "b'": // GREEN (3)
                rotateFaceCounterClockwise(cubeTemp, userCube, 3);
                rotateEdgesCounterClockwise(userCube, 3);
                break;


            case "quit": // For when game quit
                proceedLoop = false;
                break;

            case "scramble": // For when to scramble cube.. Uncertain how to have user specify number of random moves
                // default is 5..
                System.out.println("Moves to Solve Cube: " + (scrambleCube(userCube, 5) + "\n"));
                break;
        }

    }





    /**
     * Rotates the focal face CW once each time it's called
     * @param referenceCube is the Rubik's Cube that is being referenced for change
     * @param updateCube is the Rubik's Cube user wishes to change.
     * @param face is the face that is being rotated CLOCKWISE, changes focus! This will
     *             help determine what edges of other faces are changed/rotated
     */
    static void rotateFaceClockwise(String[][][] referenceCube, String[][][] updateCube, int face) {
        int cw1 = 2;
        int cw2 = 0;

        do {
            for (int row = 0; row < 3; row++) {
                updateCube[face][row][cw1] = referenceCube[face][cw2][row];
            }

            cw1--;
            cw2++;

        } while (cw1 >= 0);
    }



    /**
     * Rotates the focal face CCW once each time it's called
     * @param referenceCube is the Rubik's Cube that is being referenced for change
     * @param updateCube is the Rubik's Cube user wishes to change.
     * @param face is the face that is being rotated CLOCKWISE, changes focus! This will
     *             help determine what edges of other faces are changed/rotated
     */
    static void rotateFaceCounterClockwise(String[][][] referenceCube, String[][][] updateCube, int face) {
        int ccw1 = 0;

        do {
            int row2 = 2;
            for (int row = 0; row < 3; row++) {
                updateCube[face][row2][ccw1] = referenceCube[face][ccw1][row];
                row2--;
            }

            ccw1++;

        } while (ccw1 < 3);
    }


    // The following two methods are perhaps not the best way to rotate the edges.. But I can't figure it out.


    /**
     * This rotates the edges CW in respect to the focal face. I cannot come up with a better method..
     * @param userCube is the cube we are changing
     * @param face is the specific focal face to use as a reference for the others
     */
    static void rotateEdgesClockwise(String[][][] userCube, int face) {

        int secondIndex = 2;

        for (int firstIndex = 0; firstIndex < 3; firstIndex++) {
            switch (face) {

                case 0: // RED
                    // edge face one to edge face two - green to white
                    userCube[5][2][secondIndex] = cubeTemp[3][firstIndex][2];

                    // edge face two to edge face three - white to blue
                    userCube[1][secondIndex][0] = cubeTemp[5][2][secondIndex];

                    // edge face three to edge face four - blue to yellow
                    userCube[4][0][firstIndex] = cubeTemp[1][secondIndex][0];

                    // edge face four to edge face one - yellow to green
                    userCube[3][firstIndex][2] = cubeTemp[4][0][firstIndex];
                    break;


                case 1: // BLUE
                    // edge face one to edge face two - red to white
                    userCube[5][firstIndex][2] = cubeTemp[0][firstIndex][2];

                    // edge face two to edge face three - white to orange
                    userCube[2][secondIndex][0] = cubeTemp[5][firstIndex][2];

                    // edge face three to edge face four - orange to yellow
                    userCube[4][firstIndex][2] = cubeTemp[2][secondIndex][0];

                    // edge face four to edge face one - yellow to red
                    userCube[0][firstIndex][2] = cubeTemp[4][firstIndex][2];
                    break;


                case 2: // ORANGE
                    // edge face one to edge face two - blue to white
                    userCube[5][0][firstIndex] = cubeTemp[1][firstIndex][2];

                    // edge face two to edge face three - white to green
                    userCube[3][secondIndex][0] = cubeTemp[5][0][firstIndex];

                    // edge face three to edge face four - green to yellow
                    userCube[4][2][secondIndex] = cubeTemp[3][secondIndex][0];

                    // edge face four to edge face one - yellow to blue
                    userCube[1][firstIndex][2] = cubeTemp[4][2][secondIndex];
                    break;


                case 3: // GREEN
                    // edge face one to edge face two - orange to white
                    userCube[5][secondIndex][0] = cubeTemp[2][firstIndex][2];

                    // edge face two to edge face three - white to red
                    userCube[0][secondIndex][0] = cubeTemp[5][secondIndex][0];

                    // edge face three to edge face four - red to yellow
                    userCube[4][secondIndex][0] = cubeTemp[0][secondIndex][0];

                    // edge face four to edge face one - yellow to orange
                    userCube[2][firstIndex][2] = cubeTemp[4][secondIndex][0];
                    break;


                case 4: // YELLOW
                    // edge face one to edge face two - green to red
                    userCube[0][2][secondIndex] = cubeTemp[3][2][secondIndex];

                    // edge face two to edge face three - red to blue
                    userCube[1][2][secondIndex] = cubeTemp[0][2][secondIndex];

                    // edge face three to edge face four - blue to orange
                    userCube[2][2][secondIndex] = cubeTemp[1][2][secondIndex];

                    // edge face four to edge face one - orange to green
                    userCube[3][2][secondIndex] = cubeTemp[2][2][secondIndex];
                    break;


                case 5: // WHITE
                    // edge face one to edge face two - green to orange
                    userCube[2][0][firstIndex] = cubeTemp[3][0][firstIndex];

                    // edge face two to edge face three - orange to blue
                    userCube[1][0][firstIndex] = cubeTemp[2][0][firstIndex];

                    // edge face three to edge face four - blue to red
                    userCube[0][0][firstIndex] = cubeTemp[1][0][firstIndex];

                    // edge face four to edge face one - red to green
                    userCube[3][0][firstIndex] = cubeTemp[0][0][firstIndex];
                    break;
            }

            // outside switch, inside for loop
            secondIndex--;
        }
    }




    /**
     * This rotates the edges CCW in respect to the focal face. I cannot come up with a better method..
     * @param userCube is the cube we are changing
     * @param face is the specific focal face to use as a reference for the others
     */
    static void rotateEdgesCounterClockwise(String[][][] userCube, int face) {
        int secondIndex = 0;

        for (int firstIndex = 2; firstIndex >= 0; firstIndex--) {
            switch (face) {

                case 0: // RED
                    // edge face one to edge face two - green to yellow
                    userCube[4][0][firstIndex] = cubeTemp[3][firstIndex][2];

                    // edge face two to edge face three - yellow to blue
                    userCube[1][secondIndex][0] = cubeTemp[4][0][firstIndex];

                    // edge face three to edge face four - blue to white
                    userCube[5][2][secondIndex] = cubeTemp[1][secondIndex][0];

                    // edge face four to edge face one - white to green
                    userCube[3][firstIndex][2] = cubeTemp[5][2][secondIndex];
                    break;


                case 1: // BLUE
                    // edge face one to edge face two - red to yellow
                    userCube[4][firstIndex][2] = cubeTemp[0][firstIndex][2];

                    // edge face two to edge face three - yellow to orange
                    userCube[2][secondIndex][0] = cubeTemp[4][firstIndex][2];

                    // edge face three to edge face four - orange to white
                    userCube[5][firstIndex][2] = cubeTemp[2][secondIndex][0];

                    // edge face four to edge face one - white to red
                    userCube[0][firstIndex][2] = cubeTemp[5][firstIndex][2];
                    break;


                case 2: // ORANGE
                    // edge face one to edge face two - blue to yellow
                    userCube[4][2][secondIndex] = cubeTemp[1][firstIndex][2];

                    // edge face two to edge face three - yellow to green
                    userCube[3][secondIndex][0] = cubeTemp[4][2][secondIndex];

                    // edge face three to edge face four - green to white
                    userCube[5][0][firstIndex] = cubeTemp[3][secondIndex][0];

                    // edge face four to edge face one - white to blue
                    userCube[1][firstIndex][2] = cubeTemp[5][0][firstIndex];
                    break;


                case 3: // GREEN
                    // edge face one to edge face two - orange to yellow
                    userCube[4][secondIndex][0] = cubeTemp[2][firstIndex][2];

                    // edge face two to edge face three - yellow to red
                    userCube[0][secondIndex][0] = cubeTemp[4][secondIndex][0];

                    // edge face three to edge face four - red to white
                    userCube[5][secondIndex][0] = cubeTemp[0][secondIndex][0];

                    // edge face four to edge face one - white to orange
                    userCube[2][firstIndex][2] = cubeTemp[5][secondIndex][0];
                    break;


                case 4: // YELLOW
                    // edge face one to edge face two - green to orange
                    userCube[2][2][secondIndex] = cubeTemp[3][2][secondIndex];

                    // edge face two to edge face three - orange to blue
                    userCube[1][2][secondIndex] = cubeTemp[2][2][secondIndex];

                    // edge face three to edge face four - blue to red
                    userCube[0][2][secondIndex] = cubeTemp[1][2][secondIndex];

                    // edge face four to edge face one - red to green
                    userCube[3][2][secondIndex] = cubeTemp[0][2][secondIndex];
                    break;


                case 5: // WHITE
                    // edge face one to edge face two - green to red
                    userCube[0][0][firstIndex] = cubeTemp[3][0][firstIndex];

                    // edge face two to edge face three - red to blue
                    userCube[1][0][firstIndex] = cubeTemp[0][0][firstIndex];

                    // edge face three to edge face four - blue to orange
                    userCube[2][0][firstIndex] = cubeTemp[1][0][firstIndex];

                    // edge face four to edge face one - orange to green
                    userCube[3][0][firstIndex] = cubeTemp[2][0][firstIndex];
                    break;
            }

            // outside switch, inside for loop
            secondIndex++;
        }
    }



    /**
     * Scrambles the cube using a pseudo-random number generator with a range from 1 to
     * 12. This is the amount of possible moves one can make)
     * @param userCube is the Rubik's Cube user wishes to change.
     * @param numMoves is how many moves the cube is to be scrambled
     * @return The sequence of moves needed to solve the cube after it's scrambled. May
     *         not be the shortest amount of moves.
     */
    static String scrambleCube(String[][][] userCube, int numMoves) {
        int max = 12;
        int min = 1;
        int randomMove = 0;

        String solveCubeSequence = "";


        for (int scrambleCounter = 0; scrambleCounter < numMoves; scrambleCounter++) {

            randomMove = (int)(Math.random() * (max - min + 1) + min);

            switch (randomMove) {

                case 1: // u, Yellow (4)
                    moveCubeFace(userCube, "u");
                    solveCubeSequence = "u'" + solveCubeSequence;
                    break;

                case 2: // u', Yellow (4)
                    moveCubeFace(userCube, "u'");
                    solveCubeSequence = "u" + solveCubeSequence;
                    break;

                case 3: // d, White (5)
                    moveCubeFace(userCube, "d");
                    solveCubeSequence = "d'" + solveCubeSequence;
                    break;

                case 4: // d', White (5)
                    moveCubeFace(userCube, "d'");
                    solveCubeSequence = "d" + solveCubeSequence;
                    break;

                case 5: // r, Red (0)
                    moveCubeFace(userCube, "r");
                    solveCubeSequence = "r'" + solveCubeSequence;
                    break;

                case 6: // r', Red (0)
                    moveCubeFace(userCube, "r'");
                    solveCubeSequence = "r" + solveCubeSequence;
                    break;

                case 7: // l, Orange (2)
                    moveCubeFace(userCube, "l");
                    solveCubeSequence = "l'" + solveCubeSequence;
                    break;

                case 8: // l', Orange (2)
                    moveCubeFace(userCube, "l'");
                    solveCubeSequence = "l" + solveCubeSequence;
                    break;

                case 9: // f, Blue (1)
                    moveCubeFace(userCube, "f");
                    solveCubeSequence = "f'" + solveCubeSequence;
                    break;

                case 10: // f', Blue (1)
                    moveCubeFace(userCube, "f'");
                    solveCubeSequence = "f" + solveCubeSequence;
                    break;

                case 11: // b, Green (3)
                    moveCubeFace(userCube, "b");
                    solveCubeSequence = "b'" + solveCubeSequence;
                    break;

                case 12: // b', Green (3)
                    moveCubeFace(userCube, "b'");
                    solveCubeSequence = "b" + solveCubeSequence;
                    break;

            }

            if ((scrambleCounter + 1) != numMoves) {
                solveCubeSequence = ", " + solveCubeSequence;
            }
        }
    

        return solveCubeSequence;
    }

}
