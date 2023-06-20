import React, { useContext, useState, useEffect } from "react";
import { Container, Row, Col } from "react-bootstrap";
import { ThemeContext } from "../themes/ThemeProvider";
import "../../App.css";
import "./home.css"

import { AppWrapper } from "../common/AppWrapper";
import { useLocation, useNavigate } from "react-router-dom";
import { ArticleShort, loadLatestArticles } from "../article/apis/article";
import { useError } from "../common/ErrorContext";

type Props = {};

const HomeScreen = (props: Props) => {
  const { theme, toggleTheme } = useContext(ThemeContext);
  const [articles, setArticles] = useState<ArticleShort[]>([]);
  const location = useLocation();
  const [isLoading, setisLoading] = useState(true);
  const { errorMessages, setError } = useError();
  const navigate = useNavigate();

  useEffect(() => {
    loadLatestArticles().then((data) => {
      if (data.isOk) {
        setArticles(data.value);
      } else {
        setError("Nie udało się załadować najnowszych artykułów");
      }
    });

    setisLoading(false);
  }, [location]);

  return (
      <AppWrapper>
        <Container className="pt-3">
          {/* Big Banner */}
          <div className="banner">
          <img className="banner-img" src={require("../../assets/background.png")} />
          </div>
          <p className="site-description">Witaj! Ta strona do nauki języka Python jest doskonałym źródłem wiedzy dla wszystkich, którzy chcą zdobyć umiejętności programowania w tym języku. Na stronie znajdziesz szeroki wybór artykułów, które pokrywają różnorodne tematy związane z Pythonem.</p>
          <h2>Najnowsze artykuły</h2>
          <Row className="mt-3">
            {articles.map((article) => (
              <Col md={2} sm={4} key={article.id} onClick={() => navigate(`/article/${article.id}`)}>
                <div className="article-tile">
                  <h3><strong>{article.title}</strong></h3>
                </div>
              </Col>
            ))}
          </Row>
        </Container>
      </AppWrapper>
  );
};

export default HomeScreen;
