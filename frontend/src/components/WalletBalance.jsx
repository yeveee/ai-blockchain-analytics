import { useState } from "react";
import axios from "axios";
import styles from "./WalletBalance.module.css";
import { API_ENDPOINTS } from "../constants/api";
import { formatUSD, formatBalance, calculateTotalBalance, isValidEthereumAddress } from "../utils/formatters";
import { useApi } from "../hooks/useApi";

export default function WalletBalance() {
  const [address, setAddress] = useState("");
  const [balances, setBalances] = useState([]);
  const { loading, error, fetchData, setError } = useApi();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setBalances([]);
    setError(null);

    if (!isValidEthereumAddress(address)) {
      setError("Format d'adresse Ethereum invalide. L'adresse doit commencer par 0x et contenir 40 caractères hexadécimaux.");
      return;
    }

    const data = await fetchData(() => axios.get(API_ENDPOINTS.WALLET_BALANCE(address)));
    
    if (data) {
      setBalances(data);
    }
  };

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>🔍 Recherche de Wallet Ethereum</h2>

      <form onSubmit={handleSubmit} className={styles.form}>
        <input
          type="text"
          value={address}
          placeholder="0x... Adresse wallet Ethereum"
          onChange={(e) => setAddress(e.target.value)}
          className={styles.input}
          required
        />
        <button 
          type="submit" 
          disabled={loading}
          className={styles.button}
        >
          {loading ? "⏳ Recherche..." : "Rechercher"}
        </button>
      </form>

      {loading && <p className={styles.loading}>⏳ Chargement des balances...</p>}
      {error && <p className={styles.error}>❌ {error}</p>}

      {balances.length > 0 && (
        <div>
          <h3 className={styles.resultsTitle}>✅ Tokens trouvés ({balances.length})</h3>
          <table className={styles.table}>
            <thead className={styles.tableHeader}>
              <tr>
                <th>Nom</th>
                <th>Symbole</th>
                <th>Balance</th>
                <th>Valeur USD</th>
              </tr>
            </thead>
            <tbody>
              {balances.map((token, index) => (
                <tr key={index} className={styles.tableRow}>
                  <td className={styles.tableCell}>{token.contractName}</td>
                  <td className={`${styles.tableCell} ${styles.tokenSymbol}`}>{token.symbol}</td>
                  <td className={`${styles.tableCell} ${styles.balance}`}>{formatBalance(token.balance)}</td>
                  <td className={`${styles.tableCell} ${styles.usdValue}`}>
                    {formatUSD(token.balanceUsd)}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <p className={styles.total}>
            Total: {formatUSD(calculateTotalBalance(balances))} USD
          </p>
        </div>
      )}
    </div>
  );
}
