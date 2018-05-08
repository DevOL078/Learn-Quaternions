package animation;

import controller.SceneController;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
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
    private HashMap<Shape, ObservableList<Transform>> memberTransforms;

    public Scenario() {
        actionsNow = new Vector<>();
        queueActions = new PriorityQueue<>(comparator);
        copyActions = new PriorityQueue<>(comparator);
        memberTransforms = new HashMap<>();
    }

    public void addAction(Action action){
        queueActions.add(action);
        copyActions.add(action);
        ObservableList<Transform> vec = memberTransforms.get(action.getShape());
        if(vec == null){
            memberTransforms.put(action.getShape(), copyTransforms(action.getShape().getTranslationGroup().getTransforms()));
        }
    }

    public void deleteAction(Action action){
        queueActions.remove(action);
        copyActions.remove(action);
    }

    private ObservableList<Transform> copyTransforms(ObservableList<Transform> list){
        ObservableList<Transform> copyList = FXCollections.observableArrayList();
        Transform copy = null;
        for (Transform transform: list) {
            if(transform instanceof Translate){
                Translate translate = (Translate)transform;
                copy = new Translate(translate.getX(), translate.getY(), translate.getZ());
            }else if(transform instanceof Rotate){
                Rotate rotate = (Rotate)transform;
                copy = rotate.clone();
            }
            if(copy != null) {
                copyList.add(copy);
            }
        }
        return copyList;
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

    public void stopTimer(){
        timer.stop();
        actionsNow.clear();
        queueActions.clear();
        queueActions.addAll(copyActions);
        for (Action act:queueActions) {
            act.setTicks(0);
        }
        for (Shape shape: memberTransforms.keySet()) {
            shape.getTranslationGroup().getTransforms().clear();
            shape.getTranslationGroup().getTransforms().addAll(memberTransforms.get(shape));
            shape.getRotationGroup().getTransforms().clear();
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

    public void addMemberTransform(Shape shape, Transform transform){
        Transform copy = null;
        if(transform instanceof Translate){
            Translate translate = (Translate)transform;
            copy = new Translate(translate.getX(), translate.getY(), translate.getZ());
        }else if(transform instanceof Rotate){
            Rotate rotate = (Rotate)transform;
            copy = rotate.clone();
        }
        if(copy != null) {
            ObservableList<Transform> list = memberTransforms.get(shape);
            if (list != null) {
                list.add(copy);
            } else {
                list = FXCollections.observableArrayList();
                list.add(transform);
                memberTransforms.put(shape, list);
            }
        }
    }

    public void deleteShape(Shape shape){
        if(memberTransforms.get(shape) != null){
            memberTransforms.remove(shape);
        }
        Vector<Action> toDelete = new Vector<>();
        for(Action act: queueActions){
            if(act.getShape().equals(shape)){
                toDelete.add(act);
            }
        }
        queueActions.removeAll(toDelete);
        toDelete.clear();
        for(Action act: copyActions){
            if(act.getShape().equals(act)){

                toDelete.add(act);
            }
        }
        copyActions.removeAll(toDelete);
        toDelete.clear();
    }

}
