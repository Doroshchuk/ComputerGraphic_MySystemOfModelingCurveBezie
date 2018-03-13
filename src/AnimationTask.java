import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class AnimationTask {
    private double frame = 0;
    private Plot plot;
    private String display;

    public AnimationTask(Plot plot) throws InterruptedException {
        this.plot = plot;
        this.display = plot.getDisplay();
        startAnimation();
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    private void startAnimation() throws InterruptedException {
        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            ArrayList<Point> startingPoints = new ArrayList<>();
            ArrayList<Point> endingPoints = new ArrayList<>();
            if (Objects.equals(display, "Re")){
                startingPoints = plot.getVertexesRe();
                endingPoints = plot.getVertexesIm();
            } else if (Objects.equals(display, "Im")){
                startingPoints = plot.getVertexesIm();
                endingPoints = plot.getVertexesRe();
            }
            ArrayList<Point> intermediateValues = new ArrayList<>();;
            for (int i = 0; i < startingPoints.size(); i++) {
                double x = startingPoints.get(i).getX() * (1 - frame) + endingPoints.get(i).getX() * frame;
                double y = startingPoints.get(i).getY() * (1 - frame) + endingPoints.get(i).getY() * frame;
                intermediateValues.add(new Point(x, y, endingPoints.get(i).getTypeOfPoint()));
            }
            plot.setVertexes(intermediateValues);
            plot.repaint();
            frame += 0.1;
            if (frame > 1){
                timer.stop();
                if (Objects.equals(display, "Re")) plot.setDisplay("Im");
                else if (Objects.equals(display, "Im")) plot.setDisplay("Re");
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
    }
}
