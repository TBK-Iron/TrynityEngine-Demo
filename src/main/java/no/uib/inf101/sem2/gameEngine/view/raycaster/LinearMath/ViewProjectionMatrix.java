package no.uib.inf101.sem2.gameEngine.view.raycaster.LinearMath;

import no.uib.inf101.sem2.gameEngine.grid3D.Rotation;
import no.uib.inf101.sem2.gameEngine.view.raycaster.RelativeRotation;

public class ViewProjectionMatrix {
    
    Matrix viewMatrix;
    Matrix projectionMatrix;
    Matrix viewProjectionMatrix;

    public ViewProjectionMatrix(float fov, float aspectRatio, float near, float far, Rotation rot){
        createProjectionMatrix(fov, aspectRatio, near, far);
        createViewMatrix(rot);
        createViewProjectionMatrix(this.viewMatrix, this.projectionMatrix);
    }


    private void createProjectionMatrix(float fov, float aspectRatio, float near, float far){
        float tanHalfFov = (float) Math.tan(fov / 2); 

        Matrix pMatrix = new Matrix( new float[][] {
            {1/(aspectRatio*tanHalfFov), 0           , 0                       , 0                     },
            {0                         , 1/tanHalfFov, 0                       , 0                     },
            {0                         , 0           , -(far+near)/(far-near)  , -2*far*near/(far-near)},
            {0                         , 0           , -1                      , 0                     }
        });
    
        this.projectionMatrix = pMatrix;
    }

    private void createViewMatrix(Rotation rot){
        Matrix viewMatrix = Matrix.getRotationMatrix(rot.getNegRotation());
        this.viewMatrix = viewMatrix;
    }

    private void createViewProjectionMatrix(Matrix viewM, Matrix ProjM){
        float[][] m = viewM.value;
        Matrix expandedViewMatrix = new Matrix(new float[][]{
            {m[0][0], m[0][1], m[0][2], 0},
            {m[1][0], m[1][1], m[1][2], 0},
            {m[2][0], m[2][1], m[2][2], 0},
            {0      , 0      , 0      , 1}
        });

        this.viewProjectionMatrix = Matrix.multiply(ProjM, expandedViewMatrix);
    }

    public Vector viewMatrixTransform(Vector v){
        return this.viewMatrix.multiply(v);
    }

    public Vector projectionMatrixTransform(Vector v){
        if(v.getDims() != 3){
            throw new IllegalArgumentException("Vector must have 3 dimensions");
        }
        Vector homogenousV = new Vector(new float[] {v.value[0], v.value[1], v.value[2], 1});
        Vector transformedV = this.projectionMatrix.multiply(homogenousV);

        float x = transformedV.value[0]/transformedV.value[3];
        float y = transformedV.value[1]/transformedV.value[3];
        
        return new Vector(new float[] {x, y});

    }

    public Matrix getViewProjectionMatrix(){
        return this.viewProjectionMatrix;
    }
}
