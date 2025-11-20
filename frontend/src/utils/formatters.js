/**
 * Utility functions for formatting data
 */

/**
 * Format a number as USD currency
 * @param {number} value - The value to format
 * @param {number} decimals - Number of decimal places (default: 2)
 * @returns {string} Formatted currency string
 */
export const formatUSD = (value, decimals = 2) => {
  return `$${Number(value).toFixed(decimals)}`;
};

/**
 * Format a token balance with specified decimals
 * @param {number} balance - The balance to format
 * @param {number} decimals - Number of decimal places (default: 6)
 * @returns {string} Formatted balance string
 */
export const formatBalance = (balance, decimals = 6) => {
  return Number(balance).toFixed(decimals);
};

/**
 * Validate Ethereum address format
 * @param {string} address - The address to validate
 * @returns {boolean} True if valid Ethereum address
 */
export const isValidEthereumAddress = (address) => {
  return /^0x[a-fA-F0-9]{40}$/.test(address);
};

/**
 * Calculate total balance from array of token balances
 * @param {Array} balances - Array of balance objects with balanceUsd property
 * @returns {number} Total balance in USD
 */
export const calculateTotalBalance = (balances) => {
  return balances.reduce((sum, token) => sum + (token.balanceUsd || 0), 0);
};
