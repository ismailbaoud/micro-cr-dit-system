-- ==============================================
-- Database Creation
-- ==============================================
CREATE DATABASE IF NOT EXISTS micro_credit;
USE micro_credit;

-- ==============================================
-- Employee Table
-- Contains personal and professional information for salaried clients.
-- ==============================================
CREATE TABLE Employee (
    id VARCHAR(200) PRIMARY KEY,
    -- Person attributes
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE,
    city VARCHAR(255),
    investment VARCHAR(50),
    placement VARCHAR(50),
    children_count INT DEFAULT 0,
    marital_status VARCHAR(100),
    score INT ,
    -- Employee specific attributes
    salary DECIMAL(15, 2),
    seniority_in_years INT,
    contract_type ENUM("CDI" ,
                          "CDD",
                          "Other",
                          "STABLE_LIBERAL_PROFESSION",
                          "SELF_EMPLOYED"),
    sector ENUM('PUBLIC', 'LARGE_COMPANY', 'SME')
);

-- ==============================================
-- Professional Table
-- Contains personal and professional information for self-employed clients.
-- ==============================================
 CREATE TABLE Professional (
    id VARCHAR(200) PRIMARY KEY,
    -- Person attributes
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE,
    city VARCHAR(255),
    investment VARCHAR(50),
    placement VARCHAR(50),
    children_count INT DEFAULT 0,
    marital_status VARCHAR(100),
    score INT,
    -- Professional specific attributes
    income DECIMAL(15, 2),
    seniority_in_years INTEGER ,
    tax_registration VARCHAR(255),
    last_declaration_date DATE,
    contract_type ENUM("STABLE_LIBERAL_PROFESSION","SELF_EMPLOYED", "OTHERS"),
    activity VARCHAR(200),
    activity_sector ENUM('AGRICULTURE', 'SERVICE',  'INDUSTRY', 'CONSTRUCTION', 'OTHER')
 );

-- ==============================================
-- Loan Table (Credit)
-- Each loan is linked to a client (either an Employee or a Professional).
-- ==============================================
CREATE TABLE Loan (
    id VARCHAR(200) PRIMARY KEY,
    employee_id VARCHAR(200) NULL,
    professional_id VARCHAR(200) NULL,
    application_date DATE NOT NULL,
    requested_amount DECIMAL(15, 2),
    amount_granted DECIMAL(15, 2),
    interest_rate DECIMAL(15, 2),
    duration_in_months INT NOT NULL,
    decision ENUM('IMMEDIATE_APPROVAL', 'MANUAL_REVIEW', 'AUTOMATIC_REJECTION'),

    -- Foreign Keys
    CONSTRAINT fk_loan_employee FOREIGN KEY (employee_id) REFERENCES Employee(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_loan_professional FOREIGN KEY (professional_id) REFERENCES Professional(id)
        ON DELETE CASCADE
);

-- ==============================================
-- Installment Table
-- Each installment is linked to a specific loan.
-- ==============================================
CREATE TABLE Installment (
    id VARCHAR(200) PRIMARY KEY,
    loan_id VARCHAR(200) NOT NULL,
    due_date DATE NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    payment_date DATE, -- Can be NULL if not yet paid
   status ENUM'NON','PAID_ON_TIME','LATE','PAID_LATE','UNPAID_NOT_SETTLED','UNPAID_SETTLED'),
    -- Foreign Key
    CONSTRAINT fk_installment_loan FOREIGN KEY (loan_id) REFERENCES Loan(id)
        ON DELETE CASCADE
);

ALTER TABLE Installment MODIFY COLUMN status ENUM('NON','PAID_ON_TIME','LATE','PAID_LATE','UNPAID_NOT_SETTLED','UNPAID_SETTLED');

-- ==============================================
-- Incident Table
-- Each incident is linked to a specific payment installment.
-- ==============================================
CREATE TABLE Incident (
    id VARCHAR(200) PRIMARY KEY,
    installment_id VARCHAR(200) NOT NULL,
    incident_date DATE NOT NULL,
    incident_type VARCHAR(255),

    -- Foreign Key
    CONSTRAINT fk_incident_installment FOREIGN KEY (installment_id) REFERENCES Installment(id)
        ON DELETE CASCADE
);