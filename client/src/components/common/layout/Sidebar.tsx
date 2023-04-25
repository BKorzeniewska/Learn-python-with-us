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
  dropdown?: boolean;
}

function DropdownLink({ children, name, dropdown }: DropdownLinkProps) {
  const [isDropdownOpen, setIsDropdownOpen] = useState(dropdown?true:false);

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
  const [currentArticle, setCurrentArticle] = useState<number | null>(null);

  const handleDropdownToggle = () => {
    setIsDropdownOpen(!isDropdownOpen);
  }

  const [result, setResult] = useState<ArticleMenu[]>();
  const { errorMessages, setError } = useError();

  const location = useLocation();
  useEffect(() => {
    loadArticleMenu().then((data) => {
      if (data.isOk) {
        setResult(data.value);
      } else {
        setError("Nie udało się załadować listy artykułów");
      }
    });
    console.log(location.pathname.split("/"));
    if(location.pathname.split("/")[1] === "article")
    {
      setCurrentArticle(parseInt(location.pathname.split("/")[2]));
    }
  }, [location]);

  const { theme } = useContext(ThemeContext);

  return (
    <Nav className="flex-column sidebar" data-theme={theme} style={{ width: props.width }} >
      {result?.map((value) => {
        return (
          <div key={value.id}>
            <DropdownLink name={value.name} dropdown={value.articles.some((article) => article.id === currentArticle)} >
              {value.articles.map((article) => (
                <Nav.Link onClick={() => navigate(`/article/${article.id}`)} className={`sidebar-item inner-sidebar ${article.id === currentArticle ? 'active' : ''}`}>{article.title}</Nav.Link>
              ))}
            </DropdownLink>
          </div>
        );
      })}
    </Nav>
  );
};

