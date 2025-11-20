package com.ai.blockchain_analytics.controller;

import com.ai.blockchain_analytics.dto.WalletBalanceDTO;
import com.ai.blockchain_analytics.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for Ethereum wallet operations.
 * Provides endpoints to retrieve wallet balances and token information.
 */
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Retrieves the balance information for a given Ethereum wallet address.
     * Returns all ERC-20 tokens held by the wallet with their current USD values.
     * 
     * @param address the Ethereum wallet address (must start with 0x)
     * @return list of wallet balances or error response
     */
    @GetMapping("/{address}")
    public ResponseEntity<List<WalletBalanceDTO>> getWalletBalances(@PathVariable String address) {
        try {
            logger.info("Fetching wallet balances for address: {}", address);
            
            if (!isValidEthereumAddress(address)) {
                logger.warn("Invalid Ethereum address format: {}", address);
                return ResponseEntity.badRequest().build();
            }
            
            List<WalletBalanceDTO> balances = walletService.getWalletBalances(address);
            logger.info("Successfully retrieved {} token balances for address: {}", balances.size(), address);
            return ResponseEntity.ok(balances);
        } catch (Exception e) {
            logger.error("Error fetching wallet balances for address: {}", address, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Validates Ethereum address format.
     * 
     * @param address the address to validate
     * @return true if the address is valid
     */
    private boolean isValidEthereumAddress(String address) {
        return address != null && address.matches("^0x[a-fA-F0-9]{40}$");
    }
}
