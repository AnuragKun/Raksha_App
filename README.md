Raksha – Women’s Safety Navigation App

Raksha is an Android application built using Kotlin and powered by Firebase to enhance women’s safety during travel. The app provides real-time safety insights, crowdsourced incident reporting, safer route suggestions, and automated emergency features. It aims to help users make informed decisions and stay protected in unsafe environments.


---

📌 Features

1. Real-Time Route Safety Analysis

Displays safety scores for chosen routes.

Highlights unsafe areas using color-coded zones:

Green: Safe

Yellow: Caution

Red: Danger


Provides safer alternative routes when available.


2. Community-Driven Incident Reporting

Verified users can submit reports about local incidents.

Reports influence the safety rating of locations.

Includes a validation mechanism to prevent misuse (details in next section).


3. Report Verification System (Anti-Fake Reports)

Each location maintains an average safety rating.

New report ratings are checked against this average.

Outliers are ignored unless:

Multiple similar reports appear in a short time window.


Ensures false data doesn’t corrupt public safety insights.


4. Timed Safety Check (Auto-SOS)

User can start a timed check before traveling.

If they fail to confirm their safety:

Server triggers Auto SOS

Live location is sent to emergency contacts.


Works even if the app is killed (handled via Cloud Functions).


5. Live Safety Alerts

Uses Firebase Cloud Messaging (FCM).

Notifies users when they enter or approach a danger zone.


6. Emergency SOS

One-tap SOS button.

Sends:

Live location

Status message

SMS fallback when offline



7. Secure Authentication

Google Sign-In with Firebase Authentication.



---

🛠 Tech Stack

Frontend

Kotlin

Android Studio (Ladybug / Latest)

Google Maps SDK

Google Places API


Backend

Firebase Firestore (data storage)

Firebase Authentication

Firebase Cloud Functions (timed safety check automation)

Firebase Cloud Messaging (FCM)


Other Integrations

SMS API (offline emergency alerts)



---

📂 Project Architecture

Raksha follows Clean Architecture:

Presentation Layer
   ↓
Domain Layer
   ↓
Data Layer
   ↓
Firebase / APIs

Modules include:

Auth

Reports

Safety Zones

Routing

TimedCheck

EmergencySOS



---

📁 Folder Structure (Simplified)

/app
  /data
    /repository
    /models
    /firebase
  /domain
    /usecases
    /repositories
  /presentation
    /auth
    /home
    /maps
    /report
    /safetycheck
    /sos


---

🚀 Getting Started

1. Clone the Repository

git clone https://github.com/<your-username>/raksha-app.git

2. Add Required API Keys

Create or edit:

local.properties

Add:

MAPS_API_KEY=your_key_here
PLACES_API_KEY=your_key_here

3. Setup Firebase

Download google-services.json from Firebase Console

Place it under:


app/

4. Build and Run

Open Android Studio → Sync Gradle → Run app on device.


---

🧪 Future Enhancements

AI-based anomaly detection for abnormal route behavior

WearOS companion app

Offline maps with caching

Emergency triggers using gesture detection



---

📚 Documentation

Full technical report

Use Case Diagram

ER Diagram

Class Diagram
(Include links here if hosted elsewhere)



---

👥 Contributors

Anurag Rana
Aayush Soam
Gaurav 
Harishankar

Team Raksha



---

📜 License

MIT License or whichever you prefer.


---

If you want, I can tailor this README to match your exact repository name, add badges, or include screenshots sections.
