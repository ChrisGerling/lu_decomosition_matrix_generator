import java.io.*;
import java.security.SecureRandom;
import java.util.Random;

public class Generator{

    public static void main(String[] args) throws IOException {
        int[] sizeArr = new int[]{10000, 20000};
        for (int size : sizeArr) {
            float[][] upper = generateUpper(size);
            float[][] lower = generateLower(size);
            createFile(matrixMul(upper, lower),"luCompable");
        }

    }

    private static float[][] generateLower(int size) {
        Random r = new SecureRandom();
        float[][] toReturn = new float[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < i; j++) {
                toReturn[i][j] = r.nextFloat(0, 9);
            }
            toReturn[i][i] = 1;
        }
        return toReturn;
    }

    private static float[][] generateUpper(int size) {
        Random r = new SecureRandom();
        float[][] toReturn = new float[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= i; j--) {
                toReturn[i][j] = r.nextFloat(0, 9);
            }

        }
        return toReturn;
    }


    private static void printArray(float[][] toPrint) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                String toPrint_ = String.format("%.3f ", toPrint[i][j]);
                System.out.print(toPrint_);
            }
            System.out.println();
        }
    }

    private static void createFiles(float[][] upper, float[][] lower, float[][] luCompatible) throws IOException {
        createFile(upper, "upper");
        createFile(lower, "lower");
        createFile(luCompatible, "luCompatible");

    }

    private static void createFile(float[][] matrix, String name) throws IOException {
        File fout = new File(matrix.length +"_"+ name   + ".txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (float[] floats : matrix) {
            StringBuilder stringBuffer = new StringBuilder();
            for (int j = 0; j < matrix.length-1; j++) {
                String toAppend = String.format("%.3f ", floats[j]);
                stringBuffer.append(toAppend);
            }
            String toAppend = String.format("%.3f", floats[matrix.length-1]);
            stringBuffer.append(toAppend);
            bw.write(stringBuffer.toString());
            bw.newLine();
        }
        bw.close();
    }

    private static float[][] matrixMul(float[][] lower, float[][] upper) {
        float[][] result = new float[upper.length][upper.length];
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(upper, lower, row, col);
            }
        }
        return result;
    }

    private static float multiplyMatricesCell(float[][] firstMatrix, float[][] secondMatrix, int row, int col) {
        float cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

}
