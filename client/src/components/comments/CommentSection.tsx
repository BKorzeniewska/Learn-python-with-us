import { useContext, useEffect, useState } from "react";
import { CommentResponse, loadCommentsByArticleId } from "./apis/comment"
import { UserInfo, loadUserById } from "../user/apis/profile";
import { useError } from "../common/ErrorContext";
import { Comment } from "./Comment";
import { CommentForm } from "./CommentForm";
import { AuthContext } from "../auth/AuthContext";


type Props = {
    articleId: number;
}

export const CommentSection = (props: Props) => {
    const [comments, setComments] = useState<CommentResponse[]>();
    const { errorMessages, setError } = useError();
    const { isLoggedIn } = useContext(AuthContext);

    useEffect(() => {
        loadCommentsByArticleId(props.articleId.toString()).then((com) => {
            if (com.isOk) {
                setComments(com.value);
            } else {
                setError("Nie udało się wczytać listy komentarzy");
            }
        });
    }, [props.articleId]);

    const handleDeleteComment = (commentId: number) => {
        setComments((prevComments) => prevComments?.filter((comment) => comment.id !== commentId));
      };

    return (
        <div>
          {isLoggedIn() && <CommentForm setComments={setComments} articleId={props.articleId} />}
          {comments &&
            comments.slice().reverse().map((comment) => (
              <Comment onDelete={handleDeleteComment} data={comment} key={comment.id} />
            ))}
        </div>
      );
};