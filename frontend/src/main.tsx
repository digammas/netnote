import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { ApolloClient, ApolloProvider, InMemoryCache } from "@apollo/client";

const client = new ApolloClient({
    uri: import.meta.env.VITE_API_URL,
    cache: new InMemoryCache(),
});

const root = createRoot(document.getElementById('root')!);

root.render(
    <StrictMode>
        <ApolloProvider client={client}>
            <App/>
        </ApolloProvider>
    </StrictMode>,
)
