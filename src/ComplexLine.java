public class ComplexLine {
    private ComplexPoint startPoint;
    private ComplexPoint endPoint;
    private TypeOfLine typeOfLine;

    public ComplexLine (ComplexPoint startPoint, ComplexPoint endPoint, TypeOfLine typeOfLine) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.typeOfLine = typeOfLine;
    }

    public ComplexPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(ComplexPoint startPoint) {
        this.startPoint = startPoint;
    }

    public ComplexPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(ComplexPoint endPoint) {
        this.endPoint = endPoint;
    }

    public TypeOfLine getTypeOfLine() {
        return typeOfLine;
    }

    public void setTypeOfLine(TypeOfLine typeOfLine) {
        this.typeOfLine = typeOfLine;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof Line) {
            sameSame = this.startPoint.getComplexX() == ((ComplexLine) object).startPoint.getComplexX() && this.startPoint.getComplexY() == ((ComplexLine) object).startPoint.getComplexY() &&
                    this.endPoint.getComplexX() == ((ComplexLine) object).endPoint.getComplexX() && this.endPoint.getComplexY() == ((ComplexLine) object).endPoint.getComplexY() &&
                    this.typeOfLine == ((ComplexLine) object).getTypeOfLine();
        }

        return sameSame;
    }
}
