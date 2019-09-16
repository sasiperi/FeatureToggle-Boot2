package com.sasip.service;

import org.ff4j.aop.Flip;

public interface CalculationsService
{

    @Flip(name = "system-binary", alterBean = "caluculationServiceBinary")
    String addNumbers(int numOne, int numTwo);
}
