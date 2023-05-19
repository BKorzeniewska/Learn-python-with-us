import { useEffect, useState } from "react";
import { CommentResponse } from "./apis/comment";
import { UserInfo, loadUserById } from "../user/apis/profile";
import { useError } from "../home/ErrorContext";
import { Card } from "react-bootstrap";

type Props = {
  data: CommentResponse;
};

export const Comment = (props: Props) => {
  return (
    <Card className="mb-3">
      <Card.Body>
        <div>
          <Card.Title>{props.data.userDetails.nickname}</Card.Title>
          <Card.Text>{props.data.content}</Card.Text>
          <Card.Text>
            <small className="text-muted">{props.data.createdAt}</small>
          </Card.Text>
        </div>
      </Card.Body>
    </Card>
  );
};
