import { Spinner } from "react-bootstrap";


export const LoadingSpinner = (props: {
    isLoading: boolean;
    children: React.ReactNode;
}) => {
    return (
        <div style={{width: "100%" }}>
            {props.isLoading ? <Spinner animation="border" role="status">
                <span className="sr-only" style={{
                    position: "absolute",
                    fontSize: "0.8rem",
                }}>Loading</span>
            </Spinner> : props.children}
        </div>
    )
}