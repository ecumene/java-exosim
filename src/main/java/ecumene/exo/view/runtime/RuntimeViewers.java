package ecumene.exo.view.runtime;

import ecumene.exo.runtime.ExoRuntime;
import ecumene.exo.runtime.viewer.IViewerTag;
import ecumene.exo.runtime.viewer.ViewerRunnable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RuntimeViewers extends JFrame {
    private JComboBox comboBoxRunnables;
    private JButton newThread;
    private JPanel newThreadContainer;
    private JPanel root;
    private JScrollPane scrollPane;
    private JPanel scrollPaneContent;
    private JPanel status;
    private JPanel content;

    public RuntimeViewers(){
        super("Runtime viewer");
        setSize(460, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createUIComponents();
        setContentPane(root);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private int selectedRunnable;

    private void createUIComponents() {
        scrollPaneContent = new JPanel();
        scrollPaneContent.add(new JButton("Test"));

        String[] comboObjects = new String[ExoRuntime.INSTANCE.getRunnables().length];
        int i = ExoRuntime.INSTANCE.getRunnables().length - 1;
        for(IViewerTag runnableTag : ExoRuntime.INSTANCE.getRunnables()){ comboObjects[i] = runnableTag.getIdentifier(); i--; }

        comboBoxRunnables = new JComboBox(comboObjects);
        comboBoxRunnables.addActionListener(actionEvent -> {
            selectedRunnable = (((JComboBox) actionEvent.getSource()).getSelectedIndex());
        });
        newThread = new JButton("Open Thread");
        newThread.addActionListener(ae -> {
            try {
                ExoRuntime.INSTANCE.runViewer(ExoRuntime.INSTANCE.getRunnables().length - selectedRunnable - 1, new String[]{});
            } catch(Throwable e){
                ExoRuntime.INSTANCE.getExceptionListener().exceptionThrown((Exception) e);
            }
        });
    }

    private void trackRunnable(){

    }
}
