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

    // Used for the user primarily to change up and mess around with
    static String[][][] cube = new String[6][3][3];

    // Used for reference when changing cube values
    static String[][][] cubeTemp = new String [6][3][3];

    // Used to determine if there's command line args or not, changes the mode
    static boolean argsCheck = false;

    // Used to determine whether the cube is being scrambled or not
    static boolean isScarmbled = false;

    // Used for loop when not in args mode for user to mess around til they wish to stop
    static boolean proceedLoop = true;

    // Used to log moves to solve cube. This will be assuming that 'cube' is the primary one
    static String moveSolveSequence = "";


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
     * Compares two 3D Rubik's Cube arrays to determine whether their states are the same
     * @param cube1 is a 3D cube array to be compared, order doesn't matter
     * @param cube2 is a 3D cube array to be compared to the first, order doesn't matter
     * @return True if all values of both cubes are the same, false if they're not.
     */
    static boolean compareCubes(String[][][] cube1, String[][][] cube2) {
        for (int outerIndex = 0; outerIndex < 6; outerIndex++) {
            for (int midIndex = 0; midIndex < 3; midIndex++) {
                for (int innerIndex = 0; innerIndex < 3; innerIndex++) {

                    // if the values are not equal
                    if (!(cube1[outerIndex][midIndex][innerIndex].equals(cube2[outerIndex][midIndex][innerIndex]))) {
                        return false;

                    }
                }
            }
        }

        // all comparisons have passed
        return true;
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
        // Goes from 1 to 12, 0 being yellow cw, 1 being yellow ccw, etc. going down the switch cases
        boolean beenScrambled = false;

        int faceMove = 0;

        passCubeByValue(userCube, cubeTemp);

        // what moves to do, does not necessarily match up with reference point above ^ front is blue for these, red is right, top is yellow.
        switch (moveInput) {
            // up, clockwise
            case "u": // YELLOW (4)
                rotateFaceClockwise(cubeTemp, userCube, 4);
                rotateEdgesClockwise(userCube, 4);
                faceMove = 1; // yellow cw
                break;

            // up, counterclockwise
            case "u'": // YELLOW (4)
                rotateFaceCounterClockwise(cubeTemp, userCube, 4);
                rotateEdgesCounterClockwise(userCube, 4);
                faceMove = 2; // yellow ccw
                break;

            // down, clockwise
            case "d": // WHITE (5)
                rotateFaceClockwise(cubeTemp, userCube, 5);
                rotateEdgesClockwise(userCube, 5);
                faceMove = 3; // white cw
                break;

            // down, counterclockwise
            case "d'": // WHITE (5)
                rotateFaceCounterClockwise(cubeTemp, userCube, 5);
                rotateEdgesCounterClockwise(userCube, 5);
                faceMove = 4; // white ccw
                break;

            // right, clockwise
            case "r": // RED (0)
                rotateFaceClockwise(cubeTemp, userCube, 0);
                rotateEdgesClockwise(userCube, 0);
                faceMove = 5; // red cw
                break;

            // right, counterclockwise
            case "r'": // RED (0)
                rotateFaceCounterClockwise(cubeTemp, userCube, 0);
                rotateEdgesCounterClockwise(userCube, 0);
                faceMove = 6; // red ccw
                break;

            // left, clockwise
            case "l": // ORANGE (2)
                rotateFaceClockwise(cubeTemp, userCube, 2);
                rotateEdgesClockwise(userCube, 2);
                faceMove = 7; // orange cw
                break;

            // left, counterclockwise
            case "l'": // ORANGE (2)
                rotateFaceCounterClockwise(cubeTemp, userCube, 2);
                rotateEdgesCounterClockwise(userCube, 2);
                faceMove = 8; // orange ccw
                break;

            // front, clockwise
            case "f": // BLUE (1)
                rotateFaceClockwise(cubeTemp, userCube, 1);
                rotateEdgesClockwise(userCube, 1);
                faceMove = 9; // blue cw
                break;

            // front, counterclockwise
            case "f'": // BLUE (1)
                rotateFaceCounterClockwise(cubeTemp, userCube, 1);
                rotateEdgesCounterClockwise(userCube, 1);
                faceMove = 10; // blue ccw
                break;

            // back, clockwise
            case "b": // GREEN (3)
                rotateFaceClockwise(cubeTemp, userCube, 3);
                rotateEdgesClockwise(userCube, 3);
                faceMove = 11; // green cw
                break;

            // back, counterclockwise
            case "b'": // GREEN (3)
                rotateFaceCounterClockwise(cubeTemp, userCube, 3);
                rotateEdgesCounterClockwise(userCube, 3);
                faceMove = 12; // green cw
                break;


            case "quit": // For when game quit
                proceedLoop = false;
                break;

            case "scramble": // For when to scramble cube.. Uncertain how to have user specify number of random moves
                isScarmbled = true;
                // default is 5..
                moveSolveSequence = (scrambleCube(userCube, 5)) + moveSolveSequence;
                isScarmbled = false;
                beenScrambled = true;
                break;
        }

        // For checking the move sequence to solve
        if (!isScarmbled && !argsCheck) {

            // for when scramble is complete
            if (beenScrambled) {
                System.out.println("Sequence of Moves to Solve the Cube: " + moveSolveSequence);
                return; //escapes method
            }

            // user solved cube, sequence cleared
            if (compareCubes(userCube, cubeSolved)) {
                moveSolveSequence = "";

            } else {

                // if the current move is not equal to the first-most move that is logged, then log inverse of the move
                // as it is technically a step away from the cube
                if (!(checkForMoveSequence(moveInput))) {
                    moveSolveSequence = logMoveSequence(faceMove) + ", " + moveSolveSequence;

                // current move is equal to the front-most move that is logged, therefore user is a step closer to solved cube
                } else {

                    StringBuilder tempStrBuild = new StringBuilder(moveSolveSequence);
                    // needs to remove the front-most move in sequence
                    tempStrBuild.deleteCharAt(0);

                    boolean logLoopTemp;

                    if (tempStrBuild.charAt(0) == '\'' || tempStrBuild.charAt(0) == ',' || tempStrBuild.charAt(0) == ' ') {
                        logLoopTemp = true;

                    } else {
                        logLoopTemp = false;
                    }

                    while (logLoopTemp) {

                        if (tempStrBuild.charAt(0) == '\'') {
                            tempStrBuild.deleteCharAt(0);

                        } else if (tempStrBuild.charAt(0) == ' ') {
                            tempStrBuild.deleteCharAt(0);

                        } else if (tempStrBuild.charAt(0) == ',') {
                            tempStrBuild.deleteCharAt(0);

                        } else {
                            logLoopTemp = false;
                        }

                    }

                    moveSolveSequence = tempStrBuild.toString();
                }
            }

            System.out.println("Sequence of Moves to Solve the Cube: " + moveSolveSequence);
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
     * Scrambles the cube using a pseudo-random generator with a range from 1 to
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

        String sequence = "";


        for (int scrambleCounter = 0; scrambleCounter < numMoves; scrambleCounter++) {

            randomMove = (int)(Math.random() * (max - min + 1) + min);

            switch (randomMove) {

                case 1: // u, Yellow (4)
                    moveCubeFace(userCube, "u");
                    break;

                case 2: // u', Yellow (4)
                    moveCubeFace(userCube, "u'");
                    break;

                case 3: // d, White (5)
                    moveCubeFace(userCube, "d");
                    break;

                case 4: // d', White (5)
                    moveCubeFace(userCube, "d'");
                    break;

                case 5: // r, Red (0)
                    moveCubeFace(userCube, "r");
                    break;

                case 6: // r', Red (0)
                    moveCubeFace(userCube, "r'");
                    break;

                case 7: // l, Orange (2)
                    moveCubeFace(userCube, "l");
                    break;

                case 8: // l', Orange (2)
                    moveCubeFace(userCube, "l'");
                    break;

                case 9: // f, Blue (1)
                    moveCubeFace(userCube, "f");
                    break;

                case 10: // f', Blue (1)
                    moveCubeFace(userCube, "f'");
                    break;

                case 11: // b, Green (3)
                    moveCubeFace(userCube, "b");
                    break;

                case 12: // b', Green (3)
                    moveCubeFace(userCube, "b'");
                    break;

            }

            sequence = logMoveSequence(randomMove) + ", " + sequence;
        }

        return sequence;
    }


    /**
     * Takes the move done on cube and adds the opposite to the move log so cube can be solved.
     * @param move the move in int with 1 being yellow cw, and 2 being yellow ccw (etc.)
     * @return sequence needed to solve cube after move
     */
    static String logMoveSequence(int move) {
        
        String cubeSolveSequence = "";

        switch (move) {

            case 1: // u, Yellow (4)
                cubeSolveSequence = "u'" + cubeSolveSequence;
                break;

            case 2: // u', Yellow (4)
                cubeSolveSequence = "u" + cubeSolveSequence;
                break;

            case 3: // d, White (5)
                cubeSolveSequence = "d'" + cubeSolveSequence;
                break;

            case 4: // d', White (5)
                cubeSolveSequence = "d" + cubeSolveSequence;
                break;

            case 5: // r, Red (0)
                cubeSolveSequence = "r'" + cubeSolveSequence;
                break;

            case 6: // r', Red (0)
                cubeSolveSequence = "r" + cubeSolveSequence;
                break;

            case 7: // l, Orange (2)
                cubeSolveSequence = "l'" + cubeSolveSequence;
                break;

            case 8: // l', Orange (2)
                cubeSolveSequence = "l" + cubeSolveSequence;
                break;

            case 9: // f, Blue (1)
                cubeSolveSequence = "f'" + cubeSolveSequence;
                break;

            case 10: // f', Blue (1)
                cubeSolveSequence = "f" + cubeSolveSequence;
                break;

            case 11: // b, Green (3)
                cubeSolveSequence = "b'" + cubeSolveSequence;
                break;

            case 12: // b', Green (3)
                cubeSolveSequence = "b" + cubeSolveSequence;
                break;

        }
        return cubeSolveSequence;

    }


    /**
     * Determines whether the user is getting closer to solved cube according to current logged move sequence
     * @param move is the move that is being done on the cube
     * @return True if the move done is the same was the front-most move logged, false if not.
     */
    static boolean checkForMoveSequence(String move) {

        move = move + " ";

        char[] sequenceChrArray = moveSolveSequence.toCharArray();
        String condensedSolveSequence = "";

        // quick escape
        if (sequenceChrArray.length < 1) {
            return false;            
        }

        // iterates over the array removing commas and spaces
        for (int index = 0; index < sequenceChrArray.length; index++) {
            
            if (sequenceChrArray[index] == '\'') {
                condensedSolveSequence += sequenceChrArray[index];

            }

            if (sequenceChrArray[index] != ',') {
                if (sequenceChrArray[index] != ' ') {
                    condensedSolveSequence += sequenceChrArray[index];
                }

            } else { 
                condensedSolveSequence += " ";
            }

        }
        condensedSolveSequence += " ";


        // grabs the first move, sorta making the whole iterating over the entirety of the
        // current move sequence somewhat pointless
        if (condensedSolveSequence.charAt(1) == '\'') {
            condensedSolveSequence = "" + condensedSolveSequence.charAt(0);
            condensedSolveSequence += "'";

        }

        // If user move is equal to logged move, then returns true
        if (!(move.charAt(0) == condensedSolveSequence.charAt(0))) {
            return false;

        }

        if (!(move.charAt(1) == condensedSolveSequence.charAt(1))) {
            return false;

        }

        return true;
    }
}
