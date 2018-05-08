package animation;

import controller.SceneController;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import model.Shape;

import java.io.PrintStream;
import java.util.*;

public class Scenario {

    private PriorityQueue<Action> queueActions;
    private PriorityQueue<Action> copyActions;
    private Comparator<Action> comparator = (o1, o2) -> {
        if(o1.getPriority() > o2.getPriority())
            return 1;
        else if(o1.getPriority() < o2.getPriority())
            return -1;
        else
            return 0;
    };
    private Vector<Action> actionsNow;
    private AnimationTimer timer;
    private static Scenario instance;
    private HashMap<Shape, ObservableList<Transform>> memberTransforms;

    public Scenario() {
        actionsNow = new Vector<>();
        queueActions = new PriorityQueue<>(comparator);
        copyActions = new PriorityQueue<>(comparator);
        memberTransforms = new HashMap<>();
        instance = this;
    }

    public void addAction(Action action){
        queueActions.add(action);
        copyActions.add(action);
        ObservableList<Transform> vec = memberTransforms.get(action.getShape());
        if(vec == null){
            memberTransforms.put(action.getShape(), action.getShape().getTranslationGroup().getTransforms());
        }
    }

    public void addAll(Action ... actions){
        queueActions.addAll(Arrays.asList(actions));
        copyActions.addAll(Arrays.asList(actions));
    }

    public void play(int ticks) {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(actionsNow.size() == 0){
                    if(!makeFullActions()){
                        stopTimer();
                    }
                }
                boolean flag = false;
                for (Action act:actionsNow) {
                    if(act.getTicks() < ticks) {
                        act.playTick((double) 1 / ticks);
                        flag = true;
                    }
                }
                if(!flag)
                    makeEmptyActions();

            }
        };
        timer.start();
    }

    private void stopTimer(){
        timer.stop();
        queueActions.addAll(copyActions);
        for (Action act:queueActions) {
            act.setTicks(0);
        }
        for (Shape shape: memberTransforms.keySet()) {
            shape.getTranslationGroup().getTransforms().clear();
            shape.getTranslationGroup().getTransforms().addAll(memberTransforms.get(shape));
        }
        SceneController.getInstance().onStopScenario();
    }

    private boolean makeFullActions(){
        if(queueActions.size() == 0)
            return false;
        int priority = queueActions.peek().getPriority();
        while(queueActions.size() != 0 && queueActions.peek(). getPriority() == priority){
            actionsNow.add(queueActions.poll());
        }
        return true;
    }

    private void makeEmptyActions(){
        if(actionsNow.size() != 0){
            actionsNow.clear();
        }
    }

    public static Scenario getInstance(){
        return instance;
    }


}
