
import React from 'react';
import ReactDOM from 'react-dom';

import Root from './components/Root';
import * as serviceWorker from './serviceWorker';

(async () => {
    ReactDOM.render(<React.StrictMode><Root /></React.StrictMode>, document.getElementById('root'));
    serviceWorker.register();
})();

