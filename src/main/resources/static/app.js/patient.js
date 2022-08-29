window.addEventListener('load', () => {

/* -------------------------------------------------------------------------- */
/*   Momento inicial atrapamos todos los id del HTML que vamos a utilizar     */
/* -------------------------------------------------------------------------- */
    let btnOpenPopup = document.getElementById('open-popup-patient'),
        btnClosePopup = document.getElementById('close-popup'),
        overlay = document.getElementById('overlay'),
        popup = document.getElementById('popup'),

        register = document.getElementById('register'),
        clearLog = document.getElementById('clear-log'),
        listPatients = document.getElementById('list-patients'),
        list = document.getElementById('list'),
        read = document.getElementById('read-form-patient'),
        readPatient = document.getElementById('read-patient');

/* -------------------------------------------------------------------------- */
/*                      Método que abre el poppup                             */
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

        register.innerHTML += `<form id="registerPatient">
                                        <fieldset>
                                            <legend>Datos del Paciente</legend>
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
                                            <label for="date-admission">
                                            Fecha de Ingreso:
                                                <input type="date" name="date-admission" id="date-admission" />
                                            </label>
                                        </fieldset>
                                        <fieldset>
                                            <legend>Datos del Domicilio</legend>
                                            <label for="street">
                                            Calle:
                                                <input type="text" name="street" id="street" placeholder="Calle"/>
                                            </label>
                                            <label for="number">
                                            Número:
                                                <input type="text" name="number" id="number" placeholder="Número" />
                                            </label>
                                            <label for="city">
                                            Localidad:
                                                <input type="text" name="city" id="city" placeholder="Ciudad" />
                                            </label>
                                            <label for="province">
                                            Provincia:
                                                <input type="text" name="province" id="province" placeholder="Provincia"/>
                                            </label>
                                        </fieldset>
                                            <button type="submit">Guardar</button>
                                            <div class="ok" id="ok"></div>
                                            <div class="problem" id="problem"></div>
                                    </form>`
        
        recordDate();        
    }

/* -------------------------------------------------------------------------- */
/*                Método POST para la carga de pacientes                      */
/* -------------------------------------------------------------------------- */
    function recordDate() {
        let registerPatient = document.getElementById('registerPatient'),
            name = document.getElementById('name'),
            surname = document.getElementById('surname'),
            dni = document.getElementById('dni'),
            dateOfAdmission = document.getElementById('date-admission'),
            street = document.getElementById('street'),
            number = document.getElementById('number'),
            city = document.getElementById('city'),
            province = document.getElementById('province'),
            ok = document.getElementById('ok');
            problem = document.getElementById('problem');

        registerPatient.addEventListener('submit', (e) =>{
            e.preventDefault();

            ok.innerHTML = "";
            problem.innerHTML = "";
            clearLog.innerHTML = "";

            let patientData = {
                name: name.value,
                surname: surname.value,
                dni: dni.value,
                dateOfAdmission: dateOfAdmission.value,
                address: {
                    street: street.value,
                    number: number.value,
                    city: city.value,
                    province: province.value,
                }
            }

            let config = {
                method: 'POST',
                body: JSON.stringify(patientData),
                headers: {
                        'Content-Type': 'application/json',
                }
            }

            fetch('http://localhost:8080/pacientes', config)
                    .then(response => {
                        return response.json();
                    })

                    .then(info => {
                        console.log(info);
                        ok.innerHTML = `<p>Paciente: ${info.name.toUpperCase()} ${info.surname.toUpperCase()} cargado correctamente!</p>`;
                    })

                    .catch(err => {
                                problem.innerHTML = `<p>Error: Intente nuevamente</p>`;
                                console.log(err);
                    })

            registerPatient.reset();

        })
    }
    
/* -------------------------------------------------------------------------- */
/*                Método GET para traer la lista de pacientes                 */
/* -------------------------------------------------------------------------- */
    listPatients.addEventListener('click', (e) => {
        e.preventDefault();

        fetch('http://localhost:8080/pacientes')
            .then(response => {
                return response.json();
            })

            .then(info => {
                console.log(info);

                renderListPatients(info);
            })
    })

/* -------------------------------------------------------------------------- */
/*            Método para renderizar la tabla de pacientes                    */
/* -------------------------------------------------------------------------- */
    function renderListPatients(info) {
        list.innerHTML = `<div class="render-list" id="render-list">
                            <table id="render-table">
                                <caption>PACIENTES</caption>
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>NOMBRE</th>
                                        <th>APELLIDO</th>
                                        <th>DNI</th>
                                        <th>CALLE | NUMERO</th>
                                        <th>CIUDAD | PROVINCIA</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>`

        renderTable = document.getElementById('render-table')

        info.map(infoPatient => {
            renderTable.innerHTML += `<tbody>
                                        <tr>
                                            <td data-label="ID">${infoPatient.id}</td>
                                            <td data-label="NOMBRE">${infoPatient.name.toUpperCase()}</td>
                                            <td data-label="APELLIDO">${infoPatient.surname.toUpperCase()}</td>
                                            <td data-label="DNI">${infoPatient.dni}</td>
                                            <td data-label="CALLE | NÚMERO">${infoPatient.address.street.toUpperCase()}  N°: ${infoPatient.address.number}</td>
                                            <td data-label="CIUDAD | PROVINCIA">${infoPatient.address.city.toUpperCase()}-${infoPatient.address.province.toUpperCase()}</td>
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

        fetch(`http://localhost:8080/pacientes/${readPatient.value}`)
            .then(response => {
                return response.json();
            })
            .then(infoPatient => {
                console.log(infoPatient)

                list.innerHTML = `<div class="render-list" id="render-list">
                                    <table id="render-table">
                                        <caption>PACIENTE CON ID: ${infoPatient.id}</caption>
                                        <thead>
                                            <tr>
                                                <th>NOMBRE</th>
                                                <th>APELLIDO</th>
                                                <th>DNI</th>
                                                <th>MODIFICAR</th>
                                                <th>ELIMINAR</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td data-label="NOMBRE">${infoPatient.name.toUpperCase()}</td>
                                                <td data-label="APELLIDO">${infoPatient.surname.toUpperCase()}</td>
                                                <td data-label="DNI">${infoPatient.dni}</td>
                                                <td data-label="MODIFICAR"><button type="submit" id="update-patient">Modificar</button></td>
                                                <td data-label="ELIMINAR"><button type="submit" id="delete-patient">Eliminar</button></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>`
                
                read.reset();
                modifypatient(infoPatient);
                removePatient(infoPatient);
            })
    })

/* -------------------------------------------------------------------------- */
/*                  Método PUT para modificar el paciente                     */
/* -------------------------------------------------------------------------- */
    function modifypatient(infoPatient) {
        let updatePatient = document.getElementById('update-patient'); 

        updatePatient.addEventListener('click', (e) =>{
        overlay.classList.add('active');
        popup.classList.add('active');

        list.innerHTML = "";
        register.innerHTML = "";
        clearLog.innerHTML = "";

        register.innerHTML += `<form id="update-register">
                                    <fieldset>
                                        <legend>Datos del Paciente</legend>
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
                                            <label for="date-admission">
                                            Fecha de Ingreso:
                                                <input type="date" name="date-admission" id="date-admission" />
                                            </label>
                                    </fieldset>
                                    <fieldset>
                                        <legend>Datos del Domicilio</legend>
                                            <label for="street">
                                            Calle:
                                                <input type="text" name="street" id="street" placeholder="Calle"/>
                                            </label>
                                            <label for="number">
                                            Número:
                                                <input type="text" name="number" id="number" placeholder="Número" />
                                            </label>
                                            <label for="city">
                                            Localidad:
                                                <input type="text" name="city" id="city" placeholder="Ciudad" />
                                            </label>
                                            <label for="province">
                                            Provincia:
                                                <input type="text" name="province" id="province" placeholder="Provincia"/>
                                            </label>
                                    </fieldset>
                                    <button type="submit">Guardar</button>
                                    <div class="ok" id="update-ok"></div>
                                    <div class="problem" id="update-problem"></div>
                                </form>`
        
        let updateRegister = document.getElementById('update-register'),
            updateOk = document.getElementById('update-ok'),
            updateProblem = document.getElementById('update-problem');

        document.getElementById('name').value = infoPatient.name;
        document.getElementById('surname').value = infoPatient.surname;
        document.getElementById('dni').value = infoPatient.dni;
        document.getElementById('date-admission').value = infoPatient.dateOfAdmission;
        document.getElementById('street').value = infoPatient.address.street;
        document.getElementById('number').value = infoPatient.address.number;
        document.getElementById('city').value = infoPatient.address.city;
        document.getElementById('province').value = infoPatient.address.province;
        
        updateRegister.addEventListener('submit', (e) => {
            e.preventDefault();

            let patientData = {
                id: infoPatient.id,
                name: document.getElementById('name').value,
                surname: document.getElementById('surname').value,
                dni: document.getElementById('dni').value,
                dateOfAdmission: document.getElementById('date-admission').value,
                address: {
                    street: document.getElementById('street').value,
                    number: document.getElementById('number').value,
                    city: document.getElementById('city').value,
                    province: document.getElementById('province').value,
                }
            }

            let config = {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(patientData)
                
            }

            fetch('http://localhost:8080/pacientes', config)
                .then(response => {
                    return response.json();
                })

                .then(info => {
                    console.log(info);
                    updateOk.innerHTML = `<p>Paciente con ID ${info.id}: modificado correctamente!</p>`;
                })

                .catch(err => {
                            updateProblem.innerHTML = `<p>Error: Intente nuevamente</p>`;
                            console.log(err);
                })
        })

    })
    }

/* -------------------------------------------------------------------------- */
/*                Método DELETE para eliminar el paciente                     */
/* -------------------------------------------------------------------------- */
    function removePatient(infoPatient) {
    let deletePatient = document.getElementById('delete-patient');

    deletePatient.addEventListener('click', (e) => {
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

            fetch(`http://localhost:8080/pacientes/${infoPatient.id}`, config)
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