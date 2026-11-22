# Test Results — GuidedRecordsApp

PBSIT 002 Object Oriented Programming — Week 17
University of La Salette, Inc., Section 3A

## Test Environment

- **Project:** GuidedRecordsApp (Java with Ant, JDK 24)
- **Main Class:** `app.Main`
- **Platform:** Windows 11 (x64)
- **Build Result:** BUILD SUCCESSFUL
- **Test Data:** Synthetic only (`R001`–`R003`, `Sample One/Two/Three`, `Tools`/`Electronics`/`Furniture`)

## Test Case Matrix (TC-01 – TC-12)

| Case | Scenario | Input | Expected Result | Status |
|------|----------|-------|-----------------|--------|
| TC-01 | Add valid record | R001, Sample One, Tools, 5, Good | Record added; appears in list | PASS |
| TC-02 | Blank ID | "   ", Sample Two, Tools, 2, New | Rejected: "Record ID is required."; menu stays active | PASS |
| TC-03 | Non-numeric quantity | R002, Sample Two, Electronics, five, New | Rejected: "Quantity must be a whole number." | PASS |
| TC-04 | Zero quantity | R003, Sample Three, Furniture, 0, Fair | Rejected: "Quantity must be greater than zero." | PASS |
| TC-05 | Duplicate ID (case variation) | R001 (and r001), Another Sample, Tools, 3, Good | Rejected: "Record ID already exists: R001"; original unchanged | PASS |
| TC-06 | Search existing record | R002 | Found: R002 | Sample Two | Electronics | Quantity: 8 | Status: New | PASS |
| TC-07 | Search missing record | R999 | Record not found | PASS |
| TC-08 | Search with whitespace/case | "  r002  " | Found: R002 | PASS |
| TC-09 | Update valid record | R002, Updated Two, Electronics, 20, Excellent | Record updated with new values | PASS |
| TC-10 | Update with invalid quantity | R002, Name, Category, -1, Status | Rejected: "Quantity must be greater than zero."; original state preserved | PASS |
| TC-11 | Remove existing record | R001 | Record removed successfully | PASS |
| TC-12 | Remove missing record | R999 | Record not found | PASS |

## Encapsulation Check

| Check | Action | Result |
|-------|--------|--------|
| ENC-01 | `manager.findAll().add(...)` | Throws `UnsupportedOperationException` (unmodifiable snapshot) | PASS |

## Persistence Check

| Check | Action | Result |
|-------|--------|--------|
| PER-01 | Add record, save (option 7 or 0), exit, restart, list | Reloaded records match saved values exactly | PASS |
| PER-02 | No `data/records.txt` on first run | Starts cleanly with 0 records; "Loaded 0 record(s)." | PASS |

## Defect / Retest Log

| ID | Description | Root Cause | Fix | Retest Result |
|----|-------------|------------|-----|---------------|
| DEF-001 | `StandardCharsets` imported from `java.nio.file` instead of `java.nio.charset` | Incorrect import statement | Changed import to `java.nio.charset.StandardCharsets` | PASS |
| DEF-002 | `update()` could leave `name` half-updated if `quantity` validation failed mid-mutation | Validation applied sequentially to existing object | Validate via temporary `ItemRecord` before applying any mutation | PASS |

## Regression Log (REG-01 – REG-06)

| ID | Area | Description | Result |
|----|------|-------------|--------|
| REG-01 | Validation | Blank ID rejected with correct message | PASS |
| REG-02 | Validation | Pipe delimiter in any field rejected | PASS |
| REG-03 | Duplicate detection | Case-insensitive duplicate ID blocked | PASS |
| REG-04 | Persistence | Saved records survive exit and restart | PASS |
| REG-05 | Encapsulation | `findAll()` returns unmodifiable copy | PASS |
| REG-06 | Recovery | Menu remains active after any caught exception | PASS |

## Summary

- **Total test cases:** 12 (TC-01 – TC-12)
- **Passed:** 12
- **Failed:** 0
- **Defects found:** 2 (DEF-001, DEF-002) — both fixed and retested
- **Regression checks:** 6 (REG-01 – REG-06) — all passed
- **Build:** BUILD SUCCESSFUL
- **Data privacy:** All test data is synthetic
