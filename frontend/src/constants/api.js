/**
 * API configuration constants
 */

export const API_BASE_URL = process.env.REACT_APP_BACKEND_URL || 'http://localhost:8081';

export const API_ENDPOINTS = {
  WALLET_BALANCE: (address) => `${API_BASE_URL}/api/wallet/${address}`,
  PRICE_SNAPSHOTS: `${API_BASE_URL}/api/prices/snapshots`,
};

export const CRYPTOCURRENCIES = [
  { value: 'BTC', label: '₿ Bitcoin', symbol: '₿' },
  { value: 'ETH', label: 'Ξ Ethereum', symbol: 'Ξ' },
  { value: 'MATIC', label: '⬡ Polygon', symbol: '⬡' },
];
