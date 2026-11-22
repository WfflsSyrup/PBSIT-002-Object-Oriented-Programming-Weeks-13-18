# User Guide — GuidedRecordsApp

PBSIT 002 Object Oriented Programming — Week 17
University of La Salette, Inc., Section 3A

## Overview

GuidedRecordsApp is a console application for managing records with exception-safe input validation and text-file persistence. Records consist of five fields: ID, Name, Category, Quantity, and Status.

## Getting Started

### Prerequisites
- JDK 17 or later
- Apache NetBeans (optional) or command-line Java tools

### Launching the Application

**Via Apache NetBeans:**
1. Open the `GuidedRecordsApp` project.
2. Right-click the project and select **Run**.

**Via Command Line:**
```bash
javac -d build\classes src\model\ItemRecord.java src\exception\DuplicateRecordException.java src\service\RecordManager.java src\repository\RecordRepository.java src\repository\TextFileRecordRepository.java src\app\Main.java
java -cp build\classes app.Main
```

On startup, the application automatically loads records from `data/records.txt` and reports how many records were loaded.

## Menu Options

| Option | Description |
|--------|-------------|
| **1 — Add record** | Prompts for ID, name, category, quantity, and status. Validates all inputs before adding. |
| **2 — List records** | Displays all stored records in formatted rows. Shows "No records stored." if empty. |
| **3 — Search record** | Prompts for a record ID (case-insensitive). Shows the matching record or "Record not found." |
| **4 — Filter by category** | Prompts for a category name. Displays all records matching that category (case-insensitive). |
| **5 — Update record** | Prompts for an ID and new values for all fields. Validates before applying changes atomically. |
| **6 — Remove record** | Prompts for a record ID. Removes the matching record or reports "Record not found." |
| **7 — Show summary & save** | Displays record count and total quantity, then saves to file. |
| **0 — Save and exit** | Saves all records to `data/records.txt` and exits. |

## Data File

Records are persisted in `data/records.txt` using pipe-delimited format:

```
id|name|category|quantity|status
```

Example starter data:
```
R001|Sample One|Tools|5|Good
R002|Sample Two|Electronics|8|New
R003|Sample Three|Furniture|12|Fair
```

## Validation & Error Handling

| Input Issue | Message Displayed |
|-------------|-------------------|
| Blank ID | "Unable to add: Record ID is required." |
| Blank name | "Unable to add: Record name is required." |
| Blank category | "Unable to add: Record category is required." |
| Blank status | "Unable to add: Record status is required." |
| Pipe (`|`) in any field | "Unable to add: Record fields must not contain the '|' delimiter." |
| Zero or negative quantity | "Unable to add: Quantity must be greater than zero." |
| Non-numeric quantity | "Unable to add: Quantity must be a whole number." |
| Duplicate ID (case-insensitive) | "Unable to add: Record ID already exists: R001" |
| Missing record on search/update/remove | "Record not found." |
| File load failure | "Records were not loaded: <reason>" |
| File save failure | "Records were not saved: <reason>" |

After any error, the application returns to the main menu — it never crashes or terminates unexpectedly.

## Data Privacy

All sample data is strictly synthetic. No real names, passwords, tokens, or personal identifiable information (PII) are used.
