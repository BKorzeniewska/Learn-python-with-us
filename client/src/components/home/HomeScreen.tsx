import React, { useContext } from "react";
import { Container, Row, Col } from "react-bootstrap";
import { ThemeContext } from "../themes/ThemeProvider";
import "../../App.css";
import "./home.css"

import { AppWrapper } from "../common/AppWrapper";

type Props = {};

const HomeScreen = (props: Props) => {
  const { theme, toggleTheme } = useContext(ThemeContext);

  // Assuming you have an array of articles
  const articles = [
    { id: 1, title: "Article 1", content: "Content for Article 1" },
    { id: 2, title: "Article 2", content: "Content for Article 2" },
    { id: 3, title: "Article 3", content: "Content for Article 3" },
    { id: 4, title: "Article 4", content: "Content for Article 4" },
    { id: 5, title: "Article 5", content: "Content for Article 5" },
    { id: 6, title: "Article 6", content: "Content for Article 6" },
    { id: 7, title: "Article 7", content: "Content for Article 7" },
    { id: 8, title: "Article 8", content: "Content for Article 8" },
  ];

  return (
      <AppWrapper>
        <Container className="pt-3">
          {/* Big Banner */}
          <div className="banner">
          <img className="banner-img" src={require("../../assets/background.png")} />
          </div>

          {/* Latest Articles */}
          <Row className="mt-3">
            {articles.map((article) => (
              <Col md={3} sm={6} key={article.id}>
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
