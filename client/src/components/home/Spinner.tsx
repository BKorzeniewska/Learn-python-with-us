import { Spinner } from "react-bootstrap";


export const LoadingSpinner = (props: {
    isLoading: boolean;
    children: React.ReactNode;
}) => {
    return (
        <>
            {props.isLoading ? <Spinner animation="border" role="status" className="mt-5">
                <span className="sr-only">Loading...</span>
            </Spinner> : props.children}
        </>
    )
}