
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tenzin
 */
public class SpriteAnimation extends Transition{
    private final ImageView iv;
    private final int count;
    private final int columns;
    private int offsetX;
    private int offsetY;
    private final int width;
    private final int height;
    
    public SpriteAnimation(String image, int count, int columns, int offsetX, int offsetY,
                            int width, int height, Duration duration)
    {
        this.iv = new ImageView(image);
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = width;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(Animation.INDEFINITE);
        iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            iv.setViewport(new Rectangle2D(x, y, width, height));
    }
    
    public void setOffset(int x, int y)
    {
        offsetX = x;
        offsetY = y;
    }
    
    public ImageView getIV()
    {
        return iv;
    }
    
}