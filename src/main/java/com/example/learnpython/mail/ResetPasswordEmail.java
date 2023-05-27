package com.example.learnpython.mail;

import lombok.Data;

@Data
public class ResetPasswordEmail {

    public static final String SUBJECT = "Resetowanie hasła";
    public static final String MAIL_BODY = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8">
              <title>Potwierdzenie rejestracji konta</title>
              <style>
                body {
                  font-family: Arial, sans-serif;
                  font-size: 14px;
                  color: #333;
                  margin: 0;
                  padding: 0;
                }
                header {
                  background-color: #f2f2f2;
                  padding: 20px;
                  text-align: center;
                }
                main {
                  padding: 20px;
                }
                section {
                  margin-bottom: 20px;
                  padding-bottom: 20px;
                  border-bottom: 1px solid #ccc;
                }
                h1, h3 {
                  margin-top: 0;
                  margin-bottom: 10px;
                }
                 h2 {
                  margin-top: 0;
                  margin-bottom: 10px;
                   font-size: 15px;
                }
                p {
                  margin-top: 0;
                  margin-bottom: 10px;
                }
                ul {
                  margin-top: 0;
                  margin-bottom: 10px;
                  padding-left: 20px;
                }
                li {
                  margin-bottom: 5px;
                }
              </style>
            </head>
            <body>
            <header>
              <h1>Witaj ${USERNAME}!</h1>
            </header>
            <main>
              <section>
                <p>Resetowanie hasła.</p>
                <h1> ${TOKEN} </h1>
                <h2>Przypominamy jeszcze najważniejsze z zasad serwisu:</h2>
                <ul>
                  <li>Rozprzestzrenianie materiałów dydaktycznych bez zgody administratorów jest zakazane</li>
                  <li>Szanujmy innych użytkoników serwisu, dbajmy o miłą atmosferę w społeczności</li>
                  <li>Za wszelkiego rodzaju wulgaryzmy wyrażane w komentarzach, administratorzy będą usuwać konta</li>
                </ul>
                <p> W razie pytań napisz do nas maila, postaramy jak najszybciej Ci pomóc.</p>
              </section>
              <section>
                <h3>Kontakt</h3>
                <p>E-mail: <a href="mailto:learnpython_kontakt@example.com">earnpython_kontakt@example.com</a></p>
              </section>
            </main>
            <footer>
              <p>Potwierdzenie rejestracji © 2023 LearnPythonWithUs.com</p>
            </footer>
            </body>
            </html>          
            """;
}
