package telran.spring.calculator.service;

import telran.spring.calculator.dto.OperationData;
public class TwoOperands {
	
	public double[] getOperands(OperationData operationData) {
		
		try {
			double op1 = Double.parseDouble(operationData.operand1());
			double op2 = Double.parseDouble(operationData.operand2());
			return new double[] {op1, op2};
		} catch (Exception e) {
			throw new IllegalStateException("two operands must be the numbers"); 
		}

	}


}
