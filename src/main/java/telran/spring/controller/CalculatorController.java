package telran.spring.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.spring.service.Calculator;

@RestController
@RequestMapping("calculator")
@RequiredArgsConstructor
public class CalculatorController  {
	final Calculator calculator;
	Map<String, String> operationsMap = Map.of("multiply", "*", "sum", "+", "substact", "-",  "divide", "/");
	
	private String getExpression(double op1, double op2, String name) {
		return op1 + operationsMap.get(name) + op2 + " = ";
	}
	@GetMapping("{action}/{op1}/{op2}")
	String calc(@PathVariable String action, @PathVariable double op1, @PathVariable double op2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String resCalc = "";
		if(operationsMap.get(action) != null) {		
			String name = action;
			String expression = getExpression(op1, op2, name);
			Method method = calculator.getClass().getDeclaredMethod(name, double.class, double.class);				
			double resInvoke = (Double) method.invoke(calculator, op1, op2);		
			resCalc = expression + resInvoke;
		} else {
			resCalc = "The action must be one from:";
			for(String key: operationsMap.keySet()) {
			resCalc += " " + key;
			}
		}
			return resCalc;		
	}

}
