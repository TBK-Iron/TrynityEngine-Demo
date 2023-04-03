package no.uib.inf101.sem2.gameEngine.model.shape;

import java.io.File;

import no.uib.inf101.sem2.gameEngine.model.shape.positionData.GridPosition;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public record ShapeData(GridPosition position, RelativeRotation rotation, File file) {}
