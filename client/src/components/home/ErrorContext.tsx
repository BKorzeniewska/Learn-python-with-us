import { createContext, useContext, useEffect } from 'react';
import { useState } from 'react';

type ErrorContextType = {
  errorMessage: string | null;
  setError: (errorMessage: string | null) => void;
};

const ErrorContext = createContext<ErrorContextType>({
  errorMessage: null,
  setError: () => {},
});

export const useError = () => useContext(ErrorContext);



export const ErrorProvider = ({ children }: { children: React.ReactNode }) => {
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const setError = (errorMessage: string | null) => {
    setErrorMessage(errorMessage);
  };

  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => {
        setErrorMessage(null);
      }, 2000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  return (
    <ErrorContext.Provider value={{ errorMessage, setError }}>
      {children}
    </ErrorContext.Provider>
  );
};