package com.sandradita.lg.quick_settings.ui.abs;

import com.sandradita.lg.quick_settings.ui.controllers.settings.TransitionsCoordinator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aditkovskaya
 */

public abstract class BaseTransitionsCoordinator {

    private List<CircleController> controllerQueue = new ArrayList<>();

    private BaseTransitionsCoordinator.QCircleTransitions transitions;

    public BaseTransitionsCoordinator(BaseTransitionsCoordinator.QCircleTransitions transitions) {
        this.transitions = transitions;
    }

    /**
     * Prepares last view when parent should be resumed.
     */
    public void onResume() {
        int queueSize = controllerQueue.size();
        if (queueSize > 0) {
            resumeController(controllerQueue.get(queueSize - 1));
        }
    }

    /**
     * Prepares all views before pause.
     */
    public void onPause() {
        for (CircleController controller : controllerQueue) {
            pauseController(controller);
        }
    }

    /**
     * Prepares all views before parent should be destroyed.
     */
    public void onDestroy() {
        for (CircleController controller : controllerQueue) {
            destroyController(controller);
        }
    }

    /**
     * Adds selected controller to stack and shows it by {@link TransitionsCoordinator.QCircleTransitions}. Pauses previous
     * controller.
     */
    public void pushController(CircleController controller) {
        int queueSize = controllerQueue.size();
        if (queueSize > 0) {
            pauseController(controllerQueue.get(queueSize - 1));
        }
        if (controller != null) {
            controllerQueue.add(controller);
            showController(controller);
        }
    }

    /**
     * Removes current controller from stack and shows previous by {@link TransitionsCoordinator.QCircleTransitions}.
     */
    public void popController() {
        int queueSize = controllerQueue.size();
        if (queueSize > 0) {
            CircleController currentController = controllerQueue.get(queueSize - 1);
            destroyController(currentController);
            controllerQueue.remove(currentController);

            showController(controllerQueue.get((--queueSize) - 1));
        }
    }

    private void showController(CircleController controller) {
        resumeController(controller);
        if (transitions != null) transitions.openController(controller);
    }

    /**
     * Calls {@link CircleController#onResume()} ()} when view should be resumed.
     */
    private void resumeController(CircleController controller) {
        if (!controller.resumed) {
            // Is rewritten here, if user forgets to call onResume of parent class.
            controller.resumed = true;
            controller.paused = false;
            controller.onResume();
        }
    }

    /**
     * Calls {@link CircleController#onPause()} ()} when view should be paused.
     */
    private void pauseController(CircleController controller) {
        if (!controller.paused) {
            controller.paused = true;
            controller.resumed = false;
            controller.onPause();
        }
    }

    /**
     * Calls {@link CircleController#onDestroy()} when view should be destroyed.
     */
    private void destroyController(CircleController controller) {
        if (!controller.destroyed) {
            controller.destroyed = true;
            controller.resumed = false;
            controller.onDestroy();
        }
    }

    public interface QCircleTransitions {
        void openController(CircleController controller);
    }

}
