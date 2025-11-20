import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from 'recharts';
import styles from './PriceChart.module.css';
import { API_ENDPOINTS, CRYPTOCURRENCIES } from '../constants/api';

const PriceChart = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [symbol, setSymbol] = useState("BTC");

  const handleSelect = (e) => {
    setSymbol(e.target.value);
  };

  useEffect(() => {
    const fetchPriceData = async () => {
      setLoading(true);
      setError(null);

      try {
        const response = await axios.get(API_ENDPOINTS.PRICE_SNAPSHOTS);
        console.log("Données reçues:", response.data);
        
        const filteredData = response.data
          .filter(d => d.symbol === symbol)
          .map(d => ({
            timestamp: d.timestamp,
            price: d.price
          }));
        
        setData(filteredData);
      } catch (err) {
        console.error("Erreur lors de la récupération des prix:", err);
        setError("Impossible de charger les données de prix");
      } finally {
        setLoading(false);
      }
    };

    fetchPriceData();
  }, [symbol]);


  if (loading) {
    return (
      <div className={styles.loading}>
        ⏳ Chargement des données...
      </div>
    );
  }
  
  if (error) {
    return (
      <div className={styles.error}>
        ❌ {error}
      </div>
    );
  }
  
  if (data.length === 0) {
    return (
      <div className={styles.error}>
        ❌ Aucune donnée pour {symbol}
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h2 className={styles.title}>
          📈 Prix {symbol}
        </h2>
        <select 
          onChange={handleSelect} 
          value={symbol}
          className={styles.select}
        >
          {CRYPTOCURRENCIES.map(crypto => (
            <option key={crypto.value} value={crypto.value}>
              {crypto.label}
            </option>
          ))}
        </select>
      </div>

      <div className={styles.chartWrapper}>
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
