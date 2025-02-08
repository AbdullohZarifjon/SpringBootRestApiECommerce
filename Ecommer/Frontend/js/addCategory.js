let addCategoryModal = document.getElementById("addCategoryModal");
let updateCategoryModal = document.getElementById("updateCategoryModal");


async function openUpdateCategoryModal(event) {
  let s
  const categoryRes = await axios.get(`http://localhost:8080/category/id/${event.target.value}`);
  const category = categoryRes.data;
  s = `
    <div class="card">
      <div class="card-header bg-dark text-white text-center">
        Update category
      </div>
      <div class="card-body">
        <form onsubmit="saveCategory(event)">
          <div class="form-group mb-2">
            <input type="hidden" name="id" value="${category.id}">
            <input type="text" class="form-control" name="name" value="${category.name}" placeholder="Category name" required>
          </div>
          <div class="text-end">
            <button class="btn btn-primary" type="submit">Submit</button>
            <button class="btn btn-danger" type="button" value="${category.id}" onclick="deleteCategory(event)">delete</button>
            <button class="btn btn-warning" type="button" onclick="closeCategoryModal()">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  `
  updateCategoryModal.innerHTML = s;
}
function saveCategory(event) {
  event.preventDefault();
  let name = event.target.elements['name'].value;
  let id = event.target.elements['id'].value;
  axios({
    method: 'post',
    url: `http://localhost:8080/category`,
    headers: {
      'Content-Type': 'application/json'
    },
    data: { id: id, name: name } // JSON formatda yuborish
  }).then(res => {
    let a = document.createElement('a');
    a.href = "main.html";
    a.click();
  })
}

function deleteCategory(event) {
  axios({
    method: 'delete',
    url: `http://localhost:8080/category/${event.target.value}`,
  }).then(res => {
    let a = document.createElement('a');
    a.href = "main.html";
    a.click();
  })
}

function openUpdateCategory(event) {
  updateCategoryModal.style.display = "block";
  document.getElementById("modalBackdrop").style.display = "block";
  openUpdateCategoryModal(event);
}


function openAddCategoryModal() {
  addCategoryModal.style.display = "block";
  document.getElementById("modalBackdrop").style.display = "block";
}

function closeCategoryModal() {
  addCategoryModal.style.display = "none";
  document.getElementById("modalBackdrop").style.display = "none";
}


// tokenManager.js

export function setupAxiosInterceptors() {
  axios.interceptors.request.use(async (config) => {
    let token = localStorage.getItem("token");

    if (token) {
      let tokenExpiringSoon = isTokenExpiringSoon(token);
      if (tokenExpiringSoon) {
        try {
          let response = await axios.post("http://localhost:8080/refresh-token", {}, {
            headers: { "token": token }
          });
          localStorage.setItem("token", response.data.token);
          token = response.data.token;
        } catch (error) {
          console.error("Token yangilashda xatolik:", error);
          localStorage.removeItem("token");
          window.location.href = "/login.html"; // Login sahifasiga qaytarish
        }
      }
      config.headers["token"] = token;
    }
    return config;
  }, (error) => {
    return Promise.reject(error);
  });
}

function isTokenExpiringSoon(token) {
  const payload = JSON.parse(atob(token.split('.')[1])); // JWT tokenni dekod qilish
  const expTime = payload.exp * 1000; // Sekunddan millisekundga o'tkazish
  return expTime - Date.now() < 60000; // 1 daqiqa ichida muddati tugasa true qaytaradi
}
