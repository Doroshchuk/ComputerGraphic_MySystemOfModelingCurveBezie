import java.util.ArrayList;

public class PlotOfElementaryCurve {
    private Plot plot;

    public PlotOfElementaryCurve(Plot plot){
        this.plot = plot;
    }

    private void drawCurve(Curve curve){
        ArrayList<Line> lines = curve.calculateLinesOnTheCurve();
        for (Line line : lines) {
            plot.drawLine(line.getStartPoint(), line.getEndPoint(), line.getTypeOfLine());
        }
    }

    public void drawPlot(ArrayList<Point> points){
        plot.setComplexVertexes(new ArrayList<>());
        for (int i = 0; i < points.size() - 3; i += 3) {
            drawCurve(new Curve(points.get(i), points.get(i + 1), points.get(i + 2), points.get(i + 3), TypeOfLine.CURVELine));
        }
        plot.initReImVertexes();
    }
}
