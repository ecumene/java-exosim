package ecumene.exo.view.runtime;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.runtime.viewer.ViewerRunnable;
import ecumene.exo.sim.SimContext;
import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.galaxy.IExoGalaxyObject;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.planet.IExoPlanetObject;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.solar.IExoSolarObject;
import ecumene.exo.view.fbd.FBDViewer;
import ecumene.exo.view.fbd.FreeBody;
import ecumene.exo.view.fbd.FreeBodyFactory;
import ecumene.exo.view.fbd.FreeBodyShape;
import org.joml.Vector2f;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.ExceptionListener;
import java.util.HashMap;
import java.util.Map;

public class RuntimeManager extends ViewerRunnable {
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
    private JButton galaxyOpenInFBD;
    private JRadioButton galaxyFBDUPS;
    private JRadioButton solarFBDUPS;
    private JButton solarOpenInFBD;
    private JSpinner moonFocusSpinner;
    private JButton clearTrackingDataButton;
    private JButton planetOpenInFBD;
    private JRadioButton planetFBDUPS;
    private JRadioButton planetOpenPlanetFBD;

    private Timer simStepper;
    private int simStepsPerT = 1;

    private Map<Pair<IExoGalaxyObject, ExoGalaxyMap>, Pair<FreeBody, FBDViewer>> galaxyFBPairs;
    private Map<Pair<IExoSolarObject, ExoSolarMap>,  Pair<FreeBody, FBDViewer>> solarFBPairs;
    private Map<Pair<IExoPlanetObject, ExoPlanetMap>, Pair<FreeBody, FBDViewer>> planetFBPairs;

    public RuntimeManager(int id, ExceptionListener listener) {
        super(id, listener);
        galaxyFBPairs = new HashMap<Pair<IExoGalaxyObject, ExoGalaxyMap>, Pair<FreeBody, FBDViewer>>();
        solarFBPairs  = new HashMap<Pair<IExoSolarObject, ExoSolarMap>,   Pair<FreeBody, FBDViewer>>();
        planetFBPairs = new HashMap<Pair<IExoPlanetObject, ExoPlanetMap>, Pair<FreeBody, FBDViewer>>();
    }

    private boolean gcfbd = true, scfbd = true, pcfbd = true, ppfbd = false;
    private int focusMoon = 0; // 0 = moon not planet

