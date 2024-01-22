import { BrowserRouter } from 'react-router-dom';
import { RoutesComponent } from './router/Routes/index';
import "./index.css";
import {UserStateContextProvider} from "./context/UserContext.tsx";

const App = () => {
  return (
    <BrowserRouter>
          <UserStateContextProvider>
              <RoutesComponent></RoutesComponent>
          </UserStateContextProvider>
    </BrowserRouter>
  );
};

export default App
