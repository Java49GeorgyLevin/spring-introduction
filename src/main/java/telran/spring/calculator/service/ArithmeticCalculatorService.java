package telran.spring.calculator.service;

import org.springframework.stereotype.Service;

import telran.spring.calculator.dto.OperationData;
@Service
public class ArithmeticCalculatorService implements CalculatorService {

	@Override
	public String calculate(OperationData operationData) {
		TwoOperands twoOperands = new TwoOperands();
		double[] operands = twoOperands.getOperands(operationData);
		String operation = operationData.operation();
		return switch(operation) {
			case "sum" -> Double.toString(operands[0] + operands[1]); 
			case "subtract" -> Double.toString(operands[0] + operands[1]);
			case "multiply" -> Double.toString(operands[0] * operands[1]); 
			case "divide" -> Double.toString(operands[0] / operands[1]); 
		default -> throw new IllegalArgumentException(operation + " is unsupported operation"); 
		
		};
	}

	@Override
	public String getCalculationType() {
		
		return "arithmetic";
	}

}
