import { useState } from 'react';

/**
 * Custom hook for making API requests
 * @returns {Object} API state and fetch function
 */
export const useApi = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  /**
   * Perform an API request
   * @param {Function} apiCall - Async function that performs the API call
   * @returns {Promise} The response data or null if error
   */
  const fetchData = async (apiCall) => {
    setLoading(true);
    setError(null);

    try {
      const response = await apiCall();
      return response.data;
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.message || 'An error occurred';
      setError(errorMessage);
      console.error('API Error:', err);
      return null;
    } finally {
      setLoading(false);
    }
  };

  return { loading, error, fetchData, setError };
};
