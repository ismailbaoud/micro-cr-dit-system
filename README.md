#  Micro-credit Scoring System

##  Description

The **Micro-credit Scoring System** is a Java console application designed to automate credit risk evaluation for microfinance institutions.  
It provides an intelligent scoring engine to assess client eligibility based on professional stability, financial capacity, credit history, customer relationship, and assets.

The system aims to:
- Enhance access to financing.
- Reduce subjectivity in credit decisions.
- Ensure rapid, traceable, and data-driven approvals.

---

##  Project Structure

```
Micro-credit scoring system/
├── .idea/
├── lib/
├── out/
├── src/
│   ├── main/
│   │   ├── java/com/ismail/MicroCreditScoringSystem/
│   │   │   ├── app/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── util/
│   │   │   └── view/
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── script.sql
│   └── test/java/com/ismail/MicroCreditScoringSystem/
├── .gitignore
├── Micro-credit scoring system.iml
└── README.md
```

---

##  Main Features

### **Module 1: Client Management**
- Add, modify, delete, and list clients.
- View detailed client profiles.

**Classes:**
- `Personne` *(abstract)* → `nom`, `prenom`, `dateNaissance`, `ville`, `nombreEnfants`, `investissement`, `placement`, `situationFamiliale`, `createdAt`, `score`
- `Employe` *(extends Personne)* → `salaire`, `anciennete`, `poste`, `typeContrat`, `secteur`
- `Professionnel` *(extends Personne)* → `revenu`, `immatriculationFiscale`, `secteurActivite`, `activite`

---

### **Module 2: Credit & Score Management**
- Automatic credit scoring using 5 components:
    - Professional stability
    - Financial capacity
    - Credit history
    - Customer relationship
    - Complementary criteria
- Loan eligibility validation and decision engine.
- Loan amount calculation based on score and client type.

**Classes:**
- `Credit` → `dateCredit`, `montantDemande`, `montantOctroye`, `tauxInteret`, `dureeEnMois`, `typeCredit`, `decision`

---

### **Module 3: Payment History Tracking**
- Generate payment schedules and track payment status.
- Classify payments:
    - `PAYE_A_TEMPS`
    - `EN_RETARD`
    - `IMPAYE_REGLE`
    - `IMPAYE_NON_REGLE`
- Apply bonuses and penalties based on history.

**Classes:**
- `Echeance`
- `Incident`

---

### **Module 4: Decision Engine**
- **ACCORD_IMMEDIAT** → score ≥ 80
- **ETUDE_MANUELLE** → 60 ≤ score < 80
- **REFUS_AUTOMATIQUE** → score < 60

---

### **Module 5: Analytics**
- Multi-criteria search and reporting:
    - Eligible clients for real estate loans.
    - Top 10 high-risk clients.
    - Sorting and filtering by score, revenue, and seniority.
    - Distribution by job type (CDI, public, PME, etc.).
- Support for marketing campaigns and client segmentation.

---

##  Console Menu

```
=== MAIN MENU ===
1 => Client Management
2 => Credit & Score Management
0 => Exit
```

### Client Management
```
1 => Add New Client
2 => Modify Client
3 => Delete Client
4 => View Client Profile
5 => List All Clients
0 => Back to Main Menu
```

### Credit & Score Management
```
1 => Add New Credit
2 => Handle Manual Review Loans
3 => Display Manual Review Loans
4 => Display General Statistics
5 => Pay Credit
0 => Go Back
```

---

##  Technical Specifications

- **Language:** Java 8
- **Architecture:** Multi-layered (MVC pattern)
    - `Controller`, `Service`, `Repository`, `Model`, `View`, `Util`
- **Database:** MySQL (via JDBC)
- **Design Patterns:** Singleton, Repository
- **Date Handling:** Java Time API
- **Collections Used:** `Stream`, `HashMap`, `Optional`

---

##  Scoring Rules

| Client Type | Score Range | Max Loan Amount |
|--------------|--------------|-----------------|
| New Client | ≥ 70 | 4 × Salary |
| Existing Client | 60–80 | 7 × Salary |
| Existing Client | > 80 | 10 × Salary |

---

##  Bonus & Penalties

| Event | Effect |
|-------|---------|
| On-time payments | +10 points |
| Late payment (5–30 days) | -5 points |
| Unpaid (31+ days) | -10 points |

---

##  How to Run the Project

###  Prerequisites
- Java 8+
- MySQL Server
- IntelliJ IDEA or any Java IDE
- Git

###  Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/micro-credit-scoring-system.git
   ```
2. Import the project into IntelliJ IDEA.
3. Configure your `application.properties` with database credentials.
4. Execute the SQL script `script.sql` to create necessary tables.
5. Run the `Main` class from the `app` package.

---

##  Technologies Used

- Java SE 8
- JDBC
- MySQL
- Collections Framework
- Design Patterns (Repository, Singleton)
- Java Time API

---

##  Author

**Name:** Ismail [Your Last Name]  
**Project:** Micro-credit Scoring System  
**Institution:** Simplon Maroc  
**Session:** Promotion MicroJavaneers – 2025

---

##  License

This project is licensed under the MIT License.