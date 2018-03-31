import java.util.ArrayList;

public class Grid {
    private Plot plot;

    public Grid(Plot plot){
        this.plot = plot;
    }

    private void drawCurve(ComplexCurve complexCurve){
        ArrayList<ComplexLine> complexLines = complexCurve.calculateLinesOnTheCurve();
        for (ComplexLine complexLine : complexLines) {
            switch (plot.getDisplay()){
                case "Re":
                    plot.drawLine(complexLine.getStartPoint().getPointRe(), complexLine.getEndPoint().getPointRe(), complexLine.getTypeOfLine());
                    break;
                case "Im":
                    plot.drawLine(complexLine.getStartPoint().getPointIm(), complexLine.getEndPoint().getPointIm(), complexLine.getTypeOfLine());
                    break;
            }
        }
    }

    public void drawGrid(ArrayList<ComplexPoint> complexVertexes){
        ArrayList<ComplexPoint> initComplexPoints = new ArrayList<>();
        initComplexPoints.addAll(complexVertexes);
        for (int i = 0; i < initComplexPoints.size() - 3; i += 3) {
            drawCurve(new ComplexCurve(initComplexPoints.get(i), initComplexPoints.get(i + 1), initComplexPoints.get(i + 2), initComplexPoints.get(i + 3), TypeOfLine.CURVELine));
        }
    }
}
