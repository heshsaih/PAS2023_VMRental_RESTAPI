import { BrowserRouter } from 'react-router-dom';
import { RoutesComponent } from './router/Routes/index';
import "./index.css";

const App = () => {
  return (
    <BrowserRouter>
      <RoutesComponent></RoutesComponent>
    </BrowserRouter>
  );
};

export default App