    private void createUIComponents() {
        galaxyOpenInFBD = new JButton("Open in FBD Viewer");
        galaxyOpenInFBD.addActionListener(actionEvent -> {
            FreeBody body;

            IExoGalaxyObject object = ExoRuntime.INSTANCE.getContext().getGalaxy().getMap().getOrbiters().get(ExoRuntime.INSTANCE.getContext().getGalaxy().getFollow() + 1);
            ExoGalaxyMap map = ExoRuntime.INSTANCE.getContext().getGalaxy().getMap();

            FBDViewer frame = new FBDViewer(new Vector2f(0, 1), 500, 500);
            if(gcfbd) {
                galaxyFBPairs.put(new Pair<>(object, map), new Pair<>(null, frame)); // Null for initialized, but new
                body = new FreeBody(FreeBodyShape.BALL, 0);
            } else {
                body = FreeBodyFactory.createBody(map, object);
            }
            frame.setBody(body);
        });

        solarOpenInFBD = new JButton("Open in FBD Viewer");
        solarOpenInFBD.addActionListener(actionEvent -> {
            FreeBody body;
            IExoSolarObject object = ExoRuntime.INSTANCE.getContext().getSolarSystem().getSolarMap().getObjects().get(ExoRuntime.INSTANCE.getContext().getSolarSystem().getFollowing());
            ExoSolarMap map = ExoRuntime.INSTANCE.getContext().getSolarSystem().getSolarMap();
            FBDViewer frame = new FBDViewer(new Vector2f(0, 1), 500, 500);
            if(scfbd) {
                solarFBPairs.put(new Pair<>(object, map), new Pair<>(null, frame)); // Null for initialized, but new
                body = new FreeBody(FreeBodyShape.BALL, 0);
            } else {
                body = FreeBodyFactory.createBody(map, object);
            }
            frame.setBody(body);
        });

        planetOpenInFBD = new JButton("Open in FBD Viewer");
        planetOpenInFBD.addActionListener(actionEvent1 -> {
            FreeBody body;
            IExoPlanetObject object = ExoRuntime.INSTANCE.getContext().getPlanet().getMap().getObjects().get(focusMoon);
            ExoPlanetMap map = ExoRuntime.INSTANCE.getContext().getPlanet().getMap();

            FBDViewer frame = new FBDViewer(new Vector2f(0, 1), 500, 500);
            if (ppfbd){
                object = ExoRuntime.INSTANCE.getContext().getPlanet().getMap().getPlanet();
            }

            if(pcfbd) {
                planetFBPairs.put(new Pair<>(object, map), new Pair<>(null, frame)); // Null for initialized, but new
                body = new FreeBody(FreeBodyShape.BALL, 0);
            } else {
                body = FreeBodyFactory.createBody(map, object);
            }
            frame.setBody(body);
        });

        galaxyFBDUPS = new JRadioButton("Update every step");
        galaxyFBDUPS.setSelected(true);
        galaxyFBDUPS.addActionListener(actionEvent -> {
            gcfbd = ((JRadioButton) actionEvent.getSource()).isSelected();
        });

        solarFBDUPS = new JRadioButton("Update every step");
        solarFBDUPS.setSelected(true);
        solarFBDUPS.addActionListener(actionEvent -> {
            scfbd = ((JRadioButton) actionEvent.getSource()).isSelected();
        });

        galaxyFocusReset = new JButton("Reset");
        galaxyFocusReset.addActionListener(actionEvent -> ExoRuntime.INSTANCE.getContext().getGalaxy().setFollow(-1));
        galaxyFocusSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        galaxyFocusSpinner.addChangeListener(changeEvent -> ExoRuntime.INSTANCE.getContext().getGalaxy().setFollow((Integer) ((JSpinner) changeEvent.getSource()).getModel().getValue()));

        solarFocusReset = new JButton("Reset");
        solarFocusReset.addActionListener(actionEvent -> ExoRuntime.INSTANCE.getContext().getSolarSystem().setFollow(-1));
        solarFocusSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        solarFocusSpinner.addChangeListener(changeEvent -> ExoRuntime.INSTANCE.getContext().getSolarSystem().setFollow((Integer) ((JSpinner) changeEvent.getSource()).getModel().getValue()));

        clearTrackingDataButton = new JButton("Clear Tracking Data");
        clearTrackingDataButton.addActionListener(actionEvent -> ExoRuntime.INSTANCE.getContext().getPlanet().getMap().clearTrackedPositions());

        planetFBDUPS = new JRadioButton("Update every step");
        planetFBDUPS.addActionListener(actionEvent -> pcfbd = ((JRadioButton)actionEvent.getSource()).isSelected());
        moonFocusSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        moonFocusSpinner.addChangeListener(changeEvent -> focusMoon = (Integer) ((JSpinner) changeEvent.getSource()).getModel().getValue());

        planetOpenPlanetFBD = new JRadioButton("Open planet");
        planetOpenPlanetFBD.addActionListener(actionEvent -> ppfbd = ((JRadioButton) actionEvent.getSource()).isSelected());

        targetStepsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        targetStepsSpinner.addChangeListener(changeEvent -> {
            JSpinner spinner = (JSpinner) changeEvent.getSource();
            SpinnerModel spinnerModel = spinner.getModel();
            simStepsPerT = (Integer) spinnerModel.getValue();
        });
        toggleStep = new JButton("Run Simulation");
        toggleStep.addActionListener(actionEvent -> {
            ExoRuntime.INSTANCE.getContext().running = !ExoRuntime.INSTANCE.getContext().running;
            if (simStepper != null) simStepper.stop();
            simStepper = new Timer(60, new StepTimer());
            simStepper.start();
        });

        planetClearTracked = new JButton("Clear Tracked Moons");
        planetClearTracked.addActionListener(ae -> ExoRuntime.INSTANCE.getContext().getPlanet().getMap().clearTrackedPositions());
    }

