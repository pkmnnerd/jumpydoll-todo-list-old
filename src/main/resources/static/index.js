document.addEventListener('DOMContentLoaded', function() {
  document.getElementById('addItemForm').addEventListener('submit', (e) => {
    e.preventDefault()
    const description = document.getElementById('description').value;
    const postBody = {
      description,
      finished: false,
    }
    document.getElementById("addButton").disabled = true;
    fetch('api/tasks', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(postBody),
    }).then(res => res.json())
      .then(body => {
        document.getElementById('description').value = '';
        document.getElementById("addButton").disabled = false;
        refreshList();
      });
  })
  refreshList();
})

function refreshList() {
  document.getElementById('loading').style.display = 'block';
  document.getElementById('itemList').style.display = 'none';
  document.getElementById('addItemForm').style.display = 'none';
  fetch('api/tasks/')
    .then(res => res.json())
    .then(body => {
      console.log(body);
      const itemList = document.getElementById('itemList');
      while (itemList.firstChild) {
        itemList.removeChild(itemList.firstChild)
      }
      body.forEach((item) => createItem(itemList, item));
      document.getElementById('loading').style.display = 'none';
      document.getElementById('itemList').style.display = 'block';
      document.getElementById('addItemForm').style.display = 'block';
    })
}

function createItem(itemList, todoItem) {
  const element = document.createElement("li")

  const deleteButton = document.createElement("button");
  deleteButton.setAttribute('class', 'btn btn-light ms-auto');
  const deleteButtonIcon = document.createElement("i");
  deleteButtonIcon.setAttribute('class', 'bi bi-trash-fill');
  deleteButton.appendChild(deleteButtonIcon);
  deleteButton.addEventListener('click', createDeleteHandler(todoItem.id));

  const checkBox = document.createElement("input");
  checkBox.setAttribute('type', 'checkbox');
  checkBox.setAttribute('class', 'form-check-input me-1');
  checkBox.checked = todoItem.finished;
  checkBox.addEventListener('change', createCompletedHandler(todoItem));

  element.id = todoItem.id;

  const flex = document.createElement('div');
  flex.setAttribute('class', 'd-flex')
  flex.appendChild(checkBox);
  if (todoItem.finished) {
    const text = document.createTextNode(todoItem.description);
    const strike = document.createElement("s");
    strike.appendChild(text);
    flex.appendChild(strike);
  } else {
    const text = document.createTextNode(todoItem.description);
    flex.appendChild(text);
  }
  flex.appendChild(deleteButton);
  element.append(flex);

  if (todoItem.finished) {
    element.setAttribute('class', 'list-group-item list-group-item-secondary');
  } else {
    element.setAttribute('class', 'list-group-item');
  }

  itemList.appendChild(element);
}

function createCompletedHandler(todoItem) {
  return function (e) {
    console.log(e);
    fetch(`api/tasks/${todoItem.id}`, {
      method: 'PUT',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({ ...todoItem, finished: !todoItem.finished }),
    }).then(res => {
      refreshList();
    });
  }
}

function createDeleteHandler(id) {
  return function (e) {
    fetch(`api/tasks/${id}`, {method: 'DELETE'})
      .then(res => {
        refreshList();
      });
  }
}
