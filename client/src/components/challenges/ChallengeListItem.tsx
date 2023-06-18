import { ReactNode, useContext, useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Form, ListGroup, Modal, Nav, NavDropdown, Row } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { AppWrapper } from '../common/AppWrapper';
import { ChallengeResponse, ChallengeResult, deleteChallenge, getChallengesByArticleId } from './apis/challenge';
import { useError } from '../common/ErrorContext';
import { ChooseChallenge } from './choose';
import { CodeChallenge } from './code';
import { OpenChallenge } from './open';
import { BsCheck2Circle } from 'react-icons/bs';
import { AuthContext } from '../auth/AuthContext';
import { FaPen, FaTrash } from 'react-icons/fa';

type Props = {
    challenge: ChallengeResponse,
}


export const ChallengeListItem = (props: Props) => {
    const navigate = useNavigate();
    const { articleId } = useParams();
    const { errorMessages, setError } = useError();
    const location = useLocation();
    const { isAuthorized } = useContext(AuthContext);
    //make a copy of challenge

    const challenge: ChallengeResponse = { ...props.challenge };

    const [showModal, setShowModal] = useState(false);


    const handleDeleteChallenge = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        event.preventDefault();
        event.stopPropagation();
        deleteChallenge(challenge.id).then((res) => {
            if (res.isOk) {
                navigate(0);
            } else {
                setError("Nie udało się usunąć zadania");
            }
        })

        setShowModal(false);
    };


    return (
        <div
            className="custom-list-item d-flex justify-content-between align-items-start "
            onClick={() => navigate(`/challenge/`, { state: { challenge } })}
        >
            <Modal show={showModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Usunąć zadanie?</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Czy jesteś pewny że chcesz usunąć to zadanie?
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={(event) => { event.preventDefault();event.stopPropagation();setShowModal(false) }}>
                        Nie
                    </Button>
                    <Button variant="danger" onClick={(e) => handleDeleteChallenge(e)}>
                        Tak
                    </Button>
                </Modal.Footer>
            </Modal>

            <div className="ms-2 me-auto">
                <div className="fw-bold">{props.challenge.name}</div>
                {props.challenge.question.length > 150 ? (
                    <span>{props.challenge.question.slice(0, 120).trim()}...</span>
                ) : (
                    <span>{props.challenge.question}</span>
                )}

            </div>
            <div className='option-section'>
                {isAuthorized("MODERATOR") &&
                    <span className="modify-button">
                        <FaPen onClick={(event) => { event.preventDefault(); event.stopPropagation(); navigate(`/admin/challenge/edit/${props.challenge.id}`) }} />

                    </span>}
                {isAuthorized("MODERATOR") &&
                    <span className="modify-button">
                        <FaTrash onClick={(event) => { event.preventDefault(); event.stopPropagation(); setShowModal(true) }} />

                    </span>}
                {props.challenge.done && <BsCheck2Circle className="ms-2" />}
            </div>
        </div>
    );
};
