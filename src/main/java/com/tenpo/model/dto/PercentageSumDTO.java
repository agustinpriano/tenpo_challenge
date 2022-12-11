package com.tenpo.model.dto;

import java.math.*;

public class PercentageSumDTO {

    private BigDecimal number1;
    private BigDecimal number2;
    private BigDecimal percentageApplied;
    private BigDecimal result;


    public PercentageSumDTO(BigDecimal number1, BigDecimal number2, BigDecimal percentageApplied, BigDecimal result) {
        this.number1 = number1;
        this.number2 = number2;
        this.percentageApplied = percentageApplied;
        this.result = result;
    }

    public BigDecimal getNumber1() {
        return number1;
    }

    public void setNumber1(BigDecimal number1) {
        this.number1 = number1;
    }

    public BigDecimal getNumber2() {
        return number2;
    }

    public void setNumber2(BigDecimal number2) {
        this.number2 = number2;
    }

    public BigDecimal getPercentageApplied() {
        return percentageApplied;
    }

    public void setPercentageApplied(BigDecimal percentageApplied) {
        this.percentageApplied = percentageApplied;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}
