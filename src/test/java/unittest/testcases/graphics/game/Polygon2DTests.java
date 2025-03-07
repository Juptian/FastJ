package unittest.testcases.graphics.game;

import io.github.lucasstarsz.fastj.math.Maths;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.DrawUtil;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.game.Polygon2D;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Polygon2DTests {

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);
        Polygon2D polygon2D = new Polygon2D(square);

        assertEquals(Polygon2D.DefaultColor, polygon2D.getColor(), "The created polygon's color should match the default color.");
        assertEquals(Polygon2D.DefaultFill, polygon2D.isFilled(), "The created polygon's 'fill' option should match the default fill option.");
        assertEquals(Polygon2D.DefaultShow, polygon2D.shouldRender(), "The created polygon's 'show' option should match the default show option.");
        assertEquals(GameObject.DefaultTranslation, polygon2D.getTranslation(), "The created polygon's translation should match an origin translation.");
        assertEquals(GameObject.DefaultRotation, polygon2D.getRotation(), "The created polygon's rotation should match an origin rotation.");
        assertEquals(GameObject.DefaultScale, polygon2D.getScale(), "The created polygon's scaling should match an origin scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedColorFillShowParams() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        boolean shouldFill = Maths.randomBoolean();
        boolean shouldRender = Maths.randomBoolean();

        Polygon2D polygon2D = new Polygon2D(square, randomColor, shouldFill, shouldRender);

        assertEquals(randomColor, polygon2D.getColor(), "The created polygon's color should match the randomly generated color.");
        assertEquals(shouldFill, polygon2D.isFilled(), "The created polygon's 'fill' option should match the randomly generated fill option.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated show option.");
        assertEquals(GameObject.DefaultTranslation, polygon2D.getTranslation(), "The created polygon's translation should match an origin translation.");
        assertEquals(GameObject.DefaultRotation, polygon2D.getRotation(), "The created polygon's rotation should match an origin rotation.");
        assertEquals(GameObject.DefaultScale, polygon2D.getScale(), "The created polygon's scaling should match an origin scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedColorFillShowParams_andRandomlyGeneratedTransformParams() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        boolean shouldFill = Maths.randomBoolean();
        boolean shouldRender = Maths.randomBoolean();

        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-5000f, 5000f);
        float expectedNormalizedRotation = randomRotation % 360;

        Polygon2D polygon2D = new Polygon2D(square, randomTranslation, randomRotation, randomScale, randomColor, shouldFill, shouldRender);

        AffineTransform expectedTransform = new AffineTransform();
        expectedTransform.setToScale(randomScale.x, randomScale.y);
        expectedTransform.setToRotation(Math.toRadians(randomRotation), polygon2D.getCenter().x, polygon2D.getCenter().y);
        expectedTransform.setToTranslation(randomTranslation.x, randomTranslation.y);

        assertEquals(randomColor, polygon2D.getColor(), "The created polygon's color should match the randomly generated color.");
        assertEquals(shouldFill, polygon2D.isFilled(), "The created polygon's 'fill' option should match the randomly generated fill option.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated show option.");
        assertEquals(randomTranslation, polygon2D.getTranslation(), "The created polygon's translation should match the randomly generated translation.");
        assertEquals(randomRotation, polygon2D.getRotation(), "The created polygon's rotation should match the randomly generated rotation.");
        assertEquals(expectedNormalizedRotation, polygon2D.getRotationWithin360(), "The created model's normalized rotation should match the normalized rotation.");
        assertEquals(expectedTransform, polygon2D.getTransformation(), "The created polygon's generated transform should match the expected transform.");
        assertEquals(randomScale, polygon2D.getScale(), "The created polygon's scaling should match the randomly generated scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    public void checkPolygon2DCreation_withPointfArrayParam_andRandomlyGeneratedColorFillShowParams_andRandomlyGeneratedTransformParams_usingMethodChaining() {
        Pointf[] square = DrawUtil.createBox(0f, 0f, 50f);

        Color randomColor = DrawUtil.randomColorWithAlpha();
        boolean shouldFill = Maths.randomBoolean();
        boolean shouldRender = Maths.randomBoolean();

        Pointf randomTranslation = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScale = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float randomRotation = Maths.random(-5000f, 5000f);
        float expectedNormalizedRotation = randomRotation % 360;

        Polygon2D polygon2D = (Polygon2D) new Polygon2D(square)
                .setColor(randomColor)
                .setFilled(shouldFill)
                .setTranslation(randomTranslation)
                .setRotation(randomRotation)
                .setScale(randomScale)
                .setShouldRender(shouldRender);

        AffineTransform expectedTransform = new AffineTransform();
        expectedTransform.setToScale(randomScale.x, randomScale.y);
        expectedTransform.setToRotation(Math.toRadians(randomRotation), polygon2D.getCenter().x, polygon2D.getCenter().y);
        expectedTransform.setToTranslation(randomTranslation.x, randomTranslation.y);

        assertEquals(randomColor, polygon2D.getColor(), "The created polygon's color should match the randomly generated color.");
        assertEquals(shouldFill, polygon2D.isFilled(), "The created polygon's 'fill' option should match the randomly generated fill option.");
        assertEquals(shouldRender, polygon2D.shouldRender(), "The created polygon's 'show' option should match the randomly generated show option.");
        assertEquals(randomTranslation, polygon2D.getTranslation(), "The created polygon's translation should match the randomly generated translation.");
        assertEquals(randomRotation, polygon2D.getRotation(), "The created polygon's rotation should match the randomly generated rotation.");
        assertEquals(expectedNormalizedRotation, polygon2D.getRotationWithin360(), "The created model's normalized rotation should match the normalized rotation.");
        assertEquals(expectedTransform, polygon2D.getTransformation(), "The created polygon's generated transform should match the expected transform.");
        assertEquals(randomScale, polygon2D.getScale(), "The created polygon's scaling should match the randomly generated scale.");
        assertArrayEquals(square, polygon2D.getOriginalPoints(), "The created polygon's Pointf array should match the original Pointf array.");
    }

    @Test
    public void checkModifyPointsOfPolygon2D_withTransformResetting() {
        Pointf[] squarePoints = DrawUtil.createBox(Pointf.Origin, 5f);
        Pointf translationBeforeReset = new Pointf(Maths.random(0f, 1f), Maths.random(0f, 1f));
        float rotationBeforeReset = Maths.random(0f, 1f);
        Pointf scaleBeforeReset = new Pointf(Maths.random(0f, 1f), Maths.random(0f, 1f));

        Polygon2D square = (Polygon2D) new Polygon2D(squarePoints)
                .setTranslation(translationBeforeReset)
                .setRotation(rotationBeforeReset)
                .setScale(scaleBeforeReset);

        Pointf[] newSquarePoints = DrawUtil.createBox(Pointf.Origin.copy().add(1f), 20f);
        square.modifyPoints(newSquarePoints, true, true, true);

        assertArrayEquals(newSquarePoints, square.getPoints(), "The expected points should match the square's points -- no transformations have been performed.");
        assertArrayEquals(newSquarePoints, square.getOriginalPoints(), "The expected points should match the square's points.");
        assertEquals(GameObject.DefaultTranslation, square.getTranslation(), "The square's translation should match the default GameObject translation.");
        assertEquals(GameObject.DefaultRotation, square.getRotation(), "The square's rotation should match the default GameObject rotation.");
        assertEquals(GameObject.DefaultScale, square.getScale(), "The square's scale should match the default GameObject scale.");
    }

    @Test
    public void checkModifyPointsOfPolygon2D_withoutTransformResetting() {
        Pointf[] squarePoints = DrawUtil.createBox(Pointf.Origin, 5f);
        Pointf translationBeforeReset = new Pointf(Maths.random(0f, 1f), Maths.random(0f, 1f));
        float rotationBeforeReset = Maths.random(0f, 1f);
        Pointf scaleBeforeReset = new Pointf(Maths.random(0f, 1f), Maths.random(0f, 1f));

        Polygon2D square = (Polygon2D) new Polygon2D(squarePoints)
                .setTranslation(translationBeforeReset)
                .setRotation(rotationBeforeReset)
                .setScale(scaleBeforeReset);

        Pointf[] newSquarePoints = DrawUtil.createBox(Pointf.Origin.copy().add(1f), 20f);
        square.modifyPoints(newSquarePoints, false, false, false);

        assertArrayEquals(newSquarePoints, square.getPoints(), "The expected points should match the square's points -- no transformations have been performed.");
        assertArrayEquals(newSquarePoints, square.getOriginalPoints(), "The expected points should match the square's points.");
        assertEquals(translationBeforeReset, square.getTranslation(), "The square's translation should match the translation before point modification.");
        assertEquals(rotationBeforeReset, square.getRotation(), "The square's rotation should match the rotation before point modification.");
        assertEquals(scaleBeforeReset, square.getScale(), "The square's scale should match the scale before point modification.");
    }

    @Test
    public void checkPolygon2DTranslation_shouldMatchExpected() {
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, 5f);
        Pointf randomTranslation = new Pointf(
                Maths.random(0f, 1f),
                Maths.random(0f, 1f)
        );
        Pointf[] expectedTranslatedPoints = {
                originalPoints[0].copy().add(randomTranslation),
                originalPoints[1].copy().add(randomTranslation),
                originalPoints[2].copy().add(randomTranslation),
                originalPoints[3].copy().add(randomTranslation)
        };

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        polygon2D.translate(randomTranslation);
        Pointf[] actualTranslatedPoints = polygon2D.getPoints();

        assertArrayEquals(expectedTranslatedPoints, actualTranslatedPoints, "The actual Pointf array, which has been translated, should match the expected Pointf array.");
    }

    @Test
    public void checkPolygon2DRotation_aroundOrigin_shouldMatchExpected() {
        float randomRotationInDegrees = Maths.random(0f, 1f);
        float randomRotationInRadians = (float) Math.toRadians(randomRotationInDegrees);
        float cosOfRotation = (float) Math.cos(randomRotationInRadians);
        float sinOfRotation = (float) Math.sin(randomRotationInRadians);
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf[] expectedRotatedPoints = {
                new Pointf(
                        originalPoints[0].x * cosOfRotation - originalPoints[0].y * sinOfRotation,
                        originalPoints[0].y * cosOfRotation + originalPoints[0].x * sinOfRotation
                ),
                new Pointf(
                        originalPoints[1].x * cosOfRotation - originalPoints[1].y * sinOfRotation,
                        originalPoints[1].y * cosOfRotation + originalPoints[1].x * sinOfRotation
                ),
                new Pointf(
                        originalPoints[2].x * cosOfRotation - originalPoints[2].y * sinOfRotation,
                        originalPoints[2].y * cosOfRotation + originalPoints[2].x * sinOfRotation
                ),
                new Pointf(
                        originalPoints[3].x * cosOfRotation - originalPoints[3].y * sinOfRotation,
                        originalPoints[3].y * cosOfRotation + originalPoints[3].x * sinOfRotation
                )
        };

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        polygon2D.rotate(randomRotationInDegrees, Pointf.Origin);
        Pointf[] actualRotatedPoints = polygon2D.getPoints();

        assertArrayEquals(expectedRotatedPoints, actualRotatedPoints, "The actual Pointf array, which has been rotated about the origin, should match the expected Pointf array.");
    }

    @Test
    public void checkPolygon2DRotation_aroundPolygonCenter_shouldMatchExpected() {
        float randomRotationInDegrees = Maths.random(0f, 1f);
        float randomRotationInRadians = (float) Math.toRadians(randomRotationInDegrees);
        float cosOfRotation = (float) Math.cos(randomRotationInRadians);
        float sinOfRotation = (float) Math.sin(randomRotationInRadians);
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf[] pointsAtOrigin = {
                originalPoints[0].copy().subtract(size / 2f),
                originalPoints[1].copy().subtract(size / 2f),
                originalPoints[2].copy().subtract(size / 2f),
                originalPoints[3].copy().subtract(size / 2f),
        };

        Pointf[] expectedRotatedPoints = {
                new Pointf(
                        pointsAtOrigin[0].x * cosOfRotation - pointsAtOrigin[0].y * sinOfRotation,
                        pointsAtOrigin[0].y * cosOfRotation + pointsAtOrigin[0].x * sinOfRotation
                ).add(size / 2f),
                new Pointf(
                        pointsAtOrigin[1].x * cosOfRotation - pointsAtOrigin[1].y * sinOfRotation,
                        pointsAtOrigin[1].y * cosOfRotation + pointsAtOrigin[1].x * sinOfRotation
                ).add(size / 2f),
                new Pointf(
                        pointsAtOrigin[2].x * cosOfRotation - pointsAtOrigin[2].y * sinOfRotation,
                        pointsAtOrigin[2].y * cosOfRotation + pointsAtOrigin[2].x * sinOfRotation
                ).add(size / 2f),
                new Pointf(
                        pointsAtOrigin[3].x * cosOfRotation - pointsAtOrigin[3].y * sinOfRotation,
                        pointsAtOrigin[3].y * cosOfRotation + pointsAtOrigin[3].x * sinOfRotation
                ).add(size / 2f)
        };

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        polygon2D.rotate(randomRotationInDegrees);
        Pointf[] actualRotatedPoints = polygon2D.getPoints();

        assertArrayEquals(expectedRotatedPoints, actualRotatedPoints, "The actual Pointf array, which has been rotated about its center, should match the expected Pointf array.");
    }

    @Test
    public void checkPolygon2DRotation_aroundRandomPoint_shouldMatchExpected() {
        float randomRotationInDegrees = Maths.random(0f, 1f);
        float randomRotationInRadians = (float) Math.toRadians(randomRotationInDegrees);
        float cosOfRotation = (float) Math.cos(randomRotationInRadians);
        float sinOfRotation = (float) Math.sin(randomRotationInRadians);
        float size = 5f;
        Pointf randomCenter = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf[] pointsAtOrigin = {
                originalPoints[0].copy().subtract(randomCenter),
                originalPoints[1].copy().subtract(randomCenter),
                originalPoints[2].copy().subtract(randomCenter),
                originalPoints[3].copy().subtract(randomCenter),
        };

        Pointf[] expectedRotatedPoints = {
                new Pointf(
                        pointsAtOrigin[0].x * cosOfRotation - pointsAtOrigin[0].y * sinOfRotation,
                        pointsAtOrigin[0].y * cosOfRotation + pointsAtOrigin[0].x * sinOfRotation
                ).add(randomCenter),
                new Pointf(
                        pointsAtOrigin[1].x * cosOfRotation - pointsAtOrigin[1].y * sinOfRotation,
                        pointsAtOrigin[1].y * cosOfRotation + pointsAtOrigin[1].x * sinOfRotation
                ).add(randomCenter),
                new Pointf(
                        pointsAtOrigin[2].x * cosOfRotation - pointsAtOrigin[2].y * sinOfRotation,
                        pointsAtOrigin[2].y * cosOfRotation + pointsAtOrigin[2].x * sinOfRotation
                ).add(randomCenter),
                new Pointf(
                        pointsAtOrigin[3].x * cosOfRotation - pointsAtOrigin[3].y * sinOfRotation,
                        pointsAtOrigin[3].y * cosOfRotation + pointsAtOrigin[3].x * sinOfRotation
                ).add(randomCenter)
        };

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        polygon2D.rotate(randomRotationInDegrees, randomCenter);
        Pointf[] actualRotatedPoints = polygon2D.getPoints();
        assertArrayEquals(expectedRotatedPoints, actualRotatedPoints, "The actual Pointf array, which has been rotated about " + randomCenter + ", should match the expected Pointf array.");
    }

    @Test
    public void checkPolygon2DScaling_aroundOrigin_shouldMatchExpected() {
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf newScale = Pointf.add(randomScaling, GameObject.DefaultScale);
        Pointf[] expectedScaledPoints = {
                originalPoints[0].copy().multiply(newScale),
                originalPoints[1].copy().multiply(newScale),
                originalPoints[2].copy().multiply(newScale),
                originalPoints[3].copy().multiply(newScale)
        };

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        polygon2D.scale(randomScaling, Pointf.Origin);
        Pointf[] actualScaledPoints = polygon2D.getPoints();
        assertArrayEquals(expectedScaledPoints, actualScaledPoints, "The actual Pointf array, which has been scaled, should match the expected Pointf array.");
    }

    @Test
    public void checkPolygon2DScaling_aroundPolygonCenter_shouldMatchExpected() {
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf newScale = Pointf.add(randomScaling, GameObject.DefaultScale);
        Pointf[] pointsAtOrigin = {
                originalPoints[0].copy().subtract(size / 2f),
                originalPoints[1].copy().subtract(size / 2f),
                originalPoints[2].copy().subtract(size / 2f),
                originalPoints[3].copy().subtract(size / 2f),
        };
        Pointf[] expectedScaledPoints = {
                pointsAtOrigin[0].copy().multiply(newScale).add(size / 2f),
                pointsAtOrigin[1].copy().multiply(newScale).add(size / 2f),
                pointsAtOrigin[2].copy().multiply(newScale).add(size / 2f),
                pointsAtOrigin[3].copy().multiply(newScale).add(size / 2f)
        };

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        polygon2D.scale(randomScaling);
        Pointf[] actualScaledPoints = polygon2D.getPoints();
        assertArrayEquals(expectedScaledPoints, actualScaledPoints, "The actual Pointf array, which has been scaled around its center, should match the expected Pointf array.");
    }

    @Test
    public void checkPolygon2DScaling_aroundRandomPoint_shouldMatchExpected() {
        Pointf randomCenter = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        Pointf randomScaling = new Pointf(Maths.random(-50f, 50f), Maths.random(-50f, 50f));
        float size = 5f;
        Pointf[] originalPoints = DrawUtil.createBox(Pointf.Origin, size);

        Pointf newScale = Pointf.add(randomScaling, GameObject.DefaultScale);
        Pointf[] pointsAtOrigin = {
                originalPoints[0].copy().subtract(randomCenter),
                originalPoints[1].copy().subtract(randomCenter),
                originalPoints[2].copy().subtract(randomCenter),
                originalPoints[3].copy().subtract(randomCenter),
        };
        Pointf[] expectedScaledPoints = {
                pointsAtOrigin[0].copy().multiply(newScale).add(randomCenter),
                pointsAtOrigin[1].copy().multiply(newScale).add(randomCenter),
                pointsAtOrigin[2].copy().multiply(newScale).add(randomCenter),
                pointsAtOrigin[3].copy().multiply(newScale).add(randomCenter)
        };

        Polygon2D polygon2D = new Polygon2D(originalPoints);
        polygon2D.scale(randomScaling, randomCenter);
        Pointf[] actualScaledPoints = polygon2D.getPoints();
        assertArrayEquals(expectedScaledPoints, actualScaledPoints, "The actual Pointf array, which has been scaled around " + randomScaling + ", should match the expected Pointf array.");
    }
}
