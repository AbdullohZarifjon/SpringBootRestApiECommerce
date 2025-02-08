// Fetch orders from the backend

// setupAxiosInterceptors()

async function fetchOrders() {
  axios({
    method: 'get',
    url: "http://localhost:8080/order",
    headers: {
      "Content-Type": "application/json",
      "token": localStorage.getItem("token")
    }
  }).then(response => {
    populateOrdersTable(response.data);
  })
}

// Populate orders table
function populateOrdersTable(orders) {
  const ordersTableBody = document.getElementById('orders-table-body');
  ordersTableBody.innerHTML = ''; // Clear existing rows

  orders.forEach(order => {
    const row = document.createElement('tr');
    row.innerHTML = `
          <td>${order.id}</td>
          <td>${order.date}</td>
          <td>${order.total.toFixed(2)} sum</td>
          <td>${order.status}</td>
          <td>
            <button onclick="viewOrderItems(${order.id})" class="btn btn-primary btn-sm">View Items</button>
          </td>
        `;
    ordersTableBody.appendChild(row);
  });
}

// Fetch and display order items in a modal
async function viewOrderItems(orderId) {
  axios({
    method: 'get',
    url: `http://localhost:8080/orderItem/${orderId}`,
    headers: {
      "Content-Type": "application/json",
      "token": localStorage.getItem("token")
    }
  }).then(response => {
    console.log(response.data[0]);
    populateOrderItemsModal(response.data);
  })
}

// Populate order items modal
function populateOrderItemsModal(orderItems) {
  openModal()
  const orderItemsBody = document.getElementById('order-items-body');
  orderItemsBody.innerHTML = ''; // Clear existing rows

  orderItems.forEach(item => {
    const row = document.createElement('tr');
    row.innerHTML = `
          <td>${item.product.name}</td>
          <td>${item.quantity}</td>
          <td>${item.product.price.toFixed(2)} sum</td>
          <td>${(item.price).toFixed(2)} sum</td>
        `;
    orderItemsBody.appendChild(row);
  });
}

// Open modal
function openModal() {
  const modal = document.getElementById('order-item-modal');
  modal.style.display = 'block';
}

// Close modal
function closeModal() {
  const modal = document.getElementById('order-item-modal');
  modal.style.display = 'none';
}

// Fetch orders when the page loads
document.addEventListener('DOMContentLoaded', fetchOrders);
