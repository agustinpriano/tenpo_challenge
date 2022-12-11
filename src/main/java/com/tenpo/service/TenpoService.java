package com.tenpo.service;

import com.tenpo.external.*;
import com.tenpo.model.dto.*;
import com.tenpo.model.error.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.*;

@Service
public class TenpoService {

    @Autowired
    private PercentageStorage percentageStorage;

    @Autowired
    private PercentageService percentageService;

    public TenpoService(PercentageService percentageService, PercentageStorage percentageStorage) {
        this.percentageService = percentageService;
        this.percentageStorage = percentageStorage;
    }

    public PercentageSumDTO sumNumbersWithPercentage(BigDecimal number1, BigDecimal number2) throws StoragedValueException{

        BigDecimal percentage = getPercentageToApply();
        BigDecimal partialSum = number1.add(number2);
        BigDecimal result = partialSum.multiply(new BigDecimal(1).add(percentage.divide(new BigDecimal(100))));

        return new PercentageSumDTO(number1, number2, percentage, result);

    }

    public BigDecimal getPercentageToApply() {
        BigDecimal percentage;
        try{
            percentage = getExternalPercentageToApplyWithRetries();
            percentageStorage.savePercentage(percentage.toString());
        }catch(PercentageAPIMaxTriesException e){
            try{
                percentage = percentageStorage.getPercentage();
            }catch(ExecutionException e2){
                throw new StoragedValueException("There isn't any cached percentage value in the last 30 minutes.");
            }catch(NumberFormatException e3){
                throw new StoragedValueException("There isn't any cached percentage value in the last 30 minutes.");
            }

        }
        return percentage;
    }

    public BigDecimal getExternalPercentageToApplyWithRetries() {
        int count = 0;
        int maxTries = 3;
        while(true) {
            try {
                return percentageService.getPercentage();
            } catch (RuntimeException e) {
                count++;
                if (count == maxTries) {
                    throw new PercentageAPIMaxTriesException("The maximum number of available calls to the PercentageAPI has been exceeded.");
                }
            }
        }
    }

}
