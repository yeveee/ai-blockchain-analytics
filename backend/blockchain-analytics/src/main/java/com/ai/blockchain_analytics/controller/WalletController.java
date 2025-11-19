package com.ai.blockchain_analytics.controller;

import com.ai.blockchain_analytics.dto.WalletBalanceDTO;
import com.ai.blockchain_analytics.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{address}")
    public List<WalletBalanceDTO> getWallet(@PathVariable String address) {
        return walletService.getWalletBalances(address);
    }
}
