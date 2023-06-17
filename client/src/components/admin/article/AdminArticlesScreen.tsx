import { ReactNode, useContext, useEffect, useState } from 'react';
import { Accordion, Container, Nav, NavDropdown } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate } from 'react-router-dom';
import { ArticleMenu, deleteArticle, loadArticleMenu } from '../../article/apis/article';
import { useError } from '../../common/ErrorContext';
import { ThemeContext } from '../../themes/ThemeProvider';
import { AppWrapper } from '../../common/AppWrapper';
import { AddChapter } from './AddChapter';

type ChapterProps = {
  id: number;
  name: string;
  articles: ArticleProps[];
};

type ArticleProps = {
  id: number;
  title: string;
};

const Chapter = ({ chapter }: { chapter: ChapterProps }) => {
  const [isOpen, setIsOpen] = useState(false);
  const { errorMessages, setError } = useError();

  const navigate = useNavigate();
  function removeArticle(id: number): void {
    deleteArticle(id).then((data) => {
      if (data.isOk) {
        navigate('/admin/articles');
      }
      else {
        console.log(data.error);
        setError('Nie udało się usunąć artykułu');
      }
    });
  }

  return (
    <div className={`border rounded p-3 mb-4`}>
      <button className={`btn btn-link p-0`} onClick={() => setIsOpen(!isOpen)}>
        <h2>{chapter.name}</h2>
      </button>
      <ul className={`list-unstyled ${isOpen ? '' : 'd-none'}`}>
        {chapter.articles.map((article) => (
          <li key={article.id} className={`border rounded p-2 my-2 `}>
            {article.title}
            <button className="btn btn-sm btn-primary mx-2" onClick={() => navigate(`/admin/edit/${article.id}`)}>
              Edytuj
            </button>
            <button className="btn btn-sm btn-danger" onClick={() => removeArticle(article.id)}>
              Usuń
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export const AdminArticlesScreen = () => {
  const navigate = useNavigate();
  const [currentArticle, setCurrentArticle] = useState<number | null>(null);

  const [result, setResult] = useState<ArticleMenu[]>();
  const { errorMessages, setError } = useError();
  const [addChapter, setAddChapter] = useState(false);

  const location = useLocation();
  useEffect(() => {
    loadArticleMenu().then((data) => {
      if (data.isOk) {
        setResult(data.value);
      } else {
        setError('Nie udało się załadować listy artykułów');
      }
    });
  }, [location]);

  const [showModal, setShowModal] = useState(false);

  const handleOpenModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <AppWrapper hideSidebar>
      <AddChapter isShown={showModal} onClose={handleCloseModal} />
      <Container className="my-5">
        <div className="">
          <div className="d-flex justify-content-end mb-3">
            <button className="btn btn-success" onClick={() => navigate('/admin/edit')}>
              Dodaj artykuł
            </button>
            <button className="btn btn-success" onClick={handleOpenModal}>
              Dodaj rozdział
            </button>
          </div>
          <Nav className="flex-column">
            {result?.map((chapter) => (
              <Chapter key={chapter.id} chapter={chapter} />
            ))}
          </Nav>
        </div>
      </Container>
    </AppWrapper>
  );
};
