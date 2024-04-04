let URL = "http://localhost:8085/api/users";
const roleUrl = 'http://localhost:8085/api/roles';


// получаем роли с сервера
const selectRoleForm = document.getElementById('roles');

fetch(roleUrl)
    .then(res => res.json())
    .then(data => {

        let options = '';
        for (const [k, v] of Object.entries(data)) {
            options += `<option value="${Number(k) + 1}">${v.nameRole}</option>`;
        }
        selectRoleForm.innerHTML = options;

    })


// получаем пользователей с сервера
let userTable = document.querySelector(".body__list");
let outputUser = "";
let roleLet;

const renderTable = (user) => {
    user.forEach(user => {
        roleLet = "";
        user.roleList.forEach((role) => roleLet += role.nameRole.substring(5) + " || ");
        outputUser += `
              <tr >
                <th><p>${user.id} </p></th>
                <th><p>${user.firstName} </p></th>
                <th><p>${user.lastName} </p></th>
                <th><p>${user.email} </p></th>
                <th><p>${user.username} </p></th>
                <th><p>${roleLet.slice(0, roleLet.length - 3)}</p></th>
                 
                <th>
                    <button data-id="${user.id}" type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#editModal" id="editbtn">Edit</button>
                </th>
                <th>
                    <button data-id="${user.id}" type="button" class="btn btn-danger " data-toggle="modal"
                            data-target="#deleteModal" id="delbtn">Delete</button>
                    </th>
                </tr>
            `;
    });
    userTable.innerHTML = outputUser;

}

fetch(URL)
    .then(res => res.json())
    .then(data => renderTable(data))


// добавляем пользователя
let firstNameField = document.querySelector(".firstName__input");
let lastNameField = document.querySelector(".lastName__input");
let emailField = document.querySelector(".email__input");
let passwordField = document.querySelector(".password__input");
let userFormNew = document.querySelector("#user_form_new")
let roleById = document.getElementById('roles');

userFormNew.addEventListener("submit", async (e) => {
    const roles = [];
    for (let i = 0; i < roleById.options.length; i++) {
        if (roleById.options[i].selected) {
            roles.push({
                id: roleById.options[i].value,
                nameRole: roleById.options[i].text
            });
        }
    }
    console.log('push=>');
    console.log(roles);
    const user = {
        firstName: firstNameField.value,
        lastName: lastNameField.value,
        email: emailField.value,
        password: passwordField.value,
        role: roles
    }

    try {
        await fetch(URL, {
            method: 'post',
            headers: {
                'Content-type': 'application/json',
            },
            body: JSON.stringify(user),
        })
            .then(res => res.json())
            .then(data => {
                const dataArr = []
                dataArr.push(data)
                renderTable(dataArr)
                userFormNew.reset()
                $('[href="#users_table"]').tab('show');
            })


    } catch (error) {
        console.log(error)
    }
    console.log('obj', user)
});

// заполнение форм delete и edit
userTable.addEventListener('click', async (e) => {
    e.preventDefault()
    if (e.target.id === 'delbtn') {
        await fetch(`${URL}/${e.target.dataset.id}`)
            .then(res => res.json())
            .then(data => {
                    let roles = '';
                    data.roleList.forEach(role => roles += role.nameRole + "  ")
                    document.querySelector("#idDel").value = data.id
                    document.querySelector("#firstNameDel").value = data.firstName
                    document.querySelector("#lastNameDel").value = data.lastName
                    document.querySelector("#emailDel").value = data.email
                    document.querySelector("#roles_delete").value = roles.substring(5)
                    console.log(roles)
                }
            )
            .catch(err => console.error(err));
    } else if (e.target.id === 'editbtn') {
        await fetch(`${URL}/${e.target.dataset.id}`)
            .then(res => res.json())
            .then(async data => {
                document.querySelector("#idEdit").value = data.id
                document.querySelector("#firstNameEdit").value = data.firstName
                document.querySelector("#lastNameEdit").value = data.lastName
                document.querySelector("#emailEdit").value = data.email
                await fetch(roleUrl)
                    .then(res => res.json())
                    .then(rolesData => {
                        let options = '';
                        for (const [id, name] of Object.entries(rolesData)) {
                            const selected = data.roleList.some(role => role.id === id) ? 'selected' : '';
                            options += `<option value="${Number(id) + 1}" ${selected}>${name.nameRole.substring(5)}</option>`;
                        }

                        $('#roles_edit').html(options);
                        $('#editModal').modal()

                    })
                    .catch(err => console.error(err));
            });


    }
})


// удаление пользователя
let modalFormDelete = document.querySelector('#modal__form__delete');

modalFormDelete.addEventListener('submit',  async (e) => {

    let userId = document.querySelector("#idDel").value;

    await fetch(`${URL}/${userId}`, {
        method: 'DELETE'
    })
        .then(res => console.log(res))
        .then(() => {
            outputUser = ''
        })
})

// изменение пользователя
let modalFormEdit = document.querySelector('#editModalForm');
let roleEdit = document.querySelector('#roles_edit')

modalFormEdit.addEventListener('submit', async (e) => {
    e.preventDefault()
    const rol = [];
    for (let i = 0; i < roleEdit.options.length; i++) {
        if (roleEdit.options[i].selected) {
            rol.push({
                id: roleEdit.options[i].value,
                nameRole: roleEdit.options[i].text
            });
        }
    }

    const user = {
        id: document.querySelector("#idEdit").value,
        firstName: document.querySelector("#firstNameEdit").value,
        lastName: document.querySelector("#lastNameEdit").value,
        email: document.querySelector("#emailEdit").value,
        password: passwordField.value,
        roleList: rol
    }

    await fetch(`${URL}`, {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json',
        },
        body: JSON.stringify(user),
    })
        .then(res => console.log(res))
        .then(async () => {
            $('#editModal').modal('hide')
            outputUser = ''
            await fetch(URL)
                .then(res => res.json())
                .then(data => renderTable(data))
        })
})