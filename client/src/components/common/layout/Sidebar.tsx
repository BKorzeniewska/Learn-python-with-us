import { ReactNode, useContext, useEffect, useState } from 'react';
import { Nav, NavDropdown } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import './side-bar.css'
import { ThemeContext } from '../../themes/ThemeProvider';
import { ArticleMenu, loadArticleMenu } from '../apis/article';
import { useLocation, useNavigate } from 'react-router-dom';
import { useError } from '../../home/ErrorContext';

type DropdownLinkProps = {
  name: string;
  children: React.ReactNode;
}

function DropdownLink({ children, name }: DropdownLinkProps) {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleDropdownToggle = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const arrowStyle = {
    transform: isDropdownOpen ? 'rotate(90deg) scaleY(2) scaleX(0.5)' : 'rotate(270deg) scaleY(2) scaleX(0.5)',
  };

  return (
    <Nav.Item>
      <Nav.Link onClick={handleDropdownToggle} className="sidebar-item">{name}
        <span style={arrowStyle} className='arrow'>&#9658;</span>
      </Nav.Link>
      {isDropdownOpen && children}
    </Nav.Item>
  );
}

export const Sidebar = (props: { width: number, children: React.ReactNode }) => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const navigate = useNavigate();

  const handleDropdownToggle = () => {
    setIsDropdownOpen(!isDropdownOpen);
  }

  const [result, setResult] = useState<ArticleMenu[]>();
  const { errorMessage, setError } = useError();

  const location = useLocation();
  useEffect(() => {
    loadArticleMenu().then((data) => {
      if (data.isOk) {
        setResult(data.value);
      } else {
        setError("Nie udało się załadować listy artykułów");
      }
    });
  }, [location]);

  const { theme } = useContext(ThemeContext);

  return (
    <Nav className="flex-column sidebar" data-theme={theme} style={{ width: props.width }} >
      {result?.map((value) => {
        return (
          <div key={value.id}>
            <DropdownLink name={value.name}>
              {value.articles.map((article) => (
                <Nav.Link onClick={() => navigate(`/article/${article.id}`)} className="sidebar-item inner-sidebar">{article.title}</Nav.Link>
                // <NavDropdown.Item key={article.id} href={`#${article.id}`}>
                //   {article.title}
                // </NavDropdown.Item>
              ))}
            </DropdownLink>
          </div>
        );
      })}
      <Nav.Item>
        <Nav.Link className="sidebar-item" href="#">Link 1</Nav.Link>
      </Nav.Item>
      <Nav.Item>
        <Nav.Link className="sidebar-item" href="#">Link 2</Nav.Link>
      </Nav.Item>
      <Nav.Item>
        <Nav.Link onClick={handleDropdownToggle} className="sidebar-item">Dropdown</Nav.Link>
        {isDropdownOpen && (
          <>
            <Nav.Link className="sidebar-item" href="#">Action 1</Nav.Link>
            <Nav.Link className="sidebar-item" href="#">Action 2</Nav.Link>
            <Nav.Link className="sidebar-item" href="#">Separated link</Nav.Link>
          </>
        )}
      </Nav.Item>
      <Nav.Item>
        <Nav.Link className="sidebar-item" href="#">Link 3</Nav.Link>
      </Nav.Item>
    </Nav>
  );
};

