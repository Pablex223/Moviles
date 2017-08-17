function LoginController(view) {
    this.LoginController(view);
}

LoginController.prototype = {
    LoginController: (view) => {
        this.view = view;
    },
    doValidation: (event) => {
        let form = event.target;
        let username = form.elements.namedItem('username');
        let password = form.elements.namedItem('password');
        let isInvalid = false;
        isInvalid = isBlank(username);
        isInvalid = isBlank(password);

        if(isInvalid) {
            event.preventDefault();
            return;
        }
    },
    doLogin: function() {
        let username = document.getElementById('username');
        let password = document.getElementById('password');

        username.classList.remove('invalid');
        password.classList.remove('invalid');
        Proxy.login(username.value, password.value, (response) => {
            if(response.data === undefined) {
                if(response.tipo === 1) {
                    document.location = 'sugerencias.jsp';
                } else if(response.tipo === 2) {
                    document.location = 'sugerencias.jsp';
                } else {
                    username.classList.add('invalid');
                    password.classList.add('invalid');
                }
            } else {
                username.classList.add('invalid');
                password.classList.add('invalid');
            }
        });
    },
    doLogout: function() {
        Proxy.logout((response) => {
            document.location = '/Sugerencias/index.jsp';
        });
    }
}

function isBlank(element) {
    element.classList.remove("invalid");
    if (element.value.length == 0) {
        element.classList.add("invalid");
        return true;
    }
}
