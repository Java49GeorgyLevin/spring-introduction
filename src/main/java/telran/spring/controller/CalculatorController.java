package telran.spring.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.spring.calculator.Calculator;

@RestController
@RequestMapping("calculator")
@RequiredArgsConstructor
public class CalculatorController {
	final Calculator calculator;
	Map<String, String> operationsMap = Map.of("multiply", "*", "sum", "+", "substact", "-",  "divide", "/");
	
	private String getExpression(double op1, double op2, String name) {
		return op1 + operationsMap.get(name) + op2 + " = ";
	}
	
	@GetMapping("multiply/{op1}/{op2}")
	String multiply(@PathVariable double op1, @PathVariable double op2) {
		String name = "multiply";
		String expression = getExpression(op1, op2, name);
		return expression + Double.toString(calculator.multiply(op1, op2));
	}
	
	@GetMapping("sum/{op1}/{op2}")
	String sum(@PathVariable double op1, @PathVariable double op2) {
		String name = "sum";
		String expression = getExpression(op1, op2, name);
		return expression + Double.toString(calculator.sum(op1, op2));
	}
	
	@GetMapping("substact/{op1}/{op2}")
	String substact(@PathVariable double op1, @PathVariable double op2) {
		String name = "substact";
		String expression = getExpression(op1, op2, name);
		return expression + Double.toString(calculator.substact(op1, op2));
	}
	
	@GetMapping("divide/{op1}/{op2}")
	String divide(@PathVariable double op1, @PathVariable double op2) {
		String name = "divide";
		System.out.println(name);
		String expression = getExpression(op1, op2, name);
		return expression + Double.toString(calculator.divide(op1, op2));
	}
}
