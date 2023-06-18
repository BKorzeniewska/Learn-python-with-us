import { Spinner } from "react-bootstrap";
import "./spinner.css"


export const LoadingSpinner = (props: {
    isLoading: boolean;
    children: React.ReactNode;
}) => {
    return (
        <div style={{width: "100%"}}>
            {props.isLoading ? <div className="spinner-wrapper"><Spinner animation="border" role="status" className="spinner">
                <span className="sr-only" style={{
                    position: "absolute",
                    fontSize: "0.8rem",
                }}>Loading</span>
            </Spinner></div> : props.children}
        </div>
    )
}