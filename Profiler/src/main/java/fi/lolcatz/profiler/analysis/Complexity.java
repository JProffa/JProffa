package fi.lolcatz.profiler.analysis;

import fi.lolcatz.profiler.Output;
import java.util.Arrays;
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.fitting.PolynomialFitter;
import org.apache.commons.math3.optim.nonlinear.vector.MultivariateVectorOptimizer;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.util.FastMath;

/**
 * Complexity classes that can be fitted against profiling results.
 */
public abstract class Complexity {
    private static final double LOG2 = Math.log(2);
    
    public static Complexity LINEAR = new Complexity() {
        @Override
        protected UnivariateFunction fitCurve(DoublePair[] data) {
            assert Math.abs(data[data.length - 1].x - data[0].x) > 0.1 : "Input size difference too small";
            
            // y = ax + b
            double a = (data[data.length-1].y - data[0].y) / (data[data.length-1].x - data[0].x);
            double b = data[0].y - a * data[0].x;
            
            double[] guess = { b, a };
            return fitPolynomialCurve(data, guess);
        }
    };
    
    public static Complexity NLOGN = new Complexity() {
        @Override
        protected UnivariateFunction fitCurve(DoublePair[] data) {
            double x1 = data[0].x;
            double x2 = data[1].x;
            double x3 = data[2].x;

            double y1 = data[0].y;
            double y2 = data[1].y;
            double y3 = data[2].y;
            
            // y = a*log2(x) + b*x + c
            double denom = (x1 - x2) * (x1 - x3) * (x2 - x3);
            double a = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
            double b = (x3 * (Math.log(x3) / Math.log(2)) * (y1 - y2) + x2 * (Math.log(x2) / Math.log(2)) * (y3 - y1) + x1 * (Math.log(x1) / Math.log(2)) * (y2 - y3)) / denom;
            double c = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;
            
            double[] guess = { c, b, a };
            ParametricUnivariateFunction f = new ParametricUnivariateFunction() {
                @Override
                public double value(double x, double... params) {
                    double c = params[0];
                    double b = params[1];
                    double a = params[2];
                    return a*FastMath.log(x)/LOG2 + b*x + c;
                }

                @Override
                public double[] gradient(double x, double... params) {
                    // http://www.wolframalpha.com/input/?i=grad+%28x*log%28p%29%2Flog%282%29+%2B+y*p+%2B+z%29
                    return new double[] { FastMath.log(x)/LOG2, x, 1 };
                }
            };
            return fitGeneralCurve(data, f, guess);
        }
        
        @Override
        protected boolean fallbackIsBelow(Output<?> output, double tolerance) {
            return LINEAR.isBelow(output, tolerance);
        }
    };
    
    public static Complexity QUADRATIC = new Complexity() {
        @Override
        protected UnivariateFunction fitCurve(DoublePair[] data) {
            double x1 = data[0].x;
            double x2 = data[1].x;
            double x3 = data[2].x;

            double y1 = data[0].y;
            double y2 = data[1].y;
            double y3 = data[2].y;

            // y = ax^2 + bx + c
            double denom = (x1 - x2) * (x1 - x3) * (x2 - x3);
            if (Math.abs(denom) < 0.0001) {
                double[] guess = { 1.0, 1.0, 1.0 };
                return fitPolynomialCurve(data, guess);
            } else {
                double a = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
                double b = (x3 * x3 * (y1 - y2) + x2 * x2 * (y3 - y1) + x1 * x1 * (y2 - y3)) / denom;
                double c = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;
                double[] guess = { c, b, a };
                return fitPolynomialCurve(data, guess);
            }
        }
    };
    
    public UnivariateFunction fitCurve(Output<?> output) throws TooManyEvaluationsException {
        int n = output.entryCount();
        assert n >= 2 : "Not enough inputs";
        
        DoublePair[] data = new DoublePair[n];
        for (int i = 0; i < n; ++i) {
            data[i] = new DoublePair(output.getSize().get(i), output.getTime().get(i));
        }
        Arrays.sort(data);
        return fitCurve(data);
    }
    
    /**
     * Checks whether the profiling data indicates an algorithm below this complexity.
     * 
     * <p>
     * Attempts to fit the data to this complexity class to obtain a fitted curve {@code f}.
     * Then checks if for all {@code (size, time)} pairs, {@code time &lt;= f(size) * tolerance}.
     */
    public boolean isBelow(Output<?> output, double tolerance) {
        try {
            UnivariateFunction f = fitCurve(output);
            int n = output.entryCount();
            for (int i = 0; i < n; ++i) {
                double x = output.getSize().get(i);
                double y = output.getTime().get(i);
                double maxY = f.value(x) * tolerance;
                if (y > maxY) {
                    return false;
                }
            }
        } catch (TooManyEvaluationsException e) {
            return fallbackIsBelow(output, tolerance);
        }
        return true;
    }
    
    protected boolean fallbackIsBelow(Output<?> output, double tolerance) {
        return false;
    }
    
    protected abstract UnivariateFunction fitCurve(DoublePair[] data);
    
    protected UnivariateFunction fitPolynomialCurve(DoublePair[] data, double[] coefficientGuess) {
        MultivariateVectorOptimizer optimizer = new LevenbergMarquardtOptimizer();
        PolynomialFitter fitter = new PolynomialFitter(optimizer);
        for (int i = 0; i < data.length; ++i) {
            fitter.addObservedPoint(data[i].x, data[i].y);
        }

        double[] params = fitter.fit(coefficientGuess);
        return new PolynomialFunction(params);
    }
    
    protected <T extends ParametricUnivariateFunction> UnivariateFunction fitGeneralCurve(
            DoublePair[] data,
            final T f,
            double[] paramGuess
            ) throws TooManyEvaluationsException {
        final int MAX_ITERATIONS = 10000;
        
        MultivariateVectorOptimizer optimizer = new LevenbergMarquardtOptimizer();
        CurveFitter<T> fitter = new CurveFitter<T>(optimizer);
        for (int i = 0; i < data.length; ++i) {
            fitter.addObservedPoint(data[i].x, data[i].y);
        }

        final double[] params = fitter.fit(MAX_ITERATIONS, f, paramGuess);
        return new UnivariateFunction() {
            @Override
            public double value(double x) {
                return f.value(x, params);
            }
        };
    }
    
}
