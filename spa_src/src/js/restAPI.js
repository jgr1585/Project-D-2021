import Axios from '../../node_modules/axios';

class restAPI {
    constructor(accessKey, refreshKey) {
        this.accessKey = accessKey;
        this.refreshKey = refreshKey;

        this.reTry = true;
    }

    postMethod(url, payload) {
        let refreshToken = url.includes('/token');

        this.config = {
            headers: {
                'Content-Type': refreshToken ? 'application/x-www-form-urlencoded' : 'application/json',
                'Authorization': refreshToken ? `Basic ${window.btoa('gibench:')}` : `Bearer ${localStorage.getItem(this.accessKey)}`
            }
        };

        if (refreshToken) {
            const queryString = require('query-string');
            payload = queryString.stringify({
                'grant_type': 'refresh_token',
                'refresh_token': localStorage.getItem(this.refreshKey),
            });
        }

        return new Promise((resolve, reject) => {
            Axios.post(url, payload, this.config)
                .then(response => {
                    if (response.status === 200) {
                        if (refreshToken && this.reTry === false) {
                            // update local storage
                            localStorage.setItem(this.accessKey, response.data.access_token);
                            localStorage.setItem(this.refreshKey, response.data.refresh_token);

                            let { resolve, reject, url, payload } = this.oldCall;
                            this.postMethod(url, payload).then(
                                (success) => {
                                    resolve(success);
                                },
                                (error) => {
                                    reject(error);
                                });
                        } else {
                            if (resolve) {
                                this.reTry = true;
                                resolve(response);
                            }
                        }
                    }
                })
                .catch(error => {
                    let errorMsg = error.message;
                    try {
                        let response = error.response;

                        if (response.data && (response.data.faultCode === -4312 || response.data.faultCode === -4313)) {
                            errorMsg = response.data.faultCode;
                        } else if (response.status === 406) {
                            if (response.data && response.data.ReturnState && response.data.ReturnState.Description)
                                errorMsg = response.data.ReturnState.Description;
                            else
                                errorMsg = response.status;
                        } else if ((response.status === 403 || error.response.status === 401) && this.createUrl && this.reTry) {
                            this.reTry = false;

                            this.oldCall = {
                                resolve: resolve,
                                reject: reject,
                                url: url,
                                payload: payload,
                            };

                            url = this.createUrl('/token');
                            this.postMethod(url, []).then(resolve, reject);

                            return;
                        } else {
                            if (response.data && response.data.ReturnState && response.data.ReturnState.Description)
                                errorMsg = response.data.ReturnState.Description;
                            else
                                errorMsg = response.status;
                        }

                        reject(errorMsg);
                    } catch {
                        reject(errorMsg);
                    }
                });
        });
    }

    getParams(url) {
        this.config = {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem(this.accessKey)}`
            }
        };

        return new Promise((resolve, reject) => {
            let reCallMethod = () => {
                this.reTry = false;

                this.oldCall = {
                    resolve: resolve,
                    reject: reject,
                    url: url,
                    payload: [],
                };

                url = this.createUrl('/token');
                this.postMethod(url, []).then(resolve, reject);
            };

            Axios.post(url, [], this.config)
                .then(response => {
                    if (Object.keys(response.data).length <= 0) {
                        reCallMethod();
                        return;
                    }

                    resolve(response);
                })
                .catch(error => {
                    let errorMsg = error.message;
                    try {
                        let response = error.response;

                        if (response.data && (response.data.faultCode === -4312 || response.data.faultCode === -4313)) {
                            errorMsg = response.data.faultCode;
                        } else if (response.status === 406) {
                            if (response.data && response.data.ReturnState && response.data.ReturnState.Description)
                                errorMsg = response.data.ReturnState.Description;
                            else
                                errorMsg = response.status;
                        } else if ((error.response.status === 403 || error.response.status === 401) && this.createUrl && this.reTry) {
                            reCallMethod();
                            return;
                        } else {
                            if (response.data && response.data.ReturnState && response.data.ReturnState.Description)
                                errorMsg = response.data.ReturnState.Description;
                            else
                                errorMsg = response.status;
                        }

                        reject(errorMsg);
                    } catch {
                        reject(errorMsg);
                    }
                });
        })
    }
}

export { restAPI };