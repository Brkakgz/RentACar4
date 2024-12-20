const BASE_PATH = "http://localhost:8080/api/";

// Arabaları listele
async function getAllCars() {
    try {
        const response = await fetch(BASE_PATH + "cars", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error("Failed to fetch cars, status: " + response.status);
        }

        const cars = await response.json();
        renderCarList(cars);
        return cars;

    } catch (error) {
        console.error("Error fetching cars:", error);
    }
}

// Arabaları filtrele
async function applyFilter() {
    const brand = document.getElementById('brandFilter').value;
    const color = document.getElementById('colorFilter').value;
    const maxPrice = document.getElementById('priceFilter').value;
    const availability = document.getElementById('availabilityFilter').value;

    const allCars = await getAllCars();
    const filteredCars = allCars.filter(car => {
        return (!brand || car.brand === brand) &&
               (!color || car.color === color) &&
               (!maxPrice || car.dailyPrice <= maxPrice) &&
               (!availability || (availability === "true" && car.availableCount > 0) || (availability === "false" && car.availableCount === 0));
    });

    renderCarList(filteredCars);
}

// Arabaları ekrana yazdır
function renderCarList(cars) {
    const carContainer = document.getElementById('carContainer');
    carContainer.innerHTML = "";

    cars.forEach(car => {
        const carElement = document.createElement("div");
        carElement.classList.add("car");
        carElement.innerHTML = `
            <img src="${car.imageUrl}" alt="${car.brand} ${car.model}">
            <h3>${car.brand} ${car.model}</h3>
            <p>Color: ${car.color}</p>
            <p>Daily Price: $${car.dailyPrice}</p>
            <p>${car.availableCount > 0 ? "Available" : "Not Available"}</p>
            ${car.availableCount > 0 ? `<button class="btn btn-primary" onclick="promptLogin()">Book Now</button>` : ""}
        `;
        carContainer.appendChild(carElement);
    });
}

// Sayfa yüklendiğinde arabaları getir
document.addEventListener("DOMContentLoaded", async () => {
    await getAllCars();
});

// Filtre butonu tıklandığında filtre uygula
document.getElementById('applyFilter').addEventListener('click', applyFilter);
