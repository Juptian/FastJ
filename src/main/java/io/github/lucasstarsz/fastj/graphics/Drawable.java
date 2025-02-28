package io.github.lucasstarsz.fastj.graphics;

import io.github.lucasstarsz.fastj.engine.CrashMessages;
import io.github.lucasstarsz.fastj.engine.FastJEngine;
import io.github.lucasstarsz.fastj.math.Pointf;
import io.github.lucasstarsz.fastj.graphics.game.GameObject;
import io.github.lucasstarsz.fastj.graphics.ui.UIElement;

import io.github.lucasstarsz.fastj.systems.control.Scene;
import io.github.lucasstarsz.fastj.systems.tags.TaggableEntity;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.Arrays;
import java.util.UUID;

/**
 * The abstract class to objects that can be drawn to a {@code Display}.
 * <p>
 * A {@code Drawable} is any object that can be drawn to a {@code Display}, and destroyed (freed from memory).
 *
 * @author Andrew Dey
 * @version 1.0.0
 */
public abstract class Drawable extends TaggableEntity {

    private static final String CollisionErrorMessage = CrashMessages.theGameCrashed("a collision error.");
    private static final String GameObjectErrorMessage = CrashMessages.theGameCrashed("a game object error.");
    private static final String UiElementErrorMessage = CrashMessages.theGameCrashed("a ui element error.");

    private final UUID rawID;
    private final String id;

    /** The shape defining where the Drawable collides. */
    protected Shape collisionPath;
    private boolean shouldRender;
    private Pointf[] boundaries;

    /** Constructs a {@code Drawable}, initializing its internal variables. */
    protected Drawable() {
        rawID = UUID.randomUUID();
        id = "DRAWABLE$" + getClass().getSimpleName() + "_" + rawID;
    }

    /**
     * Destroys all memory the {@code Drawable} uses.
     * <p>
     * This also removes any internal references that the {@code Drawable} may have.
     *
     * @param originScene The origin of this {@code Drawable}.
     */
    public abstract void destroy(Scene originScene);

    /**
     * Gets the collision path of the {@code Drawable}.
     *
     * @return The collision path of the {@code Drawable}, as a {@code Shape}.
     */
    public Shape getCollisionPath() {
        return collisionPath;
    }

    /**
     * Sets the collision path to the specified parameter.
     *
     * @param path {@code Shape} parameter that the collision path will be set to.
     */
    protected void setCollisionPath(Shape path) {
        collisionPath = path;
    }

    /**
     * Gets the {@code String} ID of the {@code Drawable}.
     *
     * @return String that represents the ID of the {@code Drawable}.
     */
    public String getID() {
        return id;
    }

    /**
     * Gets the raw {@code UUID} of the {@code Drawable}.
     *
     * @return The {@code UUID} that represents the raw ID of the {@code Drawable}.
     */
    public UUID getUUID() {
        return rawID;
    }

    /**
     * Gets the boundaries of the {@code Drawable}.
     * <p>
     * Bounds are in the same order as specified in the {@link Boundary}.
     * <p>
     * If you're looking to get a specific bound, use {@code getBound(Boundary)} instead.
     *
     * @return The {@code Pointf} array that contains the bounds of the {@code Drawable}.
     */
    public Pointf[] getBounds() {
        return boundaries;
    }

    /**
     * Sets the boundaries of the {@code Drawable} to the specified {@code Pointf} array.
     * <p>
     * The specified array must be exactly 4 points. If there is any deviancy from this, the game will crash out with an
     * error specifying this.
     *
     * @param bounds The {@code Pointf} array that the boundaries of the {@code Drawable} will be set to.
     */
    protected void setBounds(Pointf[] bounds) {
        if (bounds.length != 4) {
            FastJEngine.error(CrashMessages.illegalAction(getClass()),
                    new IllegalArgumentException("The boundaries for a Drawable must only have 4 points."));
        }

        boundaries = bounds;
    }

    /**
     * Gets one of the boundaries of the {@code Drawable}, based on the specified {@code Boundary} parameter.
     *
     * @param boundary The requested {@code Boundary}.
     * @return The bound that corresponds with the specified {@code Boundary}.
     */
    public Pointf getBound(Boundary boundary) {
        return boundaries[boundary.location];
    }

