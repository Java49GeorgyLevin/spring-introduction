package telran.spring;


import jakarta.validation.constraints.*;

public record Person(long id, @NotEmpty @Pattern(regexp = "[A-Z][a-z]{2,127}") String name,
		@NotEmpty String city, @NotEmpty @Email String email,
		@NotEmpty @Pattern(regexp = "(\\+972|0)(5|7)\\d{8}") String phone) {
	
}