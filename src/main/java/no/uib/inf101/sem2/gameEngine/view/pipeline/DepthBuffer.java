package no.uib.inf101.sem2.gameEngine.view.pipeline;

import no.uib.inf101.sem2.gameEngine.model.shape.Face;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ChatGPT
 */

public class DepthBuffer {
    private Map<Integer, Map<Integer, Float>> buffer;

    public DepthBuffer() {
        buffer = new HashMap<>();
    }

    public boolean isOccluded(Face face) {
        int x = (int) face.getCenter().x();
        int y = (int) face.getCenter().y();
        float z = face.getCenter().z();

        Map<Integer, Float> row = buffer.get(x);
        if (row == null) {
            return false;
        }

        Float depth = row.get(y);
        return depth != null && depth <= z;
    }

    public void update(Face face) {
        int x = (int) face.getCenter().x();
        int y = (int) face.getCenter().y();
        float z = face.getCenter().z();

        Map<Integer, Float> row = buffer.computeIfAbsent(x, k -> new HashMap<>());
        Float currentDepth = row.get(y);
        if (currentDepth == null || currentDepth > z) {
            row.put(y, z);
        }
    }
}
