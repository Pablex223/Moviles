var Proxy = Proxy || {};

Proxy.login = (username, password, callback) => {
    let AJAX_req = new XMLHttpRequest();
    let url = 'UsuarioServlet?action=login';
    AJAX_req.open("POST", url, true);
    AJAX_req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    AJAX_req.onreadystatechange = function() {
        if (AJAX_req.readyState === 4 && AJAX_req.status === 200){
            let response = JSON.parse(AJAX_req.responseText);
            if(callback)    
                callback(response);
        }
    };
    AJAX_req.send("username=" + username + "&password=" + password);
};

Proxy.logout = (callback) => {
    let AJAX_req = new XMLHttpRequest();
    let url = 'UsuarioServlet?action=logout';
    AJAX_req.open("POST", url, true);
    AJAX_req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    AJAX_req.onreadystatechange = function() {
        if (AJAX_req.readyState === 4 && AJAX_req.status === 200){
            let response = JSON.parse(AJAX_req.responseText);
            if(callback)    
                callback(response);
        }
    };
    AJAX_req.send();
};

Proxy.searchText = (texto, callback) => {
    let AJAX_req = new XMLHttpRequest();
    let url = 'SugerenciaServlet?action=searchText';
    AJAX_req.open("POST", url, true);
    AJAX_req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    AJAX_req.onreadystatechange = function() {
        if (AJAX_req.readyState === 4 && AJAX_req.status === 200){
            let response = JSON.parse(AJAX_req.responseText);
            if(callback)    
                callback(response);
        }
    };
    AJAX_req.send("texto=" + texto);
};

Proxy.addSugerencia = (texto, callback) => {
    let AJAX_req = new XMLHttpRequest();
    let url = 'SugerenciaServlet?action=addSugerencia';
    AJAX_req.open("POST", url, true);
    AJAX_req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    AJAX_req.onreadystatechange = function() {
        if (AJAX_req.readyState === 4 && AJAX_req.status === 200){
            let response = JSON.parse(AJAX_req.responseText);
            if(callback)    
                callback(response);
        }
    };
    AJAX_req.send("texto=" + texto);
};
