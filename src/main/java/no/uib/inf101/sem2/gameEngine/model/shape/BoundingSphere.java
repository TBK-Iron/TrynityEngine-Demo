package no.uib.inf101.sem2.gameEngine.model.shape;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

public record BoundingSphere(GridPosition centerPos, float radius) {}
