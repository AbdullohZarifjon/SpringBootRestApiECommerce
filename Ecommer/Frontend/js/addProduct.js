
let addProductModal = document.getElementById("addProductModal");
let updateProductModal = document.getElementById("updateProductModal");

let photoId = 0;
let select = document.getElementById("select");

// setupAxiosInterceptors()
getCategoriesForSelect()

async function openProduct(event) {
  let s;
  const productRes = await axios.get(`http://localhost:8080/product/id/${event.target.value}`);
  const product = productRes.data;
  photoId = product.attachment.id;

  // Kategoriyalarni olish
  const categoryRes = await axios.get("http://localhost:8080/category");
  const categories = categoryRes.data;

  s = `
    <div class="card">
      <div class="card-header bg-dark text-white text-center">
        Update product
      </div>
      <div class="card-body">
        <div class="text-center">
          <label class="mb-2">
            <img src="http://localhost:8080/file/${product.attachment.id}" alt="rasm joq" class="img-fluid" width="150" height="125" id="img">
            <input type="file" id="file" name="file" style="display: none;cursor: pointer;" onchange="uploadFile(event)">
          </label>
        </div>
        <form onsubmit="saveProduct(event)">
          <div class="form-group mb-2">
            <input type="hidden" name="productId" id="productIdInput" value="${product.id}">
            <input type="text" class="form-control" name="name" placeholder="Product name" value="${product.name}" required>
          </div>
          <div class="form-group mb-2">
            <input type="number" class="form-control" name="price" placeholder="Price" value="${product.price}" required>
          </div>
          <div class="form-group mb-2">
            <select name="categoryId" class="form-control" required>
              ${categories.map(category =>
    `<option value="${category.id}" ${category.id === product.category.id ? 'selected' : ''}>${category.name}</option>`
  ).join('')}
            </select>
          </div>
          <div class="text-end">
            <button class="btn btn-primary" type="submit">Submit</button>
            <button class="btn btn-warning" type="button" onclick="closeUpdateProductModal()">Cancel</button>
            <button class="btn btn-danger" type="button" name="productId" value="${product.id}" onclick="deleteProduct(event)">delete</button>
          </div>
        </form>
      </div>
    </div>`;

  updateProductModal.innerHTML = "";
  updateProductModal.insertAdjacentHTML("beforeend", s);

  // ✅ Yangi qo‘shilgan inputni yangilash
  document.getElementById("productIdInput").value = product.id;
  console.log("Product ID after setting in input:", document.getElementById("productIdInput").value);

}



function uploadFile(event) {
  let file = event.target.files[0];
  let formData = new FormData();
  let img = document.getElementById("img");
  formData.append('file', file);
  axios.post("http://localhost:8080/file", formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(response => {
    photoId = response.data;
    img.src = `http://localhost:8080/file/${photoId}`;
  })
}

function saveProduct(event) {
  event.preventDefault();
  let form = event.target;
  let obj = {
    productId: Number(form.elements["productIdInput"].value),
    name: form.elements["name"].value, // Input nomini olish
    price: form.elements["price"].value, // Narxni olish
    categoryId: form.elements["categoryId"].value, // Kategoriyani olish
    attachmentId: photoId // Rasmingizning ID-si (buni siz to'g'ri o'tkazayotgan bo'lsangiz)
  }
  axios({
    url: 'http://localhost:8080/product',
    method: 'POST',
    data: obj
  }).then(response => {
    let a = document.createElement('a');
    a.href = "main.html";
    a.click();
  })
}

function getCategoriesForSelect() {
  axios({
    url: "http://localhost:8080/category",
    method: "get"
  }).then(response => {
    let s = "<option selected disabled>Select category</option>";
    for (let category of response.data) {
      s += `<option value="${category.id}">${category.name}</option>`
    }
    select.innerHTML = s;
  })
}

function deleteProduct(event) {
  let productId = event.target.value; // Tugma (button) ichidagi qiymatni olamiz

  axios.delete(`http://localhost:8080/product/${productId}`)
    .then(response => {
      console.log("Mahsulot o‘chirildi:", response.data);
      let a = document.createElement('a');
      a.href = "main.html";
      a.click();
    })
    .catch(error => {
      console.error("O‘chirishda xatolik:", error);
    });
}


function openUpdateProductModal(event) {
  updateProductModal.style.display = "block";
  document.getElementById("modalBackdrop").style.display = "block";
  openProduct(event)
}

function closeUpdateProductModal() {
  updateProductModal.style.display = "none";
  document.getElementById("modalBackdrop").style.display = "none";
}

function openAddProductModal() {
  addProductModal.style.display = "block";
  document.getElementById("modalBackdrop").style.display = "block";
}

function closeProductModal() {
  addProductModal.style.display = "none";
  document.getElementById("modalBackdrop").style.display = "none";
}

