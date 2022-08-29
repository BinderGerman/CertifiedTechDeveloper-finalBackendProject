window.addEventListener('load', () => {

/* -------------------------------------------------------------------------- */
/*   Momento inicial atrapamos todos los id del HTML que vamos a utilizar     */
/* -------------------------------------------------------------------------- */
    let btnOpenPopup = document.getElementById('open-popup-dentist'),
        btnClosePopup = document.getElementById('close-popup'),
        overlay = document.getElementById('overlay'),
        popup = document.getElementById('popup'),

        register = document.getElementById('register'),
        clearLog = document.getElementById('clear-log'),
        listDentists = document.getElementById('list-dentists'),
        list = document.getElementById('list'),
        read = document.getElementById('read-form-dentist'), 
        readDentist = document.getElementById('read-dentist');

/* -------------------------------------------------------------------------- */
/*                      Método que abre el pop up                             */
/* -------------------------------------------------------------------------- */
    btnOpenPopup.addEventListener('click', () => {
        overlay.classList.add('active'); 
        popup.classList.add('active');

        renderForm();
    })

/* -------------------------------------------------------------------------- */
/*          Método para renderizar el formulario dentro del popup             */
/* -------------------------------------------------------------------------- */
    function renderForm() {
        list.innerHTML = "";
        register.innerHTML = "";
        clearLog.innerHTML = "";

        register.innerHTML += `<form id="registerDentist">
                                    <fieldset>
                                        <legend>Datos del Odontólogo</legend>
                                        <label for="name">
                                        Nombre:
                                            <input type="text" name="name" id="name" placeholder="Ingrese el nombre" />     
                                        </label>
                                        <label for="surname">
                                        Apellido:
                                            <input type="text" name="surname" id="surname" placeholder="Ingrese el apellido" />     
                                        </label>
                                        <label for="dni">
                                        DNI:
                                            <input type="number" name="dni" id="dni" placeholder="Ingrese el DNI" />
                                        </label>
                                        <label for="tuition">
                                        Matricula:
                                            <input type="text" name="tuition" id="tuition" placeholder="Ingrese la matrícula" /> 
                                        </label>
                                    </fieldset>
                                    <button type="submit">Guardar</button>
                                    <div class="ok" id="ok"></div>
                                    <div class="problem" id="problem"></div>
                                </form>`
        recordDate();
    }

/* -------------------------------------------------------------------------- */
/*               Método POST para la carga de odontólogos                     */
/* -------------------------------------------------------------------------- */
    function recordDate() {
        let registerDentist = document.getElementById('registerDentist'),
            name = document.getElementById('name'),
            surname = document.getElementById('surname'),
            dni = document.getElementById('dni'),
            tuition = document.getElementById('tuition');

            registerDentist.addEventListener('submit', (e) =>{
                e.preventDefault();
    
                ok.innerHTML = "";
                problem.innerHTML = "";
                clearLog.innerHTML = "";
    
                let dentistData = {
                    name: name.value,
                    surname: surname.value,
                    dni: dni.value,
                    tuition: tuition.value,
                }
    
                let config = {
                    method: 'POST',
                    body: JSON.stringify(dentistData),
                    headers: {
                            'Content-Type': 'application/json',
                    }
                }
    
                fetch('http://localhost:8080/odontologos', config)
                        .then(response => {
                            return response.json();
                        })
    
                        .then(info => {
                            console.log(info);
                            ok.innerHTML = `<p>Odontólogo: ${info.name.toUpperCase()} ${info.surname.toUpperCase()} cargado correctamente!</p>`;
                        })
    
                        .catch(err => {
                                    problem.innerHTML = `<p>Error: Intente nuevamente</p>`;
                                    console.log(err);
                        })
    
                registerDentist.reset();
    
            })
    }

/* -------------------------------------------------------------------------- */
/*              Método GET para traer la lista de odontólogos                 */
/* -------------------------------------------------------------------------- */
    listDentists.addEventListener('click', (e) => {
        e.preventDefault();

        fetch('http://localhost:8080/odontologos')
            .then(response => {
                return response.json();
            })

            .then(info => {
                console.log(info);

                renderListDentists(info);
            })
    })

/* -------------------------------------------------------------------------- */
/*          Método para renderizar la tabla de odontólogos                    */
/* -------------------------------------------------------------------------- */
    function renderListDentists(info) {
        list.innerHTML = `<div class="render-list" id="render-list">
                            <table id="render-table">
                                <caption>ODONTÓLOGOS</caption>
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>NOMBRE</th>
                                        <th>APELLIDO</th>
                                        <th>DNI</th>
                                        <th>MATRÍCULA</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>`

        renderTable = document.getElementById('render-table')

        info.map(infoDentist => {
            renderTable.innerHTML += `<tbody>
                                        <tr>
                                            <td data-label="ID">${infoDentist.id}</td>
                                            <td data-label="NOMBRE">${infoDentist.name.toUpperCase()}</td>
                                            <td data-label="APELLIDO">${infoDentist.surname.toUpperCase()}</td>
                                            <td data-label="DNI">${infoDentist.dni}</td>
                                            <td data-label="MATRÍCULA">MAT: ${infoDentist.tuition.toUpperCase()}</td>
                                        </tr>    
                                    </tbody>`
        
        })
    }

/* -------------------------------------------------------------------------- */
/*                     Método GET para buscar por ID                          */
/* -------------------------------------------------------------------------- */
    read.addEventListener('submit', (e) => {
        e.preventDefault();

        list.innerHTML = "";

        fetch(`http://localhost:8080/odontologos/${readDentist.value}`)
            .then(response => {
                return response.json();
            })
            .then(infoDentist => {
                console.log(infoDentist)

                list.innerHTML = `<div class="render-list" id="render-list">
                                    <table id="render-table">
                                        <caption>ODONTÓLOGO CON ID: ${infoDentist.id}</caption>
                                        <thead>
                                            <tr>
                                                <th>NOMBRE</th>
                                                <th>APELLIDO</th>
                                                <th>MATRÍCULA</th>
                                                <th>MODIFICAR</th>
                                                <th>ELIMINAR</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td data-label="NOMBRE">${infoDentist.name.toUpperCase()}</td>
                                                <td data-label="APELLIDO">${infoDentist.surname.toUpperCase()}</td>
                                                <td data-label="MATRÍCULA">${infoDentist.tuition.toUpperCase()}</td>
                                                <td data-label="MODIFICAR"><button type="submit" id="update-dentist">Modificar</button></td>
                                                <td data-label="ELIMINAR"><button type="submit" id="delete-dentist">Eliminar</button></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>`
                
                read.reset();
                modifyDentist(infoDentist);
                removeDentist(infoDentist);
            })
    })

/* -------------------------------------------------------------------------- */
/*                Método PUT para modificar el odontólogo                     */
/* -------------------------------------------------------------------------- */
function modifyDentist(infoDentist) {
    let updateDentist = document.getElementById('update-dentist'); 

    updateDentist.addEventListener('click', (e) =>{
    overlay.classList.add('active');
    popup.classList.add('active');

    list.innerHTML = "";
    register.innerHTML = "";
    clearLog.innerHTML = "";

    register.innerHTML += `<form id="update-register">
                                <fieldset>
                                    <legend>Datos del Odontólogo</legend>
                                        <label for="name">
                                        Nombre:
                                            <input type="text" name="name" id="name" placeholder="Ingrese el nombre" />
                                        </label>
                                        <label for="surname">
                                        Apellido:
                                            <input type="text" name="surname" id="surname" placeholder="Ingrese el apellido" />
                                        </label>
                                        <label for="dni">
                                        DNI:
                                            <input type="number" name="dni" id="dni" placeholder="Ingrese el DNI" />
                                        </label>
                                        <label for="tuition">
                                        Matrícula:
                                            <input type="text" name="tuition" id="tuition" placeholder="Ingrese la Matrícula" />
                                        </label>
                                </fieldset>
                                <button type="submit">Guardar</button>
                                <div class="ok" id="update-ok"></div>
                                <div class="problem" id="update-problem"></div>
                            </form>`
    
    let updateRegister = document.getElementById('update-register'),
        updateOk = document.getElementById('update-ok'),
        updateProblem = document.getElementById('update-problem');

    document.getElementById('name').value = infoDentist.name;
    document.getElementById('surname').value = infoDentist.surname;
    document.getElementById('dni').value = infoDentist.dni;
    document.getElementById('tuition').value = infoDentist.tuition;
    
    updateRegister.addEventListener('submit', (e) => {
        e.preventDefault();

        let dentistData = {
            id: infoDentist.id,
            name: document.getElementById('name').value,
            surname: document.getElementById('surname').value,
            dni: document.getElementById('dni').value,
            tuition: document.getElementById('tuition').value,
        }

        let config = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dentistData)
            
        }

        fetch('http://localhost:8080/odontologos', config)
            .then(response => {
                return response.json();
            })

            .then(info => {
                console.log(info);
                updateOk.innerHTML = `<p>Odontólogo con ID ${info.id}: modificado correctamente!</p>`;
            })

            .catch(err => {
                        updateProblem.innerHTML = `<p>Error: Intente nuevamente</p>`;
                        console.log(err);
            })
    })

})
}

