package Jar.userservice.patterns.strategy;

import Jar.userservice.dto.RegisterRequest;

import Jar.userservice.dto.RegisterRequest;

public interface ValidationStrategy {

    boolean validate(RegisterRequest request);

    String getErrorMessage();
}
