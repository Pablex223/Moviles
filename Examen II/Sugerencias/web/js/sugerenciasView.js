var controller;

function pageLoad() {
    controller = new SugerenciasController(this);
    addListener();
}

function addListener() {
    let search  = document.getElementById('searchText');
    search.addEventListener('click', controller.search);

    let agregar = document.getElementById('agregar');
    agregar.addEventListener('click', controller.agregar);
    
}

document.addEventListener("DOMContentLoaded", pageLoad);