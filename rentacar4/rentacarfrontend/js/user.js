const jwtToken = localStorage.getItem('jwtToken');
const BASE_PATH = "http://localhost:8080/api/";

// Arabaları listele
async function getAllCars() {
    showLoadingSpinner();
    try {
        const response = await fetch(BASE_PATH + "cars", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + jwtToken
            }
        });

        if (!response.ok) {
            throw new Error("Failed to fetch cars, status: " + response.status);
        }

        const cars = await response.json();
        renderCarList(cars);
    } catch (error) {
        console.error("Error fetching cars:", error);
        showErrorAlert("Error loading cars!");
    } finally {
        hideLoadingSpinner();
    }
}

// Arabaları kullanıcı dashboardında göster
function renderCarList(cars) {
    const carContainer = document.querySelector(".car-container");
    carContainer.innerHTML = "";

    cars.forEach(car => {
        const carCard = document.createElement("div");
        carCard.classList.add("car-card");

        carCard.innerHTML = `
            <img src="${car.imageUrl || 'https://via.placeholder.com/300'}" alt="${car.brand} ${car.model}">
            <h5>${car.brand} ${car.model}</h5>
            <p>Color: ${car.color}</p>
            <p>Daily Price: $${car.dailyPrice}</p>
            <p>Available: ${car.availableCount}</p>
            <button class="btn btn-primary" onclick="selectCar(${car.id}, ${car.dailyPrice})">Select</button>
        `;

        carContainer.appendChild(carCard);
    });
}

// Seçilen arabayı formda işleme al
function selectCar(carId, dailyPrice) {
    document.getElementById('selectedCarId').value = carId;
    document.getElementById('dailyPrice').value = dailyPrice;
    showSuccessAlert("Car selected! Fill the form to book.");
}

// Toplam fiyat hesapla
function calculateTotalPrice() {
    const dailyPrice = parseFloat(document.getElementById('dailyPrice').value || "50");
    const pickupDate = new Date(document.getElementById('pickupDate').value);
    const dropoffDate = new Date(document.getElementById('dropoffDate').value);

    if (pickupDate && dropoffDate && dropoffDate > pickupDate) {
        const days = Math.ceil((dropoffDate - pickupDate) / (1000 * 60 * 60 * 24));
        const totalPrice = days * dailyPrice;
        document.getElementById('totalPrice').value = `$${totalPrice.toFixed(2)}`;
    } else {
        document.getElementById('totalPrice').value = "$0";
    }
}

// Kiralama işlemi
async function bookCar() {
    const carId = document.getElementById('selectedCarId').value;
    const pickupDate = document.getElementById('pickupDate').value;
    const dropoffDate = document.getElementById('dropoffDate').value;

    if (!carId || !pickupDate || !dropoffDate) {
        showErrorAlert("Please fill all fields!");
        return;
    }

    const bookingData = {
        car: { id: carId },
        startDate: pickupDate,
        endDate: dropoffDate
    };

    showLoadingSpinner();

    try {
        const response = await fetch(BASE_PATH + "bookings", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + jwtToken
            },
            body: JSON.stringify(bookingData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            showErrorAlert("Booking failed: " + (errorData.message || "Unknown error"));
            throw new Error("Failed to book car, status: " + response.status);
        }

        showSuccessAlert("Car booked successfully!");
        await getAllCars();
    } catch (error) {
        console.error("Error booking car:", error);
        showErrorAlert("Failed to book the car.");
    } finally {
        hideLoadingSpinner();
    }
}

// Alert fonksiyonları
function showSuccessAlert(message) {
    const alertBox = document.getElementById('successAlert');
    const alertMessage = document.getElementById('successAlertMessage');
    alertMessage.textContent = message;
    alertBox.classList.remove('d-none');
    setTimeout(() => {
        alertBox.classList.add('d-none');
    }, 3000);
}

function showErrorAlert(message) {
    const alertBox = document.getElementById('errorAlert');
    const alertMessage = document.getElementById('errorAlertMessage');
    alertMessage.textContent = message;
    alertBox.classList.remove('d-none');
    setTimeout(() => {
        alertBox.classList.add('d-none');
    }, 3000);
}

// Loading spinner fonksiyonları
function showLoadingSpinner() {
    const spinner = document.getElementById('loadingSpinner');
    spinner.classList.remove('d-none');
}

function hideLoadingSpinner() {
    const spinner = document.getElementById('loadingSpinner');
    spinner.classList.add('d-none');
}

// Sayfa yüklendiğinde arabaları getir
document.addEventListener("DOMContentLoaded", async () => {
    await getAllCars();
});

// Tarih değişiminde toplam fiyat hesapla
document.getElementById('pickupDate').addEventListener('change', calculateTotalPrice);
document.getElementById('dropoffDate').addEventListener('change', calculateTotalPrice);

// Kiralama butonuna tıklandığında işlem yap
document.getElementById('bookCarButton').addEventListener('click', bookCar);
