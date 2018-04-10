import java.util.ArrayList;

public class Grid {
    private Plot plot;

    public Grid(Plot plot){
        this.plot = plot;
    }

    private void drawCurve(ComplexCurve complexCurve){
        plot.setShapeLines(new ArrayList<>());
        ArrayList<ComplexLine> complexLines = complexCurve.calculateLinesOnTheCurve(TypeOfModeling.NORMAL);
        for (ComplexLine complexLine : complexLines) {
            switch (plot.getDisplay()){
                case "Re":
                    plot.getShapeLines().add(new Line(complexLine.getStartPoint().getPointRe(), complexLine.getEndPoint().getPointRe(), complexLine.getTypeOfLine()));
                    break;
                case "Im":
                    plot.getShapeLines().add(new Line(complexLine.getStartPoint().getPointIm(), complexLine.getEndPoint().getPointIm(), complexLine.getTypeOfLine()));
                    break;
            }
        }
    }

    public void draw(ArrayList<ComplexPoint> complexVertexes){
        ArrayList<ComplexPoint> initComplexPoints = new ArrayList<>();
        initComplexPoints.addAll(complexVertexes);
        for (int i = 0; i < initComplexPoints.size() - 3; i += 3) {
            drawCurve(new ComplexCurve(initComplexPoints.get(i), initComplexPoints.get(i + 1), initComplexPoints.get(i + 2), initComplexPoints.get(i + 3), TypeOfLine.CURVELine));
        }
    }
}
