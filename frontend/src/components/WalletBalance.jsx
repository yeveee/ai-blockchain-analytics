import { useState } from "react";
import axios from "axios";

export default function WalletBalance() {
  const [address, setAddress] = useState("");
  const [balances, setBalances] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setBalances([]);

    try {
      const response = await axios.get(`${process.env.REACT_APP_BACKEND_URL || 'http://localhost:8081'}/api/wallet/${address}`);
      setBalances(response.data);
    } catch (err) {
      console.error(err);
      setError("Impossible de récupérer les balances. Vérifie l'adresse ou le backend.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ 
      maxWidth: "800px", 
      margin: "2rem auto",
      padding: "2rem",
      backgroundColor: "#f8f9fa",
      borderRadius: "10px",
      boxShadow: "0 2px 10px rgba(0,0,0,0.1)"
    }}>
      <h2 style={{ color: "#2c3e50", marginBottom: "1.5rem" }}>🔍 Recherche de Wallet Ethereum</h2>

      <form onSubmit={handleSubmit} style={{ marginBottom: "2rem", display: "flex", gap: "0.5rem" }}>
        <input
          type="text"
          value={address}
          placeholder="0x... Adresse wallet Ethereum"
          onChange={(e) => setAddress(e.target.value)}
          style={{ 
            flex: 1,
            padding: "0.75rem",
            fontSize: "1rem",
            border: "2px solid #ddd",
            borderRadius: "5px",
            outline: "none"
          }}
          required
        />
        <button 
          type="submit" 
          disabled={loading}
          style={{ 
            padding: "0.75rem 1.5rem",
            fontSize: "1rem",
            backgroundColor: loading ? "#95a5a6" : "#3498db",
            color: "white",
            border: "none",
            borderRadius: "5px",
            cursor: loading ? "not-allowed" : "pointer",
            fontWeight: "bold"
          }}
        >
          {loading ? "⏳ Recherche..." : "Rechercher"}
        </button>
      </form>

      {loading && <p style={{ textAlign: "center", color: "#7f8c8d" }}>⏳ Chargement des balances...</p>}
      {error && <p style={{ 
        color: "#e74c3c", 
        backgroundColor: "#fadbd8", 
        padding: "1rem", 
        borderRadius: "5px",
        border: "1px solid #e74c3c"
      }}>❌ {error}</p>}

      {balances.length > 0 && (
        <div>
          <h3 style={{ color: "#27ae60", marginBottom: "1rem" }}>✅ Tokens trouvés ({balances.length})</h3>
          <table style={{ 
            width: "100%", 
            borderCollapse: "collapse",
            backgroundColor: "white",
            borderRadius: "5px",
            overflow: "hidden",
            boxShadow: "0 1px 3px rgba(0,0,0,0.1)"
          }}>
            <thead>
              <tr style={{ backgroundColor: "#34495e", color: "white" }}>
                <th style={{ padding: "1rem", textAlign: "left" }}>Nom</th>
                <th style={{ padding: "1rem", textAlign: "left" }}>Symbole</th>
                <th style={{ padding: "1rem", textAlign: "right" }}>Balance</th>
                <th style={{ padding: "1rem", textAlign: "right" }}>Valeur USD</th>
              </tr>
            </thead>
            <tbody>
              {balances.map((token, index) => (
                <tr key={index} style={{ 
                  borderBottom: "1px solid #ecf0f1",
                  transition: "background-color 0.2s"
                }}>
                  <td style={{ padding: "0.75rem" }}>{token.contractName}</td>
                  <td style={{ padding: "0.75rem", fontWeight: "bold", color: "#3498db" }}>{token.symbol}</td>
                  <td style={{ padding: "0.75rem", textAlign: "right" }}>{token.balance.toFixed(6)}</td>
                  <td style={{ padding: "0.75rem", textAlign: "right", fontWeight: "bold", color: "#27ae60" }}>
                    ${token.balanceUsd.toFixed(2)}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <p style={{ 
            marginTop: "1rem", 
            textAlign: "right", 
            fontWeight: "bold",
            fontSize: "1.1rem",
            color: "#2c3e50"
          }}>
            Total: ${balances.reduce((sum, token) => sum + token.balanceUsd, 0).toFixed(2)} USD
          </p>
        </div>
      )}
    </div>
  );
}
