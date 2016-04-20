package ecumene.exo.view.fbd;

import ecumene.exo.sim.common.physics.instant.InsFBody;
import ecumene.exo.utils.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LoggedFBDViewer extends FBDViewer {

    // Pair of the dataset and the chart's panel (data-component+jcomponent)
    private Pair<DefaultCategoryDataset, ChartPanel> avtGraph;
    private Pair<DefaultCategoryDataset, ChartPanel> vvtGraph;
    private Pair<DefaultCategoryDataset, ChartPanel> dvtGraph;

    private int discardItr = 0;

    public LoggedFBDViewer(Vector2f north, int width, int height, int discardItr){
        super(north, width, height);
        this.discardItr = discardItr;

        DefaultCategoryDataset categoryDatasetAVT = new DefaultCategoryDataset();
        DefaultCategoryDataset categoryDatasetVVT = new DefaultCategoryDataset();
        DefaultCategoryDataset categoryDatasetDVT = new DefaultCategoryDataset();
        JFreeChart avtChart = ChartFactory.createLineChart("Acceleration v Time", "Step", "Acceleration (Magnitude)", categoryDatasetAVT, PlotOrientation.VERTICAL, false, true, false);
        JFreeChart vvtChart = ChartFactory.createLineChart("Velocity v Time"    , "Step", "Velocity (Magnitude)",     categoryDatasetVVT, PlotOrientation.VERTICAL, false, true, false);
        JFreeChart dvtChart = ChartFactory.createLineChart("Distance v Time",     "Step", "Displacement (Magnitude)", categoryDatasetDVT, PlotOrientation.VERTICAL, false, true, false);
        avtGraph = new Pair(categoryDatasetAVT, new ChartPanel(avtChart));
        vvtGraph = new Pair(categoryDatasetVVT, new ChartPanel(vvtChart));
        dvtGraph = new Pair(categoryDatasetDVT, new ChartPanel(dvtChart));
        getContentPane().remove(viewerPane);
        JPanel graphs = new JPanel(new GridLayout(2, 2));
        graphs.add(this.viewerPane);
        graphs.add(avtGraph.getSecond());
        graphs.add(vvtGraph.getSecond());
        graphs.add(dvtGraph.getSecond());
        getContentPane().add(graphs);
        JButton clearGraphs = new JButton("Clear Graphs");
        clearGraphs.addActionListener(actionEvent -> {
            avtGraph.getFirst().clear();
            vvtGraph.getFirst().clear();
            dvtGraph.getFirst().clear();
        });
        topPanel.add(clearGraphs);
        JSpinner discardItrSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        discardItrSpinner.addChangeListener(changeEvent -> {
            JSpinner spinner = (JSpinner) changeEvent.getSource();
            SpinnerModel spinnerModel = spinner.getModel();
            this.discardItr = (Integer) spinnerModel.getValue();
        });
        topPanel.add(discardItrSpinner);
    }

    Vector2f lastVelocity = new Vector2f();
    Vector2f lastDisplacement = new Vector2f();
    int   time;
    private void onFrame(InsFBody frame){
        lastVelocity.add(frame.getAcceleration());
        lastDisplacement.add(lastVelocity);
        avtGraph.getFirst().addValue(frame.getAcceleration().length(), "time", new Integer(time));
        vvtGraph.getFirst().addValue(lastVelocity.length(),            "time", new Integer(time));
        dvtGraph.getFirst().addValue(lastDisplacement.length(),        "time", new Integer(time));
        time++;
    }

    private int framesNum;

    @Override
    public void setBody(InsFBody body) {
        super.setBody(body);
        if(framesNum % discardItr == 0) {
            onFrame(body);
            repaint();
        }
        framesNum++;
    }

}
