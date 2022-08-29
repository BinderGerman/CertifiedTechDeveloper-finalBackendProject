window.addEventListener('load', () => {

    /* -------------------------------------------------------------------------- */
    /*   Momento inicial atrapamos todos los id del HTML que vamos a utilizar     */
    /* -------------------------------------------------------------------------- */
        let btnOpenPopup = document.getElementById('open-popup-turn'),
            btnClosePopup = document.getElementById('close-popup'),
            overlay = document.getElementById('overlay'),
            popup = document.getElementById('popup'),
    
            register = document.getElementById('register'),
            clearLog = document.getElementById('clear-log'),
            listTurns = document.getElementById('list-turns'),
            list = document.getElementById('list'),
            read = document.getElementById('read-form-turn'), 
            readTurn = document.getElementById('read-turn');
    
    /* -------------------------------------------------------------------------- */
    /*                      Método que abre el pop up                             */
    /* -------------------------------------------------------------------------- */
        btnOpenPopup.addEventListener('click', () => {
            overlay.classList.add('active'); 
            popup.classList.add('active');
    
            renderForm();
        })
    
    /* -------------------------------------------------------------------------- */
    /*          Método para renderizar el formulario dentro del pop up            */
    /* -------------------------------------------------------------------------- */
        function renderForm() {
            list.innerHTML = "";
            register.innerHTML = "";
            clearLog.innerHTML = "";
    
            register.innerHTML += `<form id="registerTurn">
                                        <fieldset>
                                            <legend>Datos del Turno</legend>
                                            <label for="patient-id">
                                            Paciente ID:
                                                <input type="number" name="patient-id" id="patient-id" placeholder="Ingrese el ID del paciente" />     
                                            </label>
                                            <label for="dentist-id">
                                            Odontólogo ID:
                                                <input type="number" name="dentist-id" id="dentist-id" placeholder="Ingrese el ID del odontólogo" />     
                                            </label>
                                            <label for="date">
                                            Fecha:
                                                <input type="date" name="date" id="date" placeholder="Ingrese la fecha" />
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
            let registerTurn = document.getElementById('registerTurn'),
                patientId = document.getElementById('patient-id'),
                dentistId = document.getElementById('dentist-id'),
                date = document.getElementById('date');
    
                registerTurn.addEventListener('submit', (e) => {
                    e.preventDefault();
        
                    ok.innerHTML = "";
                    problem.innerHTML = "";
                    clearLog.innerHTML = "";
        
                    let turnData = {
                        patient: {
                            id: patientId.value,
                        }, 
                        dentist: {
                            id: dentistId.value,
                        }, 
                        date: date.value,
                    }
        
                    let config = {
                        method: 'POST',
                        body: JSON.stringify(turnData),
                        headers: {
                                'Content-Type': 'application/json',
                        }
                    }
        
                    fetch('http://localhost:8080/turnos', config)
                            .then(response => {
                                return response.json();
                            })
        
                            .then(info => {
                                console.log(info);
                                ok.innerHTML = `<p>Turno con ID: ${info.id} cargado correctamente!</p>`;
                            })
        
                            .catch(err => {
                                        problem.innerHTML = `<p>Error: Intente nuevamente</p>`;
                                        console.log(err);
                            })
        
                    registerTurn.reset();
        
                })
        }
    
    /* -------------------------------------------------------------------------- */
    /*                 Método GET para traer la lista de turnos                   */
    /* -------------------------------------------------------------------------- */
        listTurns.addEventListener('click', (e) => {
            e.preventDefault();
    
            fetch('http://localhost:8080/turnos')
                .then(response => {
                    return response.json();
                })
    
                .then(info => {
                    console.log(info);
    
                    renderListTurns(info);
                })
        })
    
    /* -------------------------------------------------------------------------- */
    /*               Método para renderizar la tabla de turnos                    */
    /* -------------------------------------------------------------------------- */
        function renderListTurns(info) {
            list.innerHTML = `<div class="render-list" id="render-list">
                                <table id="render-table">
                                    <caption>TURNOS</caption>
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>PACIENTE</th>
                                            <th>ODONTÓLOGO</th>
                                            <th>FECHA</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>`
    
            renderTable = document.getElementById('render-table')
    
            info.map(infoTurn => {
                renderTable.innerHTML += `<tbody>
                                            <tr>
                                                <td data-label="ID">${infoTurn.id}</td>
                                                <td data-label="PACIENTE:">${infoTurn.patient.name} ${infoTurn.patient.surname}</td>
                                                <td data-label="ODONTÓLOGO:">${infoTurn.dentist.name} ${infoTurn.dentist.surname}</td>
                                                <td data-label="FECHA:">${infoTurn.date.slice(0, 10).split('-').reverse().join('-')}</td>
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
    
            fetch(`http://localhost:8080/turnos/${readTurn.value}`)
                .then(response => {
                    return response.json();
                })
                .then(infoTurn => {
                    console.log(infoTurn)
    
                    list.innerHTML = `<div class="render-list" id="render-list">
                                        <table id="render-table">
                                            <caption>TURNO CON ID: ${infoTurn.id}</caption>
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>PACIENTE</th>
                                                    <th>ODONTÓLOGO</th>
                                                    <th>FECHA</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td data-label="ID">${infoTurn.id}</td>
                                                    <td data-label="PACIENTE:">${infoTurn.patient.name} ${infoTurn.patient.surname}</td>
                                                    <td data-label="ODONTÓLOGO:">${infoTurn.dentist.name} ${infoTurn.dentist.surname}</td>
                                                    <td data-label="FECHA:">${infoTurn.date.slice(0, 10).split('-').reverse().join('-')}</td>
                                                    <td data-label="MODIFICAR"><button type="submit" id="update-turn">Modificar</button></td>
                                                    <td data-label="ELIMINAR"><button type="submit" id="delete-turn">Eliminar</button></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>`
                    
                    read.reset();
                    modifyTurn(infoTurn);
                    removeTurn(infoTurn);
                })
        })
    
    /* -------------------------------------------------------------------------- */
    /*                Método PUT para modificar el odontólogo                     */
    /* -------------------------------------------------------------------------- */
    function modifyTurn(infoTurn) {
        let updateTurn = document.getElementById('update-turn'); 
    
        updateTurn.addEventListener('click', (e) =>{
        overlay.classList.add('active');
        popup.classList.add('active');
    
        list.innerHTML = "";
        register.innerHTML = "";
        clearLog.innerHTML = "";
    
        register.innerHTML += `<form id="update-register">
                                    <fieldset>
                                        <legend>Datos del Turno</legend>
                                            <label for="patient-id">
                                            Paciente ID:
                                                <input type="number" name="patient-id" id="patient-id" placeholder="Ingrese el ID del paciente" />
                                            </label>
                                            <label for="dentist-id">
                                            Odontólogo ID:
                                                <input type="number" name="dentist-id" id="dentist-id" placeholder="Ingrese el ID del odontólogo" />
                                            </label>
                                            <label for="date">
                                            FECHA:
                                                <input type="date" name="date" id="date" placeholder="Ingrese la fecha" />
                                            </label>
                                    </fieldset>
                                    <button type="submit">Guardar</button>
                                    <div class="ok" id="update-ok"></div>
                                    <div class="problem" id="update-problem"></div>
                                </form>`
        
        let updateRegister = document.getElementById('update-register'),
            updateOk = document.getElementById('update-ok'),
            updateProblem = document.getElementById('update-problem');
    
        document.getElementById('patient-id').value = infoTurn.patient.id;
        document.getElementById('dentist-id').value = infoTurn.dentist.id;
        document.getElementById('date').value = infoTurn.date.slice(0, 10).split('-').reverse().join('-');
        
        updateRegister.addEventListener('submit', (e) => {
            e.preventDefault();
    
            let turnData = {
                id: infoTurn.id,
                patient: {
                    id: document.getElementById('patient-id').value,
                }, 
                dentist: {
                    id: document.getElementById('dentist-id').value,
                }, 
                date: document.getElementById('date').value,
            }
    
            let config = {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(turnData)
                
            }
    
            fetch('http://localhost:8080/turnos', config)
                .then(response => {
                    return response.json();
                })
    
                .then(info => {
                    console.log(info);
                    updateOk.innerHTML = `<p>Turno con ID ${info.id}: modificado correctamente!</p>`;
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
    function removeTurn(infoTurn) {
        let deleteTurn = document.getElementById('delete-turn');
    
        deleteTurn.addEventListener('click', (e) => {
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
    
                fetch(`http://localhost:8080/turnos/${infoTurn.id}`, config)
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