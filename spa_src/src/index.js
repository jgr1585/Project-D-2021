
import React from 'react';
import ReactDOM from 'react-dom';

import Root from './components/root';
import * as serviceWorker from './serviceWorker';

(async () => {
    ReactDOM.render(<React.StrictMode><Root /></React.StrictMode>, document.getElementById('root'));
    serviceWorker.register();
})();

