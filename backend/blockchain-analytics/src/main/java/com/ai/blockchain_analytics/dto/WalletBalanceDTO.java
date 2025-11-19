package com.ai.blockchain_analytics.dto;

import java.math.BigDecimal;

public class WalletBalanceDTO {
    private String contractName;
    private String symbol;
    private BigDecimal balance;
    private BigDecimal balanceUsd;

    public WalletBalanceDTO(String contractName, String symbol, BigDecimal balance, BigDecimal balanceUsd) {
        this.contractName = contractName;
        this.symbol = symbol;
        this.balance = balance;
        this.balanceUsd = balanceUsd;
    }

    public String getContractName() {
        return contractName;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getBalanceUsd() {
        return balanceUsd;
    }
}

