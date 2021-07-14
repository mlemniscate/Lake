package ir.maktab;

import java.util.Arrays;

/*
* For use of this class, we just need to call its constructor in the main method.
* */
public class Lake {
    private final int[] heights;
    private int lakesWater = 0;
    private final String[][] map;

    public Lake() {
        heights = new int[setHeightsNum()];
        setHeights();
        map = new String[maximumOfArray(heights)][heights.length];
        emptyMapArray();
        fillHeightsInMap();
        countLakes();
        printMap();
    }

    // Print map,
    // 🟥: represents heights
    // 🟩: represents water
    // 🟨: represents air
    private void printMap() {
        System.out.printf("%nYour map have %d water.%n%n", lakesWater);
        for (int i = map.length - 1; i >= 0; i--) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Count water of all the lakes in a map
    private void countLakes() {
        int lastHeight, newHeight = heights[0];
        for (int start = 1; start < heights.length; start++) {
            lastHeight = newHeight;
            newHeight = heights[start];
            if (newHeight < lastHeight)
                start = countLakeWater(lastHeight, start) - 1;
        }
    }

    // Count water of a lake in a map from start point and return the end point of lake
    private int countLakeWater(int peak, int start) {
        int water = 0,lastHeight, newHeight = heights[start - 1], max = 0;
        boolean hasWater = false;
        for (int end = start; end < heights.length; end++) {
            // This if else statement count water of a lake and if height >= peak add water
            // to the lakesWater and add water location to map and return end point.
            if (heights[end] < peak) water += peak - heights[end];
            else {
                lakesWater += water;
                addWaterLocations(start, end, peak);
                return end;
            }
            lastHeight = newHeight;
            newHeight = heights[end];
            // This if statement for check that we have a lake or not.
            if(!hasWater && newHeight > lastHeight) {
                hasWater = true;
                max = newHeight;
            }
            // This if statement for reassign max value to new value.
            if(hasWater && newHeight > max && newHeight <= peak) max = newHeight;
            // This if statement for checking if we're on the end point of the map and we have
            // water in our map we chose max as our new peak point and call this method again
            if (end == heights.length - 1 && hasWater) {
                return countLakeWater(max, start);
            }
        }
        return heights.length;
    }

    // Add water locations to the map
    private void addWaterLocations(int start, int end, int peak) {
        for (int col = start; col < end; col++) {
            for (int row = heights[col]; row < peak; row++) {
                map[row][col] = "🟩";
            }
        }
    }

    // Method find maximum value of an array and return it
    private int maximumOfArray(int[] array) {
        int max = array[0];
        for (int index = 1; index < array.length; index++) {
            if (max < array[index]) max = array[index];
        }
        return max;
    }

    // Method fill the map array with heights
    private void fillHeightsInMap() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (heights[col] > row) map[row][col] = "🟥";
            }
        }
    }

    // Empty our map array that all the values equal to 🟨
    private void emptyMapArray() {
        for (String[] row : map) {
            Arrays.fill(row, "🟨");
        }
    }

    // Get map heights from user by using Input object and set it to the heights array
    private void setHeights() {
        Input input = new Input("Enter your height in width 1: ", Integer.MAX_VALUE, 0, null);
        for (int i = 0; i < heights.length; i++) {
            heights[i] = input.getInputFromConsole();
            input.setInputMessage("Enter your height in width " + (i + 2) + ": ");
        }
    }

    // Get map width from user by using Input object and set it to the width
    private int setHeightsNum() {
        return new Input("Enter your map width(At least 3): ",
                Integer.MAX_VALUE,
                3,
                null)
                .getInputFromConsole();
    }
}
