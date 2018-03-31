import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;

public class Surface {
    public ArrayList<ComplexPoint> createComplexPoint(ArrayList<Complex> coefficients){
        ArrayList<ComplexPoint> complexVertexes = new ArrayList<>();
        Complex r0_x = (coefficients.get(0).subtract(coefficients.get(2))).multiply(Complex.I);
        Complex r0_y = coefficients.get(0).add(coefficients.get(2));
        Complex r0_z = coefficients.get(1).multiply(-1).multiply(Complex.I);

        Complex r1_x = (coefficients.get(0).subtract(coefficients.get(2)).subtract(coefficients.get(3))).multiply(Complex.I);
        Complex r1_y = coefficients.get(0).add(coefficients.get(2)).add(coefficients.get(3));
        Complex r1_z = coefficients.get(1).multiply(-1).multiply(Complex.I);

        Complex r2_x = (coefficients.get(0).subtract(coefficients.get(2)).subtract(coefficients.get(3).multiply(2))).multiply(Complex.I);
        Complex r2_y = coefficients.get(0).add(coefficients.get(2)).add(coefficients.get(3).multiply(2));
        Complex r2_z = (coefficients.get(3).subtract(coefficients.get(1))).multiply(Complex.I);

        Complex r3_x = (coefficients.get(0).subtract(coefficients.get(2)).subtract(coefficients.get(3).multiply(2))).multiply(Complex.I);
        Complex r3_y = coefficients.get(0).add(coefficients.get(2)).add(coefficients.get(3).multiply(4));
        Complex r3_z = (coefficients.get(3).multiply(3).subtract(coefficients.get(1))).multiply(Complex.I);

        complexVertexes.add(new ComplexPoint(new Point(r0_x.getReal(), r0_y.getReal(), r0_z.getReal()), new Point(r0_x.getImaginary(), r0_y.getImaginary(), r0_z.getImaginary())));
        complexVertexes.add(new ComplexPoint(new Point(r1_x.getReal(), r1_y.getReal(), r1_z.getReal()), new Point(r1_x.getImaginary(), r1_y.getImaginary(), r1_z.getImaginary())));
        complexVertexes.add(new ComplexPoint(new Point(r2_x.getReal(), r0_y.getReal(), r2_z.getReal()), new Point(r2_x.getImaginary(), r2_y.getImaginary(), r2_z.getImaginary())));
        complexVertexes.add(new ComplexPoint(new Point(r3_x.getReal(), r3_y.getReal(), r3_z.getReal()), new Point(r3_x.getImaginary(), r3_y.getImaginary(), r3_z.getImaginary())));
        return complexVertexes;
    }
}