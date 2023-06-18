import { useContext, useEffect, useState } from "react";
import { CommentResponse, deleteComment } from "./apis/comment";
import { useError } from "../common/ErrorContext";
import { Card } from "react-bootstrap";
import { generateRandomPixels } from "../user/avatarGen";
import "./comments.css";
import { AuthContext } from "../auth/AuthContext";

type Props = {
  data: CommentResponse;
  onDelete: (commentId: number) => void;
};

export const Comment = (props: Props) => {
  const formattedDate = new Date(props.data.createdAt).toLocaleString();
  const {getUser, isAuthorized} = useContext(AuthContext); 
  const { errorMessages, setError } = useError();

  function removeComment(): void {
    deleteComment(props.data.id).then((res) => {
      if (res.isOk) {
          console.log("ok");
          props.onDelete(props.data.id);
      } else {
          setError("Nie udało się usunąć komentarza");
          setError(res.error.errorMessage);
      }
  }
  )
  }

  return (
    <Card className="mb-3">
      <Card.Body>
        <div className="d-flex align-items-center">
          <img
            src={`data:image/png;base64,${generateRandomPixels(props.data.userDetails.nickname + props.data.userDetails.email)}`}
            className="rounded-circle avatar"
            width={25}
          />
          <div className="content-container">
            <Card.Title>{props.data.userDetails.nickname}</Card.Title>
            <Card.Text>{props.data.content}</Card.Text>
            <Card.Text>
              <small className="text-muted">{formattedDate}</small>
              {(isAuthorized("ADMIN") || getUser()?.userId === props.data.userDetails.id) && 
              
              <span className="delete-button" onClick={removeComment}>usuń komentarz</span>
              }
              
            </Card.Text>
          </div>
        </div>
      </Card.Body>
    </Card>
  );
};

