/**
 * 
 */
package com.sasip.service;

import org.springframework.stereotype.Component;

/**
 * @author venkatasuryasasikala
 *
 */
@Component("caluculationServiceBinary")
public class CaluculationServiceBinaryImpl implements CalculationsService
{

    /*
     * (non-Javadoc)
     * 
     * @see com.sasip.service.CalculationsService#addNumbers(int, int)
     */
    @Override
    public String addNumbers(int numOne, int numTwo)
    {
        return Integer.toBinaryString(numOne + numTwo);
    }

}
