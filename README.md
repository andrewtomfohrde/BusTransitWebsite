# Visual Transit Simulator

## 🚀 How to Run the Application

### 1. Start the Simulation Server

From the project root directory:

```
./gradlew clean appRun
```

This launches the backend simulation engine.

---

### 2. Open the Visualization Interface

Open your browser and navigate to:

```
http://localhost:7777/project/web_graphics/project.html
```

---

### 3. Run the Simulation

Using the web interface, you can:

- Set how long the simulation should run  
- Configure how frequently new vehicles are added  
- Start and pause the simulation  

---

### Stopping the Application

To stop the backend server, press:

```
ENTER
```

in the terminal.

If the server fails to start due to a previously running instance:

```
pkill gretty
```

---

## Overview

The Visual Transit Simulator is a real-time web-based transportation simulation platform that models buses and trains operating across a geographic transit network.

Vehicles travel along bidirectional routes, stop to service passengers, and exit the system after completing their assigned routes. Passenger demand is generated dynamically, requiring multiple vehicles to coordinate service across the network.

This project demonstrates real-time simulation design, backend–frontend communication, and object-oriented modeling of transportation systems.

---

## Features

### Real-Time Transit Simulation

- Simulates buses and trains servicing transit lines  
- Vehicles follow outbound and inbound routes  
- Passengers are generated probabilistically at stops  
- Multiple vehicles can operate on the same line  
- Vehicles leave the system after completing service  

### Live Web Visualization

- Browser-based interface displays vehicles and stops  
- Simulation updates are streamed using WebSockets  
- Users can control simulation execution and configuration  

### Dynamic Vehicle Coloring

Vehicles are visually distinguished by type:

- Small Bus — Maroon  
- Large Bus — Pink  
- Electric Train — Green  
- Diesel Train — Yellow  

If operational issues occur on a transit line, affected vehicles become semi-transparent to indicate disruption.

---

## System Architecture

The application consists of two main components.

### Simulation Engine (Backend)

A Java server responsible for:

- modeling routes and stops  
- generating passengers  
- managing vehicle movement and lifecycle  
- coordinating simulation timing  
- sending real-time updates via WebSockets  

### Visualization Client (Frontend)

A JavaScript web client responsible for:

- rendering the transit network  
- displaying vehicle movement  
- providing user interaction controls  

Communication between the frontend and backend occurs using JSON messages over WebSockets.

---

## Configuration

Simulation behavior is controlled by:

```
app/src/main/resources/config.txt
```

This file defines:

- transit lines  
- route stop locations  
- passenger generation probabilities  
- available vehicle inventory  

---

## Testing

Tests validate core simulation logic including:

- route traversal  
- passenger generation  
- vehicle lifecycle behavior  
- server message handling  

Coverage reports can be generated using:

```
./gradlew clean test jacocoTestReport
```

---

## Technologies Used

- Java  
- Gradle  
- WebSockets  
- JavaScript  
- HTML5  
- JUnit  
- Jacoco  

---

## Author

Andrew Tomfohrde
