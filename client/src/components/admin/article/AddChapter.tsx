import { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import { createChapter } from "../../common/apis/chapter";
import { useError } from "../../common/ErrorContext";

type Props = {
  isShown: boolean;
  onClose: () => void;
};

export const AddChapter: React.FC<Props> = ({ isShown, onClose }) => {
  const [chapterName, setChapterName] = useState("");
  const { errorMessages, setError } = useError();

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    console.log("Chapter Name:", chapterName);
    createChapter(chapterName).then(
        (article) => {
            if (article.isOk) {
                window.location.reload();
            } else {
                setError("Nie udało się utworzyć rodziału");
            }
        }
    )
    onClose();
  };

  return (
    <>
      <Modal show={isShown} onHide={onClose}>
        <Modal.Header closeButton>
          <Modal.Title>Dodaj Rozdział</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
              <Form.Label>Nazwa rozdziału</Form.Label>
              <Form.Control
                type="text"
                placeholder="funkcje"
                autoFocus
                value={chapterName}
                onChange={(event) => setChapterName(event.target.value)}
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Dodaj
            </Button>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={onClose}>
            Zamknij
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
