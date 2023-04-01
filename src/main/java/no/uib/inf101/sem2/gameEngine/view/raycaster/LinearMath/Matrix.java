package no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;

public class Matrix {
    
    float[][] value;

    public Matrix(float[][] value){
        if(!isValid(value)){
            throw new IllegalArgumentException("Number of rows not consistent");
        }
        this.value = value;
    }

    private static boolean isValid(float[][] matrix){
        int cols = matrix[0].length;
        for(float[] row : matrix){
            if(row.length != cols){
                return false;
            }
        }
        return true;
    }

    public static Matrix multiply(Matrix m1, Matrix m2) {
        //Matrix multiplication
        if (m1.value[0].length != m2.value.length) {
            throw new IllegalArgumentException("Matrices can't be multiplied");
        } else {
            float[][] newMatrix = new float[m1.value.length][m2.value[0].length];
            for (int i = 0; i < m1.value.length; i++) {
                for (int j = 0; j < m2.value[0].length; j++) {
                    float sum = 0;
                    for (int k = 0; k < m1.value[0].length; k++) {
                        sum += m1.value[i][k] * m2.value[k][j];
                    }
                    newMatrix[i][j] = sum;
                }
            }
            return new Matrix(newMatrix);
        }
    }

    public Vector multiply(Vector vector){
        if(this.getCols() != vector.getDims()){
            throw new IllegalArgumentException("Vector can't be multiplied with matrix");
        } else {
            float[] result = new float[this.getRows()];

            for (int i = 0; i < this.value.length; i++) {
                result[i] = 0;
                for (int j = 0; j < this.value[0].length; j++) {
                    result[i] += this.value[i][j] * vector.get(j);
                }
            }

            return new Vector(result);
        }
        
    }

    public int getRows(){
        return this.value.length;
    }

    public int getCols(){
        return this.value[0].length;
    }

    public static Matrix getRotationMatrix(Rotation rot){
        float rotX = rot.getxAxis();
        float rotY = rot.getyAxis();
        float rotZ = rot.getzAxis();

        Matrix matrixX = new Matrix(new float[][] {
            {1f, 0f, 0f},
            {0f, (float) Math.cos(rotX), (float) -Math.sin(rotX)},
            {0f, (float) Math.sin(rotX), (float) Math.cos(rotX)}
        });

        Matrix matrixY = new Matrix(new float[][] {
            {(float) Math.cos(rotY), 0f, (float) Math.sin(rotY)},
            {0f, 1f, 0f},
            {(float) -Math.sin(rotY), 0f, (float) Math.cos(rotY)}
        });

        Matrix matrixZ = new Matrix(new float[][] {
            {(float) Math.cos(rotZ), (float) -Math.sin(rotZ), 0f},
            {(float) Math.sin(rotZ), (float) Math.cos(rotZ), 0f},
            {0f, 0f, 1f}
        });

        Matrix rotationMatrix = multiply(multiply(matrixX, matrixY), matrixZ);

        return rotationMatrix;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                sb.append(value[i][j]);
                if (j < value[i].length - 1) {
                    sb.append(", ");
                }
            }
            if (i < value.length - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Matrix other = (Matrix) obj;
        if (value.length != other.value.length) {
            return false;
        }

        for (int i = 0; i < value.length; i++) {
            if (value[i].length != other.value[i].length) {
                return false;
            }
            for (int j = 0; j < value[i].length; j++) {
                if (Math.round(value[i][j] * 10000) != Math.round(other.value[i][j] * 10000)) {
                    return false;
                }
            }
        }

        return true;
    }
}
