var controller;

function pageLoad(event) {
    controller = new LoginController(this);
}

function addListeners() {
    let form = document.getElementById('form');
    form.addEventListener('submit', controller.doValidation);

}

function doLogin() {
    controller.doLogin();
}

document.addEventListener("DOMContentLoaded", pageLoad);