/* -------------------------------------------------------------------------- */
/*              Método DELETE para eliminar el odontólogo                     */
/* -------------------------------------------------------------------------- */
function removeDentist(infoDentist) {
    let deleteDentist = document.getElementById('delete-dentist');

    deleteDentist.addEventListener('click', (e) => {
        e.preventDefault();
        overlay.classList.add('active');
        popup.classList.add('active');

        register.innerHTML = "";
        
        

        clearLog.innerHTML = `<p>¿Está seguro que quiere eliminar este registro?</p>
                                    <button type="click" id="remove">Borrar</button>`

        let remove = document.getElementById('remove');

        remove.addEventListener('click', () => {
            
            let config = {
                method : "DELETE",
                headers: {
                    'Content-Type': 'application/json'
                },
            }

            fetch(`http://localhost:8080/odontologos/${infoDentist.id}`, config)
                .then((response) => {
                    console.log(response.status);

                    overlay.classList.remove('active');
                    popup.classList.remove('active');
                    list.innerHTML = "";

                })
                
                .catch((error) => console.log(error))

        })

    })


}

/* -------------------------------------------------------------------------- */
/*                      Método para cerrar el popup                           */
/* -------------------------------------------------------------------------- */
    btnClosePopup.addEventListener('click', () => {
        overlay.classList.remove('active');
        popup.classList.remove('active');
    })
})