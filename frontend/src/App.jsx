import WalletBalance from "./components/WalletBalance";
import PriceChart from "./components/PriceChart";
import styles from "./App.module.css";

/**
 * Main application component
 * Provides a dashboard for cryptocurrency price tracking and wallet analysis
 */
function App() {
  return (
    <div className={styles.app}>
      <header className={styles.header}>
        <h1 className={styles.headerTitle}>
          📊 Blockchain Analytics Dashboard
        </h1>
        <p className={styles.headerSubtitle}>
          Analyse en temps réel des prix crypto et exploration de wallets
        </p>
      </header>

      <div className={styles.content}>
        <section className={styles.section}>
          <PriceChart />
        </section>

        <section className={styles.section}>
          <WalletBalance />
        </section>
      </div>
    </div>
  );
}

export default App;
