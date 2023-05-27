import { useEffect, useState } from "react";
import { CommentResponse } from "./apis/comment";
import { UserInfo, loadUserById } from "../user/apis/profile";
import { useError } from "../home/ErrorContext";
import { Card } from "react-bootstrap";
import { generateRandomPixels } from "../user/avatarGen";
import "./comments.css";

type Props = {
  data: CommentResponse;
};

export const Comment = (props: Props) => {
  const formattedDate = new Date(props.data.createdAt).toLocaleString();

  return (
    <Card className="mb-3">
      <Card.Body>
        <div className="d-flex align-items-center">
          <img
            src={`data:image/png;base64,${generateRandomPixels(props.data.userDetails.nickname + props.data.userDetails.email)}`}
            className="rounded-circle avatar"
            width={25}
          />
          <div>
            <Card.Title>{props.data.userDetails.nickname}</Card.Title>
            <Card.Text>{props.data.content}</Card.Text>
            <Card.Text>
              <small className="text-muted">{formattedDate}</small>
            </Card.Text>
          </div>
        </div>
      </Card.Body>
    </Card>
  );
};

