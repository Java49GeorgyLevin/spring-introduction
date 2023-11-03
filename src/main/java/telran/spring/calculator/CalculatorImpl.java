package telran.spring.calculator;

import org.springframework.stereotype.Service;

@Service
public class CalculatorImpl implements Calculator {

	@Override
	public double multiply(double op1, double op2) {
		return op1 * op2;
	}

	@Override
	public double sum(double op1, double op2) {
		return op1 + op2;
	}

	@Override
	public double substact(double op1, double op2) {
		return op1 - op2;
	}

	@Override
	public double divide(double op1, double op2) {
		return op2 == 0 ? Double.NaN : op1 / op2;
	}

}
