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
    axios.get('https://aware-imagination.up.railway.app/api/prices/snapshots')
    //axios.get('http://localhost:8081/api/prices/snapshots')
    
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


  if (loading) return <div>Chargement des données...</div>;
  if (data.length === 0) return <div>Aucune donnée pour {symbol}</div>;

    return (
        <div>
      <h2>Prix {symbol}</h2>
      <select onChange={handleSelect} value={symbol}>
        <option value="BTC">Bitcoin</option>
        <option value="ETH">Ethereum</option>
        <option value="MATIC">Polygon</option>
      </select>

      <LineChart width={800} height={400} data={data}>
        <CartesianGrid stroke="#eee" strokeDasharray="5 5" />
        <XAxis dataKey="timestamp" />
        <YAxis />
        <Tooltip />
        <Line type="monotone" dataKey="price" stroke="#8884d8" />
      </LineChart>
    </div>
    );
};

export default PriceChart;
