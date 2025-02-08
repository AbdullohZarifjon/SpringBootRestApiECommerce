
let tableForProducts = document.getElementById("tableForProducts");
let productsForLocaleStorage = new Map()
let total = document.getElementById("total");

// setupAxiosInterceptors()

getProductsForBasket()

function getProductsForBasket() {
  const productIds = JSON.parse(localStorage.getItem("products")) || []; // JSON'dan parse qilib olish

  axios.get('http://localhost:8080/product/ids', {
    params: { products: productIds },
    paramsSerializer: params => {
      return productIds.map(id => `products=${encodeURIComponent(id)}`).join("&"); // To‘g‘ri formatlash
    }
  }).then(response => {
    let s = ""
    let t = 0;
    for (let product of response.data) {
      if (!productsForLocaleStorage.has(Number (product.id))) {
        productsForLocaleStorage.set(Number (product.id), Number(1))
      }
      t += product.price * productsForLocaleStorage.get(product.id);
      s += `<tr>
<td><img src="http://localhost:8080/file/${product.attachment.id}" alt="rasm joq" width="140" height="130"></td>
<td>${product.name}</td>
<td>${product.price}</td>
<td><button class="btn btn-success" onclick="decrement(event)" value="${product.id}">-</button></td>
<td>${productsForLocaleStorage.get(Number(product.id))}</td>
<td><button class="btn btn-success" onclick="increment(event)" value="${product.id}">+</button></td>
<td>${product.price * productsForLocaleStorage.get(Number(product.id))}</td>
<td><button class="btn btn-danger" onclick="deleteProduct(event)" value="${product.id}">delete</button></td>
</tr>`
    }
    tableForProducts.innerHTML = s;
    total.innerHTML = `<h4 id="total">${t} sum</h4>
      <button type="button" class="btn btn-success" onclick="createOrder()">Order</button>`;
  }).catch(error => {
    console.error("Xatolik:", error);
  });
}

function increment(event) {
  productsForLocaleStorage.set(Number (event.target.value), productsForLocaleStorage.get(Number(event.target.value)) + 1);
  getProductsForBasket()
}

function decrement(event) {
  if (productsForLocaleStorage.get(Number(event.target.value)) > 0 ) {
    productsForLocaleStorage.set(Number (event.target.value), productsForLocaleStorage.get(Number(event.target.value)) - 1);
    getProductsForBasket()
  } else {
    deleteProduct(event)
  }
}

function deleteProduct(event) {
  productsForLocaleStorage.delete(Number(event.target.value))

  let productIds = JSON.parse(localStorage.getItem("products")) || [];

  productIds = productIds.filter(id => Number(id) !== Number(event.target.value));

  localStorage.setItem("products", JSON.stringify(productIds));

  getProductsForBasket();
}

function createOrder() {
  if (localStorage.getItem("token")) {
    let productsForOrder = Object.fromEntries(productsForLocaleStorage);

    axios({
      method: "POST",
      url: "http://localhost:8080/order",
      headers: {
        "Content-Type": "application/json",
        "token": localStorage.getItem("token") // Token faqat headers orqali
      },
      data: JSON.stringify({ products: productsForOrder }) // Faqat products JSON formatda
    })
      .then(response => {
        localStorage.setItem("products", JSON.stringify([])); // Savatni tozalash
        window.location.href = "order.html";
      })
      .catch(error => console.error("Buyurtma yaratishda xatolik:", error));
  } else {
    productsForLocaleStorage.clear();
    window.location.href = "login.html";
  }
}




