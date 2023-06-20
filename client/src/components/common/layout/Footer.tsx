import { useContext } from 'react';
import './footer.css';
import { AuthContext } from '../../auth/AuthContext';

const Footer = () => {

  const { isAuthorized } = useContext(AuthContext);

  return (
    <footer>
      <div className="footer-wrap">
        <div className="container first_class">
        </div>
        <div className="second_class">
          <div className="container second_class_bdr">
            <div className="row">
              <div className="col-md-5 col-sm-6">

                <div className="footer-logo"><img alt="logo" width="40" height="40" className="d-inline-block align-center mx-2" src={require("../../../assets/logo512.png")} />
                </div>
                <p>Witaj na stronie internetowej poświęconej językowi programowania Python! Nasza witryna jest pełna zasobów, które pomogą Ci zgłębić tajniki tego wszechstronnego języka programowania. Bez względu na to, czy jesteś początkującym programistą, który dopiero zaczyna przygodę z Pythonem, czy doświadczonym specjalistą, szukającym zaawansowanych informacji, tutaj znajdziesz to, czego potrzebujesz.</p>
              </div>
              <div className="col-md-3 col-sm-6">
                <h3>Linki</h3>
                <ul className="footer-links">
                  <li><a href="/">Strona Główna</a>
                  </li>
                  {isAuthorized("USER") &&
                    <li><a href="/user">Uzytkownik</a>
                    </li>
                  }
                </ul>
              </div>
              <div className="col-md-4 col-sm-6">
                <h3>Znajdź nas</h3>
                <ul className="footer-links">
                  <li><a href="#">Facebook</a>
                  </li>
                  <li><a href="#">Twitter</a>
                  </li>
                  <li><a href="#">Instagram</a>
                  </li>
                </ul>
              </div>
            </div>

          </div>
        </div>

        <div className="row">

          <div className="container-fluid">
            <div className="copyright"> Copyright 2019 | All Rights Reserved by LearnPython XD</div>
          </div>

        </div>
      </div>

    </footer>
  );
};

export default Footer;
