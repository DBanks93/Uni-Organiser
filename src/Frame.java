import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frame {
    private final ArrayList<JComponent> addedComponents = new ArrayList<JComponent>();
    private final MainMenuFrame mainMenuFrame;
    private final Container container;

    public Frame(MainMenuFrame mainMenuFrame, String windowName) {
        this.mainMenuFrame = mainMenuFrame;
        this.container = mainMenuFrame.getContainer();
        mainMenuFrame.setTitle(windowName);
    }

    public void addComponents(JComponent[] components) {
        for (JComponent component : components) {
            addComponent(component);
        }
    }

    public void addComponent(JComponent component) {
        container.add(component);
        addedComponents.add(component);
        mainMenuFrame.repaint();
    }

    public void closeWindow() {
        addedComponents.forEach(container::remove);
        mainMenuFrame.repaint();
        addedComponents.clear();
    }

    public void removeComponent(Component component) {
        mainMenuFrame.remove(component);
    }

    public void refreshPage() {
        mainMenuFrame.repaint();
    }

    public MainMenuFrame getMainMenuFrame() {
        return mainMenuFrame;
    }
}