    /**
     * Gets the center point of the {@code Drawable}.
     *
     * @return The center point, as a {@code Pointf}.
     */
    public Pointf getCenter() {
        return DrawUtil.centerOf(boundaries);
    }

    /**
     * Gets the value that defines whether the {@code Drawable} should be rendered.
     *
     * @return Boolean value that defines whether the {@code Drawable} should be rendered.
     */
    public boolean shouldRender() {
        return shouldRender;
    }

    /**
     * Sets whether the {@code Drawable} should be rendered.
     *
     * @param shouldBeRendered Boolean parameter that defines whether the {@code Drawable} should be rendered.
     * @return The {@code Drawable}, for method chaining.
     */
    public Drawable setShouldRender(boolean shouldBeRendered) {
        shouldRender = shouldBeRendered;
        return this;
    }

    /**
     * Determines whether or not two objects are colliding (intersection).
     *
     * @param obj The other {@code Drawable} that is being tested against this {@code Drawable}.
     * @return Boolean value that states whether the two {@code Drawable}s intersect.
     */
    public boolean collidesWith(Drawable obj) {
        Area otherObject, thisObject;

        try {
            otherObject = new Area(obj.collisionPath);
        } catch (NullPointerException e) {
            if (!FastJEngine.getLogicManager().isSwitchingScenes()) {
                FastJEngine.error(CollisionErrorMessage, new NullPointerException("Collision path for Drawable with id: " + obj.id + " is null"));
            }
            return false;
        }

        try {
            thisObject = new Area(collisionPath);
        } catch (NullPointerException e) {
            if (!FastJEngine.getLogicManager().isSwitchingScenes()) {
                FastJEngine.error(CollisionErrorMessage, new NullPointerException("Collision path for Drawable with id: " + id + " is null"));
            }
            return false;
        }

        otherObject.intersect(thisObject);
        return !otherObject.isEmpty();
    }

    /**
     * Adds the {@code Drawable} to the {@code Scene} parameter's list of game objects.
     *
     * @param origin {@code Scene} parameter that will add the {@code Drawable} to its list of game objects.
     * @return the {@code Drawable} is returned for method chaining.
     */
    public GameObject addAsGameObject(Scene origin) {
        if (this instanceof GameObject) {
            origin.drawableManager.addGameObject((GameObject) this);
            return (GameObject) this;
        } else {
            FastJEngine.error(GameObjectErrorMessage, new IllegalStateException("Cannot add non-game object as a game object."));
            return null;
        }
    }

    /**
     * Adds the {@code Drawable} to the {@code Scene} parameter's list of GUI objects.
     *
     * @param origin {@code Scene} parameter that will add the {@code Drawable} to its list of GUI objects.
     * @return the {@code Drawable} is returned for method chaining.
     */
    public UIElement addAsGUIObject(Scene origin) {
        if (this instanceof UIElement) {
            origin.drawableManager.addGUIObject((UIElement) this);
            return (UIElement) this;
        } else {
            FastJEngine.error(UiElementErrorMessage, new IllegalStateException("Cannot add non-ui object as a ui object."));
            return null;
        }
    }

    /**
     * Translates the boundaries of the {@code Drawable} by the specified {@code Pointf}.
     *
     * @param translation {@code Pointf} that the boundaries of the {@code Drawable} will be moved by.
     */
    protected void translateBounds(Pointf translation) {
        for (Pointf bound : boundaries) {
            bound.add(translation);
        }
    }

    /**
     * Destroys the {@code Drawable}'s {@code Drawable} components, as well as any references the {@code Drawable} has
     * within the {@code Scene} parameter.
     *
     * @param origin {@code Scene} parameter that will have all references to this {@code Drawable} removed.
     */
    protected void destroyTheRest(Scene origin) {
        origin.removeTaggableEntity(this);
        clearTags();

        collisionPath = null;
        boundaries = null;
    }

    @Override
    public String toString() {
        return "Drawable{" +
                "rawID=" + rawID +
                ", id='" + id + '\'' +
                ", collisionPath=" + collisionPath +
                ", shouldRender=" + shouldRender +
                ", boundaries=" + Arrays.toString(boundaries) +
                '}';
    }
}
