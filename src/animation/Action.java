package animation;

import model.Shape;

public interface Action {

    void playTick(double tick);
    int getTicks();
    void setTicks(int ticks);
    int getPriority();
    String getName();
    Shape getShape();

}
