# Eisenhower Matrix Task Manager

A simple, self-contained task management tool using the Eisenhower Decision Matrix. Available in both CLI and Web UI versions.

Inspired by Randy Pausch's Time Management Lecture
https://www.cs.virginia.edu/~robins/Randy/RandyPauschTimeManagement2007.pdf


## Table of Contents

- [Quick Start](#quick-start)
  - [CLI Version](#cli-version-matrixpy)
  - [Web UI Version](#web-ui-version-apppy)
- [Requirements](#requirements)
- [Installation](#installation)
- [Features](#features)
  - [CLI Features](#cli-features)
  - [Web UI Features](#web-ui-features)
- [Usage](#usage)
  - [CLI Commands](#cli-commands)
  - [Web UI Operations](#web-ui-operations)
- [Database](#database)
- [Implementation Details](#implementation-details)
- [Productivity Principles](#productivity-principles)
- [Testing Plan](#testing-plan)

---

## Quick Start


No installation, no dependencies, no setup required.

### Web UI Version (app.py)

```bash
# Install Flask (one-time setup)
pip install flask

# Run the application
python3 app.py
```

Open browser to http://localhost:5000

---

## Requirements

### Web UI Version (app.py)
- Python 3.6 or higher
- Flask 3.0.0+ (single external dependency)
- Modern web browser

---

## Installation

### Web UI Version Setup

#### Option 1: Direct Installation
```bash
pip install flask
python3 app.py
```

#### Option 2: With Virtual Environment (Recommended)
For isolation:
```bash
cd /path/to/eisen-h-matrix-task-manager
python3 -m venv venv

# Linux/Mac:
source venv/bin/activate

# Windows:
venv\Scripts\activate

# Install Flask
pip install flask

# Run
python3 app.py

# Access at http://localhost:5000

# When done
deactivate
```

---

## Features


### Web UI Features

- ✅ Modern web interface with responsive design
- ✅ Single file application with embedded HTML templates
- ✅ **Add Task Form** with inline validation
  - Title field (required, max 100 characters)
  - Description field (optional, max 500 characters)
  - Quadrant selector with explanations
  - Due date picker with minimum date validation
  - Character count indicators
  - Collapsible form to reduce clutter
- ✅ 2×2 grid layout with color-coded quadrants
  - Green for DO (Urgent & Important)
  - Blue for DECIDE (Not Urgent & Important)
  - Red for DELEGATE (Urgent & Not Important)
  - Gray for DELETE (Neither)
- ✅ Task cards with comprehensive information
  - Title and description display
  - Due date display
  - **Overdue visual indicator** - Cards turn bright red when due date passes
  - Completion status checkbox
  - Movement arrows to other quadrants
  - **📦 Archive button** on every task card
- ✅ **Scroll Position Preservation**
  - Maintains scroll position after all form submissions (move, archive, add)
  - Uses sessionStorage for reliable position tracking
  - Automatically restores position on page reload
  - Works seamlessly with archived matrix section
  - Graceful degradation when JavaScript disabled
- ✅ **Archived Eisenhower Matrix Section**
  - Duplicate 2×2 matrix below active tasks
  - Shows archived tasks in their original quadrants
  - Archived badge on each quadrant header
  - Displays archive date timestamp
  - Faded styling to distinguish from active tasks
  - Read-only view (no edit/move capabilities)
- ✅ Arrow button navigation between quadrants
  - Context-aware buttons (only show valid moves)
  - Visual feedback on hover
- ✅ SQLite database backend for persistence
- ✅ Fully offline operation (no external APIs)
- ✅ Cross-platform browser support
- ✅ Server-side rendering (no JavaScript required)
- ✅ Principles page accessible via header button
- ✅ Form validation and input sanitization
- ✅ Automatic timestamp tracking (created_at, completed_at, archived_at)
- ✅ Original quadrant tracking for archived tasks

---

## Usage

Both versions display a 2×2 matrix with four quadrants:
- **DO** (Urgent & Important) - Green - Do it now
- **DECIDE** (Not Urgent & Important) - Blue - Schedule it
- **DELEGATE** (Urgent & Not Important) - Red - Assign it
- **DELETE** (Neither) - Gray - Eliminate it

### Web UI Operations

1. **Adding Tasks**
   - Click "➕ Add Task" button in header
   - Fill out the form:
     - Enter title (required)
     - Add description (optional)
     - Select target quadrant from dropdown
     - Choose due date (optional)
   - Click "Add Task" to submit
   - Form auto-collapses after successful submission

2. **Moving Tasks**
   - Each task card displays contextual arrow buttons
   - Click arrow button pointing to desired quadrant
   - Task instantly moves to new quadrant
   - Page refreshes to show updated matrix

3. **Archiving Tasks**
   - Click **📦 Archive** button on any task card
   - Task immediately moves to Archived Matrix below
   - Appears in same quadrant as original (DO/DECIDE/DELEGATE/DELETE)
   - Original quadrant preserved in database
   - Archive timestamp automatically recorded

4. **Viewing Archived Tasks**
   - Scroll down to see "📦 Archived Eisenhower Matrix" section
   - Archived tasks organized by their original quadrants
   - Each quadrant shows "Archived" badge
   - Tasks display faded styling for visual distinction
   - Archive date shown on each task
   - Read-only (cannot move or edit archived tasks)

5. **Completing Tasks**
   - Click checkbox on task card to mark complete
   - Completed tasks show [x] status
   - Completion timestamp automatically recorded

6. **Viewing Principles**
   - Click "View Principles" button in header
   - Displays all 10 productivity principles
   - Click "← Back to Matrix" to return

---

## Database
```sql
CREATE TABLE tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    description TEXT,
    quadrant TEXT NOT NULL,
    due_date TEXT,
    completed INTEGER DEFAULT 0,
    archived INTEGER DEFAULT 0,
    original_quadrant TEXT,
    created_at TEXT NOT NULL,
    completed_at TEXT,
    archived_at TEXT
)
```

Both CLI and Web UI versions share the same database format and can be used interchangeably.

---

## Implementation Details

### Files Structure
- `app.py` - Web UI version (requires Flask)
- `tasks.db` - SQLite database (auto-created)
- `readme.md` - This documentation

### Current Implementation Status

#### Completed Features
- ✅ CLI version fully implemented
- ✅ Web UI version with add task form
- ✅ SQLite database integration
- ✅ Task CRUD operations (Create, Read, Update, Delete/Archive)
- ✅ Quadrant color coding
- ✅ Task movement between quadrants
- ✅ Completion tracking
- ✅ Archive functionality with quadrant history
- ✅ Archived Eisenhower Matrix display section
- ✅ Due date support
- ✅ Principles viewer
- ✅ Form validation and input constraints
- ✅ Responsive design for web UI
- ✅ Server-side rendering (no JavaScript dependencies)
- ✅ **Scroll position preservation during form submissions**
  - JavaScript-based scroll position saving to sessionStorage
  - Automatic restoration on page reload
  - Works with move, archive, and add operations
  - Graceful degradation when JavaScript disabled

#### Pending Features (From TODO List)
- [ ] Task limit enforcement (100 task maximum) in web UI
- [ ] Edit existing tasks
- [ ] View archived tasks page in web UI
- [ ] Delete (hard delete) functionality
- [ ] Task filtering and search
- [ ] Export/import functionality
- [ ] Statistics dashboard
- [ ] Weekly reflection prompts (Principle #6)

---

## Productivity Principlesiples" button in header
   - Displays all 10 productivity principles
   - Click "← Back to Matrix" to return

---
  - Description field (optional, max 500 characters)
  - Quadrant selector with explanations
  - Due date picker with minimum date validation
  - Character count indicators
  - Collapsible form to reduce clutter
- ✅ 2×2 grid layout with color-coded quadrants
  - Green for DO (Urgent & Important)
  - Blue for DECIDE (Not Urgent & Important)
  - Red for DELEGATE (Urgent & Not Important)
  - Gray for DELETE (Neither)
- ✅ Task cards with comprehensive information
  - Title and description display
  - Due date display
  - Completion status checkbox
  - Movement arrows to other quadrants
- ✅ Arrow button navigation between quadrants
  - Context-aware buttons (only show valid moves)
  - Visual feedback on hover
- ✅ SQLite database backend for persistence
- ✅ Fully offline operation (no external APIs)
- ✅ Cross-platform browser support
- ✅ Server-side rendering (no JavaScript required)
- ✅ Principles page accessible via header button
- ✅ Form validation and input sanitization
- ✅ Automatic timestamp tracking (created_at, completed_at, archived_at)
- ✅ Original quadrant tracking for archived tasks

---

## Database

Tasks are stored in `tasks.db` (SQLite) in the application directory. This file is portable and can be backed up or shared.

---

# All Unit and Functional Tests

See **Testing Plan** section below for comprehensive test coverage strategy.

---

# Important Information

1. All plans and tests must be written in this readme file or add to it and other markdown should be removed.

2. This application is intended to be a simple, self-contained project without external dependencies beyond standard libraries and lightweight frameworks.


# Principles Mentioned

Concentrate on the most important tasks and ignore/delegate the rest (see #4).


2. Aim to be excellent enough to take advantage of the 80/20 rule, but do it correctly.


3. There is no such thing as "finding time to accomplish things." You must consciously choose not to do anything else in order to make time.


4. To categorize tasks, use the Eisenhower decision matrix.

If it's urgent and vital, do it right away.

Schedule a time to accomplish it if it is not urgent and critical.

If it is both urgent and unimportant, attempt to assign it.

Ignore it if it isn't urgent or vital.


5. Make a fake deadline and pretend it's real to deal with procrastination.


6. Work to slowly minimize wasted time through weekly reflections and periodically tracking your time.

7. You can't accomplish anything worthwhile alone. Write thank you notes to people who help you.


8. Find your creative time, the few hours a day you are most productive, and guard it with your life (no meetings).


9. Set an hourly rate for your time and value it more than your money. Try to outsource most things below your rate.


10. It takes time to recover after you're interrupted. Checking your phone for 3 minutes takes roughly 10 minutes away from you since you need to refocus.
---

## Plan for Application and TODO Items

### Design Principles
- **Simplicity** - Keep the architecture straightforward and easy to understand
- **Extensibility** - Design components to allow future enhancements
- **Maintainability** - Write clean, well-documented code for long-term support

### Overview
Build a self-sustained, offline-capable Python web application that runs locally on any hardware. Features drag-and-drop task cards organized into four quadrants (Urgent/Important, Not Urgent/Important, Urgent/Not Important, Neither), backed by a SQLite database, with a principles pop-up displaying the 10 productivity principles. All tasks include due dates, completion tracking, and archival with original quadrant information.

### Implementation Steps

1. **Set up backend with Flask and SQLAlchemy (simplest approach)**
   - Create database models for tasks (id, title, description, quadrant, position, due_date, completed, archived, original_quadrant, created_at, completed_at, archived_at)
   - Build RESTful API endpoints (GET, POST, PUT, DELETE) for task CRUD operations
   - Use embedded SQLite database for offline capability and portability
   - Implement in `app.py` (single file for simplicity)

2. **Build frontend with HTML/CSS/JavaScript and drag-drop library**
   - Create `static/index.html` with a 2×2 grid layout matching the Eisenhower matrix
   - Integrate SortableJS for dragging task cards between quadrants (lightweight, no dependencies)
   - Add a principles button at the top that triggers a modal/pop-up displaying the 10 principles
   - Embed all CSS and JavaScript in HTML file for single-file portability

3. **Implement task card components with full lifecycle management**
   - Design card UI elements with "Add Task" forms including title, description, and due date fields
   - Display existing tasks as draggable cards showing title, description, and due date
   - Add completion checkboxes to mark tasks as done
   - Include archive functionality with automatic tracking of original quadrant
   - Handle card repositioning by sending PUT requests to update the quadrant field in the database

4. **Style the interface matching Eisenhower/Covey aesthetics**
   - Apply color scheme (green for Do, blue for Decide, red for Delegate, gray for Delete)
   - Add hover effects and smooth transitions
   - Ensure responsive design for different screen sizes

5. **Wire up backend-frontend integration**
   - Connect JavaScript fetch calls to API endpoints (no external dependencies)
   - Implement real-time database updates when cards are moved between quadrants
   - Add error handling and loading states
   - Ensure all operations work offline without internet connectivity

6. **Package for local deployment**
   - Create simple `requirements.txt` with minimal dependencies (Flask, SQLAlchemy)
   - Include startup script or instructions to run `python app.py`
   - Ensure cross-platform compatibility (Windows, Mac, Linux)
   - Store database file locally in application directory

### TODO: Implementation Checklist

- [ ] ✅ **Use Flask for simplicity** - Single Python file with embedded SQLite database
- [ ] ✅ **All tasks include due dates** - Required field in task creation form and database model
- [ ] ✅ **Task completion functionality** - Checkbox to mark tasks complete with timestamp
- [ ] ✅ **Task archival with quadrant history** - Archive feature that stores original quadrant information
- [ ] **Priority ordering within quadrants** - Optional: Allow users to reorder tasks within the same quadrant
- [ ] **Offline functionality verified** - Test application runs without internet connection on Windows, Mac, and Linux

---

## Testing Plan

### Design Philosophy
The testing strategy focuses on **foolproof operation** through **highly constrained functionality**. Every feature is limited to its essential purpose with extensive validation to prevent misuse or errors.

### Unit Tests

#### 1. Scroll Position Preservation Tests (`test_scroll_position.py`)
**Purpose:** Ensure page maintains scroll position after form submissions (move, archive, add operations)

- **Test scroll position saved on form submission**
  - Navigate to page and scroll to specific position (e.g., 500px from top)
  - Submit move task form
  - Verify sessionStorage contains saved scroll position
  - Confirm scroll position is restored after page reload

- **Test scroll position maintained during task movement**
  - Scroll to middle of page (near archived matrix section)
  - Click move button on a task
  - Verify page returns to same scroll position after reload
  - Measure scroll position before and after (should be within ±10px tolerance)

- **Test scroll position during archive operation**
  - Scroll to top matrix (active tasks)
  - Archive a task from DO quadrant
  - Verify scroll position doesn't jump to top
  - Confirm user can see where task was archived from

- **Test scroll position during task addition**
  - Scroll to add task form
  - Fill out and submit form
  - Verify form area remains visible after submission
  - Confirm page doesn't scroll to top or bottom

- **Test scroll position cleanup**
  - Submit multiple forms in sequence
  - Verify sessionStorage scroll position is removed after each restore
  - Confirm no memory leaks or stale scroll data

- **Test scroll position on browser back/forward**
  - Navigate to principles page
  - Use browser back button
  - Verify scroll position is restored correctly
  - Test with various scroll positions (top, middle, bottom)

- **Test scroll behavior without JavaScript**
  - Disable JavaScript in browser
  - Verify forms still submit correctly
  - Confirm graceful degradation (may scroll to top, but functionality works)

#### 2. Task Model Tests (`test_task_model.py`)
- **Test task creation with valid data**
  - Verify all required fields (title, description, quadrant, due_date)
  - Validate quadrant values are restricted to: 'do', 'decide', 'delegate', 'delete'
  - Ensure due_date is a valid date format and not in the past
  - Confirm title length is between 1-100 characters
  - Confirm description length is between 0-500 characters

- **Test task creation with invalid data**
  - Empty/null title should fail
  - Invalid quadrant values should fail
  - Past due dates should fail (or trigger warning)
  - Extremely long text should be truncated or rejected

- **Test task completion**
  - Verify completed flag toggles correctly
  - Ensure completed_at timestamp is set automatically
  - Confirm completed tasks cannot be uncompleted (constraint)

- **Test task archival**
  - Verify original_quadrant is stored before archival
  - Ensure archived_at timestamp is set automatically
  - Confirm archived tasks are hidden from main view
  - Test archived tasks cannot be unarchived (constraint)

#### 2. Task Movement Tests (`test_task_movement.py`)
- **Test valid quadrant transitions**
  - Move from 'do' to 'decide', 'delegate', 'delete'
  - Move from 'decide' to 'do', 'delegate', 'delete'
  - Move from 'delegate' to 'do', 'decide', 'delete'
  - Move from 'delete' to 'do', 'decide', 'delegate'
  - Verify position is updated in database
  - **Verify scroll position maintained after each move**

- **Test scroll position during rapid successive moves**
  - Move task 1 from DO to DECIDE (scroll at 200px)
  - Immediately move task 2 from DELEGATE to DELETE (scroll at 400px)
  - Verify each operation restores correct scroll position
  - Confirm no scroll position conflicts between operations

- **Test invalid quadrant transitions**
  - Cannot move to same quadrant (no-op)
  - Cannot move to non-existent quadrant
  - Cannot move archived tasks
  - Cannot move completed tasks (constraint: must uncomplete first)

- **Test edge cases**
  - Moving non-existent task ID
  - Moving with invalid task ID (non-integer, negative)
  - Simultaneous moves of same task (race condition)

#### 3. Database Operations Tests (`test_database.py`)
- **Test database initialization**
  - Verify SQLite database file is created
  - Confirm all tables exist with correct schema
  - Test database is created in app directory (not system directory)

- **Test CRUD operations**
  - Create: Insert new task successfully
  - Read: Retrieve single task, all tasks, filtered tasks
  - Update: Modify task fields
  - Delete: Soft delete (archive) vs hard delete (never allowed)

- **Test data persistence**
  - Create tasks, restart app, verify tasks still exist
  - Test database file is portable across systems

- **Test data integrity**
  - Prevent duplicate task IDs
  - Prevent orphaned records
  - Enforce foreign key constraints (if any)

### Functional Tests

#### 4. Web Interface Tests (`test_web_interface.py`)
- **Test homepage rendering**
  - Verify all four quadrants are displayed (active matrix)
  - Verify all four archived quadrants are displayed (archived matrix)
  - Confirm color scheme matches design (green, blue, red, gray)
  - Test responsive layout on different screen sizes
  - Verify empty quadrants show "No tasks here" message

- **Test scroll position preservation in UI**
  - Render page with multiple tasks in each quadrant
  - Set scroll position to 500px from top
  - Simulate form submission (move/archive/add)
  - Verify scroll position persists via sessionStorage
  - Test scroll restoration timing (should happen on page load)

- **Test task display**
  - Tasks appear in correct quadrant
  - Title, description, and due date are visible
  - Movement buttons are present and labeled correctly
  - Archive button (📦) is visible on all active task cards
  - Test card layout with multiple tasks in one quadrant

- **Test task movement via buttons**
  - Click arrow buttons to move tasks
  - Verify page refreshes and task appears in new quadrant
  - Confirm movement buttons adjust based on current quadrant
  - Test rapid consecutive button clicks (no duplicate moves)
  - **Verify scroll position maintained during task movement**

- **Test archive button functionality**
  - Click 📦 Archive button on task
  - Verify task disappears from active matrix
  - Confirm task appears in archived matrix (same quadrant)
  - Verify original_quadrant is preserved
  - **Test scroll position remains stable during archiving**

- **Test archived matrix display**
  - Verify "Archived Eisenhower Matrix" header present
  - Confirm archived tasks appear in original quadrants
  - Check "Archived" badge on each quadrant header
  - Verify faded styling on archived task cards
  - Confirm no action buttons on archived tasks (read-only)

- **Test principles page**
  - "View Principles" button navigates to principles page
  - All 10 principles are displayed
  - "Back to Matrix" button returns to homepage
  - Test direct URL access to /principles

#### 5. Task Form Tests (`test_task_forms.py`)
- **Test add task form**
  - Submit form with all required fields
  - Verify task appears in correct quadrant
  - Test form validation (empty fields, invalid dates)
  - Test XSS prevention (HTML in title/description)
  - Confirm form resets after successful submission
  - **Verify scroll position returns to form area after submission**

- **Test form scroll behavior**
  - Open add task form (scroll to top)
  - Fill out form and submit
  - Verify page returns to form location (not top of page)
  - Test with form collapsed and expanded states

- **Test edit task form** (when implemented)
  - Edit existing task fields
  - Verify changes persist in database
  - Test cancel button discards changes
  - Prevent editing archived/completed tasks

#### 6. Error Handling Tests (`test_error_handling.py`)
- **Test 404 errors**
  - Access non-existent routes
  - Verify friendly error page (not default Flask error)

- **Test 500 errors**
  - Simulate database connection failure
  - Test graceful degradation
  - Verify error message doesn't expose system details

- **Test malformed requests**
  - POST with missing data
  - GET with invalid query parameters
  - Non-integer task IDs in URLs

#### 7. Constraint Validation Tests (`test_constraints.py`)
- **Test maximum task limits**
  - Limit total tasks to 100 (prevent database bloat)
  - Limit tasks per quadrant to 25
  - Test warning when approaching limits

- **Test input sanitization**
  - Strip leading/trailing whitespace
  - Prevent SQL injection attempts
  - Block script tags in text fields
  - Test Unicode and special characters

- **Test due date constraints**
  - Cannot set due date more than 1 year in future
  - Warning for dates in the past
  - Auto-format date input

- **Test overdue task indicators**
  - Create task with past due date
  - Verify card has 'overdue' CSS class
  - Check for bright red background (#ffebee)
  - Verify OVERDUE badge appears
  - Confirm due date text is bold red
  - Test that completed tasks don't show as overdue
  - Test that archived tasks don't show as overdue
  - Verify overdue status recalculates daily

### Integration Tests

#### 8. End-to-End Workflow Tests (`test_e2e.py`)
- **Test complete task lifecycle**
  1. Create new task in 'decide' quadrant
  2. Move to 'do' quadrant
  3. Mark as completed
  4. Archive completed task
  5. Verify task appears in archive view
  6. Confirm original quadrant is 'decide'

- **Test multi-task scenarios**
  - Create 5 tasks in different quadrants
  - Move each task through multiple quadrants
  - Complete 2 tasks, archive 1 task
  - Verify database state matches expectations

- **Test offline operation**
  - Disconnect network
  - Perform all CRUD operations
  - Restart application
  - Verify all changes persisted

#### 9. Cross-Platform Tests (`test_platform_compatibility.py`)
- **Test on Windows**
  - Run app on Windows 10/11
  - Verify file paths use correct separators
  - Test virtual environment activation

- **Test on macOS**
  - Run app on macOS 12+
  - Verify database file location
  - Test Python 3.x compatibility

- **Test on Linux**
  - Run app on Ubuntu 20.04+
  - Test with different Python versions (3.6-3.12)
  - Verify no dependency on system packages

### Security Tests

#### 10. Security Validation Tests (`test_security.py`)
- **Test CSRF protection**
  - Verify forms include CSRF tokens (when implemented)
  - Test form submission without valid token fails

- **Test XSS prevention**
  - Input `<script>alert('XSS')</script>` in all text fields
  - Verify output is escaped/sanitized
  - Test stored XSS and reflected XSS

- **Test SQL injection prevention**
  - Input `'; DROP TABLE tasks; --` in text fields
  - Verify SQLAlchemy prevents injection
  - Test with various injection patterns

- **Test path traversal**
  - Attempt to access `../../etc/passwd` via URLs
  - Verify static file serving is restricted

### Performance Tests

#### 11. Load Tests (`test_performance.py`)
- **Test with maximum tasks**
  - Create 100 tasks (system limit)
  - Verify page loads in < 2 seconds
  - Test moving tasks is still responsive

- **Test database query efficiency**
  - Measure query time for retrieving all tasks
  - Verify no N+1 query problems
  - Test with concurrent requests (5 users)

### Test Execution Strategy

#### Test Environment
- Use `pytest` as testing framework
- Use `pytest-flask` for Flask-specific testing
- Use `pytest-cov` for code coverage reports
- Target: 90%+ code coverage

#### Test Data
- Use fixtures for sample tasks
- Create separate test database (not production db)
- Reset database between test runs

#### Continuous Testing
- Run unit tests on every code change
- Run functional tests before commits
- Run full test suite before releases

#### Test Documentation
- Each test file includes docstring explaining purpose
- Each test function has clear, descriptive name
- Use assertions with helpful error messages

### Constraints to Enforce Through Testing

1. **Single-Purpose Constraint**: Each quadrant serves one purpose only
2. **Immutability Constraint**: Completed and archived tasks cannot be modified
3. **Simplicity Constraint**: No more than 4 quadrants, no sub-quadrants
4. **Limit Constraint**: Maximum 100 total tasks to prevent bloat
5. **Offline Constraint**: All operations work without internet
6. **Portability Constraint**: Application runs on any platform with Python 3.6+
7. **Self-Contained Constraint**: No external APIs or services required
8. **Data Safety Constraint**: No permanent data deletion (soft delete only)

### Test Maintenance

- Review and update tests when adding new features
- Remove obsolete tests when removing features
- Refactor tests to avoid duplication
- Keep test execution time under 30 seconds total