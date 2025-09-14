# 🛒 E-Commerce Android App

An **E-Commerce Android application** built with **Kotlin** and **Jetpack Compose (Material 3)**.  
It uses the [FakeStore API](https://fakestoreapi.com/) for products and demonstrates modern Android development with **MVVM architecture**, **Room**, **Paging 3**, and **Retrofit**.

---

## 🚀 Features
- 📱 Modern UI with **Jetpack Compose (Material 3)**
- 🔍 Product listing with **filters** (Category, Rating, Price, Sorting)
- 🛍️ Product detail screen
- 🛒 Add to **Cart**
- ⚡ Pagination with **Paging 3**
- 💾 Offline caching using **Room**
- 🌐 API integration with **Retrofit + OkHttp**

---

## 🛠️ Tech Stack
- **Language**: Kotlin  
- **UI**: Jetpack Compose (Material 3)  
- **Architecture**: MVVM + ViewModel + StateFlow  
- **Networking**: Retrofit + OkHttp  
- **Database**: Room  
- **Pagination**: Paging 3  

---

## 📂 Project Structure

app/
├── data/ # Repository, Retrofit API, Room
├── model/ # Product, Cart models
├── ui/ # Jetpack Compose screens
│ ├── component/ # Reusable UI components (ProductCard, FilterRow, etc.)
│ ├── ProductListScreen.kt
│ ├── ProductDetailScreen.kt
│ ├── CartScreen.kt
├── viewmodel/ # ViewModels
└── utils/ # Helpers


## ⚙️ Setup & Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/<Pavandew>/E-Commerce.git
