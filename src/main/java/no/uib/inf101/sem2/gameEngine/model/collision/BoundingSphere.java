package no.uib.inf101.sem2.gameEngine.model.collision;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;

public record BoundingSphere(GridPosition centerPos, float radius) {}
