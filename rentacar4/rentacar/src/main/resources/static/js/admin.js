const jwtToken = localStorage.getItem('jwtToken');
const BASE_PATH = "http://localhost:8080/api/admin/";

// Arabaları listele
async function getAllCars() {
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
        renderCarTable(cars);

    } catch (error) {
        console.error("Error fetching cars:", error);
    }
}

// Arabaları tabloya yazdır
function renderCarTable(cars) {
    const carTableBody = document.getElementById('carTableBody');
    carTableBody.innerHTML = "";

    cars.forEach(car => {
        const row = carTableBody.insertRow();
        row.innerHTML = `
            <td>${car.brand}</td>
            <td>${car.model}</td>
            <td>${car.color}</td>
            <td>${car.dailyPrice}</td>
            <td>${car.availableCount}</td>
            <td>
                <button class="btn btn-danger" onclick="deleteCar(${car.id})">Delete</button>
            </td>
        `;
    });
}

// Yeni araba ekle
async function addCar() {
    const brand = document.getElementById('carBrand').value;
    const model = document.getElementById('carModel').value;
    const color = document.getElementById('carColor').value;
    const dailyPrice = document.getElementById('carDailyPrice').value;
    const availableCount = document.getElementById('carAvailableCount').value;

    if (!brand || !model || !color || !dailyPrice || !availableCount) {
        alert("All fields are required!");
        return;
    }

    const carData = {
        brand: brand,
        model: model,
        color: color,
        dailyPrice: parseFloat(dailyPrice),
        availableCount: parseInt(availableCount)
    };

    try {
        const response = await fetch(BASE_PATH + "cars", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + jwtToken
            },
            body: JSON.stringify(carData)
        });

        if (!response.ok) {
            throw new Error("Failed to add car, status: " + response.status);
        }

        alert("Car added successfully!");
        await getAllCars();

    } catch (error) {
        console.error("Error adding car:", error);
        alert("Failed to add car.");
    }
}

// Araba sil
async function deleteCar(carId) {
    try {
        const response = await fetch(BASE_PATH + `cars/${carId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + jwtToken
            }
        });

        if (!response.ok) {
            throw new Error("Failed to delete car, status: " + response.status);
        }

        alert("Car deleted successfully!");
        await getAllCars();

    } catch (error) {
        console.error("Error deleting car:", error);
        alert("Failed to delete car.");
    }
}

// Sayfa yüklendiğinde arabaları getir
document.addEventListener("DOMContentLoaded", async () => {
    await getAllCars();
});
