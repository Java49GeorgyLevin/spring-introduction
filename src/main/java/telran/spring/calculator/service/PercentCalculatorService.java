package telran.spring.calculator.service;

import org.springframework.stereotype.Service;

import telran.spring.calculator.dto.OperationData;
@Service
public class PercentCalculatorService implements CalculatorService {

	@Override
	public String calculate(OperationData operationData) {
		TwoOperands twoOperands = new TwoOperands();
		double[] operands = twoOperands.getOperands(operationData);
		String operation = operationData.operation();
		return switch(operation) {
		case "per" -> Double.toString(operands[1] / 100 * operands [0]);
		case "per mille" -> Double.toString(operands[1] / 1000 * operands [0]);
		case "of" -> Double.toString(operands[0] /operands [1]  * 100)+"%";
		default -> throw new IllegalArgumentException(String.format("This %s must be \"per\", or \"per mille\", or \"of\"", operation));
		};
	}

	@Override
	public String getCalculationType() {
		
		return "percent";
	}

}
