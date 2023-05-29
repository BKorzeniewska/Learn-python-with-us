import { Fragment, createContext, useContext, useEffect } from 'react';
import { useState } from 'react';
import { Alert } from 'react-bootstrap';
import "./error.css"
import React from 'react';

type ErrorMessage = {
  message: string;
  timestamp: number;
};

type ErrorContextType = {
  errorMessages: ErrorMessage[];
  setError: (errorMessage: string) => void;
};

const ErrorContext = createContext<ErrorContextType>({
  errorMessages: [],
  setError: () => { },
});

export const useError = () => useContext(ErrorContext);

export const ErrorProvider = ({ children }: { children: React.ReactNode }) => {
  const [errorMessages, setErrorMessages] = useState<ErrorMessage[]>([]);

  const setError = (errorMessage: string) => {
    const timestamp = Date.now();
    setErrorMessages((prevMessages) => [...prevMessages, { message: errorMessage, timestamp }]);
    console.log(errorMessages);
  };

  useEffect(() => {
    const clearErrors = () => {
      const now = Date.now();
      setErrorMessages((prevMessages) => prevMessages.filter((message) => now - message.timestamp <= 2500));
    };

    if (errorMessages.length > 0) {
      const interval = setInterval(clearErrors, 400);
      return () => clearInterval(interval);
    }
  }, [errorMessages]);

  return (
    <ErrorContext.Provider value={{ errorMessages, setError }}>
      <div className="error-wrapper">
        {errorMessages.map((errorMessage, index) => (
          <Alert
            key={index}
            className="error-alert"
            variant="danger"
          >
            {errorMessage.message.split('\n').map((line, lineIndex) => (
              <Fragment key={lineIndex}>
                {line}
                <br />
              </Fragment>
            ))}
          </Alert>
        ))}
      </div>
      {children}
    </ErrorContext.Provider>
  );
};
