
let products = document.getElementById('products')
let categories = document.getElementById('categories');
let addCategoryForAdmin = document.getElementById('addCategoryForAdmin');
let addProductForAdmin = document.getElementById('addProductForAdmin');
let loginPanel = document.getElementById('loginPanel');
let basketPanel = document.getElementById('basketPanel');
let basket = document.getElementById('basket');
let myOrders = document.getElementById('myOrders');

// setupAxiosInterceptors()

buttonsForNavbarByRoleUser()

function buttonsForNavbarByRoleUser() {
  if (localStorage.getItem("token")) {
    const userRoles = getUserRolesFromToken();
    if (userRoles.includes("ROLE_ADMIN")) {
      addProductForAdmin.style.display = "block";
      addCategoryForAdmin.style.display = "block";
      loginPanel.style.display = "block";
      basketPanel.style.display = "none";
      myOrders.style.display = "none";
    } else {
      basketPanel.style.display = "block";
      addProductForAdmin.style.display = "none";
      addCategoryForAdmin.style.display = "none";
      loginPanel.style.display = "none";
      myOrders.style.display = "block";
    }
  } else {
    loginPanel.style.display = "block";
    basketPanel.style.display = "block";
    addProductForAdmin.style.display = "none";
    addCategoryForAdmin.style.display = "none";
    myOrders.style.display = "none";
  }
}

let letEvent = "0";
getCategories()

function getCategories(event) {
  axios({
    method: "get",
    url: "http://localhost:8080/category",
  })
    .then((response) => {
      let categoryId = letEvent !== "0" ? letEvent : "0"; // Default category (All)
      if (event && event.target) {
        categoryId = event.target.value; // Get selected category ID
        letEvent = categoryId;
      }

      const userRoles = getUserRolesFromToken();
      const isAdmin = userRoles.includes("ROLE_ADMIN");

      let s = `<button onclick="getCategories(event)" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center ${categoryId === "0" ? 'bg-dark text-white' : ''}" value="0">
                All
              </button>`;

      for (let category of response.data) {
        s += `<div class="d-flex justify-content-between align-items-center list-group-item ${categoryId == category.id ? 'bg-dark text-white' : ''}">
                <button onclick="getCategories(event)" class="btn btn-link text-decoration-none text-reset flex-grow-1 text-start" value="${category.id}">
                  ${category.name}
                </button>
                ${
          isAdmin
            ? `<button class="btn btn-sm btn-warning" value="${category.id}" onclick="openUpdateCategory(event)">Edit</button>`
            : ""
        }
              </div>`;
      }

      categories.innerHTML = s;
      getAllProducts();
    })
    .catch((error) => {
      console.error("Kategoriya yuklashda xatolik", error);
    });
}



function getAllProducts() {

  getCountProductToBasket()
  axios({
    method: 'get',
    url: `http://localhost:8080/product/all/${letEvent}`
  }).then(response => {
    let s = "";
    let storedProducts = localStorage.getItem("products") ? JSON.parse(localStorage.getItem("products")) : [];

    const userRoles = getUserRolesFromToken();
    const isAdmin = userRoles.includes("ROLE_ADMIN");

    for (let product of response.data) {
      let isAdded = storedProducts.includes(String(product.id)); // ID localStorage'da bor-yo‘qligini tekshiramiz

      s += `<div class="col">
              <div class="card h-100">
                <img src="http://localhost:8080/file/${product.attachment.id}" class="card-img-top" alt="Product Image" width="125" height="150"/>
                <div class="card-body">
                  <h5 class="card-title">Product: ${product.name}</h5>
                  <p class="card-text">Price: ${product.price}</p>
                  ${
        !isAdmin
          ?
        isAdded
          ? `<button class="btn btn-secondary w-100 mb-1" disabled>Qo'shildi</button>`
          : `<button class="btn btn-primary w-100 mb-1" value="${product.id}" onclick="addProductToBasket(event)">Add to Basket</button>`:``
      } ${
        isAdmin
        ? `<button class="btn btn-warning w-100" id="editProductButton" onclick="openUpdateProductModal(event)"
                value="${product.id}">Edit</button>`:``
      }
                </div>
              </div>
            </div>`;
    }
    document.getElementById("products").innerHTML = s;
  });
}


function addProductToBasket(event) {
  event.preventDefault();

  // Oldin localStorage'dan "products" ni olib, array qilib ochamiz
  let products = localStorage.getItem("products")
    ? JSON.parse(localStorage.getItem("products"))
    : [];

  // Yangi productId ni arrayga qo‘shamiz (agar oldin qo‘shilmagan bo‘lsa)
  if (!products.includes(event.target.value)) {
    products.push(event.target.value);
  }

  // Yangilangan arrayni localStorage ga saqlaymiz
  localStorage.setItem("products", JSON.stringify(products));
  for (let product of products) {
    console.log(product);
  }
  console.log("Basket:", products); // Konsolda tekshirish uchun
  getAllProducts();

}

function getUserRolesFromToken() {
  const token = localStorage.getItem("token");
  if (!token) return [];

  try {
    const payloadBase64 = token.split('.')[1]; // Payloadni ajratamiz
    const payloadDecoded = atob(payloadBase64); // Base64 decode
    const payloadObject = JSON.parse(payloadDecoded); // JSON obyektga o'tkazamiz

    return payloadObject.roles || []; // Agar roles mavjud bo'lmasa, bo'sh array qaytaramiz
  } catch (error) {
    console.error("Tokenni dekod qilishda xatolik:", error);
    return [];
  }
}

function getCountProductToBasket() {
  let products = localStorage.getItem("products") ? JSON.parse(localStorage.getItem("products"))
    : []
  basket.innerText = `Basket (${products.length})`;
  if (products.length > 0) {
    basket.style.color = 'white';
  }
}
