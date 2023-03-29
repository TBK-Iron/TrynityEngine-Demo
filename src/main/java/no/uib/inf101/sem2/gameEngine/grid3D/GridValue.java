package no.uib.inf101.sem2.gameEngine.grid3D;

import no.uib.inf101.sem2.gameEngine.model.shape.GridPosition;

public record GridValue<E>(E value, GridPosition pos) {}
