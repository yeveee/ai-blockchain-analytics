import WalletBalance from "./WalletBalance";
import PriceChart from "./PriceChart";

function App() {
  return (
    <div style={{ 
      minHeight: "100vh",
      backgroundColor: "#ecf0f1",
      padding: "2rem 1rem"
    }}>
      <header style={{ 
        textAlign: "center", 
        marginBottom: "2rem",
        padding: "2rem",
        backgroundColor: "#2c3e50",
        borderRadius: "10px",
        color: "white",
        boxShadow: "0 4px 6px rgba(0,0,0,0.1)"
      }}>
        <h1 style={{ margin: 0, fontSize: "2.5rem" }}>
          📊 Blockchain Analytics Dashboard
        </h1>
        <p style={{ margin: "0.5rem 0 0 0", opacity: 0.9 }}>
          Analyse en temps réel des prix crypto et exploration de wallets
        </p>
      </header>

      <div style={{ maxWidth: "1200px", margin: "0 auto" }}>
        {/* Section des prix */}
        <section style={{ marginBottom: "2rem" }}>
          <PriceChart />
        </section>

        {/* Section de recherche de wallet */}
        <section>
          <WalletBalance />
        </section>
      </div>
    </div>
  );
}

export default App;