    private class StepTimer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (ExoRuntime.INSTANCE.getContext().running) {
                for (int i = 0; i < simStepsPerT; i++) {
                    for(Map.Entry<Pair<IExoSolarObject, ExoSolarMap>, Pair<FreeBody, FBDViewer>> entry : solarFBPairs.entrySet()) {
                        Pair<IExoSolarObject, ExoSolarMap> solarKey = (Pair<IExoSolarObject, ExoSolarMap>) entry.getKey();
                        Pair<FreeBody, FBDViewer>          freebKey = (Pair<FreeBody, FBDViewer>)          entry.getValue();
                        FreeBody body = FreeBodyFactory.createBody(solarKey.getSecond(), solarKey.getFirst());
                        freebKey.getSecond().setBody(body);
                    }

                    for(Map.Entry<Pair<IExoGalaxyObject, ExoGalaxyMap>, Pair<FreeBody, FBDViewer>> entry : galaxyFBPairs.entrySet()) {
                        Pair<IExoGalaxyObject, ExoGalaxyMap> galaxyKey= (Pair<IExoGalaxyObject, ExoGalaxyMap>) entry.getKey();
                        Pair<FreeBody, FBDViewer>            freebKey = (Pair<FreeBody, FBDViewer>)          entry.getValue();
                        FreeBody body = FreeBodyFactory.createBody(galaxyKey.getSecond(), galaxyKey.getFirst());
                        freebKey.getSecond().setBody(body);
                    }

                    for(Map.Entry<Pair<IExoPlanetObject, ExoPlanetMap>, Pair<FreeBody, FBDViewer>> entry : planetFBPairs.entrySet()) {
                        Pair<IExoPlanetObject, ExoPlanetMap> planetKey = (Pair<IExoPlanetObject, ExoPlanetMap>) entry.getKey();
                        Pair<FreeBody, FBDViewer>            freebKey  = (Pair<FreeBody, FBDViewer>) entry.getValue();
                        FreeBody body = FreeBodyFactory.createBody(planetKey.getSecond(), planetKey.getFirst());
                        freebKey.getSecond().setBody(body);
                    }
                    
                    ExoRuntime.INSTANCE.getContext().step();
                    currentStepNo.setText("" + ExoRuntime.INSTANCE.getContext().getSteps());
                    currentStepNo.repaint();
                    parent.pack();
                }
            } // If running step, if not skip frame
        }
    }

    @Override
    public void onContextChanged(SimContext context) {
    }

    @Override
    public void onStep(SimContext context, int step) {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        root = new JPanel();
        root.setLayout(new GridLayoutManager(6, 1, new Insets(10, 10, 10, 10), -1, -1));
        galaxyConfig = new JPanel();
        galaxyConfig.setLayout(new GridLayoutManager(3, 3, new Insets(5, 5, 5, 5), -1, -1));
        root.add(galaxyConfig, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        galaxyConfig.setBorder(BorderFactory.createTitledBorder(null, "Galaxy", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        galaxyConfig.add(galaxyFocusSpinner, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        galaxyFocus = new JLabel();
        galaxyFocus.setText("Galaxy Focus:");
        galaxyConfig.add(galaxyFocus, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        galaxyFocusReset.setText("Reset");
        galaxyConfig.add(galaxyFocusReset, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        galaxyOpenInFBD.setText("Open in FBD Viewer");
        galaxyConfig.add(galaxyOpenInFBD, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        galaxyFBDUPS.setText("Update every step");
        galaxyConfig.add(galaxyFBDUPS, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        galaxyConfig.add(separator1, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        solarConfig = new JPanel();
        solarConfig.setLayout(new GridLayoutManager(3, 3, new Insets(5, 5, 5, 5), -1, -1));
        root.add(solarConfig, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        solarConfig.setBorder(BorderFactory.createTitledBorder(null, "Solar System", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        solarConfig.add(solarFocusSpinner, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        solarFocus = new JLabel();
        solarFocus.setText("Solar Focus:");
        solarConfig.add(solarFocus, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        solarFocusReset.setText("Reset");
        solarConfig.add(solarFocusReset, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        solarFBDUPS.setText("Update every step");
        solarConfig.add(solarFBDUPS, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        solarOpenInFBD.setText("Open in FBD Viewer");
        solarConfig.add(solarOpenInFBD, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        solarConfig.add(separator2, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        planetConfig = new JPanel();
        planetConfig.setLayout(new GridLayoutManager(4, 3, new Insets(5, 5, 5, 5), -1, -1));
        root.add(planetConfig, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        planetConfig.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Planet", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        moonFocusSpinner = new JSpinner();
        planetConfig.add(moonFocusSpinner, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Moon Focus: ");
        planetConfig.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        clearTrackingDataButton = new JButton();
        clearTrackingDataButton.setText("Clear Tracking Data");
        planetConfig.add(clearTrackingDataButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        planetConfig.add(panel1, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Color");
        panel1.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Interval");
        panel1.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        planetClearTracked.setText("Clear Tracked Moons");
        panel1.add(planetClearTracked, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator3 = new JSeparator();
        panel1.add(separator3, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Add Tracking Data:");
        planetConfig.add(label4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator4 = new JSeparator();
        planetConfig.add(separator4, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        simulatorConfig = new JPanel();
        simulatorConfig.setLayout(new GridLayoutManager(2, 3, new Insets(5, 5, 5, 5), -1, -1));
        simulatorConfig.setBackground(new Color(-16765361));
        root.add(simulatorConfig, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        simulatorConfig.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Simulator Config", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        toggleStep.setText("Run Simulation");
        simulatorConfig.add(toggleStep, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        currentStep = new JLabel();
        currentStep.setForeground(new Color(-723466));
        currentStep.setText("Current Step: ");
        simulatorConfig.add(currentStep, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        currentStepNo = new JLabel();
        currentStepNo.setForeground(new Color(-723466));
        currentStepNo.setText("0");
        simulatorConfig.add(currentStepNo, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        targetSteps = new JLabel();
        targetSteps.setForeground(new Color(-723466));
        targetSteps.setText("Target steps per T");
        simulatorConfig.add(targetSteps, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        simulatorConfig.add(targetStepsSpinner, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        root.add(spacer1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 20), new Dimension(-1, 20), new Dimension(-1, 20), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

}
