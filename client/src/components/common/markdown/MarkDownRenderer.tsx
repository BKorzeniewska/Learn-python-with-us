import ReactMarkdown from "react-markdown";
import remarkParse from 'remark-parse'
import remarkGfm from 'remark-gfm'
import { Prism as SyntaxHighlighter } from "react-syntax-highlighter";
import { CodeProps } from "react-markdown/lib/ast-to-react";
import { oneDark, oneLight } from 'react-syntax-highlighter/dist/cjs/styles/prism';
import { useContext } from "react";
import { ThemeContext } from "../../themes/ThemeProvider";



export const MarkDownRenderer = (props: { content: string }) => {
    const {theme} = useContext(ThemeContext);
    
    const usedTheme = theme === "light" ? oneLight : oneDark;

    return (
        <ReactMarkdown
            children={props.content}
            remarkPlugins={[remarkParse, [remarkGfm]]}
            components={{
                code({ node, inline, className, children, style, ...props }: CodeProps) {
                    const match = /language-(\w+)/.exec(className || "");
                    
                    return !inline && match ? (
                        <SyntaxHighlighter
                            children={String(children).replace(/\n$/, "")}
                            language={match[1]}
                            style={usedTheme}
                            PreTag="div"
                            {...props}
                        />
                    ) : (
                        <code className={className} {...props}>
                            {children}
                        </code>
                    );
                },
            }}
        />
    );
}
