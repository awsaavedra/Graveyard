


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