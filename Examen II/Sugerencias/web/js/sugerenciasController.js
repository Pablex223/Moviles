function SugerenciasController(view) {
    this.SugerenciasController(view);
}

SugerenciasController.prototype = {
    SugerenciasController: (view) => {
        this.view = view;
    },
    search: (event) => {
        let topic = document.getElementById('texto').value;
        Proxy.searchText(topic, (response) => {
            if(response.data) {
                listSugerencias([]);
            } else {
                listSugerencias(response);
            }
        })
    },
    agregar: (event) => {
        let texto = document.getElementById('sugerenciaNueva').value;
        Proxy.addSugerencia(texto, (response) => {
            if(response.data.split('~')[0] === 'C') {
                window.alert('Sugerencia Agregada!');
            } else {
                window.alert('No se pudo agregar la sugerencia!');
            }
        })
    },
}

function listSugerencias(sugerencias) {
    let listado = document.getElementById('sugerencia-list');
    listado.innerHTML = '';
    if (sugerencias.length == 0) {
        let tr = document.createElement('tr');
        let td;
        td = document.createElement('td');
        td.setAttribute('colspan', 2);
        td.appendChild(document.createTextNode('Usted no tiene sugerencias'));
        tr.appendChild(td);
        listado.appendChild(tr);
    } else {
        sugerencias.forEach( (sugerencia) => listSugerencia(listado, sugerencia) );
    }
}

function listSugerencia(listado, sugerencia) {
    let tr = document.createElement('tr');
    tr.setAttribute('id', sugerencia.id);
    let td;
    td = document.createElement('td');
    td.appendChild(document.createTextNode(sugerencia.texto));
    tr.appendChild(td);

    td = document.createElement('td');
    let star = '<img src="img/star.png" class="small-star"> ';
    let stars = '';
    for (let i = 0; i < sugerencia.puntaje; i++) stars += star;
    td.innerHTML = stars;
    tr.appendChild(td);
    /*
    tr.addEventListener('click', function () {
        controller.desplegarRespuestas(pregunta.id, pregunta.questionname);
    });
    */
    listado.appendChild(tr);
}