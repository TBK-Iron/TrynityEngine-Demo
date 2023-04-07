package no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath;


public final class Matrix {
    
    private final float[][] value;

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

    public float get(int row, int col){
        return this.value[row][col];
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
    public static Matrix add(Matrix m1, Matrix m2){
        if(m1.getRows() != m2.getRows() || m1.getCols() != m2.getCols()){
            throw new IllegalArgumentException("Matrices can't be added");
        } else {
            float[][] newMatrix = new float[m1.getRows()][m1.getCols()];
            for (int i = 0; i < m1.getRows(); i++) {
                for (int j = 0; j < m1.getCols(); j++) {
                    newMatrix[i][j] = m1.get(i, j) + m2.get(i, j);
                }
            }
            return new Matrix(newMatrix);
        }
    }

    public Matrix scaledBy(float scalar){
        float[][] newMatrix = new float[this.getRows()][this.getCols()];
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                newMatrix[i][j] = this.get(i, j) * scalar;
            }
        }
        return new Matrix(newMatrix);
    }

    public static Matrix identityMatrix(int dimensions){
        float[][] newMatrix = new float[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if(i == j){
                    newMatrix[i][j] = 1;
                } else {
                    newMatrix[i][j] = 0;
                }
            }
        }
        return new Matrix(newMatrix);
    }

    public int getRows(){
        return this.value.length;
    }

    public int getCols(){
        return this.value[0].length;
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
