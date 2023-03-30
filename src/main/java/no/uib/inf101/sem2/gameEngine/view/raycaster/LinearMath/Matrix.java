package no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath;

import java.util.ArrayList;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;

public class Matrix {
    
    double[][] value;

    public Matrix(double[][] value){
        this.value = value;
    }

    public static Matrix multiply(Matrix m1, Matrix m2){
        //Matrix multiplication
        if(m1.getCols() != m2.getRows()){
            throw new IllegalArgumentException("Matrices can't be multiplied");
        } else {
            double[][] newMatrix = new double[m1.getRows()][m2.getCols()];
            for(int i = 0; i < m2.getCols(); i++){
                double[] v = new double[m1.getRows()];
                for(int j = 0; j < m1.getRows(); j++){
                    v[j] = m1.value[j][i];
                }
                Vector mulVector = m1.multiply(new Vector(v));
                for(int j = 0; j < mulVector.getDims(); j++){
                    newMatrix[j][i] = mulVector.get(j);
                }
            }
            return new Matrix(newMatrix);
        }
    }

    public Vector multiply(Vector vector){
        if(this.getCols() != vector.getDims()){
            throw new IllegalArgumentException("Vector can't be multiplied with matrix");
        } else {
            double[] result = new double[vector.getDims()];

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
        double rotX = rot.getxAxis();
        double rotY = rot.getyAxis();
        double rotZ = rot.getzAxis();

        Matrix matrixX = new Matrix(new double[][] {
            {1, 0, 0},
            {0, Math.cos(rotX), -Math.sin(rotX)},
            {0, Math.sin(rotX), Math.cos(rotX)}
        });

        Matrix matrixY = new Matrix(new double[][] {
            {Math.cos(rotY), 0, Math.sin(rotY)},
            {0, 1, 0},
            {-Math.sin(rotY), 0, Math.cos(rotY)}
        });

        Matrix matrixZ = new Matrix(new double[][] {
            {Math.cos(rotZ), -Math.sin(rotZ), 0},
            {Math.sin(rotZ), Math.cos(rotZ), 0},
            {0, 0, 1}
        });

        Matrix rotationMatrix = multiply(multiply(matrixX, matrixY), matrixZ);

        return rotationMatrix;
    }
    
}
