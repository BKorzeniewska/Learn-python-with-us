import { useEffect, useState } from "react";
import { CommentResponse } from "./apis/comment"
import { UserInfo, loadUserById } from "../user/apis/profile";
import { useError } from "../home/ErrorContext";


type Props = {
    content: string;
    userId: number;
    date: string;
}

export const Comment = (props: Props) => {
    const [user, setUser] = useState<UserInfo | undefined>(undefined);
    const { errorMessages, setError } = useError();

    useEffect(() => {
        loadUserById(props.userId.toString()).then((currentUser) => {
            if (currentUser.isOk) {
                setUser(currentUser.value);
            } else {
                setError("Nie udało się wczytać informacji o użytkowniku");
            }
        });
    }, [props.userId]);

    return (
        <div className="card mb-3">
            <div className="card-body">
                {user && (
                    <div>
                        <h5 className="card-title">{user.nickname}</h5>
                        <p className="card-text">{props.content}</p>
                        <p className="card-text">
                            <small className="text-muted">{props.date}</small>
                        </p>
                    </div>
                )}
            </div>
        </div>
    );
};