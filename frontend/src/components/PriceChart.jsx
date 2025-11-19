import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from 'recharts';

const PriceChart = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
  const [symbol, setSymbol] = useState("BTC"); // crypto sélectionnée

  // Fonction pour gérer la sélection
  const handleSelect = (e) => {
    setSymbol(e.target.value);
  };


    useEffect(() => {
    //axios.get('https://back-production-710c.up.railway.app/api/prices/snapshots')
    axios.get('http://localhost:8081/api/prices/snapshots')
    
        .then(res => {
            console.log("Données reçues:", res.data); // check here
            const filteredData = res.data
                .filter(d => d.symbol === symbol)
                .map(d => ({
                    timestamp: d.timestamp,
                    price: d.price
                }));
            setData(filteredData);
            setLoading(false);
        })
        .catch(err => {
            console.error("Erreur Axios :", err);
            setLoading(false);
        });
}, [symbol]); // Re-fetch when symbol changes


  if (loading) return (
    <div style={{ 
      padding: "2rem", 
      textAlign: "center", 
      backgroundColor: "#f8f9fa", 
      borderRadius: "10px",
      margin: "2rem auto",
      maxWidth: "900px"
    }}>
      ⏳ Chargement des données...
    </div>
  );
  
  if (data.length === 0) return (
    <div style={{ 
      padding: "2rem", 
      textAlign: "center", 
      backgroundColor: "#fadbd8", 
      borderRadius: "10px",
      margin: "2rem auto",
      maxWidth: "900px",
      color: "#e74c3c",
      border: "1px solid #e74c3c"
    }}>
      ❌ Aucune donnée pour {symbol}
    </div>
  );

    return (
        <div style={{
          maxWidth: "900px",
          margin: "0 auto",
          padding: "2rem",
          backgroundColor: "#f8f9fa",
          borderRadius: "10px",
          boxShadow: "0 2px 10px rgba(0,0,0,0.1)"
        }}>
      <div style={{ 
        display: "flex", 
        justifyContent: "space-between", 
        alignItems: "center",
        marginBottom: "1.5rem",
        flexWrap: "wrap",
        gap: "1rem"
      }}>
        <h2 style={{ color: "#2c3e50", margin: 0 }}>
          📈 Prix {symbol}
        </h2>
        <select 
          onChange={handleSelect} 
          value={symbol}
          style={{
            padding: "0.5rem 1rem",
            fontSize: "1rem",
            border: "2px solid #3498db",
            borderRadius: "5px",
            backgroundColor: "white",
            color: "#2c3e50",
            cursor: "pointer",
            fontWeight: "bold"
          }}
        >
          <option value="BTC">₿ Bitcoin</option>
          <option value="ETH">Ξ Ethereum</option>
          <option value="MATIC">⬡ Polygon</option>
        </select>
      </div>

      <div style={{ 
        backgroundColor: "white", 
        padding: "1rem",
        borderRadius: "5px",
        overflow: "auto"
      }}>
        <LineChart width={800} height={400} data={data}>
          <CartesianGrid stroke="#eee" strokeDasharray="5 5" />
          <XAxis dataKey="timestamp" />
          <YAxis />
          <Tooltip />
          <Line type="monotone" dataKey="price" stroke="#3498db" strokeWidth={2} />
        </LineChart>
      </div>
    </div>
    );
};

export default PriceChart;
