package ecumene.exo.view.runtime;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.runtime.viewer.ViewerRunnable;
import ecumene.exo.sim.SimContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.ExceptionListener;

public class NeoRuntimeViewer extends ViewerRunnable {
    private JFrame parent;
    private JPanel root;
    private JPanel galaxyConfig;
    private JSpinner galaxyFocusSpinner;
    private JLabel galaxyFocus;
    private JButton galaxyFocusReset;
    private JSpinner solarFocusSpinner;
    private JPanel solarConfig;
    private JLabel solarFocus;
    private JButton solarFocusReset;
    private JPanel planetConfig;
    private JButton planetClearTracked;
    private JPanel simulatorConfig;
    private JButton toggleStep;
    private JLabel currentStep;
    private JLabel currentStepNo;
    private JSpinner targetStepsSpinner;
    private JLabel targetSteps;

    private Timer simStepper;
    private int   simStepsPerT = 1;

    public NeoRuntimeViewer(int id, ExceptionListener listener){
        super(id, listener);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            listener.exceptionThrown(e);
        }
    }

    private void createUIComponents() {
        galaxyFocusReset = new JButton("Reset");
        galaxyFocusReset.addActionListener(actionEvent -> ExoRuntime.INSTANCE.getContext().getGalaxy().setFollow(-1));
        galaxyFocusSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        galaxyFocusSpinner.addChangeListener(changeEvent -> {
            JSpinner spinner = ( JSpinner ) changeEvent.getSource();
            SpinnerModel spinnerModel = spinner.getModel();
            int spin = (Integer) spinnerModel.getValue();
            if (spin==0) ExoRuntime.INSTANCE.getContext().getGalaxy().setFollow(-1); // -1 points to nothing
            else         ExoRuntime.INSTANCE.getContext().getGalaxy().setFollow((Integer) spin + 1);
        });

        solarFocusReset = new JButton("Reset");
        solarFocusReset.addActionListener(actionEvent -> ExoRuntime.INSTANCE.getContext().getSolarSystem().setFollow(-1));
        solarFocusSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        solarFocusSpinner.addChangeListener(changeEvent -> {
            JSpinner spinner = ( JSpinner ) changeEvent.getSource();
            SpinnerModel spinnerModel = spinner.getModel();
            int spin = (Integer) spinnerModel.getValue();
            if (spin==0) ExoRuntime.INSTANCE.getContext().getSolarSystem().setFollow(-1); // -1 points to nothing
            else         ExoRuntime.INSTANCE.getContext().getSolarSystem().setFollow((Integer) spin + 1);
        });

        targetStepsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        targetStepsSpinner.addChangeListener(changeEvent -> {
            JSpinner spinner = ( JSpinner ) changeEvent.getSource();
            SpinnerModel spinnerModel = spinner.getModel();
            simStepsPerT = (Integer) spinnerModel.getValue();
        });
        toggleStep = new JButton("Run Simulation");
        toggleStep.addActionListener(actionEvent -> {
            ExoRuntime.INSTANCE.getContext().running = !ExoRuntime.INSTANCE.getContext().running;
            if(simStepper != null) simStepper.stop();
            simStepper = new Timer(60, new StepTimer());
            simStepper.start();
        });

        planetClearTracked = new JButton("Clear Tracked Moons");
        planetClearTracked.addActionListener(ae -> ExoRuntime.INSTANCE.getContext().getPlanet().getMap().clearTrackedPositions());
    }

    @Override
    public void init() throws Throwable {
        parent = new JFrame("Runtime viewer");
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createUIComponents();
        parent.setContentPane(root);

        parent.pack();
        parent.setLocationRelativeTo(null);
        parent.setVisible(true);
    }

    @Override
    public void kill(int id) {
        parent.dispose();
    }

    private class StepTimer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (ExoRuntime.INSTANCE.getContext().running) {
                for(int i = 0; i < simStepsPerT; i++) {
                    ExoRuntime.INSTANCE.getContext().step();
                    currentStepNo.setText("" + ExoRuntime.INSTANCE.getContext().getSteps());
                    currentStepNo.repaint();
                    parent.pack();
                }
            } // If running step, if not skip frame
        }
    }

    @Override public void onContextChanged(SimContext context) {}
    @Override public void onStep(SimContext context, int step) {}
}
